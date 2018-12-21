package ch.atdit.smp;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

import ch.atdit.smp.Commands.Buy;
import ch.atdit.smp.Commands.Debug;
import ch.atdit.smp.Commands.Prefix;
import ch.atdit.smp.Commands.SMP;
import ch.atdit.smp.Commands.Settings;
import ch.atdit.smp.Commands.Spawn;
import ch.atdit.smp.Commands.Stats;
import ch.atdit.smp.Events.Chat;
import ch.atdit.smp.Events.Death;
import ch.atdit.smp.Events.Join;
import ch.atdit.smp.Events.Leave;

public class Main extends JavaPlugin implements Listener {

    private static Main instance;

	public static Main instance() {
		return instance;
	}

	@Override
	public void onEnable() {
		Bukkit.getServer().getPluginManager().registerEvents(new Chat(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Death(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Join(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Leave(), this);
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		getCommand("stats").setExecutor(new Stats());
		getCommand("prefix").setExecutor(new Prefix());
		getCommand("smp").setExecutor(new SMP());
		getCommand("spawn").setExecutor(new Spawn());
		getCommand("setspawn").setExecutor(new Spawn());
		getCommand("enable").setExecutor(new Settings());
		getCommand("disable").setExecutor(new Settings());
		getCommand("buy").setExecutor(new Buy());
		getCommand("uuid").setExecutor(new Debug());
		getCommand("name").setExecutor(new Debug());
		getLogger().info("SMP enabled!");
		saveDefaultConfig();
		
		getConfig().set("latestStart", System.currentTimeMillis());

		new BukkitRunnable() {

            private int getLevel(Long onlineTime) {
				int divider = 60 * 1000; // 60 seconds
				DecimalFormat formatLevel = new DecimalFormat("#0");
				String groundLevel = formatLevel.format(Math.sqrt(onlineTime / divider)); // Exponential growth
				return Integer.parseInt(groundLevel) + 1;
			}

            private String convertToDate(Long timestamp) {
				SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss Z");

				return format.format(new Date(timestamp));
			}

            private String onlineTime(Long onlineTime, Long latestJoin) {
				long ms;
				
				if (latestJoin == 0) {
					ms = onlineTime;
				} else {
					ms = onlineTime + (System.currentTimeMillis() - latestJoin);
				}
				
				String output = "";

				int days = (int) Math.floor(ms / 86400 / 1000);
				ms -= days * 86400 * 1000;

				int hours = (int) Math.floor(ms / 3600 / 1000);
				ms -= hours * 3600 * 1000;

				int minutes = (int) Math.floor(ms / 60 / 1000);
				ms -= minutes * 60 * 1000;

				int seconds = (int) Math.floor(ms / 1000);
				ms -= seconds * 1000;

				if (days > 1) {
					output += (days + " days, ");
				} else if (days == 1) {
					output += "1 day, ";
				}

				if (hours > 1) {
					output += (hours + " hours, ");
				} else if (hours == 1) {
					output += "1 hour, ";
				}

				if (minutes > 1) {
					output += (minutes + " minutes and ");
				} else if (minutes == 1) {
					output += "1 minute and ";
				}

				if (seconds > 1 || seconds == 0) {
					output += (seconds + " seconds");
				} else if (seconds == 1) {
					output += "1 second";
				}

				return output;
			}

			private String getKD(Double kills, Double deaths) {
				if (kills == 0 && deaths == 0) {
					return "0.00";
				} else if (kills > 0) {
					DecimalFormat decimalFormat0 = new DecimalFormat("#0.00");
					return decimalFormat0.format(kills);
				} else {
					DecimalFormat decimalFormat = new DecimalFormat("#0.00");
					return decimalFormat.format(kills / deaths);
				}
			}
			
			@Override
			public void run() {
				try {
					HttpResponse<JsonNode> response = Unirest.post("http://localhost:35568/commands")
							.header("Content-Type", "application/json").asJson();
					if (response.getBody().getObject().length() > 0) {
						for (int i = 0; i < response.getBody().getObject().length(); i++) {
							String command = response.getBody().getArray().getJSONObject(0).getJSONObject(String.valueOf(i)).getString("command");
							if (command.equalsIgnoreCase("getPlayers")) {
								String players = "";
								int i2 = 0;
								for (Player p : Bukkit.getOnlinePlayers()) {
									if (i2 == 0) {
										players += p.getName();
									} else {
										players += ", " + p.getName();
									}
									i2++;
								}
								
								if (players.equalsIgnoreCase("")) {
									players = "No players are online.";
								} else {
									players = String.valueOf(i2) + " players are currently online: " + players;
								}
								try {
									Unirest.post("http://localhost:35568/commands")
											.header("Content-Type", "application/json").queryString("response", players).asJson();
								} catch (Exception e) {
									e.printStackTrace();
								}
							} else if (command.equalsIgnoreCase("restart")) {
								getServer().dispatchCommand(getServer().getConsoleSender(), "restart");
							} else if (command.equalsIgnoreCase("stats")) {
								String output = "";
								@SuppressWarnings("deprecation")
								OfflinePlayer p = Bukkit.getOfflinePlayer(response.getBody().getArray().getJSONObject(0).getJSONObject(String.valueOf(i)).getString("player"));
								if (p.hasPlayedBefore() && p.isOnline()) { // Played before and online
									String uuid = p.getUniqueId().toString();
									Double kills = getConfig().getDouble("kills." + uuid);
									Double deaths = getConfig().getDouble("deaths." + uuid);
									Double alldeaths = getConfig().getDouble("alldeaths." + uuid);
									String kdr = getKD(kills, deaths);
									String allkdr = getKD(kills, alldeaths);
									output += "**Viewing stats for: " + p.getName() + "**";
									output += "\n\nLevel: " + getLevel(getConfig().getLong("onlineTime." + uuid) + (System.currentTimeMillis() - getConfig().getLong("latestJoin" + uuid)));
									output += "\n\nTotal playtime: " + onlineTime(getConfig().getLong("onlineTime." + uuid), getConfig().getLong("latestJoin." + uuid));
									output += "\nFirst joined: " + convertToDate(getConfig().getLong("firstJoin." + uuid));
									output += "\nOnline since: " + onlineTime(0L, getConfig().getLong("latestJoin." + uuid));
									output += "\n\nPvP Kills: " + getConfig().getInt("kills." + uuid);
									output += "\nPvP Deaths: " + getConfig().getInt("deaths." + uuid);
									output += "\nAll Deaths: " + getConfig().getInt("alldeaths." + uuid);
									output += "\n\nPvP K/D: " + kdr;
									output += "\nK/D: " + allkdr;
								} else if (p.hasPlayedBefore() && !p.isOnline()) { // Played before but offline
									String uuid = p.getUniqueId().toString();
									Double kills = getConfig().getDouble("kills." + uuid);
									Double deaths = getConfig().getDouble("deaths." + uuid);
									Double alldeaths = getConfig().getDouble("alldeaths." + uuid);
									String kdr = getKD(kills, deaths);
									String allkdr = getKD(kills, alldeaths);
									output += "**Viewing stats for: " + p.getName() + "**";
									output += "\n\nLevel: " + getLevel(getConfig().getLong("onlineTime." + uuid));
									output += "\n\nTotal playtime: " + onlineTime(getConfig().getLong("onlineTime." + uuid), 0L);
									output += "\nFirst joined: " + convertToDate(getConfig().getLong("firstJoin." + uuid));
									output += "\nOffline since: " + onlineTime(0L, getConfig().getLong("latestJoin." + uuid));
									output += "\n\nPvP Kills: " + getConfig().getInt("kills." + uuid);
									output += "\nPvP Deaths: " + getConfig().getInt("deaths." + uuid);
									output += "\nAll Deaths: " + getConfig().getInt("alldeaths." + uuid);
									output += "\n\nPvP K/D: " + kdr;
									output += "\nK/D: " + allkdr;
								} else if (!p.hasPlayedBefore() && p.isOnline()) { // First join and online
									String uuid = p.getUniqueId().toString();
									Double kills = getConfig().getDouble("kills." + uuid);
									Double deaths = getConfig().getDouble("deaths." + uuid);
									Double alldeaths = getConfig().getDouble("alldeaths." + uuid);
									String kdr = getKD(kills, deaths);
									String allkdr = getKD(kills, alldeaths);
									output += "**Viewing stats for: " + p.getName() + "**";
									output += "\n\nLevel: " + getLevel(getConfig().getLong("onlineTime." + uuid) + (System.currentTimeMillis() - getConfig().getLong("latestJoin" + uuid)));
									output += "\n\nTotal playtime: " + onlineTime(getConfig().getLong("onlineTime." + uuid), getConfig().getLong("latestJoin." + uuid));
									output += "\nFirst joined: " + convertToDate(getConfig().getLong("firstJoin." + uuid));
									output += "\nOffline since: " + onlineTime(0L, getConfig().getLong("latestJoin." + uuid));
									output += "\n\nPvP Kills: " + getConfig().getInt("kills." + uuid);
									output += "\nPvP Deaths: " + getConfig().getInt("deaths." + uuid);
									output += "\nAll Deaths: " + getConfig().getInt("alldeaths." + uuid);
									output += "\n\nPvP K/D: " + kdr;
									output += "\nK/D: " + allkdr;
								} else {
									System.out.println("Player not found");
								}
								try {
									Unirest.post("http://localhost:35568/commands")
											.header("Content-Type", "application/json").queryString("response", output).asJson();
								} catch (Exception e) {
									e.printStackTrace();
								}
							} else if (command.equalsIgnoreCase("kick")) {
								String toKick = response.getBody().getArray().getJSONObject(0).getJSONObject(String.valueOf(i)).getString("toKick");
								String reason = response.getBody().getArray().getJSONObject(0).getJSONObject(String.valueOf(i)).getString("reason");
								getServer().dispatchCommand(getServer().getConsoleSender(), command + " " + toKick + " " + reason);
							} else if (command.equalsIgnoreCase("ban")) {
								String toBan = response.getBody().getArray().getJSONObject(0).getJSONObject(String.valueOf(i)).getString("toBan");
								String reason = response.getBody().getArray().getJSONObject(0).getJSONObject(String.valueOf(i)).getString("reason");
								getServer().dispatchCommand(getServer().getConsoleSender(), command + " " + toBan + " " + reason);
							} else if (command.equalsIgnoreCase("mute")) {
								String toMute = response.getBody().getArray().getJSONObject(0).getJSONObject(String.valueOf(i)).getString("toMute");
								String reason = response.getBody().getArray().getJSONObject(0).getJSONObject(String.valueOf(i)).getString("reason");
								getServer().dispatchCommand(getServer().getConsoleSender(), command + " " + toMute + " " + reason);
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.runTaskTimer(this, 0L, 2);
	}

	@Override
	public void onDisable() {
		for (Player p : Bukkit.getServer().getOnlinePlayers()) {
			p.kickPlayer(ChatColor.RED + "Server stopped");
		}
		getLogger().info("SMP disabled!");
	}
}
