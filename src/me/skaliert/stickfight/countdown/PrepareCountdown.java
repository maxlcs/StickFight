package me.skaliert.stickfight.countdown;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import me.skaliert.stickfight.StickFight;
import me.skaliert.stickfight.gamestate.EndingState;
import me.skaliert.stickfight.gamestate.LobbyState;

public class PrepareCountdown extends Countdown {

	private StickFight plugin;

	private int seconds;
	private boolean isRunning;

	public PrepareCountdown(StickFight plugin) {
		this.plugin = plugin;
		seconds = plugin.getConfigManager().getPrepareCountdown();
		isRunning = false;
	}

	@Override
	public void start() {
		isRunning = true;
		taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {

			@Override
			public void run() {
				if (plugin.getPlayers().size() < LobbyState.MIN_PLAYERS
						|| plugin.getGameStateManager().getCurrentGameState() instanceof EndingState) {
					stop();
					return;
				}

				switch (seconds) {
				case 30:
				case 20:
				case 10:
				case 5:
				case 4:
				case 3:
				case 2:
					if (plugin.getConfigManager().playSounds()) {
						for (Player all : Bukkit.getOnlinePlayers())
							all.playSound(all.getLocation(), Sound.BLOCK_LEVER_CLICK, 1, 1);
					}
					Bukkit.broadcastMessage(
							plugin.getMessageManager().getPrepareCountdown().replace("%seconds%", "" + seconds));
					break;
				case 1:
					Bukkit.broadcastMessage(
							plugin.getMessageManager().getPrepareCountdownOne().replace("%seconds%", "" + seconds));
					if (plugin.getConfigManager().playSounds()) {
						for (Player all : Bukkit.getOnlinePlayers())
							all.playSound(all.getLocation(), Sound.BLOCK_LEVER_CLICK, 1, 1);
					}
					break;
				case 0:
					stop();
					Bukkit.broadcastMessage(plugin.getMessageManager().getPrepareCountdownEnd());
					for (Player player : plugin.getPlayers())
						plugin.getFreeze().remove(player);

					if (plugin.getConfigManager().playSounds()) {
						for (Player all : Bukkit.getOnlinePlayers())
							all.playSound(all.getLocation(), Sound.BLOCK_NOTE_PLING, 1, 1);
					}

					plugin.getTimer().start(System.currentTimeMillis());
					break;
				default:
					break;
				}

				seconds--;
			}
		}, 20, 20);
	}

	@Override
	public void stop() {
		if (isRunning) {
			Bukkit.getScheduler().cancelTask(taskId);
			isRunning = false;
			seconds = plugin.getConfigManager().getPrepareCountdown();
		}
	}

	public boolean isRunning() {
		return isRunning;
	}
}