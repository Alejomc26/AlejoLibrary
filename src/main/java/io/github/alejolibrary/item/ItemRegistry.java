package io.github.alejolibrary.item;

import io.github.alejolibrary.AlejoLibrary;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ItemRegistry {

    private static final Map<String, CustomItem> ITEM_MAP = new ConcurrentHashMap<>();
    private static final NamespacedKey KEY = AlejoLibrary.createKey("custom-item");

    public static void register(ItemStack itemStack, CustomItem customItem) {
        String itemClassName = customItem.getClass().getSimpleName();
        ITEM_MAP.put(itemClassName, customItem);

        ItemStack resultItem = itemStack.clone();
        resultItem.editMeta(meta -> meta.getPersistentDataContainer().set(KEY, PersistentDataType.STRING, itemClassName));
        customItem.modifyItemStack(resultItem);

        Recipe recipe = customItem.recipe(resultItem);
        if (recipe != null) {
            Bukkit.addRecipe(recipe);
        }

    }

    /**
     * Register the listener for CustomItem implementations to work correctly.
     * @param plugin Plugin that owns the CustomItem implementations.
     */
    public static void listener(Plugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(new CustomItemListener(), plugin);
    }

    /**
     * Gets the custom item associated to the given ItemStack.
     * @param itemStack ItemStack to check.
     * @return the associated CustomItem, or null if not found.
     */
    @Nullable
    public static CustomItem getCustomItem(ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();
        if (meta != null) {
            String key = meta.getPersistentDataContainer().get(KEY, PersistentDataType.STRING);
            return ITEM_MAP.get(key);
        }
        return null;
    }

}
