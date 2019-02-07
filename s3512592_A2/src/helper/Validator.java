package helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Validator {

    /**
     * Validate whether a holding ID or a member ID is valid
     */
    public static boolean validateId(char type, String id) {
        String regex = "";

        if (type == 'h'){
            regex = "(^[bv])([0-9]{6}$)";
        }else if(type == 'm'){
            regex = "(^[ps])([0-9]{6}$)";
        }

        // create pattern object
        Pattern pattern = Pattern.compile(regex);

        // create matcher object
        Matcher matcher = pattern.matcher(id);

        if (matcher.find())
            return true;
        else
            return false;
    }

    /**
     * Validate whether it is a valid name
     */
    public static boolean validateFullName (String name) {
        String regex = "^[A-Za-z][A-Za-z\\s]*";

        // create pattern object
        Pattern pattern = Pattern.compile(regex);

        // create matcher object
        Matcher matcher = pattern.matcher(name);

        if (matcher.find())
            return true;
        else
            return false;
    }

    /**
     * Validate whether the holding name is valid or not
     */
    public static boolean validateHoldingTitle (String title) {
        String regex = "^[A-Za-z][A-Za-z0-9\\s]*";

        // create pattern object
        Pattern pattern = Pattern.compile(regex);

        // create matcher object
        Matcher matcher = pattern.matcher(title);

        if (matcher.find())
            return true;
        else
            return false;
    }

}
