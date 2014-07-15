package dzineit.chatcooldown;

import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public final class CCListener implements Listener {
	private final Cooldowns manager;

	CCListener(final Cooldowns manager) {
		this.manager = manager;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerChat(final AsyncPlayerChatEvent event) {
		final Player player = event.getPlayer();
		final UUID id = player.getUniqueId();

		if (!bypassesFilter(player) && manager.hasCooldown(id)) {
			event.setCancelled(true);
			player.sendMessage(ChatColor.RED
					+ "Slow down! You're sending messages too fast!");
		} else {
			manager.add(new Cooldown(id, manager.getCooldownLength(), System
					.currentTimeMillis()));
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onCommandPreProcess(final PlayerCommandPreprocessEvent event) {
		final Player player = event.getPlayer();
		final UUID id = player.getUniqueId();

		if (!bypassesFilter(player) && manager.checkCommands()
				&& manager.hasCooldown(id)) {
			event.setCancelled(true);
			player.sendMessage(ChatColor.RED
					+ "Slow down! You're sending messages too fast!");
		} else {
			manager.add(new Cooldown(id, manager.getCooldownLength(), System
					.currentTimeMillis()));
		}
	}

	private boolean bypassesFilter(final Player player) {
		return player.hasPermission("chatcooldown.bypass") || player.isOp();
	}
}
