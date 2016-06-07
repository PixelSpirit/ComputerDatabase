#!/bin/bash

mysql -uroot < ../spec-cdb/config/db/1-SCHEMA.sql
mysql -uroot < ../spec-cdb/config/db/2-PRIVILEGES.sql
mysql -uroot < ../spec-cdb/config/db/3-ENTRIES.sql
