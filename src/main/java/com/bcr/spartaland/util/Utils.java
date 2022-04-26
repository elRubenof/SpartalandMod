package com.bcr.spartaland.util;

import com.bcr.spartaland.Spartaland;
import com.bcr.spartaland.item.HolyArmorAmulet;
import com.bcr.spartaland.item.HolyArmorItem;
import com.bcr.spartaland.item.ModItems;
import com.bcr.spartaland.item.Rhitta;
import com.bcr.spartaland.screen.TpSelectorScreen;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

import javax.annotation.Nullable;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

public class Utils {

    public static void houseTeleport(PlayerEntity player) {
        boolean sneak = player.isShiftKeyDown();
        Vector3d pos = player.position();
        boolean shift = player.getPersistentData().getBoolean(Spartaland.MOD_ID + "shift");

        Vector3d min = new Vector3d(-1199,0,-1717);
        Vector3d max = new Vector3d(-1186,0,-1712);

        if (!shift && sneak) {
            player.getPersistentData().putBoolean(Spartaland.MOD_ID + "shift", true);

            if (pos.x > min.x && pos.x < max.x && pos.z > min.z && pos.z < max.z && (pos.y == 71 || pos.y == 33 && !player.level.isClientSide())) {
                player.moveTo(pos.x, pos.y == 71 ? 33 : 71, pos.z);
            }

        } else if (!sneak && shift) {
            player.getPersistentData().putBoolean(Spartaland.MOD_ID + "shift", false);
        }
    }

    public static String getVersion() throws IOException {
        URL url = new URL("https://raw.githubusercontent.com/elRubenof/SpartalandMod/main/VERSION.txt");
        String RR = "";

        BufferedReader in = new BufferedReader(
                new InputStreamReader(url.openStream()));

        String inputLine;
        while ((inputLine = in.readLine()) != null)
            RR = inputLine;
        in.close();

        return RR;
    }

    public static void versionChecker(PlayerEvent.PlayerLoggedInEvent event) throws IOException {
        String version = Utils.getVersion();
        String updateWarn = "Hay una nueva version del mod " + TextFormatting.GOLD + "Spartaland" + TextFormatting.WHITE + " disponible. Actualizalo para evitar errores.";

        if (!version.equals(Spartaland.VERSION)) {
            event.getPlayer().sendMessage(ITextComponent.nullToEmpty(updateWarn), event.getPlayer().getUUID());
        }
    }

    public static void dropArmor(PlayerEntity player, boolean shield) {
        player.drop(player.getItemBySlot(EquipmentSlotType.HEAD), true);
        player.drop(player.getItemBySlot(EquipmentSlotType.CHEST), true);
        player.drop(player.getItemBySlot(EquipmentSlotType.LEGS), true);
        player.drop(player.getItemBySlot(EquipmentSlotType.FEET), true);

        if (shield) {
            player.drop(player.getItemBySlot(EquipmentSlotType.OFFHAND), true);
        }

        removeArmor(player, shield);
    }

    public static void removeArmor(PlayerEntity player, boolean shield) {
        player.inventory.removeItem(player.getItemBySlot(EquipmentSlotType.HEAD));
        player.inventory.removeItem(player.getItemBySlot(EquipmentSlotType.CHEST));
        player.inventory.removeItem(player.getItemBySlot(EquipmentSlotType.LEGS));
        player.inventory.removeItem(player.getItemBySlot(EquipmentSlotType.FEET));

        if (shield) {
            player.inventory.removeItem(player.getItemBySlot(EquipmentSlotType.OFFHAND));
        }
    }

    public static void equipArmor(PlayerEntity player, ItemStack helmet, ItemStack chestplaate, ItemStack leggins, ItemStack boots) {
        player.setItemSlot(EquipmentSlotType.HEAD, helmet);
        player.setItemSlot(EquipmentSlotType.CHEST, chestplaate);
        player.setItemSlot(EquipmentSlotType.LEGS, leggins);
        player.setItemSlot(EquipmentSlotType.FEET, boots);
    }

    public static boolean isOp(PlayerEntity player) {
        return player.getStringUUID().equals("380df991-f603-344c-a090-369bad2a924a")
                || player.getStringUUID().equals("4d17d85a-accc-49ae-9a4f-b6b5a965cf1e");
    }

    public static boolean isLegenday(Item item) {
        return item instanceof Rhitta //PUENTE
                || item instanceof HolyArmorAmulet //BOLUDO
                || item.getRegistryName().toString().equals("mahoutsukai:theripper") //LARO
                || item.getRegistryName().toString().equals("mahoutsukai:weapon_projectile_bow") //ALVARO
                || item.getRegistryName().toString().equals("mahoutsukai:clarent") //RUBEN
                || item.getRegistryName().toString().equals("relics:space_dissector") //NATALIA
                || item.getRegistryName().toString().equals("villagers_and_mosnetrs_legacy:exp_sword") //SERGIO
                || item.getRegistryName().toString().equals("villagers_and_mosnetrs_legacy:pink_blade") //CLAUDIA
                || item.getRegistryName().toString().equals("bmorph:metasword"); //MALLAVIA
    }

    public static Duration time() {
        LocalDateTime start = LocalDateTime.of(2022, 4, 4, 18, 35);
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Europe/Madrid"));

        return Duration.between(now, start);
    }

    public static ITextComponent timer() {
        Duration time = time();
        String hours = String.format("%02d", time.toHours());
        String minutes = String.format("%02d", time.toMinutes() % 60);
        String seconds = String.format("%02d", time.getSeconds() % 60);

        return ITextComponent.nullToEmpty(TextFormatting.GREEN + "" + TextFormatting.BOLD + "Desastre de mana: " + hours + ":" + minutes + ":" + seconds);
    }

    public static boolean wearHolyArmor(PlayerEntity player) {
        Item item = player.inventory.armor.get(1).getItem();
        return item instanceof HolyArmorItem;
    }
}
