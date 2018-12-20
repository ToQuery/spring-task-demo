package io.github.toquery.task.event;

import io.github.toquery.task.event.InitPullUserTask;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author toquery
 * @version 1
 */
@Component
public class ApplicationEventListener implements ApplicationListener<ContextRefreshedEvent> {

    @Resource
    private InitPullUserTask initPullUserTask;


    @EventListener(ContextRefreshedEvent.class)
    public void appContextStartedEvent(ContextRefreshedEvent event) throws SchedulerException {
        initPullUserTask.addNewJob();
    }


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            initPullUserTask.addNewJob();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
