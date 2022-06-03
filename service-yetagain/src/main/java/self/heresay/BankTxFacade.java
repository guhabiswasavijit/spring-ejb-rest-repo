package self.heresay;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import lombok.extern.slf4j.Slf4j;
import self.heresay.model.Role;
import self.heresay.model.Transaction;
import self.heresay.model.User;

@Stateless(name = "BankTxFacade")
@Local(BankTxFacadeLocal.class)
@Remote(BankTxFacadeRemote.class)
@Slf4j
public class BankTxFacade implements BankTxFacadeLocal, BankTxFacadeRemote{
	private SessionFactory sessionFactory;
	public BankTxFacade() {
		Configuration config = new Configuration();
		config.addAnnotatedClass(Transaction.class);
		config.addAnnotatedClass(Role.class);
		config.addAnnotatedClass(User.class);
		/*
		 * Properties props = new Properties(); try {
		 * props.load(BankTxFacade.class.getResourceAsStream("hibernate.properties")); }
		 * catch (IOException ex) { log.error("unable to load hibernate.properties",ex);
		 * } config.addProperties(props);
		 */
		sessionFactory = config.buildSessionFactory();
		log.info("created session factory");
	}
	public void addTransaction(Transaction tx) {
		Session session = sessionFactory.openSession();
		org.hibernate.Transaction transaction = session.beginTransaction();
		session.persist(tx);
		transaction.commit();
	}

	public List<Transaction> getTransactions() {
		Session session = sessionFactory.openSession();
		Query<Transaction> qry = session.createQuery("from Transaction",Transaction.class);
		return qry.list();
	}
	public User getUserByName(String userName) {
		Session session = sessionFactory.openSession();
		Query<User> qry = session.createQuery("from User where username =:userName", User.class);
		qry.setParameter("userName",userName);
		User user = qry.uniqueResult();
		return user;
	}
	public void addUser(User user) {
		Session session = sessionFactory.openSession();
		org.hibernate.Transaction transaction = session.beginTransaction();
		session.persist(user);
		transaction.commit();
	}

}
