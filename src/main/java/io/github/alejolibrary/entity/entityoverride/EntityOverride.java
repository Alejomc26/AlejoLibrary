package io.github.alejolibrary.entity.entityoverride;

import com.destroystokyo.paper.event.entity.EntityAddToWorldEvent;
import com.destroystokyo.paper.event.entity.EntityRemoveFromWorldEvent;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.event.entity.EntitySpawnEvent;

/**
 * Interface to override a vanilla entity, register using {@link EntityOverrideRegistry#register(EntityOverride)}.
 */
public interface EntityOverride {

    /**
     * Called when the entity spawns.
     */
    default void onSpawn(EntitySpawnEvent event) {}

    /**
     * Called 1 tick after the entity is added to the world (calling it instantly breaks teleport api).
     */
    default void onAdd(EntityAddToWorldEvent event) {}

    /**
     * Called when the entity dies.
     */
    default void onDeath(EntityDeathEvent event) {}

    /**
     * Called when the entity is involved in EntityDamageEvent
     */
    default void onDamage(EntityDamageEvent event) {}

    /**
     * Called when the entity is spawned or added to the world
     */
    default void onLoad(EntityEvent event) {}

    /**
     * Called when the entity is removed from a world.
     */
    default void onRemove(EntityRemoveFromWorldEvent event) {}

    /**
     * Check if the entity should have your override.
     * @param entity Entity that is going to be loaded.
     * @return True to apply your override to the loaded entity, otherwise false.
     */
    boolean testEntity(Entity entity);

}
