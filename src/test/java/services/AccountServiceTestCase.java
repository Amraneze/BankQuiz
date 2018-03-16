package services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import exception.AccountAlreadyExistException;
import exception.AccountDoesNotExistException;
import model.AccountEntity;
import service.AccountServices;
import util.MethodOrder;
import util.Sort;
import util.TestUtil;

@RunWith(MethodOrder.class)
public class AccountServiceTestCase {
	
	private static AccountServices accountServices;
	private static AccountEntity generatedAccount;
	
	@Rule
    public ExpectedException thrown = ExpectedException.none();
	
	@BeforeClass
	public static void setUp() {
		accountServices = AccountServices.Factory.INSTANCE.getInstance();
    	generatedAccount = TestUtil.generateAccount();
	}

    @Test
    @Sort(order = 1)
    public void addAccount() throws AccountAlreadyExistException {
    	AccountEntity account = accountServices.addAccount(generatedAccount);
    	assertEquals(generatedAccount, account);
    }
    
    @Test
    @Sort(order = 2)
    public void withdrawMoney() {
    	double money = generatedAccount.getBalance() / 2;
    	AccountEntity account = accountServices.withdrawMoney(generatedAccount.getId(), money);
    	assertEquals(generatedAccount.getBalance(), account.getBalance());
    }
    
    
    @Test
    @Sort(order = 3)
    public void depositMoney() {
    	double money = TestUtil.getRandomDoubleInRange(50, 1000);
    	AccountEntity account = accountServices.withdrawMoney(generatedAccount.getId(), money);
    	assertEquals(generatedAccount.getBalance(), account.getBalance());
    }


    @Test
    @Sort(order = 5)
    public void deleteAccount() throws AccountDoesNotExistException {
    	assertEquals(true, accountServices.deleteAccount(generatedAccount.getId()));
    }
    
    @Test
    @Sort(order = 6)
    public void deleteNonExistantAccount() throws AccountDoesNotExistException {
    	thrown.expect(AccountDoesNotExistException.class);
    	accountServices.deleteAccount(TestUtil.generatedUUID());
    }
}
