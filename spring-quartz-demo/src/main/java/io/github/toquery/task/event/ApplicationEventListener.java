package io.github.toquery.task.event;

import org.quartz.SchedulerException;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author toquery
 * @version 1
 */
@Component
public class ApplicationEventListener {

    @Resource
    private InitPullUserTask initPullUserTask;


    @EventListener(ContextRefreshedEvent.class)
    public void appContextStartedEvent(ContextRefreshedEvent event) throws SchedulerException {
        initPullUserTask.addNewJob();
    }
}
