package com.finalproject.secondhand.config.utils;

import javax.servlet.http.HttpServletRequest;

public class SiteUrl {

    public static String getSiteURL(HttpServletRequest request) {
        String siteURL =request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }
}
