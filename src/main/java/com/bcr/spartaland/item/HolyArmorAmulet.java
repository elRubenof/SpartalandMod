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
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Optional;

public class HolyArmorAmulet extends Item {
    private double durability = 0;
    private PlayerEntity player = null;

    public HolyArmorAmulet() {
        super(new Properties().fireResistant());
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (!Utils.wearHolyArmor(player)) {
            player.inventory.removeItem(player.getMainHandItem());
            Utils.dropArmor(player, false);
            Utils.equipArmor(player, ModItems.HOLY_ARMOR_HEAD.get().getDefaultInstance(), ModItems.HOLY_ARMOR_CHEST.get().getDefaultInstance(), ModItems.HOLY_ARMOR_LEGS.get().getDefaultInstance(), ModItems.HOLY_ARMOR_FEET.get().getDefaultInstance());
        }

        return super.use(world, player, hand);
    }
}
