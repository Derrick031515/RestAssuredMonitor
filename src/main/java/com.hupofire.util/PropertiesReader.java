package com.hupofire.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * 读取 properties 文件的工具类
 *
 * @author Derrick
 * @version 1.0.0
 * @date 2021/1/22
 */

public class PropertiesReader {
    /**
     * 配置文件
     */
    private static final Properties PROPERTIES = new Properties();

    /**
     * 私有化构造器，不允许外部构造
     */
    private PropertiesReader() {}

    /**
     * 读取 properties 测试项目配置文件
     *
     * @param propertiesPath 配置文件路径
     * @return PROPERTIES
     * @throws IOException IOException
     */
    public static Properties readProperties(String propertiesPath) throws IOException {
        InputStream inputStream = new FileInputStream(propertiesPath);
        // 读取配置文件通过 utf-8 编码方式读取
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        PROPERTIES.load(bufferedReader);
        return PROPERTIES;
    }

    /**
     * 依据键名获取配置文件中的键值
     *
     * @param key 键名
     * @return 键值
     */
    public static String getKey(String key) {
        return PROPERTIES.getProperty(key);
    }
}