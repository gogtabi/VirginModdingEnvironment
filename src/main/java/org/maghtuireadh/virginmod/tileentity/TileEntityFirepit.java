package org.maghtuireadh.virginmod.tileentity;


import org.maghtuireadh.virginmod.objects.blocks.furnaces.BlockFirepit;
import org.maghtuireadh.virginmod.util.Utils;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;


public class TileEntityFirepit extends TileEntity implements ITickable, ICapabilityProvider{
	private boolean isBurning, isStoked;
	private int firepitBurnTime = 0;
	private int fuelLvl = 0;
	private int burnRate = 0;
	private int coalBurn = 0;
	private int coalCount = 0;
	private int coalGrowth = 0;
	private int coalRate = 0;
	private int ashBurn = 0;
	private int ashCount = 0;
	private int ashGrowth = 0;
	private int ashRate = 0;
	float lightLvl = 0.0F;
	private int cooldown;

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.cooldown = nbt.getInteger("Cooldown");
		this.firepitBurnTime = nbt.getInteger("FPBT");
		this.fuelLvl = nbt.getInteger("FuelLvl");
		this.burnRate = nbt.getInteger("BurnRate");
		this.coalBurn = nbt.getInteger("CoalBurn");
		this.coalCount = nbt.getInteger("CoalCount");
		this.coalGrowth = nbt.getInteger("CoalGrowth");
		this.coalRate = nbt.getInteger("CoalRate");
		this.ashBurn = nbt.getInteger("AshBurn");
		this.ashCount = nbt.getInteger("AshCount");
		this.ashGrowth = nbt.getInteger("AshGrowth");
		this.ashRate = nbt.getInteger("AshRate");
		this.isBurning = nbt.getBoolean("IsBurning");
		this.isStoked = nbt.getBoolean("IsStoked");
		this.lightLvl = nbt.getFloat("Light Level");
		
	}
	
	@Override 
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		nbt.setInteger("Cooldown", cooldown);
		nbt.setInteger("FPBT", firepitBurnTime);
		nbt.setInteger("FuelLvl", fuelLvl);
		nbt.setInteger("BurnRate", burnRate);
		nbt.setInteger("CoalBurn",coalBurn);
		nbt.setInteger("CoalCount",coalCount);
		nbt.setInteger("CoalGrowth",coalGrowth);
		nbt.setInteger("CoalRate",coalRate);
		nbt.setInteger("AshBurn",ashBurn);
		nbt.setInteger("AshCount",ashCount);
		nbt.setInteger("AshGrowth",ashGrowth);
		nbt.setInteger("AshRate",ashRate);
		nbt.setBoolean("IsBurning",isBurning);
		nbt.setBoolean("IsStoked",isStoked);
		nbt.setFloat("Light Level",lightLvl);
		return super.writeToNBT(nbt);
	}
	
	public float getLight (){
		return lightLvl;
	}
	
	public boolean getBurning() {
		return isBurning;
	}
	
	public void update(){
		if (firepitBurnTime>0)
		{
			isBurning=true;
			markDirty();
			Utils.getLogger().info(firepitBurnTime + " update1");
			Utils.getLogger().info("It's Burning");
		}
		
		if (isBurning)
		{
				--firepitBurnTime;
				lightLvl=1.0F;
				markDirty();
		}	
		if (isBurning && coalBurn > 0){
			--coalBurn;
			++coalGrowth;
			if (coalGrowth == coalRate){
				++coalCount;
				coalGrowth = 0;
				markDirty();
			}
		}
			
		if (isBurning && ashBurn > 0){
			--ashBurn;
			++ashGrowth;
			markDirty();
					
			if (ashGrowth == ashRate){
					++ashCount;
					this.ashGrowth = 0;
					markDirty();
			}
		}
				
			if (isBurning && firepitBurnTime == 0){
					isBurning = false;
					coalBurn = 0;
					ashBurn = 0;
					coalRate = 0;
					ashRate = 0;
					markDirty();
			}
			markDirty();
			Utils.getLogger().info(firepitBurnTime + "update");
	}

	
	public void setFuelValues(ItemStack heldItem) {
		if (heldItem.isEmpty()){}
		else
		{ 
			Item item = heldItem.getItem();
			if(Block.getBlockFromItem(item).getDefaultState().getMaterial() == Material.WOOD)
		        {
		                firepitBurnTime += 300;
		                coalBurn += 100;
		                coalRate = 25;
		                ashBurn += 100;
		                heldItem.shrink(1);
		                markDirty();
		                Utils.getLogger().info(firepitBurnTime + "setFuelValues");
		        }
		}
	}
	
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound nbt = new NBTTagCompound();
		this.writeToNBT(nbt);
		int metadata = getBlockMetadata();
		return new SPacketUpdateTileEntity(this.pos, metadata, nbt);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		this.readFromNBT(pkt.getNbtCompound());
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		NBTTagCompound nbt = new NBTTagCompound();
		this.writeToNBT(nbt);
		return nbt;
	}

	@Override
	public void handleUpdateTag(NBTTagCompound tag) {
		this.readFromNBT(tag);
	}

	@Override
	public NBTTagCompound getTileData() {
		NBTTagCompound nbt = new NBTTagCompound();
		this.writeToNBT(nbt);
		return nbt;
	}

}