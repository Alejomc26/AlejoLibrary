package io.github.alejolibrary.behavior.picker;

import io.github.alejolibrary.behavior.BehaviorManager;
import io.github.alejolibrary.behavior.CustomBehavior;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class SequentialPicker implements BehaviorPicker {

    private final List<Supplier<CustomBehavior>> behaviors = new ArrayList<>();
    private final BehaviorManager manager;
    private int index;

    public SequentialPicker(BehaviorManager manager) {
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
        int size = behaviors.size();
        index++;
        if (index >= size) {
            index = 0;
        }
        if (!behaviors.isEmpty()) {
            CustomBehavior behavior = behaviors.get(index).get();
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
