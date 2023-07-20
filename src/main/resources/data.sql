
INSERT INTO demos (id, email, artist_name, song_name, song_elaboration)
VALUES (102, 'user@email.com', 'DJ Hans', 'Electric slide double', 'I was so inspired by old school house music');

INSERT INTO roles (id, role_name) VALUES (101, 'ROLE_USER');

INSERT INTO users (id, email, enabled, password, role_id)
VALUES (101, 'user@email.com', true, '$2a$10$jEzlHDM/HSjhqWHSmJIwl.kCLhscVtsPnqGsWF1/HTTwxaEZDxpiG', 101);

INSERT INTO dj (id, first_name, last_name, artist_name, user_id)
VALUES (101, 'Hans', 'Platendraaier', 'DJ Hans', 101);

INSERT INTO roles (id, role_name)
VALUES (102, 'ROLE_ADMIN');

INSERT INTO users (id, email, enabled, password, role_id)
VALUES (102, 'admin@elevaterecords.nl', true, '$2a$10$jEzlHDM/HSjhqWHSmJIwl.kCLhscVtsPnqGsWF1/HTTwxaEZDxpiG', 102);

INSERT INTO talentmanagers (id, first_name, last_name, manager_name, user_id)
VALUES (101, 'Annie', 'Schilder', 'Schilder Talents', 102);

INSERT INTO demos (id, email, artist_name, song_name, song_elaboration, dj_id, talent_manager_id)
VALUES (101, 'test@email.com', 'DJ Dolfing', 'Electric slide', 'I was so inspired by old school house music', 101, 101);
