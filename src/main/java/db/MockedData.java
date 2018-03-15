package db;

import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

import exception.AccountAlreadyExistException;
import exception.AccountDoesNotExistException;
import exception.UserAlreadyExistException;
import exception.UserDoesNotExistException;
import model.AccountEntity;
import model.UserEntity;

public class MockedData {

	private HashMap<String, UserEntity> users = new HashMap<>();
	private HashMap<String, AccountEntity> accounts = new HashMap<>();
	
	public HashMap<String, UserEntity> getUsers() {
		return this.users;
	}
	
	public UserEntity getUser(String userId) {
		return this.users.get(userId);
	}
	
	public UserEntity addUser(UserEntity user) throws UserAlreadyExistException {
		if (!this.users.containsKey(user.getId()))
			throw new UserAlreadyExistException();
		this.users.put(user.getId(), user);
		return this.users.get(user.getId());
	}
	
	public UserEntity updateUser(UserEntity user) throws UserDoesNotExistException {
		//UserEntity existedUser = this.users.get(user.getId());
		//if (existedUser == null)
		//	return existedUser;
		if (!this.users.containsKey(user.getId())) 
			throw new UserDoesNotExistException();
		return this.users.computeIfPresent(user.getId(), (id, oldUser) -> user);
	}
	
	public boolean removeUser(String userId) throws UserDoesNotExistException {
		if (!this.users.containsKey(userId)) 
			throw new UserDoesNotExistException();
		this.users.remove(userId);
		return this.users.get(userId) == null;
	}
	
	public HashMap<String, AccountEntity> getAccounts() {
		return accounts;
	}
	
	public AccountEntity getAccount(String accountId) {
		return this.accounts.get(accountId);
	}
	
	public AccountEntity addAccount(AccountEntity account) throws AccountAlreadyExistException {
		if (!this.accounts.containsKey(account.getId()))
			throw new AccountAlreadyExistException();
		this.accounts.put(account.getId(), account);
		return this.accounts.get(account.getId());
	}
	
	public AccountEntity addAccount(String userId, AccountEntity account) {
		this.accounts.put(account.getId(), account);
		TreeSet<String> accounts = this.users.get(userId).getAccounts();
		UserEntity user = this.users.get(userId);
		accounts.add(account.getId());
		user.setAccounts(accounts);
		//updateUser(user);
		this.users.computeIfPresent(user.getId(), (id, oldUser) -> user);
		return this.accounts.get(account.getId());
	}
	
	public AccountEntity updateAccount(AccountEntity account) throws AccountDoesNotExistException {
		if (!this.accounts.containsKey(account.getId()))
			throw new AccountDoesNotExistException();
		//AccountEntity existedAccount = this.accounts.get(account.getId());
		//if (existedAccount == null)
		//	return existedAccount;
		return this.accounts.computeIfPresent(account.getId(), (id, oldAccount) -> account);
	}
	
	public boolean deleteAccount(String userId, String accountId) throws AccountDoesNotExistException {
		if (!this.accounts.containsKey(accountId))
			throw new AccountDoesNotExistException();
		this.accounts.remove(accountId);
		UserEntity user = this.users.get(userId);
		TreeSet<String> newAccounts = user.getAccounts();
		newAccounts.remove(accountId);
		user.setAccounts(newAccounts);
		//updateUser(user);
		this.users.computeIfPresent(user.getId(), (id, oldUser) -> user);
		return this.accounts.get(accountId) == null && this.users.get(userId).getAccounts().contains(accountId);
	}
	
	public List<AccountEntity> getAccountsOfUser(String userId) {
		return this.users.get(userId).getAccounts().stream()
				.map(accountId -> getAccount(accountId))
				.collect(Collectors.toList());
	}
	
	public AccountEntity linkAccountToUser(String userId, String accountId) {
		return addAccount(userId, getAccount(accountId));
	}
	
	public AccountEntity withdrawMoney(String accountId, double money) {
		AccountEntity account = getAccount(accountId);
		account.setBalance(account.getBalance() - money);
		return this.accounts.computeIfPresent(account.getId(), (id, oldAccount) -> account);
	}

	public AccountEntity depositMoney(String accountId, double money) {
		AccountEntity account = getAccount(accountId);
		account.setBalance(account.getBalance() + money);
		return this.accounts.computeIfPresent(account.getId(), (id, oldAccount) -> account);
	}
	
	public Double getSumOfAccountsOfUser(String userId) {
		return getAccountsOfUser(userId).stream().mapToDouble(AccountEntity::getBalance).sum();
	}
}
