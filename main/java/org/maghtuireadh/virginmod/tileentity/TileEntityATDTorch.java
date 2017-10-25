package org.maghtuireadh.virginmod.tileentity;

import org.maghtuireadh.virginmod.objects.blocks.torches.BlockATDTorch;
import org.maghtuireadh.virginmod.util.Utils;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

public class TileEntityATDTorch extends TileEntity implements ITickable
{
	long sentTimeset,sentStart,timeset,start = (long)0;
	BlockPos sentPos = null;
	Byte id = 42;

	public TileEntityATDTorch()
	{
		Utils.getLogger().info("constructed TE");
	}
	

	
	public void readFromNBT(NBTTagCompound nbt) 
	{
		super.readFromNBT(nbt);
		if(nbt.hasKey("timeset") && nbt.hasKey("start"))
		{
			this.timeset = nbt.getLong("timeset");
			this.start = nbt.getLong("start");
			this.id = nbt.getByte("id");
			Utils.getLogger().info("read from nbt id: "+nbt.getId());
		}
		else
		{
			nbt.setLong("timeset", (long)0);
			nbt.setLong("start", (long)0);
			nbt.setByte("id", (byte)17);
			Utils.getLogger().info("in READ i wrote");
		}
	}
	
	
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) 
	{
		nbt.setLong("timeset", this.timeset);
		nbt.setLong("start", this.start);
		nbt.setByte("id", this.id);
		Utils.getLogger().info("write to nbt TE id: "+nbt.getId());
		super.writeToNBT(nbt);
		return nbt;
	}

	@Override
	public void update() {
		if(!world.isRemote)
		{
			if (sentPos != null && sentPos == pos)
			{
				Utils.getLogger().info("sent pos-" + sentPos + ", " + sentTimeset + ", " + sentStart);
				timeset = sentTimeset;
				start = sentStart;
				sentTimeset = 0; 
				sentStart = 0;
				sentPos = null;
				Utils.getLogger().info("set settings id: " + id);
			}
			if(timeset > 0 && world.getBlockState(pos).getValue(BlockATDTorch.LIT) == true)
			{
				Utils.getLogger().info(world.getTotalWorldTime()-start + " < " + timeset);
				if (world.getTotalWorldTime()-start > timeset)
				{
					//((IIgnitable)world.getBlockState(pos).getBlock()).extinguish(world, pos, null);
					world.setBlockToAir(pos);
					world.removeTileEntity(pos);
					Utils.getLogger().info("killed");
				}
				
			}
			markDirty();
		}
	}
	
	public void setTime(long time, BlockPos pos)
	{
		if(!world.isRemote)
		{
			sentStart = world.getTotalWorldTime();
			sentTimeset = time;
			sentPos = pos;
			Utils.getLogger().info("set time " + time);
			markDirty();
		}
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) 
	{
		this.readFromNBT(pkt.getNbtCompound());
	}
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() 
	{
		NBTTagCompound nbt = new NBTTagCompound();
		this.writeToNBT(nbt);
		int metadata = getBlockMetadata();
		return new SPacketUpdateTileEntity(this.pos, metadata, nbt);
	}



	@Override
	public NBTTagCompound getUpdateTag() 
	{
		NBTTagCompound nbt = new NBTTagCompound();
		this.writeToNBT(nbt);
		return nbt;
	}

	@Override
	public void handleUpdateTag(NBTTagCompound tag) 
	{
		this.readFromNBT(tag);
	}

	@Override
	public NBTTagCompound getTileData() 
	{
		NBTTagCompound nbt = new NBTTagCompound();
		this.writeToNBT(nbt);
		return nbt;
	}
}
