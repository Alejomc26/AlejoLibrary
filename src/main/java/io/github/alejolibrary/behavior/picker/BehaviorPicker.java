package io.github.alejolibrary.behavior.picker;

import io.github.alejolibrary.behavior.BehaviorManager;
import io.github.alejolibrary.behavior.CustomBehavior;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

/**
 * Class to make auto picking behaviors easier, this uses {@link BehaviorManager#setBehavior(Entity, CustomBehavior)}.
 * @param <T> Type of the entity that you want to add behaviors
 */
public interface BehaviorPicker<T extends Entity> {

    /**
     * Adds a supplier for a CustomBehavior to the picker list.
     * @param behaviorSupplier Supplier for a behavior that will be used when picking a behavior.
     */
    default void add(@NotNull Supplier<CustomBehavior<? super T>> behaviorSupplier) {
        add(behaviorSupplier, 1);
    }

    /**
     * Adds a supplier for a CustomBehavior to the picker list.
     * @param behaviorSupplier Supplier for a behavior that will be used when picking a behavior.
     * @param timesToRepeat Times the passed supplier will be added to the picker list.
     */
    void add(@NotNull Supplier<CustomBehavior<? super T>> behaviorSupplier, int timesToRepeat);

    /**
     * Gets a CustomBehavior from the picker list.
     */
    @Nullable
    CustomBehavior<? super T> get();

    /**
     * Picks one of the CustomBehavior implementations and sets it to the entity
     * @param entity Entity to apply the CustomBehavior implementations
     */
    void pick(@NotNull T entity);

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
