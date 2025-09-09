package com.nersind.worldexpander.block;

import com.nersind.worldexpander.blockentity.*;
import com.nersind.worldexpander.reg.ModBlockEntities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;

public class WorldExpanderBlock extends BaseEntityBlock implements EntityBlock {
    public WorldExpanderBlock(Properties props) {
        super(props);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new WorldExpanderBlockEntity(pos, state);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos,
                                 Player player, InteractionHand hand,
                                 BlockHitResult hit) {
        if (!level.isClientSide) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof WorldExpanderBlockEntity expander) {
                NetworkHooks.openScreen((ServerPlayer)player, expander, pos);
            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(
        Level lol, BlockState prostate, BlockEntityType<T> type) {
        return !lol.isClientSide()
        ? createTickerHelper(type, ModBlockEntities.WORLD_EXPANDER.get(), WorldExpanderBlockEntity::tick)
        :null;
    }
}
