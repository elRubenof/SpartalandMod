package com.bcr.spartaland.command;

import com.bcr.spartaland.Spartaland;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.MessageArgument;
import net.minecraft.command.arguments.Vec3Argument;
import net.minecraft.entity.*;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Collection;
import java.util.UUID;

public class LightningCommand {
    private static final SimpleCommandExceptionType ERROR_FAILED = new SimpleCommandExceptionType(new TranslationTextComponent("commands.summon.failed"));
    private static final SimpleCommandExceptionType ERROR_DUPLICATE_UUID = new SimpleCommandExceptionType(new TranslationTextComponent("commands.summon.failed.uuid"));
    private static final SimpleCommandExceptionType INVALID_POSITION = new SimpleCommandExceptionType(new TranslationTextComponent("commands.summon.invalidPosition"));

    public LightningCommand(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(Commands.literal("lightning").requires((context) -> {
            return context.hasPermission(2);
        }).then(Commands.argument("target", EntityArgument.entities()).executes((context) -> {
            for (Entity entity : EntityArgument.getEntities(context, "target")) {
                return spawnEntity(context.getSource(), entity.position());
            }
            return 1;
        })).then(Commands.argument("position", Vec3Argument.vec3()).executes((context) -> {
            return spawnEntity(context.getSource(), Vec3Argument.getVec3(context, "position"));
        })));
    }

    private static int spawnEntity(CommandSource p_198737_0_, Vector3d p_198737_2_) throws CommandSyntaxException {
        BlockPos blockpos = new BlockPos(p_198737_2_);
        if (!World.isInSpawnableBounds(blockpos)) {
            throw INVALID_POSITION.create();
        } else {
            CompoundNBT compoundnbt = new CompoundNBT();
            compoundnbt.putString("id", "minecraft:lightning_bolt");
            ServerWorld serverworld = p_198737_0_.getLevel();
            Entity entity = EntityType.loadEntityRecursive(compoundnbt, serverworld, (p_218914_1_) -> {
                p_218914_1_.moveTo(p_198737_2_.x, p_198737_2_.y, p_198737_2_.z, p_218914_1_.yRot, p_218914_1_.xRot);
                return p_218914_1_;
            });
            if (entity == null) {
                throw ERROR_FAILED.create();
            } else {
                if (entity instanceof MobEntity) {
                    ((MobEntity)entity).finalizeSpawn(p_198737_0_.getLevel(), p_198737_0_.getLevel().getCurrentDifficultyAt(entity.blockPosition()), SpawnReason.COMMAND, (ILivingEntityData)null, (CompoundNBT)null);
                }

                if (!serverworld.tryAddFreshEntityWithPassengers(entity)) {
                    throw ERROR_DUPLICATE_UUID.create();
                } else {
                    p_198737_0_.sendSuccess(new TranslationTextComponent("Lanzaste un rayo"), true);
                    return 1;
                }
            }
        }
    }
}
