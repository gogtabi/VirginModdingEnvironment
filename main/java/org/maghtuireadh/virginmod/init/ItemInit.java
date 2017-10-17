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
	public static final Item INGOT_COPPER = new ItemBase("ingot_copper");
	public static final Item ATD_WOOD_ASH = new ItemBase("atd_ash_wood");
	public static final Item ATD_STRING_JUTE = new ItemBase("atd_string_jute");
	public static final Item ATD_STRING_SISAL = new ItemBase("atd_string_sisal");
	public static final Item ATD_BARK = new ItemBase("atd_bark");
	public static final Item ATD_TINDER = new ItemBase("atd_tinder");
	public static final Item ATD_TINDER_BUNDLE = new ItemBase("atd_tinder_bundle");
	public static final Item ATD_EMBER_BUNDLE = new ATDEmberBundle("atd_ember_bundle");
	
	//Tools
	public static final Item AXE_COPPER = new ToolAxe("axe_copper", TOOL_COPPER);
	public static final Item HOE_COPPER = new ToolHoe("hoe_copper", TOOL_COPPER);
	public static final Item PICKAXE_COPPER = new ToolPickaxe("pickaxe_copper", TOOL_COPPER);
	public static final Item SHOVEL_COPPER = new ToolShovel("shovel_copper", TOOL_COPPER);
	public static final Item SWORD_COPPER = new ToolSword("sword_copper", TOOL_COPPER);
	public static final Item ATD_TORCH = new AtdTorch("atd_torch", TOOL_COPPER, 500, 5, 70, 30);
	public static final Item ATD_POKER_IRON = new AtdPokerIron("atd_poker_iron", ToolMaterial.IRON);
	
	//Armor
	public static final Item HELMET_COPPER = new ArmorBase("helmet_copper", ARMOR_COPPER, 1, EntityEquipmentSlot.HEAD);
	public static final Item CHESTPLATE_COPPER = new ArmorBase("chestplate_copper", ARMOR_COPPER, 1, EntityEquipmentSlot.CHEST);
	public static final Item LEGGINGS_COPPER = new ArmorBase("leggings_copper", ARMOR_COPPER, 2, EntityEquipmentSlot.LEGS);
	public static final Item BOOTS_COPPER = new ArmorBase("boots_copper", ARMOR_COPPER, 1, EntityEquipmentSlot.FEET);
}

