insert into permission (name) values
    ('CREATE_AD'), ('VIEW_AD'), ('UPDATE_AD'), ('DELETE_AD'), ('POST_COMMENT'),
    ('READ_COMMENT'),  ('DENY_COMMENT'), ('APPROVE_COMMENT'), ('POST_RATE'), ('READ_RATE'),
    ('UPDATE_RATE'), ('CREATE_REQUEST'), ('LOGIN'), ('RECEIVE_MESSAGE'), ('REGISTER'),
    ('RENT_A_CAR'), ('SEARCH'), ('SEND_MESSAGE'), ('UPLOAD_PHOTO'), ('DELETE_RATE'),
    ('CREATE_AGENT'), ('READ_REQUEST'), ('APPROVE_REQUEST'), ('VIEW_IMAGE'), ('CRUD_CAR_BRAND'),
    ('CRUD_CAR_MODEL'), ('CRUD_CAR_CLASS'), ('CRUD_FUEL_TYPE'), ('CRUD_GEARSHIFT_TYPE'), ('APPROVE_AGENT'),
    ('DENY_AGENT'), ('CHANGE_PERMISSION');

insert into authority (name) values ('ROLE_ADMIN'), ('ROLE_AGENT'), ('ROLE_SIMPLE_USER'),
    ('ROLE_REVIEWER_USER'), ('ROLE_MESSAGE_USER'), ('ROLE_RENT_USER'), ('ROLE_COMMENT_USER'),
    ('ROLE_REQUEST'), ('ROLE_AD_USER');

insert into authorities_permissions (authority_id, permission_id) values
    (1, 1), (1, 2), (1, 3), (1, 4), (1, 5), (1, 6), (1, 7), (1, 8), (1, 9), (1, 10), (1, 11), (1, 12), (1, 13), (1, 14),
    (1, 15), (1, 16), (1, 17), (1, 18), (1, 19), (1, 21), (1, 22), (1, 23), (1, 25), (1, 26), (1, 27), (1, 28), (1, 29), (1, 30), (1, 31), (1, 32),
    (2, 1), (2, 2), (2, 3), (2, 4), (2, 19), (2, 22), (2, 23), (2, 24), (2, 12),
    (3, 13), (3, 15), (3, 17), (3, 2), (3, 6), (3, 10), (3, 24), (3, 14),
    (4, 9), (4, 10), (4, 11), (4, 20), (4, 24),
    (5, 14), (5, 18), (5, 24),
    (6, 16), (6, 12), (6, 24),
    (7, 5), (7, 6), (7, 7), (7, 8), (7, 24),
    (8, 22), (8, 24),
    (9, 1);

-- admin@gmail.com -> Admin123!!!
-- agent@gmail.com -> Agent123!!!
-- agent1@gmail.com -> Agent123!!!
-- customer@gmail.com -> Customer123!!!
-- customer1@gmail.com -> Customer123!!!
insert into user_entity (id, username, password, deleted, has_signed_in, last_password_reset_date, user_role) values
    ('9bbbd6c1-34b4-4ea6-8889-be247cfebc34', 'admin@gmail.com', '$2y$10$UFTyoDVYFFUqlb0lnKfoKe7H/EbQOqZH.ZYHf6sOYiOWSRCmpcJ5K', false, false, '2019-10-01 21:58:58.508-07', 2),
    ('105496cd-30f2-4b62-8082-cc14d282e845', 'agent@gmail.com', '$2a$10$zQU7XEdDSMvxt13Xkjs3X.CCY64edvCS0ZXcgqPtU8FhSYVUhtnau', false, false, '2019-10-01 21:58:58.508-07', 1),
    ('d0535564-08ec-464c-a2db-d930d2c4fcde', 'agent1@gmail.com', '$2a$10$zQU7XEdDSMvxt13Xkjs3X.CCY64edvCS0ZXcgqPtU8FhSYVUhtnau', false, false, '2019-10-01 21:58:58.508-07', 1),
    ('4fb1b61b-cc4e-45c3-86f0-cbf50de4cf54', 'customer@gmail.com', '$2a$10$UJEbOrAMWN/bh8tEPHt.Z.fD2RX.T0e0MXNuZEFCEFTNAjHkdAVju', false, false, '2019-10-01 21:58:58.508-07', 0),
    ('b9362264-17db-411e-8ed0-db8310cba9f1', 'customer2@gmail.com', '$2a$10$UJEbOrAMWN/bh8tEPHt.Z.fD2RX.T0e0MXNuZEFCEFTNAjHkdAVju', false, false, '2020-10-01 21:58:58.508-07', 0);

