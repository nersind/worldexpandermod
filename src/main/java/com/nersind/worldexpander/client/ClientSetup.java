package com.nersind.worldexpander.client;

import com.nersind.worldexpander.registry.ModMenus;
import com.nersind.worldexpander.client.WorldExpanderScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.DistExecutor;

@Mod.EventBusSubscriber(modid = "worldexpander", bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {
    public static void init(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            net.minecraft.client.gui.screens.MenuScreens.register(
                    ModMenus.WORLD_EXPANDER.get(),
                    WorldExpanderScreen::new
            );
        });
    }
}
