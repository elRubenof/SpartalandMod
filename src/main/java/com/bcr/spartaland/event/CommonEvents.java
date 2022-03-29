package com.bcr.spartaland.event;

import com.bcr.spartaland.Spartaland;
import com.bcr.spartaland.command.LightningCommand;
import com.bcr.spartaland.command.MsgCommand;
import com.bcr.spartaland.command.RCommand;
import com.bcr.spartaland.command.WarnCommand;
import com.bcr.spartaland.util.Utils;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;

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

    @SubscribeEvent
    public static void onInputUpdate(InputUpdateEvent event) {
        Utils.houseTeleport(event);
    }
}
