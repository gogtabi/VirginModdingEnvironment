package org.maghtuireadh.virginmod.util.handlers;


import net.minecraft.util.IStringSerializable;

public class EnumHandler 
{
	
	public enum LitStates implements IStringSerializable 
	{
		UNLIT("unlit"),
		LIT("lit"),
		LIT_STOKED("lit_stoked"),
		LIT_BANKED("lit_banked");
		
		private final String name;
		
		private LitStates(String name) 
		{
			this.name=name;
		}

		@Override
		public String toString() 
		{
			return this.name();
		}
		@Override
		public String getName() 
		{
			return this.name;
		}
		
	}
	public static enum EnumType implements IStringSerializable
	{
		COPPER(0, "copper"),
		ALUMINIUM(1, "aluminium");
		
		private static final EnumType[] META_LOOKUP = new EnumType[values().length];
		private final int meta;
		private final String name, unlocalizedName;
		
		private EnumType(int meta, String name)
		{
			this(meta, name, name);
		}
		
		private EnumType(int meta, String name, String unlocalizedName) 
		{
			this.meta=meta;
			this.name= name;
			this.unlocalizedName = unlocalizedName;
			
		}
		
		
		
		@Override
		public String getName() 
		{
			return this.name;
		}
		
		public int getMeta() 
		{
			return this.meta;
		}
			
		public String getUnlocalizedName()
		{
			return this.unlocalizedName;
		}
		
		public static EnumType byMetaData(int meta)
		{
			return META_LOOKUP[meta];
		}

		static
		{
			for(EnumType enumtype : values())
			{
				META_LOOKUP[enumtype.getMeta()] = enumtype;
			}
		}

	}
}
