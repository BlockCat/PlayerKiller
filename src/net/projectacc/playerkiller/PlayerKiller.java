package net.projectacc.playerkiller;

import org.bukkit.plugin.java.JavaPlugin;

public class PlayerKiller extends JavaPlugin {

	private KillListener listener;
	
	public void onEnable() {
		listener = new KillListener(this);
		this.getCommand("pkill").setExecutor(listener);
	}
	
}
