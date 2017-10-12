package org.maghtuireadh.virginmod.init;

import java.util.ArrayList;
import java.util.List;

import org.maghtuireadh.virginmod.objects.blocks.BlockBase;
import org.maghtuireadh.virginmod.objects.blocks.hearths.BlockFirepit;
import org.maghtuireadh.virginmod.objects.blocks.torches.BlockATDLitPumpkin;
import org.maghtuireadh.virginmod.objects.blocks.torches.BlockATDPumpkin;
import org.maghtuireadh.virginmod.objects.blocks.movinglight.BlockMovingLightSource;
import org.maghtuireadh.virginmod.objects.blocks.torches.BlockATDTorch;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.biome.BiomeForest;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBirchTree;


public class BlockInit {
	public static final List<Block> BLOCKS = new ArrayList<Block>();
	public static final Block BLOCK_COPPER = new BlockBase("block_copper", Material.IRON);
	public static final Block ATD_TORCH = new BlockATDTorch("block_atd_torch");
	public static final BlockFirepit BLOCK_FIREPIT = new BlockFirepit("block_firepit", Material.ROCK);
	public static final BlockATDLitPumpkin BLOCK_ATD_LIT_PUMPKIN = new BlockATDLitPumpkin("block_atd_pumpkin", Material.GOURD);
	public static final BlockMovingLightSource BLOCK_MLS = new BlockMovingLightSource("block_mls");
	public static final Block ORE_END = new BlockOres("ore_end", "end");
	public static final Block ORE_OVERWORLD = new BlockOres("ore_overworld", "overworld");
	public static final Block ORE_NETHER = new BlockOres("ore_nether", "nether");
	public static final Block LEECHING_BUCKET = new BlockBase("atd_leeching_bucket", Material.WOOD);
}
