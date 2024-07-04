package io.github.alejolibrary.entity.entityoverride;

import com.destroystokyo.paper.event.entity.EntityAddToWorldEvent;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class EntityOverrideListener implements Listener {

    private final Map<UUID, EntityOverride> activeOverridesMap = new ConcurrentHashMap<>();

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        Entity entity = event.getEntity();
        EntityOverride entityOverride = EntityOverrideRegistry.getEntityOverride(entity);
        if (entityOverride != null) {
            activeOverridesMap.put(entity.getUniqueId(), entityOverride);
            entityOverride.onSpawn(event);
            entityOverride.onLoad(event);
        }

    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        UUID uuid = event.getEntity().getUniqueId();
        EntityOverride override = activeOverridesMap.remove(uuid);
        if (override != null) {
            override.onDeath(event);
        }
    }

    @EventHandler
    public void onEntityAdd(EntityAddToWorldEvent event) {
        Entity entity = event.getEntity();
        EntityOverride entityOverride = EntityOverrideRegistry.getEntityOverride(entity);
        if (entityOverride != null) {
            activeOverridesMap.put(entity.getUniqueId(), entityOverride);
            entityOverride.onAdd(event);
            entityOverride.onLoad(event);
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        EntityOverride entityOverride = EntityOverrideRegistry.getEntityOverride(entity);
        if (entityOverride != null) {
            activeOverridesMap.put(entity.getUniqueId(), entityOverride);
            entityOverride.onDamage(event);
        }
    }

}
