package com.myLab.corejava.pattern.prototype.after;

import com.myLab.corejava.pattern.prototype.Level;
import com.myLab.corejava.pattern.prototype.MessageQueueClient;

public class MessageQueueLogger extends Logger{
    private MessageQueueClient msgQueueClient;

    public MessageQueueLogger(String name, boolean enabled, Level minPermittedLevel) {
        super(name, enabled, minPermittedLevel);
        MessageQueueClient msgQueueClient = new MessageQueueClient();
    }


    @Override
    protected void doLog(Level level, String message) {
        msgQueueClient.send(message);
    }
}
