package com.cta.service;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface LoanService {

	void borrow(Long userId, Long bdId);

	void returnBd(Long bdId);

}
