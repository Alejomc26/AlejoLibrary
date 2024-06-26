package io.github.alejolibrary.item;

import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Interface that allows you to make custom functionality for items.
 */
public interface ItemFunction {

    /**
     * Called every time that a player interacts with an item with the function in hand.
     */
    default void interaction(PlayerInteractEvent event) {}

}