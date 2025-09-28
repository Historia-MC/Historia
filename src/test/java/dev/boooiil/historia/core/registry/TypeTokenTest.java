package dev.boooiil.historia.core.registry;

import dev.boooiil.historia.core.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class TypeTokenTest extends BaseTest {

    @Test
    public void testGetTypeWithSimpleType() {
        TypeToken<String> token = new TypeToken<String>() {
        };
        Assertions.assertEquals(String.class, token.getType());
    }

    @Test
    public void testGetTypeWithParameterizedType() {
        TypeToken<List<String>> token = new TypeToken<List<String>>() {
        };
        Type type = token.getType();
        Assertions.assertInstanceOf(ParameterizedType.class, type);
        ParameterizedType pt = (ParameterizedType) type;
        Assertions.assertEquals(List.class, pt.getRawType());
        Assertions.assertEquals(String.class, pt.getActualTypeArguments()[0]);
    }

    @Test
    public void testToString() {
        TypeToken<Integer> token = new TypeToken<Integer>() {
        };
        String str = token.toString();
        Assertions.assertTrue(str.contains("TypeToken{type="));
        Assertions.assertTrue(str.contains("java.lang.Integer"));
    }

    @Test
    public void testMissingTypeParameterThrows() {
        // Anonymous subclass without type parameter
        class RawTypeToken extends TypeToken {
        }
        new RawTypeToken();
    }
}