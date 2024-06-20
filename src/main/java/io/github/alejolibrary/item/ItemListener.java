package io.github.alejolibrary.item;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Listener for ItemFunction implementations
 */
public class ItemListener implements Listener {

    private final ItemManager manager;

    public ItemListener(ItemManager manager) {
        this.manager = manager;
    }

    @EventHandler
    public void interactListener(PlayerInteractEvent event) {
        ItemFunction function = manager.getItemFunction(event.getItem());
        if (function != null) {
            function.interaction(event);
        }
    }

}
