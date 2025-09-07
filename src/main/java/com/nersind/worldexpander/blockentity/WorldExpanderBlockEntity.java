package com.nersind.worldexpander.blockentity;

import com.nersind.worldexpander.registry.ModBlockEntities;
import com.nersind.worldexpander.json.ExpansionRequirements;
import com.nersind.worldexpander.json.ExpansionRequirements.LevelRequirement;
import com.nersind.worldexpander.json.ExpansionRequirements.Requirement;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class WorldExpanderBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler inventory = new ItemStackHandler(9) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged(); // помечаем BE как изменённое при изменении предмета
        }
    };

    public WorldExpanderBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.WORLD_EXPANDER.get(), pos, state);
    }

    public ItemStackHandler getInventory() {
        return inventory;
    }

    // ---------- GUI ----------
    @Override
    public Component getDisplayName() {
        return Component.literal("World Expander");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory playerInv, Player player) {
        return new WorldExpanderMenu(id, playerInv, this);
    }

    // ---------- Сохранение ----------
    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("Inventory", inventory.serializeNBT());
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("Inventory")) {
            inventory.deserializeNBT(tag.getCompound("Inventory"));
        }
    }

    // ---------- Логика ----------
    public static void tick(Level level, BlockPos pos, BlockState state, WorldExpanderBlockEntity be) {
        if (level.isClientSide) return;

        WorldBorder border = level.getWorldBorder();
        double radius = border.getSize(); // радиус в блоках

        LevelRequirement req = ExpansionRequirements.getForRadius(radius);
        if (req == null) return;

        if (hasRequiredItems(be, req)) {
            consumeItems(be, req);
            border.setSize(border.getSize() + 16); // +16 блоков к радиусу
            level.sendBlockUpdated(pos, state, state, 3);
            be.setChanged();
        }
    }

    private static boolean hasRequiredItems(WorldExpanderBlockEntity be, LevelRequirement req) {
        for (Requirement r : req.cost) {
