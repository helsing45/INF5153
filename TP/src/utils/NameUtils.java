package utils;

import java.util.ArrayList;

/**
 * Created by j-c9 on 2017-03-04.
 */
public class NameUtils {

    private ArrayList<String> existingName;

    private static NameUtils instance;

    private NameUtils() {
        existingName = new ArrayList<>();
    }

    public static NameUtils getInstance() {
        if (instance == null) instance = new NameUtils();
        return instance;
    }

    public static boolean isNameAvailable(String name) {
        return !getInstance().existingName.contains(name);
    }

    public static void reserveName(String name) {
        getInstance().existingName.add(name);
    }
}
