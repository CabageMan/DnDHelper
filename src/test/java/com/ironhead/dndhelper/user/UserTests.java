package com.ironhead.dndhelper.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class UserTests {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void getAllUsersUnauthorizedStatusForbidden() throws Exception {
    mockMvc.perform(get("/api/v1/users")).andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser("Blob")
  public void getAllUsersAuthorizedStatusOk() throws Exception {
    mockMvc.perform(get("/api/v1/users")).andExpect(status().isOk());
  }
}
