insert into user (first_name, last_name, email, password, role) values
    ('Adam', 'Kowalski', 'adam@gmail.com', 'password', 'ADMIN'),
    ('Marcin', 'Grabowski', 'marcin@mail.pl', 'password', 'MODERATOR'),
    ('Paulina', 'Pawłowicz', 'paulina@hehe.com', 'password', 'USER');

insert into travel_stop (name, latitude, longitude) values
    ('PJATK', '20.993419', '52.223731'),
    ('Galeria mokotów', '21.002459', '52.179929'),
    ('Park trampolin', '20.961083', '52.212200'),

    ('Muzeum Pałacu Króla Jana III w Wilanowie', '21.089915', '52.165296');

insert into travel_route (name, difficulty, description) values
    ('Wycieczka po Warszawie', 'MEDIUM', 'Trasa dla osób odwiedzających Warszawe');

insert into blog_post (content, creation_date_time, last_edit_date_time, user_id) values
    ('Przechadzka po Warszawie. Nauka-zakupy-spacer', '2021-10-09', null, 3),
    ('Wszedzie lepiej gdzie nas nie ma, GeaWalk!', '2021-10-09', null, 2);

insert into blog_comment(content, creation_date_time, rating, user_id) values
    ('Ciekawa trasa!','2021-10-10', 4, 1),
    ('Duzo atrakcji, a trampoliny - SUPER', '2021-11-10', 5, 1),
    ('Malo zieleni, trasa bez rewelacji', '2021-10-22', 1, 1),
    ('Calkiem fajna :)',' 2021-10-10', 4, 2);

insert into tag(name) into



insert into travel_stop_travel_route(TRAVEL_STOP_ID, TRAVEL_ROUTE_ID) values
    (1, 1),
    (2, 1),
    (3, 1);

insert into blog_posts_travel_routes(TRAVEL_ROUTE_ID, BLOG_POST_ID) values
    (1, 1);

insert into blog_posts_travel_stop(TRAVEL_STOP_ID, BLOG_POST_ID) values
    (4, 1);

