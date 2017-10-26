package org.maghtuireadh.virginmod.objects.tools;

import javax.annotation.Nullable;

import org.maghtuireadh.virginmod.Main;
import org.maghtuireadh.virginmod.init.BlockInit;
import org.maghtuireadh.virginmod.init.ItemInit;
import org.maghtuireadh.virginmod.objects.blocks.hearths.BlockHearth;
import org.maghtuireadh.virginmod.objects.blocks.movinglight.BlockMovingLightSource;
import org.maghtuireadh.virginmod.objects.blocks.torches.BlockATDTorch;
import org.maghtuireadh.virginmod.tileentity.TileEntityMovingLightSource;
import org.maghtuireadh.virginmod.util.Utils;
//Utils;
import org.maghtuireadh.virginmod.util.handlers.ListHandler;
import org.maghtuireadh.virginmod.util.interfaces.IFireStarter;
import org.maghtuireadh.virginmod.util.interfaces.IHasModel;
import org.maghtuireadh.virginmod.util.interfaces.IIgnitable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import scala.xml.dtd.EMPTY;

public class AtdTorch extends ItemSword	 implements IHasModel, ITileEntityProvider, IIgnitable, IFireStarter
{
	

	BlockPos BP;
	IBlockState BSHold;
	EntityPlayer player;
	ItemStack stackSet = new ItemStack(Items.AIR);
	long burntTime = 0;
	private long BurnTime = 0;
	private int EntityFireTime, RainRes;
	private float RainChance, lightlevel;
	private int TimeAway = 30;
	public static Block ATD_TORCH;
	
	
	public static Material[] PlaceBlocks = new Material[] {Material.CLAY,Material.GRASS,Material.SAND,Material.SNOW,Material.CRAFTED_SNOW,Material.GROUND};
    /**
     * ATD torch set
     * @param name The name of the torch, used for unlocalized and registry name
     * @param material The item material, affects damage to entity
     * @param lightlevel the light level
     * @param burnTime How many ticks it can burn for
     * @param entityFireTime How long it sets entities on fire for
     * @param rainChance The chance that rain will damage res
     * @param rainRes The number of rain res ticks the item has
     * @param stackSize The size of the stack
     */
	
