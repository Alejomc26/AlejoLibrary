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

    private static final Map<UUID, BehaviorWrapper> ACTIVE_BEHAVIOR_MAP = new HashMap<>();

    /**
     * Sets a CustomBehavior to an entity, if another one is set the previous one will stop working,
     * you can add more CustomBehavior implementations without stopping this one using {@link #addBehavior(Entity, CustomBehavior)}.
     *
     * @param entity   Entity to add the CustomBehavior.
     * @param behavior Behavior that will be ticked until the entity is unloaded or killed.
     */
    public void setBehavior(@NotNull Entity entity, @Nullable CustomBehavior behavior) {
        BehaviorWrapper wrapper = ACTIVE_BEHAVIOR_MAP.computeIfAbsent(entity.getUniqueId(), k -> {
            BehaviorWrapper newWrapper = new BehaviorWrapper();
            entity.getScheduler().runAtFixedRate(
                    plugin,
                    scheduledTask -> newWrapper.tickBehaviors(entity),
                    () -> ACTIVE_BEHAVIOR_MAP.remove(entity.getUniqueId()),
                    1, 1
            );
            return newWrapper;
        });

        wrapper.setUniqueBehavior(behavior);
        if (behavior != null) {
            behavior.start(entity);
        }

    }

    /**
     * Adds a CustomBehavior to an entity, if a CustomBehavior has been set using
     * {@link #setBehavior(Entity, CustomBehavior)} it will not be stopped.
     *
     * @param entity   Entity to add the CustomBehavior.
     * @param behavior Behavior that will be ticked until the entity is unloaded or killed.
     */
    public void addBehavior(@NotNull Entity entity, @NotNull CustomBehavior behavior) {
        BehaviorWrapper wrapper = ACTIVE_BEHAVIOR_MAP.computeIfAbsent(entity.getUniqueId(), k -> {
            BehaviorWrapper newWrapper = new BehaviorWrapper();
            entity.getScheduler().runAtFixedRate(
                    plugin,
                    scheduledTask -> newWrapper.tickBehaviors(entity),
                    () -> ACTIVE_BEHAVIOR_MAP.remove(entity.getUniqueId()),
                    1, 1
            );
            return newWrapper;
        });

        wrapper.addBehavior(behavior);
        behavior.start(entity);
    }

    private static class BehaviorWrapper {

        private final List<CustomBehavior> behaviors = new ArrayList<>();
        private CustomBehavior uniqueBehavior;

        public void tickBehaviors(@NotNull Entity entity) {
            for (CustomBehavior behavior : behaviors) {
                behavior.tick(entity);
            }
            if (uniqueBehavior != null) {
                uniqueBehavior.tick(entity);
            }
        }

        public List<CustomBehavior> getBehaviors() {
            return behaviors;
        }

        public void addBehavior(@NotNull CustomBehavior behavior) {
            behaviors.add(behavior);
        }

        public CustomBehavior getUniqueBehavior() {
            return uniqueBehavior;
        }

        public void setUniqueBehavior(@Nullable CustomBehavior behavior) {
            uniqueBehavior = behavior;
        }

    }

}
