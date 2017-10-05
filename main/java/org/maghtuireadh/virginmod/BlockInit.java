package anbaric.mod.init;

import java.util.ArrayList;
import java.util.List;

import anbaric.mod.objects.blocks.BlockBase;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockInit 
{
	public static final List<Block> BLOCKS = new ArrayList<Block>();
	
	//Soils

	
	
	
	
	
	public static final Block SOIL_MUD = new BlockBase("soil_mud",               Material.GROUND, 0.5f, "shovel", 0, 2.5f);
	public static final Block SOIL_ASH = new BlockBase("soil_ash",               Material.GROUND, 0.9f, "shovel", 2, 2.5f);
	public static final Block GRASS_JUNGLE = new BlockBase("grass_jungle",       Material.GROUND, 0.6f, "shovel", 0, 3f);
	public static final Block GRASS_MUSHROOM = new BlockBase("grass_mushroom",   Material.GROUND, 0.6f, "shovel", 0, 3f);
	public static final Block GRASS_CORRUPT = new BlockBase("grass_corrupt",     Material.GROUND, 0.7f, "shovel", 1, 2.5f);
	public static final Block GRASS_CRIMSON = new BlockBase("grass_crimson",     Material.GROUND, 0.7f, "shovel", 1, 2.5f);
	public static final Block GRASS_HALLOWED = new BlockBase("grass_hallowed",   Material.GROUND, 0.6f, "shovel", 1, 2.5f);
	public static final Block SAND_EBON = new BlockBase("sand_ebon",             Material.GROUND, 0.5f, "shovel", 2, 2.5f);
	public static final Block SAND_CRIM = new BlockBase("sand_crim",             Material.GROUND, 0.5f, "shovel", 2, 2.5f);
	public static final Block SAND_PEARL = new BlockBase("sand_pearl",           Material.GROUND, 0.5f, "shovel", 2, 2.5f);
	public static final Block SAND_SILT = new BlockBase("sand_silt",             Material.GROUND, 0.5f, "shovel", 2, 2.5f);
	public static final Block SAND_SLUSH = new BlockBase("sand_slush",           Material.GROUND, 0.5f, "shovel", 2, 2.5f);
	
	//Stones
	public static final Block STONE_EBONSAND = new BlockBase("stone_ebonsand",            Material.ROCK, 0.8f, "pickaxe", 1, 30.0f);
	public static final Block STONE_CRIMSAND = new BlockBase("stone_crimsand",            Material.ROCK, 0.8f, "pickaxe", 1, 30.0f);
	public static final Block STONE_PEARLSAND = new BlockBase("stone_pearlsand",          Material.ROCK, 0.8f, "pickaxe", 1, 30.0f);
	public static final Block STONE_HARDEBONSAND = new BlockBase("stone_hardebonsand",    Material.ROCK, 1.1f, "pickaxe", 2, 30.0f);
	public static final Block STONE_HARDCRIMSAND = new BlockBase("stone_hardcrimsand",    Material.ROCK, 1.1f, "pickaxe", 2, 30.0f);
	public static final Block STONE_HARDPEARLSAND = new BlockBase("stone_hardpearlsand",  Material.ROCK, 1.1f, "pickaxe", 2, 30.0f);
	public static final Block STONE_EBON = new BlockBase("stone_ebon",                    Material.ROCK, 1.8f, "pickaxe", 2, 30.0f);
	public static final Block STONE_CRIM = new BlockBase("stone_crim",                    Material.ROCK, 1.8f, "pickaxe", 2, 30.0f);
	public static final Block STONE_PEARL = new BlockBase("stone_pearl",                  Material.ROCK, 1.8f, "pickaxe", 2, 30.0f);
	public static final Block STONE_MOSS_RED = new BlockBase("stone_moss_red",            Material.ROCK, 1.7f, "pickaxe", 1, 30.0f);
	public static final Block STONE_MOSS_YELLOW = new BlockBase("stone_moss_yellow",      Material.ROCK, 1.7f, "pickaxe", 1, 30.0f);
	public static final Block STONE_MOSS_GREEN = new BlockBase("stone_moss_green",        Material.ROCK, 1.7f, "pickaxe", 1, 30.0f);
	public static final Block STONE_MOSS_BLUE = new BlockBase("stone_moss_blue",          Material.ROCK, 1.7f, "pickaxe", 1, 30.0f);
	public static final Block STONE_MOSS_PURPLE = new BlockBase("stone_moss_purple",      Material.ROCK, 1.7f, "pickaxe", 1, 30.0f);
	public static final Block STONE_MOSS_FIRE = new BlockBase("stone_moss_fire",          Material.ROCK, 1.8f, "pickaxe", 2, 30.0f);
	public static final Block STONE_GRANITE = new BlockBase("stone_granite",              Material.ROCK, 1.5f, "pickaxe", 2, 30.0f);
	public static final Block STONE_MARBLE = new BlockBase("stone_marble",                Material.ROCK, 1.5f, "pickaxe", 2, 30.0f);
	public static final Block STONE_FOSSIL = new BlockBase("stone_fossil",                Material.ROCK, 1.5f, "pickaxe", 2, 30.0f);
	public static final Block ICE_PURPLE = new BlockBase("ice_purple",                    Material.ICE, 0.5f, "pickaxe", 2, 30.0f);
	public static final Block ICE_RED = new BlockBase("ice_red",                          Material.ICE, 0.5f, "pickaxe", 2, 30.0f);
	public static final Block ICE_PINK = new BlockBase("ice_pink",                        Material.ICE, 0.5f, "pickaxe", 2, 30.0f);
	public static final Block ICE_THIN = new BlockBase("ice_thin",                        Material.ICE, 0.5f, "pickaxe", 2, 30.0f);
	
	
	//Woods
	public static final Block LOG_ASH = new BlockBase("log_ash",                        Material.WOOD, 2f, "axe", 0, 15.0f);
	public static final Block LOG_BOREAL = new BlockBase("log_boreal",                  Material.WOOD, 2f, "axe", 0, 15.0f);
	public static final Block LOG_PALM = new BlockBase("log_palm",                      Material.WOOD, 2f, "axe", 0, 15.0f);
	public static final Block LOG_MAHOGANY = new BlockBase("log_mahogany",              Material.WOOD, 2f, "axe", 0, 15.0f);
	public static final Block LOG_EBON = new BlockBase("log_ebon",                      Material.WOOD, 2f, "axe", 0, 15.0f);
	public static final Block LOG_SHADE = new BlockBase("log_shade",                    Material.WOOD, 2f, "axe", 0, 15.0f);
	public static final Block LOG_PEARL = new BlockBase("log_pearl",                    Material.WOOD, 2f, "axe", 0, 15.0f);
	public static final Block LOG_MUSHROOM = new BlockBase("log_mushroom",              Material.WOOD, 2f, "axe", 0, 15.0f);
	public static final Block PLANK_ASH = new BlockBase("plank_ash",                    Material.WOOD, 2f, "axe", 0, 15.0f);
	public static final Block PLANK_BOREAL = new BlockBase("plank_boreal",              Material.WOOD, 2f, "axe", 0, 15.0f);
	public static final Block PLANK_PALM = new BlockBase("plank_palm",                  Material.WOOD, 2f, "axe", 0, 15.0f);
	public static final Block PLANK_MAHOGANY = new BlockBase("plank_mahogany",          Material.WOOD, 2f, "axe", 0, 15.0f);
	public static final Block PLANK_EBON = new BlockBase("plank_ebon",                  Material.WOOD, 2f, "axe", 0, 15.0f);
	public static final Block PLANK_SHADE = new BlockBase("plank_shade",                Material.WOOD, 2f, "axe", 0, 15.0f);
	public static final Block PLANK_PEARL = new BlockBase("plank_pearl",                Material.WOOD, 2f, "axe", 0, 15.0f);
	public static final Block PLANK_DYNASTY = new BlockBase("plank_dynasty",            Material.WOOD, 2f, "axe", 0, 15.0f);
	public static final Block PLANK_SPOOKY = new BlockBase("plank_spooky",              Material.WOOD, 2f, "axe", 0, 15.0f);
	
	//Foliage
	public static final Block PLANT_MOSS_RED = new BlockBase("plant_moss_red",                   Material.PLANTS, 0f, "shears", 0, 0.0f);
	public static final Block PLANT_MOSS_YELLOW = new BlockBase("plant_moss_yellow",             Material.PLANTS, 0f, "shears", 0, 0.0f);
	public static final Block PLANT_MOSS_GREEN = new BlockBase("plant_moss_green",               Material.PLANTS, 0f, "shears", 0, 0.0f);
	public static final Block PLANT_MOSS_BLUE = new BlockBase("plant_moss_blue",                 Material.PLANTS, 0f, "shears", 0, 0.0f);
	public static final Block PLANT_MOSS_PURPLE = new BlockBase("plant_moss_purple",             Material.PLANTS, 0f, "shears", 0, 0.0f);
	public static final Block PLANT_MOSS_FIRE = new BlockBase("plant_moss_fire",                 Material.PLANTS, 0f, "shears", 0, 0.0f);
	public static final Block PLANT_BLINKROOT = new BlockBase("plant_blinkroot",                 Material.PLANTS, 0f, "shears", 0, 0.0f);
	public static final Block PLANT_DAYBLOOM = new BlockBase("plant_daybloom",                   Material.PLANTS, 0f, "shears", 0, 0.0f);
	public static final Block PLANT_DEATHWEED = new BlockBase("plant_deathweed",                 Material.PLANTS, 0f, "shears", 0, 0.0f);
	public static final Block PLANT_FIREBLOSSOM = new BlockBase("plant_fireblossom",             Material.PLANTS, 0f, "shears", 0, 0.0f);
	public static final Block PLANT_MOONGLOW = new BlockBase("plant_moonglow",                   Material.PLANTS, 0f, "shears", 0, 0.0f);
	public static final Block PLANT_SHIVERTHORN = new BlockBase("plant_shiverthorn",             Material.PLANTS, 0f, "shears", 0, 0.0f);
	public static final Block PLANT_WATERLEAF = new BlockBase("plant_waterleaf",                 Material.PLANTS, 0f, "shears", 0, 0.0f);
	public static final Block PLANT_MUSHROOM_GLOWING = new BlockBase("plant_mushroom_glowing",   Material.PLANTS, 0f, "shears", 0, 0.0f);
	public static final Block PLANT_MUSHROOM_VILE = new BlockBase("plant_mushroom_vile",         Material.PLANTS, 0f, "shears", 0, 0.0f);
	public static final Block PLANT_MUSHROOM_VICIOUS = new BlockBase("plant_mushroom_vicious",   Material.PLANTS, 0f, "shears", 0, 0.0f);
	public static final Block PLANT_THORN_PURPLE = new BlockBase("plant_thorn_purple",           Material.PLANTS, 0f, "shears", 0, 0.0f);
	public static final Block PLANT_THORN_RED = new BlockBase("plant_thorn_red",                 Material.PLANTS, 0f, "shears", 0, 0.0f);
	public static final Block PLANT_THORN_JUNGLE = new BlockBase("plant_thorn_jungle",           Material.PLANTS, 0f, "shears", 0, 0.0f);
	public static final Block PLANT_LIFEFRUIT = new BlockBase("plant_lifefruit",                 Material.PLANTS, 0f, "shears", 0, 0.0f);
	public static final Block PLANT_KELP = new BlockBase("plant_kelp",                           Material.PLANTS, 0f, "shears", 0, 0.0f);
	public static final Block PLANT_BLOODROOT = new BlockBase("plant_bloodroot",                 Material.PLANTS, 0f, "shears", 0, 0.0f);
	public static final Block PLANT_PEAR = new BlockBase("plant_pear",                           Material.PLANTS, 0f, "shears", 0, 0.0f);
	public static final Block PLANT_DYE_PURPLE = new BlockBase("plant_dye_purple",               Material.PLANTS, 0f, "shears", 0, 0.0f);
	public static final Block PLANT_DYE_ORANGE = new BlockBase("plant_dye_orange",               Material.PLANTS, 0f, "shears", 0, 0.0f);
	public static final Block PLANT_DYE_CYAN = new BlockBase("plant_dye_cyan",                   Material.PLANTS, 0f, "shears", 0, 0.0f);
	public static final Block PLANT_DYE_RED = new BlockBase("plant_dye_red",                     Material.PLANTS, 0f, "shears", 0, 0.0f);
	public static final Block PLANT_BULB = new BlockBase("plant_bulb",                           Material.PLANTS, 0f, "shears", 0, 0.0f);
	public static final Block LEAF_ASH = new BlockBase("leaf_ash",                               Material.LEAVES, 0f, "shears", 0, 0.0f);
	public static final Block LEAF_BOREAL = new BlockBase("leaf_boreal",                         Material.LEAVES, 0f, "shears", 0, 0.0f);
	public static final Block LEAF_PALM = new BlockBase("leaf_palm",                             Material.LEAVES, 0f, "shears", 0, 0.0f);
	public static final Block LEAF_MAHOGANY = new BlockBase("leaf_mahogany",                     Material.LEAVES, 0f, "shears", 0, 0.0f);
	public static final Block LEAF_EBON = new BlockBase("leaf_ebon",                             Material.LEAVES, 0f, "shears", 0, 0.0f);
	public static final Block LEAF_SHADE = new BlockBase("leaf_shade",                           Material.LEAVES, 0f, "shears", 0, 0.0f);
	public static final Block LEAF_PEARL = new BlockBase("leaf_pearl",                           Material.LEAVES, 0f, "shears", 0, 0.0f);
	
	//Bricks
	public static final Block BRICK_SNOW = new BlockBase("brick_snow",                  Material.SNOW, 1.8f, "pickaxe", 2, 30f);
	public static final Block BRICK_ICE = new BlockBase("brick_ice",                    Material.ICE, 1.8f, "pickaxe", 2, 30f);
	public static final Block BRICK_MUDSTONE = new BlockBase("brick_mudstone",          Material.ROCK, 1.8f, "pickaxe", 2, 30f);
	public static final Block BRICK_ASHSTONE = new BlockBase("brick_ashstone",          Material.ROCK, 1.8f, "pickaxe", 2, 30f);
	public static final Block BRICK_EBONSTONE = new BlockBase("brick_ebonstone",        Material.ROCK, 1.8f, "pickaxe", 2, 30f);
	public static final Block BRICK_PEARLSTONE = new BlockBase("brick_pearlstone",      Material.ROCK, 1.8f, "pickaxe", 2, 30f);
	public static final Block BRICK_COPPER = new BlockBase("brick_copper",              Material.IRON, 5f, "pickaxe", 2, 30f);
	public static final Block BRICK_TIN = new BlockBase("brick_tin",                    Material.IRON, 5f, "pickaxe", 2, 30f);
	public static final Block BRICK_SILVER = new BlockBase("brick_silver",              Material.IRON, 5f, "pickaxe", 2, 30f);
	public static final Block BRICK_TUNGSTEN = new BlockBase("brick_tungsten",          Material.IRON, 5f, "pickaxe", 2, 30f);
	public static final Block BRICK_GOLD = new BlockBase("brick_gold",                  Material.IRON, 5f, "pickaxe", 2, 30f);
	public static final Block BRICK_PLATINUM = new BlockBase("brick_platinum",          Material.IRON, 5f, "pickaxe", 2, 30f);
	public static final Block BRICK_DEMONITE = new BlockBase("brick_demonite",          Material.IRON, 5f, "pickaxe", 2, 30f);
	public static final Block BRICK_CRIMTANE = new BlockBase("brick_crimtane",          Material.IRON, 5f, "pickaxe", 2, 30f);
	public static final Block BRICK_METEORITE = new BlockBase("brick_meteorite",        Material.IRON, 5f, "pickaxe", 2, 30f);
	public static final Block BRICK_OBSIDIAN = new BlockBase("brick_obsidian",          Material.IRON, 5f, "pickaxe", 2, 30f);
	public static final Block BRICK_HELLSTONE = new BlockBase("brick_hellstone",        Material.IRON, 5f, "pickaxe", 2, 30f);
	public static final Block BRICK_COBALT = new BlockBase("brick_cobalt",              Material.IRON, 5f, "pickaxe", 2, 30f);
	public static final Block BRICK_PALLADIUM = new BlockBase("brick_palladium",        Material.IRON, 5f, "pickaxe", 2, 30f);
	public static final Block BRICK_MYTHRIL = new BlockBase("brick_mythril",            Material.IRON, 5f, "pickaxe", 2, 30f);
	public static final Block BRICK_ORICHALCUM = new BlockBase("brick_orichalcum",      Material.IRON, 5f, "pickaxe", 2, 30f);
	public static final Block BRICK_ADAMANTITE = new BlockBase("brick_adamantite",      Material.IRON, 5f, "pickaxe", 2, 30f);
	public static final Block BRICK_TITANIUM = new BlockBase("brick_titanium",          Material.IRON, 5f, "pickaxe", 2, 30f);
	public static final Block BRICK_CHLOROPHYTE = new BlockBase("brick_chlorophyte",    Material.IRON, 5f, "pickaxe", 2, 30f);
	public static final Block BRICK_LUMINITE = new BlockBase("brick_luminite",          Material.IRON, 5f, "pickaxe", 2, 30f);
	public static final Block BRICK_RAINBOW = new BlockBase("brick_rainbow",            Material.IRON, 5f, "pickaxe", 2, 30f);
	public static final Block BRICK_DUNGEON_BLUE = new BlockBase("brick_dungeon_blue",  Material.IRON, 5f, "pickaxe", 2, 30f);
	public static final Block BRICK_DUNGEON_RED = new BlockBase("brick_dungeon_red",    Material.IRON, 5f, "pickaxe", 2, 30f);
	public static final Block BRICK_DUNGEON_GREEN = new BlockBase("brick_dungeon_green",Material.IRON, 5f, "pickaxe", 2, 30f);
	public static final Block BRICK_LIHZAHRD = new BlockBase("brick_lihzahrd",          Material.IRON, 5f, "pickaxe", 2, 30f);
	public static final Block BRICK_SUNPLATE = new BlockBase("brick_sunplate",          Material.IRON, 5f, "pickaxe", 2, 30f);
	public static final Block BRICK_STUCCO = new BlockBase("brick_stucco",              Material.IRON, 5f, "pickaxe", 2, 30f);
	public static final Block BRICK_STUCCO_RED = new BlockBase("brick_stucco_red",      Material.IRON, 5f, "pickaxe", 2, 30f);
	public static final Block BRICK_STUCCO_GREEN = new BlockBase("brick_stucco_green",  Material.IRON, 5f, "pickaxe", 2, 30f);
	public static final Block BRICK_STUCCO_YELLOW = new BlockBase("brick_stucco_yellow",Material.IRON, 5f, "pickaxe", 2, 30f);
	public static final Block BRICK_FLESH = new BlockBase("brick_flesh",                Material.IRON, 5f, "pickaxe", 2, 30f);
	public static final Block BRICK_STONE = new BlockBase("brick_stone",                Material.ROCK, 1.5f, "pickaxe", 1, 30f);
	public static final Block BRICK_SANDSTONE = new BlockBase("brick_sandstone",        Material.ROCK, 1f, "pickaxe", 1, 30f);
	public static final Block PLATE_TIN = new BlockBase("plate_tin",                    Material.IRON, 5f, "pickaxe", 2, 30f);
	public static final Block PLATE_COPPER = new BlockBase("plate_copper",              Material.IRON, 5f, "pickaxe", 2, 30f);
	public static final Block PLATE_MARTIAN = new BlockBase("plate_martian",            Material.IRON, 5f, "pickaxe", 2, 30f);
	public static final Block PLATE_SHROOMITE = new BlockBase("plate_shroomite",        Material.IRON, 5f, "pickaxe", 2, 30f);
	public static final Block BRICK_CRYSTAL = new BlockBase("brick_crystal",            Material.IRON, 1f, "pickaxe", 2, 30f);
	public static final Block BRICK_GRANITE = new BlockBase("brick_granite",            Material.ROCK, 1f, "pickaxe", 1, 30f);
	public static final Block BRICK_MARBLE = new BlockBase("brick_marble",              Material.ROCK, 1f, "pickaxe", 1, 30f);
		
	//Ores
	public static final Block ORE_COPPER = new BlockBase("ore_copper",           Material.ROCK, 3.0f, "pickaxe", 2, 15.0f);
	public static final Block ORE_TIN= new BlockBase("ore_tin",                  Material.ROCK, 3.0f, "pickaxe", 2, 15.0f);
	public static final Block ORE_LEAD= new BlockBase("ore_lead",                Material.ROCK, 3.0f, "pickaxe", 2, 15.0f);
	public static final Block ORE_SILVER = new BlockBase("ore_silver",           Material.ROCK, 3.0f, "pickaxe", 2, 15.0f);
	public static final Block ORE_TUNGSTEN = new BlockBase("ore_tungsten",       Material.ROCK, 2.0f, "pickaxe", 2, 15.0f);
	public static final Block ORE_PLATINUM = new BlockBase("ore_platinum",       Material.ROCK, 2.0f, "pickaxe", 2, 15.0f);
	public static final Block ORE_METEORITE = new BlockBase("ore_meteorite",     Material.ROCK, 5.0f, "pickaxe", 3, 15.0f);
	public static final Block ORE_DEMONITE = new BlockBase("ore_demonite",       Material.ROCK, 6.0f, "pickaxe", 3, 500.0f);
	public static final Block ORE_CRIMTANE = new BlockBase("ore_crimtane",       Material.ROCK, 6.0f, "pickaxe", 3, 500.0f);
	public static final Block ORE_HELLSTONE = new BlockBase("ore_hellstone",     Material.ROCK, 2.0f, "pickaxe", 2, 500.0f);
	public static final Block ORE_COBALT = new BlockBase("ore_cobalt",           Material.ROCK, 3.0f, "pickaxe", 5, 500.0f);
	public static final Block ORE_PALLADIUM = new BlockBase("ore_palladium",     Material.ROCK, 5.0f, "pickaxe", 5, 500.0f);
	public static final Block ORE_MYTHRIL = new BlockBase("ore_mythril",         Material.ROCK, 3.0f, "pickaxe", 6, 500.0f);
	public static final Block ORE_ORICHALCUM = new BlockBase("ore_orichalcum",   Material.ROCK, 5.0f, "pickaxe", 6, 500.0f);
	public static final Block ORE_ADAMANTITE = new BlockBase("ore_adamantite",   Material.ROCK, 3.0f, "pickaxe", 7, 500.0f);
	public static final Block ORE_TITANIUM = new BlockBase("ore_titanium",       Material.ROCK, 5.0f, "pickaxe", 7, 500.0f);
	public static final Block ORE_CHLOROPHYTE = new BlockBase("ore_chlorophyte", Material.ROCK, 5.0f, "pickaxe", 8, 500.0f);
	public static final Block ORE_AMETHYST = new BlockBase("ore_amethyst",       Material.ROCK, 2.0f, "pickaxe", 2, 15.0f);
	public static final Block ORE_TOPAZ = new BlockBase("ore_topaz",             Material.ROCK, 2.0f, "pickaxe", 2, 15.0f);
	public static final Block ORE_SAPPHIRE = new BlockBase("ore_sapphire",       Material.ROCK, 2.0f, "pickaxe", 2, 15.0f);
	public static final Block ORE_RUBY = new BlockBase("ore_ruby",               Material.ROCK, 2.0f, "pickaxe", 2, 15.0f);
	
	//Misc
	public static final Block MISC_CACTUS = new BlockBase("misc_cactus",              Material.WOOD, 2f, "axe", 0, 15.0f);
	public static final Block MISC_CLOUD = new BlockBase("misc_cloud",                Material.CLOTH, 0.5f, "shovel", 0, 15.0f);
	public static final Block MISC_RAINCLOUD = new BlockBase("misc_raincloud",        Material.CLOTH, 0.5f, "shovel", 0, 15.0f);
	public static final Block MISC_HIVE = new BlockBase("misc_hive",                  Material.WOOD, 2f, "axe", 0, 15.0f);
	public static final Block MISC_HONEY = new BlockBase("misc_honey",                Material.GROUND, 2f, "axe", 0, 15.0f);
	public static final Block MISC_CRISPYHONEY = new BlockBase("misc_crispyhoney",    Material.GROUND, 2f, "axe", 1, 15.0f);
	public static final Block MISC_SLIME = new BlockBase("misc_slime",                Material.CLAY, 2f, "shovel", 0, 15.0f);
	public static final Block MISC_SLIME_ICE = new BlockBase("misc_slime_ice",        Material.CLAY, 2f, "shovel", 0, 15.0f);
	public static final Block MISC_SLIME_PINK = new BlockBase("misc_slime_pink",      Material.CLAY, 2f, "shovel", 0, 15.0f);
	public static final Block MISC_ASPHALT = new BlockBase("misc_asphalt",            Material.CLOTH, 2f, "pickaxe", 1, 15.0f);
	public static final Block MISC_SHINGLE_BLUE = new BlockBase("misc_shingle_blue",  Material.ROCK, 2f, "pickaxe", 1, 15.0f);
	public static final Block MISC_SHINGLE_RED = new BlockBase("misc_shingle_red",    Material.ROCK, 2f, "pickaxe", 1, 15.0f);
	public static final Block MISC_COG = new BlockBase("misc_cog",                    Material.IRON, 2f, "pickaxe", 1, 15.0f);
	public static final Block MISC_CANDY_RED = new BlockBase("misc_candy_red",        Material.ROCK, 2f, "pickaxe", 1, 15.0f);
	public static final Block MISC_CANDY_GREEN = new BlockBase("misc_candy_green",    Material.ROCK, 2f, "pickaxe", 1, 15.0f);
	public static final Block MISC_BONE = new BlockBase("misc_bone",                  Material.ROCK, 2f, "pickaxe", 1, 15.0f);
	public static final Block MISC_ROPE = new BlockBase("misc_rope",                  Material.CLOTH, 2f, "", 0, 15.0f);
	public static final Block MISC_ROPE_VINE = new BlockBase("misc_rope_vine",        Material.LEAVES, 2f, "", 0, 15.0f);
	public static final Block MISC_ROPE_WEB = new BlockBase("misc_rope_web",          Material.WEB, 2f, "", 0, 15.0f);
	public static final Block MISC_ROPE_SILK = new BlockBase("misc_rope_silk",        Material.CLOTH, 2f, "", 0, 15.0f);
	public static final Block MISC_CHAIN = new BlockBase("misc_chain",                Material.IRON, 2f, "pickaxe", 2, 15.0f);
	
	//Traps
	public static final Block TRAP_DART = new BlockBase("trap_dart",               Material.IRON, 3.0f, "pickaxe", 2, 15.0f);
	public static final Block TRAP_SUPERDART = new BlockBase("trap_superdart",     Material.IRON, 3.0f, "pickaxe", 2, 15.0f);
	public static final Block TRAP_BALL = new BlockBase("trap_ball",               Material.IRON, 3.0f, "pickaxe", 2, 15.0f);
	public static final Block TRAP_SPEAR = new BlockBase("trap_spear",             Material.IRON, 3.0f, "pickaxe", 2, 15.0f);
	public static final Block TRAP_FLAME = new BlockBase("trap_flame",             Material.IRON, 3.0f, "pickaxe", 2, 15.0f);
	public static final Block TRAP_SPIKE = new BlockBase("trap_spike",             Material.IRON, 3.0f, "pickaxe", 2, 15.0f);
	public static final Block TRAP_SUPERSPIKE = new BlockBase("trap_superspike",   Material.IRON, 3.0f, "pickaxe", 2, 15.0f);
	public static final Block TRAP_GEYSER = new BlockBase("trap_geyser",           Material.IRON, 3.0f, "pickaxe", 2, 15.0f);
}