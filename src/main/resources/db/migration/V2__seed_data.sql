-- Seed Roles
INSERT INTO roles (name) VALUES ('ADMIN');
INSERT INTO roles (name) VALUES ('CUSTOMER');
INSERT INTO roles (name) VALUES ('DRIVER');

-- Seed Users
INSERT INTO users (name, email, password)
VALUES ('System Admin', 'admin@cymelle.com', '$2a$12$gKCauTA.GzNR3ZQb8lb2eu9qeDbuYLHgYF6KR/I2y8e2UQIaoihLe');

-- Seed User Roles
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id FROM users u, roles r
WHERE u.email = 'admin@cymelle.com' AND r.name = 'ADMIN';