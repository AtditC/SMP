package ch.atdit.smp.Commands;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import ch.atdit.smp.Main;

public class Debug implements CommandExecutor {

	FileConfiguration config;
	File cfile;
	
	public boolean onCommand(CommandSender sender, Command cmnd, String arg2, String[] args) {

		String cmd = cmnd.getName();

		if (cmd.equalsIgnoreCase("uuid")) {
			if (args.length == 0) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cSyntax Error. Correct Usage: /uuid <name>"));
				return true;
			} else {
				try {
					@SuppressWarnings("deprecation")
					OfflinePlayer p = Bukkit.getOfflinePlayer(args[0]);
					String uuid = p.getUniqueId().toString();
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2" + args[0] + "&a's UUID is: &2" + uuid));
					return true;
				} catch (Exception e) {
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cPlayer not found"));
					return true;
				}
			}
		} else if (cmd.equalsIgnoreCase("name")) {
			if (args.length == 0) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cSyntax Error. Correct Usage: /name <uuid>"));
				return true;
			} else {
				try {
					@SuppressWarnings("deprecation")
					OfflinePlayer p = Bukkit.getOfflinePlayer(args[0]);
					String name = p.getName();
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2" + args[0] + "&a's name is: &2" + name));
					return true;
				} catch (Exception e) {
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cPlayer not found"));
					return true;
				}
			}
		}
		return true;
	}	
}
