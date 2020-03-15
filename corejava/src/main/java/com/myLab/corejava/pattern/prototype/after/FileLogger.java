package com.myLab.corejava.pattern.prototype.after;

import com.myLab.corejava.pattern.prototype.Level;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class FileLogger extends Logger{
    private String path;
    private Writer writer;

    public FileLogger(String name, boolean enabled, Level minPermittedLevel, String path) throws IOException {
        super(name, enabled, minPermittedLevel);
        writer = new FileWriter(path);
    }

    /**
     * 子类必须实现抽象父类的abstract方法，封装自己的实现
     * @param level
     * @param message
     */
    @Override
    protected void doLog(Level level, String message) {
        try {
            writer.write(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
