package pw.ollie.chatcooldown;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class Cooldowns {
	private final boolean commands;
	private final int cooldownLength;
	private final Map<UUID, Cooldown> cooldowns;

	Cooldowns(final boolean checkCommands, final int cooldownTime) {
		commands = checkCommands;
		cooldownLength = cooldownTime;
		cooldowns = new HashMap<>();
	}

	public void add(final Cooldown cooldown) {
		final UUID player = cooldown.getPlayer();
		if (cooldowns.containsKey(player)) {
			cooldowns.remove(player);
		}
		cooldowns.put(player, cooldown);
	}

	public void remove(final UUID player) {
		cooldowns.remove(player);
	}

	public boolean hasCooldown(final UUID player) {
		final Cooldown cooldown = cooldowns.get(player);
		if (cooldown == null) {
			return false;
		}
		if (cooldown.isExpired()) {
			cooldowns.remove(player);
			return false;
		}
		return true;
	}

	public long getTimeRemaining(final UUID player) {
		final Cooldown cooldown = cooldowns.get(player);
		if (cooldown != null) {
			return cooldown.getTimeRemaining();
		} else {
			return -1;
		}
	}

	public boolean checkCommands() {
		return commands;
	}

	public int getCooldownLength() {
		return cooldownLength;
	}
}
