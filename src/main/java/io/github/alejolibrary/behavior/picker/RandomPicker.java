package io.github.alejolibrary.behavior.picker;

import io.github.alejolibrary.behavior.BehaviorManager;
import io.github.alejolibrary.behavior.CustomBehavior;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class RandomPicker implements BehaviorPicker {

    private final List<Supplier<CustomBehavior>> behaviors = new ArrayList<>();
    private final java.util.Random random = new java.util.Random();
    private final BehaviorManager manager;

    public RandomPicker(BehaviorManager manager) {
        this.manager = manager;
    }

    @Override
    public void add(Supplier<CustomBehavior> behaviorSupplier, int timesToRepeat) {
        for (int i = 0; i < timesToRepeat; i++) {
            behaviors.add(behaviorSupplier);
        }
    }

    @Override
    public void pick(Entity entity) {
        if (!behaviors.isEmpty()) {
            int randomIndex = random.nextInt(0,this.behaviors.size());
            CustomBehavior behavior = behaviors.get(randomIndex).get();
            if (behavior == null) {
                return;
            }
            manager.setBehavior(entity, behavior);
        }
    }

    @Override
    public BehaviorManager getBehaviorManager() {
        return manager;
    }

    @Override
    public void clear() {
        behaviors.clear();
    }
}
