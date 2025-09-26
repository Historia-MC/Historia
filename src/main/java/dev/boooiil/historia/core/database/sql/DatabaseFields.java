package dev.boooiil.historia.core.database.sql;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

@NullMarked
public final class DatabaseFields<T, UK> {

    private final String name;
    private final @Nullable BiConsumer<T, UK> updater;
    private final @Nullable Function<UK, @Nullable T> getter;
    private final @Nullable Consumer<@Nullable Map<String, Object>> inserter;

    private DatabaseFields(String name, @Nullable BiConsumer<T, UK> updater, @Nullable Function<UK, @Nullable T> getter, @Nullable Consumer<@Nullable Map<String, Object>> inserter) {
        this.name = name;
        this.updater = updater;
        this.getter = getter;
        this.inserter = inserter;
    }

    public static <T, UK> DatabaseFields<T, UK> of(String name, @Nullable BiConsumer<T, UK> updater, @Nullable Function<UK, @Nullable T> getter, @Nullable Consumer<@Nullable Map<String, Object>> inserter) {
        return new DatabaseFields<>(name, updater, getter, inserter);
    }

    public String getName() {
        return name;
    }

    public void update(T value, UK uniqueKey) {
        if (updater == null) {
            throw new UnsupportedOperationException(name + " cannot be written.");
        }
        updater.accept(value, uniqueKey);
    }

    @Nullable
    public T get(UK uniqueKey) {
        if (getter == null) {
            throw new UnsupportedOperationException(name + " cannot be read.");
        }
        return getter.apply(uniqueKey);
    }

    public void insert(@Nullable Map<String, Object> values) {
        if (inserter == null) {
            throw new UnsupportedOperationException(name + " cannot be inserted.");
        }
        inserter.accept(values);

    }
}


