package com.bcr.spartaland.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.MessageArgument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.GameType;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class DesertVillage {

    public DesertVillage(CommandDispatcher<CommandSource> dispatcher) {
        LiteralCommandNode<CommandSource> literalcommandnode = dispatcher.register(Commands.literal("desertvillage").requires((context) -> {
            return context.hasPermission(2);
        }).then(Commands.argument("target", EntityArgument.players()).executes((context) -> {
            for (ServerPlayerEntity target : EntityArgument.getPlayers(context, "target")) {
                try {
                    return teleporting(context.getSource().getLevel(), target);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return 1;
        })));
    }

    public int teleporting(ServerWorld world, ServerPlayerEntity target) throws InterruptedException {
        Vector3d originalPos = target.position();
        Vector3d originalLook = target.getLookAngle();
        target.setGameMode(GameType.SPECTATOR);

        tp(target, Positions.p1, Positions.l1, Positions.m1, 10);
        tp(target, Positions.p2, Positions.l2, Positions.m2, 10);
        tp(target, Positions.p3, Positions.l3, Positions.m3, 10);
        tp(target, Positions.p4, Positions.l4, Positions.m4, 10);
        tp(target, Positions.p5, Positions.l5, Positions.m5, 10);

        target.moveTo(originalPos);
        target.lookAt(EntityAnchorArgument.Type.EYES, originalLook);
        target.setGameMode(GameType.SURVIVAL);

        return 1;
    }

    private void tp(ServerPlayerEntity player, Vector3d pos, Vector3d look, String msg, Integer time) throws InterruptedException {
        player.moveTo(pos);
        player.lookAt(EntityAnchorArgument.Type.EYES, look);
        player.sendMessage(ITextComponent.nullToEmpty(msg), player.getUUID());

        TimeUnit.SECONDS.sleep(time);
    }

    private static class Positions {
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
    }
}
