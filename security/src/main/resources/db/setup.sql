drop user if exists 'secureuser'@'localhost';
Create user 'secureuser'@'localhost' identified by 'secureuser123';
grant all privileges on securedb.* to 'secureuser'@'localhost';
flush privileges;



drop database if exists securedb;
create database securedb;