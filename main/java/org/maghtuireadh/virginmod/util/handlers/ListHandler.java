package org.maghtuireadh.virginmod.util.handlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.maghtuireadh.virginmod.config.VMEConfig;
import org.maghtuireadh.virginmod.util.Utils;

public class ListHandler {
    
    
    private static String[] fuels =   {};
    private static Integer[] fueltimes = {1000,500,100};
    private static Integer[] coalburnrate = {1000, 1000, 100};
    private static Integer[] ashburnrate = {500, 500, 100};
    
    private static String[] hearthfuels = {};
    private static String[] lanternfuels = {"test","one","two"};
    
    private static String[] hearthfirestarters = {"minecraft:flint_and_steel-0","vme:atd_ember_bundle-0","vme:atd_torch_base-1","vme:atd_torch_base-0"};
    private static String[] lanternfirestarters = {"minecraft:flint_and_steel-0"};
    private static String[] torchfirestarters = {"minecraft:flint_and_steel-0","vme:blockFirepit-0","minecraft:magma-0","vme:atd_torch_base-0","vme:block_atd_torch_base-5","vme:block_atd_torch_base-6","vme:block_atd_torch_base-7","vme:block_atd_torch_base-8","vme:block_atd_torch_base-9"};
    
    private static String[] exts =    {"item.bucketWater","item.bucketMilk","tile.dirt"};
    private static String[] snuffs =    {"test","one","two"};
    private static String[] bankers =    {"item.atd_ash_wood","one","two"};
    private static String[] pokers =    {"item.atd_poker_iron","one","two"};
    
    
    public static List<String> FuelList = new ArrayList<String>(Arrays.asList(fuels));
    public static List<Long> BurnTimeList = new ArrayList<Long>();
    public static List<Long> CoalBurnRate = new ArrayList<Long>();
    public static List<Long> AshBurnRate = new ArrayList<Long>();
    public static List<String> HearthFuelList = new ArrayList<String>(Arrays.asList(hearthfuels));
    public static List<String> LanternFuelList = new ArrayList<String>(Arrays.asList(lanternfuels));

    public static List<String> HearthFireStarterList = new ArrayList<String>(Arrays.asList(hearthfirestarters));
    public static List<String> LanternFireStarterList = new ArrayList<String>(Arrays.asList(lanternfirestarters));
    public static List<String> TorchFireStarterList = new ArrayList<String>(Arrays.asList(torchfirestarters));
    
    public static List<String> ExtinguishList = new ArrayList<String>(Arrays.asList(exts));
    public static List<String> SnufferList = new ArrayList<String>(Arrays.asList(snuffs));
    public static List<String> BankerList = new ArrayList<String>(Arrays.asList(bankers));
    public static List<String> PokerList = new ArrayList<String>(Arrays.asList(pokers));
    

    public static void writeLists()
    {
        for (int i : fueltimes)
        {
            BurnTimeList.add((long)i);
        }
        for (int i : coalburnrate)
        {
        	CoalBurnRate.add((long)i);
        }
        for (int i : ashburnrate)
        {
        	AshBurnRate.add((long)i);
        }
        
       String[] fuelObjectStringArray = VMEConfig.fuelObjectListString.split(",");
       for(int i = 0;i<fuelObjectStringArray.length;i++) {
    	   String[] fuel = fuelObjectStringArray[i].split("_");
    	   
    	   if(Boolean.parseBoolean(fuel[4])==true && Boolean.parseBoolean(fuel[5])==false) 
    	   {
    		   FuelList.add(fuel[0]);
    		   HearthFuelList.add(fuel[0]);
    		   BurnTimeList.add(Long.parseLong(fuel[1]));
    		   CoalBurnRate.add(Long.parseLong(fuel[2]));
    		   AshBurnRate.add(Long.parseLong(fuel[3]));
    	   }
    	   else if(Boolean.parseBoolean(fuel[4])==false && Boolean.parseBoolean(fuel[5])==true) 
    	   {
    		  FuelList.add(fuel[0]);
    		  LanternFuelList.add(fuel[0]);
    		  BurnTimeList.add(Long.parseLong(fuel[1]));
	   		  CoalBurnRate.add(Long.parseLong(fuel[2]));
	   		  AshBurnRate.add(Long.parseLong(fuel[3]));
    	   }
    	   else if(Boolean.parseBoolean(fuel[4])==true && Boolean.parseBoolean(fuel[5])==true) 
    	   {
    		  FuelList.add(fuel[0]);
    		  HearthFuelList.add(fuel[0]);
     		  LanternFuelList.add(fuel[0]);
     		  BurnTimeList.add(Long.parseLong(fuel[1]));
 	   		  CoalBurnRate.add(Long.parseLong(fuel[2]));
 	   		  AshBurnRate.add(Long.parseLong(fuel[3]));
    	   }
    	   else 
    	   {
    		   Utils.getLogger().info("Invalid Fuel Entry, Both Booleans False: " + fuel[0]);
    	   }

        }
    }

}
