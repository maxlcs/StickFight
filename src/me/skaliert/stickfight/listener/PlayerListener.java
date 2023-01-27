package me.skaliert.stickfight.listener;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import me.skaliert.stickfight.StickFight;
import me.skaliert.stickfight.events.PlayerLostEvent;
import me.skaliert.stickfight.events.PlayerWonEvent;
import me.skaliert.stickfight.gamestate.EndingState;
import me.skaliert.stickfight.gamestate.GameState;
import me.skaliert.stickfight.gamestate.IngameState;
import me.skaliert.stickfight.gamestate.LobbyState;
import me.skaliert.stickfight.utils.LocationManager;

@SuppressWarnings("deprecation")
public class PlayerListener implements Listener {

	private StickFight plugin;

	public PlayerListener(StickFight plugin) {
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void handleFoodLevelChange(FoodLevelChangeEvent event) {
		if (event.getEntityType() == EntityType.PLAYER)
			event.setCancelled(true);
	}

	@EventHandler
	public void handlePlayerDropItem(PlayerDropItemEvent event) {
		event.setCancelled(true);
	}

	@EventHandler
	public void handlePlayerPickupItem(PlayerPickupItemEvent event) {
		event.setCancelled(true);
	}

	@EventHandler
	public void handleInventoryClick(InventoryClickEvent event) {
		event.setCancelled(true);
	}

	@EventHandler
	public void handleBlockPlace(BlockPlaceEvent event) {
		event.setCancelled(true);
	}

	@EventHandler
	public void handleBlockBreak(BlockBreakEvent event) {
		event.setCancelled(true);
	}

	@EventHandler
	public void handleEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if (plugin.getGameStateManager().getCurrentGameState() instanceof IngameState) {
			if (event.getEntity() instanceof Player) {
				Player player = (Player) event.getEntity();
				Player damager = (Player) event.getDamager();
				if (plugin.getPlayers().contains(player) && plugin.getPlayers().contains(damager)) {
					event.setDamage(0);
				}
			}
		} else
			event.setCancelled(true);
	}

	@EventHandler
	public void handleEntityDamage(EntityDamageEvent event) {
		if (event.getCause() != DamageCause.ENTITY_ATTACK)
			event.setCancelled(true);
	}

	@EventHandler
	public void handlePlayerLogin(PlayerLoginEvent event) {
		if (plugin.getGameStateManager().getCurrentGameState() instanceof LobbyState) {
			if (plugin.getPlayers().size() >= LobbyState.MAX_PLAYERS)
				event.disallow(Result.KICK_FULL, plugin.getMessageManager().getServerFull());
			else
				event.allow();
		} else if (plugin.getGameStateManager().getCurrentGameState() instanceof EndingState) {
			event.disallow(Result.KICK_OTHER, plugin.getMessageManager().getServerStopping());
		} else
			event.allow();
	}

	@EventHandler
	public void handlePlayerMove(PlayerMoveEvent event) {
		if (plugin.getGameStateManager().getCurrentGameState() instanceof IngameState) {
			Player player = event.getPlayer();
			if (plugin.getPlayers().contains(player)) {
				ArrayList<Player> players = plugin.getPlayers();
				LocationManager locationManager = new LocationManager();
				Location location = event.getTo();
				Location pos1 = locationManager.getPos1();

				if (plugin.getFreeze().contains(player)) {
					Location from = event.getFrom();
					Location to = event.getTo();
					double x = Math.floor(from.getX());
					double z = Math.floor(from.getZ());
					if (Math.floor(to.getX()) != x || Math.floor(to.getZ()) != z) {
						x += .5;
						z += .5;
						player.getPlayer().teleport(
								new Location(from.getWorld(), x, from.getY(), z, from.getYaw(), from.getPitch()));
					}
				}

				if (location.getY() <= (pos1.getY() - 5)) {
					Player other = null;
					for (Player all : players) {
						if (!all.getName().equals(player.getName()))
							other = all;
					}

					int points = plugin.getPoints().get(other) + 1;
					plugin.getPoints().put(other, points);

					updateScoreboard();

					if (points >= plugin.getConfigManager().getPointsToWin()) {
						PlayerWonEvent won = new PlayerWonEvent(other);
						PlayerLostEvent lost = new PlayerLostEvent(player);

						Bukkit.getPluginManager().callEvent(won);
						Bukkit.getPluginManager().callEvent(lost);

						for (Player all : Bukkit.getOnlinePlayers()) {
							all.sendTitle(plugin.getMessageManager().getWinTitle().replace("%player%", other.getName()),
									"", 5, 25, 5);
							all.teleport(locationManager.getSpawn());
							all.setFallDistance(0);
						}

						if (plugin.getConfigManager().playSounds()) {
							for (Player all : Bukkit.getOnlinePlayers())
								all.playSound(all.getLocation(), Sound.ENTITY_FIREWORK_LARGE_BLAST, 1, 1);
						}

						Bukkit.broadcastMessage(
								plugin.getMessageManager().getWinBroadcast().replace("%player%", other.getName()));
						plugin.getGameStateManager().setGameState(GameState.ENDING_STATE);
						return;
					}

					if (locationManager.getPos1() != null && locationManager.getPos2() != null) {
						players.get(0).teleport(locationManager.getPos1());
						players.get(0).setFallDistance(0);
						players.get(1).teleport(locationManager.getPos2());
						players.get(1).setFallDistance(0);
					}

					Bukkit.broadcastMessage(
							plugin.getMessageManager().getFellDown().replace("%player%", player.getName()));

					if (plugin.getConfigManager().playSounds())
						other.playSound(other.getLocation(), Sound.BLOCK_NOTE_PLING, 1, 1);
				}
			}
		}
	}

	private void updateScoreboard() {
		Player player1 = plugin.getPlayers().get(0);
		Player player2 = plugin.getPlayers().get(1);

		int points1 = plugin.getPoints().get(player1);
		int points2 = plugin.getPoints().get(player2);

		for (Player all : plugin.getPlayers()) {
			Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
			Objective objective = scoreboard.registerNewObjective("points", "dummy");

			objective.setDisplaySlot(DisplaySlot.SIDEBAR);
			objective.setDisplayName(plugin.getMessageManager().getScoreboardtitle());

			objective.getScore(player1.getName()).setScore(points1);
			objective.getScore(player2.getName()).setScore(points2);

			if (plugin.getConfigManager().showScoreboard())
				all.setScoreboard(scoreboard);
		}
	}
}