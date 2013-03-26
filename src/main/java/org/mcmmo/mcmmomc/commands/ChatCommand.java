package org.mcmmo.mcmmomc.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import org.mcmmo.mcmmomc.mcMMOmc;

import com.gmail.nossr50.api.ChatAPI;

public abstract class ChatCommand implements CommandExecutor {
	private mcMMOmc plugin;

	protected String name;
	protected ChatColor color;

	public ChatCommand(mcMMOmc plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		switch(args.length) {
			case 0:
				if(isEnabled(sender)) {
					disable(sender);
				} else {
					enable(sender);
				}
				return true;

			case 1:
				if(args[0].equalsIgnoreCase("?")) {
					return false;
				} else if(args[0].equalsIgnoreCase("join")) {
					if(isEnabled(sender)) {
						sender.sendMessage(ChatColor.DARK_RED + "You are already in " + color + name);
					} else {
						enable(sender);
					}
					return true;
				} else if(args[0].equalsIgnoreCase("leave")) {
					if(!isEnabled(sender)) {
						sender.sendMessage(ChatColor.DARK_RED + "You are not in " + color + name);
					} else {
						disable(sender);
					}
					return true;
				}
				// Fallthrough

			default:
				handleChat(sender, buildChatMessage(args, 0));
				return true;
		}
	}

	protected abstract void handleChat(CommandSender sender, String message);

	protected String buildChatMessage(String[] args, int index) {
		StringBuilder builder = new StringBuilder();
		builder.append(args[index]);

		for (int i = index + 1; i < args.length; i++) {
			builder.append(" ");
			builder.append(args[i]);
		}

		return builder.toString();
	}

	private boolean isEnabled(CommandSender sender) {
		return plugin.hasEnabled(sender.getName(), name);
	}

	private void enable(CommandSender sender) {
		String playerName = sender.getName();
		plugin.enable(playerName, name);
		if(ChatAPI.isUsingAdminChat(playerName)) ChatAPI.toggleAdminChat(playerName);
		if(ChatAPI.isUsingPartyChat(playerName)) ChatAPI.togglePartyChat(playerName);
	}

	private void disable(CommandSender sender) {
		plugin.disable(sender.getName(), name);
	}
}
