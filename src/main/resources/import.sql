INSERT INTO roles(role_id, description, name) VALUES (1, 'Admin role', 'ADMIN');
INSERT INTO roles(role_id, description, name) VALUES (2, 'User role', 'USER');


INSERT INTO users(user_id, email, password, first_name, last_name, age, enabled) VALUES (1, 'admin@admin.com','$2y$12$xgBdMuMChnxO/jNf/vWYHOTwlu5f/fMkWTcPLFkoa9.BOTmlZuDWq', 'admin1', 'admin2', 33, true );
INSERT INTO users(user_id, email, password, first_name, last_name, age, enabled) VALUES (2, 'test@test.com','$2y$12$w4AWhfBDjTYZNJqcrskd2.qC0CAG7FHMLQbjgjNLXU3GfjCiaQtpK', 'test1', 'test2', 33, true );

INSERT INTO users_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO users_roles (user_id, role_id) VALUES (2, 2);
