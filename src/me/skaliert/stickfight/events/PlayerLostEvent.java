package me.skaliert.stickfight.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerLostEvent extends Event {

	private Player loser;

	public PlayerLostEvent(Player loser) {
		this.loser = loser;
	}

	public Player getLoser() {
		return loser;
	}

	private static final HandlerList handlers = new HandlerList();

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}