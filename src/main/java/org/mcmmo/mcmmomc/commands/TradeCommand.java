package org.mcmmo.mcmmomc.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.mcmmo.mcmmomc.mcMMOmc;

public class TradeCommand extends ChatCommand {
	public TradeCommand(mcMMOmc plugin) {
		super(plugin);
		this.name = "Trade";
		this.color = ChatColor.GOLD;
		this.format = color + "{" + ChatColor.WHITE + "__NAME__" + color + "}" + ChatColor.WHITE + "__MESSAGE__";
	}

	@Override
	protected void handleChat(CommandSender sender, String message) {
		// TODO Auto-generated method stub
		
	}
}
