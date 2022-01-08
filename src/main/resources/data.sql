insert into user (first_name, last_name, email, password, role, visible)
values ('Adam', 'Kowalski', 'adam@gmail.com', 'password', 'ADMIN', true),          --1
       ('Paweł', 'Brawo', 'pawel@gmail.com', 'password', 'ADMIN', true),           --2

       ('Arek', 'Digomowski', 'arek@gmail.com', 'password', 'MODERATOR', true),    --3
       ('Marcin', 'Grabowski', 'marcin@gmail.com', 'password', 'MODERATOR', true), --4

       ('Sebastian', 'Powal', 'sebastian@gmail.com', 'password', 'USER', true),    --5
       ('Maciek', 'Fikola', 'maciek@gmail.com', 'password', 'USER', true),         --6
       ('Paulina', 'Pawłowicz', 'paulina@gmail.com', 'password', 'USER', true),    --7
       ('Mateusz', 'Ustew', 'mateusz@gmail.com', 'password', 'USER', true); --8

insert into travel_stop (name, latitude, longitude, visible, country, city, street)
values ('PJATK', '52.223941713710204', '20.99456713842226', true, 'Poland', 'Warszawa', 'Koszykowa 86'),             --1
       ('Galeria mokotów', '52.17986650656597', '21.004595882599652', true, 'Poland', 'Warszawa', 'Wołoska 12'),     --2
       ('Stacja Grawitacja', '52.21286546599547', '20.96246636355756', true, 'Poland', 'Warszawa',
        'Aleja Bohaterów Września 12'),                                                                              --3

       ('Muzeum Pałacu Króla Jana III w Wilanowie', '52.165338097257546', '21.09058362913881', true, 'Polska',
        'Warszawa', 'Stanisława Kostki Potockiego 10/16'),                                                           --4

       ('Bazylika Świętego Piotra', '41.90228751188043', '12.454159580944626', true, 'Vatican', 'Roma',
        'Forum Sancti Petri'),                                                                                       --5
       ('Porta Pertusa', '41.901329277006845', '12.447389663927183', true, 'Vatican', 'Roma', 'Forum Sancti Petri'), --6
       ('Muzea Watykańskie', '41.906388500092824', '12.453673798804596', true, 'Vatican', 'Roma', 'Viale Vaticano'), --7
       ('Fontana di Porta Cavalleggeri', '41.9006810155548', '12.4561728483282', true, 'Vatican', 'Roma',
        'Forum Sancti Petri'),                                                                                       --8

       ('Meczet Ahmada Ibn Tuluna', '30.0307473551421', '31.25591957837077', true, 'Egypt', 'Egypt-city',
        'Egypt-street'),                                                                                             --9
       ('Uniwersytet Kairski', '30.028454548217717', '31.20874850766373', true, 'Egypt', 'Egypt-city',
        'Egypt-street 1'),                                                                                           --10
       ('Desert Egypt Safari', '29.982731287249205', '31.163992204860232', true, 'Egypt', 'Egypt-city',
        'Egypt-street 2'),                                                                                           --11

       ('Los Angeles County Museum of Art', '34.06487300861231', '-118.3567135201173', true, 'USA', 'USA-City',
        'USA-Street'),                                                                                               --12
       ('Walt Disney Concert Hall', '34.05289236567532', '-118.24912159231603', true, 'USA', 'USA-City 1',
        'USA-Street'),                                                                                               --13
       ('Rose Bowl', '34.16602787734609', '-118.1553944820622', true, 'USA', 'USA-City 2', 'USA-Street'),            --14
       ('Los Angeles Valley College', '34.169720706444274', '-118.41528980288447', true, 'USA', 'USA-City 3',
        'USA-Street'),                                                                                               --15
       ('The Getty', '34.07592969428845', '-118.47434131474418', true, 'USA', 'USA-City 4', 'USA-Street'),           --16

       ('Cowboys Casino', '51.0438273528732', '-114.04098858356981', true, 'USA', 'USA-City 4', 'USA-Street'); --17

insert into travel_route (name, difficulty, description, visible)
values ('Wycieczka po Warszawie', 'MEDIUM', 'Trasa dla osób odwiedzających Warszawe', true), --1
       ('Watykan za dnia', 'HARD', 'Swiete miejsca w Watykanie', true),                      --2
       ('Egipt', 'EASY', 'Latwa trasa po Egipcie, autokarem', true),                         --3
       ('Amerykanski Sen', 'HARD', 'Zwiedzanie LA w USA!', true); --4

insert into blog_post (title, short_description, content, creation_date_time, last_edit_date_time, user_id, visible, number_of_visits,
                       need_to_verify)
values ('Amerykańskia Warszawa', 'KROTKI OPIS 1', 'Przechadzka po Warszawie. Nauka-zakupy-spacer', '2021-08-09', null, 4, true, 1, false),                                                                                       --1
       ('Tytuł', 'KROTKI OPIS 2', 'Ciekawa i historyczna wycieczka po Zamku.', '2021-08-15', null, 3, true, 2, false), --2
       ('Tytułowy', 'KROTKI OPIS 3', 'Dzisiaj byłem w Watykanie. Swietne miejsce, daje dużo do myślenia!', '2021-07-15', null, 1, true, 3, false),                                                                                    --3
       ('MEGA Tytuł', 'KROTKI OPIS 4', 'Niesamowicie bylo zwiedzic chociaz troche Egipt. Wycieczka Quadami mega.', '2021-07-01', null, 2, true, 2, false),                                                                           --4
       ('Ameryka', 'KROTKI OPIS 5', 'Bardzo Amerykanskie i bardzo ciekawe przystanki. Zachecam do czytania dalej.', '2021-06-09', null, 3, true, 66, false),                                                                          --5
       ('Warszawa ale jednak nie', 'KROTKI OPIS 6', 'Kasyno w kozackim stylu.', '2021-08-10', null, 4, true, 7, false); --6

