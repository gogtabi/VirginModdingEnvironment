package org.maghtuireadh.virginmod.tileentity;

import java.util.UUID;

import javax.annotation.Nullable;

import org.maghtuireadh.virginmod.init.ItemInit;
import org.maghtuireadh.virginmod.objects.blocks.movinglight.BlockMovingLightSource;
import org.maghtuireadh.virginmod.util.Utils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TEMovingLightSource extends TileEntity implements ITickable {

	public static final String NAME = "te_moving_light_source";

	private UUID playerUUID;

	public TEMovingLightSource() {

	}

	@Override
	public void update() {
		if (shouldKill() && world.getBlockState(pos).getBlock() instanceof BlockMovingLightSource) {
			world.setBlockToAir(pos);
			world.removeTileEntity(pos);
		}

	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);

		if (playerUUID != null) {
			tag.setString("PlayerUUID", playerUUID.toString());
		}

		return tag;
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);

		if (tag.hasKey("PlayerUUID")) {
			playerUUID = UUID.fromString(tag.getString("PlayerUUID"));
		}
	}

	public boolean shouldKill() {
		final EntityPlayer player = findLightSourceCreator();
		if (player == null  || player.getDistance(pos.getX(), pos.getY(), pos.getZ()) > 2.0D) {
	
			return true;
			
		}
		else if (player.getHeldItemMainhand().getItem() != ItemInit.ATD_TORCH && player.getHeldItemOffhand().getItem() != ItemInit.ATD_TORCH) {
	
			return true;
		}
		else {
		
			return false;
		}
		

		
	}

	public TEMovingLightSource setPlayer(EntityPlayer player) {
		if (player != null) {
			playerUUID = player.getGameProfile() != null ? player.getGameProfile().getId() : null;
		}

		return this;
	}

	@Nullable
	public EntityPlayer findLightSourceCreator() {
		if (playerUUID != null) {
			return world.getPlayerEntityByUUID(playerUUID);
		}

		return world.getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), 2.0D, false);
	}

}
