package me.skaliert.stickfight.utils;

import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.skaliert.stickfight.StickFight;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;

public class Timer {

	private StickFight plugin;

	private int taskId;

	public Timer(StickFight plugin) {
		this.plugin = plugin;
	}

	public void start(long timestamp) {
		if (plugin.getConfigManager().showTimer()) {
			taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {

				@Override
				public void run() {
					long seconds = (System.currentTimeMillis() - timestamp) / 1000;

					for (Player player : plugin.getPlayers()) {
						player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
								new ComponentBuilder(plugin.getMessageManager().getTimer().replace("%timer%",
										getFormattedTime((int) seconds))).create());
					}
				}
			}, 0, 20);
		}
	}

	public void stop() {
		if (plugin.getConfigManager().showTimer()) {
			Bukkit.getScheduler().cancelTask(taskId);
			for (Player player : plugin.getPlayers()) {
				player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new ComponentBuilder("").create());
			}
		}
	}

	public String getFormattedTime(int seconds) {
		long mins = (int) TimeUnit.SECONDS.toMinutes(seconds) % 60;
		long secs = (int) TimeUnit.SECONDS.toSeconds(seconds) % 60;

		return String.format("%02d:%02d", mins, secs);
	}

	public int getTaskId() {
		return taskId;
	}
}