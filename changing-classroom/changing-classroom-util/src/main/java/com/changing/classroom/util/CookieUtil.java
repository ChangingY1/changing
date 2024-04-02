package com.changing.classroom.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

@Component
public class CookieUtil {

    /***
     * @param request
     * @param cookieName
     * @return
     */
    public static String getCookieValue(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookieName == null) {
            return null;
        }
        String retValue = null;
        for (int i = 0; i < cookies.length; i++) {
            if (cookies[i].getName().equals(cookieName)) {
                retValue = cookies[i].getValue();
                return retValue;
            }
        }
        return retValue;
    }

    /***
     * 设置cookie的值
     * @param response
     * @param cookieName
     * @param cookieValue
     * @param cookieMaxage
     */
    public static void setCookie(
                                 HttpServletResponse response,
                                 String cookieName,
                                 String cookieValue,
                                 int cookieMaxage
                                 ) {
        try {
            if (cookieValue == null) {
                cookieValue = "";
            }
            Cookie cookie = new Cookie(cookieName, cookieValue);
            if (cookieMaxage >= 0)
                cookie.setMaxAge(cookieMaxage);
            // 在域名的根路径下保存
            cookie.setPath("/");
            response.addCookie(cookie);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /***
     * 将cookie中的内容按照key删除
     * @param response
     * @param cookieName
     */
    public static void deleteCookie(HttpServletResponse response, String cookieName) {
        setCookie( response, cookieName, null, 0);
    }

}

