package com.skilrock.retailapp.utils;

import java.util.HashMap;

public class LuckySixBallColorMap {
    public static HashMap<String, String> getMapNumberConfig() {
        return mapNumberConfig;
    }

    public static void setMapNumberConfig(HashMap<String, String> mapNumberConfig) {
        LuckySixBallColorMap.mapNumberConfig = mapNumberConfig;
    }

    public static HashMap<String, String> mapNumberConfig = new HashMap<>();

    public static HashMap<String, String> getMapNumberConfigFiveByNinty() {
        return mapNumberConfigFiveByNinty;
    }

    public static void setMapNumberConfigFiveByNinty(HashMap<String, String> mapNumberConfigSuperKeno) {
        LuckySixBallColorMap.mapNumberConfigFiveByNinty = mapNumberConfigSuperKeno;
    }

    public static HashMap<String, String> mapNumberConfigFiveByNinty = new HashMap<>();

}