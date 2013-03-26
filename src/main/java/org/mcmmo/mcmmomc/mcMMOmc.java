package org.mcmmo.mcmmomc;

import java.io.IOException;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcmmo.mcmmomc.commands.ChatCommand;
import org.mcmmo.mcmmomc.commands.MiscCommand;
import org.mcmmo.mcmmomc.commands.TradeCommand;
import org.mcmmo.mcmmomc.listeners.ChatListener;
import org.mcstats.Metrics;

public class mcMMOmc extends JavaPlugin {
	// Player -> Channel
	private HashMap<String, String> enabled = new HashMap<String, String>();
	private HashMap<String, String> left = new HashMap<String, String>();

	private HashMap<String, ChatCommand> commands = new HashMap<String, ChatCommand>();

	@Override
	public void onEnable() {
		ChatCommand trade = new TradeCommand(this);
		ChatCommand misc = new MiscCommand(this);

		commands.put(trade.getName(), trade);
		commands.put(misc.getName(), misc);

		PluginCommand tradeCommand = getCommand("tradechat");
		tradeCommand.setPermission("mcmmomc.trade");
		tradeCommand.setPermissionMessage(ChatColor.DARK_RED + "Insufficient permissions.");
		tradeCommand.setExecutor(trade);

		PluginCommand miscCommand = getCommand("miscchat");
		miscCommand.setPermission("mcmmomc.misc");
		miscCommand.setPermissionMessage(ChatColor.DARK_RED + "Insufficient permissions.");
		miscCommand.setExecutor(misc);

		getServer().getPluginManager().registerEvents(new ChatListener(this), this);

		metrics();

		getLogger().info("Finished Loading " + getDescription().getFullName());
	}

	@Override
	public void onDisable() {
		getLogger().info("Finished Unloading " + getDescription().getFullName());
	}

	public boolean hasEnabled(String playerName) {
		return enabled.containsKey(playerName) && enabled.get(playerName) != null;
	}

	public boolean hasEnabled(String playerName, String channelName) {
		return enabled.containsKey(playerName) && enabled.get(playerName) != null && enabled.get(playerName).equals(channelName);
	}

	public String getEnabled(String playerName) {
		return enabled.get(playerName);
	}

	public void enable(String playerName, String channelName) {
		enabled.put(playerName, channelName);
	}

	public void disable(String playerName, String channelName) {
		if(hasEnabled(playerName, channelName)) enabled.remove(playerName);
	}

	public boolean hasLeft(String playerName, String channelName) {
		return left.containsKey(playerName) && left.get(playerName) != null && left.get(playerName).equals(channelName);
	}

	public void leave(String playerName, String channelName) {
		left.put(playerName, channelName);
	}

	public void join(String playerName, String channelName) {
		if(hasLeft(playerName, channelName)) left.remove(playerName);
	}

	public void handleChat(String channelName, String playerName, String message) {
		commands.get(channelName).handleChat(playerName, message);
	}

	private void metrics() {
		try {
			Metrics metrics = new Metrics(this);
			metrics.start();
		} catch (IOException e) { }
	}
}
