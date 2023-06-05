-- наполните таблицу пользователей первыми тестовыми записями
INSERT INTO public.cat_user (id, username, nickname) VALUES ('tom', 'Thomas C. Andersun', 'neoc@t');
INSERT INTO public.cat_user (id, username, nickname) VALUES ('grumpy', 'Альфред Хичкот', 'sca_a_a_ry');
INSERT INTO public.cat_user (id, username, nickname) VALUES ('puss_in_boots', 'Basileus Felis F.', 'under_wood');

INSERT INTO public.cat_post (author_id, description, photo_url, creation_date) VALUES ('tom', 'я и мой кот Том', 'file:///storage/catsgram/tom/1/image.png', '2022-03-28 19:22:29.000000');
INSERT INTO public.cat_post (author_id, description, photo_url, creation_date) VALUES ('tom', 'первый пост)', 'file:///storage/catsgram/tom/2/image.png', '2022-03-13 15:32:54.000000');
INSERT INTO public.cat_post (author_id, description, photo_url, creation_date) VALUES ('grumpy', 'ВсЕм ПрИвЕт!!!111', 'file:///storage/catsgram/grumpy/1/image.png', '2022-02-28 15:24:03.000000');
INSERT INTO public.cat_post (author_id, description, photo_url, creation_date) VALUES ('grumpy', 'Мой новый пост!!111', 'file:///storage/catsgram/grumpy/2/image.png', '2022-03-08 15:24:03.000000');

INSERT INTO public.cat_follow (author_id, follower_id) VALUES('grumpy', 'tom');
