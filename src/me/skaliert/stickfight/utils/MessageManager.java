package me.skaliert.stickfight.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;

public class MessageManager {

	private FileBuilder fileBuilder;

	public MessageManager() {
		fileBuilder = new FileBuilder("plugins//StickFight", "messages.yml");
	}

	public String getPrefix() {
		return translatePrefix(fileBuilder.getString("general.prefix"));
	}

	public String getServerFull() {
		return translate(fileBuilder.getString("general.server-full"));
	}

	public String getServerStopping() {
		return translate(fileBuilder.getString("general.server-stopping"));
	}

	public List<String> getCommandUsage() {
		List<String> usage = fileBuilder.getStringList("command.usage");
		List<String> translatedUsage = new ArrayList<String>();

		for (int i = 0; i < usage.size(); i++) {
			translatedUsage.add(translate(usage.get(i)));
		}

		return translatedUsage;
	}

	public String getPos1() {
		return translate(fileBuilder.getString("command.successful.player-position-1"));
	}

	public String getPos2() {
		return translate(fileBuilder.getString("command.successful.player-position-2"));
	}

	public String getSpawn() {
		return translate(fileBuilder.getString("command.successful.spawn-point"));
	}

	public String getSpec() {
		return translate(fileBuilder.getString("command.successful.spectator-spawn-point"));
	}

	public String getMustBePlayer() {
		return translate(fileBuilder.getString("command.failure.must-be-a-player"));
	}

	public String getNoPermissions() {
		return translate(fileBuilder.getString("command.failure.no-permissions"));
	}

	public String getLobbyCountdown() {
		return translate(fileBuilder.getString("countdown.lobby-countdown.countdown"));
	}

	public String getLobbyCountdownOne() {
		return translate(fileBuilder.getString("countdown.lobby-countdown.countdown-one"));
	}

	public String getLobbyCountdownReset() {
		return translate(fileBuilder.getString("countdown.lobby-countdown.countdown-reset"));
	}

	public String getLobbyCountdownEnd() {
		return translate(fileBuilder.getString("countdown.lobby-countdown.countdown-end"));
	}

	public String getIdle() {
		return translate(fileBuilder.getString("countdown.idle.idle"));
	}

	public String getIdleOne() {
		return translate(fileBuilder.getString("countdown.idle.idle-one"));
	}

	public String getPrepareCountdown() {
		return translate(fileBuilder.getString("countdown.prepare-countdown.countdown"));
	}

	public String getPrepareCountdownOne() {
		return translate(fileBuilder.getString("countdown.prepare-countdown.countdown-one"));
	}

	public String getPrepareCountdownEnd() {
		return translate(fileBuilder.getString("countdown.prepare-countdown.countdown-end"));
	}

	public String getEndingCountdown() {
		return translate(fileBuilder.getString("countdown.ending-countdown.countdown"));
	}

	public String getEndingCountdownOne() {
		return translate(fileBuilder.getString("countdown.ending-countdown.countdown-one"));
	}

	public String getScoreboardtitle() {
		return translate(fileBuilder.getString("scoreboard.title"));
	}

	public String getJoined() {
		return translate(fileBuilder.getString("game.player-joined"));
	}

	public String getQuit() {
		return translate(fileBuilder.getString("game.player-left"));
	}

	public String getQuitPlaying() {
		return translate(fileBuilder.getString("game.player-left-while-playing"));
	}

	public String getFellDown() {
		return translate(fileBuilder.getString("game.player-fell-down"));
	}

	public String getWinTitle() {
		return translate(fileBuilder.getString("game.win.title"));
	}

	public String getWinBroadcast() {
		return translate(fileBuilder.getString("game.win.broadcast"));
	}

	public String getTimer() {
		return translate(fileBuilder.getString("game.timer"));
	}

	private String translatePrefix(String prefix) {
		return ChatColor.translateAlternateColorCodes('&', prefix);
	}

	private String translate(String message) {
		return ChatColor.translateAlternateColorCodes('&', message).replace("%prefix%", getPrefix());
	}
}