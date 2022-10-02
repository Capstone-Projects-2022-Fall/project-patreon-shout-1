package com.patreonshout.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
public class WebAccountRepository {

	@PersistenceContext
	private EntityManager em;

	@Transactional
	public int putAccount(String username, String password) {
		String sql = "insert into webaccounts (username, password) values (:username, :password)";
		Query q = em.createNativeQuery(sql);

		q.setParameter("username", username);
		q.setParameter("password", password);

		try {
			q.executeUpdate();
		} catch (Exception ex) {
			return 1;
		}

		return 0;
	}
}
