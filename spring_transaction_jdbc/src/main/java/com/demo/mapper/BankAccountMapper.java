package com.demo.mapper;


import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.demo.model.*;

public class BankAccountMapper implements RowMapper<BankAccountInfo>{

	public static final String sql = "Select ba.Id, ba.full_name, ba.balance from bank_account ba";

	@Override
	public BankAccountInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		Long id 			= rs.getLong("id");
		String fullName 	= rs.getString("full_name");
		double balance 		= rs.getDouble("balance");
		return new BankAccountInfo(id, fullName, balance);
	}
	

}
