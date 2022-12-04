--Test are run on H2 in-mem DB, so it needs to be populated
insert into roles (id, role) values (1, 'ROLE_ADMIN');
insert into roles (id, role) values (2, 'ROLE_USER');
insert into users (id, username, email, password, isactive, roleid)
values (100, 'TestUser', 'Test', '{bcrypt}$2a$10$GNMVhjJNf67Y7KOUg1Pyzup.UzyqpOVRFjYHurylNJG/RU7mKMzr.', 1, 2);
insert into posts (id, body, userid, likecount, isactive)
values (100, 'TestPost', 100, 0, 1);