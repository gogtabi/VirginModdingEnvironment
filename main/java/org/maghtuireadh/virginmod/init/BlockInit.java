package org.maghtuireadh.virginmod.init;

import java.util.ArrayList;
import java.util.List;

import org.maghtuireadh.virginmod.objects.blocks.BlockBase;
import org.maghtuireadh.virginmod.objects.blocks.furnaces.BlockFirepit;
import org.maghtuireadh.virginmod.objects.blocks.movinglight.BlockMovingLightSource;
import org.maghtuireadh.virginmod.objects.blocks.torches.BlockATDTorch;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;


public class BlockInit {
	public static final List<Block> BLOCKS = new ArrayList<Block>();
	public static final Block BLOCK_COPPER = new BlockBase("block_copper", Material.IRON);
	public static final Block ATD_TORCH = new BlockATDTorch("block_atd_torch");
	public static final BlockFirepit BLOCK_FIREPIT = new BlockFirepit("block_firepit", Material.ROCK);
	public static final BlockMovingLightSource BLOCK_MLS = new BlockMovingLightSource("block_mls");
//	public static final BlockFirepit2 ADV_FIREPIT = new BlockFirepit2("advanced_firepit");
}
