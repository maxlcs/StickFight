package me.skaliert.stickfight.gamestate;

import me.skaliert.stickfight.StickFight;

public class GameStateManager {

	private StickFight plugin;
	private GameState[] gameStates;
	private GameState currentGameState;

	public GameStateManager(StickFight plugin) {
		this.plugin = plugin;
		gameStates = new GameState[3];

		gameStates[GameState.LOBBY_STATE] = new LobbyState(this);
		gameStates[GameState.INGAME_STATE] = new IngameState(plugin);
		gameStates[GameState.ENDING_STATE] = new EndingState(plugin);
	}

	public void setGameState(int gameStateId) {
		if (currentGameState != null)
			currentGameState.stop();
		currentGameState = gameStates[gameStateId];
		currentGameState.start();
	}

	public void stopCurrentGameState() {
		if (currentGameState != null) {
			currentGameState.stop();
			currentGameState = null;
		}
	}

	public GameState getCurrentGameState() {
		return currentGameState;
	}

	public StickFight getPlugin() {
		return plugin;
	}
}