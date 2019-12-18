package es.udc.paproject.backend.test.model.services;

import static org.junit.Assert.assertEquals;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import es.udc.paproject.backend.model.common.exceptions.DuplicateInstanceException;
import es.udc.paproject.backend.model.common.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.entities.User;
import es.udc.paproject.backend.model.services.IncorrectLoginException;
import es.udc.paproject.backend.model.services.IncorrectPasswordException;
import es.udc.paproject.backend.model.services.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class UserServiceTest {
	
	private final Long NON_EXISTENT_ID = new Long(-1);
	
	@Autowired
	private UserService userService;
	
	private User createUser(String userName) {
		return new User(userName, "password", "firstName", "lastName", userName + "@" + userName + ".com");
	}
	
	@Test
	public void testSignUpAndLoginFromId() throws DuplicateInstanceException, InstanceNotFoundException {
		
		User user = createUser("user");
		
		userService.signUp(user);
		
		User loggedInUser = userService.loginFromId(user.getId());
		
		assertEquals(user, loggedInUser);
		assertEquals(User.RoleType.USER, user.getRole());
		
	}
	
	@Test(expected = DuplicateInstanceException.class)
	public void testSignUpDuplicatedUserName() throws DuplicateInstanceException {
		User user = createUser("user");
		DuplicateInstanceException exn = new DuplicateInstanceException("project.entities.user", user.getUserName());

		userService.signUp(user);
		try {
			user.setUserName("user");
			userService.signUp(user);
		} catch (DuplicateInstanceException e) {
			assertEquals(exn.getName(), e.getName());
			assertEquals(exn.getKey(), e.getKey());
			throw e;
		}
	}
	
	@Test(expected = InstanceNotFoundException.class)
	public void testloginFromNonExistentId() throws InstanceNotFoundException {		
		userService.loginFromId(NON_EXISTENT_ID);
	}
	
	@Test
	public void testLogin() throws DuplicateInstanceException, IncorrectLoginException {
		
		User user = createUser("user");
		String clearPassword = user.getPassword();
				
		userService.signUp(user);
		
		User loggedInUser = userService.login(user.getUserName(), clearPassword);
		
		assertEquals(user, loggedInUser);
		
	}
	
	@Test(expected = IncorrectLoginException.class)
	public void testLoginWithIncorrectPassword() throws DuplicateInstanceException, IncorrectLoginException {
		
		User user = createUser("user");
		String clearPassword = user.getPassword();
		
		userService.signUp(user);
		try {
			userService.login(user.getUserName(), 'X' + clearPassword);
		} catch (IncorrectLoginException e){
			assertEquals(e.getUserName(), user.getUserName());
			assertEquals(e.getPassword(), 'X' + clearPassword);
			throw e;
		}
	}
	
	@Test(expected = IncorrectLoginException.class)
	public void testLoginWithNonExistentUserName() throws IncorrectLoginException {
		userService.login("X", "Y");
	}
	
	@Test
	public void testUpdateProfile() throws InstanceNotFoundException, DuplicateInstanceException {
		
		User user = createUser("user");
		
		userService.signUp(user);
		
		user.setFirstName('X' + user.getFirstName());
		user.setLastName('X' + user.getLastName());
		user.setEmail('X' + user.getEmail());
		
		userService.updateProfile(user.getId(), 'X' + user.getFirstName(), 'X' + user.getLastName(),
			'X' + user.getEmail());
		
		User updatedUser = userService.loginFromId(user.getId());
		
		assertEquals(user, updatedUser);
		
	}
	
	@Test(expected = InstanceNotFoundException.class)
	public void testUpdateProfileWithNonExistentId() throws InstanceNotFoundException {		
		userService.updateProfile(NON_EXISTENT_ID, "X", "X", "X");
	}
	
	@Test
	public void testChangePassword() throws DuplicateInstanceException, InstanceNotFoundException,
		IncorrectPasswordException, IncorrectLoginException {
		
		User user = createUser("user");
		String oldPassword = user.getPassword();
		String newPassword = 'X' + oldPassword;
		
		userService.signUp(user);
		userService.changePassword(user.getId(), oldPassword, newPassword);
		userService.login(user.getUserName(), newPassword);
		
	}
	
	@Test(expected = InstanceNotFoundException.class)
	public void testChangePasswordWithNonExistentId() throws InstanceNotFoundException, IncorrectPasswordException {
		userService.changePassword(NON_EXISTENT_ID, "X", "Y");
	}
	
	@Test(expected = IncorrectPasswordException.class)
	public void testChangePasswordWithIncorrectPassword() throws DuplicateInstanceException, InstanceNotFoundException,
		IncorrectPasswordException {
		
		User user = createUser("user");
		String oldPassword = user.getPassword();
		String newPassword = 'X' + oldPassword;
		
		userService.signUp(user);
		userService.changePassword(user.getId(), 'Y' + oldPassword, newPassword);
		
	}

}
