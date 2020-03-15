package com.myLab.corejava.pattern.prototype.before;

import com.myLab.corejava.pattern.prototype.Level;
import com.myLab.corejava.pattern.prototype.MessageQueueClient;

public class MessageQueueLogger {
    private String name;
    private boolean enabled;
    private Level minPermittedLevel;
    private MessageQueueClient msgQueueClient;

    public MessageQueueLogger(String name, boolean enabled, Level minPermittedLevel) {
        this.name = name;
        this.enabled = enabled;
        this.minPermittedLevel = minPermittedLevel;
        init();
    }

    private void init(){
        MessageQueueClient msgQueueClient = new MessageQueueClient();
    }

    public void log(Level level, String message){
        boolean loggable = enabled && minPermittedLevel.codeValue() <= level.codeValue();
        if(!loggable)
            return;
        msgQueueClient.send(message);
    }
}
