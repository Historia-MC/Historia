package dev.boooiil.historia.core.proficiency.skills;

public class SkillSupplier<T> {
    private final T data;

    public SkillSupplier(T data) {
        this.data = data;
    }

    public T get() {
        return data;
    }
}
