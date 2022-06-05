package self.heresay;

import java.util.List;

import javax.ejb.Local;

import self.heresay.model.Role;
import self.heresay.model.Transaction;
import self.heresay.model.User;

@Local
public interface BankTxFacadeLocal {
	void addTransaction(Transaction tx);
	List<Transaction> getTransactions();
	User getUserByName(String userName);
	void addUser(User user);
	Role findRoleById(Integer roleId);
	Role findRoleByName(String i_role);
}
