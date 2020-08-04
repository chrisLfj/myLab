package com.myLab.server;

import java.io.Closeable;
import java.io.IOException;

public class StreamUtil {
    public static void close(Closeable closeable){
        if(closeable == null)
            return;
        try {
            closeable.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
