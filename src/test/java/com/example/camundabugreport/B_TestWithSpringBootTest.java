package com.example.camundabugreport;


import org.camunda.bpm.engine.spring.ProcessEngineFactoryBean;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

/**
 * This test is run second. The important part here is the {@link DirtiesContext}.
 * As SpringBoot cleans up the context, {@link ProcessEngineFactoryBean#destroy()} is called, which
 * unregisters the engine from {@link org.camunda.bpm.engine.ProcessEngines#processEngines}.
 * However, {@link org.camunda.bpm.engine.impl.test.TestHelper#processEngines} is not cleared! (and should not, since
 * it doesn't have anything to do with SpringBoot).
 */
@SpringBootTest(classes = CamundaBugReportApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class B_TestWithSpringBootTest {

    @Test
    public void secondTest() {
        System.out.println("Doesn't matter what we do here as long as the SpringContext is build and cleaned up");
    }
}
