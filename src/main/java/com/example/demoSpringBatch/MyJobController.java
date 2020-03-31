package com.example.demoSpringBatch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyJobController {
    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    @Qualifier("myNewJob")
    Job myJob;

    @RequestMapping("/run-batch")
    public String handle(@RequestParam(value="id", required = false, defaultValue = "1") String id) throws Exception {

        JobParameters jobParameters = new JobParametersBuilder()
                .addString(id, "Spring Boot")
                .toJobParameters();
        jobLauncher.run(myJob, jobParameters);
        return "Batch started";
    }
}
