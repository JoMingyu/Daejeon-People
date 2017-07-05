package com.daejeonpeople.support.network;

import android.content.Context;
import android.content.pm.PackageInstaller;

import com.androidquery.callback.AjaxStatus;
import com.daejeonpeople.support.database.DBHelper;

import org.apache.http.cookie.Cookie;

import java.util.List;

/**
 * Created JoMingyu on 2017-07-04.
 */

public class SessionManager {
    private AjaxStatus status;

    public SessionManager(AjaxStatus status) {
        this.status = status;
    }

    public void setAjaxStatus(AjaxStatus status) {
        this.status = status;
    }

    public String detectCookie(String key) {
        List<Cookie> cookies = status.getCookies();
        if (!cookies.contains(key)) {
            return null;
        } else {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(key)) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }

    public static String getCookieFromDB(Context ctx) {
        DBHelper helper = DBHelper.getInstance(ctx, "CHECK.db", null, 1);
        return helper.getCookie();
    }

    public static void setCookieToDB(Context ctx, String cookie) {
        DBHelper helper = DBHelper.getInstance(ctx, "CHECK.db", null, 1);
        helper.setCookie(cookie);
    }
}
