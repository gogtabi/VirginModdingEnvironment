package org.maghtuireadh.virginmod.tileentity;

import java.util.Random;

import org.maghtuireadh.virginmod.config.VMEConfig;
import org.maghtuireadh.virginmod.init.ItemInit;
import org.maghtuireadh.virginmod.objects.blocks.hearths.BlockFirepit;
import org.maghtuireadh.virginmod.util.Utils;
import org.maghtuireadh.virginmod.util.handlers.ListHandler;

import akka.japi.Util;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class TileEntityFirepit extends TileEntityHearth
{
	private boolean isLit, isBanked, isStoked = false;
	private int firepitMaxBurn = (int)VMEConfig.firepitMaxFuelLevel*1200;
	long firepitBurnTime, ashBase, coalBase;
	private int burnRate, coalCount, coalGrowth, ashCount, ashGrowth,  pitState, stokedTimer = 0;
	private int firepitBurnRate = VMEConfig.firepitDefaultBurnRate;
	private int stokedBurnRate = VMEConfig.firepitStokedBurnRate;
	private int bankedBurnRate = VMEConfig.firepitBankedBurnRate;
	private int rainingBurnRate = VMEConfig.firepitRainingBurnRate;
	private int coalMax = (int)VMEConfig.firepitCoalMax;
	private int ashMax = (int)VMEConfig.firepitAshMax;
	private int rainIgnitePenalty = 0; //VMEConfig.rainIgnitePenalty;
	private double minBankedCoalRateMod = VMEConfig.firepitMinBankedCoalMod;
	private double minBankedAshRateMod = VMEConfig.firepitMinBankedAshMod;
	private double minCoalRateMod = VMEConfig.firepitMinCoalMod;
	private double minAshRateMod = VMEConfig.firepitMinAshMod;
	private double lowBankedCoalRateMod = VMEConfig.firepitLowBankedCoalMod;
	private double lowBankedAshRateMod = VMEConfig.firepitLowBankedAshMod;
	private double lowCoalRateMod = VMEConfig.firepitLowCoalMod;
	private double lowAshRateMod = VMEConfig.firepitLowAshMod;
	private double midStokedCoalRateMod = VMEConfig.firepitMidStokedCoalMod;
	private double midStokedAshRateMod = VMEConfig.firepitMidStokedAshMod;
	private double midCoalRateMod = VMEConfig.firepitMidCoalMod;
	private double midAshRateMod = VMEConfig.firepitMidAshMod;
	private double maxStokedCoalRateMod = VMEConfig.firepitMaxStokedCoalMod;
	private double maxStokedAshRateMod = VMEConfig.firepitMaxStokedAshMod;
	private double maxCoalRateMod = VMEConfig.firepitMaxCoalMod;
	private double maxAshRateMod = VMEConfig.firepitMaxAshMod;
	private boolean setIgnited = false;
	
	private double coalRate, ashRate = 0;
	private int lastState = 0;
	int low = (int) (firepitMaxBurn*.10);
	int mid = (int) (firepitMaxBurn*.40);
	int max = (int) (firepitMaxBurn*.70);
	private boolean Lit;
	BlockPos bpos = null;

	
	public TileEntityFirepit() 
	{
		
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) 
	{
		super.readFromNBT(nbt);
		this.coalCount = nbt.getInteger("CoalCount");
		this.coalGrowth = nbt.getInteger("CoalGrowth");
		this.coalRate = nbt.getDouble("CoalRate");
		this.coalBase = nbt.getLong("CoalBase");
		this.ashCount = nbt.getInteger("AshCount");
		this.ashGrowth = nbt.getInteger("AshGrowth");
		this.ashRate = nbt.getDouble("AshRate");
		this.ashBase = nbt.getLong("AshBase");
		this.firepitBurnTime = nbt.getLong("FPBT");
		this.pitState = nbt.getInteger("PitState");
		this.isLit = nbt.getBoolean("IsLit");
		this.isStoked = nbt.getBoolean("IsStoked");
		this.isBanked = nbt.getBoolean("IsBanked");
		this.stokedTimer = nbt.getInteger("StokedTimer");
		this.rainIgnitePenalty = nbt.getInteger("RainPenalty");
		Utils.getLogger().info("FP-TE: read nbt");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) 
	{
		nbt.setBoolean("IsBanked", isBanked);
		nbt.setLong("FPBT", firepitBurnTime);
		nbt.setInteger("CoalCount", coalCount);
		nbt.setInteger("CoalGrowth", coalGrowth);
		nbt.setDouble("CoalRate", coalRate);
		nbt.setLong("CoalBase", coalBase);
		nbt.setInteger("AshCount", ashCount);
		nbt.setInteger("AshGrowth", ashGrowth);
		nbt.setDouble("AshRate", ashRate);
		nbt.setLong("AshBase", ashBase);
		nbt.setInteger("PitState", pitState);
		nbt.setBoolean("IsLit", isLit);
		nbt.setBoolean("IsStoked", isStoked);
		nbt.setInteger("StokedTimer", stokedTimer);
		nbt.setInteger("LastState", lastState);
		nbt.setInteger("RainPenalty", rainIgnitePenalty);
		Utils.getLogger().info("FP-TE: write");
		return super.writeToNBT(nbt);
	}

	public void updateFirepit() 
	{
		/*Utils.getLogger().info("TEFirepit: updateFirepit() pre first if");
		if (this.world != null && !this.world.isRemote) 
		{
			Utils.getLogger().info("FP-TE: updateFirepit");
			this.blockType = this.getBlockType();
			BlockFirepit firePit = ((BlockFirepit) this.blockType);

			if (blockType instanceof BlockFirepit) 
			{
				Utils.getLogger().info("TEFirepit: updateFirepit() inside if instanceof");
				if (firePit.getState(world, pos) != pitState) // Only update BlockFirepit State on a Change
				{ 	
					Utils.getLogger().info("TEFirepit: updateFirepit() inside update if changed");
					firePit.setState(pitState, world, pos);
					Utils.getLogger().info("FP-TE: set new state");
				}
				markDirty();
			}				
		}*/
	}

	@Override
	public void update() 
	{
		Utils.getLogger().info("TEFirepit: update()");
		Utils.getLogger().info("TEFirepit: isLitValue inside update(): " + isLit);
		if(!world.isRemote) 
		{

			Utils.getLogger().info("TEFirepit: update() (Inside !world.isRemote)");
			Utils.getLogger().info("TEFirepit: isLitValue inside update()2: " + isLit);
		/*if (isLit) 
			{
				Utils.getLogger().info("FP-TE: it's lit");
				if (stokedTimer > 0) 
				{

					Utils.getLogger().info("TEFirepit Logger1");
					firepitBurnTime = firepitBurnTime - stokedBurnRate;
					stokedTimer--;
				}
				else if (((BlockFirepit) this.getBlockType()).getWeather(world, pos)) 
				{

					Utils.getLogger().info("TEFirepit Logger2");
					firepitBurnTime = firepitBurnTime - rainingBurnRate;
				} 
				else if (isBanked==true) 
				{

					Utils.getLogger().info("TEFirepit Logger3");
					firepitBurnTime = firepitBurnTime - bankedBurnRate;
				} 
				else 
				{

					Utils.getLogger().info("TEFirepit Logger4");
					firepitBurnTime = firepitBurnTime - firepitBurnRate;
				}
				
				if (firepitBurnTime <= 0) 
				{
					Utils.getLogger().info("TEFirepit Logger5");
					if (firepitBurnTime <= 0 && coalCount > 0) 
					{

						Utils.getLogger().info("TEFirepit Logger6");
						Random rand = new Random();
						int burnChance = rand.nextInt(100);
	
						if (burnChance < coalCount * 10) 
						{

							Utils.getLogger().info("TEFirepit Logger7");
							coalCount--;
							ashCount++;
							firepitBurnTime = 200;
							isStoked = false;
						} 
						else 
						{

							Utils.getLogger().info("TEFirepit Logger8");
							firepitBurnTime = 0;
							pitState = 8;
							isLit = false;
							isStoked = false;
							isBanked = false;
							stokedTimer = 0;
							coalRate = 0;
							coalBase = 0;
							ashRate = 0;
							ashBase = 0;
						}
					} 
					else 
					{

						Utils.getLogger().info("TEFirepit Logger9");
						firepitBurnTime = 0;
						pitState = 8;
						isLit = false;
						isStoked = false;
						isBanked = false;
						stokedTimer = 0;
						coalRate = 0;
						coalBase = 0;
						ashRate = 0;
						ashBase = 0;
					}
					
				} 
				else if ((firepitBurnTime != 0 && firepitBurnTime < low && isBanked)) 
				{
					if(lastState==5) 
					{

						Utils.getLogger().info("TEFirepit Logger10");
						pitState = 14; // set BlockFirepit State To lit_firepit1
						coalRate = (coalBase * minBankedCoalRateMod); // Increase rate of coal production by 30%
						ashRate = (ashBase * minBankedAshRateMod); // Decrease rate of ash production by 45%
						ashGrowth++;
						coalGrowth++;
						lastState=14;
						isStoked=false;
						stokedTimer=0;
					}
				} 
				else if ((firepitBurnTime != 0 && firepitBurnTime < low) && !isBanked)
				{
					Utils.getLogger().info("TEFirepit Logger11");
					pitState = 4; // set BlockFirepit State To lit_firepit1
					coalRate = (coalBase * minCoalRateMod); // Increase rate of coal production by 15%
					ashRate = (ashBase * minAshRateMod); // Decrease rate of ash production by 30%
					ashGrowth++;
					coalGrowth++;
					lastState=4;
					isStoked=false;
					stokedTimer=0;
				} 
				else if ((firepitBurnTime >= low && firepitBurnTime < mid) && isBanked) 
				{
					Utils.getLogger().info("TEFirepit Logger12");
					pitState = 15; // set BlockFirepit State To extinguished_firepit2
					isStoked=false;
					stokedTimer=0;
					coalRate = (coalBase*lowBankedCoalRateMod); // Increase rate of coal production by 15%
					ashRate = (ashBase * lowBankedAshRateMod); // Decrease rate of ash production by 30%
					ashGrowth++;
					coalGrowth++;
					lastState = 15;
				} 
				else if (firepitBurnTime >= low && firepitBurnTime < mid) 
				{
					Utils.getLogger().info("TEFirepit Logger13");
					pitState = 5; // set BlockFirepit State To lit_firepit2
					isStoked=false;
					stokedTimer=0;
					coalRate = (coalBase * midCoalRateMod);
					ashRate = (ashBase * midAshRateMod); // Decrease rate of ash production by 15%
					ashGrowth++;
					coalGrowth++;
					lastState = 15;
				} 
				else if ((firepitBurnTime > mid && firepitBurnTime <= max) && isStoked) 
				{
					Utils.getLogger().info("TEFirepit Logger14");
					pitState = 12; // set BlockFirepit State To lit_firepit3 (stoked)
					coalRate = (coalBase * midStokedCoalRateMod); // Decrease rate of coal production by 30%
					ashRate = ashBase*midStokedAshRateMod; // Increase Ash Production by 15%
					ashGrowth++;
					coalGrowth++;
					isBanked = false;
					lastState = 12;
				} 
				else if ((firepitBurnTime >= mid && firepitBurnTime < max) && !isStoked) 
				{
					Utils.getLogger().info("TEFirepit Logger15");
					pitState = 6; // set BlockFirepit State To lit_firepit3
					isBanked=false;
					coalRate = (coalBase * midCoalRateMod);
					ashRate = (ashBase * midAshRateMod); // Decrease rate of ash production by 15%
					ashGrowth++;
					coalGrowth++;
					lastState = 5;
				} 
				else if (firepitBurnTime >= max && isStoked) 
				{
					Utils.getLogger().info("TEFirepit Logger16");
					pitState=13; // set BlockFirepit State To lit_firepit4 (stoked)
					coalRate = (coalBase * maxStokedCoalRateMod); // Decrease rate of coal production by 45%
					ashRate = (ashBase * maxStokedAshRateMod); // Increase rate of ash production by 30%
					ashGrowth++;
					coalGrowth++;
					isBanked = false;
				}
				else if (firepitBurnTime >= max && !isStoked) 
				{
					Utils.getLogger().info("TEFirepit Logger17");
					pitState=7; // set BlockFirepit State To lit_firepit4
					coalRate = (coalBase * maxCoalRateMod); // Decrease rate of coal production by 30%
					ashRate = (ashBase * maxAshRateMod); // Increase rate of ash control by 15%
					ashGrowth++;
					coalGrowth++;
					isBanked = false;
					lastState = 7;
				} 
				
				if (((ashGrowth >= ashRate) && isLit) && ashCount < ashMax) 
				{
					Utils.getLogger().info("TEFirepit Logger18");
					ashCount++; // Increase amount of ash that will be returned.
					ashGrowth = 0; // Reset ashGrowth
				}
				
				if (((coalGrowth >= coalRate) && isLit) && coalCount < coalMax) 
				{
					Utils.getLogger().info("TEFirepit Logger19");
					coalCount++; // Increase amount of coal that will be returned.
					coalGrowth = 0; // Reset coalGrowth
				}
	
			}
		this.updateFirepit();
		stokedCheck();*/
		markDirty();
		}
	}

	public void stokedCheck() 
	{
		Utils.getLogger().info("TEFirepit Logger20");
		/*if (isStoked == true && stokedTimer == 0 || isStoked == true && isLit == false) 
		{
			isStoked = false;
			stokedTimer = 0;
		}*/
	}

	public boolean attemptIgnite(int igniteChance, BlockPos pos) 
	{
		if(!world.isRemote) 
		{

		Utils.getLogger().info("FP-TE:attempt Ignite Fired");
		bpos = pos;
		Lit=true;
		updateFirepit();	
		markDirty();
		return true;
		}
		return true;
	}
	
	public void setTEFuel(long fuel, int fuelIndex) 
	{
		if(!world.isRemote) 
		{
			Utils.getLogger().info("FP-TE:Initiating Fuel Setting");
			Utils.getLogger().info("FP-TE:FuelIndex Return: " + fuelIndex);
			long burnTime = fuel;
			long coalburnrate = ListHandler.CoalBurnRate.get(fuelIndex);
			long ashburnrate = ListHandler.AshBurnRate.get(fuelIndex);
			
			firepitBurnTime=firepitBurnTime+burnTime;
			if(coalRate == 0)
			{
				coalBase=coalburnrate;
			}
			else
			{
				coalBase=(long) ((coalburnrate+coalRate)/2);
			}
			if(ashBase == 0)
			{
				ashBase=ashburnrate;
			}
			else
			{
				ashBase=(long) ((ashburnrate+ashRate)/2);
			}
			
			if (!isLit) 
			{
				pitState = getUnlitState(firepitBurnTime);
			}
			updateFirepit();
			markDirty();
		}
	}
	
	public boolean getTEFuelMax() 
	{

		Utils.getLogger().info("TEFirepit Logger21");
		return firepitBurnTime<firepitMaxBurn ? true:false;
		
	};
	
	public void cleanPit(EntityPlayer player) 
	{
		if(!world.isRemote) 
		{
			Utils.getLogger().info("FP-TE:isLit?: " + isLit);
			Utils.getLogger().info("FP-TE:Beginning cleanPit: " + "coalCount: " + coalCount + " ashCount: " + ashCount);
			Utils.getLogger().info("FP-TE:Fire Pit Burn Time:" + firepitBurnTime);
			
			if (!isLit && (coalCount != 0 || ashCount != 0)) 
				{
					player.inventory.addItemStackToInventory(new ItemStack(Items.COAL, coalCount, 1));
					player.inventory.addItemStackToInventory(new ItemStack(ItemInit.ATD_WOOD_ASH, ashCount));
					coalCount = 0;
					ashCount = 0;
					Utils.getLogger().info("FP-TE:Beginning cleanPit1: " + "coalCount: " + coalCount + " ashCount: " + ashCount);
					Utils.getLogger().info("FP-TE:Why are we doing this? It's lit!");
					pitState = getUnlitState(firepitBurnTime);
					updateFirepit();
				} 
				else if (!isLit && firepitBurnTime > 0) 
				{
					player.inventory.addItemStackToInventory(
					new ItemStack(Blocks.PLANKS, MathHelper.floor(firepitBurnTime / 1000), 2));
					firepitBurnTime = 0;
					Utils.getLogger().info("FP-TE:isLit: " + isLit);
					Utils.getLogger().info("FP-TE:Beginning cleanPit2: " + "coalCount: " + coalCount + " ashCount: " + ashCount);
					Utils.getLogger().info("FP-TE:Why are we doing this too? It's lit!");
					pitState = getUnlitState(firepitBurnTime);
					updateFirepit();
				}
				else
				{
					
				}
			markDirty();
		}
	}
		
		

	public int getUnlitState(long fuelLevel) // returns state based on fuelLevel
	{ 
		Utils.getLogger().info("TEFirepit Logger22");
		if(!world.isRemote) 
		{
			if (!isLit) 
			{

				Utils.getLogger().info("TEFirepit Logger23");
				if ((coalCount == 0 && ashCount == 0) && fuelLevel > 0) 
				{

					Utils.getLogger().info("TEFirepit Logger24");
					if (firepitBurnTime >= 0 && firepitBurnTime < MathHelper.floor(firepitMaxBurn * 0.10)) 
					{

						Utils.getLogger().info("TEFirepit Logger25");
						return 1; // unlit_firepit1
					}
					if (firepitBurnTime >= MathHelper.floor(firepitMaxBurn * 0.10) && firepitBurnTime < MathHelper.floor(firepitMaxBurn * 0.70)) 
					{

						Utils.getLogger().info("TEFirepit Logger26");
						return 1; // unlit_firepit2
					}
					if (firepitBurnTime >= MathHelper.floor(firepitMaxBurn * 0.70)) 
					{
						Utils.getLogger().info("TEFirepit Logger27");
						return 3; // unlit_firepit3
					} 
				} 
				else if ((coalCount > 0 || ashCount > 0) && fuelLevel > 0) 
				{
					Utils.getLogger().info("TEFirepit Logger28");
					if ((firepitBurnTime >= 0) && firepitBurnTime < MathHelper.floor(firepitMaxBurn * 0.10)) 
					{
						Utils.getLogger().info("TEFirepit Logger29");
						return 9; // extinguished_firepit1
					}
					if (firepitBurnTime < MathHelper.floor(firepitMaxBurn * 0.10) && firepitBurnTime < MathHelper.floor(firepitMaxBurn * 0.70)) 
					{
						Utils.getLogger().info("TEFirepit Logger30");
						return 10; // extinguished_firepit2
					}
					if (firepitBurnTime >= MathHelper.floor(firepitMaxBurn * 0.70) && firepitBurnTime < MathHelper.floor(firepitMaxBurn)) 
					{
						Utils.getLogger().info("TEFirepit Logger31");
						return 11; // extinguished_firepit3
					}
				} 
				else 
				{
					if ((coalCount > 0 || ashCount > 0) && fuelLevel == 0) 
					{

						Utils.getLogger().info("TEFirepit Logger32");
						return 8; // dirty_firepit
					}
					if ((coalCount == 0 && ashCount == 0) && fuelLevel == 0) 
					{

						Utils.getLogger().info("TEFirepit Logger33");
						return 0; // empty_firepit
					}
				}
			} 
			else 
			{
				if (fuelLevel == 0) 
				{
					Utils.getLogger().info("TEFirepit Logger34");
					return 0; // vme:empty_firepit
				}
				if (firepitBurnTime > 0 && firepitBurnTime < MathHelper.floor(firepitMaxBurn * 0.10)) 
				{
					Utils.getLogger().info("TEFirepit Logger35");
					return 4; // lit_firepit1
				}
				if ((firepitBurnTime < MathHelper.floor(firepitMaxBurn * 0.10) && firepitBurnTime < MathHelper.floor(firepitMaxBurn * 0.40))) 
				{
					Utils.getLogger().info("TEFirepit Logger36");
					return 5; // lit_firepit2
				}
				if (firepitBurnTime < MathHelper.floor(firepitMaxBurn * 0.40) && firepitBurnTime < MathHelper.floor(firepitMaxBurn * 0.70)) 
				{
					Utils.getLogger().info("TEFirepit Logger37");
					return 6; // lit_firepit3
				}
				if (firepitBurnTime > MathHelper.floor(firepitMaxBurn * 0.70)) 
				{
					Utils.getLogger().info("TEFirepit Logger38");
					return 7; // lit_firepit4
				}
				}
			return 11;
			} 
		return 11;
	}

	public int getExtinguishedState(int pitState) 
	{

		Utils.getLogger().info("TEFirepit Logger39");
		if(!world.isRemote) 
		{

			Utils.getLogger().info("TEFirepit Logger40");
			switch (pitState) 
			{
			case 4: // lit_firepit1
				return 8; // dirty_firepit
	
			case 5: // lit_firepit2
				return 9; // extinguished_Firepit1
	
			case 6: // lit_firepit3
				return 10; // extinguished_Firepit2
	
			case 7: // lit_firepit4
				return 11; // extinguished_Firepit3
	
			default:
				return 8; // dirty_firepit
			}
		}
		return 8;
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
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) 
	{
		this.readFromNBT(pkt.getNbtCompound());
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

	public void setExtinguishedState(boolean b) 
	{
		Utils.getLogger().info("setExtinguishedState");
		if(!world.isRemote) {
			Utils.getLogger().info("FP-TE: set ext");
		isLit=b;
		pitState = getUnlitState(firepitBurnTime);
		}
	}

	
	public boolean getIsLit() 
	{

		Utils.getLogger().info("TEFirepit Logger1");
		return isLit;
	}

	
	public int getCoalCount() 
	{

		Utils.getLogger().info("TEFirepit Logger1");
		return coalCount;
	}
	
	public void setCoalCount(int i) 
	{

		Utils.getLogger().info("TEFirepit Logger1");
		if(!world.isRemote) {
			Utils.getLogger().info("FP-TE: set coal count");
		coalCount = coalCount-i;
		}
	}
	
	public int getAshCount() 
	{

		Utils.getLogger().info("TEFirepit Logger1");
		return ashCount;
	}


	public void setAshCount(int i) 
	{

		Utils.getLogger().info("TEFirepit Logger1");
		if(!world.isRemote) 
		{
			Utils.getLogger().info("FP-TE: set ash count");
		coalCount = coalCount-i;
		}
	}

}