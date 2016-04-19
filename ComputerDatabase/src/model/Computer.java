package model;

import java.sql.Timestamp;

public class Computer {

	long id = 0;
	String name = null;
	Timestamp introduced = null;
	Timestamp discountinued = null;
	Long companyId = null;

	
	
	public Computer(int id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Timestamp getIntroduced() {
		return introduced;
	}


	public void setIntroduced(Timestamp introduced) {
		this.introduced = introduced;
	}


	public Timestamp getDiscountinued() {
		return discountinued;
	}


	public void setDiscountinued(Timestamp discountinued) {
		this.discountinued = discountinued;
	}


	public Long getCompanyId() {
		return companyId;
	}


	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	
}
