package org.maghtuireadh.virginmod.objects.blocks.hearths;

import java.util.ArrayList;
import java.util.List;

import org.maghtuireadh.virginmod.Main;
import org.maghtuireadh.virginmod.init.BlockInit;
import org.maghtuireadh.virginmod.init.ItemInit;
import org.maghtuireadh.virginmod.objects.blocks.item.itemFireStarter;
import org.maghtuireadh.virginmod.tileentity.TileEntityFirepit;
import org.maghtuireadh.virginmod.util.Reference;
import org.maghtuireadh.virginmod.util.interfaces.IFireStarter;
import org.maghtuireadh.virginmod.util.interfaces.IHasModel;
import org.maghtuireadh.virginmod.util.interfaces.IIgnitable;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


public class BlockHearth extends Block implements IIgnitable, IFireStarter, IHasModel, ITileEntityProvider 
{
	int lightCount;
	List<ItemStack> listFireStarter = new ArrayList<ItemStack>();
	public BlockHearth(String unlocalizedName, Material material) 
	{
		super(material);
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
		BlockInit.BLOCKS.add(this);
		ItemInit.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
		this.setCreativeTab(Main.virginmodtab);
		lightCount=100;

	}
	
/*	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ, int attemptCount)
    {
		ItemStack heldItem = player.getHeldItemMainhand();
		if (worldIn.isRemote)
        {
            return true;
        }
		else for (int i=0;i<listFireStarter.size()-1 && heldItem!=listFireStarter.get(i);i++)
		{
			itemFireStarter item = (itemFireStarter) heldItem.getItem();
			item.getIgniteCount();
        }
		return true;
    }*/
	
	@Override
	public void registerModels() 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void attemptIgnite(int igniteChance, World world, BlockPos pos, EntityPlayer player) {		
	}

}
