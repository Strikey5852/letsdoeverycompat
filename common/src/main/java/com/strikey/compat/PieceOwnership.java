package com.strikey.compat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.architectury.platform.Platform;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PieceOwnership {

    // piece -> candidate mod ids, highest priority first
    private static final Map<String, List<String>> SHARED = new LinkedHashMap<>();

    static {
        SHARED.put("cabinet", List.of("candlelight", "vinery", "beachparty"));
        SHARED.put("drawer", List.of("candlelight", "vinery"));
        SHARED.put("table", List.of("candlelight", "vinery", "beachparty"));
        SHARED.put("chair", List.of("candlelight", "vinery", "beachparty"));
        SHARED.put("shelf", List.of("candlelight", "vinery"));
        SHARED.put("big_table", List.of("candlelight", "vinery"));
    }

    private static final int VERSION = 1;
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private static final Map<String, String> OWNERS = new LinkedHashMap<>();

    private static Path file() {
        return Platform.getConfigFolder().resolve("letsdoeverycompat.json");
    }

    public static void init() {
        load();
        boolean changed = false;
        for (var e : SHARED.entrySet()) {
            String piece = e.getKey();
            String owner = OWNERS.get(piece);
            if (owner != null && Platform.isModLoaded(owner)) continue;
            // unclaimed or owner gone: let a loaded candidate claim it
            String next = e.getValue().stream().filter(Platform::isModLoaded).findFirst().orElse(null);
            if (next == null) {
                if (owner != null && OWNERS.remove(piece) != null) changed = true;
            } else if (!next.equals(owner)) {
                OWNERS.put(piece, next);
                changed = true;
            }
        }
        if (changed) save();
    }

    public static boolean owns(String modId, String piece) {
        return modId.equals(OWNERS.get(piece));
    }

    private static void load() {
        Path p = file();
        if (!Files.exists(p)) return;
        try {
            JsonObject root = JsonParser.parseString(Files.readString(p, StandardCharsets.UTF_8)).getAsJsonObject();
            if (root.has("owners")) {
                root.getAsJsonObject("owners").entrySet()
                        .forEach(e -> OWNERS.put(e.getKey(), e.getValue().getAsString()));
            }
        } catch (Exception ex) {
            LetsDoEveryCompat.LOGGER.error("Failed to read piece ownership config {}: {}", p, ex.toString());
        }
    }

    private static void save() {
        Path p = file();
        try {
            Files.createDirectories(p.getParent());
            JsonObject owners = new JsonObject();
            OWNERS.forEach(owners::addProperty);
            JsonObject root = new JsonObject();
            root.addProperty("version", VERSION);
            root.add("owners", owners);
            Files.writeString(p, GSON.toJson(root), StandardCharsets.UTF_8);
        } catch (IOException ex) {
            LetsDoEveryCompat.LOGGER.error("Failed to write piece ownership config {}: {}", p, ex.toString());
        }
    }
}