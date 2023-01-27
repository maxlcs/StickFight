package me.skaliert.stickfight.gamestate;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import me.skaliert.stickfight.StickFight;
import me.skaliert.stickfight.countdown.PrepareCountdown;
import me.skaliert.stickfight.utils.ItemBuilder;
import me.skaliert.stickfight.utils.LocationManager;

public class IngameState extends GameState {

	private StickFight plugin;

	private PrepareCountdown prepareCountdown;

	public IngameState(StickFight plugin) {
		this.plugin = plugin;
		prepareCountdown = new PrepareCountdown(plugin);
	}

	@Override
	public void start() {
		List<Player> players = plugin.getPlayers();
		Collections.shuffle(players);

		LocationManager locationManager = new LocationManager();

		if (locationManager.getPos1() != null && locationManager.getPos2() != null) {
			players.get(0).teleport(locationManager.getPos1());
			players.get(1).teleport(locationManager.getPos2());
		}

		if (plugin.getConfigManager().playSounds()) {
			for (Player all : Bukkit.getOnlinePlayers())
				all.playSound(all.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 1, 1);
		}

		for (Player player : plugin.getPlayers()) {
			player.setGameMode(GameMode.SURVIVAL);
			player.getInventory().clear();
			player.getInventory().setItem(0,
					new ItemBuilder(Material.STICK).addEnchantment(Enchantment.KNOCKBACK, 1, false).build());
			player.getInventory().setHeldItemSlot(0);
			plugin.getPoints().put(player, 0);
			plugin.getFreeze().add(player);

			Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
			Objective objective = scoreboard.registerNewObjective("points", "dummy");

			objective.setDisplaySlot(DisplaySlot.SIDEBAR);
			objective.setDisplayName(plugin.getMessageManager().getScoreboardtitle());

			objective.getScore(plugin.getPlayers().get(0).getName()).setScore(0);
			objective.getScore(plugin.getPlayers().get(1).getName()).setScore(0);

			if (plugin.getConfigManager().showScoreboard())
				player.setScoreboard(scoreboard);
		}

		prepareCountdown.start();
	}

	public String getFormattedTime(int seconds) {
		// long hours = (int) TimeUnit.SECONDS.toHours(seconds) % 60;
		long mins = (int) TimeUnit.SECONDS.toMinutes(seconds) % 60;
		long secs = (int) TimeUnit.SECONDS.toSeconds(seconds) % 60;

		return String.format("%02d:%02d", mins, secs);
		// return String.format("%02d:%02d:%02d", hours, mins, secs);
	}

	@Override
	public void stop() {

	}

	public PrepareCountdown getPrepareCountdown() {
		return prepareCountdown;
	}
}