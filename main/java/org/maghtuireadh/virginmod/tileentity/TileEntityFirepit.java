package org.maghtuireadh.virginmod.tileentity;

import java.util.Random;

import org.maghtuireadh.virginmod.init.ItemInit;
import org.maghtuireadh.virginmod.objects.blocks.hearths.BlockFirepit;
import org.maghtuireadh.virginmod.util.Utils;

import akka.japi.Util;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.math.MathHelper;

public class TileEntityFirepit extends TileEntityHearth{
	private boolean Burning, isBanked, isStoked = false;
	private int firepitMaxBurn = 24000;
	private int firepitBurnTime, burnRate, coalBurn, coalCount, coalGrowth, coalBase, ashBurn, ashCount, ashGrowth, ashBase, pitState, stokedTimer = 0;
	private int firepitBurnRate = 2;
	private int stokedBurnRate = 3;
	private int bankedBurnRate = 1;
	private int rainingBurnRate = 4;
	private double coalRate, ashRate = 0;
	private int lastState = 0;
	int low=(int) (firepitMaxBurn*.10);
	int mid=(int) (firepitMaxBurn*.40);
	int max=(int) (firepitMaxBurn*.70);
	
	public TileEntityFirepit() {
		
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
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
		this.firepitBurnRate = nbt.getInteger("FirepitBurnRate");
		this.firepitBurnTime = nbt.getInteger("FPBT");
		this.bankedBurnRate = nbt.getInteger("BankedBurnRate");
		this.stokedBurnRate = nbt.getInteger("stokedBurnRate");
		this.pitState = nbt.getInteger("PitState");
		this.Burning = nbt.getBoolean("Burning");
		this.isStoked = nbt.getBoolean("IsStoked");
		this.isBanked = nbt.getBoolean("IsBanked");
		this.stokedTimer = nbt.getInteger("StokedTimer");
		this.lastState = nbt.getInteger("LastState");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		nbt.setBoolean("IsBanked", isBanked);
		nbt.setInteger("FirepitBurnRate", firepitBurnRate);
		nbt.setInteger("BankedBurnRate", bankedBurnRate);
		nbt.setInteger("StokedBurnRate", stokedBurnRate);
		nbt.setInteger("FPBT", firepitBurnTime);
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
		nbt.setInteger("StokedTimer", stokedTimer);
		nbt.setInteger("LastState", lastState);
		return super.writeToNBT(nbt);
	}

	public void setBurning() {
		if (this.world != null && !this.world.isRemote) {
			this.blockType = this.getBlockType();
			BlockFirepit firePit = ((BlockFirepit) this.blockType);

			if (blockType instanceof BlockFirepit) {
				if (firePit.getState(world, pos) != pitState) { // Only update BlockFirepit
							firePit.setState(pitState, world, pos);    // State On A Change
					}
				}				
			}
		}

