package org.maghtuireadh.virginmod.objects.tools;

import java.util.Set;

import javax.annotation.Nullable;

import org.maghtuireadh.virginmod.Main;
import org.maghtuireadh.virginmod.init.BlockInit;
import org.maghtuireadh.virginmod.init.ItemInit;
import org.maghtuireadh.virginmod.objects.blocks.movinglight.BlockMovingLightSource;
import org.maghtuireadh.virginmod.objects.blocks.torches.BlockATDTorch;
import org.maghtuireadh.virginmod.tileentity.TileEntityATDTorch;
import org.maghtuireadh.virginmod.tileentity.TileEntityMovingLightSource;
import org.maghtuireadh.virginmod.util.Utils;
import org.maghtuireadh.virginmod.util.interfaces.IHasModel;

import com.google.common.collect.Sets;

import net.minecraft.block.Block;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumAction;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AtdTorch extends ItemSword	 implements IHasModel, ITileEntityProvider 
{
	

	BlockPos BP;
	IBlockState BSHold;
	EntityPlayer player;
	private int BurnTime = 0;
	private int EntityFireTime, RainRes;
	private float RainChance;
	private int TimeAway = 30;
	private NBTTagCompound nbt;
	private boolean lit, dontkill, extinguish, place;
	public static Block[] FireBlocks = new Block[] {BlockInit.ATD_TORCH,BlockInit.BLOCK_FIREPIT,Blocks.FIRE,Blocks.FLOWING_LAVA,Blocks.LIT_FURNACE,Blocks.MAGMA,Blocks.TORCH};
	public static Material[] PlaceBlocks = new Material[] {Material.CLAY,Material.GRASS,Material.SAND,Material.SNOW,Material.CRAFTED_SNOW,Material.GROUND};
	
    /**
     * ATD torch set
     * @param name The name of the torch, used for unlocalized and registry name
     * @param material The item material, affects damage to entity
     * @param burnTime How many ticks it can burn for
     * @param entityFireTime How long it sets entities on fire for
     * @param rainChance The chance that rain will damage res
     * @param rainRes The number of rain res ticks the item has
     * @param stackSize The size of the stack
     */
	
	public AtdTorch(String name, ToolMaterial material, int burnTime, int entityFireTime, float rainChance, int rainRes, int stackSize) 
	{
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(Main.virginmodtab);
		this.maxStackSize = stackSize;
		this.EntityFireTime = entityFireTime;
		BurnTime = burnTime;
		RainRes = rainRes;
		RainChance = rainChance;
		setDamage(new ItemStack(this), 0);
		this.addPropertyOverride(new ResourceLocation("lit"), new IItemPropertyGetter() 
		{
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
            	if(stack.hasTagCompound()) 
            	{
            		
            		return stack.getTagCompound().getBoolean("lit") ? 1.0F : 0.0F;
            	}
            	else
            	{
            		return 0.0F;
            	}
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
		return new TileEntityMovingLightSource().setPlayer(player);
	}
	
	
	public boolean isLit() 
	{
		if(nbt.hasKey("lit")) 
		{
		return nbt.getBoolean("lit");
		}
		else
		{
			return false;
		}
		  
	}
	
	public  void setLit(boolean lit, long worldTime, ItemStack stack) 
	{
		if(stack.getCount()>1&&lit==true)
		{
			if(player.inventory.getFirstEmptyStack()!=-1)
			{
				ItemStack IS = new ItemStack(stack.getItem());
				IS.setCount(stack.getCount()-1);
				player.inventory.addItemStackToInventory(IS);
				player.getActiveItemStack().setCount(1);
				nbt.setBoolean("lit", lit);
				nbt.setLong("worldtime", worldTime);
				this.dontkill = false;
			}
		}
		else
		{
		nbt.setBoolean("lit", lit);
		nbt.setLong("worldtime", worldTime);
		this.dontkill = false;
		}
	}
	
	public void setBurnTime(long burntime) 
	{
		nbt.setLong("burntime", burntime);
	}
	
	public  Long getBurnTime() 
	{
		if(nbt.hasKey("burntime"))
		{
		return nbt.getLong("burntime");
		}
		else
		{
			return (long)0;
		}
	}
	
	public void setTimeAway(int timeaway) 
	{
		nbt.setInteger("timeaway", timeaway);
	}
	
	public  int getTimeAway() 
	{
		if(nbt.hasKey("timeaway"))
		{
		return nbt.getInteger("timeaway");
		}
		else
		{
		return 0;	
		}
	}
	/*
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
		
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        playerIn.setActiveHand(handIn);
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
    }
	
	@Override
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand)
    {
	
		Utils.getLogger().info("OnItemUseFirst");
		
        return EnumActionResult.PASS;
    }
	
	@Override
	public void onUsingTick(ItemStack stack, EntityLivingBase player, int count)
    {
		if(!player.world.isRemote)
		{
		Utils.getLogger().info("OnItemUsing" + count);
		}
	}
	
	@Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving)
    {
		if(!worldIn.isRemote)
		{
		Utils.getLogger().info("OnItemUseFinish");
        
		}
		return stack;
    }
	
	*/
	
	@Override
	  public void onUpdate(final ItemStack stack, final World world, final Entity entityIn, final int itemSlot, final boolean isSelected)
	    {
		 	if (!world.isRemote) 
		 	{
					nbt = stack.getTagCompound();
					player = (EntityPlayer)entityIn;
					BlockPos pos= new BlockPos(player.posX,player.posY+1,player.posZ);
					if(nbt == null)	
					{
						nbt = new NBTTagCompound();
						setLit(false, (long)0, stack);
						setDamage(stack, 0);
						nbt.setFloat("rainchance", RainChance);
						nbt.setInteger("rainres", RainRes);
					}
					else if (!nbt.hasKey("rainchance"))
					{
						setDamage(stack, 0);
						nbt.setFloat("rainchance", RainChance);
						nbt.setInteger("rainres", RainRes);
					}
				
					if (this.lit && isSelected) 
					{
						setLit(true, world.getTotalWorldTime(), stack);
						this.lit=false;
					}
					
					if (place && isSelected)
					{
						IBlockState BS;
						if(isLit())
						{
							BS = BSHold.withProperty(BlockATDTorch.LIT,true);
						}
						else
						{
							BS = BSHold.withProperty(BlockATDTorch.LIT,false);
						}
						world.setBlockState(BP, BS);
						long time;
						if (nbt.getLong("worldtime") > 0)
						{
							time = BurnTime - (world.getTotalWorldTime() - (nbt.getLong("worldtime") - getBurnTime()));
						}
						else
						{
							time = BurnTime;
						}
						((TileEntityATDTorch) world.getTileEntity(BP)).setTime(time);
						place=false;
						player.inventory.decrStackSize(player.inventory.currentItem, 1);
					}
			 
				  if(isLit()) 
				  {
					  if(isSelected && getTimeAway() < this.TimeAway) 
					  {
						  setTimeAway(this.TimeAway);
					  }
					  else if (!isSelected && player.getHeldItemOffhand().getItem() != ItemInit.ATD_TORCH)
					  {
						  setTimeAway(getTimeAway()-1);
					  }
					  
					  if (world.getTotalWorldTime() - (nbt.getLong("worldtime") - getBurnTime()) > this.BurnTime) 
					  {
						  stack.shrink(1);
					  }
					  else if(getTimeAway() <= 0) 
					  {
						  setBurnTime(world.getTotalWorldTime() - (nbt.getLong("worldtime") - getBurnTime()));
						  setLit(false, (long)0, stack);
					  }
					  
					  if (world.isRainingAt(pos.up(1)))
					  {
						  int res = nbt.getInteger("rainres");
						  float cha = nbt.getFloat("rainchance");
						  float ran = world.rand.nextFloat();
						  if (ran*cha>60) 
						  {
							  Utils.getLogger().info(ran);
								
								nbt.setInteger("rainres", res-1);
								if (res<=0) 
								{
									setBurnTime(world.getTotalWorldTime() - (nbt.getLong("worldtime") - getBurnTime()));
									nbt.setInteger("rainres", RainRes);
									setLit(false, (long)0, stack);
								}
						  }
					  }
					  
					  if(this.extinguish && isSelected)
					  {
						  setBurnTime(world.getTotalWorldTime() - (nbt.getLong("worldtime") - getBurnTime()));
						  setLit(false, (long)0, stack);
						  this.extinguish = false;
					  }
					  
					  if(player.world.isAirBlock(pos)) 
					  {
						  final BlockMovingLightSource lightSource = BlockInit.BLOCK_MLS;
						  player.world.setBlockState(pos, lightSource.setPlayer(player).getDefaultState());
						  this.dontkill = true;
					  }	
				}
		 
				else
				{
					if(player.world.getBlockState(pos) == BlockInit.BLOCK_MLS.getDefaultState() && this.dontkill == false)
					{
						player.world.setBlockToAir(pos);
					}
				}
				stack.setTagCompound(nbt);
				
				//Utils.getLogger().info("OnItemUse" + getMaxItemUseDuration(new ItemStack(this)) + ", " +  player.getItemInUseCount());
				//Utils.getLogger().info(stack+","+itemSlot+","+isSelected + "," +  getTimeAway() + "," + isLit() + "," + getBurnTime() + "," + nbt.getLong("worldtime") + "," + world.getTotalWorldTime() );		  
		 	}
	    }
		 
	
	
	
	
	
	@Override
	  public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
		{
			entity.setFire(this.EntityFireTime);
			return false;	
		}
	
	
	/*
	@Override
    public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.NONE;
    }
	
	@Override
    public int getMaxItemUseDuration(ItemStack stack)
    {
		//Utils.getLogger().info("got max duration");
        return 15;
    }
	*/
	
	@Override
	  public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    	{

			
			IBlockState BS_up = BlockInit.ATD_TORCH.getDefaultState().withProperty(BlockTorch.FACING, EnumFacing.UP);
			IBlockState BS_north = BlockInit.ATD_TORCH.getDefaultState().withProperty(BlockTorch.FACING, EnumFacing.NORTH);
			IBlockState BS_south = BlockInit.ATD_TORCH.getDefaultState().withProperty(BlockTorch.FACING, EnumFacing.SOUTH);
			IBlockState BS_east = BlockInit.ATD_TORCH.getDefaultState().withProperty(BlockTorch.FACING, EnumFacing.EAST);
			IBlockState BS_west = BlockInit.ATD_TORCH.getDefaultState().withProperty(BlockTorch.FACING, EnumFacing.WEST);
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
					for(int i = 0; i < FireBlocks.length;i++)
					{
						if(Block.getIdFromBlock(worldIn.getBlockState(pos).getBlock()) == Block.getIdFromBlock(FireBlocks[i])) 
						{
							this.lit = true;
							Utils.getLogger().info("FireBlocks " + FireBlocks[i].getUnlocalizedName());
							break;
						}
					
					}
					
					if (Block.getIdFromBlock(worldIn.getBlockState(pos).getBlock()) == Block.getIdFromBlock(Blocks.WATER))
					{
						this.extinguish = true; 
					}
				
					if (!this.lit)
					{
						for (int i = 0;i < PlaceBlocks.length-1;i++)
						{
							if(PlaceBlocks[i]==worldIn.getBlockState(pos).getMaterial())
							{
								
								if(RT.sideHit == EnumFacing.UP) 
								{
									BSHold = BS_up;
									BP =  new BlockPos(thisX,thisY1,thisZ);
									place = true;
								}
								else if(RT.sideHit == EnumFacing.NORTH) 
								{
									BSHold = BS_north;		
									BP =  new BlockPos(thisX,thisY,thisZ2);
									place = true;
								}
								else if(RT.sideHit == EnumFacing.SOUTH) 
								{
									BSHold = BS_south;	
									BP =  new BlockPos(thisX,thisY,thisZ1);
									place = true;
								}
								else if(RT.sideHit == EnumFacing.EAST) 
								{
									BSHold = BS_east;	
									BP =  new BlockPos(thisX1,thisY,thisZ);
									place = true;
								}
								else if(RT.sideHit == EnumFacing.WEST) 
								{
									BSHold = BS_west;
									BP =  new BlockPos(thisX2,thisY,thisZ);
									place = true;
									//BP =  new BlockPos(thisX2,thisY,thisZ);
									//worldIn.setBlockState(BP, BS_west);
								}
								Utils.getLogger().info("PlaceBlocks " + PlaceBlocks[i]);
								
								break;
							}
						}
					}
				}
			}
				
			return EnumActionResult.PASS;
    	}
}
