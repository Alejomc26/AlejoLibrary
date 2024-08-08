package io.github.alejolibrary.behavior.picker;

import io.github.alejolibrary.behavior.BehaviorManager;
import io.github.alejolibrary.behavior.CustomBehavior;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class RandomPicker<T extends Entity> implements BehaviorPicker<T> {

    private final List<Supplier<CustomBehavior<? super T>>> behaviorList = new ArrayList<>();
    private final Random random = new Random();
    private final BehaviorManager manager;
    private final boolean avoidRepeating;
    private int previousIndex = 0;

    public RandomPicker(BehaviorManager manager) {
        this(manager, true);
    }

    public RandomPicker(BehaviorManager manager, boolean avoidRepeating) {
        this.avoidRepeating = avoidRepeating;
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
        int randomIndex;
        if (avoidRepeating && behaviorList.size() > 1) {
            do {
                randomIndex = random.nextInt(behaviorList.size());
            } while (randomIndex == previousIndex);
        } else {
            randomIndex = random.nextInt(behaviorList.size());
        }
        previousIndex = randomIndex;
        return behaviorList.get(randomIndex).get();
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
