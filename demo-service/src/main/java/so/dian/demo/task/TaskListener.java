/*
 * Dian.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package so.dian.demo.task;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

/**
 * CMQ任务消息消费Listener
 *
 * @author ${baizhang}
 * @version $Id: TaskListener.java, v 0.1 2018-10-30 下午17:15 Exp $
 */
public class TaskListener implements ApplicationListener<ApplicationReadyEvent> {
    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        PoolTask task = (PoolTask) event.getApplicationContext().getBean("poolTask");
        task.run();

    }
}