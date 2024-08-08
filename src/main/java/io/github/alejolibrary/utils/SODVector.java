package io.github.alejolibrary.utils;

import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SODVector {

    private Vector currentVectorRate;
    private Vector previousVector;
    private Vector currentVector;
    private final float k1;
    private final float k2;
    private final float k3;

    public SODVector(float naturalFrequency, float dampingRatio, float resonantFrequency) {
        k1 = (float) (dampingRatio / (Math.PI * naturalFrequency));
        k2 = (float) (1 / ((2 * Math.PI * naturalFrequency) * (2 * Math.PI * naturalFrequency)));
        k3 = (float) (resonantFrequency * dampingRatio / (2 * Math.PI * naturalFrequency));
    }

    public void setInitialLocation(Vector vector) {
        currentVectorRate = new Vector();
        previousVector = vector.clone();
        currentVector = vector.clone();
    }

    public Vector update(float time, @NotNull Vector newVector, @Nullable Vector rateOfChange) {
        if (rateOfChange == null) {
            rateOfChange = getRateOfChange(time, newVector);
        }
        currentVector.add(currentVectorRate.multiply(time));
        updateRate(time, newVector, rateOfChange);
        return currentVector;
    }

    private void updateRate(float time, Vector newVector, Vector rateOfChange) {
        double rateX = currentVectorRate.getX();
        double rateY = currentVectorRate.getY();
        double rateZ = currentVectorRate.getZ();
        double x = rateX + time * (newVector.getX() + k3*rateOfChange.getX() - currentVector.getX() - k1* rateX) / k2;
        double y = rateY + time * (newVector.getY() + k3*rateOfChange.getY() - currentVector.getY() - k1* rateY) / k2;
        double z = rateZ + time * (newVector.getZ() + k3*rateOfChange.getZ() - currentVector.getZ() - k1* rateZ) / k2;
        currentVectorRate.setX(x);
        currentVectorRate.setY(y);
        currentVectorRate.setZ(z);
    }

    public Vector getRateOfChange(float time, Vector newLocation) {
        double x = (newLocation.getX() - previousVector.getX()) / time;
        double y = (newLocation.getY() - previousVector.getY()) / time;
        double z = (newLocation.getZ() - previousVector.getZ()) / time;
        return new Vector(x, y, z);
    }
}
