package io.github.alejolibrary.behavior.picker;

import io.github.alejolibrary.behavior.BehaviorManager;
import io.github.alejolibrary.behavior.CustomBehavior;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class SequentialPicker<T extends Entity> implements BehaviorPicker<T> {

    private final List<Supplier<CustomBehavior<? super T>>> behaviorList = new ArrayList<>();
    private final BehaviorManager manager;
    private int index = 0;

    public SequentialPicker(BehaviorManager manager) {
        this.manager = manager;
    }

    @Override
    public void add(@NotNull Supplier<CustomBehavior<? super T>> behaviorSupplier, int timesToRepeat) {
        for (int i = 0; i < timesToRepeat; i++) {
            behaviorList.add(behaviorSupplier);
        }
    }

    @Override
    public void pick(@NotNull T entity) {
        CustomBehavior<? super T> behavior = get();
        if (behavior != null) {
            manager.setBehavior(entity, behavior);
        }
    }

    @Override
    public CustomBehavior<? super T> get() {
        if (behaviorList.isEmpty()) {
            return null;
        }
        if (index < behaviorList.size()) {
            CustomBehavior<? super T> behavior = behaviorList.get(index).get();
            index++;
            return behavior;
        } else {
            index = 0;
        }
        return null;
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
