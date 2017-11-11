package ua.nure.kn156.kriat.util;

import java.util.ResourceBundle;

/**
 * The {@code Message} class contain local-specific objects.
 * This object represents as {@code String} and their value
 * specify by default user's locale.
 * @author Vadym Kriat
 */
public class Message {
    /**
     * Name of i18n file
     */
    private static final String BUNDLE_NAME = "message";
    /**
     * Resource loader for loading resource bundle from specify file
     */
    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

    /**
     * Get {@code String} value by specified key
     *
     * @param key the key of the string value
     * @return a {@code String} value for the given key
     */
    public static String getString(String key) {
        return RESOURCE_BUNDLE.getString(key);
    }
}