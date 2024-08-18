package com.skilrock.retailapp.utils;

public interface FiveByNinetyColors {

    String PINK     = "PINK";
    String RED      = "RED";
    String ORANGE   = "ORANGE";
    String BROWN    = "BROWN";
    String GREEN    = "GREEN";
    String CYAN     = "CYAN";
    String BLUE     = "BLUE";
    String MAGENTA  = "MAGENTA";
    String GREY     = "GREY";

    static String getBallColor(String colorName) {
        try {
            switch (colorName.toUpperCase()) {
                case "PINK":
                    return "#fba8d4";
                case "RED":
                    return "#f83525";
                case "ORANGE":
                    return "#f9b81c";
                case "BROWN":
                    return "#c86e29";
                case "GREEN":
                    return "#8ac539";
                case "CYAN":
                    return "#6bd0ca";
                case "BLUE":
                    return "#0588de";
                case "MAGENTA":
                    return "#d252f7";
                case "GREY":
                    return "#8b919c";
                case "BLACK":
                    return "#000000";
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
