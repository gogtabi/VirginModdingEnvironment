package org.maghtuireadh.virginmod.objects.blocks.torches;

import java.util.Random;

import org.maghtuireadh.virginmod.Main;
import org.maghtuireadh.virginmod.init.BlockInit;
import org.maghtuireadh.virginmod.init.ItemInit;
import org.maghtuireadh.virginmod.tileentity.TileEntityATDTorch;
import org.maghtuireadh.virginmod.util.Utils;
import org.maghtuireadh.virginmod.util.interfaces.IHasModel;

import net.minecraft.block.BlockTorch;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockATDTorch extends BlockTorch implements IHasModel,ITileEntityProvider 
{
	

	public Long burnTime = (long) 0;
	public Long setTime = (long) 0;
	public static Item[] FireStarters = new Item[] {Items.FLINT_AND_STEEL,ItemInit.ATD_EMBER_BUNDLE,ItemInit.ATD_TORCH};
	public static PropertyBool LIT = PropertyBool.create("lit");	
	/**
	 * Default constructor which sets the hardness and resistance
	 * @param unlocalizedName The unlocalized name
	 */
	public BlockATDTorch(String name) 
	{
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setLightLevel(1.0F);
		this.setTickRandomly(true);
		this.setDefaultState(this.getDefaultState().withProperty(FACING, EnumFacing.UP).withProperty(LIT, false));
		Blocks.FIRE.setFireInfo(this, 60, 20);
		//setCreativeTab(Main.virginmodtab);
		BlockInit.BLOCKS.add(this);
		//ItemInit.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}
	
	
	public IBlockState setTime(IBlockState blockstate, int time, World world)
	{
		this.burnTime = (long)time;
		this.setTime = world.getTotalWorldTime();
		return blockstate;
	}
	
	public void extinguish(World world, BlockPos pos)
	{
		world.setBlockToAir(pos);
	}

	@Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {FACING,LIT});
    }
	
	
	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) 
	{
		
		if(world.getTotalWorldTime() - this.setTime > burnTime)
		{
		//world.setBlockToAir(pos);
		}
	}
	
	@Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
		playerIn.inventory.setItemStack(new ItemStack(ItemInit.ATD_TORCH));
		
        return true;
    }
	
	@Override
	public void registerModels() 
	{
		for(int i = 0; i < 9; i++) 
		{
		Main.proxy.registerVariantRenderer(Item.getItemFromBlock(this), i, "Block_atd_Torch", "state="+i);
		}
	}

	@Override
	 public int quantityDropped(Random random)
    {
        return 0;
    }
	
	@Override
	public int getMetaFromState(IBlockState state) 
	{
		int meta = 0;
		if (state.getValue(FACING) == EnumFacing.UP)
		{
			meta = 1;
		}
		if (state.getValue(FACING) == EnumFacing.NORTH)
		{
			meta = 2;
		}
		if (state.getValue(FACING) == EnumFacing.SOUTH)
		{
			meta = 3;
		}
		if (state.getValue(FACING) == EnumFacing.EAST)
		{
			meta = 4;
		}
		if (state.getValue(FACING) == EnumFacing.WEST)
		{
			meta = 5;
		}
		if (state.getValue(LIT))
		{
			meta = (meta * 2) - 1;
		}
		else
		{
			meta = meta - 1;
		}
		return meta;
		}
	
	
	@Override
	public IBlockState getStateFromMeta(int meta) 
	{
		switch (meta)
		{
			case 0:
				return this.getDefaultState().withProperty(FACING, EnumFacing.UP).withProperty(LIT, false);
			case 1:
				return this.getDefaultState().withProperty(FACING, EnumFacing.NORTH).withProperty(LIT, false);
			case 2:
				return this.getDefaultState().withProperty(FACING, EnumFacing.SOUTH).withProperty(LIT, false);
			case 3:
				return this.getDefaultState().withProperty(FACING, EnumFacing.EAST).withProperty(LIT, false);
			case 4:
				return this.getDefaultState().withProperty(FACING, EnumFacing.WEST).withProperty(LIT, false);
			case 5:
				return this.getDefaultState().withProperty(FACING, EnumFacing.UP).withProperty(LIT, true);
			case 6:
				return this.getDefaultState().withProperty(FACING, EnumFacing.NORTH).withProperty(LIT, true);
			case 7:
				return this.getDefaultState().withProperty(FACING, EnumFacing.SOUTH).withProperty(LIT, true);
			case 8:
				return this.getDefaultState().withProperty(FACING, EnumFacing.EAST).withProperty(LIT, true);
			case 9:
				return this.getDefaultState().withProperty(FACING, EnumFacing.WEST).withProperty(LIT, true);
			default:
				return this.getDefaultState();
		}
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) 
	{
		return new TileEntityATDTorch();
	}
}
	
	

	

