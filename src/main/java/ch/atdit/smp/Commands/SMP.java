package ch.atdit.smp.Commands;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import ch.atdit.smp.Main;

public class SMP implements CommandExecutor {

	private Methods methods = new Methods();
	
	public boolean onCommand(CommandSender sender, Command cmnd, String arg2, String[] args) {

		String cmd = cmnd.getName();

		if (cmd.equalsIgnoreCase("smp")) {
			if (args.length > 0) {
				if (args[0].equalsIgnoreCase("restart")) {
					if (sender instanceof Player) {
						Player p = (Player) sender;
						if (p.isOp()) {
							String s;
					        Process pcs;
					        try {
					            pcs = Runtime.getRuntime().exec("sh /home/scrim/SMP/start.sh", null, new File("/home/scrim/SMP"));
					            BufferedReader br = new BufferedReader(
					                new InputStreamReader(pcs.getInputStream()));
					            while ((s = br.readLine()) != null)
					                System.out.println(s);
					            pcs.waitFor();
					            pcs.destroy();
					            Bukkit.getServer().shutdown();
					        } catch (Exception e) {
					        	e.printStackTrace();
					        }
						}
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou are not allowed to restart the server."));
						return true;
					} else {
						String s;
				        Process pcs;
				        try {
				            pcs = Runtime.getRuntime().exec("sh /home/scrim/SMP/start.sh", null, new File("/home/scrim/SMP"));
				            BufferedReader br = new BufferedReader(
				                new InputStreamReader(pcs.getInputStream()));
				            while ((s = br.readLine()) != null)
				                System.out.println(s);
				            pcs.waitFor();
				            pcs.destroy();
				            Bukkit.getServer().shutdown();
				        } catch (Exception e) {
				        	e.printStackTrace();
				        }
					}
				} else if (args[0].equalsIgnoreCase("info")) {
					Long startTime = Main.instance().getConfig().getLong("latestStart");
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aSMP version 4.1"));
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aRunning since " + methods.onlineTime(0L, startTime)));
					sender.sendMessage("");
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aMade by &2AtditC"));
					return true;
				} else {
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cSyntax Error. Correct Usage: /smp <restart|info>"));
					return true;
				}
			} else {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cSyntax Error. Correct Usage: /smp <restart|info>"));
				return true;
			}
		}
		return true;
	}
}
