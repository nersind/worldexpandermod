package com.nersind.worldexpander.json;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

public class ExpansionRequirements {
    private static RequirementsData data;

    public static class Requirement {
        public Holder<Item> item;
        public int count;

        public ItemStack toStack() {
            return new ItemStack(item, count);
        }
    }

    public static class LevelRequirement {
        public int max_radius;
        public List<Requirement> cost;
    }

    public static class RequirementsData {
        public List<LevelRequirement> levels;
    }

    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(Holder.class, (JsonDeserializer<Holder<Item>>) (json, typeOfT, context) -> {
                ResourceLocation id = new ResourceLocation(json.getAsString());
                return BuiltInRegistries.ITEM.getHolder(id)
                        .orElseThrow(() -> new IllegalArgumentException("Unknown item: " + id));
            })
            .create();

    public static void load(MinecraftServer server) {
        try {
            ResourceLocation file = new ResourceLocation("worldexpander", "requirement.json");
            var resource = server.getResourceManager().getResource(file).orElseThrow();
            try (var reader = new InputStreamReader(resource.open())) {
                Type type = new TypeToken<RequirementsData>(){}.getType();
                data = GSON.fromJson(reader, type);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static LevelRequirement getForRadius(double radius) {
        if (data == null) return null;
        for (LevelRequirement lvl : data.levels) {
            if (radius < lvl.max_radius) return lvl;
        }
        return null;
    }
}
