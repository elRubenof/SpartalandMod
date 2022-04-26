package com.bcr.spartaland.event;

import com.bcr.spartaland.Spartaland;
import com.bcr.spartaland.command.*;
import com.bcr.spartaland.item.HolyArmorAmulet;
import com.bcr.spartaland.item.ModItems;
import com.bcr.spartaland.screen.TpSelectorScreen;
import com.bcr.spartaland.util.RhittaUtils;
import com.bcr.spartaland.util.Utils;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.WallSignBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;

import java.util.Optional;


@Mod.EventBusSubscriber
public class CommonEvents {

    @SubscribeEvent
    public static void onCommandsRegister(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSource> dispatcher = event.getDispatcher();

        new MsgCommand(dispatcher);
        new RCommand(dispatcher);
        new LightningCommand(dispatcher);
        new WarnCommand(dispatcher);
        new OpenPortalCommand(dispatcher);
        new CustomEffectCommand(dispatcher);

        ConfigCommand.register(dispatcher);
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;
        boolean armor = player.getPersistentData().getBoolean(Spartaland.MOD_ID + ":Armor");

        RhittaUtils.checker(player);

        if (Utils.wearHolyArmor(player) && !armor) {
            player.getPersistentData().putBoolean(Spartaland.MOD_ID + ":Armor", true);
            if (player.level.isClientSide()) {
                Minecraft.getInstance().player.chat("/ceffect give Dev minecraft:bad_omen 300");
            }
        }
        if (!Utils.wearHolyArmor(player) && armor) {
            player.getPersistentData().putBoolean(Spartaland.MOD_ID + ":Armor", false);
            player.inventory.add(ModItems.HOLY_ARMOR_AMULET.get().getDefaultInstance());
            player.kill();
        }
    }
}
