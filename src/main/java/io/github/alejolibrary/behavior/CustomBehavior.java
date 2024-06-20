package io.github.alejolibrary.behavior;

import org.bukkit.entity.Entity;

/**
 * Interface that allows you to apply custom behavior to an Entity.
 */
@FunctionalInterface
public interface CustomBehavior {

    /**
     * Called every tick that the behavior is active.
     * @param entity Entity that runs the behavior.
     */
    void tick(Entity entity);

    /**
     * Called when the behavior is applied.
     * @param entity Entity that runs the behavior.
     */
    default void start(Entity entity) {}

}
