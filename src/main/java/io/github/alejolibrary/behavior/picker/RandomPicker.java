package io.github.alejolibrary.behavior.picker;

import io.github.alejolibrary.behavior.BehaviorManager;
import io.github.alejolibrary.behavior.CustomBehavior;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomPicker implements BehaviorPicker {

    private final List<CustomBehavior> behaviorList = new ArrayList<>();
    private final Random random = new Random();
    private final BehaviorManager manager;

    public RandomPicker(BehaviorManager manager) {
        this.manager = manager;
    }

    @Override
    public void add(CustomBehavior behavior, int timesToRepeat) {
        for (int i = 0; i < timesToRepeat; i++) {
            behaviorList.add(behavior);
        }
    }



    @Override
    public void pick(Entity entity) {
        if (!behaviorList.isEmpty()) {
            int randomIndex = random.nextInt(0, behaviorList.size());
            CustomBehavior behavior = behaviorList.get(randomIndex);
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
