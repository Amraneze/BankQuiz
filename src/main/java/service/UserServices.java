package service;

import java.util.List;
import java.util.TreeSet;

import exception.UserAlreadyExistException;
import exception.UserDoesNotExistException;
import model.UserEntity;

public interface UserServices {
	
	UserEntity addUser(UserEntity user) throws UserAlreadyExistException;
	UserEntity updateUser(UserEntity user) throws UserDoesNotExistException;
	boolean deleteUser(String userId) throws UserDoesNotExistException;
	boolean unlinkAccountFromUser(String userId, String accountId);
	UserEntity getUserById(String userId);
	List<UserEntity> getUsers();
	TreeSet<String> getUserAccounts(String userId);
	double getSumOfAllAccountsOfUser(String userId);
	
	class Factory extends InjectableFactory<UserServices> {

        public static final Factory INSTANCE = new Factory();

        private Factory() {}

        @Override
        protected UserServices createInstance() {
            return new UserServicesImpl();
        }
    }
}
