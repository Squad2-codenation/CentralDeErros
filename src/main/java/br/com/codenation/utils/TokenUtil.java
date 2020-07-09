package br.com.codenation.utils;

import java.util.Base64;

public class TokenUtil {

    public static String tokenGenerator(String... params){
        if(params == null){
            return "";
        }

        StringBuilder builder = new StringBuilder();
        for(String param : params){
            builder.append(param);
        }

        return Base64.getEncoder()
                .withoutPadding()
                .encodeToString(builder.toString().getBytes());
    }
}
