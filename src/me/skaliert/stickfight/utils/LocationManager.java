package me.skaliert.stickfight.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class LocationManager {

	private FileBuilder fileBuilder;

	public LocationManager() {
		fileBuilder = new FileBuilder("plugins//StickFight", "locations.yml");
	}

	public void setSpawn(Location location) {
		fileBuilder.setValue("spawn.world", location.getWorld().getName());
		fileBuilder.setValue("spawn.x", location.getX());
		fileBuilder.setValue("spawn.y", location.getY());
		fileBuilder.setValue("spawn.z", location.getZ());
		fileBuilder.setValue("spawn.yaw", location.getYaw());
		fileBuilder.setValue("spawn.pitch", location.getPitch());
		fileBuilder.save();
	}

	public void setPos1(Location location) {
		fileBuilder.setValue("pos1.world", location.getWorld().getName());
		fileBuilder.setValue("pos1.x", location.getX());
		fileBuilder.setValue("pos1.y", location.getY());
		fileBuilder.setValue("pos1.z", location.getZ());
		fileBuilder.setValue("pos1.yaw", location.getYaw());
		fileBuilder.setValue("pos1.pitch", location.getPitch());
		fileBuilder.save();
	}

	public void setPos2(Location location) {
		fileBuilder.setValue("pos2.world", location.getWorld().getName());
		fileBuilder.setValue("pos2.x", location.getX());
		fileBuilder.setValue("pos2.y", location.getY());
		fileBuilder.setValue("pos2.z", location.getZ());
		fileBuilder.setValue("pos2.yaw", location.getYaw());
		fileBuilder.setValue("pos2.pitch", location.getPitch());
		fileBuilder.save();
	}

	public void setSpectator(Location location) {
		fileBuilder.setValue("spectator.world", location.getWorld().getName());
		fileBuilder.setValue("spectator.x", location.getX());
		fileBuilder.setValue("spectator.y", location.getY());
		fileBuilder.setValue("spectator.z", location.getZ());
		fileBuilder.setValue("spectator.yaw", location.getYaw());
		fileBuilder.setValue("spectator.pitch", location.getPitch());
		fileBuilder.save();
	}

	public Location getSpawn() {
		World world = Bukkit.getWorld(fileBuilder.getString("spawn.world"));
		double x = fileBuilder.getDouble("spawn.x");
		double y = fileBuilder.getDouble("spawn.y");
		double z = fileBuilder.getDouble("spawn.z");
		float yaw = (float) fileBuilder.getDouble("spawn.yaw");
		float pitch = (float) fileBuilder.getDouble("spawn.pitch");
		return new Location(world, x, y, z, yaw, pitch);
	}

	public Location getPos1() {
		World world = Bukkit.getWorld(fileBuilder.getString("pos1.world"));
		double x = fileBuilder.getDouble("pos1.x");
		double y = fileBuilder.getDouble("pos1.y");
		double z = fileBuilder.getDouble("pos1.z");
		float yaw = (float) fileBuilder.getDouble("pos1.yaw");
		float pitch = (float) fileBuilder.getDouble("pos1.pitch");
		return new Location(world, x, y, z, yaw, pitch);
	}

	public Location getPos2() {
		World world = Bukkit.getWorld(fileBuilder.getString("pos2.world"));
		double x = fileBuilder.getDouble("pos2.x");
		double y = fileBuilder.getDouble("pos2.y");
		double z = fileBuilder.getDouble("pos2.z");
		float yaw = (float) fileBuilder.getDouble("pos2.yaw");
		float pitch = (float) fileBuilder.getDouble("pos2.pitch");
		return new Location(world, x, y, z, yaw, pitch);
	}

	public Location getSpectator() {
		World world = Bukkit.getWorld(fileBuilder.getString("spectator.world"));
		double x = fileBuilder.getDouble("spectator.x");
		double y = fileBuilder.getDouble("spectator.y");
		double z = fileBuilder.getDouble("spectator.z");
		float yaw = (float) fileBuilder.getDouble("spectator.yaw");
		float pitch = (float) fileBuilder.getDouble("spectator.pitch");
		return new Location(world, x, y, z, yaw, pitch);
	}
}