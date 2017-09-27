package org.maghtuireadh.virginmod.objects.blocks.furnaces;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockFirepit extends Block implements ITickable{

private final boolean isBurning;
int firepitBurnTime;
int fuelLvl;
int burnRate;
boolean isStoked;
int coalBurn;
int coalCount;
int coalGrowth;
int coalRate;
int ashBurn;
int ashCount;
int ashGrowth;
int ashRate;

	protected BlockFirepit(boolean isBurning){
		    super(Material.ROCK);
		    this.isBurning = isBurning;
	}
		
	public int quantityDropped(Random random){
			 return 6;
	}
			
	public Item getItemDropped(IBlockState state, Random rand, int fortune){
					return Item.getItemFromBlock(Blocks.STONE_SLAB);
	}

	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (worldIn.isRemote)
        {
            return true;
        }
        else
        {
             // Need to figure out how to get ItemStack in player's hand, and reduce ItemStack by one.
        }
    }
	public void update(){
			
		if (this.isBurning){
				--this.firepitBurnTime;
		}
		//How do I get World in this instance?
		if (!this.worldIn.isRemote){
			if (!this.isBurning && coalBurn > 0){
				--coalBurn;
				++coalGrowth;
				if (coalGrowth == coalRate){
					++coalCount;
					this.coalGrowth = 0;
				}
			}
				
			if (!this.isBurning && ashBurn > 0){
					--ashBurn;
					++ashGrowth;
					
				if (ashGrowth == ashRate){
						++ashCount;
						this.ashGrowth = 0;
				}
			}
				
			if (!this.isBurning && firepitBurnTime == 0){
					this.isBurning = false;
					this.coalRate = 0;
					this.ashRate = 0;
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
    @SuppressWarnings("incomplete-switch")
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
        if (this.isBurning)
        {
            double d0 = (double)pos.getX() + 0.5D;
            double d1 = (double)pos.getY() + rand.nextDouble() * 6.0D / 16.0D;
            double d2 = (double)pos.getZ() + 0.5D;
            double d3 = 0.52D;
            double d4 = rand.nextDouble() * 0.6D - 0.3D;

            if (rand.nextDouble() < 0.1D)
            {
                worldIn.playSound((double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
            }

            worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 + 0.52D, 0.0D, 0.0D, 0.0D);
            worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d4, d1, d2 + 0.52D, 0.0D, 0.0D, 0.0D);
            }
        }
    }	
	
	
}				