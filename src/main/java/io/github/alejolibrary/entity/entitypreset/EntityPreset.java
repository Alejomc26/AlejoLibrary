package io.github.alejolibrary.entity.entitypreset;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

/**
 * Interface to make spawning an entity with a default config easier. Spawn using {@link #spawn(Location, EntityPreset)} )}
 * @param <T> Entity to spawn.
 */
public interface EntityPreset<T extends Entity> {

    /**
     * Called before the entity is spawned.
     * @param location Spawn location.
     */
    void onSpawn(T entity, Location location);

    /**
     * @return Class of the entity to spawn.
     */
    Class<T> getEntityClass();

    /**
     * Spawns an EntityPresent implementation.
     * @param location Location to spawn the entity.
     * @param entityPreset Preset that will be used.
     */
    static <P extends Entity> void spawn(Location location, EntityPreset<P> entityPreset) {
        location.getWorld().spawn(location, entityPreset.getEntityClass(), entity -> entityPreset.onSpawn(entity, location));
    }

}
