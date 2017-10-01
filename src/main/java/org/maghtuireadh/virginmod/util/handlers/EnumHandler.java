package org.maghtuireadh.virginmod.util.handlers;

import net.minecraft.util.IStringSerializable;

public class EnumHandler {
	public static enum FirepitStates implements IStringSerializable {
		EMPTY("empty", 0),
		ULOW("ulow", 1),
		UMED("umed", 2),
		UFULL("ufull", 3),
		LVLOW("lcoals", 4),
		LLOW("llow", 5),
		LMED("lmed", 6),
		LFULL("lfull", 7),
		ELOW("elow", 8),
		EMED("emed", 9),
		EFULL("efull", 10),
		DPIT("dpit", 11)
		;
		
		private int ID;
		private String name;
		
		private FirepitStates(String name, int ID) {
			this.ID = ID;
			this.name = name;}
			
		@Override
		public String getName() {
			return this.name;
		}
		
		public int getID() {
			return ID;
		}
		
		public String toString() {
			return getName();
		}
		
	}
	public static enum ChipTypes implements IStringSerializable {
		BASIC("basic", 0),
		ADVANCED("advanced", 1);
		
	;
		private int ID;
		private String name;
		
		private ChipTypes(String name, int ID) {
		this.ID = ID;
		this.name= name;
		}
		
		public int getID() {
			return ID;
		}
		
		@Override
		public String getName() {
			return this.name;
		}
		
		public String toString() {
			return getName();
		}
	}
}
