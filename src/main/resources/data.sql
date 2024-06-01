INSERT INTO users(login, password, email)
VALUES ('admin', '$2a$04$k9/iISbGfw4u4RNAEnP.muVfb2mcyqqZ4Reb3FWrrB.6N2UGhBO1K', 'email@a.ru'),
       ('user', '$2a$04$PMX/Lu12p4VzUv3z3z1nbedUYgAyfTnukPUYNR1cT0oBxqHJ9nIVe', 'email@b.ru');

INSERT INTO user_roles(user_id, role)
VALUES (1, 'ADMIN'),
       (1, 'USER'),
       (2, 'USER');