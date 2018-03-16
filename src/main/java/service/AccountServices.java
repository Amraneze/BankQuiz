package service;

import java.util.List;

import exception.AccountAlreadyExistException;
import exception.AccountDoesNotExistException;
import model.AccountEntity;

public interface AccountServices {

	AccountEntity addAccount(String userId, AccountEntity account);
	AccountEntity addAccount(AccountEntity account) throws AccountAlreadyExistException;
	AccountEntity updateAccount(AccountEntity account) throws AccountDoesNotExistException;
	boolean deleteAccountFromUser(String userId, String accountId) throws AccountDoesNotExistException;
	boolean deleteAccount(String accountId) throws AccountDoesNotExistException;
	void deleteAllAccounts();
	AccountEntity linkAccountToUser(String userId, String accountId) throws AccountDoesNotExistException;
	List<AccountEntity> getAccounts();
	List<AccountEntity> getAccountsOfUser(String userId);
	AccountEntity withdrawMoney(String accountId, double money);
	AccountEntity depositMoney(String accountId, double money);
	
	class Factory extends InjectableFactory<AccountServices> {

        public static final Factory INSTANCE = new Factory();

        private Factory() {}

        @Override
        protected AccountServices createInstance() {
            return new AccountServicesImpl();
        }
    }
	
}
