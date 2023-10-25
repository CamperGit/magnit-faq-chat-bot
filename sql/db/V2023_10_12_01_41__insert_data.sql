INSERT INTO category(id, title) VALUES
(nextval('category_id_seq'), 'Алкоголь'),
(nextval('category_id_seq'), 'Табак'),
(nextval('category_id_seq'), 'Текстиль'),
(nextval('category_id_seq'), 'Парфюмерия');

INSERT INTO user_role(id, title) VALUES
(nextval('user_role_id_seq'), 'ROLE_ALCOHOL'),
(nextval('user_role_id_seq'), 'ROLE_TOBACCO'),
(nextval('user_role_id_seq'), 'ROLE_TEXTILE'),
(nextval('user_role_id_seq'), 'ROLE_PERFUMERY'),
(nextval('user_role_id_seq'), 'ROLE_USER'),
(nextval('user_role_id_seq'), 'ROLE_ADMIN');
