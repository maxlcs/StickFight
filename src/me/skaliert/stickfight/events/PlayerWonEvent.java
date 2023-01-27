package me.skaliert.stickfight.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerWonEvent extends Event {

	private Player winner;

	public PlayerWonEvent(Player winner) {
		this.winner = winner;
	}

	public Player getWinner() {
		return winner;
	}

	private static final HandlerList handlers = new HandlerList();

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}