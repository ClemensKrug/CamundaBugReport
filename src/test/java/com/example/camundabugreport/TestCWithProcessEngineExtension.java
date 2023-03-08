package com.example.camundabugreport;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.impl.test.TestHelper;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.assertions.bpmn.AbstractAssertions;
import org.camunda.bpm.engine.test.junit5.ProcessEngineExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.assertThat;

/**
 * Now the third test is executed. Again the {@link ProcessEngineExtension} calls
 * {@link org.camunda.bpm.engine.impl.test.TestHelper#getProcessEngine(String)}, but since
 * {@link org.camunda.bpm.engine.impl.test.TestHelper#processEngines} was never cleared, an engine is
 * successfully returned. At this point however, the engine is no longer present in {@link org.camunda.bpm.engine.ProcessEngines#processEngines} !!!
 *
 * Lastly, we call {@link org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests#assertThat(ProcessInstance)} which
 * in return calls {@link AbstractAssertions#processEngine()}. {@link AbstractAssertions#processEngine} is still null,
 * as it hasn't been initialized in the first test. However, {@link ProcessEngines#getProcessEngines()} return an empty list,
 * since it was cleaned up by SpringBoot. Finally, we get an exception, that 'No ProcessEngine found to be registered with ...'.
 *
 *
 * This could e.g. be solved if {@link ProcessEngineExtension#afterAll(ExtensionContext)} called {@link TestHelper#closeProcessEngines()},
 * or if the {@link TestHelper} didn't store it's own map of ProcessEngines.
 */
@ExtendWith(ProcessEngineExtension.class)
public class TestCWithProcessEngineExtension {

    public static ProcessEngine processEngine;

    @Test
    @Deployment(resources = "testProcess.bpmn")
    public void thirdTest() {

        ProcessInstance processInstance = processEngine.getRuntimeService().createProcessInstanceByKey("testProcess")
                .execute();

        assertThat(processInstance).hasPassed("EndEvent_1");
    }
}
