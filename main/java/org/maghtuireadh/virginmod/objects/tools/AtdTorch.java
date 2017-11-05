package org.maghtuireadh.virginmod.objects.tools;

import javax.annotation.Nullable;

import org.maghtuireadh.virginmod.Main;
import org.maghtuireadh.virginmod.init.ItemInit;
import org.maghtuireadh.virginmod.objects.blocks.hearths.BlockHearth;
import org.maghtuireadh.virginmod.objects.blocks.movinglight.BlockMovingLightSource;
import org.maghtuireadh.virginmod.objects.blocks.torches.BlockATDTorch;
import org.maghtuireadh.virginmod.util.Utils;
import org.maghtuireadh.virginmod.util.handlers.ListHandler;
import org.maghtuireadh.virginmod.util.interfaces.IFireStarter;
import org.maghtuireadh.virginmod.util.interfaces.IHasModel;
import org.maghtuireadh.virginmod.util.interfaces.IIgnitable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
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

public class AtdTorch extends ItemSword	 implements IHasModel, IIgnitable, IFireStarter
{
	

	BlockPos BP;
	IBlockState BSHold;
	World wrld;
	EntityPlayer player;
	ItemStack stackSet = new ItemStack(Items.AIR);
	long burntTime = 0;
	private long BurnTime = 0;
	private int EntityFireTime, RainRes;
	private float RainChance, lightlevel, EntityFireChance, SwingExtChance;
	private int TimeAway = 30;
	public Block ATD_TORCH;
	
	
	public static Material[] PlaceBlocks = new Material[] {Material.CLAY,Material.GRASS,Material.SAND,Material.SNOW,Material.CRAFTED_SNOW,Material.GROUND};
    /**
     * ATD torch set
     * @param name The name of the torch, used for unlocalized and registry name
     * @param material The item material, affects damage to entity
     * @param lightleve The brightness
     * @param burnTime How many ticks it can burn for
     * @param entityFireTime How long it sets entities on fire for
     * @param rainChance The chance that rain will damage res
     * @param rainRes The number of rain res ticks the item has
     * @param stackSize The size of the stack
     */
	
	public AtdTorch(String name, ToolMaterial material, float lightleve, int burntime, float entityFireChance, float swingExtChance,  int entityFireTime, float rainChance, int rainRes, int stackSize) 
	{
		super(material);
		setUnlocalizedName("atd_torch_"+name);
		setRegistryName("atd_torch_"+name);
		ATD_TORCH = new BlockATDTorch("block_atd_torch_"+name, lightleve, rainChance, burntime, this);
		setCreativeTab(Main.virginmodtab);
		maxStackSize = stackSize;
		lightlevel = lightleve;
		EntityFireChance = entityFireChance;
		SwingExtChance = swingExtChance;
		EntityFireTime = entityFireTime;
		BurnTime = burntime;
		RainRes = rainRes;
		RainChance = rainChance;
		setDamage(new ItemStack(this), 0);
		ItemInit.ITEMS.add(this);
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

	}
	
	@Override
	public void registerModels()
	{
		Main.proxy.registerItemRenderer(this, 0, "inventory");
	}
	
	
	/*@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) 
	{
		return new TileEntityMovingLightSource().setPlayer(player);
	}*/
	
	public NBTTagCompound newNBT(ItemStack stack)
	{
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setLong("burntime", 0);
		nbt.setFloat("rainchance", RainChance);
		nbt.setInteger("rainres", RainRes);
		nbt.setBoolean("lit", false);
		stack.setTagCompound(nbt);
		//Utils.getLogger().info("Torch:new nbt");
		return nbt;
	}
	
