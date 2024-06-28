package io.github.alejolibrary.item;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class CustomItemListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        ItemStack itemStack = event.getItem();
        if (itemStack == null) {
            return;
        }
        CustomItem customItem = ItemRegistry.getCustomItem(itemStack);
        if (customItem != null) {
            customItem.onInteraction(event);
        }
    }

}
