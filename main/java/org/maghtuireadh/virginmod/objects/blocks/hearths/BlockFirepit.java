package org.maghtuireadh.virginmod.objects.blocks.hearths;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import org.maghtuireadh.virginmod.Main;
import org.maghtuireadh.virginmod.config.VMEConfig;
import org.maghtuireadh.virginmod.init.BlockInit;
import org.maghtuireadh.virginmod.init.ItemInit;
import org.maghtuireadh.virginmod.objects.items.ATDEmberBundle;
import org.maghtuireadh.virginmod.objects.tools.AtdTorch;
import org.maghtuireadh.virginmod.tileentity.TileEntityFirepit;
import org.maghtuireadh.virginmod.util.Reference;
import org.maghtuireadh.virginmod.util.Utils;
import org.maghtuireadh.virginmod.util.interfaces.IFireStarter;
import org.maghtuireadh.virginmod.util.interfaces.IHasModel;
import org.maghtuireadh.virginmod.util.interfaces.IIgnitable;
import org.maghtuireadh.virginmod.util.handlers.ListHandler;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;



public class BlockFirepit extends BlockHearth
{
	protected static final AxisAlignedBB FIREPIT_AABB = new AxisAlignedBB(1.5D, 0.0D, 1.5D, -0.5D, .4D, -0.5D);
	public static final PropertyInteger PITSTATE = PropertyInteger.create("pitstate", 0, 15);
	public static IBlockState[] states = new IBlockState[16];
	
