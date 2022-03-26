package com.bcr.spartaland.event;

import com.bcr.spartaland.Spartaland;
import com.bcr.spartaland.command.LightningCommand;
import com.bcr.spartaland.command.MsgCommand;
import com.bcr.spartaland.command.RCommand;
import com.bcr.spartaland.command.WarnCommand;
import com.bcr.spartaland.item.ModItems;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;
import virtuoel.pehkui.Pehkui;
import virtuoel.pehkui.api.ScaleData;
import virtuoel.pehkui.api.ScaleType;
import virtuoel.pehkui.api.ScaleTypes;
import virtuoel.pehkui.command.argument.ScaleTypeArgumentType;
import virtuoel.pehkui.util.PehkuiEntityExtensions;

@Mod.EventBusSubscriber
public class CommonEvents {

    @SubscribeEvent
    public static void onCommandsRegister(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSource> dispatcher = event.getDispatcher();

        new MsgCommand(dispatcher);
        new RCommand(dispatcher);
        new LightningCommand(dispatcher);
        new WarnCommand(dispatcher);

        ConfigCommand.register(dispatcher);
    }

    public static void onAxeHold(TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;
        ItemStack itemInHand = player.getItemInHand(Hand.MAIN_HAND);
        final ScaleData data = ScaleTypes.BASE.getScaleData(player);

        Spartaland.LOGGER.info(data.getBaseScale());

        if (itemInHand.getItem().equals(ModItems.RHITTA.get())) {
            Spartaland.LOGGER.info("HOLAAAA");

            data.setTargetScale(1.5F);
        }
    }
}
