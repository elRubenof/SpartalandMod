package com.bcr.spartaland.command;

import com.bcr.spartaland.Spartaland;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.MessageArgument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.GameType;

import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class WarnCommand {
    private static Vector3d p1 = new Vector3d(-1193.5, 107, -1537.5);
    private static Vector3d l1 = new Vector3d(-1193.5, 71, -1580.0);
    private static String m1 = TextFormatting.GREEN + "Rannen: " + TextFormatting.WHITE + "Esta es nuestra aldea, hasta donde conocemos no existen más pueblos pacíficos cerca.";


    private static Vector3d p2 = new Vector3d(-1184.5, 68.7, -1582.5);
    private static Vector3d l2 = new Vector3d(-1178.0, 68.5, -1573.0);
    private static String m2 = TextFormatting.GREEN + "Rannen: " + TextFormatting.WHITE + "En la zona de comerciantes podrás encontrar herramientas, amuletos, armaduras o incluso armas exclusivos.";


    private static Vector3d p3 = new Vector3d(-1228.5, 74.5, -1595.5);
    private static Vector3d l3 = new Vector3d(-1223.5, 71.5, -1590.5);
    private static String m3 = TextFormatting.GREEN + "Rannen: " + TextFormatting.WHITE + "Si vas al templo encontrarás gente que necesita ayuda, a cambio te ofrecerán una recompensa.";


    private static Vector3d p4 = new Vector3d(-1117.5, 74.0, -1582.5);
    private static Vector3d l4 = new Vector3d(-1132.0, 70.5, -1574.0);
    private static String m4 = TextFormatting.GREEN + "Rannen: " + TextFormatting.WHITE + "Para pasar la noche puedes usar la posada sin problema. Habrá camas de sobra";


    private static Vector3d p5 = new Vector3d(-1168.0, 85.0, -1610.0);
    private static Vector3d l5 = new Vector3d(-1153.5, 70.0, -1575.5);
    private static String m5 = TextFormatting.GREEN + "Rannen: " + TextFormatting.WHITE + "Por último, si no has ido aún, deberías visitar el credo de " + TextFormatting.DARK_PURPLE + "Cazadores de Demonio" + TextFormatting.WHITE + ".";

    public WarnCommand(CommandDispatcher<CommandSource> dispatcher) {
        LiteralCommandNode<CommandSource> literalcommandnode = dispatcher.register(Commands.literal("warn").requires((context) -> {
            return context.hasPermission(2);
        }).then(Commands.argument("targets", EntityArgument.players()).executes((p_198539_0_) -> {
            try {
                return sendMessage2(EntityArgument.getPlayers(p_198539_0_, "targets"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 0;
        })));
    }

    private static int sendMessage(CommandSource p_198538_0_, Collection<ServerPlayerEntity> p_198538_1_, ITextComponent p_198538_2_) {
        UUID uuid = p_198538_0_.getEntity() == null ? Util.NIL_UUID : p_198538_0_.getEntity().getUUID();
        Entity entity = p_198538_0_.getEntity();
        Consumer<ITextComponent> consumer;

        if (entity instanceof ServerPlayerEntity) {
            ServerPlayerEntity serverplayerentity = (ServerPlayerEntity)entity;
            consumer = (p_244374_2_) -> {
                String out = "Avisaste a " + p_244374_2_.getString() + ": " + TextFormatting.GOLD + p_198538_2_.getString();

                serverplayerentity.sendMessage((new TranslationTextComponent(out)).withStyle(new TextFormatting[]{TextFormatting.GRAY, TextFormatting.ITALIC}), serverplayerentity.getUUID());
            };
        } else {
            consumer = (p_244375_2_) -> {
                String out = "Avisaste a " + p_244375_2_.getString() + ": " + TextFormatting.GOLD + p_198538_2_.getString();

                p_198538_0_.sendSuccess((new TranslationTextComponent(out)).withStyle(new TextFormatting[]{TextFormatting.GRAY, TextFormatting.ITALIC}), false);
            };
        }

        for(ServerPlayerEntity serverplayerentity1 : p_198538_1_) {
            String in = TextFormatting.GOLD + p_198538_2_.getString();

            consumer.accept(serverplayerentity1.getDisplayName());
            serverplayerentity1.sendMessage((new TranslationTextComponent(in)).withStyle(new TextFormatting[]{TextFormatting.GRAY, TextFormatting.ITALIC}), uuid);
        }

        return p_198538_1_.size();
    }

    public static int sendMessage2(Collection<ServerPlayerEntity> p_198538_1_) throws InterruptedException {

        for(ServerPlayerEntity serverplayerentity1 : p_198538_1_) {
            Vector3d originalPos = serverplayerentity1.position();
            Vector3d originalLook = serverplayerentity1.getLookAngle();
            serverplayerentity1.setGameMode(GameType.SPECTATOR);




            tp(serverplayerentity1, p1, l1, m1, 9);
            tp(serverplayerentity1, p2, l2, m2, 9);
            tp(serverplayerentity1, p3, l3, m3, 9);
            tp(serverplayerentity1, p4, l4, m4, 9);
            tp(serverplayerentity1, p5, l5, m5, 9);


            serverplayerentity1.moveTo(originalPos);
            serverplayerentity1.lookAt(EntityAnchorArgument.Type.EYES, originalLook);
            serverplayerentity1.setGameMode(GameType.SURVIVAL);
        }

        return 1;
    }

    private static void tp(ServerPlayerEntity player, Vector3d pos, Vector3d look, String msg, Integer time) throws InterruptedException {
        player.moveTo(pos);
        player.lookAt(EntityAnchorArgument.Type.EYES, look);

        player.sendMessage((new TranslationTextComponent(msg)).withStyle(new TextFormatting[]{TextFormatting.GRAY, TextFormatting.ITALIC}), player.getUUID());

        TimeUnit.SECONDS.sleep(time);
    }
}
