package com.nersind.worldexpander.blockentity;

import com.nersind.worldexpander.reg.ModMenus;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraftforge.items.SlotItemHandler;

public class WorldExpanderMenu extends AbstractContainerMenu {
    private final WorldExpanderBlockEntity blockEntity;

    public WorldExpanderMenu(int id, Inventory playerInv, WorldExpanderBlockEntity be) {
        super(ModMenus.WORLD_EXPANDER.get(), id);
        this.blockEntity = be;

        // Слоты блока (9 штук)
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                this.addSlot(new SlotItemHandler(be.getInventory(),
                        col + row * 3, 62 + col * 18, 17 + row * 18));
            }
        }

        // Инвентарь игрока
        int startY = 84;
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(playerInv, col + row * 9 + 9,
                        8 + col * 18, startY + row * 18));
            }
        }

        // Хотбар
        for (int col = 0; col < 9; ++col) {
            this.addSlot(new Slot(playerInv, col, 8 + col * 18, startY + 58));
        }
    }

    // Конструктор для клиента
    public WorldExpanderMenu(int id, Inventory playerInv, FriendlyByteBuf buf) {
        this(id, playerInv,
                (WorldExpanderBlockEntity) playerInv.player.level().getBlockEntity(buf.readBlockPos()));
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}
