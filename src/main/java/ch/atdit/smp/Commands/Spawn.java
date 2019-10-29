package ch.atdit.smp.Commands;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import ch.atdit.smp.Main;

public class Spawn implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command cmnd, String arg2, String[] args) {

		String cmd = cmnd.getName();

		if (cmd.equalsIgnoreCase("spawn")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("Only players can execute this command.");
				return true;
			}
			
			Player p = (Player) sender;
			
			int x = Main.instance().getConfig().getInt("spawn.x");
			int y = Main.instance().getConfig().getInt("spawn.y");
			int z = Main.instance().getConfig().getInt("spawn.z");
			float v = (float) Main.instance().getConfig().getDouble("spawn.v");
			float u = (float) Main.instance().getConfig().getDouble("spawn.u");
			String worldname = Main.instance().getConfig().getString("spawn.world");
			
			if (x == y && y == z && x == 0 || worldname == null) {
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cInternal server error: Invalid input."));
				return true;
			}
			
			if (Main.instance().getServer().getWorld(worldname) == null) {
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cInternal server error: Invalid world."));
				return true;
			}
			
			World w = Main.instance().getServer().getWorld(worldname);
			Location l = new Location(w, x, y, z, v, u);
			p.teleport(l.add(0.5, 0, 0.5));
			return true;
		} else if (cmd.equalsIgnoreCase("setspawn")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("Only players can execute this command.");
				return true;
			}
			
			Player p = (Player) sender;
			
			if (!p.isOp()) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou don't have permissions."));
				return true;
			}
			
			if (args.length < 2) {
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cSyntax Error. Correct Usage: /setspawn <yaw> <pitch>"));
				return true;
			}
			
			Main.instance().getConfig().set("spawn.x", p.getLocation().getBlockX());
			Main.instance().getConfig().set("spawn.y", p.getLocation().getBlockY());
			Main.instance().getConfig().set("spawn.z", p.getLocation().getBlockZ());
			Main.instance().getConfig().set("spawn.world", p.getWorld().getName());
			Main.instance().getConfig().set("spawn.v", args[0]);
			Main.instance().getConfig().set("spawn.u", args[1]);
			Main.instance().saveConfig();
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aSpawn set to &2" + p.getLocation().getBlockX() + "x, " + p.getLocation().getBlockY() + "y, " + p.getLocation().getBlockZ() + "z &a!"));
			return true;			
		}
		return true;
	}
}
