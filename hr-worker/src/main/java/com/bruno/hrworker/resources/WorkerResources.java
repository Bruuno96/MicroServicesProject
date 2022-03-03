package com.bruno.hrworker.resources;


import com.bruno.hrworker.entities.Worker;
import com.bruno.hrworker.repository.WorkerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RefreshScope
@RestController
@RequestMapping("/workers")
public class WorkerResources {

    private static Logger logger = LoggerFactory.getLogger(WorkerResources.class);

    @Value("${test.config}")
    private String testConfig;



    @Autowired
    private WorkerRepository workerRepository;

    @Autowired
    private Environment environment;

    @GetMapping(value = "/configs")
    public ResponseEntity<Void> getConfigs() {
        logger.info("CONFIG = " + testConfig);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<Worker> findById(@PathVariable Long id){

        logger.info("PORT = "+environment.getProperty("local.server.port"));
        Worker found = workerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Object not found"));
        return ResponseEntity.ok().body(found);
    }


    @GetMapping
    public ResponseEntity<List<Worker>> findAll(){
        List<Worker> all = workerRepository.findAll();
        return ResponseEntity.ok().body(all);
    }

}
