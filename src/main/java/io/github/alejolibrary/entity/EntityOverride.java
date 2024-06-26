package io.github.alejolibrary.entity;

import com.destroystokyo.paper.event.entity.EntityAddToWorldEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.event.entity.EntitySpawnEvent;

import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Interface to override a vanilla entity, register using {@link EntityOverrideRegistry#register(Predicate, Supplier)}.
 */
public interface EntityOverride {

    /**
     * Called when the entity spawns.
     */
    default void onSpawn(EntitySpawnEvent event) {}

    /**
     * Called when the entity is added to the world.
     */
    default void onAdd(EntityAddToWorldEvent event) {}

    /**
     * Called when the entity dies.
     */
    default void onDeath(EntityDeathEvent event) {}

    /**
     * Called when the entity is spawned or added to the world
     */
    default void onLoad(EntityEvent event) {}

}
