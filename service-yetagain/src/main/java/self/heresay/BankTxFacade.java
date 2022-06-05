package self.heresay;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.hibernate.NonUniqueResultException;

import self.heresay.model.Role;
import self.heresay.model.Transaction;
import self.heresay.model.User;

@Stateless(name = "BankTxFacade")
@Local(BankTxFacadeLocal.class)
@Remote(BankTxFacadeRemote.class)
public class BankTxFacade implements BankTxFacadeLocal, BankTxFacadeRemote {
	@PersistenceContext(unitName="finance-db")
	private EntityManager entityManager;

	public void addTransaction(Transaction tx) {
		entityManager.persist(tx);
	}

	public List<Transaction> getTransactions() {
		return entityManager.createQuery("from Transaction",Transaction.class).getResultList();
	}
	public User getUserByName(String userName) {
		TypedQuery<User> qry = entityManager.createQuery("from User where username =:userName", User.class);
		qry.setParameter("userName",userName);
		User user = null;
		try {
			user = qry.getSingleResult();
		} catch (NoResultException | NonUniqueResultException ex) {
			ex.printStackTrace();
		}
		return user;
	}
	public void addUser(User user) {
		entityManager.persist(user);
	}
	public Role findRoleById(Integer roleId) {
		return entityManager.find(Role.class, roleId);
	}
	public Role findRoleByName(String i_role) {
		TypedQuery<Role> qry = entityManager.createQuery("from Role where name =:roleName",Role.class);
		qry.setParameter("roleName",i_role);
		Role role = null;
		try {
			role = qry.getSingleResult();
		} catch (NoResultException | NonUniqueResultException ex) {
			ex.printStackTrace();
		}
		return role;
	}
}
