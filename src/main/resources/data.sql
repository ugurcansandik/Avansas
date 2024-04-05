CREATE DATABASE IF NOT EXISTS avansas;

INSERT IGNORE INTO `avansas`.`user`
(`birthday`, `email`, `first_name`, `last_name`, `password`, `phone`, `role_enum`, `username`)
VALUES
('1990-01-01', 'admin@avansas.com', 'Admin', 'User', 'nimda', '1234567890', 'ADMIN', 'admin');