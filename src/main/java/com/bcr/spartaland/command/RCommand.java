package com.bcr.spartaland.command;

import com.bcr.spartaland.Spartaland;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.MessageArgument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;

public class RCommand {

    public RCommand(CommandDispatcher<CommandSource> dispatcher) {
        LiteralCommandNode<CommandSource> literalcommandnode = dispatcher.register(Commands.literal("r").then(Commands.argument("message", MessageArgument.message()).executes(
                (context) -> {
                    ServerPlayerEntity player = context.getSource().getServer().getPlayerList().getPlayer(context.getSource().getEntity().getPersistentData().getUUID(Spartaland.MOD_ID + "lastMsg"));

                    System.out.println(player.getScoreboardName());

                    return sendMessage(context.getSource(), player, MessageArgument.getMessage(context, "message"));
                }
        )));
    }

    private static int sendMessage(CommandSource p_198538_0_, ServerPlayerEntity p_198538_1_, ITextComponent p_198538_2_) {
        UUID uuid = p_198538_0_.getEntity() == null ? Util.NIL_UUID : p_198538_0_.getEntity().getUUID();
        Entity entity = p_198538_0_.getEntity();
        Consumer<ITextComponent> consumer;

        if (entity instanceof ServerPlayerEntity) {
            ServerPlayerEntity serverplayerentity = (ServerPlayerEntity) entity;
            consumer = (p_244374_2_) -> {
                String out = TextFormatting.GOLD + "[" + TextFormatting.GREEN + "Tú" + TextFormatting.GOLD + " -> " + TextFormatting.RED + p_244374_2_.getString() + TextFormatting.GOLD + "]" + TextFormatting.GRAY + ": " + p_198538_2_.getString();

                serverplayerentity.sendMessage((new TranslationTextComponent(out)).withStyle(new TextFormatting[]{TextFormatting.GRAY, TextFormatting.ITALIC}), serverplayerentity.getUUID());
            };
        } else {
            consumer = (p_244375_2_) -> {
                String out = TextFormatting.GOLD + "[" + TextFormatting.GREEN + "Tú" + TextFormatting.GOLD + " -> " + TextFormatting.RED + p_244375_2_.getString() + TextFormatting.GOLD + "]" + TextFormatting.GRAY + ": " + p_198538_2_.getString();

                p_198538_0_.sendSuccess((new TranslationTextComponent(out)).withStyle(new TextFormatting[]{TextFormatting.GRAY, TextFormatting.ITALIC}), false);
            };
        }

        String in = TextFormatting.GOLD + "[" + TextFormatting.RED + p_198538_0_.getDisplayName().getString() + TextFormatting.GOLD + " -> " + TextFormatting.GREEN + "Tú" + TextFormatting.GOLD + "]" + TextFormatting.GRAY + ": " + p_198538_2_.getString();

        p_198538_1_.getPersistentData().putUUID(Spartaland.MOD_ID + "lastMsg", p_198538_0_.getEntity().getUUID());

        consumer.accept(p_198538_1_.getDisplayName());
        p_198538_1_.sendMessage((new TranslationTextComponent(in)).withStyle(new TextFormatting[]{TextFormatting.GRAY, TextFormatting.ITALIC}), uuid);


        return 1;
    }
}
