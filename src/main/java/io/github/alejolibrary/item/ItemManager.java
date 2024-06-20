package io.github.alejolibrary.item;

import io.github.alejolibrary.AlejoLibrary;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.logging.Logger;

/**
 * Manager that allows setting and retrieving ItemFunction implementations to or from an item
 */
public class ItemManager {

    private static final NamespacedKey KEY = AlejoLibrary.createKey("custom-item");
    private static boolean isListenerRegistered = false;
    private final Logger logger;

    public ItemManager(@NotNull Plugin plugin) {
        if (!isListenerRegistered) {
            Bukkit.getPluginManager().registerEvents(new ItemListener(this), plugin);
            isListenerRegistered = true;
        }
        this.logger = plugin.getLogger();
    }

    /**
     * Sets a registered ItemFunction functionality to an item.
     * @param item Item that will receive the ItemFunction.
     * @param itemFunctionClass Class of the ItemFunction.
     */
    public void setItemFunction(@NotNull ItemStack item, @NotNull Class<? extends ItemFunction> itemFunctionClass) {
        item.editMeta(meta -> setItemMetaFunction(meta, itemFunctionClass));
    }

    /**
     * Sets a registered ItemFunction functionality to an ItemMeta.
     * @param meta ItemMeta that will receive the ItemFunction.
     * @param itemFunctionClass Class of the ItemFunction.
     */
    public void setItemMetaFunction(@NotNull ItemMeta meta, @NotNull Class<? extends ItemFunction> itemFunctionClass) {
        String functionName = itemFunctionClass.getSimpleName();
        if (ItemFunctionRegistry.getFunction(functionName) == null) {
            this.logger.warning("ItemFunction " + functionName + " is not registered and has been added to an item.");
        }
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(KEY, PersistentDataType.STRING, functionName);
    }

    /**
     * Gets the ItemFunction of an ItemStack.
     * @param itemStack Item with an ItemFunction.
     * @return ItemFunction of the passed ItemStack, or null if the ItemStack does not have one.
     */
    @Nullable
    public ItemFunction getItemFunction(@Nullable ItemStack itemStack) {
        ItemMeta meta = itemStack != null ? itemStack.getItemMeta() : null;
        if (meta == null) {
            return null;
        }
        return getItemMetaFunction(meta);
    }

    /**
     * Gets the ItemFunction of an ItemMeta.
     * @param meta ItemMeta with an ItemFunction.
     * @return ItemFunction of the passed ItemMeta, or null if the ItemMeta does not have one.
     */
    @Nullable
    public ItemFunction getItemMetaFunction(@NotNull ItemMeta meta) {
        PersistentDataContainer container = meta.getPersistentDataContainer();
        String functionName = container.get(KEY, PersistentDataType.STRING);
        return ItemFunctionRegistry.getFunction(functionName);
    }

}
