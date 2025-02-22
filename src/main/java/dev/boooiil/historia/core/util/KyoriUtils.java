package dev.boooiil.historia.core.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver.Single;

public class KyoriUtils {

    /**
     * Replace a given string in a string.
     * 
     * @param text        - The text to search in.
     * @param placeholder - The placeholder to find.
     * @param replacement - The replacement.
     * @return - string
     */
    public static String replace(String text, String placeholder, String replacement) {

        Component c = MiniMessage.miniMessage().deserialize(text, Placeholder.component(placeholder,
                Component.text(replacement)));

        TextComponent tc = (TextComponent) c;

        return tc.content();

    }

    /**
     * Replace a given string in a string.
     * 
     * @param text        - The text to search in.
     * @param placeholder - The placeholder to find.
     * @param replacement - The replacement.
     * @return - string
     */
    public static String replace(String text, String placeholder, int replacement) {

        Component c = MiniMessage.miniMessage().deserialize(text, Placeholder.component(placeholder,
                Component.text(replacement)));

        TextComponent tc = (TextComponent) c;

        return tc.content();

    }

    /**
     * Replace a given string in a string.
     * 
     * @param text        - The text to search in.
     * @param placeholder - The placeholder to find.
     * @param replacement - The replacement.
     * @return - string
     */
    public static String replace(String text, String placeholder, float replacement) {

        Component c = MiniMessage.miniMessage().deserialize(text, Placeholder.component(placeholder,
                Component.text(replacement)));

        TextComponent tc = (TextComponent) c;

        return tc.content();

    }

    /**
     * Replace a given string in a string.
     * 
     * @param text        - The text to search in.
     * @param placeholder - The placeholder to find.
     * @param replacement - The replacement.
     * @return - string
     */
    public static String replace(String text, String placeholder, double replacement) {

        Component c = MiniMessage.miniMessage().deserialize(text, Placeholder.component(placeholder,
                Component.text(replacement)));

        TextComponent tc = (TextComponent) c;

        return tc.content();

    }

    /**
     * Replace a given string in a string.
     * 
     * @param text        - The text to search in.
     * @param placeholder - The placeholder to find.
     * @param replacement - The replacement.
     * @return - string
     */
    public static String replace(String text, String placeholder, long replacement) {

        Component c = MiniMessage.miniMessage().deserialize(text, Placeholder.component(placeholder,
                Component.text(replacement)));

        TextComponent tc = (TextComponent) c;

        return tc.content();

    }

    /**
     * Replace a given string in a string.
     * 
     * @param text        - The text to search in.
     * @param placeholder - The placeholder to find.
     * @param replacement - The replacement.
     * @return - string
     */
    public static String replace(String text, String placeholder, boolean replacement) {

        Component c = MiniMessage.miniMessage().deserialize(text, Placeholder.component(placeholder,
                Component.text(replacement)));

        TextComponent tc = (TextComponent) c;

        return tc.content();

    }

    /**
     * Replace a given string in a component.
     * 
     * @param component   - The component to search in.
     * @param placeholder - The placeholder to find.
     * @param replacement - The replacement.
     * @return - string
     */
    public static String replace(Component component, String placeholder, String replacement) {

        if (!(component instanceof TextComponent)) {
            return "";
        }

        Component c = MiniMessage.miniMessage().deserialize(((TextComponent) component).content(),
                Placeholder.component(placeholder, Component.text(replacement)));

        TextComponent tc = (TextComponent) c;

        return tc.content();

    }

    /**
     * Replace a given string in a component.
     * 
     * @param component   - The component to search in.
     * @param placeholder - The placeholder to find.
     * @param replacement - The replacement.
     * @return - string
     */
    public static String replace(Component component, String placeholder, int replacement) {

        if (!(component instanceof TextComponent)) {
            return "";
        }

        Component c = MiniMessage.miniMessage().deserialize(((TextComponent) component).content(),
                Placeholder.component(placeholder, Component.text(replacement)));

        TextComponent tc = (TextComponent) c;

        return tc.content();

    }

    /**
     * Replace a given string in a component.
     * 
     * @param component   - The component to search in.
     * @param placeholder - The placeholder to find.
     * @param replacement - The replacement.
     * @return - string
     */
    public static String replace(Component component, String placeholder, float replacement) {

        if (!(component instanceof TextComponent)) {
            return "";
        }

        Component c = MiniMessage.miniMessage().deserialize(((TextComponent) component).content(),
                Placeholder.component(placeholder, Component.text(replacement)));

        TextComponent tc = (TextComponent) c;

        return tc.content();

    }

