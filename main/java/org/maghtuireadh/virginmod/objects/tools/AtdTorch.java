package org.maghtuireadh.virginmod.objects.tools;

import org.maghtuireadh.virginmod.Main;
import org.maghtuireadh.virginmod.init.BlockInit;
import org.maghtuireadh.virginmod.init.ItemInit;
import org.maghtuireadh.virginmod.objects.blocks.movinglight.te.TEMovingLightSource;
import org.maghtuireadh.virginmod.util.Utils;
import org.maghtuireadh.virginmod.util.interfaces.IHasModel;

import net.minecraft.block.BlockTorch;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class AtdTorch extends ItemSword implements IHasModel, ITileEntityProvider {

	BlockPos BP;
	
	public AtdTorch(String name, ToolMaterial material) {
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(CreativeTabs.MATERIALS);
	
		ItemInit.ITEMS.add(this);
	}
	
	@Override
	public void registerModels()
	{
		Main.proxy.registerItemRenderer(this, 0, "inventory");
	}
	
	@Override

	public TileEntity createNewTileEntity(World worldIn, int meta) {

		return new TEMovingLightSource();

	}
	
	@Override
	  public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
		IBlockState BS_up = BlockInit.ATD_TORCH.getDefaultState().withProperty(BlockTorch.FACING, EnumFacing.UP);
		IBlockState BS_north = BlockInit.ATD_TORCH.getDefaultState().withProperty(BlockTorch.FACING, EnumFacing.NORTH);
		IBlockState BS_south = BlockInit.ATD_TORCH.getDefaultState().withProperty(BlockTorch.FACING, EnumFacing.SOUTH);
		IBlockState BS_east = BlockInit.ATD_TORCH.getDefaultState().withProperty(BlockTorch.FACING, EnumFacing.EAST);
		IBlockState BS_west = BlockInit.ATD_TORCH.getDefaultState().withProperty(BlockTorch.FACING, EnumFacing.WEST);
		int thisX = pos.getX();
		int thisX1 = pos.getX() + 1;
		int thisX2 = pos.getX() - 1;
		int thisY = pos.getY();
		int thisY1 = pos.getY() + 1;
		int thisZ = pos.getZ();
		int thisZ1 = pos.getZ() + 1;
		int thisZ2 = pos.getZ() - 1;
		RayTraceResult RT = this.rayTrace(worldIn, player, false);
		if (RT!=null) {	
			if(RT.sideHit == EnumFacing.UP) {
				BP =  new BlockPos(thisX,thisY1,thisZ);
				worldIn.setBlockState(BP, BS_up);
			}
			else if(RT.sideHit == EnumFacing.NORTH) {
				BP =  new BlockPos(thisX,thisY,thisZ2);
				worldIn.setBlockState(BP, BS_north);
			}
			else if(RT.sideHit == EnumFacing.SOUTH) {
				BP =  new BlockPos(thisX,thisY,thisZ1);
				worldIn.setBlockState(BP, BS_south);
			}
			else if(RT.sideHit == EnumFacing.EAST) {
				BP =  new BlockPos(thisX1,thisY,thisZ);
				worldIn.setBlockState(BP, BS_east);
			}
			else if(RT.sideHit == EnumFacing.WEST) {
				BP =  new BlockPos(thisX2,thisY,thisZ);
				worldIn.setBlockState(BP, BS_west);
			}
			else {
			}
			Utils.getLogger().info("Thats null bruh");	
		}
		
        return EnumActionResult.PASS;
    }
	
	
}
