package ch.atdit.smp.Events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.mashape.unirest.http.Unirest;

import ch.atdit.smp.Main;

public class Leave implements Listener {
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		Player p = event.getPlayer();
		String uuid = p.getUniqueId().toString();
		event.setQuitMessage(null);
		
		Main.instance().getConfig().set("latestQuit", System.currentTimeMillis());
		long latestJoin = Main.instance().getConfig().getLong("latestJoin." + uuid);
		long onlineTime = Main.instance().getConfig().getLong("onlineTime." + uuid);
		Main.instance().getConfig().set("onlineTime." + uuid, onlineTime + (System.currentTimeMillis() - latestJoin));
		Main.instance().saveConfig();

		Bukkit.broadcastMessage(ChatColor.DARK_AQUA + p.getName() + ChatColor.AQUA + " left the server!");
	}
}
