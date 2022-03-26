package com.bcr.spartaland.api;

import com.bcr.spartaland.block.Sphere;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

public class SphereApi {

    public static void inSphere(PlayerEntity player, int radius) {
        if (checkSphere(player, radius)) {
            player.kill();
        }

    }

    public static boolean checkSphere(PlayerEntity player, int radius) {
        boolean RR = false;
        BlockPos pos = player.blockPosition();

        for (int x = pos.getX() - radius; x < pos.getX() + radius; x++) {
            for (int y = pos.getY() - radius; y < pos.getY() + radius; y++) {
                for (int z = pos.getZ() - radius; z < pos.getZ() + radius; z++) {
                    Block block = player.level.getBlockState(new BlockPos(x, y, z)).getBlock();
                    Vector3d spherePos = new Vector3d(x, y, z);

                    if (block instanceof Sphere && player.position().distanceTo(spherePos) < radius) {
                        RR = true;
                    }
                }
            }
        }
        return RR;
    }
}
