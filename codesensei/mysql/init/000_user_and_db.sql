-- Create DB + app user for local MySQL (run as root)

CREATE DATABASE IF NOT EXISTS codesensei CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- If you already created this user, you can ignore errors.
CREATE USER IF NOT EXISTS 'codesensei'@'localhost' IDENTIFIED BY 'codesensei';
CREATE USER IF NOT EXISTS 'codesensei'@'%' IDENTIFIED BY 'codesensei';

GRANT ALL PRIVILEGES ON codesensei.* TO 'codesensei'@'localhost';
GRANT ALL PRIVILEGES ON codesensei.* TO 'codesensei'@'%';
FLUSH PRIVILEGES;

