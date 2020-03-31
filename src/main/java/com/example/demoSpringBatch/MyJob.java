package com.example.demoSpringBatch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MyJob extends JobExecutionListenerSupport {

    @Value("${input.file}")
    Resource resource;

    @Autowired
    StepBuilderFactory stepBuilderFactory;

    @Autowired
    JobBuilderFactory jobBuilderFactory;

    @Autowired
    MyProcessor processor;

    @Autowired MyWriter writer;

    @Bean(name = "myNewJob")
    public Job accountKeeperJob() {
        Step step1 = stepBuilderFactory.get("step-1")
                .<User, User> chunk(2)
                .reader(new MyReader(resource))
                .processor(processor)
                .writer(writer)
                .build();

        Job job = jobBuilderFactory.get("accounting-job")
                .incrementer(new RunIdIncrementer())
                .listener(this)
                .start(step1)
                .build();
        return job;
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.debug("BATCH COMPLETED SUCCESSFULLY");
        }

        if (jobExecution.getStatus() == BatchStatus.FAILED) {
            log.error("BATCH FAILED....");
        }
    }
}
