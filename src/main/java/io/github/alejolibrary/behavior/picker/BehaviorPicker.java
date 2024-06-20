package io.github.alejolibrary.behavior.picker;

import io.github.alejolibrary.behavior.BehaviorManager;
import io.github.alejolibrary.behavior.CustomBehavior;
import org.bukkit.entity.Entity;

import java.util.function.Supplier;

/**
 * Class to make auto picking behaviors easier.
 */
public interface BehaviorPicker {

    /**
     * Adds a CustomBehavior to the picker list.
     * @param behaviorSupplier Supplier of a CustomBehavior implementation instance.
     */
    default void add(Supplier<CustomBehavior> behaviorSupplier) {
        add(behaviorSupplier, 1);
    }

    /**
     * Adds a CustomBehavior to the picker list.
     * @param behaviorSupplier Supplier of a CustomBehavior implementation instance.
     * @param timesToRepeat Times the passed behavior will be added to the picker list.
     */
    void add(Supplier<CustomBehavior> behaviorSupplier, int timesToRepeat);

    /**
     * Picks one of the CustomBehavior implementations and sets it to the entity
     * @param entity Entity to apply the CustomBehavior implementations
     */
    void pick(Entity entity);

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
