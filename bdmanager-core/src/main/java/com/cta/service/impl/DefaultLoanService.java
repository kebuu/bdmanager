package com.cta.service.impl;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.cta.dao.CrudDao;
import com.cta.exception.AppException;
import com.cta.model.Bd;
import com.cta.model.Loan;
import com.cta.model.Model;
import com.cta.model.User;
import com.cta.service.LoanService;

public class DefaultLoanService implements LoanService {

	@Autowired
	protected CrudDao crudDao;
	@Autowired
	protected SessionFactory sessionFactory;
	
	@Override
	public void borrow(Long userId, Long bdId) {
		checkBdNotAlreadyBorrowed(bdId);
		
		Loan loan = new Loan();
		loan.setStartDate(new Date());
		loan.setUser(Model.newInstanceWithId(User.class, userId));
		loan.getBds().add(Model.newInstanceWithId(Bd.class, bdId));
		crudDao.create(loan);
	}

	@Override
	public void returnBd(Long bdId) {
		Session session = sessionFactory.getCurrentSession();
		Loan loan = (Loan) session.createCriteria(Loan.class, "loan").createAlias("bds", "bd")
				.add(Restrictions.isNull("loan.endDate"))
				.add(Restrictions.eq("bd.id", bdId))
				.uniqueResult();
		loan.setEndDate(new Date());
		crudDao.update(loan);
	}
	
	private void checkBdNotAlreadyBorrowed(Long bdId) {
		Object uniqueResult = sessionFactory.getCurrentSession().createCriteria(Loan.class)
			.add(Restrictions.isNull("endDate"))
			.createCriteria("bds").add(Restrictions.idEq(bdId)).uniqueResult();
		
		if(uniqueResult != null) {
			throw new AppException("bd.already.borrowed");
		}
	}
}
