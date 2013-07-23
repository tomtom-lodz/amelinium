package com.tomtom.amelinium.projectservice.model;

import guestbook.AbstractJdoDao;

import java.util.List;

import javax.jdo.PersistenceManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jdo.TransactionAwarePersistenceManagerFactoryProxy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@Component
public class ProjectDaoImpl /*extends AbstractJdoDao*/ implements ProjectDao {

	public Project store(Project project) {
		project.save();
		return project;
	}

	public Project get(long id) {
		return OfyService.ofy().load().type(Project.class).id(id).get();
	}

	public List<Project> getAll() {
		return OfyService.ofy().load().type(Project.class).list();
	}

	public void delete(long id) {
		OfyService.ofy().delete().type(Project.class).id(id);
	}
	

//	@Autowired
//	public ProjectDaoImpl(final TransactionAwarePersistenceManagerFactoryProxy pmf) {
//		super(pmf);
//	}
//
//	@Transactional
//	public Project store(final Project project) {
//		PersistenceManager pm = getPersistenceManager();
//		try {
//			return pm.makePersistent(project);
//		} finally {
//			pm.close();
//		}
//	}
//
//	@Transactional
//	public Project get(long id) {
//		Key key = KeyFactory.createKey("Project", id);
//		PersistenceManager pm = getPersistenceManager();
//		try {
//			return pm.getObjectById(Project.class, key);
//		} finally {
////			pm.close();
//		}
//	}
//
//	@Transactional
//	@SuppressWarnings("unchecked")
//	public List<Project> getAll() {
//		PersistenceManager pm = getPersistenceManager();
//		try {
//			return (List<Project>) pm.newQuery(Project.class).execute();
//		} finally {
////			pm.close();
//		}
//	}
//
//	@Transactional
//	public void delete(long id) {
//		Key key = KeyFactory.createKey("Project", id);
//		PersistenceManager pm = getPersistenceManager();
//		try {
//			Project object = pm.getObjectById(Project.class, key);
//			pm.deletePersistent(object);
//		} finally {
//			pm.close();
//		}
//	}
//
}
