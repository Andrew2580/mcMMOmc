package org.mcmmo.mcmmomc.commands;

import org.bukkit.ChatColor;
import org.mcmmo.mcmmomc.mcMMOmc;

public class TradeCommand extends ChatCommand {
	public TradeCommand(mcMMOmc plugin) {
		super(plugin);
		this.name = "Trade";
		this.color = ChatColor.GOLD;
		this.format = color + "{" + ChatColor.WHITE + "__NAME__" + color + "} __MESSAGE__";
		this.logFormat = "[T]<__NAME__> __MESSAGE__";
	}
}
