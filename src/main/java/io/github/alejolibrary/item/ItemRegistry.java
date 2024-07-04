package io.github.alejolibrary.item;

import io.github.alejolibrary.AlejoLibrary;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ItemRegistry {

    private static final Map<String, ItemWrapper> ITEM_MAP = new ConcurrentHashMap<>();
    private static final NamespacedKey KEY = AlejoLibrary.createKey("custom-item");

    public static void register(@NotNull ItemStack itemStack, @NotNull CustomItem customItem) {
        String itemClassName = customItem.getClass().getSimpleName();

        ItemStack resultItem = itemStack.clone();
        resultItem.editMeta(meta -> meta.getPersistentDataContainer().set(KEY, PersistentDataType.STRING, itemClassName));
        customItem.modifyItemStack(resultItem);

        ItemWrapper wrapper = new ItemWrapper(resultItem, customItem);
        ITEM_MAP.put(itemClassName, wrapper);

        Recipe recipe = customItem.recipe(resultItem);
        if (recipe != null) {
            Bukkit.addRecipe(recipe);
        }

    }

    /**
     * Register the listener for CustomItem implementations to work correctly.
     * @param plugin Plugin that owns the CustomItem implementations.
     */
    public static void listener(@NotNull Plugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(new CustomItemListener(), plugin);
    }

    /**
     * Gets the ItemStack of a registered CustomItem.
     * @param customItemClass Class of the registered CustomItem.
     * @return ItemStack of a registered CustomItem, or null if an instance of that ItemStack is not registered.
     */
    @Nullable
    public static ItemStack getItem(@NotNull Class<? extends CustomItem> customItemClass) {
        String itemClassName = customItemClass.getSimpleName();
        ItemWrapper wrapper = ITEM_MAP.get(itemClassName);
        if (wrapper != null) {
            return wrapper.itemStack();
        }
        return null;
    }

    /**
     * Gets the custom item associated to the given ItemStack.
     * @param itemStack ItemStack to check.
     * @return the associated CustomItem, or null if not found.
     */
    @Nullable
    public static CustomItem getCustomItem(@NotNull ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null) {
            return null;
        }

        String key = meta.getPersistentDataContainer().get(KEY, PersistentDataType.STRING);
        if (key == null) {
            return null;
        }
        ItemWrapper wrapper = ITEM_MAP.get(key);
        if (wrapper == null) {
            return null;
        }
        return wrapper.customItem();
    }

    private record ItemWrapper(ItemStack itemStack, CustomItem customItem) {

        @Override
        public ItemStack itemStack() {
                return itemStack.clone();
            }
        }

}
