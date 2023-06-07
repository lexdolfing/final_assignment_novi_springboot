
INSERT INTO demos (id, email, artist_name, song_name, song_elaboration)
VALUES (102, 'test2@email.com', 'DJ Susanne', 'Electric slide double', 'I was so inspired by old school house music');

INSERT INTO demo_replies (id, admin_comments, has_been_replied_to) VALUES (101, 'super leuk lied man', true);
INSERT INTO dj (id, first_name, last_name, artist_name) VALUES (101, 'Lex', 'Dolfing', 'DJ Dolfing');
INSERT INTO talentmanagers (id, first_name, last_name, manager_name) VALUES (101, 'Annie', 'Schilder', 'Schilder Talents');
INSERT INTO demos (id, email, artist_name, song_name, song_elaboration, dj_id)
VALUES (101, 'test@email.com', 'DJ Dolfing', 'Electric slide', 'I was so inspired by old school house music', 101);
