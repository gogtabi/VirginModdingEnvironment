package org.maghtuireadh.virginmod.objects.tools;

import org.maghtuireadh.virginmod.Main;
import org.maghtuireadh.virginmod.init.BlockInit;
import org.maghtuireadh.virginmod.init.ItemInit;
import org.maghtuireadh.virginmod.tileentity.TEMovingLightSource;
import org.maghtuireadh.virginmod.util.Utils;
import org.maghtuireadh.virginmod.util.interfaces.IHasModel;

import net.minecraft.block.BlockTorch;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class AtdTorch extends ItemSword implements IHasModel, ITileEntityProvider {
	

	BlockPos BP;
	EntityPlayer player;
	boolean lit = false;
	int BurnTime = 0;
	int EntityFireTime, TimeAway;
	
	public AtdTorch(String name, ToolMaterial material, int BurnTime, int EntityFireTime) {
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(CreativeTabs.MATERIALS);
		this.maxStackSize = 1;
		this.EntityFireTime = EntityFireTime;
		this.BurnTime = BurnTime;
		ItemInit.ITEMS.add(this);
	}
	
	@Override
	public void registerModels()
	{
		Main.proxy.registerItemRenderer(this, 0, "inventory");
	}
	
	
	@Override

	public TileEntity createNewTileEntity(World worldIn, int meta) {

		return new TEMovingLightSource().setPlayer(player);

	}
	
	
	public boolean isLit() {
		return this.lit;
	}
	
	public void setLit(boolean lit) {
		this.lit = lit;
		
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
		
		//if (playerIn.getActiveItemStack().getUnlocalizedName() == Items.FLINT_AND_STEEL.getUnlocalizedName()) {
		//	this.setLit(true);
	//	}
        return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
    }
	
	@Override
	  public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
	    {
		 if (!worldIn.isRemote) {
			 
				  if(this.isLit()) {
					  this.BurnTime--;
					  if(isSelected) {
						  this.TimeAway = 30;
						  Utils.getLogger().info("BurnTime remaining :"+this.BurnTime);
						 }
					  else {
						  this.TimeAway--;
						  Utils.getLogger().info("TimeAway remaining :"+this.TimeAway);
					  }
					  if (this.BurnTime <= 0 || this.TimeAway <= 0) {
						  this.setLit(false);
			 }}
		
		else {
			
		
		}
				  Utils.getLogger().info(stack+","+worldIn+","+entityIn+","+itemSlot+","+isSelected);		  
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
				if(1==1) {
					//worldIn.getBlockState(pos).getBlock().getUnlocalizedName() == Blocks.GRASS.getDefaultState().getBlock().getUnlocalizedName()) {
					
					Utils.getLogger().info("b4 this is "+this.isLit());
					this.setLit(true);
					this.BurnTime = 3000;
					Utils.getLogger().info("aftr this is "+this.isLit());
				}
				else {
					Utils.getLogger().info(worldIn.getBlockState(pos).getBlock().getUnlocalizedName() +" == "+ Blocks.GRASS.getDefaultState().getBlock().getUnlocalizedName());
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
				}
				else {
					Utils.getLogger().info("dunno");
				}
				}
			}
			else {
				Utils.getLogger().info("not block");
			}
			
			}
	
		
		else {
		Utils.getLogger().info("Thats null bruh");
		}
	      Utils.getLogger().info("Used");
        return EnumActionResult.PASS;
  
    }
	
	
}
