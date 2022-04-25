package com.bcr.spartaland.util;

import com.bcr.spartaland.Spartaland;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TpSelectorUtils {
    public final static BlockPos pasarela = new BlockPos(-1097, 77, -1826);
    public final static BlockPos vacio = new BlockPos(-1095, 77, -1826);
    public final static BlockPos desierto = new BlockPos(-1093, 77, -1826);
    public final static BlockPos hogwarts = new BlockPos(-1091, 77, -1826);
    public final static BlockPos undergarden = new BlockPos(-1089, 77, -1826);
    public final static BlockPos xxx = new BlockPos(-1087, 77, -1826);
    private Minecraft mc = Minecraft.getInstance();

    public TpSelectorUtils() {
    }

    public void pasarela() {
        mc.player.chat("/openportal Pasarela");
    }

    public void vacio() {
        mc.player.chat("/openportal Vacio");
    }

    public void desierto() {
        mc.player.chat("/openportal Desierto");
    }

    public void hogwarts() {
        mc.player.chat("/openportal Hogwarts");
    }

    public void undergarden() {
        mc.player.chat("/openportal Undergarden");
    }

    public void xxx() {
        mc.player.chat("/openportal xxx");
    }

    public static boolean check(World world) {
        return !world.getBlockState(pasarela).getBlock().equals(Blocks.REDSTONE_BLOCK)
                && !world.getBlockState(vacio).getBlock().equals(Blocks.REDSTONE_BLOCK)
                && !world.getBlockState(desierto).getBlock().equals(Blocks.REDSTONE_BLOCK)
                && !world.getBlockState(hogwarts).getBlock().equals(Blocks.REDSTONE_BLOCK)
                && !world.getBlockState(undergarden).getBlock().equals(Blocks.REDSTONE_BLOCK)
                && !world.getBlockState(xxx).getBlock().equals(Blocks.REDSTONE_BLOCK);
    }

    public static boolean isActive(World world, int i) {
        switch (i) {
            case 0:
                return world.getBlockState(new BlockPos(pasarela.getX(),pasarela.getY(),-1827)).getBlock().equals(Blocks.STONE);
            case 1:
                return world.getBlockState(new BlockPos(vacio.getX(),vacio.getY(),-1827)).getBlock().equals(Blocks.STONE);
            case 2:
                return world.getBlockState(new BlockPos(desierto.getX(),desierto.getY(),-1827)).getBlock().equals(Blocks.STONE);
            case 3:
                return world.getBlockState(new BlockPos(hogwarts.getX(),hogwarts.getY(),-1827)).getBlock().equals(Blocks.STONE);
            case 4:
                return world.getBlockState(new BlockPos(undergarden.getX(),undergarden.getY(),-1827)).getBlock().equals(Blocks.STONE);
            case 5:
                return world.getBlockState(new BlockPos(xxx.getX(),xxx.getY(),-1827)).getBlock().equals(Blocks.STONE);
            default:
                return false;
        }
    }
}
