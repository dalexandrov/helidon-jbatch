package io.helidon.jbatch.example;

import com.ibm.jbatch.spi.BatchSPIManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import javax.batch.operations.JobOperator;
import javax.batch.runtime.JobExecution;
import javax.batch.runtime.StepExecution;
import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import static javax.batch.runtime.BatchRuntime.getJobOperator;


@Path("/batch")
public class BatchResource {
    private static final JsonBuilderFactory JSON = Json.createBuilderFactory(Collections.emptyMap());



    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject executeBatch() throws Exception{

        BatchSPIManager batchSPIManager = BatchSPIManager.getInstance();
        batchSPIManager.registerPlatformMode(BatchSPIManager.PlatformMode.SE);
        batchSPIManager.registerExecutorServiceProvider(new HelidonExecutorServiceProvider());

        JobOperator jobOperator = getJobOperator();
        Long executionId = jobOperator.start("myJob", new Properties());
        Thread.sleep(3000);
        JobExecution jobExecution = jobOperator.getJobExecution(executionId);


        List<StepExecution> stepExecutions = jobOperator.getStepExecutions(executionId);
        List<String> executedSteps = new ArrayList<>();
        for (StepExecution stepExecution : stepExecutions) {
            executedSteps.add(stepExecution.getStepName());
        }


        return JSON.createObjectBuilder()
                .add("Steps executed", Arrays.toString(executedSteps.toArray()))
                .add("Status", jobExecution.getBatchStatus().toString())
                .build();
    }
}
