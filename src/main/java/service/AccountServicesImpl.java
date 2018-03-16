package service;

import java.util.ArrayList;
import java.util.List;

import db.MockedData;
import exception.AccountAlreadyExistException;
import exception.AccountDoesNotExistException;
import model.AccountEntity;

public class AccountServicesImpl implements AccountServices {
	
	private MockedData db = new MockedData();

	@Override
	public AccountEntity addAccount(String userId, AccountEntity account) {
		return db.addAccount(userId, account);
	}
	
	@Override
	public AccountEntity addAccount(AccountEntity account) throws AccountAlreadyExistException {
		return db.addAccount(account);
	}

	@Override
	public AccountEntity updateAccount(AccountEntity account) throws AccountDoesNotExistException {
		return db.updateAccount(account);
	}

	@Override
	public boolean deleteAccountFromUser(String userId, String accountId) throws AccountDoesNotExistException {
		return db.deleteAccount(userId, accountId);
	}
	
	@Override
	public boolean deleteAccount(String accountId) throws AccountDoesNotExistException {
		return db.deleteAccount(accountId);
	}

	@Override
	public AccountEntity linkAccountToUser(String userId, String accountId) throws AccountDoesNotExistException {
		return db.linkAccountToUser(userId, accountId);
	}

	@Override
	public List<AccountEntity> getAccounts() {
		return new ArrayList<AccountEntity>(db.getAccounts().values());
	}

	@Override
	public List<AccountEntity> getAccountsOfUser(String userId) {
		return db.getAccountsOfUser(userId);
	}

	@Override
	public AccountEntity withdrawMoney(String accountId, double money) {
		return db.withdrawMoney(accountId, money);
	}

	@Override
	public AccountEntity depositMoney(String accountId, double money) {
		return db.depositMoney(accountId, money);
	}

	@Override
	public void deleteAllAccounts() {
		db.deleteAllAccounts();
	}
}
