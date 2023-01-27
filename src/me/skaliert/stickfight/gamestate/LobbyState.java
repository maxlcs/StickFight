package me.skaliert.stickfight.gamestate;

import org.bukkit.Bukkit;

import me.skaliert.stickfight.countdown.LobbyCountdown;

public class LobbyState extends GameState {

	public static final int MIN_PLAYERS = 2, MAX_PLAYERS = 2;

	private GameStateManager gameStateManager;

	private LobbyCountdown lobbyCountdown;

	public LobbyState(GameStateManager gameStateManager) {
		this.gameStateManager = gameStateManager;
		lobbyCountdown = new LobbyCountdown(gameStateManager);
	}

	@Override
	public void start() {
		lobbyCountdown.startIdle();
	}

	@Override
	public void stop() {
		Bukkit.broadcastMessage(gameStateManager.getPlugin().getMessageManager().getLobbyCountdownEnd());
	}

	public LobbyCountdown getLobbyCountdown() {
		return lobbyCountdown;
	}
}