package com.bcr.spartaland.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

public class Funko extends Block {
    public Funko() {
        super(Block.Properties.of(Material.STONE).strength(-1.0F, 3600000.0F));
    }

    @Override
    public BlockState rotate(BlockState state, IWorld world, BlockPos pos, Rotation direction) {
        return super.rotate(state, world, pos, direction);
    }

    @Override
    public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
        VoxelShape shape = VoxelShapes.empty();
        shape = VoxelShapes.join(shape, VoxelShapes.box(0.3125, 0, 0.375, 0.6875, 0.0234375, 0.65625), IBooleanFunction.OR);
        shape = VoxelShapes.join(shape, VoxelShapes.box(0.3125, 0, 0.375, 0.5703125, 0.09375, 0.3984375), IBooleanFunction.OR);
        shape = VoxelShapes.join(shape, VoxelShapes.box(0.5703125, 0.28125, 0.375, 0.6875, 0.3046875, 0.3984375), IBooleanFunction.OR);
        shape = VoxelShapes.join(shape, VoxelShapes.box(0.59375, 0.3046875, 0.375, 0.6875, 0.3984375, 0.3984375), IBooleanFunction.OR);
        shape = VoxelShapes.join(shape, VoxelShapes.box(0.3125, 0.3984375, 0.375, 0.6875, 0.4921875, 0.65625), IBooleanFunction.OR);
        shape = VoxelShapes.join(shape, VoxelShapes.box(0.59375, 0, 0.375, 0.6875, 0.3984375, 0.65625), IBooleanFunction.OR);
        shape = VoxelShapes.join(shape, VoxelShapes.box(0.3125, 0, 0.6328125, 0.59375, 0.3984375, 0.65625), IBooleanFunction.OR);
        shape = VoxelShapes.join(shape, VoxelShapes.box(0.3125, 0.09375, 0.5625, 0.3359375, 0.3984375, 0.6328125), IBooleanFunction.OR);
        shape = VoxelShapes.join(shape, VoxelShapes.box(0.3125, 0, 0.3984375, 0.3359375, 0.09375, 0.6328125), IBooleanFunction.OR);
        shape = VoxelShapes.join(shape, VoxelShapes.box(0.359375, 0.2109375, 0.421875, 0.546875, 0.3984375, 0.609375), IBooleanFunction.OR);
        shape = VoxelShapes.join(shape, VoxelShapes.box(0.40625, 0.1171875, 0.4921875, 0.5, 0.2109375, 0.5390625), IBooleanFunction.OR);
        shape = VoxelShapes.join(shape, VoxelShapes.box(0.40625, 0.0234375, 0.4921875, 0.453125, 0.1171875, 0.5390625), IBooleanFunction.OR);
        shape = VoxelShapes.join(shape, VoxelShapes.box(0.5, 0.1171875, 0.4921875, 0.546875, 0.2109375, 0.5390625), IBooleanFunction.OR);
        shape = VoxelShapes.join(shape, VoxelShapes.box(0.359375, 0.1171875, 0.4921875, 0.40625, 0.2109375, 0.5390625), IBooleanFunction.OR);
        shape = VoxelShapes.join(shape, VoxelShapes.box(0.453125, 0.0234375, 0.4921875, 0.5, 0.1171875, 0.5390625), IBooleanFunction.OR);
        shape = VoxelShapes.join(shape, VoxelShapes.box(0.5703125, 0, 0.375, 0.59375, 0.28125, 0.3984375), IBooleanFunction.OR);
        shape = VoxelShapes.join(shape, VoxelShapes.box(0.6640625, 0, 0.375, 0.6875, 0.28125, 0.3984375), IBooleanFunction.OR);
        shape = VoxelShapes.join(shape, VoxelShapes.box(0.59375, 0.09375, 0.375, 0.6875, 0.28125, 0.3984375), IBooleanFunction.OR);
        shape = VoxelShapes.join(shape, VoxelShapes.box(0.640625, 0, 0.375, 0.6875, 0.09375, 0.3984375), IBooleanFunction.OR);
        shape = VoxelShapes.join(shape, VoxelShapes.box(0.59375, 0, 0.375, 0.640625, 0.09375, 0.3984375), IBooleanFunction.OR);

        return shape;
    }

    @Override
    public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
        return super.getLightValue(state, world, pos);
    }
}

