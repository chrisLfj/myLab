package com.myLab.corejava.pattern.prototype.before;

import com.myLab.corejava.pattern.prototype.Level;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * 注意FileLogger和MessageQueueLogger两个类，存在重复的属性，并且各自都有一个log方法，该方法中也存在重复的代码
 */
public class FileLogger {
    private String name;
    private boolean enabled;
    private Level minPermittedLevel;
    private String path;
    private Writer writer;

    public FileLogger(String name, boolean enabled, Level minPermittedLevel, String path) {
        this.name = name;
        this.enabled = enabled;
        this.minPermittedLevel = minPermittedLevel;
        this.path = path;
    }

    public void log(Level level, String message){
        boolean loggable = enabled && minPermittedLevel.codeValue() <= level.codeValue();
        if(!loggable)
            return;

        try {
            writer = new FileWriter(path);
            writer.write(message);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
