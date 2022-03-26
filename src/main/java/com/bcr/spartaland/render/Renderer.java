package com.bcr.spartaland.render;

import com.bcr.spartaland.Spartaland;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import org.lwjgl.opengl.GL11;

import java.util.Arrays;

public class Renderer {
    public static void drawBoundingBoxAtBlockPos(MatrixStack matrixStackIn, AxisAlignedBB aabbIn, float red, float green, float blue, float alpha, BlockPos pos) {
        Vector3d cam = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
        double camX = cam.x, camY = cam.y, camZ = cam.z;

        matrixStackIn.pushPose();
        RenderSystem.lineWidth(2.0F);
        matrixStackIn.translate(pos.getX() - camX, pos.getY() - camY, pos.getZ() - camZ);
        drawBoundingBox(matrixStackIn, aabbIn.minX, aabbIn.minY, aabbIn.minZ, aabbIn.maxX, aabbIn.maxY, aabbIn.maxZ, red, green, blue, alpha, red, green, blue);
        matrixStackIn.popPose();
    }

    private static void drawBoundingBox(MatrixStack matrixStackIn, double minX, double minY, double minZ, double maxX, double maxY, double maxZ, float red, float green, float blue, float alpha, float red2, float green2, float blue2) {
        Matrix4f matrix4f = matrixStackIn.last().pose();
        float f = (float)minX;
        float f1 = (float)minY;
        float f2 = (float)minZ;
        float f3 = (float)maxX;
        float f4 = (float)maxY;
        float f5 = (float)maxZ;

        int r = 1;

        float x[] = new float[11];
        float y[] = new float[11];

        float xf[] = new float[20];
        float yf[] = new float[20];

        for (float i = 0; i < x.length; i++) {
            float y0 = (float) Math.sqrt(Math.pow(r*10, 2) - Math.pow(i, 2));

            x[(int) i] = i/10;
            y[(int) i] = y0/10;
        }

        for (int i = 0; i < x.length; i++) {
            xf[i] = x[i];
            yf[i] = y[i];
        }

        for (int i = 11; i < xf.length; i++) {
            xf[i] = y[i-10];
            yf[i] = x[i-10];
        }

        Arrays.sort(xf);
        Arrays.sort(yf);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferIn = tessellator.getBuilder();

        bufferIn.begin(1, DefaultVertexFormats.POSITION_COLOR);
        for (int i = 0, z = yf.length-1; i < xf.length - 1; i++, z--) {

            bufferIn.vertex(matrix4f, f + xf[i], f1 + yf[z], f2).color(red, green2, blue2, alpha).endVertex();
            bufferIn.vertex(matrix4f, f + xf[i + 1], f1 + yf[z - 1], f2).color(red, green2, blue2, alpha).endVertex();
        }
        Tessellator.getInstance().end();

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
    }
}
