package com.nersind.worldexpander.reg;

import com.nersind.worldexpander.WorldExpanderMod;
import com.nersind.worldexpander.blockentity.WorldExpanderMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenus {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, WorldExpanderMod.MODID);

    public static final RegistryObject<MenuType<WorldExpanderMenu>> WORLD_EXPANDER =
            MENUS.register("world_expander",
                    () -> IForgeMenuType.create(WorldExpanderMenu::new));
}
