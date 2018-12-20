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
