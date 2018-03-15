package services;

import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import exception.UserAlreadyExistException;
import exception.UserDoesNotExistException;
import model.UserEntity;
import service.AccountServices;
import service.UserServices;
import util.TestUtil;

public class UserServiceTestCase {
	
	private UserServices userServices;
	private AccountServices accountServices;
	private UserEntity generatedUser;
	
	@Rule
    public ExpectedException thrown = ExpectedException.none();
	
	@Before
	public void setUp() {
		accountServices = AccountServices.Factory.INSTANCE.getInstance();
		userServices = UserServices.Factory.INSTANCE.getInstance();
    	generatedUser = TestUtil.generateRandomUser();
	}

    @Test
    public void addUser() throws UserAlreadyExistException {
    	UserEntity user = userServices.addUser(generatedUser);
    	assertEquals(generatedUser, user);
    }
    
    @Test
    public void deleteUser() throws UserDoesNotExistException {
		assertEquals(true, userServices.deleteUser(generatedUser.getId()));
    }
    
    @Test
    public void deleteAnNonExistantUser() throws UserDoesNotExistException {
    	thrown.expect(UserDoesNotExistException.class);
        //thrown.expectMessage(startsWith("User does not exist"));
		userServices.deleteUser(TestUtil.generatedUUID());
    }
}
