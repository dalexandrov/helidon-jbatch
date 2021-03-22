package io.helidon.jbatch.example.jobs;


import javax.batch.api.AbstractBatchlet;
import javax.inject.Named;

@Named
public class MyBatchlet extends AbstractBatchlet {

    @Override
    public String process() {
        System.out.println("Running inside a batchlet");

        return "COMPLETED";
    }

}
