-- init.sql
CREATE DATABASE IF NOT EXISTS employee_db;
CREATE USER IF NOT EXISTS 'prashant'@'%' IDENTIFIED BY 'root';
GRANT ALL PRIVILEGES ON employee_db TO 'prashant'@'%';
FLUSH PRIVILEGES;
