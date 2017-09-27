package org.maghtuireadh.virginmod.init;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.maghtuireadh.virginmod.objects.blocks.BlockBase;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.block.BlockFurnace;

public class BlockInit {
	public static final List<Block> BLOCKS = new ArrayList<Block>();
	public static final Block BLOCK_COPPER = new BlockBase("block_copper", Material.ROCK, 1.0F, "pickaxe", 2, 2.0F);
}
