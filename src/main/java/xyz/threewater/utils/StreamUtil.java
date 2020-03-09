package xyz.threewater.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StreamUtil {
    public static String getStr(InputStream inputStream) throws IOException {
        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))){
            StringBuilder builder=new StringBuilder();
            String line;
            while ((line=bufferedReader.readLine())!=null){
                builder.append(line);
                builder.append("\n");
            }
            return builder.toString();
        }

    }
}
