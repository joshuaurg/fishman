package com.fishman;

import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * Created by hema on 16/9/18.
 */
public interface MqMessageListener {
    public boolean order();
    public MqStatus onMessageReceived(List<MessageExt> messages);
}
