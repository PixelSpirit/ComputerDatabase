package com.excilys.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Computer {

    private long id;
    private String name;
    private LocalDateTime introduced;
    private LocalDateTime discontinued;
    private Company company;

    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");

    /* Builder */

    public static class Builder {

        private long id;
        private String name;
        private LocalDateTime introduced;
        private LocalDateTime discontinued;
        private Company company;

        /**
         * Constructs a ComputerBuilder.
         */
        public Builder() {
            this.id = -1;
            this.name = null;
            this.introduced = null;
            this.discontinued = null;
            this.company = null;
        }

        /**
         * Sets the id of the builder.
         * @param id The new id value
         * @return The current Builder
         */
        public Builder id(long id) {
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
        public Builder introduced(LocalDateTime introduced) {
            this.introduced = introduced;
            return this;
        }

        /**
         * Sets the discontinued date of the builder.
         * @param discontinued The new discontinued date value
         * @return The current Builder
         */
        public Builder discontinued(LocalDateTime discontinued) {
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
         * @return a fresh Computer with the current Builder settings
         */
        public Computer build() {
            return new Computer(this);
        }
    }

    /* Constructors */

    /**
     * Constructs a Computer.
     */
    public Computer() {
        this.id = -1;
        this.name = null;
        this.introduced = null;
        this.discontinued = null;
        this.company = null;
    }

    /**
     * Constructs a Computer from a new Builder.
     * @param builder The Computer builder configuration
     */
    public Computer(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.introduced = builder.introduced;
        this.discontinued = builder.discontinued;
        this.company = builder.company;
    }

    /* Getters and getters */

    /**
     * @return the id
     */
    public long getId() {
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
    public LocalDateTime getIntroduced() {
        return introduced;
    }

    /**
     * @param introduced the introduced to set
     */
    public void setIntroduced(LocalDateTime introduced) {
        this.introduced = introduced;
    }

    /**
     * @return the discontinued
     */
    public LocalDateTime getDiscontinued() {
        return discontinued;
    }

    /**
     * @param discontinued the discontinued to set
     */
    public void setDiscontinued(LocalDateTime discontinued) {
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
        return "(" + id + ", " + name + ", " + introduced + ", " + discontinued + ", " + company.getName() + ")";
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