insert into user_authority (user_id, authority_id) values
    ('9bbbd6c1-34b4-4ea6-8889-be247cfebc34', 1),
    ('105496cd-30f2-4b62-8082-cc14d282e845', 2),
    ('105496cd-30f2-4b62-8082-cc14d282e845', 3),
    ('105496cd-30f2-4b62-8082-cc14d282e845', 4),
    ('105496cd-30f2-4b62-8082-cc14d282e845', 5),
    ('105496cd-30f2-4b62-8082-cc14d282e845', 7),
    ('d0535564-08ec-464c-a2db-d930d2c4fcde', 2),
    ('d0535564-08ec-464c-a2db-d930d2c4fcde', 3),
    ('d0535564-08ec-464c-a2db-d930d2c4fcde', 4),
    ('d0535564-08ec-464c-a2db-d930d2c4fcde', 5),
    ('d0535564-08ec-464c-a2db-d930d2c4fcde', 7),
    ('4fb1b61b-cc4e-45c3-86f0-cbf50de4cf54', 3),
    ('4fb1b61b-cc4e-45c3-86f0-cbf50de4cf54', 5),
    ('4fb1b61b-cc4e-45c3-86f0-cbf50de4cf54', 6),
    ('4fb1b61b-cc4e-45c3-86f0-cbf50de4cf54', 7),
--    ('4fb1b61b-cc4e-45c3-86f0-cbf50de4cf54', 8),
    ('b9362264-17db-411e-8ed0-db8310cba9f1', 3),
    ('b9362264-17db-411e-8ed0-db8310cba9f1', 6),
--    ('b9362264-17db-411e-8ed0-db8310cba9f1', 8),
    ('b9362264-17db-411e-8ed0-db8310cba9f1', 7);

insert into admin (id, first_name, last_name, user_id) values
    ('51d5e58d-ac22-4233-a1dc-e4251a18e815', 'Ms', 'Misoni', '9bbbd6c1-34b4-4ea6-8889-be247cfebc34');

insert into agent (id, bank_account_number, date_founded, name, tin, user_id, address, simple_user_id) values
    ('b38a64e2-299b-4a05-bc30-5a45dd2ebdc0', 'DE72 3702 0500 0009 7097 00', '2020/02/25', 'Marko Kraljevic',  '123-45-6789', '105496cd-30f2-4b62-8082-cc14d282e845', 'Serbia,Novi Sad,Svetislava Kasapinovica, 69', null),
    ('c72721c4-437f-4a06-b3cc-00b9a86056bc', '0500 0009 3702 FE22 7097 00', '2020/02/25', 'Dragan Topalovic', '321-54-9876', 'd0535564-08ec-464c-a2db-d930d2c4fcde', 'Serbia,Beograd,Knez Mihailova, 15', null);

insert into simple_user (id, address, first_name, last_name, request_status, ssn, user_id) values
    ('1cfe4238-9b0c-4611-abea-ddd20b4cc415', 'Serbia, Novi Sad, Pionirska,26', 'Somi', 'Misoni', 'APPROVED', '1547854896523', '4fb1b61b-cc4e-45c3-86f0-cbf50de4cf54'),
    ('9220c03b-b0b5-46af-a821-249e2a97dcaa', 'Serbia, Nis, Njegoseva, 55', 'Didi', 'Mimica-Kostovic', 'APPROVED', '1547858576523', 'b9362264-17db-411e-8ed0-db8310cba9f1');