	@Override
	public void update() {
		
		//Utils.getLogger().info("Update Is Happening");
		if (Burning) {
			if (stokedTimer > 0) {
				firepitBurnTime = firepitBurnTime - stokedBurnRate;
				stokedTimer--;
				//Utils.getLogger().info("Stoked Burn Is Happening");
			} else if (((BlockFirepit) this.getBlockType()).getWeather(world, pos)) {
				firepitBurnTime = firepitBurnTime - rainingBurnRate;
				//Utils.getLogger().info("Raining Burn Is happening");
			} else if (isBanked==true) {
				firepitBurnTime = firepitBurnTime - bankedBurnRate;
				//Utils.getLogger().info("Banked Burn Is happening");
			} else {
				firepitBurnTime = firepitBurnTime - firepitBurnRate;
				//Utils.getLogger().info("Normal Burn Is happening");
			}
			if (firepitBurnTime <= 0) {
				if (firepitBurnTime <= 0 && coalCount > 0) {
					Random rand = new Random();
					int burnChance = rand.nextInt(100);

					if (burnChance < coalCount * 10) {
						coalCount--;
						ashCount++;
						firepitBurnTime = 200;
						isStoked = false;
						//Utils.getLogger().info("Coals Burning: " + coalCount);
					} else {
						firepitBurnTime = 0;
						pitState = 8;
						Burning = false;
						isStoked = false;
						isBanked = false;
						stokedTimer = 0;
						coalBurn = 0;
						coalRate = 0;
						coalBase = 0;
						ashBurn = 0;
						ashRate = 0;
						ashBase = 0;
					}
				} else {
				firepitBurnTime = 0;
				pitState = 8;
				Burning = false;
				isStoked = false;
				isBanked = false;
				stokedTimer = 0;
				coalBurn = 0;
				coalRate = 0;
				coalBase = 0;
				ashBurn = 0;
				ashRate = 0;
				ashBase = 0;
				}
				
				} else if ((firepitBurnTime != 0 && firepitBurnTime < low && isBanked)) {
					if(lastState==5) {
						//Utils.getLogger().info("Below 10% & Banked");
						pitState = 14; // set BlockFirepit State To lit_firepit1
						coalRate = (coalBase * .85); // Increase rate of coal production by 15%
						ashRate = (ashBase * 1.3); // Decrease rate of ash production by 30%
						ashGrowth++;
						coalGrowth++;
						lastState=14;
						isStoked=false;
						stokedTimer=0;
				}
				else {
					
				}
				
				} else if ((firepitBurnTime != 0 && firepitBurnTime < low) && !isBanked){
					//Utils.getLogger().info("Below 10% & Not Banked");
					pitState = 4; // set BlockFirepit State To lit_firepit1
					coalRate = (coalBase * .85); // Increase rate of coal production by 15%
					ashRate = (ashBase * 1.3); // Decrease rate of ash production by 30%
					ashGrowth++;
					coalGrowth++;
					lastState=4;
					isStoked=false;
					stokedTimer=0;
				} else if ((firepitBurnTime >= low && firepitBurnTime < mid) && isBanked) {
	
					//Utils.getLogger().info("Between 10% - 40% & Banked");
					pitState = 15; // set BlockFirepit State To extinguished_firepit2
					isStoked=false;
					stokedTimer=0;
					coalRate = coalBase;
					ashRate = (ashBase * 1.15); // Decrease rate of ash production by 15%
					ashGrowth++;
					coalGrowth++;
					lastState = 15;
				} else if (firepitBurnTime >= low && firepitBurnTime < mid) {
					//Utils.getLogger().info("Below 10% & 40% Not Banked");
					pitState = 5; // set BlockFirepit State To lit_firepit2
					isStoked=false;
					stokedTimer=0;
					coalRate = coalBase;
					ashRate = (ashBase * 1.15); // Decrease rate of ash production by 15%
					ashGrowth++;
					coalGrowth++;
					lastState = 15;
				} 
				else if ((firepitBurnTime > mid && firepitBurnTime <= max) && isStoked) {
	
					//Utils.getLogger().info("Below 40 & 70% and Stoked");
					pitState = 12; // set BlockFirepit State To lit_firepit3 (stoked)
					coalRate = (coalBase * 1.15); // Decrease rate of coal production by 15%
					ashRate = ashBase;
					ashGrowth++;
					coalGrowth++;
					isBanked = false;
					lastState = 12;
				} else if ((firepitBurnTime >= mid && firepitBurnTime < max) && !isStoked) {
	
					//Utils.getLogger().info("Below 40 & 70% and Not Stoked");
					pitState = 6; // set BlockFirepit State To lit_firepit3
					isBanked=false;
					coalRate = coalBase;
					ashRate = (ashBase * 1.15); // Decrease rate of ash production by 15%
					ashGrowth++;
					coalGrowth++;
					lastState = 5;
				} 
				else if (firepitBurnTime >= max && isStoked) {
	
					//Utils.getLogger().info("Over 70% and Stoked");
					pitState=13; // set BlockFirepit State To lit_firepit4 (stoked)
					coalRate = (coalBase * 1.30); // Decrease rate of coal production by 30%
					ashRate = (ashBase * .85); // Increase rate of ash control by 15%
					ashGrowth++;
					coalGrowth++;
					isBanked = false;
				}
				else if (firepitBurnTime >= max && !isStoked) {
					//Utils.getLogger().info("Below 40 & 70% and not Stoked");
				pitState=7; // set BlockFirepit State To lit_firepit4
				coalRate = (coalBase * 1.30); // Decrease rate of coal production by 30%
				ashRate = (ashBase * .85); // Increase rate of ash control by 15%
				ashGrowth++;
				coalGrowth++;
				isBanked = false;
				lastState = 7;
				} 
				else {
					//Utils.getLogger().info("Oh Shit Son, What?");
				}
			if (((ashGrowth >= ashRate) && Burning) && ashCount < 10) {
				ashCount++; // Increase amount of ash that will be returned.
				ashGrowth = 0; // Reset ashGrowth
			}
			if (((coalGrowth >= coalRate) && Burning) && coalCount < 10) {
				coalCount++; // Increase amount of coal that will be returned.
				coalGrowth = 0; // Reset coalGrowth
			}

		}
		//Utils.getLogger().info("Burning: " + Burning + " fuelpitBurnTime: " + firepitBurnTime + " ashCount: " + ashCount + " coalCount: "
		//		+ coalCount );
		//Utils.getLogger().info("State: " + pitState + " isStoked: " + isStoked + " isBanked: " + isBanked);
		this.setBurning();
		stokedCheck();
		markDirty();
	}

