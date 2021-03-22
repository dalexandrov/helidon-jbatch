package io.helidon.jbatch.example;

import com.ibm.jbatch.spi.ExecutorServiceProvider;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HelidonExecutorServiceProvider implements ExecutorServiceProvider {
    @Override
    public ExecutorService getExecutorService() {
        return Executors.newFixedThreadPool(2);
    }
}
