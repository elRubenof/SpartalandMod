package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.Blocks;
import net.minecraft.block.VineBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;

public class VinesFeature extends Feature<NoFeatureConfig> {
   private static final Direction[] DIRECTIONS = Direction.values();

   public VinesFeature(Codec<NoFeatureConfig> p_i232002_1_) {
      super(p_i232002_1_);
   }

   public boolean place(ISeedReader p_241855_1_, ChunkGenerator p_241855_2_, Random p_241855_3_, BlockPos p_241855_4_, NoFeatureConfig p_241855_5_) {
      BlockPos.Mutable blockpos$mutable = p_241855_4_.mutable();

      for(int i = 64; i < 256; ++i) {
         blockpos$mutable.set(p_241855_4_);
         blockpos$mutable.move(p_241855_3_.nextInt(4) - p_241855_3_.nextInt(4), 0, p_241855_3_.nextInt(4) - p_241855_3_.nextInt(4));
         blockpos$mutable.setY(i);
         if (p_241855_1_.isEmptyBlock(blockpos$mutable)) {
            for(Direction direction : DIRECTIONS) {
               if (direction != Direction.DOWN && VineBlock.isAcceptableNeighbour(p_241855_1_, blockpos$mutable, direction)) {
                  p_241855_1_.setBlock(blockpos$mutable, Blocks.VINE.defaultBlockState().setValue(VineBlock.getPropertyForFace(direction), Boolean.valueOf(true)), 2);
                  break;
               }
            }
         }
      }

      return true;
   }
}