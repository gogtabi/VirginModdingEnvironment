package org.maghtuireadh.virginmod.init;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.maghtuireadh.virginmod.objects.blocks.BlockBase;
import org.maghtuireadh.virginmod.objects.blocks.furnaces.BlockFirepit;
import org.maghtuireadh.virginmod.objects.blocks.furnaces.BlockFirepit2;
import org.maghtuireadh.virginmod.objects.blocks.torches.BlockATDTorch;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.block.BlockFurnace;
import org.maghtuireadh.virginmod.objects.blocks.furnaces.BlockFirepit;


public class BlockInit {
	public static final List<Block> BLOCKS = new ArrayList<Block>();
	public static final Block BLOCK_COPPER = new BlockBase("block_copper", Material.IRON);
	//public static final Block ATD_TORCH = new BlockATDTorch("atd_torch");
	public static final BlockFirepit BLOCK_FIREPIT = new BlockFirepit("block_firepit");
	public static final BlockFirepit2 BLOCK_FIREPIT2 = new BlockFirepit2("block_firepit2");
}
