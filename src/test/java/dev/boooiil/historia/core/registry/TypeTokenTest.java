package dev.boooiil.historia.core.registry;

import org.junit.Test;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import static org.junit.Assert.*;

public class TypeTokenTest {

    @Test
    public void testGetTypeWithSimpleType() {
        TypeToken<String> token = new TypeToken<String>() {
        };
        assertEquals(String.class, token.getType());
    }

    @Test
    public void testGetTypeWithParameterizedType() {
        TypeToken<List<String>> token = new TypeToken<List<String>>() {
        };
        Type type = token.getType();
        assertTrue(type instanceof ParameterizedType);
        ParameterizedType pt = (ParameterizedType) type;
        assertEquals(List.class, pt.getRawType());
        assertEquals(String.class, pt.getActualTypeArguments()[0]);
    }

    @Test
    public void testToString() {
        TypeToken<Integer> token = new TypeToken<Integer>() {
        };
        String str = token.toString();
        assertTrue(str.contains("TypeToken{type="));
        assertTrue(str.contains("java.lang.Integer"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMissingTypeParameterThrows() {
        // Anonymous subclass without type parameter
        class RawTypeToken extends TypeToken {
        }
        new RawTypeToken();
    }
}