package io.github.alejolibrary.hitbox;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Interaction;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class HitboxListener implements Listener {

    @EventHandler
    public void leftListener(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Interaction interaction) {
            Hitbox hitbox = Hitbox.getHitbox(interaction);
            if (hitbox != null) {
                hitbox.callLeftClick(event);
            }
        }
    }

    @EventHandler
    public void rightListener(PlayerInteractEntityEvent event) {
        Entity entity = event.getRightClicked();
        if (entity instanceof Interaction interaction) {
            Hitbox hitbox = Hitbox.getHitbox(interaction);
            if (hitbox != null) {
                hitbox.callRightClick(event);
            }
        }
    }

}
