DELETE FROM messages;
ALTER TABLE messages ALTER COLUMN id RESTART WITH 1;
DELETE FROM user_role;
DELETE FROM users;
ALTER TABLE users ALTER COLUMN id RESTART WITH 1;
DELETE FROM chatrooms;
ALTER TABLE chatrooms ALTER COLUMN id RESTART WITH 1;
DELETE FROM user_chatroom;
DELETE FROM roles;
ALTER TABLE roles ALTER COLUMN id RESTART WITH 1;
