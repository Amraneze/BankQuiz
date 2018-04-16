package services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import exception.AccountAlreadyExistException;
import exception.AccountDoesNotExistException;
import exception.UserAlreadyExistException;
import exception.UserDoesNotExistException;
import model.AccountEntity;
import model.UserEntity;
import service.AccountServices;
import service.UserServices;
import util.MethodOrder;
import util.Sort;
import util.TestUtil;

import org.junit.runner.RunWith;

@RunWith(MethodOrder.class)
public class UserServiceTestCase {
	
	private static UserServices userServices;
	private static AccountServices accountServices;
	private static UserEntity generatedUser;
	private static AccountEntity generatedAccount;
	
	@Rule
    public ExpectedException thrown = ExpectedException.none();
	
	@BeforeClass
	public static void setUp() {
		accountServices = AccountServices.Factory.INSTANCE.getInstance();
		userServices = UserServices.Factory.INSTANCE.getInstance();
		generatedUser = TestUtil.generateRandomUser();
		generatedAccount = TestUtil.generateAccount();
	}

    @Test
    @Sort(order = 1)
    public void addUser() throws UserAlreadyExistException {
    	UserEntity user = userServices.addUser(generatedUser);
    	assertEquals(generatedUser, user);
    }
    
    @Test
    @Sort(order = 2)
    public void updateUser() throws UserDoesNotExistException {
    	generatedUser.setFirstName("Test");
    	UserEntity user = userServices.updateUser(generatedUser);
    	assertEquals(generatedUser, user);
    }
    
    @Test
    @Sort(order = 3)
    public void getAllUsers() {
    	assertEquals(1, userServices.getUsers().size());
    }
    
    @Test
    @Sort(order = 4)
    public void addAccount() throws AccountAlreadyExistException {
    	AccountEntity account = accountServices.addAccount(generatedAccount);
    	assertEquals(generatedAccount, account);
    }
    
    @Test
    @Sort(order = 5)
    public void linkAccountToUser() throws AccountDoesNotExistException {
    	AccountEntity account = accountServices.linkAccountToUser(generatedUser.getId(), generatedAccount.getId());
    	assertEquals(generatedAccount, account);
    	assert(userServices.getUserById(generatedUser.getId()).getAccounts().contains(generatedAccount.getId()));
    }
    
    @Test
    @Sort(order = 6)
    public void unlinkAccountFromUser() throws AccountDoesNotExistException {
    	accountServices.linkAccountToUser(generatedUser.getId(), generatedAccount.getId());
    	assertEquals(false, userServices.unlinkAccountFromUser(generatedUser.getId(), generatedAccount.getId()));
    }
    
    @Test
    @Sort(order = 7)
    public void linkAccountsToUser() throws AccountDoesNotExistException, AccountAlreadyExistException, UserDoesNotExistException {
    	for (int i = 0; i < 10; i++) {
    		AccountEntity newAccount = TestUtil.generateAccount();
    		accountServices.addAccount(newAccount);
    		accountServices.linkAccountToUser(generatedUser.getId(), newAccount.getId());
    	}
    	assertEquals(10, userServices.getUserById(generatedUser.getId()).getAccounts().size());
    	userServices.unlinkAccountsFromUser(generatedUser.getId());
    }
    
    @Test
    @Sort(order = 8)
    public void sumOfAccountsOfUser() throws AccountDoesNotExistException, AccountAlreadyExistException {
    	double sum = 0;
    	for (int i = 0; i < 10; i++) {
    		AccountEntity newAccount = TestUtil.generateAccount();
    		sum += newAccount.getBalance();
    		accountServices.addAccount(newAccount);
    		accountServices.linkAccountToUser(generatedUser.getId(), newAccount.getId());
    	}
    	//XXX there is the issue for double, it will generate AssertionFailedError
    	//we need add a third parameter as an epsilon to check if the difference between the two first params
    	//is bigger than epsilon or not. For example we have the case of getting AssertFailed because 
    	//sum equals 48322.32097428113 and the function getSumOfAllAccountsOfUser returns 48322.32097428114
    	assertEquals(sum, userServices.getSumOfAllAccountsOfUser(generatedUser.getId()), 0.001);
    }
    
    @Test
    @Sort(order = 9)
    public void deleteUser() throws UserDoesNotExistException {
		assertEquals(true, userServices.deleteUser(generatedUser.getId()));
    }
    
    @Test
    @Sort(order = 10)
    public void deleteAnNonExistantUser() throws UserDoesNotExistException {
    	thrown.expect(UserDoesNotExistException.class);
		userServices.deleteUser(TestUtil.generatedUUID());
    }
    
}
