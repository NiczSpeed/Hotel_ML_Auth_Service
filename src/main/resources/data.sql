INSERT INTO privilege (uuid, name)
values ('260debd2-93fb-4e0e-8815-78d9eb98d196', 'READ'),
       ('996928fb-f256-40d5-918f-4637bd1ae042', 'WRITE')
on conflict DO NOTHING;

INSERT INTO roles (uuid, name)
values ('3f112137-d9f2-4247-bf58-57ba0f59a5d5', 'ADMIN'),
       ('3dd94a96-7b96-4b61-af9a-a684e0c5e48e', 'USER')
on conflict DO NOTHING;

INSERT INTO roles_privileges(privilege_uuid, role_uuid)
values ('260debd2-93fb-4e0e-8815-78d9eb98d196', '3dd94a96-7b96-4b61-af9a-a684e0c5e48e'),
       ('260debd2-93fb-4e0e-8815-78d9eb98d196', '3f112137-d9f2-4247-bf58-57ba0f59a5d5'),
       ('996928fb-f256-40d5-918f-4637bd1ae042', '3f112137-d9f2-4247-bf58-57ba0f59a5d5')
on conflict DO NOTHING;

INSERT INTO users(creation_date, is_account_non_expired, is_enabled, uuid, email, first_name, last_name, password)
VALUES (CURRENT_DATE, true, true, '420f39e0-0a86-42c5-a34c-ee068c34fd89', 'admin@admin.com', 'admin', 'admin', '$2a$12$3ybnY6bwBe6lTsC/sQALV.1RDm/tzxGR4lTcGsK18C1B42yWsMqui')
on conflict DO NOTHING;

INSERT INTO users_role(role_uuid, user_uuid)
VALUES ('3f112137-d9f2-4247-bf58-57ba0f59a5d5', '420f39e0-0a86-42c5-a34c-ee068c34fd89')
on conflict DO NOTHING;