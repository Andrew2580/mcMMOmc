package org.mcmmo.mcmmomc;

import org.bukkit.ChatColor;
import org.bukkit.command.PluginCommand;

import org.mcmmo.mcmmomc.commands.ChatCommand;

public class Config {
	public static void load(mcMMOmc plugin) {
		plugin.saveDefaultConfig();

		String[] channels = { "Trade", "Misc" };
		for(String channel : channels) {
			try {
				String colorName = plugin.getConfig().getString(channel + ".color");
				ChatColor color = ChatColor.valueOf(colorName);
				String format = plugin.getConfig().getString(channel + ".format");
				String logFormat = plugin.getConfig().getString(channel + ".log_format");

				ChatCommand command = new ChatCommand(plugin, channel, color, format, logFormat);
				plugin.commands.put(channel, command);

				PluginCommand pluginCommand = plugin.getCommand(channel.toLowerCase() + "chat");
				pluginCommand.setPermission("mcmmomc." + channel.toLowerCase());
				pluginCommand.setPermissionMessage(ChatColor.DARK_RED + "Insufficient permissions.");
				pluginCommand.setExecutor(command);
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}
