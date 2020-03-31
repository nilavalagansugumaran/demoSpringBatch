package com.example.demoSpringBatch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Slf4j
public class MyWriter implements ItemWriter<User> {

    @Autowired
    private UserRepository repo;

    @Override
    @Transactional
    public void write(List<? extends User> users) throws Exception {
        log.debug("Processing {} entries",  users.size());
        repo.saveAll(users);
    }
}
