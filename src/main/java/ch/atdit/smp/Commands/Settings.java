package ch.atdit.smp.Commands;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import ch.atdit.smp.Main;

public class Settings implements CommandExecutor {
	
	FileConfiguration config;
	File cfile;
	
	public boolean onCommand(CommandSender sender, Command cmnd, String arg2, String[] args) {

		String cmd = cmnd.getName();

		if (cmd.equalsIgnoreCase("enable") || cmd.equalsIgnoreCase("disable")) {
			Player p = (Player) sender;
			if (p.isOp()) {
				if (args.length == 0 || args.length > 1) {
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cInvalid Syntax. Correct Usage: /" + cmd.toLowerCase() + " <setting>"));
					return true;
				} else {
					if (args[0].equalsIgnoreCase("chests")) {
						if (cmd.equalsIgnoreCase("enable")) {
							String s;
							Process pcs;
							try {
					            pcs = Runtime.getRuntime().exec("mv /home/scrim/SMP/plugins/Lockette.jar.disable /home/scrim/SMP/plugins/Lockette.jar", null, new File("/home/scrim/SMP/plugins"));
					            BufferedReader br = new BufferedReader(
					                new InputStreamReader(pcs.getInputStream()));
					            while ((s = br.readLine()) != null)
					                System.out.println(s);
					            pcs.waitFor();
					            pcs.destroy();
					            
					            if (pcs.exitValue() == 0) {
									sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aSetting " + args[0] + " enabled."));
								} else {
									sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cFailed to enable " + args[0] + ". Perhaps it already is enabled?"));
								}
					        } catch (Exception e) {
					        	e.printStackTrace();
					        }
							return true;
						} else if (cmd.equalsIgnoreCase("disable")) {
							String s;
							Process pcs;
							try {
					            pcs = Runtime.getRuntime().exec("mv /home/scrim/SMP/plugins/Lockette.jar /home/scrim/SMP/plugins/Lockette.jar.disable", null, new File("/home/scrim/SMP/plugins"));
					            BufferedReader br = new BufferedReader(
					                new InputStreamReader(pcs.getInputStream()));
					            while ((s = br.readLine()) != null)
					                System.out.println(s);
					            pcs.waitFor();
					            pcs.destroy();
					            
					            if (pcs.exitValue() == 0) {
									sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aSetting " + args[0] + " disabled."));
								} else {
									sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cFailed to disable " + args[0] + ". Perhaps it already is disabled?"));
								}
					        } catch (Exception e) {
					        	e.printStackTrace();
					        }
							return true;
						}
					} else {
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cSetting not found."));
						return true;
					}
				}
			} else {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou are not allowed to change server settings."));
				return true;
			}
		}
		return true;
	}
}
