package model;

import java.sql.Timestamp;

public class Computer {

	long id = 0;
	String name = null;
	Timestamp introduced = null;
	Timestamp discontinued = null;
	Long companyId = null;

	
	
	public Computer(long id) {
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
	insert(c);
	System.out.println(r

	public Timestamp getIntroduced() {
		return introduced;
	}


	public void setIntroduced(Timestamp introduced) {
		this.introduced = introduced;
	}

	public Timestamp getDiscontinued() {
		return discontinued;
	}


	public void setDiscontinued(Timestamp discountinued) {
		this.discontinued = discountinued;
	}


	public Long getCompanyId() {
		return companyId;
	}


	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	@Override
	public String toString() {
		return String.format("(%d, '%s', '%s', '%s', %d)", 
				id, 
				name, 
				introduced.toString(), 
				discontinued.toString(), 
				companyId
				);
	}
	
	
	
}
