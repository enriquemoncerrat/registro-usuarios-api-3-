CREATE TABLE users (
                       id UUID NOT NULL PRIMARY KEY,
                       name VARCHAR(255),
                       email VARCHAR(255),
                       password VARCHAR(255),
                       token VARCHAR(255),
                       is_active BOOLEAN,
                       created TIMESTAMP,
                       modified TIMESTAMP,
                       last_login TIMESTAMP
);

CREATE TABLE phone (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       number VARCHAR(255),
                       citycode VARCHAR(255),
                       contrycode VARCHAR(255),
                       user_id UUID,
                       CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id)
);
