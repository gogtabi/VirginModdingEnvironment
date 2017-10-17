package org.maghtuireadh.virginmod.objects.blocks.torches;

import java.util.Random;

import org.maghtuireadh.virginmod.Main;
import org.maghtuireadh.virginmod.init.BlockInit;
import org.maghtuireadh.virginmod.init.ItemInit;
import org.maghtuireadh.virginmod.objects.blocks.hearths.BlockFirepit;
import org.maghtuireadh.virginmod.tileentity.TileEntityFirepit;
import org.maghtuireadh.virginmod.util.Utils;
import org.maghtuireadh.virginmod.util.interfaces.IHasModel;

import net.minecraft.block.BlockTorch;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockATDTorch extends BlockTorch implements IHasModel,ITileEntityProvider {
	

	public Long burnTime = (long) 0;
	public Long setTime = (long) 0;
	public static Item[] FireStarters = new Item[] {Items.FLINT_AND_STEEL,ItemInit.ATD_EMBER_BUNDLE,ItemInit.ATD_TORCH};
	public static PropertyBool LIT = PropertyBool.create("lit");
	
	
	/**
	 * Default constructor which sets the hardness and resistance
	 * @param unlocalizedName The unlocalized name
	 */
	public BlockATDTorch(String name) 
	{
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setLightLevel(1.0F);
		this.setTickRandomly(true);
		Blocks.FIRE.setFireInfo(this, 60, 20);
		//setCreativeTab(Main.virginmodtab);
		BlockInit.BLOCKS.add(this);
		//ItemInit.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}
	
	
	public IBlockState setTime(IBlockState blockstate, int time, World world)
	{
		this.burnTime = (long)time;
		this.setTime = world.getTotalWorldTime();
		return blockstate;
	}
	
	@Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {FACING,LIT});
    }
	
	@Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
		for(int i=0 ; i < FireStarters.length;i++)
		{
			 if (playerIn.getActiveItemStack().getItem() == FireStarters[i] && state != this.getBlockState().getBaseState().withProperty(BlockATDTorch.LIT, true))
			 {
				BlockPos BP = new BlockPos(hitX,hitY,hitZ);
				IBlockState BS = worldIn.getBlockState(BP).withProperty(BlockATDTorch.LIT, true);
				worldIn.setBlockState(BP, BS);
			 }
					
		}
		return false;
    }
	
	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		
		if(world.getTotalWorldTime() - this.setTime > burnTime)
		{
		world.setBlockToAir(pos);
		}
		Utils.getLogger().info("Get World Time: " + world.getTotalWorldTime());
	}

	@Override
	public void registerModels() {
		for(int i = 0; i < 9; i++)
		Main.proxy.registerVariantRenderer(Item.getItemFromBlock(this), i, "Block_atd_Torch", "inventory");
		
	}

	@Override
	 public int quantityDropped(Random random)
    {
        return 0;
    }
	
	@Override
	public int getMetaFromState(IBlockState state) {
		//int meta = state.getValue(PITSTATE);
		return meta;
		}
	
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
			return //BlockFirepit.states[meta];
		}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {

		return new TileEntityFirepit();

	}
}
	
	

	

