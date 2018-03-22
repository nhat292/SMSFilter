
package com.hoaianh.smsfilter.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.provider.Settings;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Nhat on 12/13/17.
 */


public final class CommonUtils {

    private static final String TAG = "CommonUtils";

    private CommonUtils() {
        // This utility class is not publicly instantiable
    }

    @SuppressLint("all")
    public static String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static boolean isEmailValid(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN =
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static String loadJSONFromAsset(Context context, String jsonFileName)
            throws IOException {

        AssetManager manager = context.getAssets();
        InputStream is = manager.open(jsonFileName);

        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();

        return new String(buffer, "UTF-8");
    }

    public static String getTimeStamp() {
        return new SimpleDateFormat(AppConstants.TIMESTAMP_FORMAT, Locale.US).format(new Date());
    }

    public static boolean isNotAllowNumber(String str) {
        for (char c : str.toCharArray()) {
            if (Character.isDigit(c)) return false;
        }
        return true;
    }

    public static String formatMoney(String money) {
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        return "Rp " + formatter.format(Double.valueOf(money));
    }

    public static String formatAccountNumber(String account) {
        char[] accountArr = account.toCharArray();
        return "" + accountArr[0] + accountArr[1] + accountArr[2]
                + "-" + accountArr[3] + accountArr[4] + accountArr[5]
                + "-" + accountArr[6] + accountArr[7] + accountArr[8];
    }

    public static String showTime(String time) {
        SimpleDateFormat fmt = new SimpleDateFormat("h:mm a");
        String dateString = fmt.format(new Date(Long.valueOf(time)));
        return dateString;
    }

    public static String showTime(Date date) {
        SimpleDateFormat fmt = new SimpleDateFormat("h:mm a");
        String dateString = fmt.format(date);
        return dateString;
    }

    public static String showDate(Date date) {
        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = fmt.format(date);
        return dateString;
    }

    public static String showDateTime(Date date) {
        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy h:mm a");
        String dateString = fmt.format(date);
        return dateString;
    }

    public static String getDateFormat(Date date) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String dateString = fmt.format(date);
        return dateString;
    }

    public static String getDateString(Date date) {
        SimpleDateFormat fmt = new SimpleDateFormat("ddMMyyyy_HHmmss");
        String dateString = fmt.format(date);
        return dateString;
    }

    public static void setClipboard(Context context, String text) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(text);
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", text);
            clipboard.setPrimaryClip(clip);
        }
    }

    public static boolean isExpired() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, 2);
        calendar.set(Calendar.DAY_OF_MONTH, 8);
        if (calendar.getTime().getTime() <= date.getTime()) {
            return true;
        }
        return false;
    }
}
