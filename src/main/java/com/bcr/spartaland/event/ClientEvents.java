package com.bcr.spartaland.event;

import com.bcr.spartaland.Spartaland;
import com.bcr.spartaland.screen.TpSelectorScreen;
import com.bcr.spartaland.util.TpSelectorUtils;
import com.bcr.spartaland.util.Utils;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.IOException;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void onLogin(PlayerEvent.PlayerLoggedInEvent event) throws IOException {
        Utils.versionChecker(event);
    }

    @SubscribeEvent
    public static void onPlayerInteract(PlayerInteractEvent event) {
        PlayerEntity player = event.getPlayer();
        World world = event.getWorld();
        BlockState block = world.getBlockState(event.getPos());

        if (block.getBlock().equals(Blocks.STRIPPED_OAK_LOG) && player.position().distanceTo(new Vector3d(-1093,82, -1825)) < 8) {
            Minecraft mc = Minecraft.getInstance();
            if (TpSelectorUtils.check(world)) {
                mc.setScreen(new TpSelectorScreen(world, player));
            }
        }
    }
}
