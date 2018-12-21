package ch.atdit.smp.Events;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.mashape.unirest.http.Unirest;

import ch.atdit.smp.Main;

public class Join implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player p = event.getPlayer();
		String uuid = p.getUniqueId().toString();
		event.setJoinMessage(null);

		Main.instance().getConfig().set("latestJoin." + uuid, System.currentTimeMillis());
		Main.instance().saveConfig();

		List<String> joinedPlayers = Main.instance().getConfig().getStringList("joinedPlayers");
		if (joinedPlayers.contains(uuid)) {
			Bukkit.broadcastMessage(ChatColor.DARK_AQUA + p.getName() + ChatColor.AQUA + " joined the server!");
		} else {
			joinedPlayers.add(uuid);
			Main.instance().getConfig().set("joinedPlayers", joinedPlayers);
			Main.instance().getConfig().set("firstJoin." + uuid, System.currentTimeMillis());
			Main.instance().saveConfig();
			Bukkit.broadcastMessage(ChatColor.DARK_AQUA + p.getName() + ChatColor.AQUA + " joined for the first time!");
		}
	}
}
