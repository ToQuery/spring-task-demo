package io.github.toquery.task.demo.task;

import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 模拟获取用户信息的操作
 */
@Slf4j
@DisallowConcurrentExecution
public class AppJobPullUserInfo implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String id = jobExecutionContext.getJobDetail().getKey().getName();
        log.info("执行开始 Job - {} - 任务ID为 {}", this.getClass().getName(), id);
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("执行结束 Job - {} - 任务ID为 {}", this.getClass().getName(), id);
    }

}
