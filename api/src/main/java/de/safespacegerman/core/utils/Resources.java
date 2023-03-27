package de.safespacegerman.core.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/**
 * SpaceCore; de.safespacegerman.core.utils:Resources
 *
 * @author <a href="https://github.com/LuciferMorningstarDev">LuciferMorningstarDev</a>
 * @since 27.03.2023
 */
public class Resources {

    private Resources() {} // prevent instantiation

    public static void saveResource(File folder, String resourcePath, boolean overwrite) {
        if (!resourcePath.startsWith("/")) {
            resourcePath = "/" + resourcePath;
        }
        File file = new File(folder, resourcePath);
        if (!file.exists() || overwrite) {
            try (InputStream in = Resources.class.getResourceAsStream(resourcePath)) {
                Files.copy(in, file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Read File content to String
     *
     * @param resourcePath
     * @return content
     */
    public static String readToString(String resourcePath) {
        try {
            return new String(Resources.class.getClassLoader().getResourceAsStream(resourcePath).readAllBytes(), StandardCharsets.UTF_8);
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

}
