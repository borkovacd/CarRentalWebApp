package borkovacd.carrentalapp;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import borkovacd.carrentalapp.controller.UserController;
import borkovacd.carrentalapp.service.UserService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = UserController.class)
public class ValidateParametersControllerTest {
	
	//NE RADI TEST

  @Autowired
  private MockMvc mvc;
  
  @Autowired
  private UserService userService;

  @Test
  void whenPathVariableIsInvalid_thenReturnsStatus400() throws Exception {
    mvc.perform(get("/api/users/3"))
            .andExpect(status().isBadRequest());
  }

  /*@Test
  void whenRequestParameterIsInvalid_thenReturnsStatus400() throws Exception {
    mvc.perform(get("/validateRequestParameter")
            .param("param", "3"))
            .andExpect(status().isBadRequest());
  }*/

}