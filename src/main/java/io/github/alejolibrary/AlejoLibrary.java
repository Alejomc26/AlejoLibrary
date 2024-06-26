package io.github.alejolibrary;

import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public class AlejoLibrary {

    private static final String NAMESPACE = "alejo-library";

    /**
     * Returns a key for the use of AlejoLibrary, must not be used by library consumers.
     * @param key key
     * @return Namespace with the passed key.
     */
    @ApiStatus.Internal
    public static NamespacedKey createKey(@NotNull String key) {
        return new NamespacedKey(NAMESPACE, key);
    }

}
