package org.mb.utils;

import java.io.*;
import java.util.Properties;

public class PropertyUtils {

    public static Properties propertyLoader(String filePath){
        Properties properties = new Properties();
        BufferedReader bufferedReader;
        try{
            bufferedReader = new BufferedReader(new FileReader(filePath));
            properties.load(bufferedReader);
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Properties file not found at: "+filePath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load properties file: "+filePath);
        }
        return properties;
    }
}
