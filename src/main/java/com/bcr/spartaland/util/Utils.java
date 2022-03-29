package com.bcr.spartaland.util;

import com.bcr.spartaland.Spartaland;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class Utils {

    public static void houseTeleport(InputUpdateEvent event) {
        PlayerEntity player = event.getPlayer();
        Vector3d pos = player.position();
        boolean shift = player.getPersistentData().getBoolean(Spartaland.MOD_ID + "shift");

        Vector3d min = new Vector3d(-1199,0,-1717);
        Vector3d max = new Vector3d(-1186,0,-1712);

        if (event.getMovementInput().shiftKeyDown && !shift) {
            player.getPersistentData().putBoolean(Spartaland.MOD_ID + "shift", true);

            if (pos.x > min.x && pos.x < max.x && pos.z > min.z && pos.z < max.z && (pos.y == 71 || pos.y == 33)) {
                player.moveTo(pos.x, pos.y == 71 ? 33 : 71, pos.z);
            }

        } else if (!event.getMovementInput().shiftKeyDown && shift) {
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
        String updateWarn = "Hay una nueva versión del mod " + TextFormatting.GOLD + "Spartaland" + TextFormatting.WHITE + " disponible. Actualízalo para evitar errores.";

        if (!version.equals(Spartaland.VERSION)) {
            event.getPlayer().sendMessage(ITextComponent.nullToEmpty(updateWarn), event.getPlayer().getUUID());
        }
    }
}
