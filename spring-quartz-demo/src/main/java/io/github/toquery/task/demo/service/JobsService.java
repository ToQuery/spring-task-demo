package io.github.toquery.task.demo.service;

import io.github.toquery.task.config.AppJobsListenerService;
import io.github.toquery.task.demo.entity.AppJobStatus;
import io.github.toquery.task.demo.task.AppBaseJob;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.utils.Key;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

@Service
public class JobsService {
    private final String groupName = "normal-group";

    private final Scheduler scheduler;

    @Autowired
    public JobsService(SchedulerFactoryBean schedulerFactory) {
        this.scheduler = schedulerFactory.getScheduler();
    }

    public List<String> addNewJobs(int jobs) throws SchedulerException {
        LinkedList<String> list = new LinkedList<>();
        for (int i = 0; i < jobs; i++) {
            list.add(addNewJob());
        }
        return list.stream().sorted(Comparator.naturalOrder()).collect(Collectors.toList());
    }

    public String addNewJob() throws SchedulerException {
        String id = UUID.randomUUID().toString();
        // http://www.quartz-scheduler.org/documentation/quartz-2.2.x/configuration/ConfigJDBCJobStoreClustering.html
        // https://stackoverflow.com/a/19270566/285571
        JobDetail job =
                newJob(AppBaseJob.class)
                        .withIdentity(id, groupName)

                        .requestRecovery(true)
                        .build();

        Trigger trigger =
                newTrigger()
                        .withIdentity(id + "-trigger", groupName)
                        .startNow()
                        .withSchedule(
                                simpleSchedule().withIntervalInSeconds(30)
//                                CronScheduleBuilder.cronSchedule("0/5 * * * * ?")
                        )
                        .build();
        scheduler.getListenerManager().addJobListener(new AppJobsListenerService());
        //Listener attached to jobKey
//        scheduler.getListenerManager().addJobListener(
//                new HelloJobListener(), KeyMatcher.keyEquals(jobKey)
//        );

        //Listener attached to group named "group 1" only.
        //scheduler.getListenerManager().addJobListener(
        //	new HelloJobListener(), GroupMatcher.jobGroupEquals("group1")
        //);

        scheduler.scheduleJob(job, trigger);

        return id;
    }

    public boolean deleteJob(String id) throws SchedulerException {
        JobKey jobKey = new JobKey(id, groupName);
        return scheduler.deleteJob(jobKey);
    }

    public List<String> getJobs() throws SchedulerException {
        return scheduler
                .getJobKeys(GroupMatcher.jobGroupEquals(groupName))
                .stream()
                .map(Key::getName)
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());
    }

    /**
     * Check realization was inspired by https://stackoverflow.com/a/31479434/285571
     */
    public List<AppJobStatus> getJobsStatuses() throws SchedulerException {
        LinkedList<AppJobStatus> list = new LinkedList<>();
        for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobDetail.getKey());
            for (Trigger trigger : triggers) {
                Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                if (Trigger.TriggerState.COMPLETE.equals(triggerState)) {
                    list.add(new AppJobStatus(jobKey.getName(), true));
                } else {
                    list.add(new AppJobStatus(jobKey.getName(), false));
                }
            }
        }
        list.sort(Comparator.comparing(AppJobStatus::getId));
        return list;
    }
}
