package io.github.alejolibrary.utils;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.Collection;

public class BossUtils {

    private BossUtils() {}

    @Nullable
    public static Player getNearestPlayer(@Nullable Location location) {
        if (location == null) {
            return null;
        }
        return getNearestPlayer(location.getWorld().getPlayers(), location);
    }


    public static Player getNearestPlayer(@Nullable Location location, double radius) {
        if (location == null) {
            return null;
        }
        return getNearestPlayer(location.getNearbyPlayers(radius), location);
    }

    public static Player getNearestPlayer(@NotNull Collection<Player> players, Location location) {
        Player nearestPlayer = null;
        double nearestDistanceSquare = Double.MAX_VALUE;
        if (players.isEmpty()) {
            return null;
        }
        for (Player player : players) {
            double distanceSquare = player.getLocation().distanceSquared(location);
            if (distanceSquare >= nearestDistanceSquare) {
                continue;
            }
            nearestDistanceSquare = distanceSquare;
            nearestPlayer = player;
        }

        return nearestPlayer;
    }

}
