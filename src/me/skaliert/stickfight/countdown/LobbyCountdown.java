package me.skaliert.stickfight.countdown;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import me.skaliert.stickfight.gamestate.GameState;
import me.skaliert.stickfight.gamestate.GameStateManager;
import me.skaliert.stickfight.gamestate.LobbyState;

public class LobbyCountdown extends Countdown {

	private GameStateManager gameStateManager;

	private int seconds;
	private boolean isRunning;

	private int idleId;
	private boolean isIdling;

	public LobbyCountdown(GameStateManager gameStateManager) {
		this.gameStateManager = gameStateManager;
		seconds = gameStateManager.getPlugin().getConfigManager().getLobbyCountdown();
	}

	@Override
	public void start() {
		isRunning = true;
		stopIdle();

		if (gameStateManager.getPlugin().getConfigManager().playSounds()) {
			for (Player all : Bukkit.getOnlinePlayers())
				all.playSound(all.getLocation(), Sound.BLOCK_NOTE_PLING, 1, 1);
		}

		taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(gameStateManager.getPlugin(), new Runnable() {

			@Override
			public void run() {

				for (Player player : gameStateManager.getPlugin().getPlayers()) {
					player.setLevel(seconds);
				}

				switch (seconds) {
				case 90:
				case 80:
				case 70:
				case 60:
				case 50:
				case 40:
				case 30:
				case 20:
				case 10:
				case 5:
				case 4:
				case 3:
				case 2:
					Bukkit.broadcastMessage(gameStateManager.getPlugin().getMessageManager().getLobbyCountdown()
							.replace("%seconds%", "" + seconds));
					if (gameStateManager.getPlugin().getConfigManager().playSounds()) {
						for (Player all : Bukkit.getOnlinePlayers())
							all.playSound(all.getLocation(), Sound.BLOCK_LEVER_CLICK, 1, 1);
					}
					break;
				case 1:
					Bukkit.broadcastMessage(gameStateManager.getPlugin().getMessageManager().getLobbyCountdownOne()
							.replace("%seconds%", "" + seconds));
					if (gameStateManager.getPlugin().getConfigManager().playSounds()) {
						for (Player all : Bukkit.getOnlinePlayers())
							all.playSound(all.getLocation(), Sound.BLOCK_LEVER_CLICK, 1, 1);
					}
					break;
				case 0:
					stop();
					if (!(gameStateManager.getPlugin().getPlayers().size() >= LobbyState.MIN_PLAYERS)) {
						startIdle();
						Bukkit.broadcastMessage(
								gameStateManager.getPlugin().getMessageManager().getLobbyCountdownReset());
						if (gameStateManager.getPlugin().getConfigManager().playSounds()) {
							for (Player all : Bukkit.getOnlinePlayers())
								all.playSound(all.getLocation(), Sound.BLOCK_ANVIL_BREAK, 1, 1);
						}

						for (Player player : gameStateManager.getPlugin().getPlayers()) {
							player.setLevel(seconds);
						}
						return;
					}
					gameStateManager.setGameState(GameState.INGAME_STATE);
					break;
				default:
					break;
				}
				seconds--;
			}
		}, 0, 20);
	}

	@Override
	public void stop() {
		if (isRunning) {
			Bukkit.getScheduler().cancelTask(taskId);
			isRunning = false;
			seconds = gameStateManager.getPlugin().getConfigManager().getLobbyCountdown();
		}
	}

	public void startIdle() {
		isIdling = true;
		idleId = Bukkit.getScheduler().scheduleSyncRepeatingTask(gameStateManager.getPlugin(), new Runnable() {

			@Override
			public void run() {
				if (isRunning)
					stop();
				int neededPlayers = LobbyState.MIN_PLAYERS - gameStateManager.getPlugin().getPlayers().size();
				if (neededPlayers == 1) {
					Bukkit.broadcastMessage(gameStateManager.getPlugin().getMessageManager().getIdleOne()
							.replace("%missing%", "" + neededPlayers));
				} else
					Bukkit.broadcastMessage(gameStateManager.getPlugin().getMessageManager().getIdle()
							.replace("%missing%", "" + neededPlayers));
			}
		}, 0, 20 * gameStateManager.getPlugin().getConfigManager().getIdlePeriod());
	}

	public void stopIdle() {
		if (isIdling) {
			Bukkit.getScheduler().cancelTask(idleId);
			isIdling = false;
		}
	}

	public int getSeconds() {
		return seconds;
	}

	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}

	public boolean isRunning() {
		return isRunning;
	}
}