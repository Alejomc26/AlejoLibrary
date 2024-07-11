package io.github.alejolibrary.behavior.picker;

import io.github.alejolibrary.behavior.BehaviorManager;
import io.github.alejolibrary.behavior.CustomBehavior;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class SequentialPicker implements BehaviorPicker {

    private final List<Supplier<CustomBehavior>> behaviorList = new ArrayList<>();
    private final BehaviorManager manager;
    private int index = 0;

    public SequentialPicker(BehaviorManager manager) {
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
        int size = behaviorList.size();
        if (behaviorList.isEmpty()) {
            return;
        }
        if (index < size) {
            CustomBehavior behavior = behaviorList.get(index).get();
            manager.setBehavior(entity, behavior);
            index++;
        } else {
            index = 0;
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
