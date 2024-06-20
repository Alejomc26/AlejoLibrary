package io.github.alejolibrary.behavior;

import com.destroystokyo.paper.event.entity.EntityRemoveFromWorldEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * Manager that allows setting and deleting CustomBehavior implementations to or from an Entity.
 */
public record BehaviorManager(Plugin plugin) {

    private static final Map<UUID, BehaviorWrapper> BEHAVIOR_MAP = new HashMap<>();
    private static final Map<UUID, CustomBehavior> CUSTOM_BEHAVIOR_MAP = new HashMap<>();
    private static boolean isListenerRegistered = false;

    public BehaviorManager {
        if (!isListenerRegistered) {
            Bukkit.getPluginManager().registerEvents(new BehaviorListener(this, BEHAVIOR_MAP), plugin);
            isListenerRegistered = true;
        }
    }

    /**
     * Sets a CustomBehavior to an entity.
     * @param entity   Entity that will receive the behavior.
     * @param behavior Custom behavior to apply.
     */
    public void setBehavior(@NotNull Entity entity, @NotNull CustomBehavior behavior) {
        behavior.start(entity);
        BehaviorWrapper behaviorWrapper = BEHAVIOR_MAP.computeIfAbsent(entity.getUniqueId(), k -> {
            BehaviorWrapper newWrapper = new BehaviorWrapper();
            entity.getScheduler().runAtFixedRate(this.plugin, scheduledTask -> {
                if (newWrapper.getBehavior() != null) {
                    newWrapper.getBehavior().tick(entity);
                }
            }, null, 1, 1);
            return newWrapper;
        });
        behaviorWrapper.setBehavior(behavior);

    }

    public void setBehavior(@NotNull Entity entity, @NotNull Class<? extends CustomBehavior> behaviorClass) {
        String behaviorName = behaviorClass.getSimpleName();
        CustomBehavior behavior = BehaviorRegistry.getBehaviorSupplier(behaviorName).get();
        behavior.start(entity);
    }

    /**
     * Sets a consumer that will be accepted when the Entity is removed.
     *
     * @param entity   Entity whose event will be used on the consumer.
     * @param consumer Consumer that will be accepted when the Entity is removed.
     */
    public void setOnRemoved(Entity entity, Consumer<EntityRemoveFromWorldEvent> consumer) {
        BehaviorWrapper behaviorWrapper = getWrapper(entity);
        behaviorWrapper.setRemoveConsumer(consumer);
    }

    /**
     * Sets a consumer that will be accepted when the Entity dies.
     *
     * @param entity   Entity whose event will be used on the consumer.
     * @param consumer Consumer that will be accepted when the Entity dies.
     */
    public void setOnDeath(Entity entity, Consumer<EntityDeathEvent> consumer) {
        BehaviorWrapper behaviorWrapper = getWrapper(entity);
        behaviorWrapper.setDeathConsumer(consumer);
    }

    /**
     * Removes the current CustomBehavior of an Entity.
     *
     * @param entity Entity that will have its CustomBehavior stopped.
     */
    public void removeBehavior(Entity entity) {
        BehaviorWrapper behaviorWrapper = getWrapper(entity);
        behaviorWrapper.setBehavior(null);
    }

    /**
     * Gets the wrapper used to tick the CustomBehavior and Consumers.
     *
     * @param entity Entity that owns the wrapper.
     * @return Wrapper used to tick the CustomBehavior and Consumers.
     */
    public BehaviorWrapper getWrapper(Entity entity) {
        return BEHAVIOR_MAP.computeIfAbsent(entity.getUniqueId(), k -> new BehaviorWrapper());
    }

    /**
     * Gets the plugin of the BehaviorManager
     *
     * @return plugin
     */
    @Override
    public Plugin plugin() {
        return plugin;
    }

    public static class BehaviorWrapper {

        private Consumer<EntityRemoveFromWorldEvent> removeConsumer;
        private Consumer<EntityDeathEvent> deathConsumer;
        private CustomBehavior behavior;

        public void callRemoveConsumer(EntityRemoveFromWorldEvent event) {
            if (this.removeConsumer != null) {
                this.removeConsumer.accept(event);
            }
        }

        public void callDeathConsumer(EntityDeathEvent event) {
            if (this.deathConsumer != null) {
                this.deathConsumer.accept(event);
            }
        }

        public void setRemoveConsumer(Consumer<EntityRemoveFromWorldEvent> removeConsumer) {
            this.removeConsumer = removeConsumer;
        }

        public void setDeathConsumer(Consumer<EntityDeathEvent> deathConsumer) {
            this.deathConsumer = deathConsumer;
        }

        public CustomBehavior getBehavior() {
            return behavior;
        }

        public void setBehavior(@Nullable CustomBehavior behavior) {
            this.behavior = behavior;
        }

        public boolean isNew() {
            return this.removeConsumer == null || this.deathConsumer == null || this.behavior == null;
        }

    }

}

