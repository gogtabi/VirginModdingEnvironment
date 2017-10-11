package org.maghtuireadh.virginmod.tileentity;


import java.util.Random;

import org.maghtuireadh.virginmod.objects.blocks.furnaces.BlockFirepit;
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

public class TileEntityFirepit extends TileEntity implements ITickable {
	private boolean Burning, isStoked;
	private int firepitBurnTime = 0;
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
	public void TileEntityFirpit() {
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.cooldown = nbt.getInteger("Cooldown");
		this.firepitBurnTime = nbt.getInteger("FPBT");
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

	public void setBurning() {
        if (this.world != null && !this.world.isRemote) { 
	            this.blockType = this.getBlockType();
	
	            if (this.blockType instanceof BlockFirepit) {
	                if(((BlockFirepit) this.blockType).getBurning()!=Burning){ //Only update BlockFirepit Burning bool on a change
	                		((BlockFirepit) this.blockType).setBurning(Burning);
	                }
	                if(((BlockFirepit) this.blockType).getState(world, pos)!=pitState){ //Only update BlockFirepit State On A Change
		                ((BlockFirepit) this.blockType).setState(pitState, world, pos);
		            }
	         markDirty();
	            } 
        }
	}
	
	public void update(){
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
					pitState=4;	//set BlockFirepit State To lit_firepit1
					coalRate = (coalBase*.85); //Increase rate of coal production by 15%
					ashRate = (ashBase*1.3); //Decrease rate of ash production by 30%
					ashGrowth++;
					coalGrowth++;
				}
				if(firepitBurnTime>=201 && firepitBurnTime<=600) {
					pitState=5; //set BlockFirepit State To lit_firepit2
					coalRate = coalBase; 
					ashRate = (ashBase*1.15); //Decrease rate of ash production by 15%
					ashGrowth++;
					coalGrowth++;
				}
				if(firepitBurnTime>=601 && firepitBurnTime<=900) {
					pitState=6; //set BlockFirepit State To lit_firepit3
					coalRate = (coalBase*1.15); //Decrease rate of coal production by 15%
					ashRate = ashBase; 
					ashGrowth++;
					coalGrowth++;
				}
				if(firepitBurnTime>=901){
					pitState=7; //set BlockFirepit State To lit_firepit4
					coalRate = (coalBase*1.30); //Decrease rate of coal production by 30%
					ashRate = (ashBase*.85); //Increase rate of ash control by 15%
					ashGrowth++;
					coalGrowth++;
				}	
			
		}
		if((ashGrowth>=ashRate) && Burning) {
			ashCount++; //Increase amount of ash that will be returned.
			ashGrowth=0; //Reset ashGrowth
		}
		if((coalGrowth>=coalRate) && Burning) {
			coalCount++; //Increase amount of coal that will be returned.
			coalGrowth=0; //Reset coalGrowth
		}
		markDirty();
		this.setBurning();
	}


public void rightClick(ItemStack heldItem, InventoryPlayer inventory) {
			
	if (heldItem.isEmpty()) {
		if(!Burning && (coalCount!=0 || ashCount!=0))
		{
			inventory.addItemStackToInventory(new ItemStack(Items.COAL, coalCount, 1));
//			inventory.addItemStackToInventory();
			coalCount=0;
			ashCount=0;
			pitState = getUnlit(firepitBurnTime);
		}
		else if(!Burning && firepitBurnTime!=0)
		{
			inventory.addItemStackToInventory(new ItemStack(Blocks.PLANKS, MathHelper.floor(firepitBurnTime/300),2));
			firepitBurnTime=0;
			pitState = getUnlit(firepitBurnTime);
		}
	}
	else {
		Item item = heldItem.getItem(); 
		int itemName = Item.getIdFromItem(item);
		switch (itemName) {
		case 259:
			if (firepitBurnTime>0 && !Burning)
			{
				Burning=true;
				
			}			
			break;
		case 4108:
			if (firepitBurnTime>0 && !Burning)
			{
				Burning=true;
			
			}			
			break;
		case 326:
			if (Burning) {
				Burning=false;
				pitState = getExtinguished(pitState);
				coalBurn = ((int)((float)coalBurn*0.5));
				coalRate = coalBase*.75;
				ashBurn = ((int)((float)ashBurn*0.5));
				ashRate = ashBase*1.25;
			}
			break;
		case 3:
			if (firepitBurnTime<=150 && Burning) {
				Burning = false;
				pitState = getExtinguished(pitState);
				coalBurn = 0;
				coalRate = coalBase;
				ashBurn = 0;
				ashRate = ashBase;
				heldItem.shrink(1);
			break;
			}
			else if (firepitBurnTime>=151 && Burning){
				firepitBurnTime=(firepitBurnTime-150);
				heldItem.shrink(1);
			}
		default:
			if (Block.getBlockFromItem(item).getDefaultState().getMaterial() == Material.WOOD) {
				firepitBurnTime += 300;
				coalBurn += 200; //How long will produce coal
				coalBase = 400; //How often will produce coal
				coalBase = 400;
				ashBurn += 200; //How long will produce ash
				ashBase = 400;	//How often will produce ash
				ashRate = ashBase;
				coalRate = coalBase;
				pitState = getUnlit(firepitBurnTime);
				this.setBurning();
				heldItem.shrink(1);
				markDirty();}
			break;}
		}
	}
		
	public int getUnlit(int fuelLevel) { //returns state based on fuelLevel
		if(!Burning) {
			if((coalCount==0 && ashCount==0) && fuelLevel>0) {
				if(fuelLevel>0 && fuelLevel <=300) {
					return 1; // ulit_firepit1
				}
				if(fuelLevel>=301 && fuelLevel <= 600) {
					return 2; // ulit_firepit2
				}
				if(fuelLevel>=610) {
					return 3;} // unlit_firepit3
			}
			else if((coalCount>0 || ashCount>0) && fuelLevel>0)
			{
				if(fuelLevel>0 && fuelLevel <=300) {
					return 9; // extinguished_firepit1
				}
				if(fuelLevel>=301 && fuelLevel <= 600) {
					return 10; // extinguished_firepit1
				}
				if(fuelLevel>=610) {
					return 11;} // extinguished_firepit1
			}
			else
			{
				if((coalCount>0 || ashCount>0) && fuelLevel==0) {
					return 8; // dirty_firepit
				}
				if((coalCount==0 && ashCount==0) && fuelLevel==0) {
					return 0; // empty_firepit
				}
			}
		}
		else {
			if(fuelLevel==0) {
				return 0; // vme:empty_firepit
			}
			if(fuelLevel>0 && fuelLevel <=300) {
				return 4; // lit_firepit1
			}
			if(fuelLevel>=301 && fuelLevel <=600) {
				return 5; // lit_firepit2
			}
			if(fuelLevel>=610 && fuelLevel <=900) {
				return 6; // lit_firepit3
			}
			if(fuelLevel>=901) {
				return 7; // lit_firepit4
			}
		}
		return 11;
	}
	public int getExtinguished(int pitState){
		switch (pitState)
		{
			case 4: // lit_firepit1
			return 8; // dirty_firepit
			
			case 5: // lit_firepit2
			return 9;	// extinguished_Firepit1
			
			case 6: // lit_firepit3
			return 10;  // extinguished_Firepit2
			 
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