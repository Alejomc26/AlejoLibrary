package io.github.alejolibrary.entity;

import com.destroystokyo.paper.event.entity.EntityAddToWorldEvent;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EntityOverrideListener implements Listener {

    private final Map<UUID, EntityOverride> activeOverridesMap = new HashMap<>();

    @EventHandler
    public void entitySpawnListener(EntitySpawnEvent event) {
        Entity entity = event.getEntity();
        EntityOverride entityOverride = activeOverridesMap.computeIfAbsent(
                entity.getUniqueId(),
                k -> EntityOverrideRegistry.getEntityOverride(entity)
        );
        if (entityOverride != null) {
            entityOverride.onSpawn(event);
        }

    }

    @EventHandler
    public void entityAddListener(EntityAddToWorldEvent event) {
        Entity entity = event.getEntity();
        EntityOverride entityOverride = activeOverridesMap.computeIfAbsent(
                entity.getUniqueId(),
                k -> EntityOverrideRegistry.getEntityOverride(entity)
        );
        if (entityOverride != null) {
            entityOverride.onAdd(event);
        }
    }

}
