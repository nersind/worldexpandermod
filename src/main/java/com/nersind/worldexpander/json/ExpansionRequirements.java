package com.nersind.worldexpander.json;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.registries.BuiltInRegistries;

import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class ExpansionRequirements {
    private static final Gson GSON = new Gson();
    private static RequirementsData data;

    public static class Requirement {
        public String item;
        public int count;

        public ItemStack toStack() {
            Item i = BuiltInRegistries.ITEM.get(new ResourceLocation(item));
            return new ItemStack(i, count);
        }
    }

    public static class LevelRequirement {
        public int max_radius;
        public List<Requirement> cost;
    }

    public static class RequirementsData {
        public List<LevelRequirement> levels;
    }

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
