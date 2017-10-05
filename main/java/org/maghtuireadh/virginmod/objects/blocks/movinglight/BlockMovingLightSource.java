package org.maghtuireadh.virginmod.objects.blocks.movinglight;

import org.maghtuireadh.virginmod.init.BlockInit;
import org.maghtuireadh.virginmod.tileentity.TEMovingLightSource;
import org.maghtuireadh.virginmod.util.Reference;

import net.minecraft.block.BlockAir;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockMovingLightSource extends BlockAir implements ITileEntityProvider {


	private EntityPlayer player;

	public BlockMovingLightSource(String name) {
		super();
		this.setUnlocalizedName(name);
		this.setRegistryName(new ResourceLocation(Reference.MODID, name));
		BlockInit.BLOCKS.add(this);
		this.setDefaultState(getDefaultState());
		setLightLevel(0.9F);
	}
	
	@Override
	public boolean isReplaceable(IBlockAccess world, BlockPos pos) {
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TEMovingLightSource().setPlayer(player);
	}

	public BlockMovingLightSource setPlayer(EntityPlayer player) {
		this.player = player;
		return this;
	}

}
