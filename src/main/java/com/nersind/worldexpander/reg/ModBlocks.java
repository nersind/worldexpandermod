package com.nersind.worldexpander.registry;

import com.nersind.worldexpander.WorldExpanderMod;
import com.nersind.worldexpander.block.WorldExpanderBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class WorldExpanderBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, WorldExpanderMod.MODID);

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, WorldExpanderMod.MODID);

    public static final RegistryObject<Block> WORLD_EXPANDER = registerBlock(
            "world_expander",
            () -> new WorldExpanderBlock(BlockBehaviour.Properties.of(Material.METAL).strength(4f).noOcclusion())
    );

    private static RegistryObject<Block> registerBlock(String name, Supplier<Block> block) {
        RegistryObject<Block> registryObject = BLOCKS.register(name, block);
        ITEMS.register(name, () -> new BlockItem(registryObject.get(), new Item.Properties()));
        return registryObject;
    }
}