	public BlockFirepit(String unlocalizedName, Material material) 
	{
		super(unlocalizedName, material);
		this.setHardness(1.5f);
		this.setResistance(5.0f);
		this.setSoundType(SoundType.STONE);
		for (int i = 0; i < 15; i++) 
		{
	        states[i] =  this.blockState.getBaseState().withProperty(PITSTATE, i);
	    }
		
	}
	
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
		Utils.getLogger().info("Fire Pit: onBlockActivated()");
		TileEntityFirepit tileentity = (TileEntityFirepit) world.getTileEntity(pos);
		ItemStack heldItemStack = player.getHeldItemMainhand();
		Item heldItem = player.getHeldItemMainhand().getItem();
		String heldItemName = heldItem.getRegistryName() + "-" + heldItemStack.getMetadata();	
		/*Utils.getLogger().info("heldItemName: " + heldItemName + " ResourceLocation: " + heldItem.getRegistryName() + " Meta: " + heldItem.getMetadata(heldItemStack));
		Utils.getLogger().info("Length of FuelList: " + ListHandler.FuelList.size());
		Utils.getLogger().info("Fuel Object List String: " +  VMEConfig.fuelObjectListString);
		
		for(int i = 0;i<=(ListHandler.FuelList.size()-1);i++){
			Utils.getLogger().info("Values of FuelList: " + i + " " + ListHandler.FuelList.get(i));	
		}
		for(int i = 0;i<=(ListHandler.BurnTimeList.size()-1);i++) {
			Utils.getLogger().info("Values of BurnTimeList: " + i + " " + ListHandler.BurnTimeList.get(i));
		}
		*/
			if(ListHandler.HearthFireStarterList.contains(heldItemName))
	        {
	        	return false;
	        }
	        else if (ListHandler.HearthFuelList.contains(heldItemName)) 
	        {
	        	if(tileentity.getTEFuelMax())
	        	{
	        		Utils.getLogger().info("On Fuel List, Fuel Not Full");
	        		long fuel = ListHandler.BurnTimeList.get(ListHandler.FuelList.indexOf(heldItemName));
	        		setFuel(heldItemStack, fuel, world, pos); //Fuel Long is set by setFuel before sending to setTEFuel
	        		heldItemStack.shrink(1);
		           	return true;
	        	}
		        else
		        {
	        		Utils.getLogger().info("On Fuel List, Fuel Full");
	        		player.sendMessage(new TextComponentString("This fire pit can hold no more fuel."));
		        	return false;
		        }
	        }  
	        else if (ListHandler.ExtinguishList.contains(heldItemName))
	        {
	    		Utils.getLogger().info("On Extinguish List");
	           	tileentity.setExtinguishedState(true);
	           	return true;
	        }
	        else if (ListHandler.BankerList.contains(heldItemName))
	        {
	        	player.sendMessage(new TextComponentString("You bank the coals."));
	    		Utils.getLogger().info("On Banker List, Fuel Full");
	        	return true;
	        }
	        else if (ListHandler.PokerList.contains(heldItemName))
	        {
	        	player.sendMessage(new TextComponentString("You stoke the fire."));
	        	Utils.getLogger().info("On Poker List");
	        	return true;
	        }
	        else if (player.getHeldItemMainhand().getUnlocalizedName() == "item.atd_tinder_bundle") {
	        	Utils.getLogger().info("Fire Pit: onBlockActivated() It's a tinder bundle");
	        	if(tileentity.getCoalCount()>0 && tileentity.getBurning() == true) {
	        		player.getHeldItemMainhand().shrink(1);
	            	player.inventory.addItemStackToInventory(new ItemStack(ItemInit.ATD_EMBER_BUNDLE, 1));
	            	tileentity.setCoalCount(1);
	            	return true;
	        	}
	        	else
	        	{
	        		player.sendMessage(new TextComponentString("This firepit contains no Burning coals."));
	        		return false;
	        	}
	        }
	        else if (player.getHeldItemMainhand().isEmpty())
	        {
	
	    		Utils.getLogger().info("Cleaning The Pit");
	    		Utils.getLogger().info("Burning?: " + tileentity.getBurning());
	        	tileentity.cleanPit(player);
	        	return true;
	        }
	        else
	        {
	        	Utils.getLogger().info("Not On List");
	    		Utils.getLogger().info("Burning?: " + tileentity.getBurning());
	        	return false;
	        }
    }

	
	/* =========================================================================================================
	 * Interface Methods
	 * =========================================================================================================
	 */

	@Override
	public boolean Burning(World world, BlockPos pos, EntityPlayer player) 
	{
		Utils.getLogger().info("Fire Pit: Burning()");
		TileEntityFirepit tileentity = (TileEntityFirepit) world.getTileEntity(pos);
		return tileentity.getBurning();
	}


	@Override
	public boolean extinguish(World world, BlockPos pos, EntityPlayer player) 
	{
		Utils.getLogger().info("Fire Pit: Extinguish()");
		TileEntityFirepit tileentity = (TileEntityFirepit) world.getTileEntity(pos);
		ItemStack heldItem=player.getHeldItemMainhand();
		tileentity.setExtinguishedState(true);
		return false;
		
	}

	@Override
	public void setFuel(ItemStack stack, long fuel, World world, BlockPos pos) 
	{
		Utils.getLogger().info("Fire Pit: setFuel()");
		TileEntityFirepit tileentity = (TileEntityFirepit) world.getTileEntity(pos);
			String fuelItemName = stack.getItem().getRegistryName() + "-" + stack.getMetadata();
	        int fuelIndex = ListHandler.FuelList.indexOf(fuelItemName);
			tileentity.setTEFuel(fuel, fuelIndex);
		Utils.getLogger().info("Fire Pit: Burning() fuelItemName: " + fuelItemName + " Fuel Amount: " + fuel);
	}
	
	public boolean attemptIgnite(int igniteChance, World world, BlockPos pos, EntityPlayer player) 
	{
			Utils.getLogger().info("Fire Pit: igniteChance()");
			TileEntityFirepit tileentity = (TileEntityFirepit) world.getTileEntity(pos);
	        ItemStack heldItem = player.getHeldItemMainhand();
	        Utils.getLogger().info("Fire Pit: igniteChance() igniteChance: " + igniteChance + " BlockPos " + pos + " tileentity: " + tileentity);
	        return tileentity.attemptIgnite(igniteChance, pos);

	}
	
	/*============================================================================
	 *                         Getters & Setters
	 *============================================================================*/

	public boolean getWeather(World world, BlockPos pos) 
	{
		Utils.getLogger().info("Fire Pit: getWeather() pos");
		if(world.isRainingAt(pos.up()) && world.canBlockSeeSky(pos))
		{
			return true;
		}
		else 
		{
			return false;	
		}
	}	

	public int getState(World worldIn, BlockPos pos) 
	{
		IBlockState state = worldIn.getBlockState(pos);
		Utils.getLogger().info("Fire pit: getState(): " + getMetaFromState(state));
		return getMetaFromState(state);
	}
	
	public void setState(int meta, World worldIn, BlockPos pos) 
	{
		IBlockState state = worldIn.getBlockState(pos);
		TileEntity tileentity = worldIn.getTileEntity(pos);
		worldIn.setBlockState(pos, BlockInit.BLOCK_FIREPIT.getDefaultState().withProperty(PITSTATE, meta));
		Utils.getLogger().info("Fire Pit: setState()");
		if(tileentity != null) {
			tileentity.validate();
			worldIn.setTileEntity(pos, tileentity);
		}
	}
	
	@Override
	public int getLightValue(IBlockState state) 
	{
		Utils.getLogger().info("Fire Pit: getLightValue() state" + state);
			switch (getMetaFromState(state)) {
			case 0: return 0; //empty_firepit
					
			case 1: return 0; //unlit_firepit1
				
			case 2: return 0; //unlit_firepit2
					
			case 3: return 0; //unlit_firepit3
				
			case 4: return 4; //lit_firepit1
				
			case 5: return 8; //lit_firepit2
				
			case 6: return 10; //lit_firepit3
			
			case 7: return 12; //lit_firepit4
				
			case 8: return 0; //dirty_firepit
	
			case 9: return 0; //extinguished_firepit1
			
			case 10: return 0; //extinguished_firepit2
				
			case 11: return 0; //extinguished_firepit3
			
			case 12: return 12; //lit_firepit3
			
			case 13: return 14; //lit_firepit4
			
			case 14: return 6; //unlit_firepit2
			
			case 15: return 6; //unlit_firepit3
				
			default: return 0;}
	}
	
	/*============================================================================
	 *                         Initialization Elements
	  ============================================================================*/	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) 
	{
		worldIn.setBlockState(pos, state.withProperty(PITSTATE, 0));
	}
	
	public int quantityDropped(Random random)
	{
		return 6;
	}			
	
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return Item.getItemFromBlock(Blocks.STONE_SLAB);
	}
	
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) 
	{
	this.blockState.getBaseState().withProperty(PITSTATE, Integer.valueOf(0));
	}
	
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) 
	{
	super.breakBlock(worldIn, pos, state);
	}
	
	public EnumBlockRenderType getRenderType(IBlockState state) 
	{
	return EnumBlockRenderType.MODEL;
	}
	
	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) 
	{
		Utils.getLogger().info("Fire pit: getStateForPlacement)");
		return this.getDefaultState().withProperty(PITSTATE, 0);
	}
	
	@Override
	/**
	 * Returns the correct meta for the block
	 * I recommend also saving the EnumFacing to the meta but I haven't
	 */
	public int getMetaFromState(IBlockState state) 
	{

		Utils.getLogger().info("Fire pit: getMetaFromState(): " + state);
		int meta = state.getValue(PITSTATE);
		Utils.getLogger().info("Fire pit: getMetaFromState(): " + meta);
		return meta;
	}
	
	
	@Override
	/**
	 * Gets the block state from the meta
	 */
	public IBlockState getStateFromMeta(int meta) 
	{
		Utils.getLogger().info("Fire pit: getStateFromMeta():" + meta);
		return BlockFirepit.states[meta];
	}
	/**
	 * Makes sure that when you pick block you get the right version of the block
	 */
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) 
	{
		return new ItemStack(Item.getItemFromBlock(this), 1, (int) (getMetaFromState(world.getBlockState(pos))));
	}
	
	/**
	 * Makes the block drop the right version of the block from meta data
	 */
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) 
	{
		Utils.getLogger().info("Fire pit: createNewTileEntity");
		return new TileEntityFirepit();
	}

	@Override
	public int damageDropped(IBlockState state) 
	{
		return (int) (getMetaFromState(state));
	}
	
	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) 
	{
			items.add(new ItemStack(this, 1, 0));
	}
	
	@Override
	public void registerModels() 
	{
		for (int i=0;i<states.length-1;i++)
		{
		Main.proxy.registerVariantRenderer(Item.getItemFromBlock(this), i, "block_firepit", "states="+i);
		}
	}
	
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.CUTOUT;
	}
	
	@Override
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) 
	{
		return true;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) 
	{
		return false;
	}
	
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}
	
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return FIREPIT_AABB;
    }	
	
    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
    {
        return FIREPIT_AABB;
    }
	
	@Override
	protected BlockStateContainer createBlockState() 
	{
		return new BlockStateContainer(this, new IProperty[] {PITSTATE});
	}
}
