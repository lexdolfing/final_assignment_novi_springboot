INSERT INTO demos (id, email) VALUES (1, 'test@email.com');
INSERT INTO demo_replies (id, admin_comments, has_been_replied_to) VALUES (1, 'super leuk lied man', true);

-- onderstaand werkt niet
-- INSERT INTO demos (artist_name, song_name, email, mp3_file, song_elaboration)
-- VALUES ('John Smith', 'My Song', 'john.smith@example.com',
--         '${new String(java.util.Base64.getEncoder().encode(java.nio.file.Files.readAllBytes(new java.io.File("classpath:audio/Free_Test_Data_500KB_MP3.mp3").toPath())))}',
--         'This is a description of my song.');