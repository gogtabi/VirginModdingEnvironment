package org.maghtuireadh.virginmod.init;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.maghtuireadh.virginmod.objects.blocks.BlockBase;
import org.maghtuireadh.virginmod.objects.blocks.furnaces.BlockFirepit;
import org.maghtuireadh.virginmod.objects.blocks.torches.atd_torch;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.block.BlockFurnace;
import org.maghtuireadh.virginmod.objects.blocks.furnaces.BlockFirepit;


public class BlockInit {
	public static Block BlockFirepit = new BlockFirepit();
	
	public static final List<Block> BLOCKS = new ArrayList<Block>();
	public static final Block BLOCK_COPPER = new BlockBase("block_copper", Material.IRON);
<<<<<<< HEAD:src/main/java/org/maghtuireadh/virginmod/init/BlockInit.java
	public static final Block ATD_TORCH = new atd_torch("atd_torch");

=======
	public static final Block BLOCK_FIREPIT = new BlockBase("block_firepit", BlockFirepit, Material.ROCK);
>>>>>>> refs/remotes/LunariusH/master:main/java/org/maghtuireadh/virginmod/init/BlockInit.java
}