insert into blog_comment(content, creation_date_time, rating, user_id, visible, blog_post_id, need_to_verify)
values ('Ciekawa trasa!', '2021-09-15', 4, 1, true, 1, false),                        --1
       ('Duzo atrakcji, a trampoliny - SUPER', '2021-11-10', 5, 2, true, 2, false),   --2
       ('Malo zieleni, trasa bez rewelacji', '2021-10-22', 1, 3, true, 3, false),     --3
       ('Calkiem fajna :)', '2021-10-23', 4, 4, true, 4, false),                      --4

       ('Slabe', '2021-09-10', 1, 8, true, 1, false),                                 --5
       ('Wgl nie ciekawe', '2021-09-17', 1, 5, true, 5, false),                       --6
       ('Mmm myslalem ze lepsze', '2021-08-29', 1, 6, true, 5, false),                --7
       ('Moglo byc wiecej informacji', '2021-10-10', 1, 7, true, 3, false),           --8
       ('Usuniety kom na 1!', '2021-10-10', 1, 5, false, 4, false),                   --9

       ('Moze byc', '2021-11-12', 2, 4, true, 1, false),                              --10
       ('ok', '2021-10-10', 2, 5, true, 6, false),                                    --11
       ('Chyba niebezpiecznie', '2021-12-17', 2, 8, true, 2, false),                  --12
       ('Nudne', '2021-10-10', 2, 7, true, 3, false),                                 --13
       ('Usuniety kom na 2@', '2021-08-28', 2, 6, false, 4, false),                   --14

       ('Dzieki za artykol', '2021-11-18', 3, 5, true, 1, false),                     --15
       ('Nie poniosło ale duzo informacji', '2021-09-19', 3, 5, true, 6, false),      --16
       ('Fajny', '2021-10-19', 3, 7, true, 4, false),                                 --17
       ('A o czym to', '2021-10-18', 3, 7, true, 4, false),                           --18
       ('Usuniety kom na 3#', '2021-10-15', 3, 7, false, 2, false),                   --19

       ('Przyjemnie sie czytało', '2021-12-14', 4, 6, true, 1, false),                --20
       ('Oooo o wielu rzeczach nie wiedzialem', '2021-12-24', 4, 5, true, 2, false),  --21
       ('Duzo swierzych informacji', '2021-11-28', 4, 5, true, 2, false),             --22
       ('Wysle znajomym, dzieki', '2021-09-30', 4, 6, true, 3, false),                --23
       ('Usuniety kom na 4$', '2021-08-18', 4, 8, false, 6, false),                   --24

       ('Super artykol!!!!', '2021-09-10', 5, 5, true, 1, false),                     --25
       ('Ciekawe, ciekawe i jeszcze raz ciekawe', '2021-12-01', 5, 7, true, 3, false),--26
       ('Ocena na pięć', '2021-11-12', 5, 7, true, 5, false),                         --27
       ('Rzetelne i na temat', '2021-10-29', 5, 6, true, 2, false),                   --28
       ('Piateczka!', '2021-08-24', 5, 8, true, 2, false),                            --29
       ('Usuniety kom na 5%', '2021-09-05', 5, 1, false, 4, false),                   --30

       ('Nie powinno sie wspierac hazardu', '2021-11-22', 2, 5, true, 1, false),      --31
       ('Z artykulu mozna wyczytac ze warto przyjechac. Bilety juz kupione.', '2021-10-02', 5, 8, true, 6, false); --32


insert into tag(name, visible)
values ('Wycieczka', true),    --1
       ('Ciekawa', true),      --2
       ('Krajoznawcze', true), --3
       ('Krotka', true),       --4
       ('Dluga', true),        --5

       ('Warszawa', true),     --6
       ('Polska', true),       --7
       ('Zamki', true),        --8

       ('Egipt', true),        --9
       ('Kair', true),         --10
       ('Gorace kraje', true), --11

       ('USA', true),          --12
       ('Stany', true),        --13
       ('LA', true),           --14

       ('Watykan', true); --15


insert into BLOG_POSTS_TAGS(TAG_ID, BLOG_POST_ID)
values (1, 1),
       (2, 1),
       (5, 1),
       (6, 1),
       (7, 1),

       (8, 2),
       (7, 2),
       (6, 2),
       (4, 2),

       (15, 3),
       (4, 3),
       (3, 3),
       (2, 3),

       (9, 4),
       (10, 4),
       (11, 4),
       (5, 4),
       (1, 4),
       (2, 4),
       (3, 4),

       (12, 5),
       (13, 5),
       (14, 5),
       (1, 5),
       (2, 5),
       (3, 5),
       (5, 5),

       (2, 6),
       (13, 6),
       (12, 6);


insert into travel_stop_travel_route(TRAVEL_STOP_ID, TRAVEL_ROUTE_ID)
values (1, 1),
       (2, 1),
       (3, 1),

       (5, 2),
       (6, 2),
       (7, 2),
       (8, 2),

       (9, 3),
       (10, 3),
       (11, 3),

       (12, 4),
       (13, 4),
       (14, 4),
       (15, 4),
       (16, 4);


insert into blog_posts_travel_routes(TRAVEL_ROUTE_ID, BLOG_POST_ID)
values (1, 1),
       (2, 3),
       (3, 4),
       (4, 5);

insert into blog_posts_travel_stop(TRAVEL_STOP_ID, BLOG_POST_ID)
values (4, 2),
       (17, 6);