	public AtdTorch(String name, ToolMaterial material, float lightlevel, int burnTime, int entityFireTime, float rainChance, int rainRes, int stackSize) 
	{
		super(material);
		setUnlocalizedName("atd_torch_"+name);
		setRegistryName("atd_torch_"+name);
		ATD_TORCH = new BlockATDTorch("block_atd_torch_"+name, lightlevel);
		setCreativeTab(Main.virginmodtab);
		this.maxStackSize = stackSize;
		this.lightlevel = lightlevel;
		this.EntityFireTime = entityFireTime;
		BurnTime = (long)burnTime;
		RainRes = rainRes;
		RainChance = rainChance;
		setDamage(new ItemStack(this), 0);
		//Utils.getLogger().info("Torch:constructed torch");
		this.addPropertyOverride(new ResourceLocation("lit"), new IItemPropertyGetter() 
		{
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
            	if(stack.hasTagCompound()) 
            	{
            		return stack.getTagCompound().getBoolean("lit") ? 1.0F : 0.0F;
            	}
            		return 0.0F;
            }
        });
		ItemInit.ITEMS.add(this);
	}
	
	@Override
	public void registerModels()
	{
		Main.proxy.registerItemRenderer(this, 0, "inventory");
	}
	
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) 
	{
		//Utils.getLogger().info("Torch:constructed TE");
		return new TileEntityMovingLightSource().setPlayer(player, this.lightlevel);
	}
	
	public NBTTagCompound newNBT(ItemStack stack)
	{
		NBTTagCompound nbt = new NBTTagCompound();
		//nbt.setLong("burntime", (long)0);
		nbt.setFloat("rainchance", RainChance);
		nbt.setInteger("rainres", RainRes);
		nbt.setBoolean("lit", false);
		stack.setTagCompound(nbt);
		return nbt;
	}
	
	@Override
	public void onUpdate(final ItemStack stack, final World world, final Entity entityIn, final int itemSlot, final boolean isSelected)
	    {
		 	if (!world.isRemote) 
		 	{	
				NBTTagCompound nbt = stack.getTagCompound();
				player = (EntityPlayer)entityIn;
				BlockPos pos= new BlockPos(player.posX,player.posY+1,player.posZ);
				if(nbt == null)	
				{
					nbt = newNBT(stack);
					//setDamage(stack, 0);
					//Utils.getLogger().info("Torch:new nbt on torch");
				}
				else if (!nbt.hasKey("rainchance"))
				{
					setDamage(stack, 0);
					nbt.setFloat("rainchance", RainChance);
					nbt.setInteger("rainres", RainRes);
				}
				

				if(isLit(stack))// && nbt.hasKey("lit")) 
				{
					if(stackSet == stack)
					{
						setBurnTime(burntTime, stack);
						stackSet = new ItemStack(Items.AIR);
					}
					else
					{
						//Utils.getLogger().info("Torch:stacks aren't the same apparently\n                                       "+stackSet+"\n                                       "+stack);
					}
	                          				 if(isSelected && getTimeAway(stack) < this.TimeAway) 
					 {
						 setTimeAway(this.TimeAway, stack);
						 //Utils.getLogger().info("Torch:rst timeaway");
					 }
					 else if (!isSelected && player.getHeldItemOffhand().getItem() != ItemInit.ATD_TORCH)
					 {
						 setTimeAway(getTimeAway(stack)-1, stack);
						 //Utils.getLogger().info("Torch:deduct timeaway");
					 }
					  
					 if ((world.getTotalWorldTime() - nbt.getLong("worldtime")) + getBurnTime(stack) > this.BurnTime) 
					 {
						 //Utils.getLogger().info("Torch:shrunk: " + "cur world time: " + world.getTotalWorldTime() + " - set world time: " + nbt.getLong("worldtime") + " + get burn time: " + getBurnTime(stack) + " > " + this.BurnTime);
						 stack.shrink(1);
					 }
					 else if(getTimeAway(stack) <= 0) 
					 {
						 extinguish(world, pos, player, stack);
					 }
					 else
					 {
						 //Utils.getLogger().info(world.getTotalWorldTime()+" - "+ nbt.getLong("worldtime") +" + "+ getBurnTime(stack) +" > " + this.BurnTime);
						 
					 }
					 
					 if (world.isRainingAt(pos.up(1)))
					 {
						 int res = nbt.getInteger("rainres");
						 float cha = nbt.getFloat("rainchance");
						 float ran = world.rand.nextFloat();
						 if (ran*cha>60) 
						 {
							//Utils.getLogger().info(ran);
							nbt.setInteger("rainres", res-1);
							if (res<=0) 
							{
								extinguish(world, pos, player, stack);
								nbt.setInteger("rainres", RainRes);
							}
						 }
					  }
					  
					  if(world.isAirBlock(pos)) 
					  {
						 final BlockMovingLightSource lightSource = BlockInit.BLOCK_MLS;
						 BlockPos pos1 = new BlockPos(pos.getX()+1,pos.getY(),pos.getZ()+1);
						 BlockPos pos2 = new BlockPos(pos.getX()-1,pos.getY(),pos.getZ()-1);
						 BlockPos pos3 = new BlockPos(pos.getX()-1,pos.getY(),pos.getZ()+1);
						 BlockPos pos4 = new BlockPos(pos.getX()+1,pos.getY(),pos.getZ()-1);
						 world.setBlockState(pos, lightSource.setPlayer(player).getDefaultState());
						 if(world.isAirBlock(pos1)) 
						 {
							 world.setBlockState(pos1, lightSource.setPlayer(player).getDefaultState());	 
						 }
						 if(world.isAirBlock(pos2)) 
						 {
							 world.setBlockState(pos2, lightSource.setPlayer(player).getDefaultState());	 
						 }
						 if(world.isAirBlock(pos3)) 
						 {
							 world.setBlockState(pos3, lightSource.setPlayer(player).getDefaultState());	 
						 }
						 if(world.isAirBlock(pos4)) 
						 {
							 world.setBlockState(pos4, lightSource.setPlayer(player).getDefaultState());	 
						 }
						 
					  }	
				}
				stack.setTagCompound(nbt);
			
				////Utils.getLogger().info(stack+","+itemSlot+","+isSelected + "," +  getTimeAway() + "," + isLit() + "," + getBurnTime() + "," + nbt.getLong("worldtime") + "," + world.getTotalWorldTime() );		  
		 	}
	    }
		 
	@Override
	public boolean attemptIgnite(int igniteChance, World world, BlockPos pos, EntityPlayer player) {
		if (world.rand.nextInt(100)<igniteChance)
		{
			if(!player.getHeldItemMainhand().isEmpty())
			{
			setLit(true, world.getTotalWorldTime(), player.getHeldItemMainhand());
			return true;
			}
			else
			{
				//Utils.getLogger().info("torch: attempted to light nothing");
			}
		}
		return false;
	}

	@Override
	public boolean isLit(World world, BlockPos pos, EntityPlayer player) {
		return player.inventory.getCurrentItem().getTagCompound().getBoolean("lit");
	}

	@Override
	public boolean extinguish(World world, BlockPos pos, EntityPlayer player) {
		ItemStack stack = player.getHeldItemMainhand();
		return extinguish(world, pos, player, stack);
	}
	
	public boolean extinguish(World world, BlockPos pos, EntityPlayer player, ItemStack stack) 
	{
		NBTTagCompound nbt = stack.getTagCompound();
		setBurnTime(world.getTotalWorldTime() - (nbt.getLong("worldtime") - getBurnTime(stack)), stack);
		setLit(false, (long)0, stack);
		return true;
	}

	@Override
	public long getFuel(World world, BlockPos pos, EntityPlayer player) {
		NBTTagCompound nbt = player.inventory.getCurrentItem().getTagCompound(); 
		return nbt.getBoolean("lit") ? (world.getTotalWorldTime() - (nbt.getLong("worldtime") - nbt.getLong("burntime"))) : nbt.getLong("burntime");
	}

	@Override
	public void setFuel(ItemStack stack, long fuel, World world, BlockPos pos) 
	{	
		/*this.setBurnTime(fuel, stack);*/
		this.burntTime = fuel;
		this.stackSet = stack;
		//Utils.getLogger().info("Torch: fuel: "+fuel+", stack: "+stack);
		
	}
	
	public boolean isLit(ItemStack stack) 
	{
		NBTTagCompound nbt = stack.getTagCompound();
		boolean lit;
		if(nbt.hasKey("lit")) 
		{
			lit = nbt.getBoolean("lit");
		}
		else
		{
			lit = false;
		}
		return lit;
	}
	
	public  void setLit(boolean lit, long worldTime, ItemStack stack) 
	{
		NBTTagCompound nbt = stack.getTagCompound();
		if(nbt==null)
		{
			nbt = new NBTTagCompound();
		}
		if(stack.getCount()>1&&lit==true)
		{
			if(player.inventory.getFirstEmptyStack()!=-1)
			{
				ItemStack IS = new ItemStack(stack.getItem());
				IS.setCount(stack.getCount()-1);
				player.inventory.addItemStackToInventory(IS);
				player.inventory.getCurrentItem().setCount(1);
				nbt.setBoolean("lit", lit);
				nbt.setLong("worldtime", worldTime);
			}
		}
		else
		{
		nbt.setBoolean("lit", lit);
		nbt.setLong("worldtime", worldTime);
		}
		stack.setTagCompound(nbt);
	}
	
	public void setBurnTime(long burntime, ItemStack stack) 
	{
		NBTTagCompound nbt = stack.getTagCompound();
		if(nbt==null)
		{
			nbt = newNBT(stack);
			//Utils.getLogger().info("Torch: made new nbt for set burntime");
		}		
		nbt.setLong("burntime", burntime);
		//Utils.getLogger().info("Torch: set burntime to: " + burntime);
		stack.setTagCompound(nbt);
	}
	
	public  Long getBurnTime(ItemStack stack) 
	{
		NBTTagCompound nbt = stack.getTagCompound();
		long burntime;
		if(nbt==null)
		{
			nbt = newNBT(stack);
			//Utils.getLogger().info("Torch: made new nbt for get burntime");
		}		
		if(nbt.hasKey("burntime"))
		{
			burntime =  nbt.getLong("burntime");
			//Utils.getLogger().info("Torch: has burntime, got: " + burntime);
		}
		else
		{
			burntime = (long)0;
			//Utils.getLogger().info("Torch: no burntime, set to 0");
		}
		return burntime;
	}
	
	public void setTimeAway(int timeaway, ItemStack stack) 
	{
		NBTTagCompound nbt = stack.getTagCompound();
		nbt.setInteger("timeaway", timeaway);
		stack.setTagCompound(nbt);
	}
	
	public  int getTimeAway(ItemStack stack) 
	{
		NBTTagCompound nbt = stack.getTagCompound();
		int timeaway;
		if(nbt.hasKey("timeaway"))
		{
			timeaway = nbt.getInteger("timeaway");
		}
		else
		{
			timeaway = 0;	
		}
		return timeaway;
	}
	
	public boolean place(World world, ItemStack stack, IBlockState blockstate, BlockPos pos)
	{
			long time;
			IBlockState BS;
			NBTTagCompound nbt = stack.getTagCompound();
			if(isLit(stack))
			{
				BS = blockstate.withProperty(BlockATDTorch.LIT,true);
			}
			else
			{
				BS = blockstate.withProperty(BlockATDTorch.LIT,false);
			}
			
			world.setBlockState(pos, BS);
			//Utils.getLogger().info("Torch:placed it");
			if (nbt.getLong("worldtime") > 0)
			{
				time = BurnTime - ((world.getTotalWorldTime() - nbt.getLong("worldtime")) + getBurnTime(stack));
				//Utils.getLogger().info("Torch:place: burntime- " + BurnTime + ", world time- " + world.getTotalWorldTime()  +", set world time- " + nbt.getLong("worldtime") + ", get burntime- " + getBurnTime(stack));
			}
			else
			{
				time = BurnTime;
			}
			((IIgnitable) world.getBlockState(BP).getBlock()).setFuel(stack, time, world, pos);
			//Utils.getLogger().info("Torch:place: set fuel to " + time);
			//Utils.getLogger().info("Torch:place: itemstack b4 = " + player.inventory.getCurrentItem());
			player.inventory.getCurrentItem().shrink(1);//setCount(player.inventory.getCurrentItem().getCount() -1 );
			//Utils.getLogger().info("Torch:place: itemstack aftr = " + player.inventory.getCurrentItem());
			return true;
	}
	
