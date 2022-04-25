package com.bcr.spartaland.item;

import com.bcr.spartaland.Spartaland;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static net.minecraft.inventory.EquipmentSlotType.*;
import static net.minecraft.inventory.EquipmentSlotType.FEET;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Spartaland.MOD_ID);

    public static final RegistryObject<Item> RHITTA = ITEMS.register("rhitta",
            () -> new Rhitta());

    public static final RegistryObject<Item> HOLY_ARMOR_AMULET = ITEMS.register("holy_armor_amulet",
            () -> new HolyArmorAmulet());

    public static final RegistryObject<Item> LIGHTNING_SPEAR = ITEMS.register("lightning_spear",
            () -> new LightningSpear());

    public static final RegistryObject<Item> HOLY_ARMOR_HEAD = reg(new HolyArmorItem(HEAD));
    public static final RegistryObject<Item> HOLY_ARMOR_CHEST = reg(new HolyArmorItem(CHEST));
    public static final RegistryObject<Item> HOLY_ARMOR_LEGS = reg(new HolyArmorItem(LEGS));
    public static final RegistryObject<Item> HOLY_ARMOR_FEET = reg(new HolyArmorItem(FEET));

    public static void register(IEventBus eventBus) {

        ITEMS.register(eventBus);
    }

    private static RegistryObject<Item> reg(CustomArmorItem item){
        return reg(item.getItemPartName(), item);
    }

    public static RegistryObject<Item> reg(String name, Item item) {
        return ITEMS.register(name, () -> item);
    }
}
