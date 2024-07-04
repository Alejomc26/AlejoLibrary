package io.github.alejolibrary.utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.NumberConversions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.Collection;

public class BossUtils {

    private BossUtils() {}

    @Nullable
    public static Player getNearestPlayer(@NotNull Location location) {
        return getNearestPlayer(location.getWorld().getPlayers(), location.x(), location.y(), location.z());
    }

    @Nullable
    public static Player getNearestPlayer(Entity entity) {
        return getNearestPlayer(entity.getWorld().getPlayers(), entity.getX(), entity.getY(), entity.getZ());

    }

    @Nullable
    private static Player getNearestPlayer(Collection<Player> players, double x, double y, double z) {
        Player nearestPlayer = null;
        double nearestDistanceSquare = Double.MAX_VALUE;
        if (players.isEmpty()) {
            return null;
        }
        for (Player player : players) {
            double distanceSquare = NumberConversions.square(x - player.getX()) +
                    NumberConversions.square(y - player.getY()) +
                    NumberConversions.square(z - player.getZ());
            if (distanceSquare >= nearestDistanceSquare) {
                continue;
            }
            nearestDistanceSquare = distanceSquare;
            nearestPlayer = player;
        }

        return nearestPlayer;
    }

    public static boolean isEntityInsideBlocks(Entity entity) {
        BoundingBox entityBB = entity.getBoundingBox().expand(0.2);
        World world = entity.getWorld();
        return isEntityInsideBlocks(entityBB, world);
    }

    public static boolean isEntityInsideBlocks(BoundingBox entityBB, World entityWorld) {
        for (int x = (int) entityBB.getMinX(); x <= entityBB.getMaxX(); x++) {
            for (int y = (int) entityBB.getMinY(); y <= entityBB.getMaxY(); y++) {
                for (int z = (int) entityBB.getMinZ(); z <= entityBB.getMaxZ(); z++) {
                    Block block = entityWorld.getBlockAt(x, y, z);
                    if (block.getType() != Material.AIR) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