	public void stokedCheck() {
		if (isStoked == true && stokedTimer == 0 || isStoked == true && Burning == false) {
			isStoked = false;
			stokedTimer = 0;
		}
	}

	public void rightClick(ItemStack heldItem, EntityPlayer player) {
		Item item = heldItem.getItem();
		String itemName = item.getUnlocalizedName();
		
		if (heldItem.isEmpty()) {
			if (!Burning && (coalCount != 0 || ashCount != 0)) {
				player.inventory.addItemStackToInventory(new ItemStack(Items.COAL, coalCount, 1));
				player.inventory.addItemStackToInventory(new ItemStack(ItemInit.ATD_WOOD_ASH, ashCount));
				coalCount = 0;
				ashCount = 0;
				pitState = getUnlit(firepitBurnTime);
			} else if (!Burning && firepitBurnTime != 0) {
				player.inventory.addItemStackToInventory(
						new ItemStack(Blocks.PLANKS, MathHelper.floor(firepitBurnTime / 300), 2));
				firepitBurnTime = 0;
				pitState = getUnlit(firepitBurnTime);				
			}
		}
			switch (itemName) {
			case "item.atd_tinder_bundle":
				if (Burning && coalCount>0) {
					player.inventory.addItemStackToInventory(new ItemStack(ItemInit.ATD_EMBER_BUNDLE));
					coalCount--;
					heldItem.shrink(1);
				}
				else
				{
				}
				break;
			case "item.atd_ember_bundle":
				if (!Burning && firepitBurnTime > 0) {
					Burning = true;
					heldItem.shrink(1);
				}
			case "item.flintAndSteel":
				if (firepitBurnTime > 0 && !Burning) {
					Burning = true;
					heldItem.damageItem(1, player);
				} else {
				}
				break;
			case "item.atd_torch":
				if (firepitBurnTime > 0 && !Burning) {
					Burning = true;
				} else {
				}
				break;
			case "item.atd_poker_iron":
				if (pitState==6 || pitState==7) {
					isStoked = true;
					stokedTimer = 1000;
					heldItem.damageItem(1, player);
				} else if (isStoked) {
					stokedTimer = 0;
					isStoked = false;					
				}
				break;
			case "item.atd_ash_wood":
				if (pitState==4 || pitState==5) {
						isBanked = true;
						ashCount++;
						heldItem.shrink(1);	
				}
				break;
			case "item.bucketWater":
				if (Burning) {
					this.Burning = false;
					this.pitState = getExtinguished(pitState);
					isStoked = false;
					stokedTimer = 0;
					isBanked = false;
					heldItem.shrink(1);
					player.inventory.addItemStackToInventory(new ItemStack(Items.BUCKET, 1));
				}
				break;
			case "tile.dirt":
				if (firepitBurnTime <= 1500 && Burning) {
					Burning = false;
					pitState = getExtinguished(pitState);
					coalBurn = 0;
					coalRate = coalBase;
					ashBurn = 0;
					ashRate = ashBase;
					heldItem.shrink(1);
				} else {
					firepitBurnTime = firepitBurnTime - 1500;
					heldItem.shrink(1);
				}
				break;
			default:
				if (firepitBurnTime <= firepitMaxBurn) {
					if (Block.getBlockFromItem(item).getDefaultState().getMaterial() == Material.WOOD) {
						firepitBurnTime += 1000;
						coalBurn += 1000; // How long will produce coal
						coalBase = 2000; // How often will produce coal
						ashBurn += 1000; // How long will produce ash
						ashBase = 1000; // How often will produce ash
						ashRate = ashBase;
						coalRate = coalBase;
						this.setBurning();
						heldItem.shrink(1);
						if (!Burning) {
							pitState = getUnlit(firepitBurnTime);
						}
						markDirty();
					}
					} else {
					}
					break;
		}

	}
		
		

