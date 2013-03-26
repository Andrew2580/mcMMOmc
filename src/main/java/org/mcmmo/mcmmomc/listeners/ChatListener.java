package org.mcmmo.mcmmomc.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.mcmmo.mcmmomc.mcMMOmc;

public class ChatListener implements Listener {
	private mcMMOmc plugin;

	public ChatListener(mcMMOmc plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onPlayerChatAsync(AsyncPlayerChatEvent event) {
		if(plugin.hasEnabled(event.getPlayer().getName())) {
			plugin.handleChat(plugin.getEnabled(event.getPlayer().getName()), event.getPlayer().getName(), event.getMessage());
			event.setCancelled(true);
		}
	}
}
