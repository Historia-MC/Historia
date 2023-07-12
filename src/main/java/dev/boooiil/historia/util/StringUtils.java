package dev.boooiil.historia.util;

import java.nio.charset.StandardCharsets;
import java.util.HexFormat;

import org.bukkit.ChatColor;

/**
 * It takes a variable amount of strings and returns them as one string
 */
public class StringUtils {

    /**
     * It takes a variable number of strings and returns a single string
     * 
     * @return The method is returning a string.
     */
    public static String build(String... args) {

        String build = "";

        for (String string : args) {

            build += string;

        }

        return build;

    }

    public static String hideText(String text) {

        StringBuilder output = new StringBuilder();
        HexFormat hex = HexFormat.of();

        byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
        String conv = "";

        for (byte b : bytes) {

            conv += hex.toHexDigits(b);

        }

        for (char c : conv.toCharArray()) {
            output.append(ChatColor.COLOR_CHAR).append(c);
        }

        return output.toString();
    }
}
