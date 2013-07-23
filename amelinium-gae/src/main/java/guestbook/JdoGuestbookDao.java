package guestbook;

import java.util.List;

import javax.jdo.PersistenceManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jdo.TransactionAwarePersistenceManagerFactoryProxy;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class JdoGuestbookDao extends AbstractJdoDao implements GuestbookDao {
	@Autowired
	public JdoGuestbookDao(
			final TransactionAwarePersistenceManagerFactoryProxy pmf) {
		super(pmf);
	}

	@Override
	@Transactional
	public Greeting store(final Greeting greeting) {
		PersistenceManager pm = getPersistenceManager();
		try {
			return pm.makePersistent(greeting);
		} finally {
			pm.close();
		}
	}

	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public List<Greeting> getAll() {
		PersistenceManager pm = getPersistenceManager();
		try {
			return (List<Greeting>) pm.newQuery(Greeting.class).execute();
		} finally {
			pm.close();
		}
	}
}