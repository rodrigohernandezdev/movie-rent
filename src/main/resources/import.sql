INSERT INTO users(user_id, email, password, age, enabled) VALUES (1, 'admin@admin.com', '$2y$12$vPZm9Wqfu7AbRuI3JMwaN.ZGpCaxx64fbwjq5p8lsP/Xim74hNXA.', 33, true );
INSERT INTO users(user_id, email, password, age, enabled) VALUES (2, 'test@test.com', '$2y$12$vPZm9Wqfu7AbRuI3JMwaN.ZGpCaxx64fbwjq5p8lsP/Xim74hNXA.', 33, false );

INSERT INTO roles(role_id, description, name) VALUES (1, 'Admin role', 'ADMIN');
INSERT INTO roles(role_id, description, name) VALUES (2, 'User role', 'USER');

INSERT INTO users_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO users_roles (user_id, role_id) VALUES (2, 2);
