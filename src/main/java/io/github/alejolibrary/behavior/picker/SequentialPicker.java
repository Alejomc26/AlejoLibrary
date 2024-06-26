package io.github.alejolibrary.behavior.picker;

import io.github.alejolibrary.behavior.BehaviorManager;
import io.github.alejolibrary.behavior.CustomBehavior;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class SequentialPicker implements BehaviorPicker {

    private final List<CustomBehavior> behaviorList = new ArrayList<>();
    private final BehaviorManager manager;
    private int index;

    public SequentialPicker(BehaviorManager manager) {
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
        int size = behaviorList.size();
        index++;
        if (index >= size) {
            index = 0;
        }
        if (!behaviorList.isEmpty()) {
            CustomBehavior behavior = behaviorList.get(index);
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
