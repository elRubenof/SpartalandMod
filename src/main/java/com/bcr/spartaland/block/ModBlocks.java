package com.bcr.spartaland.block;

import com.bcr.spartaland.Spartaland;
import com.bcr.spartaland.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS
            = DeferredRegister.create(ForgeRegistries.BLOCKS, Spartaland.MOD_ID);

    public static final RegistryObject<Block> INEALD_DEAD = registerBlock("ineald_dead", Ineald::new, true);
    public static final RegistryObject<Block> FERTILE_DIRT = registerBlock("fertile_dirt", FertileDirt::new, false);
    public static final RegistryObject<Block> SPHERE = registerBlock("sphere", Sphere::new, true);

    private static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block, boolean hide) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);

        if (!hide) {
            registerBlockItem(name, toReturn);
        } else {
            registerHide(name, toReturn);
        }

        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties().tab(ItemGroup.TAB_MISC)));
    }

    private static <T extends Block> void registerHide(String name, RegistryObject<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
