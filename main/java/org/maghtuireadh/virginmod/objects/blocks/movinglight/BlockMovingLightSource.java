package org.maghtuireadh.virginmod.objects.blocks.movinglight;

import javax.annotation.Nullable;

import org.maghtuireadh.virginmod.init.BlockInit;
import org.maghtuireadh.virginmod.tileentity.TileEntityMovingLightSource;
import org.maghtuireadh.virginmod.util.Reference;

import net.minecraft.block.BlockAir;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockMovingLightSource extends BlockAir implements ITileEntityProvider 
{
	private EntityPlayer player;

	public BlockMovingLightSource(String name) 
	{
		super();
		this.setUnlocalizedName(name);
		this.setRegistryName(new ResourceLocation(Reference.MODID, name));
		BlockInit.BLOCKS.add(this);
		this.setDefaultState(getDefaultState());
		setLightLevel(0.9F);
	}
	
	@Override
	public boolean isReplaceable(IBlockAccess world, BlockPos pos) 
	{
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) 
	{
		return new TileEntityMovingLightSource().setPlayer(player);
	}

	public BlockMovingLightSource setPlayer(EntityPlayer player) 
	{
		this.player = player;
		return this;
	}

	  
	    /**
	     * The type of render function called. MODEL for mixed tesr and static model, MODELBLOCK_ANIMATED for TESR-only,
	     * LIQUID for vanilla liquids, INVISIBLE to skip all rendering
	     */
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state)
	{
	    return EnumBlockRenderType.INVISIBLE;
	}

	@Nullable
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
	{
	    return NULL_AABB;
	}

	    /**
	     * Used to determine ambient occlusion and culling when rebuilding chunks for render
	     */
	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
	    return false;
	}
	    
	@Override
	public boolean canCollideCheck(IBlockState state, boolean hitIfLiquid)
	{
		return false;
	}

	public boolean isFullCube(IBlockState state)
	{
	   return false;
	}

	public BlockFaceShape getBlockFaceShape(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_)
	{
	    return BlockFaceShape.UNDEFINED;
	}
}
