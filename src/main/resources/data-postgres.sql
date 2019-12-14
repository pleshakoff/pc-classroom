INSERT INTO public.users (id, email, family_name, first_name, middle_name, phone)
VALUES (1, 'admin@mail.com', 'Волан-де-Морт', 'Лорд', null, '79151111111');

INSERT INTO public.users (id, email, family_name, first_name, middle_name, phone)
VALUES (2, 'molly@weasley.com', 'Уизли', 'Молли', null, '79032222222');

INSERT INTO public.users (id, email, family_name, first_name, middle_name, phone)
VALUES (3, 'artur@weasley.com', 'Уизли', 'Артур', 'Септимус', '79263333333');


INSERT INTO public.school (id, name)
VALUES (11, 'Академия Чародейства и Волшебства «Хогвартс»');


INSERT INTO public.groups (id, name, id_school)
VALUES (21, 'Грифиндор 3 курс', 11);

INSERT INTO public.groups (id, name, id_school)
VALUES (22, 'Грифиндор 1 курс', 11);


INSERT INTO public.student (id, birth_day, family_name, first_name, middle_name, id_group)
VALUES (31, '1980-07-31', 'Потер', 'Гарри', null, 21);

INSERT INTO public.student (id, birth_day, family_name, first_name, middle_name, id_group)
VALUES (32, '1980-03-01', 'Уизли', 'Рон', 'Артурович', 21);

INSERT INTO public.student (id, birth_day, family_name, first_name, middle_name, id_group)
VALUES (33, '1979-09-19', 'Грейнджер', 'Гермиона', null, 21);

INSERT INTO public.student (id, birth_day, family_name, first_name, middle_name, id_group)
VALUES (34, '2013-05-12', 'Уизли', 'Джинни', null, 22);


INSERT INTO public.student_to_user (id, id_student, id_user)
VALUES (41, 31, 1);

INSERT INTO public.student_to_user (id, id_student, id_user)
VALUES (42, 32, 2);

INSERT INTO public.student_to_user (id, id_student, id_user)
VALUES (43, 32, 3);

INSERT INTO public.student_to_user (id, id_student, id_user)
VALUES (44, 33, 2);

INSERT INTO public.student_to_user (id, id_student, id_user)
VALUES (45, 34, 2);

INSERT INTO public.student_to_user (id, id_student, id_user)
VALUES (46, 34, 3);



INSERT INTO public.group_to_user (id, id_group, id_user)
VALUES (51, 21, 1);

INSERT INTO public.group_to_user (id, id_group, id_user)
VALUES (52, 21, 2);

INSERT INTO public.group_to_user (id, id_group, id_user)
VALUES (53, 21, 3);

INSERT INTO public.group_to_user (id, id_group, id_user)
VALUES (54, 22, 2);

INSERT INTO public.group_to_user (id, id_group, id_user)
VALUES (55, 22, 3);


SELECT setval('hibernate_sequence', 100, true);