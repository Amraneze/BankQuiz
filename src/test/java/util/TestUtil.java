package util;

import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import model.AccountEntity;
import model.GenericBuilder;
import model.UserEntity;

public class TestUtil {

	private static final List<String> firstNames = Arrays.asList("Carlene", "Dunlap", "Landry", "Tasha", "Monique", "James", "Fulton");
	private static final List<String> lastNames = Arrays.asList("Elliott", "Chen", "Jordan", "Arnold", "Schwartz", "Hammond", "Benton");
	private static final List<String> addresses = Arrays.asList("41 Bonner Point", "86 Buell Avenue", "700 Leroy Avenue", 
			"989 Blue Bill Park Circle", "2 Pepper Wood Pass", "20 Fremont Point", "1019 Forest Dale Hill");
	private static final List<String> cities = Arrays.asList("Paris", "Rome", "Lyon", "Lille", "Rennes", "Brest", "Versailles");
	private static final List<String> countries = Arrays.asList("France", "Italy", "Norway", "Japan", "Holland", "Spain", "Canada");
	private static final List<String> phones = Arrays.asList("+865174041936", "+3538225362776", "+869494441919",
			"+449213938098", "+334496405997", "+339241853190", "+72577349400");
	private static final List<String> emails = Arrays.asList("ahalcro1@nba.com", "bfoulser7@rakuten.co.jp", "hiacomob@usa.gov",
			"dsiemianowicze@telegraph.co.uk", "	ovaudh@hugedomains.com", "ldenyagink@amazonaws.com", "tconstanto@tripadvisor.com");

    private static int getRandomInRange(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
    
    public static double getRandomDoubleInRange(double min, double max) {
        return ThreadLocalRandom.current().nextDouble(min, max + 1);
    }

    public static String generatedUUID() {
        return UUID.randomUUID().toString();
    }
    
    public static UserEntity generateRandomUser() {
    	int random = getRandomInRange(0, 5);
    	return GenericBuilder.of(UserEntity::new)
    			.with(UserEntity::setId, generatedUUID())
    			.with(UserEntity::setFirstName, firstNames.get(random))
    			.with(UserEntity::setLastName, lastNames.get(random))
    			.with(UserEntity::setAge, getRandomInRange(18, 80))
    			.with(UserEntity::setAddress, addresses.get(random))
    			.with(UserEntity::setCity, cities.get(random))
    			.with(UserEntity::setCountry, countries.get(random))
    			.with(UserEntity::setPhone, phones.get(random))
    			.with(UserEntity::setEmail, emails.get(random))
    			.with(UserEntity::setAccounts, new TreeSet<>())
    			.build();
    }

    public static AccountEntity generateAccount() {
    	return GenericBuilder.of(AccountEntity::new)
    			.with(AccountEntity::setId, generatedUUID())
    			.with(AccountEntity::setCreationDate, System.currentTimeMillis())
    			.with(AccountEntity::setLastOperationDate, System.currentTimeMillis())
    			.with(AccountEntity::setBalance, getRandomDoubleInRange(-1000, 10000))
    			.with(AccountEntity::setMaxWithdrawAmount, getRandomInRange(400, 1000))
    			.with(AccountEntity::setMinWithdrawAmount, getRandomInRange(10, 50))
    			.build();
    }
}
