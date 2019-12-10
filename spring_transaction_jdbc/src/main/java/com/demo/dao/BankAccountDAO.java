package com.demo.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.support.DaoSupport;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.demo.exception.BankTransactionException;
import com.demo.mapper.BankAccountMapper;
import com.demo.model.BankAccountInfo;

@Repository
@Transactional
public class BankAccountDAO extends JdbcDaoSupport{

	public BankAccountDAO(DataSource dataSource) {
		// TODO Auto-generated constructor stub
		this.setDataSource(dataSource);
	}
	
	public List<BankAccountInfo> getBankAccounts(){
		
		String sql = BankAccountMapper.sql;
		
		Object[] params = new Object[] {};
		BankAccountMapper mapper = new BankAccountMapper();
		List<BankAccountInfo> list = this.getJdbcTemplate().query(sql, params, mapper);
		
		return list;
	}
	
	public 	BankAccountInfo findAccountInfo(Long id) {
		
		String sql = BankAccountMapper.sql + " where ba.id = ?";
		Object[] param = new Object[] {id};
		BankAccountMapper mapper = new BankAccountMapper();
		BankAccountInfo bai = (BankAccountInfo) this.getJdbcTemplate().queryForObject(sql, param, mapper);
		return bai;
				
	}
	
	public void addAmount(Long id, double amount) throws BankTransactionException {
		BankAccountInfo accountInfo = this.findAccountInfo(id);
		if(accountInfo == null) {
			throw new BankTransactionException("Account not found: " + id);
		}
		
		double newBalance = accountInfo.getBalance() + amount;
		if(accountInfo.getBalance() + amount < 0) {
			throw new BankTransactionException("The money in the account: " + id + " is not enough (" + accountInfo.getBalance() + ")");
		}
		
		accountInfo.setBalance(newBalance);
		String sqlUpdate = "update bank_account set balance = ? where id = ?";
		this.getJdbcTemplate().update(sqlUpdate, accountInfo.getBalance(), accountInfo.getId());
	}
	
	@Transactional(rollbackFor = BankTransactionException.class, propagation = Propagation.REQUIRES_NEW)
	public void sendMoney(Long fromAccountId, Long toAccountId, double amount) throws BankTransactionException {
			addAmount(toAccountId, amount);
			addAmount(fromAccountId, -amount);
		
	}
}
