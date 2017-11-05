package org.maghtuireadh.virginmod.tileentity;

import java.util.UUID;

import javax.annotation.Nullable;

import org.maghtuireadh.virginmod.objects.tools.AtdTorch;
import org.maghtuireadh.virginmod.util.Utils;
import org.maghtuireadh.virginmod.util.interfaces.IIgnitable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.MathHelper;

public class TileEntityMovingLightSource extends TileEntity implements ITickable 
{

	public static final String NAME = "te_moving_light_source";

	private UUID playerUUID;
	float lightlevel;

	public TileEntityMovingLightSource()
	{

	}



	
	@Override
	public void update() 
	{
		if (shouldKill())// && world.getBlockState(pos).getBlock() instanceof BlockMovingLightSource) 
		{
			//Utils.getLogger().info("log 6 "+ world.getBlockState(pos).getBlock().getUnlocalizedName().substring(5));
			world.setBlockToAir(pos);
			world.removeTileEntity(pos);
		}
		//Utils.getLogger().info("log 5 "+ world.getBlockState(pos).getBlock().getUnlocalizedName().substring(5));
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag) 
	{
		super.writeToNBT(tag);
		if (playerUUID != null) 
		{
			tag.setString("PlayerUUID", playerUUID.toString());
		}
		return tag;
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) 
	{
		super.readFromNBT(tag);
		if (tag.hasKey("PlayerUUID")) 
		{
			playerUUID = UUID.fromString(tag.getString("PlayerUUID"));
		}
	}

	public boolean shouldKill() 
	{
		final EntityPlayer player = findLightSourceCreator();
		ItemStack offStack = player.getHeldItemOffhand();
		if (player == null  || player.getDistance(pos.getX(), pos.getY(), pos.getZ()) > 3.0D) 
		{
			//Utils.getLogger().info("log 1 " + player);
			return true;
		}
		else if (offStack.getItem() instanceof AtdTorch && ((AtdTorch)offStack.getItem()).isLit(offStack))
		{
			//Utils.getLogger().info("log 2");
			return false;
		}
		else for (ItemStack stack : player.inventory.mainInventory) 
		{
			if (stack.getItem() instanceof IIgnitable && ((IIgnitable)stack.getItem()).isLit(world, pos, player))
			{
				//Utils.getLogger().info("log 3");
			return false;
			}
		}
		return true;
	}

	public TileEntityMovingLightSource setPlayer(EntityPlayer player) 
	{
		if (player != null) 
		{
			playerUUID = player.getGameProfile() != null ? player.getGameProfile().getId() : null;
		}
		return this;
	}

	@Nullable
	public EntityPlayer findLightSourceCreator() 
	{
		if (playerUUID != null) 
		{
			return world.getPlayerEntityByUUID(playerUUID);
		}
		return world.getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), 3.0D, false);
	}
}
