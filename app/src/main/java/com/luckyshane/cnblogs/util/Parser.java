package com.luckyshane.cnblogs.util;

import com.blankj.utilcode.util.StringUtils;

public class Parser {


    public static String parseImageUrl(String url) {
        if (!StringUtils.isEmpty(url)) {
            if (url.contains("///")) {
                int index = url.lastIndexOf("///");
                return "http://" + url.substring(index + 3);
            } else if (url.contains("https://") && !url.startsWith("https://")) {
                return url.substring(url.lastIndexOf("https://"));
            } else if (url.startsWith("//")) {
                return "http:" + url;
            }
        }
        return url;
    }


}
