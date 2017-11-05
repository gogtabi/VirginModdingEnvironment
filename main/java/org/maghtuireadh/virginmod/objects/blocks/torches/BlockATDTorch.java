package org.maghtuireadh.virginmod.objects.blocks.torches;

import java.util.Random;

import org.maghtuireadh.virginmod.Main;
import org.maghtuireadh.virginmod.init.BlockInit;
import org.maghtuireadh.virginmod.init.ItemInit;
import org.maghtuireadh.virginmod.objects.tools.AtdTorch;
import org.maghtuireadh.virginmod.tileentity.TileEntityATDTorch;
import org.maghtuireadh.virginmod.util.Utils;
import org.maghtuireadh.virginmod.util.handlers.ListHandler;
import org.maghtuireadh.virginmod.util.interfaces.IFireStarter;
import org.maghtuireadh.virginmod.util.interfaces.IHasModel;
import org.maghtuireadh.virginmod.util.interfaces.IIgnitable;

import net.minecraft.block.BlockTorch;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public class BlockATDTorch extends BlockTorch implements IHasModel, ITileEntityProvider, IIgnitable, IFireStarter
{
	public Long burnTime = (long) 0;
	public Long setTime = (long) 0;
	public float lightlevel, rainchance;
	private Item item;
	public static PropertyBool LIT = PropertyBool.create("lit");	

	public BlockATDTorch(String name, float lightlevel, float rainChance, long burntime, Item item) 
	{
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setLightLevel(lightlevel);
		this.lightlevel = lightlevel;
		this.rainchance = rainChance;
		burnTime = burntime;
		this.item = item;		
		this.setTickRandomly(true);
		this.setDefaultState(this.getDefaultState().withProperty(FACING, EnumFacing.UP).withProperty(LIT, false));
		Blocks.FIRE.setFireInfo(this, 60, 20);
		//setCreativeTab(Main.virginmodtab);
		BlockInit.BLOCKS.add(this);
		//ItemInit.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}
	
	@SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.TRANSLUCENT;
    }
	
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
    	if(stateIn.getValue(LIT))
    	{
    		EnumFacing enumfacing = (EnumFacing)stateIn.getValue(FACING);
    		double d0 = (double)pos.getX() + 0.5D;
    		double d1 = (double)pos.getY() + 0.8D;
    		double d2 = (double)pos.getZ() + 0.5D;
    		double d3 = 0.22D;
    		double d4 = 0.27D;
    	
    		if (enumfacing.getAxis().isHorizontal())
    		{
    			EnumFacing enumfacing1 = enumfacing.getOpposite();
    			worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d0 + 0.27D * (double)enumfacing1.getFrontOffsetX(), d1 + 0.22D, d2 + 0.27D * (double)enumfacing1.getFrontOffsetZ(), 0.0D, 0.0D, 0.0D);
    			worldIn.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, d0 + 0.27D * (double)enumfacing1.getFrontOffsetX(), d1 + 0.22D, d2 + 0.27D * (double)enumfacing1.getFrontOffsetZ(), 0.0D, 0.0D, 0.0D);
    		}
    		else
    		{
	            worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
	            worldIn.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, d0, d1, d2, 0.0D, 0.0D, 0.0D);
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
		
		NBTTagCompound nbt = world.getTileEntity(pos).getTileData();
		byte id = nbt.getId();
		long start = nbt.getLong("start");
		long timeset = nbt.getLong("timeset");
		//Utils.getLogger().info("BlockATD:time elapsed= "+(world.getTotalWorldTime()-start)+", burntimr= "+burnTime+", timeset= "+timeset+", on tagID= "+id);
		if(start != 0 && timeset != 0)
		{
			return (timeset - (world.getTotalWorldTime()-start));
		}
		else
		{
			return burnTime;
		}
	}


	@Override
	public void setFuel(ItemStack stack, long fuel, World world, BlockPos pos) {
		((TileEntityATDTorch)world.getTileEntity(pos)).setTime(fuel, burnTime);
	}

	@Override
	public boolean attemptIgnite(int igniteChance, World world, BlockPos pos, EntityPlayer player) 
	{
		if(!world.getBlockState(pos).getValue(LIT))
		{
			world.setBlockState(pos, world.getBlockState(pos).withProperty(LIT, true));
			long gettime = ((TileEntityATDTorch)world.getTileEntity(pos)).getTime();
			if (gettime == 0)
			{
				gettime = burnTime;
			}
			setFuel(player.getHeldItemMainhand(),gettime,world,pos);
			return true;
		}
		else
		{
			//Utils.getLogger().info("BlockATD:block was alrdy lit");
			return false;
		}
		
	}
	
	public boolean extinguish(World world, BlockPos pos, EntityPlayer playa)
	{
		if (world.getBlockState(pos).getValue(LIT))
		{
			long gettime = ((TileEntityATDTorch)world.getTileEntity(pos)).getTime();
			world.setBlockState(pos, world.getBlockState(pos).withProperty(LIT, false));
			((TileEntityATDTorch)world.getTileEntity(pos)).setTime(gettime,burnTime);
			
			return true;
		}

		//Utils.getLogger().info("BlockATD:ext block in block fail");
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
		if(world.isRainingAt(pos.up()) && rand.nextFloat() < rainchance)
		{
			this.extinguish(world, pos, player);
		}
	}
	
	@Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
		ItemStack stack = playerIn.getHeldItemMainhand();
		Item item = stack.getItem();
		String name = item.getRegistryName() + "-" + item.getMetadata(stack);
		if(stack.getItem() instanceof IIgnitable)
		{
			return false; 
		}
		if (ListHandler.SnufferList.contains(name))
		{
			this.extinguish(worldIn, pos, playerIn);
			return true;
		}
		if(stack.isEmpty())
		{
			ItemStack torch = new ItemStack(this.item);
			AtdTorch newItem = (AtdTorch)torch.getItem();
			playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem, newItem.setBurnTime(burnTime-((TileEntityATDTorch)worldIn.getTileEntity(pos)).getTime(), torch));
			if(this.getMetaFromState(state)>=5)
			{
			newItem.attemptIgnite(100, worldIn, pos, playerIn);
			}
		}
		return false;
	}
		
	@Override
	public void registerModels() 
	{
		Main.proxy.registerVariantRenderer(Item.getItemFromBlock(this), (0), getUnlocalizedName().substring(5) , "facing=up,lit=false");
		Main.proxy.registerVariantRenderer(Item.getItemFromBlock(this), (1), getUnlocalizedName().substring(5) , "facing=north,lit=false");
		Main.proxy.registerVariantRenderer(Item.getItemFromBlock(this), (2), getUnlocalizedName().substring(5) , "facing=south,lit=false");
		Main.proxy.registerVariantRenderer(Item.getItemFromBlock(this), (3), getUnlocalizedName().substring(5) , "facing=east,lit=false");
		Main.proxy.registerVariantRenderer(Item.getItemFromBlock(this), (4), getUnlocalizedName().substring(5) , "facing=west,lit=false");
		Main.proxy.registerVariantRenderer(Item.getItemFromBlock(this), (5), getUnlocalizedName().substring(5) , "facing=up,lit=true");
		Main.proxy.registerVariantRenderer(Item.getItemFromBlock(this), (6), getUnlocalizedName().substring(5) , "facing=north,lit=true");
		Main.proxy.registerVariantRenderer(Item.getItemFromBlock(this), (7), getUnlocalizedName().substring(5) , "facing=south,lit=true");
		Main.proxy.registerVariantRenderer(Item.getItemFromBlock(this), (8), getUnlocalizedName().substring(5) , "facing=east,lit=true");
		Main.proxy.registerVariantRenderer(Item.getItemFromBlock(this), (9), getUnlocalizedName().substring(5) , "facing=west,lit=true");
		
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
		{
			meta = (meta + 5) - 1;
		}
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
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        return getMetaFromState(state) > 4 ? (int)(this.lightlevel*15.0F) : 0;
    }
	
	static double low = 0.4D;
	static double high = 0.95D;
	static double lowhold = 0.20000000298023224D;
	static double highhold = 0.800000011920929D;
	
    protected static final AxisAlignedBB STANDING_AABB = new AxisAlignedBB(0.4000000059604645D, 0.0D, 0.4000000059604645D, 0.6000000238418579D, 0.6000000238418579D, 0.6000000238418579D);
    protected static final AxisAlignedBB TORCH_NORTH_AABB = new AxisAlignedBB(0.3499999940395355D, low, 0.699999988079071D, 0.6499999761581421D, high, 1.0D);
    protected static final AxisAlignedBB TORCH_SOUTH_AABB = new AxisAlignedBB(0.3499999940395355D, low, 0.0D, 0.6499999761581421D, high, 0.30000001192092896D);
    protected static final AxisAlignedBB TORCH_WEST_AABB = new AxisAlignedBB(0.699999988079071D, low, 0.3499999940395355D, 1.0D, high, 0.6499999761581421D);
    protected static final AxisAlignedBB TORCH_EAST_AABB = new AxisAlignedBB(0.0D, low, 0.3499999940395355D, 0.30000001192092896D, high, 0.6499999761581421D);
    
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        switch ((EnumFacing)state.getValue(FACING))
        {
            case EAST:
                return TORCH_EAST_AABB;
            case WEST:
                return TORCH_WEST_AABB;
            case SOUTH:
                return TORCH_SOUTH_AABB;
            case NORTH:
                return TORCH_NORTH_AABB;
            default:
                return STANDING_AABB;
        }
    }
}
	
	

	

