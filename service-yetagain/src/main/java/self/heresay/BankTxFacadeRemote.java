package self.heresay;

import java.util.List;

import javax.ejb.Remote;

import self.heresay.model.Transaction;
import self.heresay.model.User;

@Remote
public interface BankTxFacadeRemote {
	void addTransaction(Transaction tx);
	List<Transaction> getTransactions();
	User getUserByName(String userName);
	void addUser(User user);
}

