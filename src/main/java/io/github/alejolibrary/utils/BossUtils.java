package io.github.alejolibrary.utils;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.NumberConversions;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Array;
import java.util.Collection;

public class BossUtils {

    @Nullable
    public static Player getNearestPlayer(Entity entity, double radius) {
        Collection<Entity> nearbyPlayers = entity.getNearbyEntities(radius, radius, radius);
        return (Player) filterNearestEntityByClass(nearbyPlayers, entity.getX(), entity.getY(), entity.getZ(), Player.class);
    }

    @Nullable
    @SafeVarargs
    public static Entity getNearestEntityByClass(Entity entity, double radius, Class<? extends Entity> filter, Class<? extends Entity>... filters) {
        Collection<Entity> nearbyEntities = entity.getNearbyEntities(radius, radius, radius);
        return filterNearestEntityByClass(nearbyEntities, entity.getX(), entity.getY(), entity.getZ(), filter, filters);
    }

    public static void draw(Particle particle, Location start, Vector vector, double gap) {
        Vector clonedVector = vector.clone();
        double length = normalize(clonedVector);
        World world = start.getWorld();

        for (double i = 0; i < length; i += gap) {
            double x = clonedVector.getX() * i;
            double y = clonedVector.getY() * i;
            double z = clonedVector.getZ() * i;
            start.add(x, y, z);
            world.spawnParticle(particle, start, 0, 0, 0, 0, 1);
            start.subtract(x, y, z);
        }

    }

    public static double normalize(Vector vector) {
        double length = vector.length();
        vector.setX(vector.getX() / length);
        vector.setY(vector.getY() / length);
        vector.setZ(vector.getZ() / length);
        return length;
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
            T[] genericArray = (T[]) Array.newInstance(object.getClass(), 1);
            genericArray[0] = object;
            return genericArray;
        }
        if (object == null) {
            return array;
        }

        T[] groupedArray = (T[]) Array.newInstance(array.getClass().getComponentType(), array.length + 1);
        System.arraycopy(array, 0, groupedArray, 0, array.length);
        groupedArray[array.length] = object;

        return groupedArray;
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

    public static double distanceSquare(Entity entity1, Entity entity2) {
        return NumberConversions.square(entity1.getX() - entity2.getX()) +
                NumberConversions.square(entity1.getY() - entity2.getY()) +
                NumberConversions.square(entity1.getZ() - entity2.getZ());
    }

}
