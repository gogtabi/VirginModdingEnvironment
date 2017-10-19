package org.maghtuireadh.virginmod.objects.items;

import java.util.Random;

import org.maghtuireadh.virginmod.Main;
import org.maghtuireadh.virginmod.init.ItemInit;
import org.maghtuireadh.virginmod.objects.blocks.hearths.BlockHearth;
import org.maghtuireadh.virginmod.util.Utils;
import org.maghtuireadh.virginmod.util.interfaces.IFireStarter;
import org.maghtuireadh.virginmod.util.interfaces.IHasModel;
import org.maghtuireadh.virginmod.util.interfaces.IIgnitable;


import net.minecraft.block.Block;
import net.minecraft.block.BlockFire;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import net.minecraftforge.event.entity.item.ItemEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.fml.common.eventhandler.Event;
import scala.languageFeature.postfixOps;

public class ATDEmberBundle extends Item implements IHasModel, IFireStarter
{
	private int burnTime=24000;
	private int count=100;
	private int itemUseDuration=0;
	private int igniteChance=100;
	private BlockPos targetPosition;

	public ATDEmberBundle(String name) 
	{
		setUnlocalizedName(name);
		setRegistryName(name);
		this.setCreativeTab(Main.virginmodtab);
		this.setMaxDamage(64);
		this.setMaxStackSize(1);
		ItemInit.ITEMS.add(this);
	}
	

	/**
     * This is called when the item is used, before the block is activated.
     * @param stack The Item Stack
     * @param player The Player that used the item
     * @param world The Current World
     * @param pos Target position
     * @param side The side of the target hit
     * @param hand Which hand the item is being held in.
     * @return Return PASS to allow vanilla handling, any other to skip normal code.
     */
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand)
    {    	Utils.getLogger().info("Ember Bundle getItemuseFirst");
    		targetPosition=pos;
     	if(world.getBlockState(pos).getBlock() instanceof IIgnitable) {
     		Utils.getLogger().info("Ember Bundle getItemuseFirst: PASS");
     		return EnumActionResult.PASS;
     		
    	}
    	else
    	{

     		Utils.getLogger().info("Ember Bundle getItemuseFirst: FAIL");
    		return EnumActionResult.FAIL;

    	}
    }
	
    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
    	Utils.getLogger().info("Ember Bundle getItemuseAction");
    	return EnumAction.EAT;
    }
    
    
  /* @Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		
		return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}*/
    
   /* @Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entityLiving) {
    	((IIgnitable) world.getBlockState(targetPosition).getBlock()).attemptIgnite(igniteChance, world, targetPosition, (EntityPlayer) entityLiving);
    	Utils.getLogger().info("Ember Bundle onItemUseFinish");
    	stack.shrink(1);
		return stack;
    }*/

	public void registerModels() 
	{
		Main.proxy.registerItemRenderer(this, 0, "inventory");
	}
	
	@Override
	public void onUpdate(final ItemStack item, final World world, final Entity par3Entity, final int par4, final boolean par5) 
	{
		if(!world.isRemote) 
		{
			NBTTagCompound nbt = item.getTagCompound();
			final EntityPlayer player = (EntityPlayer)par3Entity;
			BlockPos pos= new BlockPos(player.posX,player.posY,player.posZ);
			if(nbt == null)	
				{
					nbt = new NBTTagCompound();
					nbt.setInteger("burntime", burnTime);
					nbt.setLong("timestamp", world.getWorldTime());
					this.setDamage(item, 0);
				}
		
			if (item.getItemDamage()<64) 
			{
				if(world.getBlockState(pos.up()).getBlock()==Blocks.WATER) 
				{
					item.shrink(1);
				}
				if((((world.getWorldTime() - nbt.getInteger("timestamp"))/burnTime))<64) 
				{
					if(world.isRainingAt(pos.up(3))) 
					{
						nbt.setInteger("burntime", (nbt.getInteger("burntime")-2));
					} 
					else 
					{
						nbt.setInteger("burntime", (nbt.getInteger("burntime")-1));
					}
				}
				else 
				{
					item.shrink(1);		
				}

			if(item.getItemDamage()==32 && item.getItemDamage()==100) 
			{
				player.sendMessage(new TextComponentString("Your ember bundle glows dimly"));
			}
			else if(item.getItemDamage()==48) 
			{
				player.sendMessage(new TextComponentString("Your ember bundle is mostly ash"));
			}
			else if(item.getItemDamage()>=64) 
			{
				player.sendMessage(new TextComponentString("Your ember bundle has gone out!"));
				item.shrink(1);
			}
			item.setTagCompound(nbt);	
			Utils.getLogger().info("Info Output: " + item.getItemDamage() + "  BurningNBT: " + nbt.getInteger("burntime") + " BurningVar: " + burnTime);
			}
		}
	}
}






/*	
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft)
		 {
		 }
	 
	public int getMaxItemUseDuration(ItemStack stack)
	    {
	        return 72000;
	    }
	public EnumAction getItemUseAction(ItemStack stack)
	    {
	        return EnumAction.NONE;
	    }*/

