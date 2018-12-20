# spring-task-demo

> 基于 Spring Boot 2 的 quartz 集群定时任务，保证集群环境中同时只运行一个任务


- spring 配置属性类`org.springframework.boot.autoconfigure.quartz.QuartzProperties`
- spring boot 2 自动装配类`org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration`
- 自定义生成实例名称，需要实现`org.quartz.spi.InstanceIdGenerator`接口
- 自定义Job监听事件，需要实现`org.quartz.JobListener`接口



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




