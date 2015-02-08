package ru.javawebinar.webapp.util;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Reader;
import java.io.Writer;

/**
 * User: gkislin
 * Date: 30.10.13
 */
public class JsonParser {
    private static Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    //new Gson();

    public static <T> T read(Reader reader, Class<T> clazz) {
        return GSON.fromJson(reader, clazz);
    }

    public static <T> void write(T object, Writer writer) {
        GSON.toJson(object, writer);
    }
}
