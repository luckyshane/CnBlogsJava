package com.luckyshane.cnblogs.util;

import java.io.IOException;
import java.io.InputStream;

public class StringUtil {

    public static String getEmptyIfNull(String str) {
        return str == null ? "" : str;
    }

    public static String readFromInputStream(InputStream inputStream) {
        byte[] buffer = new byte[2048];
        int readBytes = 0;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            while ((readBytes = inputStream.read(buffer)) > 0) {
                stringBuilder.append(new String(buffer, 0, readBytes));
            }
            return stringBuilder.toString();
        } catch (IOException e) {

        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }


}
