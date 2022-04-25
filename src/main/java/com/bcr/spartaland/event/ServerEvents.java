package com.bcr.spartaland.event;

import com.bcr.spartaland.Spartaland;
import com.bcr.spartaland.util.DistorsionEvent;
import com.bcr.spartaland.util.RhittaUtils;
import com.bcr.spartaland.util.Utils;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(Dist.DEDICATED_SERVER)
public class ServerEvents {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;
        ItemStack stack = player.inventory.getSelected();

        Utils.houseTeleport(player);
        DistorsionEvent.onVoid(player);

        if (stack.hasTag()) {
            CompoundNBT nbt = stack.getTag();
            if (nbt.hasUUID(Spartaland.MOD_ID + ":Owner")) {
                if(!nbt.getUUID(Spartaland.MOD_ID + ":Owner").equals(player.getUUID())) {
                    player.drop(stack, true);
                }
            }
        }
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
    public static void onItemPickUp(EntityItemPickupEvent event) {
        PlayerEntity player = event.getPlayer();
        ItemStack itemStack = event.getItem().getItem();
        CompoundNBT nbt;

        if (Utils.isLegenday(itemStack.getItem())) {
            if (itemStack.hasTag()) {
                nbt = itemStack.getTag();
            } else {
                nbt = new CompoundNBT();
            }

            if (!nbt.hasUUID(Spartaland.MOD_ID + ":Owner")) {
                nbt.putUUID(Spartaland.MOD_ID + ":Owner", player.getUUID());
            }

            itemStack.setTag(nbt);
        }
    }
}
