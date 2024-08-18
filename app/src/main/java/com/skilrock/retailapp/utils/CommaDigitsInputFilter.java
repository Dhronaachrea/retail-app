package com.skilrock.retailapp.utils;

import android.text.InputFilter;
import android.text.Spanned;

import java.util.regex.Pattern;

public class CommaDigitsInputFilter implements InputFilter {
    Pattern pattern;
    private int digitsAfterDecimal;
    private String formatter;
    private int digitsBeforeDecimal;

    public CommaDigitsInputFilter(int digitsBeforeDecimal, int digitsAfterDecimal, String formatter) {
        this.digitsAfterDecimal = digitsAfterDecimal;
        this.formatter = formatter;
        this.digitsBeforeDecimal = digitsBeforeDecimal;

        pattern = Pattern.compile("(([1-9]{1}[0-9]{0," + (digitsBeforeDecimal - 1) + "})?||[0]{1})((\\.[0-9]{0," + digitsAfterDecimal + "})?)||(\\.)?");
    }

    @Override
    public CharSequence filter(CharSequence source, int sourceStart, int sourceEnd, Spanned destination, int destinationStart, int destinationEnd) {
      /*  // Remove the string out of destination that is to be replaced.
        String newString = destination.toString().substring(0, destinationStart) + destination.toString().substring(destinationEnd, destination.toString().length());
        // Add the new string in.
        newString = newString.substring(0, destinationStart) + source.toString() + newString.substring(destinationStart, newString.length());
        // Now check if the new string is valid.
        Matcher matcher = pattern.matcher(newString);

        if (matcher.matches()) {
            // Returning null indicates that the input is valid.
            return null;
        }
        // Returning the empty string indicates the input is invalid.
        return "";*/


      /*  if (source.toString().equalsIgnoreCase(",") && destination.toString().contains(",")) {
            return "";
        }

        if (destination.toString().contains(",")) {
            if (destination.toString().split(",")[1] != null && destination.toString().split(",")[1].length() == digitsAfterDecimal) {
                return "";
            }
        }*/
/*

        if (destination.toString().length() == digitsBeforeDecimal) {
            return "";
        }
*/

        if (digitsAfterDecimal == 0) {
            if (source.toString().equalsIgnoreCase(formatter)) {
                return "";
            }
        } else {
            if (source.toString().equalsIgnoreCase(formatter) && destination.toString().contains(formatter)) {
                return "";
            }

            if (destination.toString().contains(formatter) && destination.toString().split("\\" + formatter) != null
                    && destination.toString().split("\\" + formatter).length > 1
                    && destination.toString().split("\\" + formatter)[1].length() == digitsAfterDecimal) {

                if (getCharacterPosition(formatter, destination.toString()) >= destinationStart)
                    if (destination.toString().split("\\" + formatter).length > 0
                            && destination.toString().split("\\" + formatter)[0].length() >= digitsBeforeDecimal)
                        return "";
                    else
                        return null;
                else
                    return "";
            }
        }

        if (!destination.toString().contains(formatter) && destination.toString().length() >= digitsBeforeDecimal) {
            return "";
        }

        return null;
    }

    private int getCharacterPosition(String character, String source){
        return source.indexOf(character);
    }
}
