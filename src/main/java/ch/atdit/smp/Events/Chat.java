package ch.atdit.smp.Events;

import java.text.DecimalFormat;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.mashape.unirest.http.Unirest;

import ch.atdit.smp.Main;

public class Chat implements Listener {

	private Integer getLevel(Long onlineTime) {
		int divider = 60 * 1000; // 60 seconds
		DecimalFormat formatLevel = new DecimalFormat("#0");
		String groundLevel = formatLevel.format(Math.sqrt(onlineTime / divider)); // Exponential growth
		return Integer.parseInt(groundLevel) + 1;
	}

	private String formatMessage(String message, String name, String prefix, Long onlineTime) {
		if (prefix != null) {
			return ChatColor.translateAlternateColorCodes('&', "&b[" + getLevel(onlineTime) + "]&r " + prefix + "&r " + name + ": " + message);
		} else {
			return ChatColor.translateAlternateColorCodes('&', "&b[" + getLevel(onlineTime) + "]&r " + name + ": " + message);
		}
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		Player p = event.getPlayer();
		String uuid = p.getUniqueId().toString();
		String prefix = Main.instance().getConfig().getString("prefix." + uuid);

		Long latestJoin = Main.instance().getConfig().getLong("latestJoin." + uuid);
		Long onlineTime = Main.instance().getConfig().getLong("onlineTime." + uuid + "") + (System.currentTimeMillis() - latestJoin);
		
		event.setFormat(formatMessage(event.getMessage(), p.getName(), prefix, onlineTime).replace("%", "%%"));
	}
}