    /**
     * Replace a given string in a component.
     * 
     * @param component   - The component to search in.
     * @param placeholder - The placeholder to find.
     * @param replacement - The replacement.
     * @return - string
     */
    public static String replace(Component component, String placeholder, double replacement) {

        if (!(component instanceof TextComponent)) {
            return "";
        }

        Component c = MiniMessage.miniMessage().deserialize(((TextComponent) component).content(),
                Placeholder.component(placeholder, Component.text(replacement)));

        TextComponent tc = (TextComponent) c;

        return tc.content();

    }

    /**
     * Replace a given string in a component.
     * 
     * @param component   - The component to search in.
     * @param placeholder - The placeholder to find.
     * @param replacement - The replacement.
     * @return - string
     */
    public static String replace(Component component, String placeholder, long replacement) {

        if (!(component instanceof TextComponent)) {
            return "";
        }

        Component c = MiniMessage.miniMessage().deserialize(((TextComponent) component).content(),
                Placeholder.component(placeholder, Component.text(replacement)));

        TextComponent tc = (TextComponent) c;

        return tc.content();

    }

    /**
     * Replace a given string in a component.
     * 
     * @param component   - The component to search in.
     * @param placeholder - The placeholder to find.
     * @param replacement - The replacement.
     * @return - string
     */
    public static String replace(Component component, String placeholder, boolean replacement) {

        if (!(component instanceof TextComponent)) {
            return "";
        }

        Component c = MiniMessage.miniMessage().deserialize(((TextComponent) component).content(),
                Placeholder.component(placeholder, Component.text(replacement)));

        TextComponent tc = (TextComponent) c;

        return tc.content();

    }

    /**
     * Replace a given string in a component.
     * 
     * @param component   - The component to search in.
     * @param placeholder - The placeholder to find.
     * @param replacement - The replacement.
     * @return - string
     */
    public static String replace(Component component, Single placeholder) {

        if (!(component instanceof TextComponent)) {
            return "";
        }

        Component c = MiniMessage.miniMessage().deserialize(((TextComponent) component).content(),
                placeholder);

        TextComponent tc = (TextComponent) c;

        return tc.content();
    }

    /**
     * Replace a given string in a string.
     * 
     * @param text        - The string to search in.
     * @param placeholder - The placeholder to find.
     * @param replacement - The replacement.
     * @return - TextComponent
     */
    public static Component replaceComponent(String text, String placeholder, String replacement) {
        return MiniMessage.miniMessage().deserialize(text, Placeholder.component(placeholder,
                Component.text(replacement)));
    }

    /**
     * Replace a given string in a string.
     * 
     * @param text        - The string to search in.
     * @param placeholder - The placeholder to find.
     * @param replacement - The replacement.
     * @return - TextComponent
     */
    public static Component replaceComponent(String text, String placeholder, int replacement) {
        return MiniMessage.miniMessage().deserialize(text, Placeholder.component(placeholder,
                Component.text(replacement)));
    }

    /**
     * Replace a given string in a string.
     * 
     * @param text        - The string to search in.
     * @param placeholder - The placeholder to find.
     * @param replacement - The replacement.
     * @return - TextComponent
     */
    public static Component replaceComponent(String text, String placeholder, float replacement) {
        return MiniMessage.miniMessage().deserialize(text, Placeholder.component(placeholder,
                Component.text(replacement)));
    }

    /**
     * Replace a given string in a string.
     * 
     * @param text        - The string to search in.
     * @param placeholder - The placeholder to find.
     * @param replacement - The replacement.
     * @return - TextComponent
     */
    public static Component replaceComponent(String text, String placeholder, double replacement) {
        return MiniMessage.miniMessage().deserialize(text, Placeholder.component(placeholder,
                Component.text(replacement)));
    }

    /**
     * Replace a given string in a string.
     * 
     * @param text        - The string to search in.
     * @param placeholder - The placeholder to find.
     * @param replacement - The replacement.
     * @return - TextComponent
     */
    public static Component replaceComponent(String text, String placeholder, long replacement) {
        return MiniMessage.miniMessage().deserialize(text, Placeholder.component(placeholder,
                Component.text(replacement)));
    }

    /**
     * Replace a given string in a string.
     * 
     * @param text        - The string to search in.
     * @param placeholder - The placeholder to find.
     * @param replacement - The replacement.
     * @return - TextComponent
     */
    public static Component replaceComponent(String text, String placeholder, boolean replacement) {
        return MiniMessage.miniMessage().deserialize(text, Placeholder.component(placeholder,
                Component.text(replacement)));
    }

