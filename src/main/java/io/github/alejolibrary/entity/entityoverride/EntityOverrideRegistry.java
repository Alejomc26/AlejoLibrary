package io.github.alejolibrary.entity.entityoverride;

import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Register your EntityOverride implementations
 */
public class EntityOverrideRegistry {

    private static final List<EntityOverride> OVERRIDE_LIST = new ArrayList<>();

    /**
     * Register an EntityOverride to be called when an entity is loaded.
     */
    public static void register(@NotNull EntityOverride entityOverride) {
        OVERRIDE_LIST.add(entityOverride);
    }

    /**
     * Register the listener for EntityOverride implementations to work correctly.
     * @param plugin Plugin that owns the EntityOverride implementations.
     */
    public static void listener(@NotNull Plugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(new EntityOverrideListener(plugin), plugin);
    }

    /**
     * Gets the EntityOverride that meets the requirements of the passed entity.
     * @param entity Entity whose requirements will be tested.
     * @return EntityOverride that meets the requirements of the passes entity, or null if none was found.
     */
    @Nullable
    public static EntityOverride getEntityOverride(@NotNull Entity entity) {
        for (EntityOverride entityOverride : OVERRIDE_LIST) {
            if (entityOverride.testEntity(entity)) {
                return entityOverride;
            }
        }
        return null;
    }

}
