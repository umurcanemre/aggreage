package com.umurcan.aggregate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CounterController.class)
public class CounterControllerTest {

  @Autowired private MockMvc mockMvc;

  @Test
  void shouldCountWithStaticId() throws Exception {
    var responseStr =
        mockMvc
            .perform(get("/"))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

    var response =
        new ObjectMapper()
            .readValue(responseStr, CounterController.IdentifiedCounterResponse.class);

    var instanceId = response.instanceIdentifier();

    assertThat(response.counter()).isEqualTo(0);
    assertThat(response.instanceIdentifier()).isEqualTo(instanceId);

    for (int i = 0; i < 4; i++) {
      responseStr = mockMvc.perform(get("/")).andReturn().getResponse().getContentAsString();
      response =
          new ObjectMapper()
              .readValue(responseStr, CounterController.IdentifiedCounterResponse.class);

      assertThat(response.counter()).isEqualTo(1 + i);
      assertThat(response.instanceIdentifier()).isEqualTo(instanceId);
    }
  }
}
