package com.cas.portfolio.core.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.cas.portfolio.core.entity.Customer;

@Repository
public class CustomerDaoImpl implements CustomerDao {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private DataSourceTransactionManager transactionManager;
	
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(this.dataSource);
	}

	@Override
	public Customer createCustomer(final Customer customer) {
		/* creating a new customer involves insertions into two separate tables.
		 * all the insertions should be performed atomically. so we wrap all of
		 * them into one database transaction to guarantee the atomicity. 
		 */
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setIsolationLevel(TransactionDefinition.ISOLATION_REPEATABLE_READ);
		TransactionStatus status = transactionManager.getTransaction(def);

		try {
			// insert into customer table
			String SQL = "insert into customer (email, password, age) " + " values (?, ?, ?)";
			jdbcTemplate.update(SQL, customer.getEmail(), customer.getPassword(), customer.getAge());
			
			// insert authorities into authority table
			SQL = "insert into authority (username, role) " + " values (?, ?)";
			String role = "ROLE_USER";
			if (customer.getAge() >= 20 && customer.getAge() <= 29) role = "ROLE_DBA";
			if (customer.getAge() >= 30 && customer.getAge() <= 39) role = "ROLE_ADMIN";
			jdbcTemplate.update(SQL, customer.getEmail(), role);
			
			logger.info("Created Customer Name = " + customer.getEmail());
			
			// commit the transaction
			transactionManager.commit(status);
		} catch (DataAccessException e) {
			System.out.println("Error in creating customer record, rolling back");
			transactionManager.rollback(status);
			throw e;
		}
		
		return customer;
	}

	@Override
	public int deleteCustomer(Customer customer) {
		String SQL = "delete from customer where email = ?";
		jdbcTemplate.update(SQL, customer.getEmail());
		
		SQL = "delete from authority";
		jdbcTemplate.update(SQL);
		
		return 1;
	}
	
	@Override
	public List<Customer> getAllCustomers() {
		String SQL = "select * from customer";
		List<Customer> customers = jdbcTemplate.query(SQL, new CustomerMapper());

		return customers;
	}

	@Override
	public Customer getCustomerByEmail(String email) {
		String SQL = "select * from customer where email = ?";
		Customer customer = jdbcTemplate.queryForObject(SQL,
				new Object[] { email }, new CustomerMapper());
		
		logger.info("Retrieved Customer Name = " + customer.getEmail());
		
		return customer;
	}
	
	@Override
	public Customer getCustomerByEmailAndPassword(String email, String password) {
		String SQL = "select * from customer where email = ? and password = ?";
		
		if (findNumberOfRecords(email, password) != 1) {
			return null;
		}
		Customer customer = jdbcTemplate.queryForObject(SQL,
				new Object[] { email, password }, new CustomerMapper());
		
		logger.info("Retrieved Customer Name = " + customer.getEmail());
		
		return customer;
	}
	@Override
	public int update(Customer customer) {
		String sql = "update customer set age = '"
				+ customer.getAge() + "' where email= ?";
		jdbcTemplate.update(sql, customer.getEmail());
		System.out.println("Updated Record with ID = " + customer.getEmail());
		return 1;
	}
	
	private int findNumberOfRecords(String email, String password){
		String SQL = "select count(*) from customer where email = ? and password = ?";

		int numRecords = jdbcTemplate.queryForObject(SQL, new Object[] { email, password }, Integer.class);

		return numRecords;
	}
	
	class CustomerMapper implements RowMapper<Customer> {
		public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
			Customer customer = new Customer();
			customer.setEmail(rs.getString("email"));
			customer.setPassword(rs.getString("password"));
			customer.setAge(rs.getInt("age"));

			return customer;
		}
	}
}
