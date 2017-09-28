package org.maghtuireadh.virginmod.tileentity;


import org.maghtuireadh.virginmod.objects.blocks.furnaces.BlockFirepit;

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


public class TileEntityFirePit extends TileEntity implements ITickable{
	private boolean isBurning, isStoked;
	int firepitBurnTime, fuelLvl, burnRate, coalBurn, coalCount, coalGrowth, coalRate, ashBurn, ashCount, ashGrowth, ashRate;
	float lightLvl;
	
	private int cooldown;
	
	public TileEntityFirePit() {
		
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
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
		
		super.readFromNBT(nbt);
	}
	
	@Override 
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		nbt.setInteger("Cooldown", this.cooldown);
		nbt.setInteger("FPBT", this.firepitBurnTime);
		nbt.setInteger("FuelLvl", this.fuelLvl);
		nbt.setInteger("BurnRate",this.burnRate);
		nbt.setInteger("CoalBurn", this.coalBurn);
		nbt.setInteger("CoalCount", this.coalCount);
		nbt.setInteger("CoalGrowth", this.coalGrowth);
		nbt.setInteger("CoalRate", this.coalRate);
		nbt.setInteger("AshBurn", this.ashBurn);
		nbt.setInteger("AshCount", this.ashCount);
		nbt.setInteger("AshGrowth", this.ashGrowth);
		nbt.setInteger("AshRate", this.ashRate);
		nbt.setBoolean("IsBurning", this.isBurning);
		nbt.setBoolean("IsStoked", this.isStoked);
		nbt.setFloat("Light Level", lightLvl);
		
		return super.writeToNBT(nbt);
	}
	
	public float getLight (){
		return lightLvl;
	}
	
	public boolean getBurning() {
		return isBurning;
	}
	
	public void update(){
		if (this.firepitBurnTime>0)
		{
			this.isBurning=true;
		}
		
		if (this.isBurning){
				
				--this.firepitBurnTime;
				this.lightLvl=1.0f;
		}	
		
		if (!this.world.isRemote){
			if (this.isBurning && coalBurn > 0){
				--coalBurn;
				++coalGrowth;
				if (coalGrowth == coalRate){
					++coalCount;
					this.coalGrowth = 0;
				}
			}
			
						if (this.isBurning && ashBurn > 0){
					--ashBurn;
					++ashGrowth;
					
				if (ashGrowth == ashRate){
						++ashCount;
						this.ashGrowth = 0;
				}
			}
				
			if (this.isBurning && firepitBurnTime == 0){
					this.isBurning = false;
					this.coalBurn = 0;
					this.ashBurn = 0;
					this.coalRate = 0;
					this.ashRate = 0;
			}
			
		}
		this.markDirty();
	}

	
	public void setFuelValues(ItemStack heldItem) {
		if (heldItem.isEmpty()){}
		else
		{ 
			Item item = heldItem.getItem();
			if(Block.getBlockFromItem(item).getDefaultState().getMaterial() == Material.WOOD)
	        {
	                this.firepitBurnTime += 300;
	                this.coalBurn += 100;
	                this.coalRate = 25;
	                this.ashBurn += 100;
	                heldItem.shrink(1);
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