    /**
     * Replace a given string in a component.
     * 
     * @param component   - The component to search in.
     * @param placeholder - The placeholder to find.
     * @param replacement - The replacement.
     * @return - TextComponent
     */
    public static Component replaceComponent(Component component, String placeholder, String replacement) {

        if (!(component instanceof TextComponent)) {
            return component;
        }

        Component c = MiniMessage.miniMessage().deserialize(((TextComponent) component).content(),
                Placeholder.component(placeholder, Component.text(replacement)));

        return c;
    }

    /**
     * Replace a given string in a component.
     * 
     * @param component   - The component to search in.
     * @param placeholder - The placeholder to find.
     * @param replacement - The replacement.
     * @return - TextComponent
     */
    public static Component replaceComponent(Component component, String placeholder, int replacement) {

        if (!(component instanceof TextComponent)) {
            return component;
        }

        Component c = MiniMessage.miniMessage().deserialize(((TextComponent) component).content(),
                Placeholder.component(placeholder, Component.text(replacement)));

        return c;
    }

    /**
     * Replace a given string in a component.
     * 
     * @param component   - The component to search in.
     * @param placeholder - The placeholder to find.
     * @param replacement - The replacement.
     * @return - TextComponent
     */
    public static Component replaceComponent(Component component, String placeholder, float replacement) {

        if (!(component instanceof TextComponent)) {
            return component;
        }

        Component c = MiniMessage.miniMessage().deserialize(((TextComponent) component).content(),
                Placeholder.component(placeholder, Component.text(replacement)));

        return c;
    }

    /**
     * Replace a given string in a component.
     * 
     * @param component   - The component to search in.
     * @param placeholder - The placeholder to find.
     * @param replacement - The replacement.
     * @return - TextComponent
     */
    public static Component replaceComponent(Component component, String placeholder, double replacement) {

        if (!(component instanceof TextComponent)) {
            return component;
        }

        Component c = MiniMessage.miniMessage().deserialize(((TextComponent) component).content(),
                Placeholder.component(placeholder, Component.text(replacement)));

        return c;
    }

    /**
     * Replace a given string in a component.
     * 
     * @param component   - The component to search in.
     * @param placeholder - The placeholder to find.
     * @param replacement - The replacement.
     * @return - TextComponent
     */
    public static Component replaceComponent(Component component, String placeholder, long replacement) {

        if (!(component instanceof TextComponent)) {
            return component;
        }

        Component c = MiniMessage.miniMessage().deserialize(((TextComponent) component).content(),
                Placeholder.component(placeholder, Component.text(replacement)));

        return c;
    }

    /**
     * Replace a given string in a component.
     * 
     * @param component   - The component to search in.
     * @param placeholder - The placeholder to find.
     * @param replacement - The replacement.
     * @return - TextComponent
     */
    public static Component replaceComponent(Component component, String placeholder, boolean replacement) {

        if (!(component instanceof TextComponent)) {
            return component;
        }

        Component c = MiniMessage.miniMessage().deserialize(((TextComponent) component).content(),
                Placeholder.component(placeholder, Component.text(replacement)));

        return c;
    }

    /**
     * Replace a given {@link Single} placeholder in a component.
     * 
     * @param component   - The component to search in.
     * @param placeholder - The placeholder to find.
     * @return - TextComponent
     */
    public static Component replaceComponent(Component component, Single placeholder) {

        if (!(component instanceof TextComponent)) {
            return component;
        }

        Component c = MiniMessage.miniMessage().deserialize(((TextComponent) component).content(),
                placeholder);

        return c;
    }

    /**
     * Create a text component.
     * 
     * @param text - The text to set in the component.
     * @return a text component.
     */
    public static TextComponent textComponent(String text) {
        return Component.text(text);
    }

    /**
     * Get the content of a component.
     * 
     * @param component - The component to get the content from.
     * @return The content of the component.
     */
    public static String content(Component component) {
        if (component instanceof TextComponent) {
            return ((TextComponent) component).content();
        }

        return "";
    }

    /**
     * Check if a component contains a value.
     * 
     * @param component - the component to check.
     * @param search    - the string to search.
     * @return true of the component contains the string
     */
    public static boolean contains(Component component, String search) {
        if (component instanceof TextComponent) {
            return ((TextComponent) component).content().contains(search);
        }

        return false;
    }

}
