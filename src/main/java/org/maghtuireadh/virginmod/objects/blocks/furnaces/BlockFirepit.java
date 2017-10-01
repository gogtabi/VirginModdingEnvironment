package org.maghtuireadh.virginmod.objects.blocks.furnaces;

import java.util.Random;

import org.maghtuireadh.virginmod.Main;
import org.maghtuireadh.virginmod.init.BlockInit;
import org.maghtuireadh.virginmod.init.ItemInit;
import org.maghtuireadh.virginmod.tileentity.TileEntityFirepit;
import org.maghtuireadh.virginmod.util.Reference;
import org.maghtuireadh.virginmod.util.handlers.EnumHandler;
import org.maghtuireadh.virginmod.util.handlers.EnumHandler.FirepitStatesTemp;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;



public class BlockFirepit extends BlockContainer implements ITileEntityProvider {

	
	public static final PropertyBool LIT = PropertyBool.create("lit");
	private IBlockState blockState1 = this.blockState.getBaseState().withProperty(LIT, Boolean.valueOf(false));
	private IBlockState blockState2 = this.blockState.getBaseState().withProperty(LIT, Boolean.valueOf(true));

	Float lightLvl;

	boolean Burning;



	public BlockFirepit(String unlocalizedName) {

	super(Material.ROCK);
	this.setUnlocalizedName(unlocalizedName);
	this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
	setCreativeTab(Main.virginmodtab);
	this.setDefaultState(this.blockState.getBaseState().withProperty(LIT, Boolean.valueOf(false)));
	BlockInit.BLOCKS.add(blockState1.getBlock());
	//BlockInit.BLOCKS.add(blockState2.getBlock());
	ItemInit.ITEMS.add(new ItemBlock(blockState1.getBlock()).setRegistryName(this.getRegistryName()));
	//ItemInit.ITEMS.add(new ItemBlock(blockState2.getBlock()).setRegistryName(this.getRegistryName()));
	
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] {LIT});
	}
	
	@Override
	public int getLightValue(IBlockState state) {
		;			if(state == blockState2) {
			return 15;
		}
		else {
			return 0;
		}
	}
	
	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY,
			float hitZ, int meta, EntityLivingBase placer) {
		// TODO Auto-generated method stub
		return this.getDefaultState().withProperty(LIT, false);
	}
	

	/*@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY,
			float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		return this.blockState1;
	}*/
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		
		worldIn.setBlockState(pos, state.withProperty(LIT, false));
	}
	
	@Override

	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {

	 }

	public void setBurning(boolean bool, float light)
	{
		this.Burning = bool;
	}

	public void setState(boolean isLit, World worldIn, BlockPos pos) {
		IBlockState state = worldIn.getBlockState(pos);
		TileEntity tileentity = worldIn.getTileEntity(pos);
		
		if(isLit) {
			worldIn.setBlockState(pos, BlockInit.BLOCK_FIREPIT.getDefaultState().withProperty(LIT, true));
		}
		if(!isLit) {
			worldIn.setBlockState(pos, BlockInit.BLOCK_FIREPIT.getDefaultState().withProperty(LIT, false));
		}
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
		this.blockState.getBaseState().withProperty(LIT, Boolean.valueOf(false));
		this.setBurning(false, 0.0F);
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

 

	@SideOnly(Side.CLIENT)

    @SuppressWarnings("incomplete-switch")

    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)

    {

        if (this.Burning)

        {

            double d0 = (double)pos.getX() + 0.5D;

            double d1 = (double)pos.getY() + rand.nextDouble() * 6.0D / 16.0D;

            double d2 = (double)pos.getZ() + 0.5D;

            double d3 = 0.52D;

            double d4 = rand.nextDouble() * 0.6D - 0.3D;



            if (rand.nextDouble() < 0.1D)

            {

                worldIn.playSound((double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.BLOCKS, 1.0F, 1.0F, false);

            }



            worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 + 0.52D, 0.0D, 0.0D, 0.0D);

            worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d4, d1, d2 + 0.52D, 0.0D, 0.0D, 0.0D);

            }

        }



	@Override

	public TileEntity createNewTileEntity(World worldIn, int meta) {

		return new TileEntityFirepit();

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
			if(state == blockState2) {
				return 0;
			}
			else {
				return 1;
			}
		}
	/**
	 * Gets the block state from the meta
	 */
	@Override
	public IBlockState getStateFromMeta(int meta) {
		if(meta==0) {
		return blockState2;
		} //Returns the correct state
		else {
			return blockState1;
		}
	
	
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
	
	


}	