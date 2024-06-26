package io.github.alejolibrary.item;

import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

/**
 * Registry for ItemFunction implementations to work correctly.
 */
public class ItemFunctionRegistry {

    private static final Map<String, ItemFunction> FUNCTION_MAP = new ConcurrentHashMap<>();

    /**
     * Register an ItemFunction implementation. Apply registered functions to items using
     * {@link ItemManager#setItemFunction(ItemStack, Class)}.
     * @param function ItemFunction implementation instance whose methods will be called when a player interacts with
     *                an item with the item function in hand.
     */
    public static void register(@NotNull ItemFunction function, @NotNull Plugin plugin) {
        String functionName = function.getClass().getSimpleName();
        Logger logger = plugin.getLogger();

        boolean registeredSuccessfully = FUNCTION_MAP.putIfAbsent(functionName, function) == null;
        if (registeredSuccessfully) {
            logger.info("ItemFunction " + functionName + " has been registered successfully");
        } else {
            logger.warning("ItemFunction " + functionName + " is already registered");
        }
    }

    /**
     * Gets an ItemFunction with that name.
     * @param functionName ItemFunction name.
     * @return ItemFunction with that name or null if an ItemFunction with that name has not been registered.
     */
    @Nullable
    public static ItemFunction getFunction(@Nullable String functionName) {
        if (functionName == null) {
            return null;
        }
        return FUNCTION_MAP.get(functionName);
    }
}
