insert into user (first_name, last_name, email, password, role, is_visible) values
    ('Adam', 'Kowalski', 'adam@gmail.com', 'password', 'ADMIN', true),
    ('Marcin', 'Grabowski', 'marcin@mail.pl', 'password', 'MODERATOR', true),
    ('Paulina', 'Pawłowicz', 'paulina@hehe.com', 'password', 'USER', true);

insert into travel_stop (name, latitude, longitude, is_visible) values
    ('PJATK', '20.993419', '52.223731', true),
    ('Galeria mokotów', '21.002459', '52.179929', true),
    ('Park trampolin', '20.961083', '52.212200', true),

    ('Muzeum Pałacu Króla Jana III w Wilanowie', '21.089915', '52.165296', true);

insert into travel_route (name, difficulty, description, is_visible) values
    ('Wycieczka po Warszawie', 'MEDIUM', 'Trasa dla osób odwiedzających Warszawe', true);

insert into blog_post (content, creation_date_time, last_edit_date_time, user_id, is_visible) values
    ('Przechadzka po Warszawie. Nauka-zakupy-spacer', '2021-10-09', null, 3, true),
    ('Ciekawa i historyczna wycieczka po Zamku.', '2021-10-09', null, 2, true);

insert into blog_comment(content, creation_date_time, rating, user_id, is_visible) values
    ('Ciekawa trasa!','2021-10-10', 4, 1, true),
    ('Duzo atrakcji, a trampoliny - SUPER', '2021-11-10', 5, 1, true),
    ('Malo zieleni, trasa bez rewelacji', '2021-10-22', 1, 1, true),
    ('Calkiem fajna :)',' 2021-10-10', 4, 2, true);

insert into tag(name, is_visible) values
    ('Warszawa', true),
    ('Wycieczka', true),
    ('Ciekawa', true),
    ('Zamki', true);


insert into BLOG_POSTS_TAGS(TAG_ID, BLOG_POST_ID) values
    (1, 1),
    (2, 1),
    (3, 1),
    (4, 2);

insert into travel_stop_travel_route(TRAVEL_STOP_ID, TRAVEL_ROUTE_ID) values
    (1, 1),
    (2, 1),
    (3, 1);

insert into blog_posts_travel_routes(TRAVEL_ROUTE_ID, BLOG_POST_ID) values
    (1, 1);

insert into blog_posts_travel_stop(TRAVEL_STOP_ID, BLOG_POST_ID) values
    (4, 1);

