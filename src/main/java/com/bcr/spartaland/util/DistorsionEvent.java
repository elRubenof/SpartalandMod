package com.bcr.spartaland.util;

import com.bcr.spartaland.Spartaland;
import com.bcr.spartaland.block.ModBlocks;
import com.bcr.spartaland.command.LightningCommand;
import com.google.common.collect.Lists;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.command.CommandSource;
import net.minecraft.command.arguments.BlockPosArgument;
import net.minecraft.command.arguments.BlockPredicateArgument;
import net.minecraft.command.arguments.BlockStateArgument;
import net.minecraft.command.arguments.BlockStateInput;
import net.minecraft.command.impl.DifficultyCommand;
import net.minecraft.command.impl.FillCommand;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IClearable;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.CachedBlockInfo;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameType;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.annotation.Nullable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

public class DistorsionEvent {
    private static final Vector3d center = new Vector3d(-1160.5, 129, -1654.5);
    private static final BlockPos blockPos = new BlockPos(center);

    public static void clientSide(PlayerEntity player) {
        Duration time = time();
        List<? extends PlayerEntity> players = player.level.players();

        if (!time().isNegative() && time.toHours() < 12) {
            player.displayClientMessage(timer(), true);

        } else if (time().isNegative() && isInside(player)) {

            if (playersInside(players) < players.size() && player.level.getBlockState(new BlockPos(center)).getBlock().equals(ModBlocks.SPHERE.get())) {
                player.displayClientMessage(ITextComponent.nullToEmpty("Esperando jugadores (" + playersInside(players) + "/" + players.size() + ")"), true);
            } else {
                player.displayClientMessage(ITextComponent.nullToEmpty(""), true);
            }
        }
    }

    public static void serverSide(TickEvent.WorldTickEvent event) throws CommandSyntaxException, InterruptedException {
        Duration time = time();
        ServerWorld world = event.world.getServer().overworld();
        List<ServerPlayerEntity> players = world.players();

        if (players.size() > 0 && time.toHours() < 12 && time.toMinutes() > -30) {
            if (time.toHours() < 6) {
                Random r = new Random();
                int RR = r.nextInt(time.isNegative() ? 1500 : 8000);

                if (RR < 6 - time.toHours()) {
                    LightningCommand.spawnEntity(world, center, null);
                }

                if (time.isNegative() && RR < 3) {
                    int n = r.nextInt(positions().size());
                    LightningCommand.spawnEntity(world, positions().get(n), null);
                }
            }

            if (time.isNegative()) {
                int RR = 0;
                for (ServerPlayerEntity player : players) {
                    RR += player.position().distanceTo(new Vector3d(center.x, player.position().y, center.z)) < 20 ? 1 : 0;
                }

                if (RR >= players.size() && world.getBlockState(blockPos).getBlock().equals(ModBlocks.SPHERE.get())) {
                    world.setBlockAndUpdate(blockPos, Blocks.AIR.defaultBlockState());

                    startEvent(world);
                }
            }

            world.setWeatherParameters(0, 100, true, true);
        }
    }

    public static Duration time() {
        LocalDateTime start = LocalDateTime.of(2022, 4, 4, 18, 35);
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Europe/Madrid"));

        return Duration.between(now, start);
    }

    public static ITextComponent timer() {
        Duration time = time();
        String hours = String.format("%02d", time.toHours());
        String minutes = String.format("%02d", time.toMinutes() % 60);
        String seconds = String.format("%02d", time.getSeconds() % 60);

        return ITextComponent.nullToEmpty(TextFormatting.GREEN + "" + TextFormatting.BOLD + "Desastre de mana: " + hours + ":" + minutes + ":" + seconds);
    }

    public static boolean isInside(PlayerEntity player) {
        boolean RR;
        Vector3d pos = player.position();

        if (pos.distanceTo(new Vector3d(center.x, pos.y, center.z)) < 20) {
            RR = true;
        } else {
            RR = false;
        }

        return RR;
    }

