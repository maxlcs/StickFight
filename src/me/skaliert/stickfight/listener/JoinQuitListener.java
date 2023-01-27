package me.skaliert.stickfight.listener;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.skaliert.stickfight.StickFight;
import me.skaliert.stickfight.events.PlayerLostEvent;
import me.skaliert.stickfight.events.PlayerWonEvent;
import me.skaliert.stickfight.gamestate.GameState;
import me.skaliert.stickfight.gamestate.GameStateManager;
import me.skaliert.stickfight.gamestate.IngameState;
import me.skaliert.stickfight.gamestate.LobbyState;
import me.skaliert.stickfight.utils.LocationManager;

public class JoinQuitListener implements Listener {

	private StickFight plugin;

	public JoinQuitListener(StickFight plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
		this.plugin = plugin;
	}

	@EventHandler
	public void handlePlayerJoin(PlayerJoinEvent event) {
		event.setJoinMessage(null);
		Player player = event.getPlayer();

		if (player.hasPotionEffect(PotionEffectType.INVISIBILITY))
			player.removePotionEffect(PotionEffectType.INVISIBILITY);

		GameStateManager gameStateManager = plugin.getGameStateManager();
		GameState gameState = gameStateManager.getCurrentGameState();

		if (gameState instanceof LobbyState) {
			plugin.getPlayers().add(player);

			player.setGameMode(GameMode.ADVENTURE);
			player.setHealth(20);
			player.setFoodLevel(20);
			player.getInventory().clear();
			player.setExp(0);
			player.setLevel(plugin.getConfigManager().getLobbyCountdown());

			LocationManager locationManager = new LocationManager();

			if (locationManager.getSpawn() != null)
				player.teleport(locationManager.getSpawn());

			Bukkit.broadcastMessage(plugin.getMessageManager().getJoined().replace("%player%", player.getName())
					.replace("%players%", "" + plugin.getPlayers().size()));

			LobbyState lobbyState = (LobbyState) gameState;

			if (plugin.getPlayers().size() >= LobbyState.MIN_PLAYERS && !lobbyState.getLobbyCountdown().isRunning())
				lobbyState.getLobbyCountdown().start();
		} else if (gameState instanceof IngameState) {
			player.setGameMode(GameMode.ADVENTURE);
			player.setGameMode(GameMode.ADVENTURE);
			player.setHealth(20);
			player.setFoodLevel(20);
			player.getInventory().clear();
			player.setExp(0);
			player.setLevel(0);
			player.setAllowFlight(true);
			player.setFlying(true);
			player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, false, false));
			player.setCollidable(false);

			LocationManager locationManager = new LocationManager();

			if (locationManager.getSpectator() != null)
				player.teleport(locationManager.getSpectator());
		}
	}

	@EventHandler
	public void handlePlayerQuit(PlayerQuitEvent event) {
		event.setQuitMessage(null);
		Player player = event.getPlayer();

		if (player.hasPotionEffect(PotionEffectType.INVISIBILITY))
			player.removePotionEffect(PotionEffectType.INVISIBILITY);

		GameStateManager gameStateManager = plugin.getGameStateManager();
		GameState gameState = gameStateManager.getCurrentGameState();

		if (gameState instanceof LobbyState) {
			plugin.getPlayers().remove(player);
			Bukkit.broadcastMessage(plugin.getMessageManager().getQuit().replace("%player%", player.getName())
					.replace("%players%", "" + plugin.getPlayers().size()));
		} else if (gameState instanceof IngameState) {
			if (plugin.getPlayers().contains(player)) {
				plugin.getPlayers().remove(player);

				Player other = plugin.getPlayers().get(0);
				LocationManager locationManager = new LocationManager();

				PlayerWonEvent won = new PlayerWonEvent(other);
				PlayerLostEvent lost = new PlayerLostEvent(player);

				Bukkit.getPluginManager().callEvent(won);
				Bukkit.getPluginManager().callEvent(lost);

				Bukkit.broadcastMessage(
						plugin.getMessageManager().getQuitPlaying().replace("%player%", player.getName()));

				for (Player all : Bukkit.getOnlinePlayers()) {
					all.sendTitle(plugin.getMessageManager().getWinTitle().replace("%player%", other.getName()), "", 5,
							25, 5);
					all.teleport(locationManager.getSpawn());
					all.setFallDistance(0);
				}

				if (plugin.getConfigManager().playSounds()) {
					for (Player all : Bukkit.getOnlinePlayers())
						all.playSound(all.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 1, 1);
				}

				if (gameStateManager.getPlugin().getConfigManager().playSounds()) {
					for (Player all : Bukkit.getOnlinePlayers())
						all.playSound(all.getLocation(), Sound.ENTITY_FIREWORK_LARGE_BLAST, 1, 1);
				}

				Bukkit.broadcastMessage(
						plugin.getMessageManager().getWinBroadcast().replace("%player%", other.getName()));
				plugin.getGameStateManager().setGameState(GameState.ENDING_STATE);
			}
		}
	}
}