package ch.atdit.smp.Commands;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import ch.atdit.smp.Main;

public class Stats implements CommandExecutor {
	
	FileConfiguration config;
	File cfile;

	private Methods methods = new Methods();
	
	public boolean onCommand(CommandSender sender, Command cmnd, String arg2, String[] args) {

		String cmd = cmnd.getName();

		if (cmd.equalsIgnoreCase("stats")) {
			if (args.length == 0) {
				Player p = (Player) sender;
				String uuid = p.getUniqueId().toString();
				Double kills = Main.instance().getConfig().getDouble("kills." + uuid);
				Double deaths = Main.instance().getConfig().getDouble("deaths." + uuid);
				Double alldeaths = Main.instance().getConfig().getDouble("alldeaths" + uuid);
				String kdr = methods.getKD(kills, deaths);
				String allkdr = methods.getKD(kills, alldeaths);
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&1&m------------------------------"));
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3   Viewing stats for: &b" + p.getName()));
				sender.sendMessage("");
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3   Level: &b" + methods.getLevel(Main.instance().getConfig().getLong("onlineTime." + uuid) + (System.currentTimeMillis() - Main.instance().getConfig().getLong("latestJoin." + uuid)))));
				sender.sendMessage("");
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3   Total playtime: &b" + methods.onlineTime(Main.instance().getConfig().getLong("onlineTime." + uuid), Main.instance().getConfig().getLong("latestJoin." + uuid))));
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3   First joined: &b" + methods.convertToDate(Main.instance().getConfig().getLong("firstJoin." + uuid))));
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3   Online since: &b" + methods.onlineTime(0L, Main.instance().getConfig().getLong("latestJoin." + uuid))));
				sender.sendMessage("");
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3   PvP Kills: &b" + Main.instance().getConfig().getInt("kills." + uuid)));
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3   PvP Deaths: &b" + Main.instance().getConfig().getInt("deaths." + uuid)));
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3   All Deaths: &b" + Main.instance().getConfig().getInt("alldeaths." + uuid)));
				sender.sendMessage("");
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3   PvP K/D: &b" + kdr));
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3   K/D: &b" + allkdr));
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&1&m------------------------------"));
				return true;
			} else if (args.length == 1) {
				@SuppressWarnings("deprecation")
				OfflinePlayer p = Bukkit.getOfflinePlayer(args[0]);
				if (p.hasPlayedBefore() && p.isOnline()) { // Played before and online
					String uuid = p.getUniqueId().toString();
					Double kills = Main.instance().getConfig().getDouble("kills." + uuid);
					Double deaths = Main.instance().getConfig().getDouble("deaths." + uuid);
					Double alldeaths = Main.instance().getConfig().getDouble("alldeaths" + uuid);
					String kdr = methods.getKD(kills, deaths);
					String allkdr = methods.getKD(kills, alldeaths);
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&1&m------------------------------"));
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3   Viewing stats for: &b" + p.getName()));
					sender.sendMessage("");
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3   Level: &b" + methods.getLevel(Main.instance().getConfig().getLong("onlineTime." + uuid) + (System.currentTimeMillis() - Main.instance().getConfig().getLong("latestJoin." + uuid)))));
					sender.sendMessage("");
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3   Total playtime: &b" + methods.onlineTime(Main.instance().getConfig().getLong("onlineTime." + uuid), Main.instance().getConfig().getLong("latestJoin." + uuid))));
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3   First joined: &b" + methods.convertToDate(Main.instance().getConfig().getLong("firstJoin." + uuid))));
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3   Online since: &b" + methods.onlineTime(0L, Main.instance().getConfig().getLong("latestJoin." + uuid))));
					sender.sendMessage("");
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3   PvP Kills: &b" + Main.instance().getConfig().getInt("kills." + uuid)));
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3   PvP Deaths: &b" + Main.instance().getConfig().getInt("deaths." + uuid)));
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3   All Deaths: &b" + Main.instance().getConfig().getInt("alldeaths." + uuid)));
					sender.sendMessage("");
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3   PvP K/D: &b" + kdr));
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3   K/D: &b" + allkdr));
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&1&m------------------------------"));
					return true;
				} else if (p.hasPlayedBefore() && !p.isOnline()) { // Played before but offline
					String uuid = p.getUniqueId().toString();
					Double kills = Main.instance().getConfig().getDouble("kills." + uuid);
					Double deaths = Main.instance().getConfig().getDouble("deaths." + uuid);
					Double alldeaths = Main.instance().getConfig().getDouble("alldeaths" + uuid);
					String kdr = methods.getKD(kills, deaths);
					String allkdr = methods.getKD(kills, alldeaths);
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&1&m------------------------------"));
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3   Viewing stats for: &b" + p.getName()));
					sender.sendMessage("");
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3   Level: &b" + methods.getLevel(Main.instance().getConfig().getLong("onlineTime." + uuid) + (System.currentTimeMillis() - Main.instance().getConfig().getLong("latestJoin." + uuid)))));
					sender.sendMessage("");
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3   Total playtime: &b" + methods.onlineTime(Main.instance().getConfig().getLong("onlineTime." + uuid), Main.instance().getConfig().getLong("latestJoin." + uuid))));
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3   First joined: &b" + methods.convertToDate(Main.instance().getConfig().getLong("firstJoin." + uuid))));
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3   Offline since: &b" + methods.onlineTime(0L, Main.instance().getConfig().getLong("latestQuit." + uuid))));
					sender.sendMessage("");
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3   PvP Kills: &b" + Main.instance().getConfig().getInt("kills." + uuid)));
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3   PvP Deaths: &b" + Main.instance().getConfig().getInt("deaths." + uuid)));
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3   All Deaths: &b" + Main.instance().getConfig().getInt("alldeaths." + uuid)));
					sender.sendMessage("");
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3   PvP K/D: &b" + kdr));
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3   K/D: &b" + allkdr));
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&1&m------------------------------"));
					return true;
				} else if (!p.hasPlayedBefore() && p.isOnline()) { // First join and online
					String uuid = p.getUniqueId().toString();
					Double kills = Main.instance().getConfig().getDouble("kills." + uuid);
					Double deaths = Main.instance().getConfig().getDouble("deaths." + uuid);
					Double alldeaths = Main.instance().getConfig().getDouble("alldeaths" + uuid);
					String kdr = methods.getKD(kills, deaths);
					String allkdr = methods.getKD(kills, alldeaths);
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&1&m------------------------------"));
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3   Viewing stats for: &b" + p.getName()));
					sender.sendMessage("");
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3   Level: &b" + methods.getLevel(Main.instance().getConfig().getLong("onlineTime." + uuid) + (System.currentTimeMillis() - Main.instance().getConfig().getLong("latestJoin." + uuid)))));
					sender.sendMessage("");
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3   Total playtime: &b" + methods.onlineTime(Main.instance().getConfig().getLong("onlineTime." + uuid), Main.instance().getConfig().getLong("latestJoin." + uuid))));
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3   First joined: &b" + methods.convertToDate(Main.instance().getConfig().getLong("firstJoin." + uuid))));
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3   Online since: &b" + methods.onlineTime(0L, Main.instance().getConfig().getLong("latestJoin." + uuid))));
					sender.sendMessage("");
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3   PvP Kills: &b" + Main.instance().getConfig().getInt("kills." + uuid)));
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3   PvP Deaths: &b" + Main.instance().getConfig().getInt("deaths." + uuid)));
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3   All Deaths: &b" + Main.instance().getConfig().getInt("alldeaths." + uuid)));
					sender.sendMessage("");
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3   PvP K/D: &b" + kdr));
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3   K/D: &b" + allkdr));
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&1&m------------------------------"));
					return true;
				} else {
					sender.sendMessage(ChatColor.RED + "Player not found");
					return true;
				}
			}
		}
		return true;
	}
}
