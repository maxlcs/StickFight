package me.skaliert.stickfight.gamestate;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import me.skaliert.stickfight.StickFight;
import me.skaliert.stickfight.countdown.EndingCountdown;

public class EndingState extends GameState {

	private StickFight plugin;

	public EndingState(StickFight plugin) {
		this.plugin = plugin;
	}

	@Override
	public void start() {
		plugin.getFreeze().clear();
		plugin.getTimer().stop();

		for (Player player : Bukkit.getOnlinePlayers()) {
			player.setGameMode(GameMode.ADVENTURE);
			player.getInventory().clear();
			if (player.hasPotionEffect(PotionEffectType.INVISIBILITY))
				player.removePotionEffect(PotionEffectType.INVISIBILITY);
		}

		new EndingCountdown(plugin).start();
	}

	@Override
	public void stop() {

	}
}