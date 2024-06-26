package io.github.alejolibrary.entity;

import com.destroystokyo.paper.event.entity.EntityAddToWorldEvent;
import org.bukkit.event.entity.EntitySpawnEvent;

import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Interface to override a vanilla entity, register using {@link EntityOverrideRegistry#register(Predicate, Supplier)}.
 */
public interface EntityOverride {

    /**
     * Called when an entity that meets the predicate requirements spawns.
     */
    void onSpawn(EntitySpawnEvent event);

    /**
     * Called when an entity that meets the predicate requirements is added to the world.
     * @param event
     */
    void onAdd(EntityAddToWorldEvent event);

}
