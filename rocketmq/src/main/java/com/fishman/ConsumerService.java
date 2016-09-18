package com.fishman;

import java.util.List;

/**
 * Created by hema on 16/9/18.
 */
public abstract class ConsumerService {

    public abstract void start();

    private MqMessageListener listener;

    private List<String> topics;

    public MqMessageListener getListener() {
        return listener;
    }

    public void setListener(MqMessageListener listener) {
        this.listener = listener;
    }

    public List<String> getTopics() {
        return topics;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }
}
