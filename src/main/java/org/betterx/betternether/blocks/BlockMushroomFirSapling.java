package org.betterx.betternether.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;

import org.betterx.bclib.blocks.FeatureSaplingBlock;
import org.betterx.betternether.BlocksHelper;
import org.betterx.betternether.interfaces.SurvivesOnNetherMycelium;
import org.betterx.betternether.registry.features.TreeFeatures;


public class BlockMushroomFirSapling extends FeatureSaplingBlock implements BonemealableBlock, SurvivesOnNetherMycelium {

    public BlockMushroomFirSapling() {
        super((state) -> TreeFeatures.MUSHROOM_FIR);
    }

    @Override
    protected boolean mayPlaceOn(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return isSurvivable(blockState);
    }

    @Override
    public boolean isBonemealSuccess(Level world, RandomSource random, BlockPos pos, BlockState state) {
        return BlocksHelper.isFertile(world.getBlockState(pos.below()))
                ? (random.nextInt(8) == 0)
                : (random.nextInt(16) == 0);
    }
}