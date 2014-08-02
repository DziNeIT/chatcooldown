package pw.ollie.chatcooldown;

import java.io.File;
import java.io.IOException;

import org.bukkit.plugin.java.JavaPlugin;

public final class ChatCooldown extends JavaPlugin {
	private Cooldowns manager;

	@Override
	public void onEnable() {
		checkFiles();

		if (!getConfig().contains("check-commands")) {
			getConfig().set("check-commands", true);
			saveConfig();
		}

		manager = new Cooldowns(getConfig().getBoolean("check-commands", true),
				getConfig().getInt("cooldown-length", 5) * 1000);
		getServer().getPluginManager().registerEvents(new CCListener(manager),
				this);
	}

	@Override
	public void onDisable() {
		manager = null;
	}

	public Cooldowns getCooldownManager() {
		return manager;
	}

	private void checkFiles() {
		final File data = getDataFolder();
		final File conf = new File(data, "config.yml");

		if (!data.exists()) {
			data.mkdirs();
		}
		if (!conf.exists()) {
			try {
				conf.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
