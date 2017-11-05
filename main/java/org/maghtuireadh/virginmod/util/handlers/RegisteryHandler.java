package org.maghtuireadh.virginmod.util.handlers;

import org.maghtuireadh.virginmod.init.BlockInit;
import org.maghtuireadh.virginmod.init.ItemInit;
import org.maghtuireadh.virginmod.tileentity.TileEntityATDTorch;
import org.maghtuireadh.virginmod.util.Reference;
import org.maghtuireadh.virginmod.util.Utils;
import org.maghtuireadh.virginmod.util.interfaces.IHasModel;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@EventBusSubscriber
public class RegisteryHandler 
{
	
	@SubscribeEvent
	public static void onItemRegister(RegistryEvent.Register<Item> event)
	{
		event.getRegistry().registerAll(ItemInit.ITEMS.toArray(new Item[0]));
	}
	
	@SubscribeEvent
	public static void onBlockRegister(RegistryEvent.Register<Block> event)
	{
		event.getRegistry().registerAll(BlockInit.BLOCKS.toArray(new Block[0]));
		GameRegistry.registerTileEntity(TileEntityATDTorch.class, Reference.MODID+":atdtorch");
	}
	
	@SubscribeEvent
	public static void onModelRegister(ModelRegistryEvent event)
	{
		for(Item item : ItemInit.ITEMS)
		{
			if(item instanceof IHasModel)
			{
				((IHasModel)item).registerModels();
				Utils.getLogger().info("Registered Model for item: " + item.getUnlocalizedName());
			}
		}
		for(Block block : BlockInit.BLOCKS)
		{
			if(block instanceof IHasModel)
			{
				((IHasModel)block).registerModels();
				Utils.getLogger().info("Registered Model for block: " + block.getUnlocalizedName());
			}
		}
	}
}
