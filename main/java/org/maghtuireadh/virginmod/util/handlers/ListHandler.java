package org.maghtuireadh.virginmod.util.handlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListHandler {
    
    
    private static String[] fuels =   {"test","one","two"};
    private static Integer[] fueltimes = {100   ,50   ,100};
    
    private static String[] hearthfuels = {"test","one","two"};
    private static String[] lanternfuels = {"test","one","two"};
    
    private static String[] hearthfirestarters = {"test","one","two"};
    private static String[] lanternfirestarters = {"test","one","two"};
    private static String[] torchfirestarters = {"test","one","two"};
    
    private static String[] exts =    {"test","one","two"};
    private static String[] bankers =    {"test","one","two"};
    private static String[] pokers =    {"test","one","two"};
    
    
    public static List<String> FuelList = new ArrayList<String>(Arrays.asList(fuels));
    public static List<Long> BurnTimeList = new ArrayList<Long>();
    
    public static List<String> HearthFuelList = new ArrayList<String>(Arrays.asList(hearthfuels));
    public static List<String> LanternFuelList = new ArrayList<String>(Arrays.asList(lanternfuels));

    public static List<String> HearthFireStarterList = new ArrayList<String>(Arrays.asList(hearthfirestarters));
    public static List<String> LanternFireStarterList = new ArrayList<String>(Arrays.asList(lanternfirestarters));
    public static List<String> TorchFireStarterList = new ArrayList<String>(Arrays.asList(torchfirestarters));
    
    public static List<String> ExtinguishList = new ArrayList<String>(Arrays.asList(exts));
    public static List<String> BankerList = new ArrayList<String>(Arrays.asList(bankers));
    public static List<String> PokerList = new ArrayList<String>(Arrays.asList(pokers));

    public static void writeLists()
    {
        for (int i : fueltimes)
        {
            BurnTimeList.add((long)i);
        }
    }

}