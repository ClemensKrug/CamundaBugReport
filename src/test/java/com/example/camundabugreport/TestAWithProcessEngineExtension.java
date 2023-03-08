package com.example.camundabugreport;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.junit5.ProcessEngineExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.assertThat;

/**
 * This test is run first. It uses the ProcessEngineExtension but no Spring-Context.
 *
 * The {@link ProcessEngineExtension} calls {@link org.camunda.bpm.engine.impl.test.TestHelper#getProcessEngine(String)},
 * but since it's static map {@link org.camunda.bpm.engine.impl.test.TestHelper#processEngines} is still empty,
 * a new Engine is created. The created engine is put in {@link org.camunda.bpm.engine.impl.test.TestHelper#processEngines}
 * as well as in {@link org.camunda.bpm.engine.ProcessEngines#processEngines}.
 *
 * It is important that this test does not call any of the assertThat() methods from {@link org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests}.
 * Because of that, {@link org.camunda.bpm.engine.test.assertions.bpmn.AbstractAssertions#processEngine} is never initialized
 * in this test.
 */
@ExtendWith(ProcessEngineExtension.class)
public class TestAWithProcessEngineExtension {

    public static ProcessEngine processEngine;

    @Test
    @Deployment(resources = "testProcess.bpmn")
    public void firstTest() {

        ProcessInstance processInstance = processEngine.getRuntimeService().createProcessInstanceByKey("testProcess")
                .execute();

        // Here we must not call any of the assertThat() methods from BpmnAwareTests.
    }
}
