package sample;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

@Singleton
public class ResourceBundleHelper {

    private Locale locale;

    public static final String messages = "messages";

    public static final Map<Locale, ResourceBundle> BUNDLE_MAP = new HashMap<>();

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    static {
        try {
            BUNDLE_MAP.put(Locale.SIMPLIFIED_CHINESE, ResourceBundle.getBundle(messages, Locale.SIMPLIFIED_CHINESE));
            BUNDLE_MAP.put(Locale.US, ResourceBundle.getBundle(messages, Locale.US));
            BUNDLE_MAP.put(Locale.ENGLISH, ResourceBundle.getBundle(messages, Locale.ENGLISH));
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public ResourceBundle resource() {
        return BUNDLE_MAP.containsKey(locale) ? BUNDLE_MAP.get(locale) : ResourceBundle.getBundle(messages, Locale.getDefault());
    }
}
