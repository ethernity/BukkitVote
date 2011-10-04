/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fabianbohr.bukkitvote;
import java.io.*;
import java.util.*;
import org.bukkit.ChatColor;

/**
 *
 * @author ethernity
 */
public class LocaleManager {

    private static final String LOCALE_DIR = "com.fabianbohr.bukkitvote.resources.locale.bukkitvote";
    private static ResourceBundle localeBundle = null;

    public static void init(String loc) {
        String locale = loc;
        try {
           
            localeBundle = ResourceBundle.getBundle(LOCALE_DIR, new Locale(locale));
        } catch (MissingResourceException e) {
            localeBundle = ResourceBundle.getBundle(LOCALE_DIR, new Locale("en_us"));
        }



    }

    public static String getString(String key) {
        return getString(key, null);
    }

    public static String getString(String key, Map<String, String> params) {
        try {
            String output = localeBundle.getString(key);

            if (params != null) {
                for (Map.Entry<String, String> e : params.entrySet()) {
                    String ekey = e.getKey();
                    String evalue = e.getValue();
                    output = output.replaceAll("(?i)\\Q{{" + ekey + "}}\\E", evalue);
                }
            }

            output = addColors(output);


            return output;
        } catch (MissingResourceException e) {
            return "Missing locale string: " + key;
        }
    }

    private static String addColors(String input) {
        input = input.replaceAll("(?i)\\Q{{BLACK}}\\E", ChatColor.BLACK.toString());
        input = input.replaceAll("(?i)\\Q{{DARK_BLUE}}\\E", ChatColor.DARK_BLUE.toString());
        input = input.replaceAll("(?i)\\Q{{DARK_GREEN}}\\E", ChatColor.DARK_GREEN.toString());
        input = input.replaceAll("(?i)\\Q{{DARK_AQUA}}\\E", ChatColor.DARK_AQUA.toString());
        input = input.replaceAll("(?i)\\Q{{DARK_RED}}\\E", ChatColor.DARK_RED.toString());
        input = input.replaceAll("(?i)\\Q{{DARK_PURPLE}}\\E", ChatColor.DARK_PURPLE.toString());
        input = input.replaceAll("(?i)\\Q{{GOLD}}\\E", ChatColor.GOLD.toString());
        input = input.replaceAll("(?i)\\Q{{GRAY}}\\E", ChatColor.GRAY.toString());
        input = input.replaceAll("(?i)\\Q{{DARK_GRAY}}\\E", ChatColor.DARK_GRAY.toString());
        input = input.replaceAll("(?i)\\Q{{BLUE}}\\E", ChatColor.BLUE.toString());
        input = input.replaceAll("(?i)\\Q{{GREEN}}\\E", ChatColor.GREEN.toString());
        input = input.replaceAll("(?i)\\Q{{AQUA}}\\E", ChatColor.AQUA.toString());
        input = input.replaceAll("(?i)\\Q{{RED}}\\E", ChatColor.RED.toString());
        input = input.replaceAll("(?i)\\Q{{LIGHT_PURPLE}}\\E", ChatColor.LIGHT_PURPLE.toString());
        input = input.replaceAll("(?i)\\Q{{YELLOW}}\\E", ChatColor.YELLOW.toString());
        input = input.replaceAll("(?i)\\Q{{WHITE}}\\E", ChatColor.WHITE.toString());

        return input;
    }

    public static HashMap<String, String> replacePlayer(String name) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("PLAYER", name);
        return map;



    }

}
