package com.excilys.model;

import java.sql.Timestamp;

public class Computer {

	long id;
	String name;
	Timestamp introduced;
	Timestamp discontinued;
	Long companyId;
	
	
	/* Builder */
	
	public static class Builder {
		long id;
		String name;
		Timestamp introduced;
		Timestamp discontinued;
		Long companyId;
		
		public Builder(){
			this.id = -1;
		}
		
		public Builder id(long l){
			this.id = l;
			return this;
		}
		
		public Builder name(String s){
			this.name = s;
			return this;
		}
		
		public Builder introduced(Timestamp t){
			this.introduced = t;
			return this;
		}
		
		public Builder discontinued(Timestamp t){
			this.discontinued = t;
			return this;
		}
		
		public Builder companyId(Long l){
			this.companyId = l;
			return this;
		}
		
		public Computer build(){
			return new Computer(this);
		}
	}
	
	
	/* Constructors */
	
	public Computer() {
		this.id = -1;
		this.name = null;
		this.introduced = null;
		this.discontinued = null;
		this.companyId = null;
	}
	
	public Computer(Builder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.introduced = builder.introduced;
		this.discontinued = builder.discontinued;
		this.companyId = builder.companyId;
	}
	
	
	/* Getters and getters */
	
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

	
	/* Object */
	
	@Override
	public String toString() {
		return "(" + id + ", " +
				name + ", " +
				introduced + ", " +
				discontinued + ", " +
				companyId + ")";
	}
	
	
	
}
