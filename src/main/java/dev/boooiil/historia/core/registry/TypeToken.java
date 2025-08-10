package dev.boooiil.historia.core.registry;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class TypeToken<T> {
    private final Type type;

    protected TypeToken() {
        Type superclass = getClass().getGenericSuperclass();
        if (superclass instanceof ParameterizedType) {
            this.type = ((ParameterizedType) superclass).getActualTypeArguments()[0];
        } else {
            throw new IllegalArgumentException("Missing type parameter for TypeToken");
        }
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return "TypeToken{" + "type=" + type + '}';
    }
}
