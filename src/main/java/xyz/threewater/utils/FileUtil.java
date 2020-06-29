package xyz.threewater.utils;

import xyz.threewater.exception.UnsupportedFileException;

import java.io.*;

public class FileUtil {
    public static String file2String(File file){
        if(file.isDirectory())
            throw new UnsupportedFileException("不能打开一个目录");
        try (
            FileInputStream fileInputStream = new FileInputStream(file);
            Reader streamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader=new BufferedReader(streamReader)
        ) {
            StringBuilder builder=new StringBuilder();
            String strLine=bufferedReader.readLine();
            while (strLine!=null){
                builder.append(strLine);
                builder.append("\n");
                strLine=bufferedReader.readLine();
            }
            return builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
            throw new UnsupportedFileException(e);
        }
    }

    public static void saveFile(String content,File file) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(file,false));
        writer.write(content);
        writer.close();
    }

    public static void saveFile(String content,String path) throws IOException {
        saveFile(content,new File(path));
    }

    public static void isEmpty(String filePath){
    }

    public static boolean exist(String filePath){
        return new File(filePath).exists();
    }
}
