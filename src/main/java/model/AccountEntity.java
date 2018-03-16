package model;

public class AccountEntity {
	
	private String id;
	private long creationDate;
	private long lastOperationDate;
	private double balance;
	private int maxWithdrawAmount;
	private int minWithdrawAmount;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(long creationDate) {
		this.creationDate = creationDate;
	}

	public long getLastOperationDate() {
		return lastOperationDate;
	}

	public void setLastOperationDate(long lastOperationDate) {
		this.lastOperationDate = lastOperationDate;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public int getMaxWithdrawAmount() {
		return maxWithdrawAmount;
	}

	public void setMaxWithdrawAmount(int maxWithdrawAmount) {
		this.maxWithdrawAmount = maxWithdrawAmount;
	}

	public int getMinWithdrawAmount() {
		return minWithdrawAmount;
	}

	public void setMinWithdrawAmount(int minWithdrawAmount) {
		this.minWithdrawAmount = minWithdrawAmount;
	}
	
	
}
