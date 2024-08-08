package io.github.alejolibrary.behavior;

import org.bukkit.entity.Entity;

/**
 * Interface that allows you to apply custom behavior to an Entity.
 */
@FunctionalInterface
public interface CustomBehavior<T extends Entity> {

    /**
     * Called when the behavior is applied.
     * @param entity Entity that runs the behavior.
     */
    default void start(T entity) {}

    /**
     * Called every tick that the behavior is active.
     * @param entity Entity that runs the behavior.
     */
    void tick(T entity);

}
