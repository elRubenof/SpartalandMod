package com.bcr.spartaland.event;

import com.bcr.spartaland.Spartaland;
import com.bcr.spartaland.util.DistorsionEvent;
import com.bcr.spartaland.util.Utils;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.Dimension;
import net.minecraft.world.DimensionType;
import net.minecraft.world.gen.DimensionSettings;
import net.minecraft.world.storage.IServerWorldInfo;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.IOException;

@Mod.EventBusSubscriber(Dist.DEDICATED_SERVER)
public class ServerEvents {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;

        Utils.houseTeleport(player);
        DistorsionEvent.onVoid(player);
    }

    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event) throws CommandSyntaxException, InterruptedException {
        DistorsionEvent.serverSide(event);

        for (PlayerEntity player : event.world.players()) {
            if (player.level.getBiome(player.blockPosition()).getRegistryName().toString().equals("minecraft:the_void")) {
                event.world.getServer().overworld().setWeatherParameters(100, 0, false, false);
                event.world.getServer().overworld().setDayTime(3000);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerHurts(LivingDamageEvent event) {
        DistorsionEvent.onHurtPlayer(event);
    }
}
