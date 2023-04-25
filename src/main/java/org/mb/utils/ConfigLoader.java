package org.mb.utils;

import java.io.File;
import java.util.Properties;

public class ConfigLoader {

    private final Properties properties;
    private static ConfigLoader configLoader;

    private ConfigLoader(){
        properties = PropertyUtils.propertyLoader("src" + File.separator + "main"  +
                File.separator + "resources" + File.separator + "config.properties");
    }

    public static ConfigLoader getInstance(){
        if(configLoader == null){
            configLoader = new ConfigLoader();
        }
        return configLoader;
    }

    public String getBaseUrl(){
        return properties.getProperty("baseURL");
    }

    public long getAClassMinimumPrice(){
        return Long.parseLong(properties.getProperty("aClassMinimumPrice"));
    }

    public long getAClassMaximumPrice(){
        return Long.parseLong(properties.getProperty("aClassMaximumPrice"));
    }

    public long getFluentWaitPollingInterval(){
        return Long.parseLong(properties.getProperty("fluentWaitPolling"));
    }

    public long getWaitInterval(){
        return Long.parseLong(properties.getProperty("waitInterval"));
    }

}
