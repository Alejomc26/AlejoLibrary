package io.github.alejolibrary.utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Golem;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Player;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.NumberConversions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;

public class BossUtils {


    public static <T extends Entity> T getNearestEntityByClass(Entity entity, double radius, Class<T> filter) {
        Collection<Entity> nearbyEntities = entity.getNearbyEntities(radius, radius, radius);
        return filterNearestEntityByClass(nearbyEntities, entity.getX(), entity.getY(), entity.getZ(), filter);
    }

    public static <T extends Entity> T getNearestEntityByClass(World world, Location location, double radius, Class<T> filter) {
        Collection<Entity> nearbyEntities = world.getNearbyEntities(location, radius, radius, radius);
        return filterNearestEntityByClass(nearbyEntities, location.x(), location.y(), location.z(), filter);
    }

    @SafeVarargs
    public static Entity getNearestEntityByClass(Entity entity, double radius, Class<? extends Entity> filter, Class<? extends Entity>... filters) {
        Collection<Entity> nearbyEntities = entity.getNearbyEntities(radius, radius, radius);
        return filterNearestEntityByClass(nearbyEntities, entity.getX(), entity.getY(), entity.getZ(), filter, filters);
    }

    @SafeVarargs
    public static Entity getNearestEntityByClass(World world, Location location, double radius, Class<? extends Entity> filter, Class<? extends Entity>... filters) {

        Collection<Entity> nearbyEntities = world.getNearbyEntities(location, radius, radius, radius);
        return filterNearestEntityByClass(nearbyEntities, location.x(), location.y(), location.z(), filter, filters);
    }

    /**
     *
     * Filter a collection of entities to get the nearest entity to a location
     * that matches a specific entity class.
     *
     * @param entities Collection to search.
     * @param filter Entity class to search.
     * @return Nearest entity from the collection that matches the passed entity class, or null if none was found.
     * @param <T> Entity type.
     */
    @Nullable
    public static <T extends Entity> T filterNearestEntityByClass(Collection<Entity> entities, double x, double y, double z, @NotNull Class<T> filter) {
        return (T) filterNearestEntityByClass(entities, x, y, z, filter, null);
    }

    /**
     * Filter a collection of entities to get the nearest entity to a location
     * that matches any of the passed entity classes.
     *
     * @param entities Collection to search.
     * @param filter Entity class to search.
     * @param filters Classes of entities to include in the search.
     * @return Nearest entity from the collection that matches any of the passed entity classes, or null if none was found.
     */
    @SafeVarargs
    @Nullable
    public static Entity filterNearestEntityByClass(@NotNull Collection<Entity> entities, double x, double y, double z,
                                                    @NotNull Class<? extends Entity> filter, @Nullable Class<? extends Entity>... filters) {
        Class<? extends Entity>[] groupedFilters = groupArrayWithObject(filter, filters);
        if (groupedFilters == null) {
            return null;
        }

        double nearestDistanceSquare = Double.MAX_VALUE;
        Entity nearestEntity = null;

        if (entities.isEmpty()) {
            return null;
        }

        for (Entity entity : entities) {
            double distanceSquare = NumberConversions.square(x - entity.getX()) +
                    NumberConversions.square(y - entity.getY()) +
                    NumberConversions.square(z - entity.getZ());
            if (distanceSquare >= nearestDistanceSquare) {
                continue;
            }
            if (isEntityFromClass(entity, groupedFilters)) {
                nearestDistanceSquare = distanceSquare;
                nearestEntity = entity;
            }
        }
        return nearestEntity;
    }

    /**
     * Groups an array and an object of the same type into a singular array.
     * @param object Object to add to the array.
     * @param array Array to add the object.
     * @return Array with the object added, if the array is null returns an array with the object,
     * if the object is null returns the same array, if both are null returns null.
     * @param <T> Array type.
     */
    @Nullable
    public static <T> T[] groupArrayWithObject(@Nullable T object, @Nullable T[] array) {
        if (array == null && object == null) {
            return null;
        }
        if (array == null) {
            return (T[]) new Object[] {object};
        }
        if (object == null) {
            return array;
        }

        int groupedArrayLength = array.length + 1;
        Object[] groupedArray = new Object[groupedArrayLength];

        System.arraycopy(array, 0, groupedArray, 0, array.length);
        groupedArray[groupedArrayLength - 1] = object;

        return (T[]) groupedArray;
    }

    /**
     * Filter a collection of entities by their specific type.
     * @param entities Entities to filter.
     * @param filters Classes of entities to include.
     * @return Filtered collection of entities.
     */
    @SafeVarargs
    public static Collection<Entity> filterEntitiesByClass(@NotNull Collection<Entity> entities, @NotNull Class<? extends Entity>... filters) {
        Collection<Entity> filteredEntities = new ArrayList<>();
        for (Entity entity : entities) {
            if (isEntityFromClass(entity, filters)) {
                filteredEntities.add(entity);
            }
        }
        return filteredEntities;
    }

    /**
     * Checks if an entity is from any of the passed entity classes.
     */
    @SafeVarargs
    public static boolean isEntityFromClass(@NotNull Entity entity, @NotNull Class<? extends Entity>... classes) {
        for (Class<? extends Entity> filter : classes) {
            if (filter.isAssignableFrom(entity.getClass())) {
                return true;
            }
        }
        return false;
    }


    /**
     * Filter a collection of entities to get the nearest to a location.
     * @param entities Entities to filter.
     * @param location Center location.
     * @return Nearest entity from the passed collection to the center, or null if the passed collection was empty.
     */
    @Nullable
    public static Entity filterNearestEntity(@NotNull Collection<Entity> entities, @NotNull Location location) {
        double nearestDistanceSquare = Double.MAX_VALUE;
        Entity nearestEntity = null;

        if (entities.isEmpty()) {
            return null;
        }

        for (Entity entity : entities) {
            double distanceSquare = distanceSquare(entity, location);
            if (distanceSquare >= nearestDistanceSquare) {
                continue;
            }
            nearestDistanceSquare = distanceSquare;
            nearestEntity = entity;
        }
        return nearestEntity;
    }

    public static double distanceSquare(Entity entity1, Entity entity2) {
        return NumberConversions.square(entity1.getX() - entity2.getX()) +
                NumberConversions.square(entity1.getY() - entity2.getY()) +
                NumberConversions.square(entity1.getZ() - entity2.getZ());
    }

    public static double distanceSquare(Entity entity, Location location) {
        return NumberConversions.square(entity.getX() - location.getX()) +
                NumberConversions.square(entity.getY() - location.getY()) +
                NumberConversions.square(entity.getZ() - location.getZ());
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
