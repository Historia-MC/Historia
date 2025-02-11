package dev.boooiil.historia.core.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver.Single;

public class KyoriUtils {

    public static String replace(String text, String placeholder, String replacement) {

        Component c = MiniMessage.miniMessage().deserialize(text, Placeholder.component(placeholder,
                Component.text(replacement)));

        TextComponent tc = (TextComponent) c;

        return tc.content();

    }

    public static String replace(String text, String placeholder, int replacement) {

        Component c = MiniMessage.miniMessage().deserialize(text, Placeholder.component(placeholder,
                Component.text(replacement)));

        TextComponent tc = (TextComponent) c;

        return tc.content();

    }

    public static String replace(String text, String placeholder, float replacement) {

        Component c = MiniMessage.miniMessage().deserialize(text, Placeholder.component(placeholder,
                Component.text(replacement)));

        TextComponent tc = (TextComponent) c;

        return tc.content();

    }

    public static String replace(String text, String placeholder, double replacement) {

        Component c = MiniMessage.miniMessage().deserialize(text, Placeholder.component(placeholder,
                Component.text(replacement)));

        TextComponent tc = (TextComponent) c;

        return tc.content();

    }

    public static String replace(String text, String placeholder, long replacement) {

        Component c = MiniMessage.miniMessage().deserialize(text, Placeholder.component(placeholder,
                Component.text(replacement)));

        TextComponent tc = (TextComponent) c;

        return tc.content();

    }

    public static String replace(String text, String placeholder, boolean replacement) {

        Component c = MiniMessage.miniMessage().deserialize(text, Placeholder.component(placeholder,
                Component.text(replacement)));

        TextComponent tc = (TextComponent) c;

        return tc.content();

    }

    public static String replace(Component component, String placeholder, String replacement) {

        if (!(component instanceof TextComponent)) {
            return "";
        }

        Component c = MiniMessage.miniMessage().deserialize(((TextComponent) component).content(),
                Placeholder.component(placeholder, Component.text(replacement)));

        TextComponent tc = (TextComponent) c;

        return tc.content();

    }

    public static String replace(Component component, String placeholder, int replacement) {

        if (!(component instanceof TextComponent)) {
            return "";
        }

        Component c = MiniMessage.miniMessage().deserialize(((TextComponent) component).content(),
                Placeholder.component(placeholder, Component.text(replacement)));

        TextComponent tc = (TextComponent) c;

        return tc.content();

    }

    public static String replace(Component component, String placeholder, float replacement) {

        if (!(component instanceof TextComponent)) {
            return "";
        }

        Component c = MiniMessage.miniMessage().deserialize(((TextComponent) component).content(),
                Placeholder.component(placeholder, Component.text(replacement)));

        TextComponent tc = (TextComponent) c;

        return tc.content();

    }

    public static String replace(Component component, String placeholder, double replacement) {

        if (!(component instanceof TextComponent)) {
            return "";
        }

        Component c = MiniMessage.miniMessage().deserialize(((TextComponent) component).content(),
                Placeholder.component(placeholder, Component.text(replacement)));

        TextComponent tc = (TextComponent) c;

        return tc.content();

    }

    public static String replace(Component component, String placeholder, long replacement) {

        if (!(component instanceof TextComponent)) {
            return "";
        }

        Component c = MiniMessage.miniMessage().deserialize(((TextComponent) component).content(),
                Placeholder.component(placeholder, Component.text(replacement)));

        TextComponent tc = (TextComponent) c;

        return tc.content();

    }

    public static String replace(Component component, String placeholder, boolean replacement) {

        if (!(component instanceof TextComponent)) {
            return "";
        }

        Component c = MiniMessage.miniMessage().deserialize(((TextComponent) component).content(),
                Placeholder.component(placeholder, Component.text(replacement)));

        TextComponent tc = (TextComponent) c;

        return tc.content();

    }

