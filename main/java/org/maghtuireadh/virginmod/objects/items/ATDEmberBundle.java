package org.maghtuireadh.virginmod.objects.items;

import java.util.Random;

import org.maghtuireadh.virginmod.Main;
import org.maghtuireadh.virginmod.init.ItemInit;
import org.maghtuireadh.virginmod.util.Utils;
import org.maghtuireadh.virginmod.util.interfaces.IHasModel;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class ATDEmberBundle extends Item implements IHasModel{
	private int burnTime=10;
	
	public ATDEmberBundle(String name) {
		setUnlocalizedName(name);
		setRegistryName(name);
		this.setCreativeTab(Main.virginmodtab);
		this.setMaxDamage(64);
		this.setMaxStackSize(1);
		ItemInit.ITEMS.add(this);
		}
	
	public void registerModels() {
		Main.proxy.registerItemRenderer(this, 0, "inventory");
		}
	
	@Override
    public void onUsingTick(ItemStack stack, EntityLivingBase player, int count)
    {
		
    }
	
	@Override
	public void onUpdate(final ItemStack item, final World world, final Entity par3Entity, final int par4, final boolean par5) {
		if(!world.isRemote) {
		NBTTagCompound nbt = item.getTagCompound();
		final EntityPlayer player = (EntityPlayer)par3Entity;
		BlockPos pos= new BlockPos(player.posX,player.posY,player.posZ);
		if(nbt == null)	{
			nbt = new NBTTagCompound();
			nbt.setInteger("burntime", burnTime);
			nbt.setLong("timestamp", world.getWorldTime());
			this.setDamage(item, 0);
		}
		if (item.getItemDamage()<64) {
			if(world.getBlockState(pos.up()).getBlock()==Blocks.WATER) {
				item.shrink(1);
			}
			if((((world.getWorldTime() - nbt.getInteger("timestamp"))/burnTime))<64) {
				if(world.isRainingAt(pos.up(3))) {
				nbt.setInteger("burntime", (nbt.getInteger("burntime")-2));
			} else {
				nbt.setInteger("burntime", (nbt.getInteger("burntime")-1));
				}
			}
		else {
			item.shrink(1);		
			}
		}
		
		if(nbt.getInteger("burntime")<=0 && world.isRainingAt(pos.up(2))) {
			Random rand = new Random();
			if(rand.nextInt(100)<25){
			player.sendMessage(new TextComponentString("Your ember hisses in the rain"));
			item.setItemDamage(item.getItemDamage()+1);
			nbt.setInteger("burntime", burnTime);}
			else {
				item.setItemDamage(item.getItemDamage()+1);
				nbt.setInteger("burntime", burnTime);
				}
		} else if (nbt.getInteger("burntime")<=0) {
			item.setItemDamage(item.getItemDamage()+1);
			nbt.setInteger("burntime", burnTime);
			}
		
		if(item.getItemDamage()==32 && item.getItemDamage()==100) {
			player.sendMessage(new TextComponentString("Your ember bundle glows dimly"));
			}else if(item.getItemDamage()==48) {
				player.sendMessage(new TextComponentString("Your ember bundle is mostly ash"));
			}else if(item.getItemDamage()>=64) {
				player.sendMessage(new TextComponentString("Your ember bundle has gone out!"));
				item.shrink(1);
			}
		item.setTagCompound(nbt);	
		Utils.getLogger().info("Info Output: " + item.getItemDamage() + "  BurningNBT: " + nbt.getInteger("burntime") + " BurningVar: " + burnTime);
		
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

