//package es.udc.paproject.backend.test.model.controller;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//
//import java.util.Arrays;
//import java.util.List;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.web.util.NestedServletException;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.ObjectWriter;
//import com.fasterxml.jackson.databind.SerializationFeature;
//
//import es.udc.paproject.backend.model.entities.User;
//import es.udc.paproject.backend.model.entities.User.RoleType;
//import es.udc.paproject.backend.model.services.UserService;
//import es.udc.paproject.backend.rest.controllers.UserController;
//import es.udc.paproject.backend.rest.dtos.UserDto;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@AutoConfigureMockMvc
////@WebMvcTest(UserController.class)
//public class UserControllerTest {
//
//	@Autowired
//    private MockMvc mockMvc;
// 
//    @MockBean
//    private UserService userService;
//    
//	public User createUser() {
//		User user = new User();
//		user.setFirstName("fName");
//		user.setLastName("lName");
//		user.setUserName("user");
//		user.setPassword("password");
//		user.setEmail("user@user.com");
//		user.setRole(RoleType.USER);
//
//		return user;
//	}
//	
//	@Test
//	public void testLoginSuccessful() throws Throwable {
//
//		UserDto userDto = new UserDto();
//		
//		userDto.setFirstName("fName");
//		userDto.setLastName("lName");
//		userDto.setUserName("user");
//		userDto.setPassword("password");
//		userDto.setEmail("user@user.com");
//		
//	    String json = "{\"userName\":\"user1\"," + 
//	    			  "\"password\":\"user1\"," + 
//	    			  "\"firstName\":\"user1\"," + 
//	    			  "\"lastName\":\"ap1 ap2\"," + 
//	    			  "\"email\":\"user1@udc.es\"" + 
//	    			  "}";
//	    ObjectMapper mapper = new ObjectMapper();
//	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
//	    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
//	    String requestJson=ow.writeValueAsString(userDto);
//	    try {
//	    	mockMvc.perform(post("/users/signUp").contentType(MediaType.APPLICATION_JSON).
//					content(requestJson)).andExpect(status().isOk());
//	    } catch (NestedServletException e) {
//	        throw e.getCause();
//	    }
//		//mockMvc.perform(post("/users/signUp").contentType(MediaType.APPLICATION_JSON).flashAttr("userDto", userDto))
//				//.andExpect(status().isBadRequest());
//
//		//mockMvc.perform(post("/login").param("message", "test"))
//			//	.andExpect(status().is2xxSuccessful());
//
//	}
//}