    public static String replace(Component component, Single placeholder) {

        if (!(component instanceof TextComponent)) {
            return "";
        }

        Component c = MiniMessage.miniMessage().deserialize(((TextComponent) component).content(),
                placeholder);

        TextComponent tc = (TextComponent) c;

        return tc.content();
    }

    public static Component replaceComponent(String text, String placeholder, String replacement) {
        return MiniMessage.miniMessage().deserialize(text, Placeholder.component(placeholder,
                Component.text(replacement)));
    }

    public static Component replaceComponent(String text, String placeholder, int replacement) {
        return MiniMessage.miniMessage().deserialize(text, Placeholder.component(placeholder,
                Component.text(replacement)));
    }

    public static Component replaceComponent(String text, String placeholder, float replacement) {
        return MiniMessage.miniMessage().deserialize(text, Placeholder.component(placeholder,
                Component.text(replacement)));
    }

    public static Component replaceComponent(String text, String placeholder, double replacement) {
        return MiniMessage.miniMessage().deserialize(text, Placeholder.component(placeholder,
                Component.text(replacement)));
    }

    public static Component replaceComponent(String text, String placeholder, long replacement) {
        return MiniMessage.miniMessage().deserialize(text, Placeholder.component(placeholder,
                Component.text(replacement)));
    }

    public static Component replaceComponent(String text, String placeholder, boolean replacement) {
        return MiniMessage.miniMessage().deserialize(text, Placeholder.component(placeholder,
                Component.text(replacement)));
    }

    public static Component replaceComponent(Component component, String placeholder, String replacement) {

        if (!(component instanceof TextComponent)) {
            return component;
        }

        Component c = MiniMessage.miniMessage().deserialize(((TextComponent) component).content(),
                Placeholder.component(placeholder, Component.text(replacement)));

        return c;
    }

    public static Component replaceComponent(Component component, String placeholder, int replacement) {

        if (!(component instanceof TextComponent)) {
            return component;
        }

        Component c = MiniMessage.miniMessage().deserialize(((TextComponent) component).content(),
                Placeholder.component(placeholder, Component.text(replacement)));

        return c;
    }

    public static Component replaceComponent(Component component, String placeholder, float replacement) {

        if (!(component instanceof TextComponent)) {
            return component;
        }

        Component c = MiniMessage.miniMessage().deserialize(((TextComponent) component).content(),
                Placeholder.component(placeholder, Component.text(replacement)));

        return c;
    }

    public static Component replaceComponent(Component component, String placeholder, double replacement) {

        if (!(component instanceof TextComponent)) {
            return component;
        }

        Component c = MiniMessage.miniMessage().deserialize(((TextComponent) component).content(),
                Placeholder.component(placeholder, Component.text(replacement)));

        return c;
    }

    public static Component replaceComponent(Component component, String placeholder, long replacement) {

        if (!(component instanceof TextComponent)) {
            return component;
        }

        Component c = MiniMessage.miniMessage().deserialize(((TextComponent) component).content(),
                Placeholder.component(placeholder, Component.text(replacement)));

        return c;
    }

    public static Component replaceComponent(Component component, String placeholder, boolean replacement) {

        if (!(component instanceof TextComponent)) {
            return component;
        }

        Component c = MiniMessage.miniMessage().deserialize(((TextComponent) component).content(),
                Placeholder.component(placeholder, Component.text(replacement)));

        return c;
    }

    public static Component replaceComponent(Component component, Single placeholder) {

        if (!(component instanceof TextComponent)) {
            return component;
        }

        Component c = MiniMessage.miniMessage().deserialize(((TextComponent) component).content(),
                placeholder);

        return c;
    }

    public static Component textComponent(String text) {
        return Component.text(text);
    }

    public static String content(Component text) {
        if (text instanceof TextComponent) {
            return ((TextComponent) text).content();
        }

        return "";
    }

    public static boolean contains(Component component, String search) {
        if (component instanceof TextComponent) {
            return ((TextComponent) component).content().contains(search);
        }

        return false;
    }

}
