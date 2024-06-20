package io.github.alejolibrary.behavior;

import io.github.alejolibrary.item.ItemFunction;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;
import java.util.logging.Logger;

public class BehaviorRegistry {

    private static final Map<String, Supplier<CustomBehavior>> BEHAVIOR_MAP = new ConcurrentHashMap<>();

    /**
     * Register a CustomBehavior supplier. Apply registered behavior using.
     * @param behaviorSupplier Supplier used to apply a new instance every time it is applied.
     * @param plugin Plugin that owns the behavior.
     */
    public static void register(@NotNull Supplier<CustomBehavior> behaviorSupplier, @NotNull Plugin plugin) {
        String behaviorName = behaviorSupplier.get().getClass().getSimpleName();
        Logger logger = plugin.getLogger();

        boolean registeredSuccessfully = BEHAVIOR_MAP.putIfAbsent(behaviorName, behaviorSupplier) == null;
        if (registeredSuccessfully) {
            logger.info("CustomBehavior " + behaviorName + " has been registered successfully");
        } else {
            logger.warning("CustomBehavior " + behaviorName + " is already registered");
        }
    }

    @Nullable
    public static Supplier<CustomBehavior> getBehaviorSupplier(@Nullable String behaviorName) {
        if (behaviorName == null) {
            return null;
        }
        return BEHAVIOR_MAP.get(behaviorName);
    }

}
