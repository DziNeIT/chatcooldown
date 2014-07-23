package pw.ollie.chatcooldown;

import java.util.UUID;

public final class Cooldown {
	private final UUID player;
	private final long length;
	private final long time;

	public Cooldown(final UUID player, final long length, final long time) {
		this.player = player;
		this.length = length;
		this.time = time;
	}

	public UUID getPlayer() {
		return player;
	}

	public boolean isExpired() {
		return System.currentTimeMillis() >= time + length;
	}

	public long getTimeRemaining() {
		return (time + length) - System.currentTimeMillis();
	}
}
