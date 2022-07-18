package com.bcr.spartaland.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Set;

public class Rhitta extends AxeItem {
    public Rhitta() {
        super(ItemTier.NETHERITE, 4F, -3.0F, new Item.Properties().fireResistant().durability(0));
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return false;
    }
}
