package org.maghtuireadh.virginmod.tileentity;


import org.maghtuireadh.virginmod.objects.blocks.furnaces.BlockFirepit;
import org.maghtuireadh.virginmod.util.Utils;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileEntityFirepit extends TileEntity implements ITickable {
	private boolean Burning, isStoked;
	private int firepitBurnTime = 0;
	private int fuelLvl = 0;
	private int burnRate = 0;
	private int coalBurn = 0;
	private int coalCount = 0;
	private int coalGrowth = 0;
	private double coalRate = 0;
	private int coalBase = 0;
	private int ashBurn = 0;
	private int ashCount = 0;
	private int ashGrowth = 0;
	private double ashRate = 0;
	private int ashBase = 0;
	private int pitState = 0;
	float lightLvl = 0.0F;
	private int cooldown;
	@SuppressWarnings("unused")
	private IBlockState blockStateLit, blockStateUnlit;
	public void TileEntityFirpit() {
		
	}

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
		this.coalRate = nbt.getDouble("CoalRate");
		this.coalBase = nbt.getInteger("CoalBase");
		this.ashBurn = nbt.getInteger("AshBurn");
		this.ashCount = nbt.getInteger("AshCount");
		this.ashGrowth = nbt.getInteger("AshGrowth");
		this.ashRate = nbt.getDouble("AshRate");
		this.ashBase = nbt.getInteger("AshBase");
		this.pitState = nbt.getInteger("PitState");
		this.Burning = nbt.getBoolean("Burning");
		this.isStoked = nbt.getBoolean("IsStoked");
		this.lightLvl = nbt.getFloat("Light Level");

	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		nbt.setInteger("Cooldown", cooldown);
		nbt.setInteger("FPBT", firepitBurnTime);
		nbt.setInteger("FuelLvl", fuelLvl);
		nbt.setInteger("BurnRate", burnRate);
		nbt.setInteger("CoalBurn", coalBurn);
		nbt.setInteger("CoalCount", coalCount);
		nbt.setInteger("CoalGrowth", coalGrowth);
		nbt.setDouble("CoalRate", coalRate);
		nbt.setInteger("CoalBase", coalBase);
		nbt.setInteger("AshBurn", ashBurn);
		nbt.setInteger("AshCount", ashCount);
		nbt.setInteger("AshGrowth", ashGrowth);
		nbt.setDouble("AshRate", ashRate);
		nbt.setInteger("AshBase", ashBase);
		nbt.setInteger("PitState", pitState);
		nbt.setBoolean("Burning", Burning);
		nbt.setBoolean("IsStoked", isStoked);
		nbt.setFloat("Light Level", lightLvl);
		return super.writeToNBT(nbt);
	}

	public float getLight() {
		return lightLvl;
	}

	public boolean getBurning() {
		return Burning;
	}

	public void setBurning() {
        if (this.world != null && !this.world.isRemote) { 
            this.blockType = this.getBlockType();

            if (this.blockType instanceof BlockFirepit) {
                if(((BlockFirepit) this.blockType).getBurning()!=Burning){
                		((BlockFirepit) this.blockType).setBurning(Burning);
                }
                if(((BlockFirepit) this.blockType).getState(world, pos)!=pitState);
                ((BlockFirepit) this.blockType).setState(pitState, world, pos);
            }
            markDirty();
        }
    }
	
	public void update(){
		if (firepitBurnTime>0 && !Burning)
			{
				Burning=true;
				markDirty();
				Utils.getLogger().info("Update1: " + "Light: " + lightLvl + " Burning: " + Burning + " BurnTime:" + firepitBurnTime);
				Utils.getLogger().info("It's Burning");
			}
		if (Burning) {
				--firepitBurnTime;
				if(firepitBurnTime==0) {
					Burning = false;
					pitState = 8;
					coalBurn = 0;
					coalRate = 0;
					coalBase = 0;
					ashBurn = 0;
					ashRate = 0;
					ashBase = 0;
					
				}
				if(firepitBurnTime!=0 && firepitBurnTime<=200) {
					pitState=4;
					coalRate = (coalBase*.85);
					ashRate = (ashBase*1.3);
					ashCount++;
					coalCount++;
				}
				if(firepitBurnTime>=201 && firepitBurnTime<=600) {
					pitState=5;
					coalRate = coalBase;
					ashRate = (ashBase*1.15);
					ashCount++;
					coalCount++;
				}
				if(firepitBurnTime>=601 && firepitBurnTime<=900) {
					pitState=6;
					coalRate = (coalBase*1.15);
					ashRate = ashBase;
					ashCount++;
					coalCount++;
				}
				if(firepitBurnTime>=901 && firepitBurnTime<=1200) {
					pitState=7;
					coalRate = (coalBase*1.30);
					ashRate = (ashBase*.85);
					ashCount++;
					coalCount++;
				}	
			markDirty();
			this.setBurning();
		}
	}
		
/*		if(!Burning) {
		if (!Burning && coalBurn > 0) {
			--coalBurn;
			++coalGrowth;
			if (coalGrowth == coalRate) {
				++coalCount;
				coalGrowth = 0;
				markDirty();
			}
		}

		if (!Burning && ashBurn > 0) {
			--ashBurn;
			++ashGrowth;
			markDirty();

			if (ashGrowth == ashRate) {
				++ashCount;
				this.ashGrowth = 0;
				markDirty();
			}
		}*/
		

public void setFuelValues(ItemStack heldItem) {
		if (heldItem.isEmpty()) {
		} else {
			Item item = heldItem.getItem();
			if (Block.getBlockFromItem(item).getDefaultState().getMaterial() == Material.WOOD) {
				firepitBurnTime += 300;
				coalBurn += 100; //How long will produce coal
				coalBase = 100; //How often will produce coal
				ashBurn += 100; //How long will produce ash
				ashBase = 100;	//How often will produce ash		
				heldItem.shrink(1);
				markDirty();
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