package io.github.toquery.task.event;

import io.github.toquery.task.config.AppJobsListenerService;
import io.github.toquery.task.demo.task.AppJobPullUserInfo;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;
import java.util.UUID;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * @author toquery
 * @version 1
 */
@Component
public class InitPullUserTask {

    private static final String GROUP = "PULL_USER";

    @Resource
    private Scheduler scheduler;

    public String addNewJob() throws SchedulerException {
        Set<JobKey> jobKeySet = scheduler.getJobKeys(GroupMatcher.jobGroupEquals(GROUP));
        if (jobKeySet != null && jobKeySet.size() > 0) {
            return null;
        }
        String id = UUID.randomUUID().toString();
        // http://www.quartz-scheduler.org/documentation/quartz-2.2.x/configuration/ConfigJDBCJobStoreClustering.html
        // https://stackoverflow.com/a/19270566/285571
        JobDetail job =
                newJob(AppJobPullUserInfo.class)
                        .withIdentity(id, GROUP)
                        .requestRecovery(true)
                        .build();

        Trigger trigger =
                newTrigger()
                        .withIdentity(id + "-trigger", GROUP)
                        .startNow()
                        .withSchedule(
                                CronScheduleBuilder.cronSchedule("0/5 * * * * ?")
                        )
                        .build();
        scheduler.getListenerManager().addJobListener(new AppJobsListenerService());
        scheduler.scheduleJob(job, trigger);
        return id;
    }
}