/*	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
		
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        playerIn.setActiveHand(handIn);
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
    }
*/
	@Override
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand)
    {
	
		if(!player.world.isRemote)
		{
			//Utils.getLogger().info("Torch:onFirstTick");
		}
		
        return EnumActionResult.PASS;
    }
	
	@Override
	public void onUsingTick(ItemStack stack, EntityLivingBase player, int count)
    {
		if(!player.world.isRemote)
		{
			Utils.getLogger().info("Torch:onUsingTick: " + count);
		}
	}
	
	@Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving)
    {
		if(!worldIn.isRemote)
		{
			Utils.getLogger().info("Torch:OnItemFinish");
			RayTraceResult rt = this.rayTrace(worldIn, player, true);
			EntityPlayer player = (EntityPlayer)entityLiving;
			IBlockState blockstate =  worldIn.getBlockState(rt.getBlockPos());
			String blockName = blockstate.getBlock().getRegistryName() + "-" + blockstate.getBlock().getMetaFromState(blockstate);
			String heldItemName = stack.getItem().getRegistryName() + "-" + stack.getMetadata();	
			if(isLit(stack))
			{
				Utils.getLogger().info("Torch:im lit");
				if (ListHandler.ExtinguishList.contains(blockName))
				{
					extinguish(worldIn, player.getPosition(), player);
					//Utils.getLogger().info("Torch:tried to extinguish me: " + blockName);
				}
				else if (blockstate.getBlock() instanceof IIgnitable && !((IIgnitable)blockstate.getBlock()).isLit(worldIn, rt.getBlockPos(), player))
				{
					if(blockstate.getBlock() instanceof BlockHearth && ListHandler.HearthFireStarterList.contains(heldItemName))
					{
						((IIgnitable)blockstate.getBlock()).attemptIgnite(100, worldIn, rt.getBlockPos(), player);
						Utils.getLogger().info("Torch:tried to light it - hearth with "+heldItemName );
					}
					else if(blockstate.getBlock() instanceof BlockATDTorch && ListHandler.TorchFireStarterList.contains(heldItemName))
					{
						((IIgnitable)blockstate.getBlock()).attemptIgnite(100, worldIn, rt.getBlockPos(), player);
						//Utils.getLogger().info("Torch:tried to light it - torch");
					}
					else if(blockstate.getBlock() instanceof BlockHearth && ListHandler.LanternFireStarterList.contains(heldItemName))
					{
						((IIgnitable)blockstate.getBlock()).attemptIgnite(100, worldIn, rt.getBlockPos(), player);
						//Utils.getLogger().info("Torch:tried to light it - lantern");
					}
					else
					{
						Utils.getLogger().info("Torch:didn't light "+blockstate.getBlock()+", i wasnt on a list: " + heldItemName);
					}
				}
			}
			else if (ListHandler.TorchFireStarterList.contains(blockName))
			{
				attemptIgnite(100, worldIn, player.getPosition(), player);
				//Utils.getLogger().info("Torch:tried to light me");
				if(stack.getTagCompound().hasKey("burntime") && stack.getTagCompound().getLong("burntime") > 0)
				{		
					setBurnTime(stack.getTagCompound().getLong("burntime"), stack);
					//Utils.getLogger().info("Torch:set my burntime to:" + stack.getTagCompound().getLong("burntime"));
				}
				else
				{
					setBurnTime((long)0, stack);
					//Utils.getLogger().info("Torch:set my burntime to 0");
				}
				
			}        
		}
		//Utils.getLogger().info("Torch:OnItemFinishUse: returned = "+player.getHeldItemMainhand());
		return player.getHeldItemMainhand();
    }
	
	

	@Override
    public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.BOW;
    }
	
	@Override
    public int getMaxItemUseDuration(ItemStack stack)
    {
		return 60;
    }
		
	@Override
	  public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    	{
		
        	ItemStack itemstack = player.getHeldItem(hand);
        	IBlockState blockstate = worldIn.getBlockState(pos);
        	Block block = blockstate.getBlock();
        	String blockName = block.getRegistryName() + "-" + block.getMetaFromState(blockstate);
        	String heldItemName = itemstack.getItem().getRegistryName() + "-" + itemstack.getMetadata();	
			IBlockState BS_up = ATD_TORCH.getDefaultState().withProperty(BlockTorch.FACING, EnumFacing.UP);
			IBlockState BS_north = ATD_TORCH.getDefaultState().withProperty(BlockTorch.FACING, EnumFacing.NORTH);
			IBlockState BS_south = ATD_TORCH.getDefaultState().withProperty(BlockTorch.FACING, EnumFacing.SOUTH);
			IBlockState BS_east = ATD_TORCH.getDefaultState().withProperty(BlockTorch.FACING, EnumFacing.EAST);
			IBlockState BS_west = ATD_TORCH.getDefaultState().withProperty(BlockTorch.FACING, EnumFacing.WEST);
			int thisX = pos.getX();
			int thisX1 = pos.getX() + 1;
			int thisX2 = pos.getX() - 1;
			int thisY = pos.getY();
			int thisY1 = pos.getY() + 1;
			int thisZ = pos.getZ();
			int thisZ1 = pos.getZ() + 1;
			int thisZ2 = pos.getZ() - 1;
			RayTraceResult RT = this.rayTrace(worldIn, player, true);
			if (RT!=null && !worldIn.isRemote) 
			{	
				if(RT.typeOfHit != RayTraceResult.Type.ENTITY) 
				{
					if (!ListHandler.ExtinguishList.contains(blockName) && !ListHandler.TorchFireStarterList.contains(blockName) && !(block instanceof IIgnitable && !((IIgnitable)block).isLit(worldIn, pos, player)))
					{
						//Utils.getLogger().info("Torch:NOT on ext or FS list " + blockName);
						for (int i = 0;i < PlaceBlocks.length;i++)
						{
							if(PlaceBlocks[i]==worldIn.getBlockState(pos).getMaterial() || worldIn.getBlockState(pos).getBlock() == Blocks.DIRT)
							{
								
								if(RT.sideHit == EnumFacing.UP) 
								{
									BSHold = BS_up;
									BP =  new BlockPos(thisX,thisY1,thisZ);
								}
								else if(RT.sideHit == EnumFacing.NORTH) 
								{
									BSHold = BS_north;		
									BP =  new BlockPos(thisX,thisY,thisZ2);
								}
								else if(RT.sideHit == EnumFacing.SOUTH) 
								{
									BSHold = BS_south;	
									BP =  new BlockPos(thisX,thisY,thisZ1);
								}
								else if(RT.sideHit == EnumFacing.EAST) 
								{
									BSHold = BS_east;	
									BP =  new BlockPos(thisX1,thisY,thisZ);
								}
								else if(RT.sideHit == EnumFacing.WEST) 
								{
									BSHold = BS_west;
									BP =  new BlockPos(thisX2,thisY,thisZ);
								}
								else
								{
									return EnumActionResult.FAIL;
								}
								
								//Utils.getLogger().info("Torch:IS PlaceBlock " + worldIn.getBlockState(pos).getBlock().getUnlocalizedName());
								place(worldIn, itemstack, BSHold, BP);
								//Utils.getLogger().info("Torch:onItemUse: itemstack b4 = " + player.inventory.getCurrentItem());
								//itemstack.shrink(1);
								//Utils.getLogger().info("Torch:onItemUse: itemstack aftr = " + player.inventory.getCurrentItem());
								return  EnumActionResult.FAIL;
							}
						}
						//Utils.getLogger().info("Torch:NOT PlaceBlock " + worldIn.getBlockState(pos).getBlock().getUnlocalizedName());
					}
					else
					{
						//Utils.getLogger().info("Torch:IS on ext or FS list " + blockName); 
					 	player.setActiveHand(hand);
					 	return EnumActionResult.PASS;
					}
				}
			}
				
			return EnumActionResult.FAIL;
    	}

	@Override
	  public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
		{
			entity.setFire(this.EntityFireTime);
			return false;	
		}
    
	
	public int getMetadata(ItemStack stack)
    {
		if(stack.hasTagCompound())
		{
        return stack.getTagCompound().getBoolean("lit") ? 1 : 0;
		}
		return 0;
    }
}
