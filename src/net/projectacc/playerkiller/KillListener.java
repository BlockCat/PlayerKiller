package net.projectacc.playerkiller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class KillListener implements CommandExecutor{

	PlayerKiller plugin;
	HashMap<String, Boolean> killList = new HashMap<String, Boolean>();

	public KillListener(PlayerKiller playerKiller) {
		plugin = playerKiller;
		plugin.getServer().getScheduler().scheduleSyncRepeatingTask(playerKiller, new Runnable() {

			@Override
			public void run() {
				for (Map.Entry<String, Boolean> map : killList.entrySet()) {
					if (map.getValue()) {
					Player p = Bukkit.getServer().getPlayerExact(map.getKey());
					p.setHealth(0);
					}
				}

			}

		}, 0, 3600);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {

		if (!(sender instanceof Player))
			return false;

		Player p = (Player) sender;

		if (!p.isOp()) {
			p.sendMessage(ChatColor.DARK_RED + "You can't use this command! ");
			return true;
		}

		if (args.length == 0) {
			p.sendMessage("");
			p.sendMessage("");
			p.sendMessage("");
			p.sendMessage("");
			p.sendMessage("");
			return true;
		}

		if (args[0].equalsIgnoreCase("add")) {
			if (args.length >= 2) {
				if (!killList.containsKey(args[1])) {
					killList.put(args[1], true);
					p.sendMessage(ChatColor.GREEN + "Player added!");
				} else {
					p.sendMessage(ChatColor.DARK_RED + "Name already in list.");
				}
			} else {
				p.sendMessage(ChatColor.DARK_RED + "Need a player name.");
			}
		}

		if (args[0].equalsIgnoreCase("remove")) {
			if (args.length >= 2) {
				if (killList.containsKey(args[1])) {
					killList.put(args[1], false);
					p.sendMessage(ChatColor.GREEN + "Player removed!");
				} else {
					p.sendMessage(ChatColor.DARK_RED + "Name not in list.");
				}
			} else {
				p.sendMessage(ChatColor.DARK_RED + "Need a player name.");
			}
		}

		return true;
	}

	public void save() {
		
		FileConfiguration conf = plugin.getConfig();
		
		for (Map.Entry<String, Boolean> map : killList.entrySet()) {
			conf.set(map.getKey(), map.getValue());
		}
		plugin.saveConfig();
	}
	
	public void load() {
		FileConfiguration conf = plugin.getConfig();
		Map<String, Object> m = conf.getValues(true);
		
		for (Entry<String, Object> map : m.entrySet()) {
			killList.put(map.getKey(), (Boolean) map.getValue());
		}
	}


}

