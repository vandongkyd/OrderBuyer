package com.fernandocejas.android10.order.presentation.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.Spanned;

import com.fernandocejas.android10.order.domain.Country;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 *
 *
 */

public class Utils {
    private static final String TAG = "Utils";

    public static String formatDecimal(String value) {
        if (value == null) {
            return null;
        }
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
        DecimalFormat df = (DecimalFormat) nf;
        df.applyPattern("#,###,##0.00");
        return df.format(Double.valueOf(value));
    }

    /**
     * Checks if the device has any active internet connection.
     *
     * @return true device with internet connection, otherwise false.
     */
    public static boolean isThereInternetConnection(final Context context) {
        boolean isConnected;

        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        isConnected = (networkInfo != null && networkInfo.isConnectedOrConnecting());

        return isConnected;
    }

    public static String getUserCountryFromDevices(Context context) {
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = context.getApplicationContext().getResources().getConfiguration().getLocales().get(0);
        } else {
            locale = context.getApplicationContext().getResources().getConfiguration().locale;
        }
        return getCountryCodeFromISO(context, locale.getCountry());
    }

    /**
     * Get ISO 3166-1 alpha-2 country code for this device (or null if not
     * available)
     *
     * @return country code or null
     */
    public static String getUserCountryFromSimOrNetwork(final Context context) {
        try {
            final TelephonyManager tm = (TelephonyManager) context.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
            final String simCountry = tm.getSimCountryIso();
            if (simCountry != null && simCountry.length() == 2) {
                return getCountryCodeFromISO(context, simCountry);
            } else if (tm.getPhoneType() != TelephonyManager.PHONE_TYPE_CDMA) {
                String networkCountry = tm.getNetworkCountryIso();
                if (networkCountry != null && networkCountry.length() == 2) {
                    return getCountryCodeFromISO(context, networkCountry);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String getCountryCodeFromISO(final Context context, String isoCode) {

        for (Country country : Constants.COUNTRIES.values()) {
            if (country.getCode().equals(isoCode.trim())) {
                return country.getDial_code().replace("+", "");
            }
        }
        return "";
    }

    public static String getISOFromCountryZipCode(Context context, String CountryZipCode) {

        for (Country country : Constants.COUNTRIES.values()) {
            if (country.getDial_code().replace("+", "").equals(CountryZipCode.trim())) {
                return country.getCode();
            }
        }
        return "";
    }

    public static String getCurrencySymbol(String currency_code) {
        if (currency_code == null || currency_code.isEmpty()) {
            return "|";
        }
        return Constants.COUNTRIES.get(currency_code).getCurrency_symbol();
    }

    public static String getTimeByFormat(String time) {
        try {
            Date date = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(time.replaceAll("Z$", "+0000"));
            long miliTimes = date.getTime();
            return getDateTime(miliTimes, "yyyy-MM-dd");
        } catch (Exception e) {
        }
        return null;
    }

    public static String getTimeAgos(String dtStart) {

        try {
            String result = "";
            Date date = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(dtStart.replaceAll("Z$", "+0000"));
            long miliTimes = date.getTime();
            Date now = new Date();
            long seconds = TimeUnit.MILLISECONDS.toSeconds(now.getTime() - miliTimes);
            long minutes = TimeUnit.MILLISECONDS.toMinutes(now.getTime() - miliTimes);
            long hours = TimeUnit.MILLISECONDS.toHours(now.getTime() - miliTimes);
            long days = TimeUnit.MILLISECONDS.toDays(now.getTime() - miliTimes);
            if (seconds >= 0 && seconds < 59) {
                result = "Just now";
            } else if (minutes < 60) {
                result = minutes + " mins";
            } else if (hours < 24) {
                result = hours + " hours";
            } else {
                /*if (days == 1) {
                    result = "Yesterday at " + getDateTime(miliTimes, "HH:mm");
                } else if (days <= 7) {
                    result = days + " days ago";
                } else {*/
                result = getDateTime(miliTimes, "yyyy-MM-dd");
                /*}*/
            }
            return result;
        } catch (Exception j) {
            j.printStackTrace();
        }
        return null;
    }

    public static String getDateTime(Long timeStamp, String format) {
        try {
            DateFormat sdf = new SimpleDateFormat(format);
            Date netDate = (new Date(timeStamp));
            return sdf.format(netDate);
        } catch (Exception ignored) {
            return "";
        }
    }

    public static void openDefaultBrowser(Context context, String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(browserIntent);
    }

    public static boolean isGooglePlayServicesAvailable(Context context) {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int status = googleAPI.isGooglePlayServicesAvailable(context);
        return status == ConnectionResult.SUCCESS;
    }

    /**
     * Checks if the app has permission to write to device storage
     * <p>
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    public static boolean verifyPermissions(Activity activity, String[] permissions) {

        for (String str : permissions) {

            // Check if we have write permission
            int permission = ActivityCompat.checkSelfPermission(activity, str);

            if (permission != PackageManager.PERMISSION_GRANTED) {
                // We don't have permission so prompt the user
                return false;
            }
        }
        return true;
    }

    public static Spanned fromHtml(String source) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY);
        }
        //noinspection deprecation
        return Html.fromHtml(source);
    }

}
