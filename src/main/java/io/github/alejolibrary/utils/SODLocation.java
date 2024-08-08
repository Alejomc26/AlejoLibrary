package io.github.alejolibrary.utils;

import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Apply second order dynamics to {@link org.bukkit.Location}
 */
public class SODLocation {

    private Location currentLocationRate;
    private Location previousLocation;
    private Location currentLocation;
    private boolean isInitialized;
    private final float k1;
    private final float k2;
    private final float k3;

    public SODLocation(float naturalFrequency, float dampingRatio, float resonantFrequency) {
        k1 = (float) (dampingRatio / (Math.PI * naturalFrequency));
        k2 = (float) (1 / ((2 * Math.PI * naturalFrequency) * (2 * Math.PI * naturalFrequency)));
        k3 = (float) (resonantFrequency * dampingRatio / (2 * Math.PI * naturalFrequency));
    }

    public void setInitialLocation(Location location) {
        currentLocationRate = new Location(location.getWorld(), 0, 0, 0);
        previousLocation = location.clone();
        currentLocation = location.clone();
        isInitialized = true;
    }

    public boolean isInitialized() {
        return isInitialized;
    }

    public Location update(float time, @NotNull Location newLocation, @Nullable Location rateOfChange) {
        if (rateOfChange == null) {
            rateOfChange = getRateOfChange(time, newLocation);
        }
        currentLocation.add(currentLocationRate.multiply(time));
        updateRate(time, newLocation, rateOfChange);
        return currentLocation;
    }

    private void updateRate(float time, Location newLocation, Location rateOfChange) {
        double x = currentLocationRate.x() + time * (newLocation.x() + k3*rateOfChange.x() - currentLocation.x() - k1*currentLocationRate.x()) / k2;
        double y = currentLocationRate.y() + time * (newLocation.y() + k3*rateOfChange.y() - currentLocation.y() - k1*currentLocationRate.y()) / k2;
        double z = currentLocationRate.z() + time * (newLocation.z() + k3*rateOfChange.z() - currentLocation.z() - k1*currentLocationRate.z()) / k2;
        currentLocationRate.set(x, y, z);
    }

    private Location getRateOfChange(float time, Location newLocation) {
        double x = (newLocation.x() - previousLocation.x()) / time;
        double y = (newLocation.y() - previousLocation.y()) / time;
        double z = (newLocation.z() - previousLocation.z()) / time;
        return new Location(newLocation.getWorld(), x, y, z);
    }

}
