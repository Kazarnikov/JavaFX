package com.finance.l10n;
import java.util.Locale;
import java.util.ResourceBundle;

public class Messages {

//    private static Map<String, ResourceBundle> resourceBundles;

//    public static ResourceBundle loadBundle(String language) {
//
//        if (resourceBundles == null) {
//            resourceBundles = new HashMap<String, ResourceBundle>();
//        }
//
//        ResourceBundle currentBundle = resourceBundles.get(language);
//
//        if (currentBundle == null) {
//
//            Locale locale = new Locale(language);
//            currentBundle = ResourceBundle.getBundle("l10n.Messages", locale);
//            resourceBundles.put(language, currentBundle);
//        }
//
//        return currentBundle;
//    }
//
//    public static String messageForKey(String key, String language) {
//        ResourceBundle currentBundle = loadBundle(language);
//        return currentBundle.getString(key);
//    }

    private static ResourceBundle resourceBundles;

    public static ResourceBundle getResourceBundle(){
        if (resourceBundles == null){
            Locale locale = new Locale("ru", "RU");
            resourceBundles = ResourceBundle.getBundle("com/finance/l10n/Messages", locale);
        }
        return resourceBundles;
    }
}
