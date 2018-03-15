package service;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import db.MockedData;
import exception.UserAlreadyExistException;
import exception.UserDoesNotExistException;
import model.UserEntity;

public class UserServicesImpl implements UserServices {
	
	private MockedData db = new MockedData();

	@Override
	public UserEntity addUser(UserEntity user) throws UserAlreadyExistException {
		return db.addUser(user);
	}

	@Override
	public UserEntity updateUser(UserEntity user) throws UserDoesNotExistException {
		return db.updateUser(user);
	}

	@Override
	public boolean deleteUser(String userId) throws UserDoesNotExistException {
		return db.removeUser(userId);
	}

	@Override
	public UserEntity getUserById(String userId) {
		return db.getUser(userId);
	}

	@Override
	public List<UserEntity> getUsers() {
		return new ArrayList<UserEntity>(db.getUsers().values());
	}

	@Override
	public TreeSet<String> getUserAccounts(String userId) {
		return db.getUser(userId).getAccounts();
	}

	@Override
	public double getSumOfAllAccountsOfUser(String userId) {
		return db.getSumOfAccountsOfUser(userId);
	}

}
