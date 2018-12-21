package ch.atdit.smp.Commands;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import ch.atdit.smp.Main;

public class Prefix implements CommandExecutor {
	
	FileConfiguration config;
	File cfile;
	
	public boolean onCommand(CommandSender sender, Command cmnd, String arg2, String[] args) {

		String cmd = cmnd.getName();

		if (cmd.equalsIgnoreCase("prefix")) {
			Player pS = (Player) sender;

			if (!pS.isOp()) {
				pS.sendMessage(
						ChatColor.translateAlternateColorCodes('&', "&cYou are not allowed to manage prefixes."));
				return true;
			}

			if (args.length < 2) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cSyntax Error. Correct Usage: /prefix <set|del> <Player> [Value]"));
				return true;
			} else if (args.length == 2) {
				if (args[0].equalsIgnoreCase("set")) {
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cSyntax Error. Correct Usage: /prefix <set|del> <Player> [Value]"));
					return true;
				} else if (args[0].equalsIgnoreCase("del")) {
					@SuppressWarnings("deprecation")
					OfflinePlayer p = Bukkit.getOfflinePlayer(args[1]);
					if (p.hasPlayedBefore() || p.isOnline()) {
						String uuid = p.getUniqueId().toString();
						Main.instance().getConfig().set("prefix." + uuid, null);
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aDeleted &2" + args[1] + "&a's prefix!"));
						Main.instance().saveDefaultConfig();
						return true;
					} else {
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&cPlayer not found."));
						return true;
					}
				} else {
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cSyntax Error. Correct Usage: /prefix <set|del> <Player> [Value]"));
					return true;
				}
			} else {
				if (args[0].equalsIgnoreCase("set")) {
					OfflinePlayer p = Bukkit.getOfflinePlayer(args[1]);
					if (p.hasPlayedBefore() || p.isOnline()) {
						String uuid = p.getUniqueId().toString();
						String prefix = "";
						
						for (int i = 2; i < args.length; i++) {
							if (i == 2) {
								prefix += args[i];
							} else {
								prefix += " " + args[i];
							}
						}
						
						Main.instance().getConfig().set("prefix." + uuid, prefix);
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aSet &2" + args[1] + "&a's prefix to &2" + prefix + "&a!"));
						Main.instance().saveDefaultConfig();
						return true;
					} else {
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cPlayer not found."));
						return true;
					}
				} else if (args[0].equalsIgnoreCase("del")) {
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cSyntax Error. Correct Usage: /prefix <set|del> <Player> [Value]"));
					return true;
				} else {
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cSyntax Error. Correct Usage: /prefix <set|del> <Player> [Value]"));
					return true;
				}
			}
		}
		return true;
	}
}
