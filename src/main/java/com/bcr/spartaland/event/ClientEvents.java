package com.bcr.spartaland.event;

import com.bcr.spartaland.Spartaland;
import com.bcr.spartaland.command.*;
import com.bcr.spartaland.util.DistorsionEvent;
import com.bcr.spartaland.util.Utils;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHelper;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.world.GameType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;

import java.io.IOException;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void onLogin(PlayerEvent.PlayerLoggedInEvent event) throws IOException {
        Utils.versionChecker(event);
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Minecraft mc = Minecraft.getInstance();
        PlayerEntity player = event.player;
        String name = player.getScoreboardName();

        DistorsionEvent.clientSide(player);

        if (mc.gameMode.getPlayerMode().equals(GameType.SPECTATOR) && !name.equals("XxFLOYxX") && !name.equals("elRubenof_YT")) {
            KeyBinding.releaseAll();
            mc.mouseHandler.releaseMouse();
        }
    }
}
