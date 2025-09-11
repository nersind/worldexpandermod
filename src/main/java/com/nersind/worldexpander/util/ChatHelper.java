package com.nersind.worldexpander.util

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

public class ChatHelper {
    public static void sendToAll(Level level, String message) {
        if (level.isClientSide) return;
        if (!(level instanceof ServerLevel serverLevel)) return;

        serverLevel.players().forEach(p -> 
            p.sendSystemMessage(Component.literal("[WorldExpander] " + message))
        );
    }
}
