package io.github.alejolibrary.entity.entityoverride;

import com.destroystokyo.paper.event.entity.EntityAddToWorldEvent;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EntityOverrideListener implements Listener {

    private final List<UUID> spawnedEntitiesCache = new ArrayList<>();
    private final Plugin plugin;

    public EntityOverrideListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        Entity entity = event.getEntity();
        EntityOverride entityOverride = EntityOverrideRegistry.getEntityOverride(entity);
        if (entityOverride != null) {
            spawnedEntitiesCache.add(entity.getUniqueId());
            entityOverride.onSpawn(event);
            entityOverride.onLoad(event);
        }

    }

    @EventHandler
    public void onEntityAdd(EntityAddToWorldEvent event) {
        Entity entity = event.getEntity();
        if (spawnedEntitiesCache.contains(entity.getUniqueId())) {
            spawnedEntitiesCache.remove(entity.getUniqueId());
            return;
        }
        entity.getScheduler().execute(
                plugin,
                () -> {
                    EntityOverride entityOverride = EntityOverrideRegistry.getEntityOverride(entity);
                    if (entityOverride != null) {
                        entityOverride.onAdd(event);
                        entityOverride.onLoad(event);
                    }
                },
                null
                ,1
        );
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Entity entity = event.getEntity();
        EntityOverride override = EntityOverrideRegistry.getEntityOverride(entity);
        if (override != null) {
            override.onDeath(event);
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        EntityOverride entityOverride = EntityOverrideRegistry.getEntityOverride(entity);
        if (entityOverride != null) {
            entityOverride.onDamage(event);
        }
    }

}
