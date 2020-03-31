package com.example.demoSpringBatch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class MyJobSchedule {

    private static int count = 1000;

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    @Qualifier("myNewJob")
    Job myJob;

    @Scheduled(cron = "0 */1 * * * ?")
    //@Scheduled(fixedRate = 60)
    public void readFileSchedular() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString(Integer.toString(count), "Spring Boot")
                .toJobParameters();
        count++;
                jobLauncher.run(myJob, jobParameters);
        log.debug("Job started at  {}",  new Date().toString() );
    }
}
