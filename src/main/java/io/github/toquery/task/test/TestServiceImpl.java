package io.github.toquery.task.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class TestServiceImpl implements TestService {
    private final Log logger = LogFactory.getLog(getClass());

    private final Random random = new Random();

    public void run(String id) throws Exception {
        logger.info("运行任务 on task, 任务 id " + id);
        if (random.nextInt(3) == 1) {
            throw new Exception("随机生成任务错误 on task");
        }
        try {
            Thread.sleep(TimeUnit.MINUTES.toMillis(1));
        } catch (InterruptedException e) {
            logger.error("Error", e);
        }
        logger.info("任务完成 on task, 任务 id " + id);
    }
}
