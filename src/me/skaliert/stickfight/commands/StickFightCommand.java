package me.skaliert.stickfight.commands;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.skaliert.stickfight.StickFight;
import me.skaliert.stickfight.utils.LocationManager;

public class StickFightCommand implements CommandExecutor {

	private StickFight plugin;

	public StickFightCommand(StickFight plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String arg2, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (player.hasPermission("stickfight.stickfight")) {
				if (args.length == 2 && args[0].equalsIgnoreCase("setpos")) {
					if (args[1].equals("1")) {
						Location location = player.getLocation();
						new LocationManager().setPos1(location);
						player.sendMessage(plugin.getMessageManager().getPos1());
					} else if (args[1].equals("2")) {
						Location location = player.getLocation();
						new LocationManager().setPos2(location);
						player.sendMessage(plugin.getMessageManager().getPos2());
					} else {
						List<String> usage = plugin.getMessageManager().getCommandUsage();
						for (String line : usage)
							sender.sendMessage(line);
					}
				} else if (args.length == 1 && args[0].equalsIgnoreCase("setspawn")) {
					Location location = player.getLocation();
					new LocationManager().setSpawn(location);
					player.sendMessage(plugin.getMessageManager().getSpawn());
				} else if (args.length == 1 && args[0].equalsIgnoreCase("setspectator")) {
					Location location = player.getLocation();
					new LocationManager().setSpectator(location);
					player.sendMessage(plugin.getMessageManager().getSpec());
				} else {
					List<String> usage = plugin.getMessageManager().getCommandUsage();
					for (String line : usage)
						sender.sendMessage(line);
				}
			} else
				player.sendMessage(plugin.getMessageManager().getNoPermissions());
		} else
			sender.sendMessage(plugin.getMessageManager().getMustBePlayer());
		return false;
	}
}