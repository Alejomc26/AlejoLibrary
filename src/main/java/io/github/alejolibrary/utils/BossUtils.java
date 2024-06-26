package io.github.alejolibrary.utils;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class BossUtils {

    private BossUtils() {
        System.out.println("ga");
    }

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

    public static List<Block> getNearbyBlocks(Location location, int radius) {
        List<Block> blocks = new ArrayList<>();
        World world = location.getWorld();

        for (int x = location.getBlockX() - radius; x <= location.getBlockX(); x++) {
            for (int y = location.getBlockY() - radius; y <= location.getBlockY(); y++) {
                for (int z = location.getBlockZ() - radius; z <= location.getBlockZ(); z++) {
                    blocks.add(world.getBlockAt(x, y, z));
                }
            }
        }
        return blocks;
    }

    public static Location lerpLocation(Location start, Location end, float lerp) {
        return start.clone().add(end.clone().subtract(start).multiply(lerp));
    }

    @SafeVarargs
    public static <T> T randomizeObject(T... objects) {
        if (objects.length == 0) {
            throw new IllegalArgumentException("Error while randomizing objects");
        }
        Random random = new Random();
        return objects[random.nextInt(0, objects.length)];
    }

}
