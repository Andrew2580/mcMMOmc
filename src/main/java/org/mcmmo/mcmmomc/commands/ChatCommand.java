package org.mcmmo.mcmmomc.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.mcmmo.mcmmomc.mcMMOmc;

import com.gmail.nossr50.api.ChatAPI;

public class ChatCommand implements CommandExecutor {
	protected mcMMOmc plugin;
	protected String name;
	protected ChatColor color;
	protected String format;
	protected String logFormat;
	protected boolean colorCodes;

	public ChatCommand(mcMMOmc plugin, String name, ChatColor color, String format, String logFormat, boolean colorCodes) {
		this.plugin = plugin;
		this.name = name;
		this.color = color;
		this.format = format;
		this.logFormat = logFormat;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		switch(args.length) {
			case 0:
				if(isEnabled(sender)) {
					disable(sender);
					sender.sendMessage(name + " Chat only " + ChatColor.RED + "Off");
				} else {
					if(hasLeft(sender)) {
						join(sender);
						sender.sendMessage("You have " + ChatColor.GREEN + "joined " + color + name);
					}

					enable(sender);
					sender.sendMessage(name + " Chat only " + ChatColor.GREEN + "On");
				}
				return true;

			case 1:
				if(args[0].equalsIgnoreCase("?")) {
					return false;
				} else if(args[0].equalsIgnoreCase("join")) {
					if(!hasLeft(sender)) {
						sender.sendMessage(ChatColor.DARK_RED + "You are already in " + color + name);
					} else {
						join(sender);
						sender.sendMessage("You have " + ChatColor.GREEN + "joined " + color + name);
					}
					return true;
				} else if(args[0].equalsIgnoreCase("leave")) {
					if(hasLeft(sender)) {
						sender.sendMessage(ChatColor.DARK_RED + "You are not in " + color + name);
					} else {
						if(isEnabled(sender)) {
							disable(sender);
							sender.sendMessage(name + " Chat only " + ChatColor.RED + "Off");
						}

						leave(sender);
						sender.sendMessage("You have " + ChatColor.RED + "left " + color + name);
					}
					return true;
				}
				// Fallthrough

			default:
				if(hasLeft(sender)) {
					sender.sendMessage(ChatColor.DARK_RED + "You are not in " + color + name);
				} else {
					if(sender instanceof Player) {
						handleChat(sender.getName(), ((Player) sender).getDisplayName(), buildChatMessage(args, 0));
					} else {
						handleChat(sender.getName(), sender.getName(), buildChatMessage(args, 0));
					}
				}
				return true;
		}
	}

	public void handleChat(String playerName, String displayName, String message) {
		String sendMessage = ChatColor.translateAlternateColorCodes('&', format.replace("__NAME__", playerName).replace("__DISPLAY_NAME__", displayName));
		if(colorCodes) {
			sendMessage = ChatColor.translateAlternateColorCodes('&', sendMessage.replace("__MESSAGE__", message));
		} else {
			sendMessage = sendMessage.replace("__MESSAGE__", message);
		}
		String logMessage = ChatColor.translateAlternateColorCodes('&', logFormat.replace("__NAME__", playerName).replace("__DISPLAY_NAME__", displayName).replace("__MESSAGE__", message));
		for(Player player : plugin.getServer().getOnlinePlayers()) {
			if(player.hasPermission("mcmmomc." + name.toLowerCase()) && !plugin.hasLeft(player.getName(), name)) {
				player.sendMessage(sendMessage);
			}
		}
		plugin.getLogger().info(logMessage);
	}

	public String getName() {
		return name;
	}

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

		// These API methods do not currently send messages, need to be changed to support such.
		if(ChatAPI.isUsingAdminChat(playerName)) ChatAPI.toggleAdminChat(playerName);
		if(ChatAPI.isUsingPartyChat(playerName)) ChatAPI.togglePartyChat(playerName);
	}

	private void disable(CommandSender sender) {
		plugin.disable(sender.getName(), name);
	}

	private boolean hasLeft(CommandSender sender) {
		return plugin.hasLeft(sender.getName(), name);
	}

	private void join(CommandSender sender) {
		plugin.join(sender.getName(), name);
	}

	private void leave(CommandSender sender) {
		plugin.leave(sender.getName(), name);
	}
}
