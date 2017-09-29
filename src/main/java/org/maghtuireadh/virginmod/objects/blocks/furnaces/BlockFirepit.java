package org.maghtuireadh.virginmod.objects.blocks.furnaces;

import java.util.Random;

import org.maghtuireadh.virginmod.Main;
import org.maghtuireadh.virginmod.init.BlockInit;
import org.maghtuireadh.virginmod.init.ItemInit;
import org.maghtuireadh.virginmod.tileentity.TileEntityFirepit;
import org.maghtuireadh.virginmod.util.Reference;
import org.maghtuireadh.virginmod.util.Utils;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockFirepit extends Block implements ITileEntityProvider{
	
	Float lightLvl;
	boolean isBurning;

	public BlockFirepit(String unlocalizedName) {
	super(Material.ROCK);
	this.setUnlocalizedName("block_firepit");
	this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
	setCreativeTab(Main.virginmodtab);
	BlockInit.BLOCKS.add(this);
	ItemInit.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}

 @Override
	 public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
	 TileEntityFirepit tileentity = (TileEntityFirepit) worldIn.getTileEntity(pos);	 
	 
		 if(tileentity.getBurning()) {
		     	if(this.isBurning && lightLvl!=tileentity.getLight())
		     	{
		     	     
		        	 this.setLightLevel(lightLvl);
		        	 Utils.getLogger().info(lightLvl);	 
		        }
			 }
		     else
		     {
		     		this.setLightLevel(0.0F);
		     		Utils.getLogger().info("Set Light To 0");
		     }
		 	Utils.getLogger().info("Why isn't this doing anything?");
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
            TileEntityFirepit tileentity = (TileEntityFirepit) worldIn.getTileEntity(pos);
           	ItemStack heldItem = playerIn.getHeldItemMainhand();
           	tileentity.setFuelValues(heldItem);
           	return true;    
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

	public TileEntity createNewTileEntity(World worldIn, IBlockState state) {
		return new TileEntityFirepit();
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityFirepit();
	}

}	