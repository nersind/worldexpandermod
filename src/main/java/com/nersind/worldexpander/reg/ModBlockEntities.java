package com.nersind.worldexpander.reg;

import com.nersind.worldexpander.WorldExpanderMod;
import com.nersind.worldexpander.blockentity.WorldExpanderBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, WorldExpanderMod.MODID);

    public static final RegistryObject<BlockEntityType<WorldExpanderBlockEntity>> WORLD_EXPANDER =
            BLOCK_ENTITIES.register("world_expander",
                    () -> BlockEntityType.Builder.of(WorldExpanderBlockEntity::new,
                            ModBlocks.WORLD_EXPANDER.get()).build(null));
}
