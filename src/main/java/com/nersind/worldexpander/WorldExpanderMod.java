package com.nersind.worldexpander;

import com.nersind.worldexpander.registry.ModBlocks;
import com.nersind.worldexpander.registry.ModBlockEntities;
import com.nersind.worldexpander.registry.ModMenus;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(WorldExpanderMod.MODID)
public class WorldExpanderMod {
    public static final String MODID = "worldexpander";

    public WorldExpanderMod() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModBlocks.BLOCKS.register(bus);
        ModBlockEntities.BLOCK_ENTITIES.register(bus);
        ModMenus.MENUS.register(bus);

        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onServerAboutToStart(ServerAboutToStartEvent event) {
        ExpansionRequirements.load(event.getServer());
    }
}
