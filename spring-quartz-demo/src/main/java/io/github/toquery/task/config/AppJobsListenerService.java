package io.github.toquery.task.config;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class AppJobsListenerService implements JobListener {

    @Override
    public String getName() {
        return "Main Listener";
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        log.info("任务 {} 要被执行 ", context.getJobDetail().getKey().getName());
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        log.info("任务 {} 被否决 ", context.getJobDetail().getKey().getName());
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        log.info("任务 {} 被执行了 ", context.getJobDetail().getKey().getName() + (jobException != null ? ", 但出错了 " : ""));
    }
}
