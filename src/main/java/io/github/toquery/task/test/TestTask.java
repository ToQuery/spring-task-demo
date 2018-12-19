package io.github.toquery.task.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

/**
 * @author toquery
 * @version 1
 */
@Slf4j
@Component
public class TestTask implements ApplicationListener<WebServerInitializedEvent> {

    private RuntimeMXBean runtimeBean = ManagementFactory.getRuntimeMXBean();

    private long pid = Long.valueOf(runtimeBean.getName().split("@")[0]);

    private int serverPort = 0;


//    @Scheduled(cron = "0/10 * * * * ? ")
//    @Schedules()
    public void task1() throws InterruptedException {
        log.info("端口 {} , 进程 {} 开始定时任务", serverPort, pid);
        Thread.sleep(5000L);
        log.info("端口 {} , 进程 {} 定时任务结束\n\n", serverPort, pid);
    }

    @Override
    public void onApplicationEvent(WebServerInitializedEvent webServerInitializedEvent) {
        this.serverPort = webServerInitializedEvent.getWebServer().getPort();
    }
}
