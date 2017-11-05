package org.maghtuireadh.virginmod.init;

import java.util.ArrayList;
import java.util.List;

import org.maghtuireadh.virginmod.objects.armor.ArmorBase.ArmorBase;
import org.maghtuireadh.virginmod.objects.items.ATDEmberBundle;
import org.maghtuireadh.virginmod.objects.items.ItemBase;
import org.maghtuireadh.virginmod.objects.tools.AtdPokerIron;
import org.maghtuireadh.virginmod.objects.tools.AtdTorch;
import org.maghtuireadh.virginmod.objects.tools.ToolAxe;
import org.maghtuireadh.virginmod.objects.tools.ToolHoe;
import org.maghtuireadh.virginmod.objects.tools.ToolPickaxe;
import org.maghtuireadh.virginmod.objects.tools.ToolShovel;
import org.maghtuireadh.virginmod.objects.tools.ToolSword;
import org.maghtuireadh.virginmod.util.Reference;

import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;

public class ItemInit {

	public static final List<Item> ITEMS = new ArrayList<Item>();
	//Material
	public static final ToolMaterial TOOL_COPPER = EnumHelper.addToolMaterial("tool_copper", 2, 180, 5.0F, 1.5F, 5);
	public static final ArmorMaterial ARMOR_COPPER = EnumHelper.addArmorMaterial("armor_copper", Reference.MODID + ":copper", 13, new int[]{1, 4, 5, 2}, 10, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F);
	
	//Items
	/*public static final Item INGOT_COPPER = new ItemBase("ingot_copper");*/
	public static final Item ATD_WOOD_ASH = new ItemBase("atd_ash_wood");
	public static final Item ATD_STRING_JUTE = new ItemBase("atd_string_jute");
	public static final Item ATD_STRING_SISAL = new ItemBase("atd_string_sisal");
	public static final Item ATD_BARK = new ItemBase("atd_bark");
	public static final Item ATD_TINDER = new ItemBase("atd_tinder");
	public static final Item ATD_TINDER_BUNDLE = new ItemBase("atd_tinder_bundle");
	public static final Item ATD_EMBER_BUNDLE = new ATDEmberBundle("atd_ember_bundle");
	
	//Tools
	/*public static final Item AXE_COPPER = new ToolAxe("axe_copper", TOOL_COPPER);
	public static final Item HOE_COPPER = new ToolHoe("hoe_copper", TOOL_COPPER);
	public static final Item PICKAXE_COPPER = new ToolPickaxe("pickaxe_copper", TOOL_COPPER);
	public static final Item SHOVEL_COPPER = new ToolShovel("shovel_copper", TOOL_COPPER);
	public static final Item SWORD_COPPER = new ToolSword("sword_copper", TOOL_COPPER);*/
	public static final Item ATD_POKER_IRON = new AtdPokerIron("atd_poker_iron", ToolMaterial.IRON);
	
	//Torches
	public static final Item ATD_TORCH_STICK = 		 new AtdTorch("stick",		 ToolMaterial.WOOD, 0.4662F, 3000,  0.1F,  0.18F,  2,  0.9F,  30, 16);
	public static final Item ATD_TORCH_BARK = 		 new AtdTorch("bark", 		 ToolMaterial.WOOD, 0.5328F, 6000,  0.15F, 0.16F,  2,  0.8F,  35, 16);
	public static final Item ATD_TORCH_SAP = 		 new AtdTorch("sap", 		 ToolMaterial.WOOD, 0.5994F, 9000,  0.4F,  0.14F,  6,  0.7F,  45, 16);
	public static final Item ATD_TORCH_TAR = 	     new AtdTorch("tar", 		 ToolMaterial.WOOD, 0.5994F, 12000, 0.5F,  0.12F,  10, 0.6F,  55, 16);
	public static final Item ATD_TORCH_CLOTH = 		 new AtdTorch("cloth", 		 ToolMaterial.WOOD, 0.5994F, 4500,  0.25F, 0.13F,  2,  0.75F, 30, 16);
	public static final Item ATD_TORCH_CLOTHSAP = 	 new AtdTorch("clothsap", 	 ToolMaterial.WOOD, 0.7326F, 9000,  0.5F,  0.11F,  6,  0.55F, 45, 16);
	public static final Item ATD_TORCH_CLOTHTAR =	 new AtdTorch("clothtar", 	 ToolMaterial.WOOD, 0.7326F, 13500, 0.6F,  0.09F,  10, 0.45F, 55, 16);
	public static final Item ATD_TORCH_HESCLOTH = 	 new AtdTorch("hescloth", 	 ToolMaterial.WOOD, 0.5328F, 6000,  0.35F, 0.1F,   2,  0.6F,  40, 16);
	public static final Item ATD_TORCH_HESCLOTHSAP = new AtdTorch("hesclothsap", ToolMaterial.WOOD, 0.666F,  18000, 0.6F,  0.08F,  6,  0.4F,  50, 16);
	public static final Item ATD_TORCH_HESCLOTHTAR = new AtdTorch("hesclothtar", ToolMaterial.WOOD, 0.666F,  24000, 0.7F,  0.06F,  10, 0.3F,  65, 16);
	
	//Armor
	/*public static final Item HELMET_COPPER = new ArmorBase("helmet_copper", ARMOR_COPPER, 1, EntityEquipmentSlot.HEAD);
	public static final Item CHESTPLATE_COPPER = new ArmorBase("chestplate_copper", ARMOR_COPPER, 1, EntityEquipmentSlot.CHEST);
	public static final Item LEGGINGS_COPPER = new ArmorBase("leggings_copper", ARMOR_COPPER, 2, EntityEquipmentSlot.LEGS);
	public static final Item BOOTS_COPPER = new ArmorBase("boots_copper", ARMOR_COPPER, 1, EntityEquipmentSlot.FEET);*/
}

