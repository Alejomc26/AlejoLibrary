package io.github.alejolibrary.behavior.picker;

import io.github.alejolibrary.behavior.BehaviorManager;
import io.github.alejolibrary.behavior.CustomBehavior;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class RandomPicker implements BehaviorPicker {

    private final List<Supplier<CustomBehavior>> behaviorList = new ArrayList<>();
    private final Random random = new Random();
    private final BehaviorManager manager;

    public RandomPicker(BehaviorManager manager) {
        this.manager = manager;
    }

    @Override
    public void add(@NotNull Supplier<CustomBehavior> behaviorSupplier, int timesToRepeat) {
        for (int i = 0; i < timesToRepeat; i++) {
            behaviorList.add(behaviorSupplier);
        }
    }



    @Override
    public void pick(@NotNull Entity entity) {
        if (!behaviorList.isEmpty()) {
            int randomIndex = random.nextInt(0, behaviorList.size());
            CustomBehavior behavior = behaviorList.get(randomIndex).get();
            manager.setBehavior(entity, behavior);
        }
    }

    @Override
    public BehaviorManager getBehaviorManager() {
        return manager;
    }

    @Override
    public void clear() {
        behaviorList.clear();
    }

}
