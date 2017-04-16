package top.thoxvi.WarriorsClipboardServer.Tool;

import java.util.Random;

/**
 * 由 Thoxvi 在 2017/4/16编写.
 * 联系方式：Thoxvi@Gmail.com
 */

public class StringTool {
    public static String getRandomString(int n) {
        StringBuilder s = new StringBuilder();
        for (int j = 0; j < n; j++) {
            String STRING_RANDOME_62 = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz0123456789";
            int i = new Random().nextInt(STRING_RANDOME_62.length());
            s.append(STRING_RANDOME_62.charAt(i));
        }
        return s.toString();
    }

    public static String getRandomString() {
        return getRandomString(32);
    }
}
