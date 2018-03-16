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

	private static HashMap<String, UserEntity> users = new HashMap<>();
	private static HashMap<String, AccountEntity> accounts = new HashMap<>();
	
	public HashMap<String, UserEntity> getUsers() {
		return users;
	}
	
	public UserEntity getUser(String userId) {
		return users.get(userId);
	}
	
	public UserEntity addUser(UserEntity user) throws UserAlreadyExistException {
		if (users.containsKey(user.getId()))
			throw new UserAlreadyExistException();
		users.put(user.getId(), user);
		return users.get(user.getId());
	}
	
	public UserEntity updateUser(UserEntity user) throws UserDoesNotExistException {
		if (!users.containsKey(user.getId())) 
			throw new UserDoesNotExistException();
		return users.computeIfPresent(user.getId(), (id, oldUser) -> user);
	}
	
	public boolean removeUser(String userId) throws UserDoesNotExistException {
		if (!users.containsKey(userId)) 
			throw new UserDoesNotExistException();
		users.remove(userId);
		return users.get(userId) == null;
	}
	
	public HashMap<String, AccountEntity> getAccounts() {
		return accounts;
	}
	
	public AccountEntity getAccount(String accountId) {
		return accounts.get(accountId);
	}
	
	public AccountEntity addAccount(AccountEntity account) throws AccountAlreadyExistException {
		if (accounts.containsKey(account.getId()))
			throw new AccountAlreadyExistException();
		accounts.put(account.getId(), account);
		return accounts.get(account.getId());
	}
	
	public AccountEntity addAccount(String userId, AccountEntity account) {
		accounts.put(account.getId(), account);
		TreeSet<String> oldAccounts = users.get(userId).getAccounts();
		UserEntity user = users.get(userId);
		oldAccounts.add(account.getId());
		user.setAccounts(oldAccounts);
		users.computeIfPresent(user.getId(), (id, oldUser) -> user);
		return accounts.get(account.getId());
	}
	
	public AccountEntity updateAccount(AccountEntity account) throws AccountDoesNotExistException {
		if (!accounts.containsKey(account.getId()))
			throw new AccountDoesNotExistException();
		return accounts.computeIfPresent(account.getId(), (id, oldAccount) -> account);
	}
	
	public boolean deleteAccount(String userId, String accountId) throws AccountDoesNotExistException {
		if (!accounts.containsKey(accountId))
			throw new AccountDoesNotExistException();
		accounts.remove(accountId);
		UserEntity user = users.get(userId);
		TreeSet<String> newAccounts = user.getAccounts();
		newAccounts.remove(accountId);
		user.setAccounts(newAccounts);
		users.computeIfPresent(user.getId(), (id, oldUser) -> user);
		return accounts.get(accountId) == null && users.get(userId).getAccounts().contains(accountId);
	}
	
	
	public boolean deleteAccount(String accountId) throws AccountDoesNotExistException {
		if (!accounts.containsKey(accountId))
			throw new AccountDoesNotExistException();
		accounts.remove(accountId);
		return accounts.get(accountId) == null;
	}
	
	public List<AccountEntity> getAccountsOfUser(String userId) {
		return users.get(userId).getAccounts().stream()
				.map(accountId -> getAccount(accountId))
				.collect(Collectors.toList());
	}
	
	public AccountEntity linkAccountToUser(String userId, String accountId) throws AccountDoesNotExistException {
		AccountEntity account = getAccount(accountId);
		if (account == null)
			throw new AccountDoesNotExistException(); 
		return addAccount(userId, account);
	}
	
	public AccountEntity withdrawMoney(String accountId, double money) {
		AccountEntity account = getAccount(accountId);
		account.setBalance(account.getBalance() - money);
		return accounts.computeIfPresent(account.getId(), (id, oldAccount) -> account);
	}

	public AccountEntity depositMoney(String accountId, double money) {
		AccountEntity account = getAccount(accountId);
		account.setBalance(account.getBalance() + money);
		return accounts.computeIfPresent(account.getId(), (id, oldAccount) -> account);
	}
	
	public Double getSumOfAccountsOfUser(String userId) {
		return getAccountsOfUser(userId).stream().mapToDouble(AccountEntity::getBalance).sum();
	}

	public boolean unlinkAccountFromUser(String userId, String accountId) {
		UserEntity user = users.get(userId);
		//We can do this because the variable is static
		user.getAccounts().remove(accountId);
		return user.getAccounts().contains(accountId);
	}

	public void deleteAllAccounts() {
		accounts.clear();
	}
}
