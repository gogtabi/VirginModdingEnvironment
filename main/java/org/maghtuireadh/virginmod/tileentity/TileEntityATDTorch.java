package org.maghtuireadh.virginmod.tileentity;

import org.maghtuireadh.virginmod.objects.blocks.torches.BlockATDTorch;
import org.maghtuireadh.virginmod.util.Utils;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeCache;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;

public class TileEntityATDTorch extends TileEntity implements ITickable
{
	long timeleft,start,burntime = (long)0;
	public NBTTagCompound nbt;
	int setAblaze;

	public TileEntityATDTorch()
	{
		
		//Utils.getLogger().info("constructed TE");
	}
	

	
	public void readFromNBT(NBTTagCompound nbt) 
	{
		super.readFromNBT(nbt);
		if(burntime==0)
		{
		burntime = nbt.getLong("burntime");
		timeleft = nbt.getLong("timeleft");
		start = nbt.getLong("start");
		//Utils.getLogger().info("read from nbt:" + burntime + ", " + timeleft + ", " + start);
		}

	}
	
	
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) 
	{
		super.writeToNBT(nbt);
		nbt.setLong("burntime", burntime);
		nbt.setLong("timeleft", timeleft);
		nbt.setLong("start", start);

		//Utils.getLogger().info("write to nbt TE:" + burntime + ", " + timeleft + ", " + start);
		markDirty();
		return nbt;
	}

	@Override
	public void update() {
		if(!world.isRemote)
		{
			if(timeleft > 0 && world.getBlockState(pos).getValue(BlockATDTorch.LIT) == true)
			{
				if(world.getBlockState(pos.up()).getBlock().isFlammable(world, pos, EnumFacing.UP))
				{
					setAblaze++;
				}
				else
				{
					setAblaze = 0;
				}
				if(setAblaze == 60)
				{
					world.setBlockState(pos.up(), Blocks.FIRE.getDefaultState(), 11);
				}
				
				//Utils.getLogger().info(world.getTotalWorldTime()-start + " < " + timeleft);
				if (world.getTotalWorldTime()-start > timeleft)
				{
					//((IIgnitable)world.getBlockState(pos).getBlock()).extinguish(world, pos, null);
					IBlockState state = world.getBlockState(pos);
					world.setBlockToAir(pos);
					world.removeTileEntity(pos);
					//Utils.getLogger().info("killed");
				}
				
			}
			markDirty();
		}
	}
	
	public void setTime(long time, long burntim)
	{
		start = world.getTotalWorldTime();
		timeleft = time;
		burntime = burntim;
		//Utils.getLogger().info("set time " + time);
		markDirty();
	}
	public long getTime()
	{
		if(timeleft != 0 && world.getBlockState(pos).getValue(BlockATDTorch.LIT) == true)
		{
			return timeleft - (world.getTotalWorldTime() - start);
		}
		return burntime;
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() 
	{
		NBTTagCompound nbt = new NBTTagCompound();
		writeToNBT(nbt);
		int metadata = getBlockMetadata();
		return new SPacketUpdateTileEntity(pos, metadata, nbt);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) 
	{
		readFromNBT(pkt.getNbtCompound());
	}

	@Override
	public NBTTagCompound getUpdateTag() 
	{
		NBTTagCompound nbt = new NBTTagCompound();
		writeToNBT(nbt);
		return nbt;
	}

	@Override
	public void handleUpdateTag(NBTTagCompound tag) 
	{
		readFromNBT(tag);
	}

	@Override
	public NBTTagCompound getTileData() 
	{
		NBTTagCompound nbt = new NBTTagCompound();
		writeToNBT(nbt);
		return nbt;
	}
}
