package com.bcr.spartaland.item;

import net.minecraft.block.Block;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTier;
import net.minecraft.item.ToolItem;

import java.util.Set;

public class Rhitta extends ToolItem {

    public Rhitta() {
        super(4F, -3.0F, ItemTier.NETHERITE, null, new Item.Properties().fireResistant().durability(0));
    }
}
