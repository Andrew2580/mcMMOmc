package org.mcmmo.mcmmomc.commands;

import org.bukkit.ChatColor;
import org.mcmmo.mcmmomc.mcMMOmc;

public class MiscCommand extends ChatCommand {
	public MiscCommand(mcMMOmc plugin) {
		super(plugin);
		this.name = "Misc";
		this.color = ChatColor.DARK_AQUA;
		this.format = color + "{" + ChatColor.WHITE + "__NAME__" + color + "}" + ChatColor.WHITE + "__MESSAGE__";
		this.logFormat = "[M]<__NAME__> __MESSAGE__";
	}
}
