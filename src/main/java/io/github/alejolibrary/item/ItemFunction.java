package io.github.alejolibrary.item;

import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Interface that allows you to make custom functionality for items.
 */
public interface ItemFunction {

    default void interaction(PlayerInteractEvent event) {}

}
