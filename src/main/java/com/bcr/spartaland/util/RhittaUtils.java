package com.bcr.spartaland.util;

import com.bcr.spartaland.Spartaland;
import com.bcr.spartaland.item.Rhitta;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import virtuoel.pehkui.api.ScaleData;
import virtuoel.pehkui.api.ScaleTypes;

public class RhittaUtils {

    public static void checker(PlayerEntity player) {
        final ScaleData data = ScaleTypes.BASE.getScaleData(player);
        boolean rhitta = player.getPersistentData().getBoolean(Spartaland.MOD_ID + ":Rhitta");
        boolean rhitta2 = player.getPersistentData().getBoolean(Spartaland.MOD_ID + ":Rhitta2");
        boolean handed = hasRhitta(player);
        World world = player.level;

        if (handed && world.isDay() && !world.isRaining() && !world.isThundering() && !world.isClientSide()) {
            if (!rhitta) {
                set(player, true);
                data.setTargetScale(1.3f);
            }
            if (rhitta && !rhitta2 && theOne(player)) {
                set2(player, true);
                data.setTargetScale(1.5f);
            }
            if (rhitta && rhitta2 && !theOne(player)) {
                set2(player, false);
                data.setTargetScale(1.3f);
            }
        } else {
            if (rhitta) {
                set(player, false);
                set2(player, false);
                data.setTargetScale(1.0f);
            }
        }

        if (rhitta2) {
            player.addEffect(new EffectInstance(Effects.DAMAGE_BOOST, 15, 6, false, false));
            player.addEffect(new EffectInstance(Effects.DAMAGE_RESISTANCE, 15, 2, false, false));
            player.addEffect(new EffectInstance(Effects.JUMP, 15, 1, false, false));
            player.addEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 15, 0, false, false));
        } else if (rhitta) {
            player.addEffect(new EffectInstance(Effects.DAMAGE_BOOST, 15, 3, false, false));
            player.addEffect(new EffectInstance(Effects.DAMAGE_RESISTANCE, 15, 1, false, false));
            player.addEffect(new EffectInstance(Effects.JUMP, 15, 0, false, false));
            player.addEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 15, 0, false, false));
        }

        if (rhitta) {
            Utils.dropArmor(player, true);
        }

        if (handed && theOne(player) && world.isClientSide()) {
            particles(player, world);
        }
    }

    private static boolean hasRhitta(PlayerEntity player) {
        return player.getMainHandItem().getItem() instanceof Rhitta;
    }

    private static boolean theOne(PlayerEntity player) {
        return player.level.getDayTime() > 5400 && player.level.getDayTime() < 6600;
    }

    private static void set(PlayerEntity player, boolean bool) {
        player.getPersistentData().putBoolean(Spartaland.MOD_ID + ":Rhitta", bool);
    }

    private static void set2(PlayerEntity player, boolean bool) {
        player.getPersistentData().putBoolean(Spartaland.MOD_ID + ":Rhitta2", bool);
    }

    private static void particles(PlayerEntity player, World world) {

            double d0 = player.getRandom().nextGaussian() * 0.02D;
            double d1 = player.getRandom().nextGaussian() * 0.02D;
            double d2 = player.getRandom().nextGaussian() * 0.02D;
            world.addParticle(ParticleTypes.FLAME, player.getRandomX(1.0D), player.getRandomY(), player.getRandomZ(1.0D), d0, d1, d2);

    }
}
