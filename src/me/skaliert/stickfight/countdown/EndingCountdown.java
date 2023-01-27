package me.skaliert.stickfight.countdown;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import me.skaliert.stickfight.StickFight;

public class EndingCountdown extends Countdown {

	private StickFight plugin;

	private int seconds;

	public EndingCountdown(StickFight plugin) {
		this.plugin = plugin;
		seconds = plugin.getConfigManager().getEndingCountdown();
	}

	@Override
	public void start() {
		taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {

			@Override
			public void run() {
				switch (seconds) {
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
					Bukkit.broadcastMessage(
							plugin.getMessageManager().getEndingCountdown().replace("%seconds%", "" + seconds));
					if (plugin.getConfigManager().playSounds()) {
						for (Player all : Bukkit.getOnlinePlayers())
							all.playSound(all.getLocation(), Sound.BLOCK_LEVER_CLICK, 1, 1);
					}
					break;
				case 1:
					Bukkit.broadcastMessage(
							plugin.getMessageManager().getEndingCountdownOne().replace("%seconds%", "" + seconds));
					if (plugin.getConfigManager().playSounds()) {
						for (Player all : Bukkit.getOnlinePlayers())
							all.playSound(all.getLocation(), Sound.BLOCK_LEVER_CLICK, 1, 1);
					}
					break;
				case 0:
					Bukkit.shutdown();
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

	}
}