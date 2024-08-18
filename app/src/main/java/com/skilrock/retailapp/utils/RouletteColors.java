package com.skilrock.retailapp.utils;

public interface RouletteColors {

    String RED      = "RED";
    String GREEN    = "GREEN";
    String BLACK    = "BLACK";

    static String getBallColor(String colorName) {
        try {
            switch (colorName.toUpperCase()) {
                case "RED":
                    return "#d10b22";
                case "GREEN":
                    return "#089148";
                case "BLACK":
                    return "#010500";
                default:
                    return "#000000";
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return "#000000";
        }
    }

}
