package io.github.alejolibrary.hitbox;

import org.bukkit.Bukkit;
import org.bukkit.entity.Interaction;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public class Hitbox {

    private static final Map<UUID, Hitbox> HITBOX_MAP = new HashMap<>();
    private static boolean isListenerRegistered = false;
    private Consumer<PlayerInteractEntityEvent> rightClick;
    private Consumer<EntityDamageEvent> leftClick;

    public Hitbox(Plugin plugin, Interaction interaction) {
        this(plugin, interaction, event -> {}, event -> {});
    }

    public Hitbox(Plugin plugin, Interaction interaction, Consumer<PlayerInteractEntityEvent> rightClick, Consumer<EntityDamageEvent> leftClick) {
        HITBOX_MAP.put(interaction.getUniqueId(), this);
        if (!isListenerRegistered) {
            Bukkit.getPluginManager().registerEvents(new HitboxListener(), plugin);
            isListenerRegistered = true;
        }
        this.rightClick = rightClick;
        this.leftClick = leftClick;
    }

    public void setRightClick(Consumer<PlayerInteractEntityEvent> consumer) {
        this.rightClick = consumer;
    }

    public void setLeftClick(Consumer<EntityDamageEvent> consumer) {
        this.leftClick = consumer;
    }

    public void callRightClick(PlayerInteractEntityEvent event) {
        if (this.rightClick != null) {
            this.rightClick.accept(event);
        }
    }

    public void callLeftClick(EntityDamageEvent event) {
        if (this.leftClick != null) {
            leftClick.accept(event);
        }
    }

    @Nullable
    public static Hitbox getHitbox(@NotNull Interaction interaction) {
        return HITBOX_MAP.get(interaction.getUniqueId());
    }

}
