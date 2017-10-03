package org.maghtuireadh.virginmod.objects.blocks.movinglight;

import org.maghtuireadh.virginmod.objects.blocks.movinglight.te.TEMovingLightSource;
import org.maghtuireadh.virginmod.util.Reference;

import net.minecraft.block.BlockAir;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockMovingLightSource extends BlockAir implements ITileEntityProvider {

	public static final String NAME = "moving_light_source";

	private EntityPlayer player;

	public BlockMovingLightSource(String name) {
		super();
		setUnlocalizedName(Reference.MODID + "." + name);
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