	public int getUnlit(int fuelLevel) { // returns state based on fuelLevel
		if (!Burning) {
			if ((coalCount == 0 && ashCount == 0) && fuelLevel > 0) {
				if (firepitBurnTime >= 0 && firepitBurnTime < MathHelper.floor(firepitMaxBurn * 0.10)) {
					return 1; // unlit_firepit1
				}
				if (firepitBurnTime >= MathHelper.floor(firepitMaxBurn * 0.10)
						&& firepitBurnTime < MathHelper.floor(firepitMaxBurn * 0.70)) {
					return 2; // unlit_firepit2
				}
				if (firepitBurnTime >= MathHelper.floor(firepitMaxBurn * 0.70)) {
					return 3;
				} // unlit_firepit3
			} else if ((coalCount > 0 || ashCount > 0) && fuelLevel > 0) {
				if ((firepitBurnTime >= 0) && firepitBurnTime < MathHelper.floor(firepitMaxBurn * 0.10)) {
					return 9; // extinguished_firepit1
				}
				if (firepitBurnTime < MathHelper.floor(firepitMaxBurn * 0.10)
						&& firepitBurnTime < MathHelper.floor(firepitMaxBurn * 0.70)) {
					return 10; // extinguished_firepit2
				}
				if (firepitBurnTime >= MathHelper.floor(firepitMaxBurn * 0.70)
						&& firepitBurnTime < MathHelper.floor(firepitMaxBurn)) {
					return 11;
				} // extinguished_firepit3
			} else {
				if ((coalCount > 0 || ashCount > 0) && fuelLevel == 0) {
					return 8; // dirty_firepit
				}
				if ((coalCount == 0 && ashCount == 0) && fuelLevel == 0) {
					return 0; // empty_firepit
				}
			}
		} else {
			if (fuelLevel == 0) {
				return 0; // vme:empty_firepit
			}
			if (firepitBurnTime > 0 && firepitBurnTime < MathHelper.floor(firepitMaxBurn * 0.10)) {
				return 4; // lit_firepit1
			}
			if ((firepitBurnTime < MathHelper.floor(firepitMaxBurn * 0.10)
					&& firepitBurnTime < MathHelper.floor(firepitMaxBurn * 0.40))) {
				return 5; // lit_firepit2
			}
			if (firepitBurnTime < MathHelper.floor(firepitMaxBurn * 0.40)
					&& firepitBurnTime < MathHelper.floor(firepitMaxBurn * 0.70)) {
				return 6; // lit_firepit3
			}
			if (firepitBurnTime > MathHelper.floor(firepitMaxBurn * 0.70)) {
				return 7; // lit_firepit4
			}
		}
		//Utils.getLogger().info("Failed");
		return 11;
	}

	public int getExtinguished(int pitState) {
		switch (pitState) {
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

	public void cleanPit(InventoryPlayer inventory) {

	}

}