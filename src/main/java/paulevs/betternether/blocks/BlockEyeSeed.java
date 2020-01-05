package paulevs.betternether.blocks;

import java.util.Random;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.entity.EntityContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import paulevs.betternether.structures.plants.StructureEye;

public class BlockEyeSeed extends BlockBaseNotFull implements Fertilizable
{
	protected static final VoxelShape SHAPE = Block.createCuboidShape(4, 6, 4, 12, 16, 12);
	protected static final StructureEye STRUCTURE = new StructureEye();
	
	public BlockEyeSeed()
	{
		super(FabricBlockSettings.of(Material.PLANT)
				.materialColor(MaterialColor.RED)
				.sounds(BlockSoundGroup.CROP)
				.nonOpaque()
				.dropsNothing()
				.breakInstantly()
				.noCollision()
				.ticksRandomly()
				.build());
		this.setRenderLayer(BNRenderLayer.CUTOUT);
	}
	
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, EntityContext ePos)
	{
		return SHAPE;
	}

	@Override
	public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient)
	{
		return true;
	}

	@Override
	public boolean canGrow(World world, Random random, BlockPos pos, BlockState state)
	{
		return random.nextInt(4) == 0 && world.getBlockState(pos.down()).getBlock() == Blocks.AIR;
	}

	@Override
	public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state)
	{
		STRUCTURE.generate(world, pos, random);
	}

	@Override
	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos)
	{
		BlockPos blockPos = pos.up();
		return world.getBlockState(blockPos).getBlock() == Blocks.NETHERRACK;
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction facing, BlockState neighborState, IWorld world, BlockPos pos, BlockPos neighborPos)
	{
		BlockPos blockPos = pos.up();
		if (world.getBlockState(blockPos).getBlock() != Blocks.NETHERRACK)
			return Blocks.AIR.getDefaultState();
		else
			return state;
	}
	
	@Override
	public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random)
	{
		super.scheduledTick(state, world, pos, random);
		if (canGrow(world, random, pos, state))
		{
			grow(world, random, pos, state);
		}
	}
	
	public boolean isTranslucent(BlockState state, BlockView view, BlockPos pos)
	{
		return true;
	}
}
