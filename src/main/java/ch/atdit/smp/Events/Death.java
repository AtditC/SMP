package ch.atdit.smp.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.mashape.unirest.http.Unirest;

import ch.atdit.smp.Main;

public class Death implements Listener {
	
	@EventHandler
	public void onKill(PlayerDeathEvent event) {
		Player p = event.getEntity();
		String killedUUID = p.getUniqueId().toString();
		if (p.getKiller() != null) {
			String killerUUID = p.getKiller().getUniqueId().toString();

			Main.instance().getConfig().set("deaths." + killedUUID, Main.instance().getConfig().getInt("deaths." + killedUUID) + 1);
			Main.instance().getConfig().set("kills." + killerUUID, Main.instance().getConfig().getInt("kills." + killerUUID) + 1);
			Main.instance().saveConfig();
			
			try {
	            Unirest.post("http://localhost:35568/death")
	                    .header("Content-Type", "application/json").queryString("killed", p.getName()).queryString("killer", p.getKiller().getName()).queryString("message", event.getDeathMessage())
	                    .asJson();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		} else {
			Main.instance().getConfig().set("alldeaths." + killedUUID, Main.instance().getConfig().getInt("alldeaths." + killedUUID) + 1);
			Main.instance().saveConfig();
			
			try {
	            Unirest.post("http://localhost:35568/death")
	                    .header("Content-Type", "application/json").queryString("killed", p.getName()).queryString("message", event.getDeathMessage())
	                    .asJson();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		}
	}
}
