# spring-task-demo

> 基于 Spring Boot 2 的 quartz 集群定时任务，保证集群环境中同时只运行一个任务


- spring 配置属性类`org.springframework.boot.autoconfigure.quartz.QuartzProperties`
- spring boot 2 自动装配类`org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration`
- 自定义生成实例名称，需要实现`org.quartz.spi.InstanceIdGenerator`接口
- 自定义Job监听事件，需要实现`org.quartz.JobListener`接口

# table 信息


| 表名                      | 说明                                                         |
| ------------------------- | ------------------------------------------------------------ |
| qrtz_blob_triggers        | Trigger作为Blob类型存储(用于Quartz用户用JDBC创建他们自己定制的Trigger类型，JobStore 并不知道如何存储实例的时候) |
| qrtz_calendars            | 以Blob类型存储Quartz的Calendar日历信息， quartz可配置一个日历来指定一个时间范围 |
| qrtz_cron_triggers        | 存储Cron Trigger，包括Cron表达式和时区信息。                 |
| qrtz_fired_triggers       | 存储与已触发的Trigger相关的状态信息，以及相联Job的执行信息   |
| qrtz_job_details          | 存储每一个已配置的Job的详细信息                              |
| qrtz_locks                | 存储程序的非观锁的信息(假如使用了悲观锁)                     |
| qrtz_paused_trigger_graps | 存储已暂停的Trigger组的信息                                  |
| qrtz_scheduler_state      | 存储少量的有关 Scheduler的状态信息，和别的 Scheduler 实例(假如是用于一个集群中) |
| qrtz_simple_triggers      | 存储简单的 Trigger，包括重复次数，间隔，以及已触的次数       |
| qrtz_triggers             | 存储已配置的 Trigger的信息                                   |
| qrzt_simprop_triggers     | 存储已配置的 Trigger的信息                                   |

# 表字段详细介绍

[表字段详细介绍](http://www.ibloger.net/article/2650.html)


# Spring Quartz 配置


```yaml
spring:
  profiles:
    active: dev
  ## QuartzProperties
  quartz:
    jdbc:
      initialize-schema: embedded
    job-store-type: jdbc
    properties:
      # Configure Main Scheduler Properties
      # 调度器实例名称
      org.quartz:scheduler.instanceName: spring-boot-quartz-cluster-example
      # 调度器实例编号自动生成
      org.quartz.scheduler.instanceId: AUTO
      
      # Configure JobStore
      # 持久化方式配置
      org.quartz.jobStore.class: org.quartz.impl.jdbcjobstore.JobStoreTX
      # 持久化方式配置数据驱动，MySQL数据库
      org.quartz.jobStore.driverDelegateClass: org.quartz.impl.jdbcjobstore.StdJDBCDelegate
      # 配置是否使用
      org.quartz.jobStore.useProperties: true
      org.quartz.jobStore.misfireThreshold: 60000
      # quartz相关数据表前缀名
      #org.quartz.jobStore.tablePrefix: QRTZ_
      # 打开集群配置
      org.quartz.jobStore.isClustered: true
      # 设置集群检查间隔20s
      org.quartz.jobStore.clusterCheckinInterval: 20000

      # Configure ThreadPool
      # 线程池实现类
      org.quartz.threadPool.class: org.quartz.simpl.SimpleThreadPool
      # 执行最大并发线程数量
      org.quartz.threadPool.threadCount: 10
      # 线程优先级
      org.quartz.threadPool.threadPriority: 5
      #配置为守护线程，设置后任务将不会执行
      #org.quartz.threadPool.makeThreadsDaemons=true
      #配置是否启动自动加载数据库内的定时任务，默认true
      org.quartz.threadPool.threadsInheritContextClassLoaderOfInitializingThread: true
```





