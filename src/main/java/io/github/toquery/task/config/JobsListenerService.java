package io.github.toquery.task.config;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class JobsListenerService implements JobListener {

    @Override
    public String getName() {
        return "Main Listener";
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        log.info("Job to be executed " + context.getJobDetail().getKey().getName());
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        log.info("Job execution vetoed " + context.getJobDetail().getKey().getName());
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        log.info("Job was executed " + context.getJobDetail().getKey().getName() + (jobException != null ? ", with error" : ""));
    }
}
