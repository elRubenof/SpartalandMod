package com.bcr.spartaland.item;

import com.bcr.spartaland.Spartaland;
import com.bcr.spartaland.util.Utils;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class HolyArmorAmulet extends Item {
    public HolyArmorAmulet() {
        super(new Properties().fireResistant());
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        Utils.dropArmor(player, false);
        Utils.equipArmor(player, ModItems.HOLY_ARMOR_HEAD.get().getDefaultInstance(), ModItems.HOLY_ARMOR_CHEST.get().getDefaultInstance(), ModItems.HOLY_ARMOR_LEGS.get().getDefaultInstance(), ModItems.HOLY_ARMOR_FEET.get().getDefaultInstance());

        CompoundNBT nbt = itemstack.getTag();
        nbt.putDouble(Spartaland.MOD_ID + ":Time", 12001);
        itemstack.setTag(nbt);

        return ActionResult.sidedSuccess(itemstack, world.isClientSide());
    }

    @Override
    public boolean showDurabilityBar(ItemStack itemStack) {
        return true;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        CompoundNBT nbt;

        if (stack.hasTag()) {
            nbt = stack.getTag();
        } else {
            nbt = new CompoundNBT();
        }

        double time = nbt.getDouble(Spartaland.MOD_ID + ":Time");

        if (time != 0) {
            if (time == 1) {
                Spartaland.LOGGER.info("LLENO");
            }
            if (time < 1200) {
                nbt.putDouble(Spartaland.MOD_ID + ":Time", time + 1);
                Spartaland.LOGGER.info("TIEMPO RESTANTE: " + time/1200);
            } else {
                Spartaland.LOGGER.info("AGOTADO");
            }
        } else {
            nbt.putDouble(Spartaland.MOD_ID + ":Time", 1);
            Spartaland.LOGGER.info("INTRODUCIDO TIEMPO");
        }

        stack.setTag(nbt);

        return time/1200;
    }
}
