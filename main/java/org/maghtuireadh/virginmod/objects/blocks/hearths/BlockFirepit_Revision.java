
package org.maghtuireadh.virginmod.objects.blocks.hearths;

import java.util.Random;

import javax.annotation.Nullable;

import org.maghtuireadh.virginmod.Main;
import org.maghtuireadh.virginmod.init.BlockInit;
import org.maghtuireadh.virginmod.init.ItemInit;
import org.maghtuireadh.virginmod.tileentity.TileEntityFirepit;
import org.maghtuireadh.virginmod.tileentity.TileEntityFirepit_Revision;
import org.maghtuireadh.virginmod.util.Reference;
import org.maghtuireadh.virginmod.util.handlers.EnumHandler.LitStates;
import org.maghtuireadh.virginmod.util.interfaces.IHasModel;

import akka.util.Switch;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockFirepit_Revision extends Block implements IHasModel, ITileEntityProvider {

	protected static final AxisAlignedBB FIREPIT_AABB = new AxisAlignedBB(1.5D, 0.0D, 1.5D, -0.5D, .4D, -0.5D);
	public static final PropertyEnum<LitStates> LIT_STATE = PropertyEnum.<LitStates>create("litstate", LitStates.class);
	public static final PropertyInteger FUELLEVEL = PropertyInteger.create("fuellevel", 0, 3);
	public static final PropertyBool ISLIT = PropertyBool.create("islit");
	public static final PropertyBool STOKED = PropertyBool.create("stoked");
	public static IBlockState[] states = new IBlockState[16];
	private boolean isLit = false;
	private boolean isStoked = false;
	
	public BlockFirepit_Revision(String unlocalizedName,Material material) {
		/*
		 * Initialization Block
		 */
		super(material);
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
		BlockInit.BLOCKS.add(this);
		ItemInit.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
		this.setCreativeTab(Main.virginmodtab);
		for (int i = 0, j = 0; i < 16 && j < 4; i++) {
			states[i] = this.blockState.getBaseState().withProperty(FUELLEVEL,j).withProperty(ISLIT, false).withProperty(STOKED, false);
			i++;
			states[i] = this.blockState.getBaseState().withProperty(FUELLEVEL,j).withProperty(ISLIT, true).withProperty(STOKED, false);
			i++;
			states[i] = this.blockState.getBaseState().withProperty(FUELLEVEL,j).withProperty(ISLIT, false).withProperty(STOKED, true);
			i++;
			states[i] = this.blockState.getBaseState().withProperty(FUELLEVEL,j).withProperty(ISLIT, true).withProperty(STOKED, true);
			j++;}
		}
	/*
	 * Block Activation - Right Click, check for tool in hand
	 */
		
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) 
	{
		
		if(worldIn.isRemote) {
			return true;
		}
		else {
			TileEntityFirepit_Revision tileentity = (TileEntityFirepit_Revision) worldIn.getTileEntity(pos);
			ItemStack heldItem = playerIn.getHeldItemMainhand();
			tileentity.onRightClick(heldItem, playerIn);
			
			return true;
		}
		
	}
	
	/*============================================================================
	 *                         Getters & Setters
	  ============================================================================*/
	@Override
	public void registerModels() {
		for (int i=0;i<states.length-1;i++)
		{
			Main.proxy.registerVariantRenderer(Item.getItemFromBlock(this), i, "block_firepit_revision", "states="+i);
		}
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {

		return new TileEntityFirepit_Revision();

	}
	
	public int getMetaFromState(IBlockState state) {
		IBlockState thisState = null;
		int meta=0;
		for(int i=0; i< states.length-1 && state!=thisState ;i++)
		{
			thisState=states[i];
			meta=i;
		}
		return meta;
	}
	
	public IBlockState getStateFromMeta(int meta) {
		return states[meta];

	}
	
	@Override 
	public int getLightValue(IBlockState state) {
		int fuel = state.getValue(FUELLEVEL);
		boolean lit = state.getValue(ISLIT);
		boolean stoked = state.getValue(STOKED);
		
		switch (fuel) {
		case 0:
			if(lit==false) {
				return 0;	
			}
			else {
				return 6;
			}
		case 1:
			if(lit==false) {
				return 0;
			}
			else if(stoked==true) {
				return 10;
			}
			else {
				return 8;
			}
		case 2:
			if(lit==false) {
				return 0;
			}
			else if(stoked==true) {
				return 12;
			}
			else {
				return 10;
			}
		case 3:
			if(lit==false) {
				return 0;
			}
			else if(stoked==true) {
				return 14;
			}
			else {
				return 12;
			}
		default:
			return 0;
		}
	}

	
	
	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY,
			float hitZ, int meta, EntityLivingBase placer) {
		return this.getDefaultState().withProperty(FUELLEVEL,0).withProperty(ISLIT, false).withProperty(STOKED, false);

	}
	

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {	
		worldIn.setBlockState(pos, state.withProperty(FUELLEVEL,0).withProperty(ISLIT, false).withProperty(STOKED, false));
	}
	
	public int quantityDropped(Random random){
	
		 return 6;
	
	}			
	
	public Item getItemDropped(IBlockState state, Random rand, int fortune){
	
				return Item.getItemFromBlock(Blocks.STONE_SLAB);
	
	}
	
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
	this.blockState.getBaseState().withProperty(FUELLEVEL,0).withProperty(ISLIT, false).withProperty(STOKED, false);
	}
	
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
	TileEntityFirepit tileentity = (TileEntityFirepit)worldIn.getTileEntity(pos);
	
	super.breakBlock(worldIn, pos, state);
	}
	
	public EnumBlockRenderType getRenderType(IBlockState state) {
	return EnumBlockRenderType.MODEL;
	}
	
	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY,
			float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		return this.blockState.getBaseState().withProperty(FUELLEVEL,0).withProperty(ISLIT, false).withProperty(STOKED, false);
	}
	/**
	 * Makes sure that when you pick block you get the right version of the block
	 */
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos,
			EntityPlayer player) {
		return new ItemStack(Item.getItemFromBlock(this), 1, (int) (getMetaFromState(world.getBlockState(pos))));
	}
	
	/**
	 * Makes the block drop the right version of the block from meta data
	 */

	@Override
	public int damageDropped(IBlockState state) {
		return (int) (getMetaFromState(state));
	}
	
	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
			items.add(new ItemStack(this, 1, 0));
		}
	
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.CUTOUT;
	}
	
	@Override
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos,
			EnumFacing side) {
		return true;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}
	
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return FIREPIT_AABB;
    }	
	
    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
    {
        return FIREPIT_AABB;
    }
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] {FUELLEVEL, ISLIT, STOKED});
	}
}
	

