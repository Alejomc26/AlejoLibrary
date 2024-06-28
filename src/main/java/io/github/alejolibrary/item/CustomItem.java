package io.github.alejolibrary.item;

import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.Nullable;

public interface CustomItem {

    default void onInteraction(PlayerInteractEvent event) {}

    default void modifyItemStack(ItemStack itemStack) {}

    @Nullable
    default Recipe recipe(ItemStack resultItem) {
        return null;
    }

}
