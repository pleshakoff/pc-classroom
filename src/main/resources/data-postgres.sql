INSERT INTO public.users (id, email, family_name, first_name, middle_name, phone)
VALUES (1, 'admin@parcom.com', 'Петрова', 'Мария', 'Павловна', '79151111111');
-- eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbkBwYXJjb20uY29tIiwidXNlciI6ImFkbWluQHBhcmNvbS5jb20iLCJpZFVzZXIiOjEsImlkR3JvdXAiOjIxLCJhdXRob3JpdGllcyI6IlJPTEVfQURNSU4iLCJpYXQiOjE1NzU4MTIyNjIsImV4cCI6MTU3NTgxNDA2Mn0.fl9JpXVnYCQOBkZ7Ht4_C0Ax3UjKgnSwEC-9MjaoTWRHd4imSgeuBMcJpdqYkYvDqxcfZSU8hWI4ThOVsYYB4w

INSERT INTO public.users (id, email, family_name, first_name, middle_name, phone)
VALUES (2, 'mom@parcom.com', 'Иванова', 'Василиса', 'Васильевна', '79032222222');
-- eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtb21AcGFyY29tLmNvbSIsInVzZXIiOiJtb21AcGFyY29tLmNvbSIsImlkVXNlciI6MiwiaWRHcm91cCI6MjEsImF1dGhvcml0aWVzIjoiUk9MRV9NRU1CRVIiLCJpYXQiOjE1NzU4MTIzNzUsImV4cCI6MTU3NTgxNDE3NX0.2_6xmj8Bet48pFPD8BCfInkynWpKmkh2Zn7ay7vce4eKoNHD8PQNwkIyj1ZITnYGARb5-EIONHYfrPMtbNzPfQ

INSERT INTO public.users (id, email, family_name, first_name, middle_name, phone)
VALUES (3, 'dad@parcom.com', 'Иванов', 'Иван', 'Иванович', '79263333333');
-- eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkYWRAcGFyY29tLmNvbSIsInVzZXIiOiJkYWRAcGFyY29tLmNvbSIsImlkVXNlciI6MywiaWRHcm91cCI6MjEsImF1dGhvcml0aWVzIjoiUk9MRV9QQVJFTlQiLCJpYXQiOjE1NzU4MTI0NjksImV4cCI6MTU3NTgxNDI2OX0.B0qh1p7TsMtXhyxz_HuyJWS0hp7MNnPrMQfsgMtRWjs_sB8nHMI7SfwBV2aeQTp_MSof3zWpswPvZOs7FUYupQ


INSERT INTO public.school (id, name)
VALUES (11, 'Лицей № 5 имени Бертольда Шварца');


INSERT INTO public.groups (id, name, id_school)
VALUES (21, 'Пятый класс', 11);

INSERT INTO public.groups (id, name, id_school)
VALUES (22, '1 A', 11);



INSERT INTO public.student (id, birth_day, family_name, first_name, middle_name, id_group)
VALUES (31, '2010-12-08', 'Иванов', 'Николай', 'Иванович', 21);

INSERT INTO public.student (id, birth_day, family_name, first_name, middle_name, id_group)
VALUES (32, '2010-12-08', 'Иванова', 'Алиса', 'Ивановна', 21);

INSERT INTO public.student (id, birth_day, family_name, first_name, middle_name, id_group)
VALUES (33, '2010-05-08', 'Петров', 'Петр', null, 21);

INSERT INTO public.student (id, birth_day, family_name, first_name, middle_name, id_group)
VALUES (34, '2013-05-12', 'Петрова', 'Маша', null, 22);


INSERT INTO public.student_to_user (id, id_student, id_user)
VALUES (41, 33, 1);

INSERT INTO public.student_to_user (id, id_student, id_user)
VALUES (42, 34, 1);

INSERT INTO public.student_to_user (id, id_student, id_user)
VALUES (43, 31, 2);

INSERT INTO public.student_to_user (id, id_student, id_user)
VALUES (44, 32, 2);

INSERT INTO public.student_to_user (id, id_student, id_user)
VALUES (45, 31, 3);

INSERT INTO public.student_to_user (id, id_student, id_user)
VALUES (46, 32, 3);


INSERT INTO public.group_to_user (id, id_group, id_user)
VALUES (51, 21, 1);

INSERT INTO public.group_to_user (id, id_group, id_user)
VALUES (52, 21, 2);

INSERT INTO public.group_to_user (id, id_group, id_user)
VALUES (53, 21, 3);

INSERT INTO public.group_to_user (id, id_group, id_user)
VALUES (54, 22, 1);

SELECT setval('hibernate_sequence', 100, true);