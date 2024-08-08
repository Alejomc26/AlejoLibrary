package io.github.alejolibrary.behavior;

import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * Manager to add and remove CustomBehavior implementations from or to an entity.
 */
public record BehaviorManager(Plugin plugin) {

    private static final Map<UUID, BehaviorContainer<?>> ACTIVE_BEHAVIOR_MAP = new HashMap<>();

    /**
     * Sets a CustomBehavior to an entity, if another one is set the previous one will stop working,
     * you can add more CustomBehaviors without stopping this one using {@link #addBehavior(Entity, CustomBehavior)}.
     * @param entity Entity to add the CustomBehavior.
     * @param behavior Behavior that will be ticked until the entity is unloaded or killed.
     * @param <T> Type of the entity that you want to add the behavior
     */
    public <T extends Entity> void setBehavior(@NotNull T entity, @Nullable CustomBehavior<? super T> behavior) {
        BehaviorContainer<T> wrapper = getWrapper(entity);
        wrapper.setUniqueBehavior(behavior);
        if (behavior != null) {
            behavior.start(entity);
        }

    }

    /**
     * Clears all the CustomBehaviors added via {@link #addBehavior(Entity, CustomBehavior)} from an entity.
     * @param entity Entity to remove the CustomBehaviors.
     * @param <T> Type of the entity that you want to clear its behaviors.
     */
    public <T extends Entity> void clearBehaviors(@NotNull T entity) {
        BehaviorContainer<T> wrapper = getWrapper(entity);
        wrapper.clear();
    }

    /**
     * Adds a CustomBehavior to an entity, if a CustomBehavior has been set using
     * {@link #setBehavior(Entity, CustomBehavior)} it will not be stopped.
     * @param entity Entity to add the CustomBehavior.
     * @param behavior Behavior that will be ticked until the entity is unloaded or killed.
     * @param <T> Type of the entity that you want to add the behavior
     */
    public <T extends Entity> void addBehavior(@NotNull T entity, @NotNull CustomBehavior<? super T> behavior) {
        BehaviorContainer<T> wrapper = getWrapper(entity);
        wrapper.addBehavior(behavior);
        behavior.start(entity);
    }

    @SuppressWarnings("unchecked")
    private <T extends Entity> BehaviorContainer<T> getWrapper(T entity) {
        return (BehaviorContainer<T>) ACTIVE_BEHAVIOR_MAP.computeIfAbsent(entity.getUniqueId(), k -> {
            BehaviorContainer<T> newWrapper = new BehaviorContainer<>();
            entity.getScheduler().runAtFixedRate(
                    plugin,
                    scheduledTask -> newWrapper.tickBehaviors(entity),
                    () -> ACTIVE_BEHAVIOR_MAP.remove(entity.getUniqueId()),
                    1, 1
            );
            return newWrapper;
        });
    }

    private static class BehaviorContainer<T extends Entity> {

        private final List<CustomBehavior<? super T>> behaviors = new ArrayList<>();
        private CustomBehavior<? super T> uniqueBehavior;

        public void tickBehaviors(@NotNull T entity) {
            for (CustomBehavior<? super T> behavior : behaviors) {
                behavior.tick(entity);
            }
            if (uniqueBehavior != null) {
                uniqueBehavior.tick(entity);
            }
        }

        public void addBehavior(@NotNull CustomBehavior<? super T> behavior) {
            behaviors.add(behavior);
        }

        public void setUniqueBehavior(@Nullable CustomBehavior<? super T> behavior) {
            uniqueBehavior = behavior;
        }

        public void clear() {
            behaviors.clear();
        }

    }

}
