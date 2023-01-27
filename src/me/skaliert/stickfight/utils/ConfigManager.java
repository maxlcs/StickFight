package me.skaliert.stickfight.utils;

public class ConfigManager {

	private FileBuilder fileBuilder;

	public ConfigManager() {
		fileBuilder = new FileBuilder("plugins//StickFight", "config.yml");
	}

	public int getLobbyCountdown() {
		return fileBuilder.getInt("countdown.lobby-countdown");
	}

	public int getPrepareCountdown() {
		return fileBuilder.getInt("countdown.prepare-countdown");
	}

	public int getEndingCountdown() {
		return fileBuilder.getInt("countdown.ending-countdown");
	}

	public int getIdlePeriod() {
		return fileBuilder.getInt("idle.idle-period");
	}

	public int getPointsToWin() {
		return fileBuilder.getInt("points.points-to-win");
	}

	public boolean showScoreboard() {
		return fileBuilder.getBoolean("extras.show-scoreboard");
	}

	public boolean playSounds() {
		return fileBuilder.getBoolean("extras.play-sounds");
	}

	public boolean showTimer() {
		return fileBuilder.getBoolean("extras.show-timer");
	}
}