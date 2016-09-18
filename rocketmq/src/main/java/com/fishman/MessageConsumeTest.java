package com.fishman;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.runner.RunWith;

/**
 * Created by hema on 16/9/18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:application.xml")
public class MessageConsumeTest {
    @Test
    public void testConsumer(){
        System.out.print("启动测试...");
    }
}
