package org.maghtuireadh.virginmod.objects.items;

import org.maghtuireadh.virginmod.Main;
import org.maghtuireadh.virginmod.init.ItemInit;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class NBTTutorialItem extends Item 
{
	
	public NBTTutorialItem(String name) 
	{
		setUnlocalizedName(name);
		setRegistryName(name);
		this.setCreativeTab(Main.virginmodtab);
		this.setCreativeTab(CreativeTabs.COMBAT);
		this.setCreativeTab(CreativeTabs.TOOLS);
		
		ItemInit.ITEMS.add(this);
	}
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) 
	{
		if(!world.isRemote) 
		{
			ItemStack stack = player.getHeldItem(hand);
			NBTTagCompound nbt = stack.getTagCompound();
			
			if(player.isSneaking()) 
			{
				if(nbt == null || !nbt.hasKey("clicks")) 
				{ 
					player.sendMessage(new TextComponentString("Clicks: 0")); 
				}
				else 
				{ 
					player.sendMessage(new TextComponentString("Clicks: " +  nbt.getInteger("clicks")));
				}
			} 
			else 
			{	
				if(nbt == null) 
				{
					nbt = new NBTTagCompound();
				}
				if(nbt.hasKey("clicks")) 
				{
					nbt.setInteger("clicks",  nbt.getInteger("clicks") +1); 
				}
				else 
				{
					nbt.setInteger("clicks", 1); 
				}
				stack.setTagCompound(nbt);	
			}	
		}
		return new ActionResult(EnumActionResult.SUCCESS, player.getHeldItem(hand));	
	}
}
