package io.github.alejolibrary.behavior;

import com.destroystokyo.paper.event.entity.EntityAddToWorldEvent;
import com.destroystokyo.paper.event.entity.EntityRemoveFromWorldEvent;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public class BehaviorListener implements Listener {

    private final Map<UUID, BehaviorManager.BehaviorWrapper> map;
    private final BehaviorManager manager;

    public BehaviorListener(BehaviorManager manager, Map<UUID, BehaviorManager.BehaviorWrapper> map) {
        this.manager = manager;
        this.map = map;
    }

    @EventHandler
    public void entityAddListener(EntityAddToWorldEvent event) {
        Entity entity = event.getEntity();
        CustomBehavior behavior = manager.getWrapper(entity).getBehavior();
        if (behavior != null) {
            manager.setBehavior(entity, behavior);
        }
    }

    @EventHandler
    public void removeListener(EntityRemoveFromWorldEvent event) {
        Entity entity = event.getEntity();
        manager.getWrapper(entity).callRemoveConsumer(event);
    }

    @EventHandler
    public void deathListener(EntityDeathEvent event) {
        Entity entity = event.getEntity();
        manager.getWrapper(entity).callDeathConsumer(event);
        map.remove(entity.getUniqueId());
    }

}
