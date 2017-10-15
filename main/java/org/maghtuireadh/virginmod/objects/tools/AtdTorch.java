package org.maghtuireadh.virginmod.objects.tools;

import javax.annotation.Nullable;

import org.maghtuireadh.virginmod.Main;
import org.maghtuireadh.virginmod.init.BlockInit;
import org.maghtuireadh.virginmod.init.ItemInit;
import org.maghtuireadh.virginmod.tileentity.TEMovingLightSource;
import org.maghtuireadh.virginmod.util.Utils;
import org.maghtuireadh.virginmod.util.interfaces.IHasModel;

import net.minecraft.block.Block;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
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

public class AtdTorch extends ItemSword implements IHasModel, ITileEntityProvider {
	

	BlockPos BP;
	EntityPlayer player;
	private int BurnTime = 0;
	private int EntityFireTime, RainRes;
	private float RainChance;
	private int TimeAway = 30;
	private NBTTagCompound nbt;
	private boolean lit;
	

    /**
     * ATD torch set
     * @param name The name of the torch, used for unlocalized and registry name
     * @param material The item material, affects damage to entity
     * @param burnTime How many ticks it can burn for
     * @param entityFireTime How long it sets entities on fire for
     * @param rainChance The chance that rain will damage res
     * @param rainRes The number of rain res ticks the item has
     */
	
	public AtdTorch(String name, ToolMaterial material, int burnTime, int entityFireTime, float rainChance, int rainRes) {
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(CreativeTabs.MATERIALS);
		this.maxStackSize = 1;
		this.EntityFireTime = entityFireTime;
		BurnTime = burnTime;
		RainRes = rainRes;
		RainChance = rainChance;
		this.addPropertyOverride(new ResourceLocation("lit"), new IItemPropertyGetter() {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
            	if(stack.hasTagCompound()) 
            	{
            		return isLit() ? 1.0F : 0.0F;
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
		//for (int i=0;i<2;i++) {
		//Main.proxy.registerVariantRenderer(this, i, this.getUnlocalizedName(), "lit="+i);
		//}
		Main.proxy.registerItemRenderer(this, 0, "inventory");
		//Main.proxy.registerItemRenderer(this, 1, "lit");
	}
	
	
	@Override

	public TileEntity createNewTileEntity(World worldIn, int meta) {

		return new TEMovingLightSource().setPlayer(player);

	}
	
	
	public boolean isLit() {
		if(nbt.hasKey("lit")) {
		return nbt.getBoolean("lit");
		}
		else
		{
			return false;
		}
		  
	}
	
	public  void setLit(boolean lit, long worldTime) {
		nbt.setBoolean("lit", lit);
		nbt.setLong("worldtime", worldTime);

	}
	
	public void setBurnTime(long burntime) {
		nbt.setLong("burntime", burntime);
		 // Utils.getLogger().info("set burn time " + burntime + ", "  + slot);
	}
	
	public  Long getBurnTime() {
		return nbt.getLong("burntime");
		  
	}
	
	public void setTimeAway(int timeaway) {
		nbt.setInteger("timeaway", timeaway);
		//Utils.getLogger().info("set time away " + timeaway + ", "  + slot);
	}
	
	public  int getTimeAway() {
		return nbt.getInteger("timeaway");
		
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
		
		//if (playerIn.getActiveItemStack().getUnlocalizedName() == Items.FLINT_AND_STEEL.getUnlocalizedName()) {
			//setLit(true);
	//	}
        return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
    }
	
	@Override
	  public void onUpdate(final ItemStack stack, final World world, final Entity entityIn, final int itemSlot, final boolean isSelected)
	    {
		 if (!world.isRemote) {
					nbt = stack.getTagCompound();
					player = (EntityPlayer)entityIn;
					BlockPos pos= new BlockPos(player.posX,player.posY,player.posZ);
					if(nbt == null)	{
						nbt = new NBTTagCompound();
						
						//setTimeAway(TimeAway);
						setLit(false, (long)0);
						setDamage(stack, 0);
						nbt.setFloat("rainchance", RainChance);
						nbt.setInteger("rainres", RainRes);
					}
				
					if (this.lit && isSelected) {
						//entityIn.replaceItemInInventory(itemSlot, new ItemStack(stack.getItem(), 1, itemSlot, nbt));
						setLit(true, world.getTotalWorldTime());
						this.lit=false;
					}
			 
				  if(isLit()) {
					  //setBurnTime(getBurnTime()-1);
					  if(isSelected && getTimeAway() < this.TimeAway) {
						  
						  setTimeAway(this.TimeAway);
						 }
					  else if (!isSelected){
						  setTimeAway(getTimeAway()-1);
					  }
					  if (world.getTotalWorldTime() - (nbt.getLong("worldtime") - getBurnTime()) > this.BurnTime) {
						  stack.shrink(1);
					  }
					  else if(getTimeAway() <= 0) {
						setBurnTime(world.getTotalWorldTime() - (nbt.getLong("worldtime") - getBurnTime()));
						  setLit(false, (long)0);
						  
						  
					  }
					  if (world.isRainingAt(pos.up(2)))
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
									setLit(false, (long)0);
								}
						  }
					  }
				  }
		
		else {
			
		
		}         stack.setTagCompound(nbt);
				  Utils.getLogger().info(stack+","+itemSlot+","+isSelected + "," +  getTimeAway() + "," + isLit() + "," + getBurnTime() + "," + nbt.getLong("worldtime") + "," + world.getTotalWorldTime() );		  
				  
		 }
		 
		}
	
	
	
	
	@Override
	  public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
  {
		entity.setFire(this.EntityFireTime);
	return false;
		
  }
	
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
		RayTraceResult RT = this.rayTrace(worldIn, player, false);
		
		if (RT!=null && !worldIn.isRemote) {	
			if(RT.typeOfHit != RayTraceResult.Type.ENTITY) {
				if(Block.getIdFromBlock(worldIn.getBlockState(pos).getBlock()) == Block.getIdFromBlock(Blocks.GRASS.getDefaultState().getBlock())) {
					//worldIn.getBlockState(pos).getBlock().getUnlocalizedName() == Blocks.GRASS.getDefaultState().getBlock().getUnlocalizedName()) {
					//setLit(true);
					this.lit = true;
				}
				else {
				if(worldIn.getBlockState(pos).getMaterial() != Material.CIRCUITS) {
				if(RT.sideHit == EnumFacing.UP) {
						BP =  new BlockPos(thisX,thisY1,thisZ);
						worldIn.setBlockState(BP, BS_up);
				}
				else if(RT.sideHit == EnumFacing.NORTH) {
					BP =  new BlockPos(thisX,thisY,thisZ2);
					worldIn.setBlockState(BP, BS_north);
				}
				else if(RT.sideHit == EnumFacing.SOUTH) {
					BP =  new BlockPos(thisX,thisY,thisZ1);
					worldIn.setBlockState(BP, BS_south);
				}
				else if(RT.sideHit == EnumFacing.EAST) {
					BP =  new BlockPos(thisX1,thisY,thisZ);
					worldIn.setBlockState(BP, BS_east);
				}
				else if(RT.sideHit == EnumFacing.WEST) {
					BP =  new BlockPos(thisX2,thisY,thisZ);
					worldIn.setBlockState(BP, BS_west);
				}
				player.inventory.deleteStack(player.inventory.getCurrentItem());
				}
				else {
				}
				}
			}
			else {
			}
			
			}
	
		
		else {
		}

        return EnumActionResult.PASS;
  
    }
	
	
}
