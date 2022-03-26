package com.bcr.spartaland.item;

import com.bcr.spartaland.Spartaland;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemTier;
import net.minecraft.item.SwordItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Spartaland.MOD_ID);

    public static final RegistryObject<Item> RHITTA = ITEMS.register("rhitta",
            () -> new Rhitta());

    public static void register(IEventBus eventBus) {

        ITEMS.register(eventBus);
    }
}
