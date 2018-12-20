package io.github.toquery.task.demo.controller;

import io.github.toquery.task.demo.entity.AppJobStatus;
import io.github.toquery.task.demo.service.JobsService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
public class TestController {

    @Resource
    private JobsService jobsService;

    @RequestMapping(value = "/jobs", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<String>> addJobs(@RequestParam(defaultValue = "10", required = false) int jobs) throws SchedulerException {
        List<String> ids = jobsService.addNewJobs(jobs);
        return ResponseEntity.status(HttpStatus.OK).body(ids);
    }

    @RequestMapping(value = "/jobs/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<String>> deleteJob(@PathVariable("id") String id) throws SchedulerException {
        return ResponseEntity.status(jobsService.deleteJob(id) ? HttpStatus.OK : HttpStatus.NOT_FOUND).build();
    }

    @RequestMapping(value = "/jobs", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<String>> getJobs() throws SchedulerException {
        List<String> ids = jobsService.getJobs();
        return ResponseEntity.status(HttpStatus.OK).body(ids);
    }

    @RequestMapping(value = "/status/jobs", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<AppJobStatus>> getJobsStatuses() throws SchedulerException {
        List<AppJobStatus> ids = jobsService.getJobsStatuses();
        return ResponseEntity.status(HttpStatus.OK).body(ids);
    }
}
