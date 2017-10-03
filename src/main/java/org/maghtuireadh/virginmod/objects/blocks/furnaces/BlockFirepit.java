package org.maghtuireadh.virginmod.objects.blocks.furnaces;

import java.util.Random;

import javax.annotation.Nullable;

import org.maghtuireadh.virginmod.Main;
import org.maghtuireadh.virginmod.init.BlockInit;
import org.maghtuireadh.virginmod.init.ItemInit;
import org.maghtuireadh.virginmod.tileentity.TileEntityFirepit;
import org.maghtuireadh.virginmod.util.Reference;
import org.maghtuireadh.virginmod.util.interfaces.IHasModel;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;



public class BlockFirepit extends Block implements IHasModel,ITileEntityProvider {
	protected static final AxisAlignedBB FIREPIT_AABB = new AxisAlignedBB(1.5D, 0.0D, 1.5D, -0.5D, .4D, -0.5D);
	public static final PropertyInteger PITSTATE = PropertyInteger.create("pitstate", 0, 11);
	public static IBlockState[] states = new IBlockState[12];
	boolean Burning;

	public BlockFirepit(String unlocalizedName, Material material) {

	super(material);
	this.setUnlocalizedName(unlocalizedName);
	this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
	BlockInit.BLOCKS.add(this);
	//BlockInit.BLOCKS.add(blockState2.getBlock());
	ItemInit.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	//ItemInit.ITEMS.add(new ItemBlock(blockState2.getBlock()).setRegistryName(this.getRegistryName()));
	this.setCreativeTab(Main.virginmodtab);
	for (int i = 0; i < 12; i++) {
        this.states[i] =  this.blockState.getBaseState().withProperty(PITSTATE, i);
        }
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
    
    
	public void setLit() {
		//this. 
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] {PITSTATE});
		//return new BlockStateContainer(this, new IProperty[] {LIT});
	}
	
	@Override
	public int getLightValue(IBlockState state) {
		switch (getMetaFromState(state)) {
		case 0: return 0; //empty_firepit
				
		case 1: return 0; //unlit_firepit1
			
		case 2: return 0; //unlit_firepit2
				
		case 3: return 0; //unlit_firepit3
			
		case 4: return 4; //lit_firepit1
			
		case 5: return 8; //lit_firepit2
			
		case 6: return 10; //lit_firepit3
		
		case 7: return 12; //lit_firepit4
			
		case 8: return 0; //dirty_firepit

		case 9: return 0; //extinguished_firepit1
		
		case 10: return 0; //extinguished_firepit2
			
		case 11: return 0; //extinguished_firepit3
			
		default: return 0;
			}
	}

	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY,
			float hitZ, int meta, EntityLivingBase placer) {
		return this.getDefaultState().withProperty(PITSTATE, 0);
	}
	

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		
		worldIn.setBlockState(pos, state.withProperty(PITSTATE, 0));
	}
	
	@Override

	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {

	 }

	public boolean getBurning()
	{
		return Burning;
	}
	public void setBurning(boolean bool)
	{
		this.Burning = bool;
	}

	public int getState(World worldIn, BlockPos pos) {
		IBlockState state = worldIn.getBlockState(pos);
		return getMetaFromState(state);
	}
	
	public void setState(int meta, World worldIn, BlockPos pos) {
		IBlockState state = worldIn.getBlockState(pos);
		TileEntity tileentity = worldIn.getTileEntity(pos);
		worldIn.setBlockState(pos, BlockInit.BLOCK_FIREPIT.getDefaultState().withProperty(PITSTATE, meta));

		this.getLightValue(state);
		
		if(tileentity != null) {
			tileentity.validate();
			worldIn.setTileEntity(pos, tileentity);
		}
	}
			
	
	public int quantityDropped(Random random){

			 return 6;

	}			

	public Item getItemDropped(IBlockState state, Random rand, int fortune){

					return Item.getItemFromBlock(Blocks.STONE_SLAB);

	}
	
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		this.blockState.getBaseState().withProperty(PITSTATE, Integer.valueOf(0));
		this.setBurning(false);
	}

	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileEntityFirepit tileentity = (TileEntityFirepit)worldIn.getTileEntity(pos);
		
		super.breakBlock(worldIn, pos, state);
	}
	
	public EnumBlockRenderType getREnderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)

    {

        if (worldIn.isRemote)

        {

            return true;

        }

        else

        {

            TileEntityFirepit tileentity = (TileEntityFirepit) worldIn.getTileEntity(pos);

           	ItemStack heldItem = playerIn.getHeldItemMainhand();

           	tileentity.setFuelValues(heldItem);

           	return true;    

           }



     }

 

	
	@Override

	public TileEntity createNewTileEntity(World worldIn, int meta) {

		return new TileEntityFirepit();

	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY,
			float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		return this.getDefaultState().withProperty(PITSTATE, 0);
	}
	
	/**
	 * Returns the correct meta for the block
	 * I recommend also saving the EnumFacing to the meta but I haven't
	 */
	
	
/*	@Override
	public int getMetaFromState(IBlockState state) {
		if(state == blockState2) {
			return 0;
		}
		else {
			return 1;
		}
	}*/
	@Override
	public int getMetaFromState(IBlockState state) {
		int meta = state.getValue(PITSTATE);
		return meta;
		}
	

	/**
	 * Gets the block state from the meta
	 */
	@Override
	public IBlockState getStateFromMeta(int meta) {
			return BlockFirepit.states[meta];
		}
			
/*		if(meta==0) {
		return blockState2;
		} //Returns the correct state
		else {
			return blockState1;
		}*/

	
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
		
	@Override
	public void registerModels() {
		for (int i=0;i<states.length-1;i++)
		{
		Main.proxy.registerVariantRenderer(Item.getItemFromBlock(this), i, "block_firepit", "states="+i);
		}
	}
	
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.CUTOUT;
	}
	
	@Override
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos,
			EnumFacing side) {
		// TODO Auto-generated method stub
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
	

	
}