	@Override
	public void onUpdate(final ItemStack stack, final World world, final Entity entityIn, final int itemSlot, final boolean isSelected)
	    {
		wrld = world;
		 	if (!world.isRemote) 
		 	{	
				NBTTagCompound nbt = stack.getTagCompound();
				player = (EntityPlayer)entityIn;
				BlockPos pos= new BlockPos(player.posX,player.posY+1,player.posZ);
				if(nbt == null)	
				{
					nbt = newNBT(stack);
					setDamage(stack, 0);
					//Utils.getLogger().info("new nbt on torch");
				}
				else if (!nbt.hasKey("rainchance"))
				{
					setDamage(stack, 0);
					nbt.setFloat("rainchance", RainChance);
					nbt.setInteger("rainres", RainRes);
				}
				

				if(isLit(stack))// && nbt.hasKey("lit")) 
				{
					 Item item = stack.getItem();
					 String name = item.getRegistryName() + "-" + item.getMetadata(stack);
					 ItemStack stack2 = player.getHeldItemOffhand();
					 Item item2 = stack2.getItem();
					 String name2 = item2.getRegistryName() + "-" + item.getMetadata(stack2);
					 Block block = world.getBlockState(pos).getBlock();
					 Block block2 = world.getBlockState(pos.up()).getBlock();
					 if(isSelected && getTimeAway(stack) < TimeAway) 
					 {
						 setTimeAway(TimeAway, stack);
					
					 }
					 else if (!isSelected && !(stack2 == stack))
					 {
						 setTimeAway(getTimeAway(stack)-1, stack);
						 //Utils.getLogger().info("Torch:deduct timeaway");
					 }
					  
					 if ((world.getTotalWorldTime() - nbt.getLong("worldtime")) + getBurnTime(stack) > BurnTime) 
					 {
						 //Utils.getLogger().info("Torch:update():worldtime - (" + world.getTotalWorldTime() + " - " + nbt.getLong("worldtime") + ") + " + getBurnTime(stack) + " > " + BurnTime);
						 stack.shrink(1);
					 }
					 else if(getTimeAway(stack) <= 0 || (block == Blocks.WATER) || (block == Blocks.FLOWING_WATER)) 
					 {
						 extinguish(world, pos, player, stack);
					 }
					 else
					 {

						 //Utils.getLogger().info("Torch:update():worldtime - (" + world.getTotalWorldTime() + " - " + nbt.getLong("worldtime") + ") + " + getBurnTime(stack) + " < " + BurnTime);
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
						 final BlockMovingLightSource lightSource = new BlockMovingLightSource("block_mls", lightlevel);
						 IBlockState state = lightSource.setPlayer(player).getDefaultState();
						/* 
						 BlockPos pos1 = new BlockPos(pos.getX()+1,pos.getY(),pos.getZ()+1);
						 BlockPos pos2 = new BlockPos(pos.getX()-1,pos.getY(),pos.getZ()-1);
						 BlockPos pos3 = new BlockPos(pos.getX()+1,pos.getY(),pos.getZ()-1);
						 BlockPos pos4 = new BlockPos(pos.getX()-1,pos.getY(),pos.getZ()+1);*/
						 
						 world.setBlockState(pos, state);
						 /*if(world.isAirBlock(pos1)) {world.setBlockState(pos1, state);}
						 if(world.isAirBlock(pos2)) {world.setBlockState(pos2, state);}
						 if(world.isAirBlock(pos3)) {world.setBlockState(pos3, state);}
						 if(world.isAirBlock(pos4)) {world.setBlockState(pos4, state);}
						 */
						 
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
		ItemStack stack = player.getHeldItemMainhand();
		ItemStack stack2 = player.getHeldItemOffhand();
		if(stack.hasTagCompound())
		{
			return stack.getTagCompound().getBoolean("lit");
		}
		else if(stack2.hasTagCompound())
		{
			return stack2.getTagCompound().getBoolean("lit");
		}
		return false;
	}

	@Override
	public boolean extinguish(World world, BlockPos pos, EntityPlayer player) {
		ItemStack stack = player.getHeldItemMainhand();
		return extinguish(world, pos, player, stack);
	}
	
	public boolean extinguish(World world, BlockPos pos, EntityPlayer player, ItemStack stack) 
	{
		NBTTagCompound nbt = stack.getTagCompound();
		setBurnTime((world.getTotalWorldTime() - (nbt.getLong("worldtime")) + getBurnTime(stack)), stack);
		setLit(false, (long)0, stack);
		//Utils.getLogger().info("Torch: ext(4)");
		return true;
	}

	@Override
	public long getFuel(World world, BlockPos pos, EntityPlayer player) {
		NBTTagCompound nbt = player.inventory.getCurrentItem().getTagCompound(); 
		return nbt.getBoolean("lit") ? ((world.getTotalWorldTime() - nbt.getLong("worldtime")) + nbt.getLong("burntime")) : nbt.getLong("burntime");
	}

	@Override
	public void setFuel(ItemStack stack, long fuel, World world, BlockPos pos) 
	{	
		setBurnTime(fuel, stack);	
		//Utils.getLogger().info("Torch: setFuel(4)");
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
	
	public ItemStack setBurnTime(long burntime, ItemStack stack) 
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
		//Utils.getLogger().info("Torch: setBurnTime(): " + burntime);
		return stack;
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
		if(world.isAirBlock(pos) || world.getBlockState(pos).getBlock() instanceof BlockBush)
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
			//Utils.getLogger().info("placed it");
			if (nbt.getLong("worldtime") > 0)
			{
				time = BurnTime - ((world.getTotalWorldTime() - nbt.getLong("worldtime")) + getBurnTime(stack));
			}
			else
			{
				time = BurnTime;
			}
			((IIgnitable) world.getBlockState(BP).getBlock()).setFuel(stack, time, world, BP);
			//Utils.getLogger().info("set fuel to " + time);
			player.inventory.getCurrentItem().shrink(1);//setCount(player.inventory.getCurrentItem().getCount() -1 );
			//Utils.getLogger().info("set count to " + (player.inventory.getCurrentItem().getCount()));
			return true;
		}
		return false;
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
			//Utils.getLogger().info("onFirstTick");
		}
		
        return EnumActionResult.PASS;
    }
	
	@Override
	public void onUsingTick(ItemStack stack, EntityLivingBase player, int count)
    {
		if(!player.world.isRemote)
		{
			//Utils.getLogger().info("onUsingTick: " + count);
		}
	}
	
	@Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving)
    {
		if(!worldIn.isRemote)
		{
			//Utils.getLogger().info("OnItemFinish");
			RayTraceResult rt = this.rayTrace(worldIn, player, true);
			if (rt == null)
			{
				return stack;
			}
			EntityPlayer player = (EntityPlayer)entityLiving;
			BlockPos blockpos = rt.getBlockPos();
			IBlockState blockstate =  worldIn.getBlockState(blockpos);
			IBlockState blockstate2 =  worldIn.getBlockState(blockpos.offset(rt.sideHit));
			Block blk = blockstate2.getBlock();
			if (blk instanceof BlockLiquid)
			{
				blockstate = blockstate2;
			}
			
			Block block = blockstate.getBlock();
			Material mat = blockstate.getMaterial();
			boolean canLight = block.isFlammable(worldIn, blockpos, rt.sideHit);
			String name = block.getRegistryName() + "-" + block.getMetaFromState(blockstate);
			
			if(isLit(stack))
			{
				if (ListHandler.SnufferList.contains(name))
				{
					extinguish(worldIn, blockpos, player);
					//Utils.getLogger().info("tried to extinguish me");
				}
				else if (block instanceof IIgnitable && !((IIgnitable)block).isLit(worldIn, blockpos, player))
				{
					//Utils.getLogger().info("im lit");
					((IIgnitable)block).attemptIgnite(100, worldIn, blockpos, player);
					//Utils.getLogger().info("tried to light it");
				}
				else if (mat == Material.WATER)
				{
					extinguish(worldIn, blockpos, player, stack);
				}
				else if (canLight)
				{
					worldIn.setBlockState(blockpos, Blocks.FIRE.getDefaultState());
				}
			}
			else if (ListHandler.TorchFireStarterList.contains(name) || (block instanceof IIgnitable && ((IIgnitable)block).isLit(worldIn, blockpos, player)) || mat == Material.LAVA)
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
				}
				attemptIgnite(100, worldIn, blockpos, player);

				//Utils.getLogger().info("tried to light me");
			}        
		}
		//Utils.getLogger().info("Torch:OnItemFinishUse: returned = "+player.getHeldItemMainhand());
		return player.getHeldItemMainhand();
    }
	
	@Override
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) 
	{
		if(!entityLiving.world.isRemote)
		{
			EntityPlayer player = (EntityPlayer)entityLiving;
			World world = player.world;
			float flt = world.rand.nextFloat();
			IIgnitable ig = (IIgnitable)stack.getItem();
			
			if(ig.isLit(world, player.getPosition(), player) && flt < SwingExtChance)
			{
				//Utils.getLogger().info("Torch: swing():" + flt + " < " + SwingExtChance);
				extinguish(world, player.getPosition(), player, stack);
			}
			//Utils.getLogger().info(flt + " < " + SwingExtChance);
		}
		return super.onEntitySwing(entityLiving, stack);
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
		
        	ItemStack stack = player.getHeldItem(hand);
        	//IBlockState state = worldIn.getBlockState(pos);
        	//Block block = state.getBlock();
        	//String name = block.getRegistryName() + "-" + block.getMetaFromState(state);
			RayTraceResult RT = rayTrace(worldIn, player, true);
       
			if (RT!=null && !worldIn.isRemote) 
			{	
				if(RT.typeOfHit != RayTraceResult.Type.ENTITY) 
				{	
					BlockPos blockpos = RT.getBlockPos();
					IBlockState state2 = worldIn.getBlockState(blockpos);
					Block block = state2.getBlock();
					IBlockState state3 = worldIn.getBlockState(blockpos.offset(facing));
					Block block2 = state3.getBlock();
					IBlockState stateUsed;
					Block blockUsed;
					String name;
					if (block2 instanceof BlockLiquid)
					{
						stateUsed = state3;
						blockUsed = block2;
						name = block2.getRegistryName() + "-" + block2.getMetaFromState(state3);
						//Utils.getLogger().info("instance of block liquid");
						//Utils.getLogger().info(state3 + " - " + state2 + " - " + state3.getMaterial());
					}
					else
					{
						stateUsed = state2;
						blockUsed = block;
						name = block.getRegistryName() + "-" + block.getMetaFromState(state2);
						//Utils.getLogger().info("instance of block liquid");
						//Utils.getLogger().info(state3 + " - " + state2 + " - " + state3.getMaterial());
					}
					Material mat = stateUsed.getMaterial();
					boolean canLight = stateUsed.getBlock().isFlammable(worldIn, blockpos, facing);
					if (!ListHandler.SnufferList.contains(name) && !ListHandler.TorchFireStarterList.contains(name) && !(blockUsed instanceof IIgnitable) && !(mat == Material.WATER || mat == Material.LAVA) && !(canLight))
					{
						//Utils.getLogger().info("NOT on ext or FS list " + name);
						for (int i = 0;i < PlaceBlocks.length;i++)
						{
							if(PlaceBlocks[i]== mat || blockUsed == Blocks.DIRT || blockUsed instanceof BlockBush)
							{
								if(blockUsed instanceof BlockBush)
								{
									BSHold = ATD_TORCH.getDefaultState().withProperty(BlockTorch.FACING, EnumFacing.UP);
									BP =  pos;
								}
								else
								{
									BSHold = ATD_TORCH.getDefaultState().withProperty(BlockTorch.FACING, facing);
									BP = pos.offset(facing);
								}
								
								//Utils.getLogger().info("IS PlaceBlock " + name);
								return place(worldIn, stack, BSHold, BP) ? EnumActionResult.SUCCESS : EnumActionResult.FAIL;
							}
						}
						//Utils.getLogger().info("NOT PlaceBlock " + name);
					}
					else
					{
						//Utils.getLogger().info("IS on ext or FS list " + name); 
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
		float flt = player.world.rand.nextFloat();
			if(((IIgnitable)stack.getItem()).isLit(wrld, player.getPosition(), player) && flt < EntityFireChance)
			{
			entity.setFire(this.EntityFireTime); 
			}
			//Utils.getLogger().info("BURN! " + flt);
			return false;
		}
	
    @Override
    public boolean showDurabilityBar(ItemStack stack)
    {
        return true;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack)
    {
    	if(stack.hasTagCompound())
    	{
    		NBTTagCompound nbt = stack.getTagCompound();  
    		if(wrld != null && nbt.hasKey("worldtime") && nbt.getLong("worldtime") > 0) 
    		{
    			return (double)((wrld.getTotalWorldTime() - nbt.getLong("worldtime")) + getBurnTime(stack)) / (double)BurnTime;
    		}
    		else if(wrld != null && getBurnTime(stack) > 0) 
    		{
    			return ((double)getBurnTime(stack)) / (double)BurnTime;
    		}
    	}
    	return 0;
    }
    /*
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
    	if(!worldIn.isRemote)
    	{
    		RayTraceResult RT = rayTrace(worldIn, playerIn, true);
    		BlockPos blockpos = RT.getBlockPos();
    		IBlockState iblockstate = worldIn.getBlockState(blockpos);
            Material material = iblockstate.getMaterial();

            if (material == Material.WATER || material == Material.LAVA)
            {
            	player.setActiveHand(handIn);
            }
    	}
        return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
    }*/
    
	
	public int getMetadata(ItemStack stack)
    {
		if(stack.hasTagCompound())
		{
        return stack.getTagCompound().getBoolean("lit") ? 1 : 0;
		}
		return 0;
    }
}
