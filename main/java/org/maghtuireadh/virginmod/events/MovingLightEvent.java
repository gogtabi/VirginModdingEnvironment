package org.maghtuireadh.virginmod.events;


import org.maghtuireadh.virginmod.init.BlockInit;
import org.maghtuireadh.virginmod.init.ItemInit;
import org.maghtuireadh.virginmod.objects.blocks.movinglight.BlockMovingLightSource;
import org.maghtuireadh.virginmod.util.Utils;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class MovingLightEvent {
	
	@SubscribeEvent(priority = EventPriority.LOW, receiveCanceled = true)
	public void movingLightHandler(PlayerTickEvent event) {
		if (event.phase != TickEvent.Phase.START || event.player.world.isRemote || event.player.getHeldEquipment() == null || event.player.getHeldItemMainhand().getItem() != ItemInit.ATD_TORCH) {

			return;
		}

		final int blockX = MathHelper.floor(event.player.posX);
		final int blockY = MathHelper.floor(event.player.posY - 0.2D - event.player.getYOffset());
		final int blockZ = MathHelper.floor(event.player.posZ);
		final BlockPos pos = new BlockPos(blockX, blockY + 1, blockZ);

		//if (event.player.world.isAirBlock(pos)) {
			if(event.player.world.isAirBlock(pos)) {
			final BlockMovingLightSource lightSource = BlockInit.BLOCK_MLS;
			event.player.world.setBlockState(pos, lightSource.setPlayer(event.player).getDefaultState());
			
			}
			else {

	}
	}

	@SubscribeEvent
	public void onBlockHarvest(HarvestDropsEvent event) {
		if (event.getState().getBlock() == Blocks.TORCH) {
			event.getDrops().clear();
			event.setDropChance(1.0F);
			event.getDrops().add(new ItemStack(ItemInit.ATD_TORCH));
	 	 }
	}

}