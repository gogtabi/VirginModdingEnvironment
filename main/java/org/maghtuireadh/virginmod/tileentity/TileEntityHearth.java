package org.maghtuireadh.virginmod.tileentity;

import java.util.Random;

import org.maghtuireadh.virginmod.init.ItemInit;
import org.maghtuireadh.virginmod.objects.blocks.hearths.BlockFirepit;
import org.maghtuireadh.virginmod.objects.blocks.hearths.BlockHearth;
import org.maghtuireadh.virginmod.util.Utils;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class TileEntityHearth extends TileEntity implements ITickable{
	private int MaxBurn, hearthState, rainingBurnRate, bankedBurnRate, coalProdTime, coalInCount, coalProgress, coalProdBase, ashProdTime, ashBaseRate, ashInCount, ashProgress, ashBaseRateRate, baseBurnRate, fuelLevel, stokedTime, bankedburnRate, stokedBurnRate = 0;
	private double coalProdRate, ashProdRate = 0.0;
	private boolean isStoked, isBanked, isLit;
	
	public TileEntityHearth() {
		
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.fuelLevel = nbt.getInteger("FuelLevel");
		this.coalProdTime = nbt.getInteger("CoalProdTime");
		this.coalInCount = nbt.getInteger("CoalInCount");
		this.coalProgress = nbt.getInteger("CoalProgress)");
		this.coalProdBase = nbt.getInteger("CoalProdBase");
		this.ashProdTime = nbt.getInteger("AshProdTime");
		this.ashInCount = nbt.getInteger("AshInCount");
		this.ashProgress = nbt.getInteger("AshProgress");
		this.baseBurnRate = nbt.getInteger("BaseBurnRate");
		this.stokedTime = nbt.getInteger("StokedTime");
		this.bankedBurnRate = nbt.getInteger("BankedBurnRate");
		this.stokedBurnRate = nbt.getInteger("StokedBurnRate");
		this.rainingBurnRate = nbt.getInteger("RainingBurnRate");
		this.isStoked = nbt.getBoolean("IsStoked");
		this.isBanked = nbt.getBoolean("IsBanked");
		this.isLit = nbt.getBoolean("IsLit");
		this.ashProdRate = nbt.getDouble("AshProdRate");
		this.coalProdRate = nbt.getDouble("CoalProdRate");
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		nbt.setInteger("FuelLevel", this.fuelLevel);
		nbt.setInteger("CoalProdTime", this.coalProdTime);
		nbt.setInteger("CoalInCount", this.coalInCount);
		nbt.setInteger("CoalProgress", this.coalProgress);
		nbt.setInteger("CoalProdBase", this.coalProdBase);
		nbt.setInteger("AshProdTime", this.ashProdTime);
		nbt.setInteger("ashInCount", this.ashInCount);
		nbt.setInteger("AshProgress", this.ashProgress);
		nbt.setInteger("BaseBurnRate", this.baseBurnRate);
		nbt.setInteger("StokedTime", this.stokedTime);
		nbt.setInteger("BankedBurnRate", this.bankedBurnRate);
		nbt.setInteger("StokedBurnRate", this.stokedBurnRate);
		nbt.setInteger("RainingBurnRate", this.rainingBurnRate);
		nbt.setBoolean("IsStoked", this.isStoked);
		nbt.setBoolean("IsBanked", this.isBanked);
		nbt.setBoolean("IsLit", this.isLit);
		nbt.setDouble("AshProdRate", this.ashProdRate);
		nbt.setDouble("CoalProdRate", this.coalProdRate);
		return super.writeToNBT(nbt);
	}

	@Override
	public void update() {
		if (isLit) {
			if (stokedTime > 0) {
				fuelLevel = fuelLevel - baseBurnRate;
				stokedTime = stokedTime - stokedBurnRate;
			} else if (((BlockFirepit) this.getBlockType()).getWeather(world, pos)) {
				fuelLevel = fuelLevel - rainingBurnRate;
			} else {
				fuelLevel = fuelLevel - baseBurnRate;
			}

			if (fuelLevel <= 0) {
				if (fuelLevel <= 0 && coalInCount > 0) {
					Random rand = new Random();
					int burnChance = rand.nextInt(100);

					if (burnChance < coalInCount * 10) {
						coalInCount--;
						ashInCount++;
						fuelLevel = 200;
						isStoked = false;
						Utils.getLogger().info("Coals isLit: " + coalInCount);
					} else {
						fuelLevel = 0;
						hearthState = 8;
						isLit = false;
						isStoked = false;
						isBanked = false;
						stokedTime = 0;
						coalProdTime = 0;
						coalProdRate = 0;
						coalProdBase = 0;
						ashProdTime = 0;
						ashProdRate = 0;
						ashBaseRate = 0;
					}
				} else {
					fuelLevel = 0;
					hearthState = 8;
					isLit = false;
					isStoked = false;
					isBanked = false;
					stokedTime = 0;
					coalProdTime = 0;
					coalProdRate = 0;
					coalProdBase = 0;
					ashProdTime = 0;
					ashProdRate = 0;
					ashBaseRate = 0;
				}
			} else if (fuelLevel != 0 && fuelLevel < MathHelper.floor(MaxBurn * 0.10)) {
				hearthState = 4; // set BlockFirepit State To lit_firepit1
				coalProdRate = (coalProdBase * .85); // Increase rate of coal production by 15%
				ashProdRate = (ashBaseRate * 1.3); // Decrease rate of ash production by 30%
				ashProgress++;
				coalProgress++;
				isBanked = false;
			} else if (fuelLevel >= MathHelper.floor(MaxBurn * 0.10)
					&& fuelLevel < MathHelper.floor(MaxBurn * 0.40)) {
				hearthState = 5; // set BlockFirepit State To lit_firepit2
				coalProdRate = coalProdBase;
				ashProdRate = (ashBaseRate * 1.15); // Decrease rate of ash production by 15%
				ashProgress++;
				coalProgress++;
				isBanked = false;
			} else if (fuelLevel >= MathHelper.floor(MaxBurn * 0.40)
					&& fuelLevel < MathHelper.floor(MaxBurn * 0.70)) {
				hearthState = 6; // set BlockFirepit State To lit_firepit3
				coalProdRate = (coalProdBase * 1.15); // Decrease rate of coal production by 15%
				ashProdRate = ashBaseRate;
				ashProgress++;
				coalProgress++;
				isBanked = false;
			} else if (fuelLevel > (MathHelper.floor(MaxBurn * 0.70))) {
				hearthState=7; // set BlockFirepit State To lit_firepit4
				coalProdRate = (coalProdBase * 1.30); // Decrease rate of coal production by 30%
				ashProdRate = (ashBaseRate * .85); // Increase rate of ash control by 15%
				ashProgress++;
				coalProgress++;
				isBanked = false;
			}

			else {
				Utils.getLogger().info("Oh Shit Son, What?");
			}
		}
		if (((ashProgress >= ashProdRate) && isLit) && ashInCount < 10) {
			ashInCount++; // Increase amount of ash that will be returned.
			ashProgress = 0; // Reset ashProgress
		}
		if (((coalProgress >= coalProdRate) && isLit) && coalInCount < 10) {
			coalInCount++; // Increase amount of coal that will be returned.
			coalProgress = 0; // Reset coalProgress
		}
		Utils.getLogger().info("fuelpitBurnTime: " + fuelLevel + " ashInCount: " + ashInCount + " coalInCount: "
				+ coalInCount + " isStoked: " + isStoked + " isBanked: " + isBanked);
		this.setIsLit();
		stokedCheck();
		markDirty();
	}
	
	public void stokedCheck() {
		if (isStoked == true && stokedTime == 0 || isStoked == true && isLit == false) {
			isStoked = false;
			stokedTime = 0;
		}
	}
	
	public void setIsLit() {
		if (this.world != null && !this.world.isRemote) {
			this.blockType = this.getBlockType();
			BlockHearth firePit = ((BlockHearth) this.blockType);

			if (blockType instanceof BlockHearth) {
				if (firePit.getBurning() != isLit) { // Only update BlockFirepit Burning bool
					firePit.setIsLit(isLit);       // on a change
					
				}
				if (firePit.getStoked() != isStoked) {
					firePit.setStoked(isStoked);
				}
				if (firePit.getState(world, pos) != hearthState) { // Only update BlockFirepit
				   firePit.setState(hearthState, world, pos);    // State On A Change
				/* ((this.getBlockMetadata() != pitState)){
					IBlockState currentState = this.world.getBlockState(pos);
					this.world.setBlockState(BlockFirepit.PITSTATE, currentState.withProperty(BlockFirepit.PITSTATE, pitstate))
					*/
				}

			}
		}
	}

	
}
