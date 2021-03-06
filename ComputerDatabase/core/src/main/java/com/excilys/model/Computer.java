package com.excilys.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity(name = "computer")
public class Computer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDate introduced;

    private LocalDate discontinued;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");

    /* Constructors */

    public Computer() {
    }

    /**
     * Constructs a computer.
     * @param name The computer's name
     */
    public Computer(String name) {
        this.id = null;
        this.name = name;
        this.introduced = null;
        this.discontinued = null;
        this.company = null;
    }

    public Computer(Computer cpt) {
        this.id = (cpt.id != null) ? new Long(cpt.id) : null;
        this.name = cpt.name;
        this.introduced = (cpt.introduced != null) ? LocalDate.from(cpt.introduced) : null;
        this.discontinued = (cpt.discontinued != null) ? LocalDate.from(cpt.discontinued) : null;
        if (cpt.company != null) {
            this.company = new Company(cpt.company.getId(), cpt.company.getName());
        } else {
            this.company = null;
        }
    }

    /**
     * Constructs a computer from a new Builder.
     * @param builder The computer builder configuration
     */
    public Computer(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.introduced = builder.introduced;
        this.discontinued = builder.discontinued;
        this.company = builder.company;
    }

    /* Builder */

    public static class Builder {

        private Long id;
        private String name;
        private LocalDate introduced;
        private LocalDate discontinued;
        private Company company;

        /**
         * Constructs a ComputerBuilder.
         * @param name The computer's name
         */
        public Builder(String name) {
            this.name = name;
            this.id = null;
            this.introduced = null;
            this.discontinued = null;
            this.company = null;
        }

        /**
         * Sets the id of the builder.
         * @param id The new id value
         * @return The current Builder
         */
        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        /**
         * Sets the name of the builder.
         * @param name The new name value
         * @return The current Builder
         */
        public Builder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * Sets the introduced date of the builder.
         * @param introduced The new introduced date value
         * @return The current Builder
         */
        public Builder introduced(LocalDate introduced) {
            this.introduced = introduced;
            return this;
        }

        /**
         * Sets the discontinued date of the builder.
         * @param discontinued The new discontinued date value
         * @return The current Builder
         */
        public Builder discontinued(LocalDate discontinued) {
            this.discontinued = discontinued;
            return this;
        }

        /**
         * Sets the company of the builder.
         * @param company The new company value
         * @return The current Builder
         */
        public Builder company(Company company) {
            this.company = company;
            return this;
        }

        /**
         * @return a fresh computer with the current Builder settings
         */
        public Computer build() {
            return new Computer(this);
        }
    }

    /* Getters and getters */

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the introduced
     */
    public LocalDate getIntroduced() {
        return introduced;
    }

    /**
     * @param introduced the introduced to set
     */
    public void setIntroduced(LocalDate introduced) {
        this.introduced = introduced;
    }

    /**
     * @return the discontinued
     */
    public LocalDate getDiscontinued() {
        return discontinued;
    }

    /**
     * @param discontinued the discontinued to set
     */
    public void setDiscontinued(LocalDate discontinued) {
        this.discontinued = discontinued;
    }

    /**
     * @return the company
     */
    public Company getCompany() {
        return company;
    }

    /**
     * @param company the company to set
     */
    public void setCompany(Company company) {
        this.company = company;
    }

    /**
     * @return the formatter
     */
    public static DateTimeFormatter getFormatter() {
        return formatter;
    }

    /**
     * @param formatter the formatter to set
     */
    public static void setFormatter(DateTimeFormatter formatter) {
        Computer.formatter = formatter;
    }

    /* Object */

    @Override
    public String toString() {
        String s = "(" + id + ", " + name + ", " + introduced + ", " + discontinued;
        if (company != null) {
            s += ", " + company.getName();
        }
        return s + ")";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((company == null) ? 0 : company.hashCode());
        result = prime * result + ((discontinued == null) ? 0 : discontinued.hashCode());
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + ((introduced == null) ? 0 : introduced.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Computer other = (Computer) obj;
        if (company == null) {
            if (other.company != null) {
                return false;
            }
        } else if (!company.equals(other.company)) {
            return false;
        }
        if (discontinued == null) {
            if (other.discontinued != null) {
                return false;
            }
        } else if (!discontinued.equals(other.discontinued)) {
            return false;
        }
        if (id != other.id) {
            return false;
        }
        if (introduced == null) {
            if (other.introduced != null) {
                return false;
            }
        } else if (!introduced.equals(other.introduced)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }

}
