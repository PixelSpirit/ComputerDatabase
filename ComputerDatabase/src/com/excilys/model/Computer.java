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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((discontinued == null) ? 0 : discontinued.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((introduced == null) ? 0 : introduced.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Computer other = (Computer) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (discontinued == null) {
			if (other.discontinued != null)
				return false;
		} else if (!discontinued.equals(other.discontinued))
			return false;
		if (id != other.id)
			return false;
		if (introduced == null) {
			if (other.introduced != null)
				return false;
		} else if (!introduced.equals(other.introduced))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
		
}
