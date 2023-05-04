INSERT INTO demos (id, email, artist_name, song_name, song_elaboration)
VALUES (1, 'test@email.com', 'DJ Dolfing', 'Electric slide', 'I was so inspired by old school house music');
INSERT INTO demos (id, email, artist_name, song_name, song_elaboration)
VALUES (2, 'test2@email.com', 'DJ Susanne', 'Electric slide double', 'I was so inspired by old school house music');

INSERT INTO demo_replies (id, admin_comments, has_been_replied_to) VALUES (1, 'super leuk lied man', true);
INSERT INTO dj (id, first_name, last_name, artist_name) VALUES (1, 'Lex', 'Dolfing', 'DJ Dolfing');
INSERT INTO talentmanagers (id, first_name, last_name, manager_name) VALUES (1, 'Annie', 'Schilder', 'Schilder Talents');