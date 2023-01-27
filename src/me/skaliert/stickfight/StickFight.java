package me.skaliert.stickfight;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.skaliert.stickfight.commands.StickFightCommand;
import me.skaliert.stickfight.gamestate.GameState;
import me.skaliert.stickfight.gamestate.GameStateManager;
import me.skaliert.stickfight.listener.JoinQuitListener;
import me.skaliert.stickfight.listener.PlayerListener;
import me.skaliert.stickfight.utils.ConfigManager;
import me.skaliert.stickfight.utils.MessageManager;
import me.skaliert.stickfight.utils.Timer;

public class StickFight extends JavaPlugin {

	private GameStateManager gameStateManager;
	private ConfigManager configManager;
	private MessageManager messageManager;

	private ArrayList<Player> players;
	private ArrayList<Player> spectators;
	private ArrayList<Player> freeze;
	private HashMap<Player, Integer> points;

	private Timer timer;

	@Override
	public void onEnable() {
		saveResource("config.yml", false);
		saveResource("messages.yml", false);

		configManager = new ConfigManager();
		messageManager = new MessageManager();

		gameStateManager = new GameStateManager(this);
		gameStateManager.setGameState(GameState.LOBBY_STATE);

		players = new ArrayList<Player>();
		spectators = new ArrayList<Player>();
		freeze = new ArrayList<Player>();
		points = new HashMap<Player, Integer>();

		timer = new Timer(this);

		new JoinQuitListener(this);
		new PlayerListener(this);

		getCommand("stickfight").setExecutor(new StickFightCommand(this));
	}

	public GameStateManager getGameStateManager() {
		return gameStateManager;
	}

	public ConfigManager getConfigManager() {
		return configManager;
	}

	public MessageManager getMessageManager() {
		return messageManager;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public ArrayList<Player> getSpectators() {
		return spectators;
	}

	public ArrayList<Player> getFreeze() {
		return freeze;
	}

	public HashMap<Player, Integer> getPoints() {
		return points;
	}

	public Timer getTimer() {
		return timer;
	}
}