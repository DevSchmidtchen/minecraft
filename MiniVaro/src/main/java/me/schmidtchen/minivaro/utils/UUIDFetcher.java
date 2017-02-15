package me.schmidtchen.minivaro.utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class UUIDFetcher {

    /**
     * Date when name changes were introduced
     */

    private static Gson gson = new Gson();
    private static final String UUID_URL = "https://api.mojang.com/users/profiles/minecraft/%s";
    private static ExecutorService pool = Executors.newCachedThreadPool();

    /**
     * Fetches the uuid asynchronously and passes it to the consumer
     *
     * @param name The name
     * @param action Do what you want to do with the uuid her
     */
    public static void getUUID(String name, Consumer<UUID> action) {
        pool.execute(() -> action.accept(requestUUID(name)));
    }

    /**
     * Fetches the uuid synchronously for a specified name and time
     *
     * @param name The name
     */
    private static UUID requestUUID(String name) {
        name = name.toLowerCase();
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(String.format(UUID_URL, name)).openConnection();
            connection.setReadTimeout(5000);

            if (connection.getResponseCode() == HttpURLConnection.HTTP_NO_CONTENT) {
                return null;
            }

            JsonElement element = new JsonParser().parse(new BufferedReader(new InputStreamReader(connection.getInputStream())));
            JsonObject obj = element.getAsJsonObject();

            String id = obj.get("id").getAsString();
            UUID uuid = UUID.fromString(id.substring(0, 8) + "-" + id.substring(8, 12) + "-" + id.substring(12, 16) + "-" + id.substring(16, 20) + "-" + id.substring(20, 32));

            return uuid;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
