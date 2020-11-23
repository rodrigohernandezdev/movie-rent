INSERT INTO users(id, email, username, password, age) VALUES (1, 'admin@admin.com', 'admin', '$2y$12$Fh8TzMremEuT3QydgqI7EuwS7nE/Dvw8Z5KWlnPyh/2q/LDQU10.C', 33);

INSERT INTO roles(id, description, name) VALUES (1, 'Admin role', 'ADMIN');

INSERT INTO users_roles (user_id, role_id) VALUES (1, 1);
