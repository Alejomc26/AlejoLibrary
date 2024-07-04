package io.github.alejolibrary.entity.entityoverride;

import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Register your EntityOverride implementations
 */
public class EntityOverrideRegistry {

    private static final Map<Predicate<Entity>, Supplier<EntityOverride>> ENTITY_OVERRIDE_MAP = new LinkedHashMap<>();

    /**
     * Register an EntityOverride implementation to be called when an entity meets a predicate requirements.
     * @param predicate Check if the entity should have your override.
     * @param overrideSupplier Supplier to get your EntityOverride implementations.
     */
    public static void register(@NotNull Predicate<Entity> predicate, @NotNull Supplier<EntityOverride> overrideSupplier) {
        ENTITY_OVERRIDE_MAP.put(predicate, overrideSupplier);
    }

    /**
     * Register the listener for EntityOverride implementations to work correctly.
     * @param plugin Plugin that owns the EntityOverride implementations.
     */
    public static void listener(@NotNull Plugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(new EntityOverrideListener(), plugin);
    }

    /**
     * Gets the EntityOverride that meets the requirements of the passed entity.
     * @param entity Entity whose requirements will be tested.
     * @return EntityOverride that meets the requirements of the passes entity, or null if none was found.
     */
    @Nullable
    public static EntityOverride getEntityOverride(@NotNull Entity entity) {
        for (Map.Entry<Predicate<Entity>, Supplier<EntityOverride>> entry : ENTITY_OVERRIDE_MAP.entrySet()) {
            if (entry.getKey().test(entity)) {
                return entry.getValue().get();
            }
        }
        return null;
    }

}