    public static int playersInside(List<? extends PlayerEntity> players) {
        int RR = 0;

        for (PlayerEntity player : players) {
            RR += isInside(player) ? 1 : 0;
        }
        return RR;
    }

    public static void lightning(ServerWorld world) throws CommandSyntaxException, InterruptedException {
        for (Vector3d pos : positions()) {
            LightningCommand.spawnEntity(world, pos, null);
        }
    }

    public static List<Vector3d> positions() {
        List<Vector3d> positions = new ArrayList<>();

        positions.add(new Vector3d(-1152.5, 64, -1634.5));
        positions.add(new Vector3d(-1165.5, 64, -1634.5));
        positions.add(new Vector3d(-1174.5, 64, -1640.5));
        positions.add(new Vector3d(-1169.5, 64, -1642.5));
        positions.add(new Vector3d(-1164.5, 64, -1639.5));
        positions.add(new Vector3d(-1164.5, 65, -1644.5));
        positions.add(new Vector3d(-1154.5, 68, -1644.5));
        positions.add(new Vector3d(-1151.5, 64, -1640.5));
        positions.add(new Vector3d(-1142.5, 66, -1643.5));
        positions.add(new Vector3d(-1139.5, 69, -1651.5));
        positions.add(new Vector3d(-1144.5, 69, -1651.5));
        positions.add(new Vector3d(-1149.5, 69, -1650.5));
        positions.add(new Vector3d(-1160.5, 68, -1648.5));
        positions.add(new Vector3d(-1173.5, 64, -1648.5));
        positions.add(new Vector3d(-1179.5, 64, -1656.5));
        positions.add(new Vector3d(-1169.5, 64, -1651.5));
        positions.add(new Vector3d(-1163.5, 64, -1656.5));
        positions.add(new Vector3d(-1157.5, 68, -1658.5));
        positions.add(new Vector3d(-1154.5, 68, -1654.5));
        positions.add(new Vector3d(-1146.5, 68, -1660.5));
        positions.add(new Vector3d(-1154.5, 68, -1661.5));
        positions.add(new Vector3d(-1166.5, 64, -1660.5));
        positions.add(new Vector3d(-1171.5, 64, -1662.5));
        positions.add(new Vector3d(-1167.5, 64, -1671.5));
        positions.add(new Vector3d(-1164.5, 66, -1667.5));
        positions.add(new Vector3d(-1154.5, 67, -1672.5));
        positions.add(new Vector3d(-1151.5, 64, -1666.5));
        positions.add(new Vector3d(-1144.5, 68, -1666.5));

        return positions;
    }

    public static void startEvent(ServerWorld world) throws CommandSyntaxException, InterruptedException {
        List<ServerPlayerEntity> players = world.players();
        Spartaland.LOGGER.info("EMPIEZA EVENTO");
        lightning(world);
        world.getServer().setDifficulty(Difficulty.NORMAL, true);

        for (ServerPlayerEntity player : players) {
            if (player.getStringUUID().equals("380df991-f603-344c-a090-369bad2a924a") || player.getStringUUID().equals("4d17d85a-accc-49ae-9a4f-b6b5a965cf1e")) {
                player.addEffect(new EffectInstance(Effects.INVISIBILITY, 200));
                player.moveTo(center.x, 150, center.z);
            }
        }
    }

    public static void onVoid(PlayerEntity player) {
        String biome = player.level.getBiome(player.blockPosition()).getRegistryName().toString();

        if (biome.equals("minecraft:the_void")) {
            player.setNoGravity(true);
        } else {
            player.setNoGravity(false);
        }
    }

    public static void onHurtPlayer(LivingDamageEvent event) {
        Entity entity = event.getEntity();
        ServerWorld world = entity.getServer().overworld();

        if (entity instanceof PlayerEntity && event.getSource().msgId.equals("onFire") && isInside((PlayerEntity) entity) && !world.getBlockState(blockPos).getBlock().equals(ModBlocks.SPHERE)) {
            entity.moveTo(-1249, 71, -2582);
            Spartaland.LOGGER.info("TELEPORTED " + entity.getScoreboardName());
        }
    }
}
