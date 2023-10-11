INSERT INTO category(id, title) VALUES
(nextval('category_id_seq'), 'Алкоголь'),
(nextval('category_id_seq'), 'Табак'),
(nextval('category_id_seq'), 'Текстиль'),
(nextval('category_id_seq'), 'Парфюмерия');

INSERT INTO user_role(id, title, permissions) VALUES
(nextval('user_role_id_seq'), 'ROLE_ALCOHOL','{"categoryPermissions" : {"categories": ["Алкоголь"]}}'),
(nextval('user_role_id_seq'), 'ROLE_TOBACCO','{"categoryPermissions" : {"categories": ["Табак"]}}'),
(nextval('user_role_id_seq'), 'ROLE_TEXTILE','{"categoryPermissions" : {"categories": ["Текстиль"]}}'),
(nextval('user_role_id_seq'), 'ROLE_PERFUMERY','{"categoryPermissions" : {"categories": ["Парфюмерия"]}}'),
(nextval('user_role_id_seq'), 'ROLE_USER','{"categoryPermissions" : {"categories": []}}'),
(nextval('user_role_id_seq'), 'ROLE_ADMIN','{"categoryPermissions": {"categories": ["Алкоголь", "Табак", "Текстиль", "Парфюмерия"]}}');
