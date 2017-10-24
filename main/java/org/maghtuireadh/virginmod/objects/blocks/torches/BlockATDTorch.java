package org.maghtuireadh.virginmod.objects.blocks.torches;

import java.util.Random;

import org.maghtuireadh.virginmod.Main;
import org.maghtuireadh.virginmod.init.BlockInit;
import org.maghtuireadh.virginmod.init.ItemInit;
import org.maghtuireadh.virginmod.tileentity.TileEntityATDTorch;
import org.maghtuireadh.virginmod.util.Utils;
import org.maghtuireadh.virginmod.util.handlers.ListHandler;
import org.maghtuireadh.virginmod.util.interfaces.IFireStarter;
import org.maghtuireadh.virginmod.util.interfaces.IHasModel;
import org.maghtuireadh.virginmod.util.interfaces.IIgnitable;

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
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public class BlockATDTorch extends BlockTorch implements IHasModel, ITileEntityProvider, IIgnitable, IFireStarter
{
	public Long burnTime = (long) 0;
	public Long setTime = (long) 0;
	public float lightlevel;
	public static PropertyBool LIT = PropertyBool.create("lit");	

	public BlockATDTorch(String name, float lightlevel) 
	{
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setLightLevel(lightlevel);
		this.lightlevel = lightlevel;
		this.setTickRandomly(true);
		this.setDefaultState(this.getDefaultState().withProperty(FACING, EnumFacing.UP).withProperty(LIT, false));
		Blocks.FIRE.setFireInfo(this, 60, 20);
		//setCreativeTab(Main.virginmodtab);
		BlockInit.BLOCKS.add(this);
		//ItemInit.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}
	
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
    	if(stateIn.getValue(LIT))
    	{
    		EnumFacing enumfacing = (EnumFacing)stateIn.getValue(FACING);
    		double d0 = (double)pos.getX() + 0.5D;
    		double d1 = (double)pos.getY() + 0.7D;
    		double d2 = (double)pos.getZ() + 0.5D;
    		double d3 = 0.22D;
    		double d4 = 0.27D;
    	
    		if (enumfacing.getAxis().isHorizontal())
    		{
    			EnumFacing enumfacing1 = enumfacing.getOpposite();
    			worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + 0.27D * (double)enumfacing1.getFrontOffsetX(), d1 + 0.22D, d2 + 0.27D * (double)enumfacing1.getFrontOffsetZ(), 0.0D, 0.0D, 0.0D);
    			worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + 0.27D * (double)enumfacing1.getFrontOffsetX(), d1 + 0.22D, d2 + 0.27D * (double)enumfacing1.getFrontOffsetZ(), 0.0D, 0.0D, 0.0D);
    		}
    		else
    		{
	            worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, 0.0D, 0.0D, 0.0D);
	            worldIn.spawnParticle(EnumParticleTypes.FLAME, d0, d1, d2, 0.0D, 0.0D, 0.0D);
    		}
    	}
    }

	@Override
	public boolean isLit(World world, BlockPos pos, EntityPlayer player) 
	{
		if (world.getBlockState(pos).getValue(LIT))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	public long getFuel(World world, BlockPos pos, EntityPlayer player) {	
		
		long start = world.getTileEntity(pos).getUpdateTag().getLong("start");
		long timeset = world.getTileEntity(pos).getUpdateTag().getLong("timeset");
		Utils.getLogger().info("start time= "+start+", timeset= "+timeset);
		if(start != 0 && timeset != 0)
		{
			return world.getTotalWorldTime()-start-timeset;
		}
		else
		{
			return burnTime;
		}
	}


	@Override
	public void setFuel(long fuel, World world, BlockPos pos, EntityPlayer player) {
		((TileEntityATDTorch)world.getTileEntity(pos)).setTime(fuel, pos);
	}

	@Override
	public boolean attemptIgnite(int igniteChance, World world, BlockPos pos, EntityPlayer player) 
	{
		if(!world.getBlockState(pos).getValue(LIT))
		{
			world.setBlockState(pos, world.getBlockState(pos).withProperty(LIT, true));
			Utils.getLogger().info("light block in block");
			return true;
		}
		else
		{
			Utils.getLogger().info("block was alrdy lit");
			return false;
		}
		
	}
	
	public boolean extinguish(World world, BlockPos pos, EntityPlayer playa)
	{
		if (world.getBlockState(pos).getValue(LIT))
		{
			world.setBlockState(pos, world.getBlockState(pos).withProperty(LIT, false));

			Utils.getLogger().info("ext block in block");
			return true;
		}

		Utils.getLogger().info("ext block in block fail");
		return false;
	}
	
	@Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {FACING,LIT});
    }
	
	
	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) 
	{
		EntityPlayer player = null;
		if(world.isRainingAt(pos.up()) && rand.nextFloat() < 0.4f)
		{
			this.extinguish(world, pos, player);
		}
	}
	
	@Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
		ItemStack stack = playerIn.getHeldItemMainhand();
		String heldItemName = stack.getItem().getRegistryName() + "-" + stack.getMetadata();	
		if(stack.getItem() instanceof IIgnitable)
		{
			Utils.getLogger().info("block: item is ignitable" + heldItemName);
			return false; 
		}
		if (ListHandler.ExtinguishList.contains(heldItemName))
		{
			Utils.getLogger().info("ext-list" + heldItemName);
			this.extinguish(worldIn, pos, playerIn);
			return true;
		}
		if(stack.isEmpty())
		{
			Utils.getLogger().info("hand is empty");
			playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem, new ItemStack(ItemInit.ATD_TORCH));
			IIgnitable item = (IIgnitable)playerIn.inventory.getStackInSlot(playerIn.inventory.currentItem).getItem();
			long time = getFuel(worldIn, pos, playerIn);
			item.setFuel(time, worldIn, pos, playerIn);
			Utils.getLogger().info("set item fuel to" + time);
			if(this.getMetaFromState(state)>=5)
			{
				item.attemptIgnite(100, worldIn, pos, playerIn);
				Utils.getLogger().info("try to light the item");
			}
			worldIn.setBlockToAir(pos);
			worldIn.removeTileEntity(pos);

			Utils.getLogger().info("killed block and TE");
			return true;
		}
		else
		{

			Utils.getLogger().info("hand not empty");
			return false;
		}
		
	}
		
	@Override
	public void registerModels() 
	{
		String facing,lit;
		for(int i = 0; i < 2; i++)
		{
			if(i==0) {lit="false";}
			else {lit="true";}
			for(int j = 0; j < 5; j++)
			{
				if(j==0) {facing="up";}
				else if(j==1) {facing="east";}
				else if(j==2) {facing="south";}
				else if(j==3) {facing="west";}
				else {facing="north";}
				Main.proxy.registerVariantRenderer(Item.getItemFromBlock(this), (((i+1)*(j+1))-1), this.getUnlocalizedName().substring(5), "facing="+facing+","+"lit="+lit);
			}
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
		{meta = 1;}
		if (state.getValue(FACING) == EnumFacing.NORTH)
		{meta = 2;}
		if (state.getValue(FACING) == EnumFacing.SOUTH)
		{meta = 3;}
		if (state.getValue(FACING) == EnumFacing.EAST)
		{meta = 4;}
		if (state.getValue(FACING) == EnumFacing.WEST)
		{meta = 5;}
		
		if (state.getValue(LIT))
		{meta = (meta + 5) - 1;}
		else
		{meta = meta - 1;}
		
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
	
	@Override
    public int getLightValue(IBlockState state)
    {
        return getMetaFromState(state) > 4 ? MathHelper.floor(this.lightlevel * 15.0F) : 0;
    }
}
	
	

	

