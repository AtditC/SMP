package ch.atdit.smp.Commands;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import ch.atdit.smp.Main;

public class Buy implements CommandExecutor {
	
	FileConfiguration config;
	File cfile;
	
	public boolean onCommand(CommandSender sender, Command cmnd, String arg2, String[] args) {

		String cmd = cmnd.getName();

		if (cmd.equalsIgnoreCase("buy")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED + "Only players are allowed to use this command.");
			} else {
				if (args.length == 0) {
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aThings to buy: tpa (10k), tp (100k) Coming soon: home (100k), warp (500k)"));
					return true;
				} else if (args.length == 1) {
					return true;
				}
			}
		}
		return true;
	}
}
