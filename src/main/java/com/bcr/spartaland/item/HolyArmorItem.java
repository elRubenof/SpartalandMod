package com.bcr.spartaland.item;

import com.bcr.spartaland.Spartaland;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import com.bcr.spartaland.client.model.armor.HolyArmorModel;

import java.util.Objects;

public class HolyArmorItem extends CustomArmorItem {

    public HolyArmorItem(EquipmentSlotType slot) {
        super("holy_armor", ModArmorMaterial.HOLY, slot);
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        CompoundNBT nbt;

        if (stack.hasTag()) {
            nbt = stack.getTag();
        } else {
            nbt = new CompoundNBT();
        }

        int time = nbt.getInt(Spartaland.MOD_ID + ":Time");

        if (time != 0) {
            if (time > 1) {
                nbt.putInt(Spartaland.MOD_ID + ":Time", time - 1);
                Spartaland.LOGGER.info("TIEMPO RESTANTE: " + time);
            } else {
                player.inventory.removeItem(stack);
                Spartaland.LOGGER.info("ELIMINADO");
            }
        } else {
            nbt.putInt(Spartaland.MOD_ID + ":Time", 1200);
            Spartaland.LOGGER.info("INTRODUCIDO TIEMPO");
        }

        stack.setTag(nbt);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public BipedModel<?> createModel(LivingEntity entityLiving) {
        return new HolyArmorModel<>(this.slot, true);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public BipedModel<?> createSlimModel(LivingEntity entityLiving) {
        return new HolyArmorModel<>(this.slot, false);
    }
}
