package org.maghtuireadh.virginmod.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.commons.codec.language.bm.Lang;




public class Utils{

    private static Logger Logger;

    private static Lang lang;

    public static Logger getLogger() {
        if(Logger == null){
            Logger = LogManager.getFormatterLogger(Reference.MODID);
        }
        return Logger;
    }

    public static Lang getLang() {


        return lang;
    }



}