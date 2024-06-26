package io.github.alejolibrary.behavior.picker;

import io.github.alejolibrary.behavior.BehaviorManager;
import io.github.alejolibrary.behavior.CustomBehavior;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

/**
 * Class to make auto picking behaviors easier, this uses {@link BehaviorManager#setBehavior(Entity, CustomBehavior)}.
 */
public interface BehaviorPicker {

    /**
     * Adds a CustomBehavior to the picker list.
     * @param behavior Behavior to add.
     */
    default void add(@NotNull CustomBehavior behavior) {
        add(behavior, 1);
    }

    /**
     * Adds a CustomBehavior to the picker list.
     * @param behavior Behavior to add.
     * @param timesToRepeat Times the passed behavior will be added to the picker list.
     */
    void add(@NotNull CustomBehavior behavior, int timesToRepeat);

    /**
     * Picks one of the CustomBehavior implementations and sets it to the entity
     * @param entity Entity to apply the CustomBehavior implementations
     */
    void pick(@NotNull Entity entity);

    /**
     * Clears all the CustomBehavior implementations from the picker list.
     */
    void clear();

    /**
     * Gets the BehaviorManager used to set the behavior in the picker.
     * @return BehaviorManager.
     */
    BehaviorManager getBehaviorManager();

}
