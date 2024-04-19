DROP TABLE IF EXISTS bookmark CASCADE;
DROP TABLE IF EXISTS comment CASCADE;
DROP TABLE IF EXISTS feedback CASCADE;
DROP TABLE IF EXISTS rating CASCADE;
DROP TABLE IF EXISTS link CASCADE;
DROP TABLE IF EXISTS user_roles CASCADE;
DROP TABLE IF EXISTS role CASCADE;
DROP TABLE IF EXISTS app_user CASCADE;

CREATE TABLE app_user
(
    id         BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name  VARCHAR(100) NOT NULL,
    login      VARCHAR(100) NOT NULL,
    password   VARCHAR(100) NOT NULL
);
CREATE TABLE role
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);
CREATE TABLE user_roles
(
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES app_user (id),
    FOREIGN KEY (role_id) REFERENCES role (id)
);
CREATE TABLE link
(
    id            BIGSERIAL PRIMARY KEY,
    url           TEXT,
    title         TEXT,
    description   TEXT,
    creation_date DATE,
    user_id       BIGINT,
    FOREIGN KEY (user_id) REFERENCES app_user (id)
);
CREATE TABLE rating
(
    id      BIGSERIAL PRIMARY KEY,
    rating  BIGINT,
    user_id BIGINT,
    link_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES app_user (id),
    FOREIGN KEY (link_id) REFERENCES link (id)
);
CREATE TABLE feedback
(
    id             BIGSERIAL PRIMARY KEY,
    questionAnswer TEXT,
    stars          INT,
    isPrivate      BOOLEAN,
    content        TEXT,
    creation_date  DATE,
    user_id        BIGINT,
    FOREIGN KEY (user_id) REFERENCES app_user (id)
);
CREATE TABLE comment
(
    id            BIGSERIAL PRIMARY KEY,
    content       TEXT,
    creation_date DATE,
    user_id       BIGINT,
    link_id       BIGINT,
    FOREIGN KEY (user_id) REFERENCES app_user (id),
    FOREIGN KEY (link_id) REFERENCES link (id)
);
CREATE TABLE bookmark
(
    id      BIGSERIAL PRIMARY KEY,
    user_id BIGINT,
    link_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES app_user (id),
    FOREIGN KEY (link_id) REFERENCES link (id)
);
INSERT INTO app_user (first_name, last_name, login, password)
VALUES ('Admin', 'User', 'admin', '$2a$10$6FSLD1xyX1k6mD8DTaCSi.DNDr/SEpKJGDspnC/P5e3qbHoLOCZD.');

INSERT INTO role (name)
VALUES ('ADMIN');
INSERT INTO role (name)
VALUES ('USER');

INSERT INTO user_roles (user_id, role_id)
VALUES (1, 1);
INSERT INTO user_roles (user_id, role_id)
VALUES (1, 2);