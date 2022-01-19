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

       ('Muzeum Pałacu Króla Jana III w Wilanowie', '52.165338097257546', '21.09058362913881', true, 'Poland',
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
values ('Najlepsza wycieczka 1 dniowa - Warszawa', 'wycieczka 1 dniowa, 3 najlepsze miejsca do odwiedzenia', '<p>Wycieczka obejmuje trzy najlepsze miejsca w Warszawie:</p>
<h2>1. Polsko-Japońska Akademia Technik Komputerowych <span style="color: #b96ad9;"><strong>[7:00-10:00]</strong></span></h2>
<p>Najlepsza uczelnia w Polsce, najlepsi absolwenci na rynku pracy oraz najlepsi wykładowcy. Budynki oraz wyposażenia w salach też na super poziomie. Najlepsza uczelnia w Polsce, najlepsi absolwenci na rynku pracy oraz najlepsi wykładowcy. Budynki oraz wyposażenia w salach też na super poziomie. Najlepsza uczelnia w Polsce, najlepsi absolwenci na rynku pracy oraz najlepsi wykładowcy. Budynki oraz wyposażenia w salach też na super poziomie. Najlepsza uczelnia w Polsce, najlepsi absolwenci na rynku pracy oraz najlepsi wykładowcy. Budynki oraz wyposażenia w salach też na super poziomie.&nbsp;</p>
<h2>2. Galeria Mokot&oacute;w <strong><span style="color: #b96ad9;">[11:00-16:00]</span></strong></h2>
<p>Czas wolny na zakupy w jednym z lepszych centr&oacute;w handlowych w Warszawie. Czas wolny na zakupy w jednym z lepszych centr&oacute;w handlowych w Warszawie.Czas wolny na zakupy w jednym z lepszych centr&oacute;w handlowych w Warszawie.Czas wolny na zakupy w jednym z lepszych centr&oacute;w handlowych w Warszawie.Czas wolny na zakupy w jednym z lepszych centr&oacute;w handlowych w Warszawie.Czas wolny na zakupy w jednym z lepszych centr&oacute;w handlowych w Warszawie.</p>
<h2>3. Stacja Grawitacja <strong><span style="color: #b96ad9;">[18:00-20:00]</span></strong></h2>
<p>Ostatnim punktem programu jest park trampolin. Ostatnim punktem programu jest park trampolin. Ostatnim punktem programu jest park trampolin. Ostatnim punktem programu jest park trampolin. Ostatnim punktem programu jest park trampolin. Ostatnim punktem programu jest park trampolin.</p>
<p>&nbsp;</p>
<p style="text-align: center;"><span style="background-color: #ffffff; color: #e03e2d;"><strong>Cena wycieczki:&nbsp; 200 zł/osobę&nbsp; (brak zniżek dla dzieci)</strong></span></p>
<p style="text-align: center;"><span style="background-color: #ffffff; color: #e03e2d;"><strong>Zainteresowanych zapraszam telefonicznie pod numer 112</strong></span></p>', '2021-08-09', null, 4, true, 1, false),                                                                                       --1
       ('Najpiękniejszy zamek do zwiedzenia w Warszawie', 'Zamek wilanowski w Warszawie', '<p style="text-align: justify;">Pałac w Wilanowie&nbsp;to jeden z najcenniejszych zabytk&oacute;w polskiego baroku. Historyczna rezydencja zbudowana została dla kr&oacute;la Jana III Sobieskiego w końcu XVII w. i była rozbudowana przez kolejnych właścicieli. Reprezentuje założenie przestrzenne łączące tradycję polskiego dworu z włoską willą wiejską i francuskim pałacem.&nbsp;</p>
<p style="text-align: justify;">Pałac Wilanowski&nbsp;zbudowany przez Augustyna Locciego dla kr&oacute;la Jana III Sobieskiego i Marii Kazimiery, był wielokrotnie przebudowywany przez takich architekt&oacute;w jak Giovanni Spazzio, Jan Zygmunt Deybel, Szymon Bogumił Zug, Chrystian Piotr Aigner i Franciszek Maria Lanci.&nbsp;Po śmierci Sobieskiego w końcu XVII w., pałac był własnością jego syn&oacute;w, a następnie siedzibą znanych rod&oacute;w: Sieniawskich, Czartoryskich, Lubomirskich, Potockich i Branickich.</p>
<p style="text-align: justify;">W latach 1730-1733 był rezydencją kr&oacute;la Augusta II Mocnego. Każda z rodzin dokonywała zmian we wnętrzach pałacu, w ogrodzie i najbliższym otoczeniu. Warto odnotować, że w 1805 r. z inicjatywy &oacute;wczesnego właściciela, Stanisława Kostki Potockiego, w części pałacu powstało jedno z <strong>pierwszych</strong> publicznych muze&oacute;w w Polsce.&nbsp;</p>
<p style="text-align: justify;"><span style="color: #2dc26b;"><strong>Kto nie był powinien tam się udać!</strong></span></p>', '2021-08-15', null, 3, true, 2, false), --2
       ('Wycieczka po Watykanie 1 dniowa', 'Wycieczka po Watykanie z najlepszymi atrakcjami', '<h2>Bazylika Świętego Piotra</h2>
<p><em>Bazylika św. Piotra (wł. Basilica di San Pietro in Vaticano) to bez wątpienia jeden z najpiękniejszych kościoł&oacute;w świata. Spacerując pomiędzy nawami i kaplicami możemy poczuć się tak, jakbyśmy zwiedzali znamienite muzeum sztuki - świątynię ozdabiają dzieła takich mistrz&oacute;w jak Gianlorenzo Bernini, Michał Anioł czy Antinio Canova.</em></p>
<p><em>W 1942 roku papież Pius XII ogłosił, że pod bazyliką odnaleziono gr&oacute;b świętego Piotra. Choć efekty tamtych prac archeologicznych nadal budzą wiele wątpliwości to dla wielu chrześcijan jest jasnym, że w podziemiach właśnie tego kościoła spoczęły szczątki pierwszego papieża.</em></p>
<h2>Porta Pertusa</h2>
<p><em>Porta Pertusa. Porta Pertusa. Porta Pertusa. Porta Pertusa. Porta Pertusa. Porta Pertusa. Porta Pertusa. Porta Pertusa. Porta Pertusa. Porta Pertusa. Porta Pertusa. Porta Pertusa. Porta Pertusa. Porta Pertusa. Porta Pertusa.&nbsp;</em></p>
<h2>Muzea Watykańskie</h2>
<p><em>Muzea Watykańskie. Muzea Watykańskie. Muzea Watykańskie. Muzea Watykańskie. Muzea Watykańskie. Muzea Watykańskie. Muzea Watykańskie. Muzea Watykańskie. Muzea Watykańskie. Muzea Watykańskie. Muzea Watykańskie. Muzea Watykańskie.&nbsp;</em></p>
<h2>Fontana di Porta Cavalleggeri</h2>
<p><em>Fontana di Porta Cavalleggeri. Fontana di Porta Cavalleggeri. Fontana di Porta Cavalleggeri. Fontana di Porta Cavalleggeri. Fontana di Porta Cavalleggeri. Fontana di Porta Cavalleggeri. Fontana di Porta Cavalleggeri.&nbsp;</em></p>', '2021-07-15', null, 1, true, 3, false),                                                                                    --3
       ('Jednodniowa wycieczka po Egipcie', 'meczet, uniwersytet, pustynia', '<h2>Meczet Ahmada Ibn Tuluna</h2>
<p><br />Meczet Ahmada Ibn Tuluna. Meczet Ahmada Ibn Tuluna. Meczet Ahmada Ibn Tuluna. Meczet Ahmada Ibn Tuluna. Meczet Ahmada Ibn Tuluna. Meczet Ahmada Ibn Tuluna. Meczet Ahmada Ibn Tuluna. Meczet Ahmada Ibn Tuluna. Meczet Ahmada Ibn Tuluna. Meczet Ahmada Ibn Tuluna. Meczet Ahmada Ibn Tuluna. Meczet Ahmada Ibn Tuluna. Meczet Ahmada Ibn Tuluna. Meczet Ahmada Ibn Tuluna.&nbsp;</p>
<h2>Uniwersytet Kairski</h2>
<p><br />Uniwersytet Kairski. Uniwersytet Kairski. Uniwersytet Kairski. Uniwersytet Kairski. Uniwersytet Kairski. Uniwersytet Kairski. Uniwersytet Kairski. Uniwersytet Kairski. Uniwersytet Kairski. Uniwersytet Kairski. Uniwersytet Kairski. Uniwersytet Kairski.&nbsp;</p>
<h2>Desert Egypt Safari</h2>
<p><br />Desert Egypt Safari. Desert Egypt Safari. Desert Egypt Safari. Desert Egypt Safari. Desert Egypt Safari. Desert Egypt Safari. Desert Egypt Safari. Desert Egypt Safari. Desert Egypt Safari. Desert Egypt Safari. Desert Egypt Safari. Desert Egypt Safari. Desert Egypt Safari.&nbsp;</p>', '2021-07-01', null, 2, true, 2, false),                                                                           --4
       ('Amerykańskie atrakcje', 'Wycieczka 2 dniowa po ciekawych atrakcjach - Ameryka', '<p><span style="text-decoration: underline;">Los Angeles County Museum of Art</span></p>
<p style="text-align: center;"><br />Los Angeles County Museum of Art. Los Angeles County Museum of Art. Los Angeles County Museum of Art. Los Angeles County Museum of Art. Los Angeles County Museum of Art. Los Angeles County Museum of Art. Los Angeles County Museum of Art. Los Angeles County Museum of Art. Los Angeles County Museum of Art. Los Angeles County Museum of Art. Los Angeles County Museum of Art.&nbsp;</p>
<p><br /><span style="text-decoration: underline;">Walt Disney Concert Hall</span></p>
<p style="text-align: center;"><br />Walt Disney Concert Hall. Walt Disney Concert Hall. Walt Disney Concert Hall. Walt Disney Concert Hall. Walt Disney Concert Hall. Walt Disney Concert Hall. Walt Disney Concert Hall. Walt Disney Concert Hall. Walt Disney Concert Hall.&nbsp;</p>
<p><br /><span style="text-decoration: underline;">Rose Bowl</span></p>
<p style="text-align: center;"><br />Rose Bowl. Rose Bowl. Rose Bowl. Rose Bowl. Rose Bowl. Rose Bowl. Rose Bowl. Rose Bowl. Rose Bowl. Rose Bowl. Rose Bowl. Rose Bowl. Rose Bowl. Rose Bowl. Rose Bowl. Rose Bowl. Rose Bowl. Rose Bowl. Rose Bowl. Rose Bowl. Rose Bowl.&nbsp;</p>
<p><br /><strong>Los Angeles Valley College</strong></p>
<p style="text-align: center;"><br />Los Angeles Valley College. Los Angeles Valley College. Los Angeles Valley College. Los Angeles Valley College. Los Angeles Valley College. Los Angeles Valley College. Los Angeles Valley College. Los Angeles Valley College. Los Angeles Valley College. Los Angeles Valley College. Los Angeles Valley College. Los Angeles Valley College. Los Angeles Valley College.&nbsp;</p>
<p><br /><strong>The Getty</strong></p>
<p style="text-align: center;"><br />The Getty. The Getty. The Getty. The Getty. The Getty. The Getty. The Getty. The Getty. The Getty. The Getty. The Getty. The Getty. The Getty. The Getty. The Getty. The Getty. The Getty. The Getty. The Getty. The Getty. The Getty.&nbsp;</p>', '2021-06-09', null, 3, true, 66, false),                                                                          --5
       ('Calgarioskie Kasyno', 'Najwieksze kasyno w Calgarach', '<p style="text-align: center;"><span style="text-decoration: line-through;"><sup>Cowboys Casino</sup></span><br />Cowboys Casino. Cowboys Casino. Cowboys Casino. Cowboys Casino. Cowboys Casino. Cowboys Casino. <span style="background-color: #b96ad9;">Cowboys Casino. Cowboys Casino. Cowboys Casino. Cowboys Casino. Cowboys Casino. Cowboys Casino. Cowboys Casino. Cowboys Casino. Cowboys Casino. Cowboys Casino. Cowboys Casino. Cowboys Casino. Cowboys Casino. Cowboys Casino. Cowboys Casino.&nbsp;</span></p>', '2021-08-10', null, 4, true, 7, false); --6

insert into image (ID,VISIBLE,NAME,URL,BLOG_POST_ID)
values (1, true, '11.jpg', 'uploads/11.jpg', 1),
       (2, true, '22.jpg', 'uploads/22.jpg', 1),
       (3, true, '33.jpg', 'uploads/33.jpg', 1),
       (4, true, '44.jpg', 'uploads/44.jpg', 1),
       (5, true, '55.jpg', 'uploads/55.jpg', 1),
       (6, true, '66.jpg', 'uploads/66.jpg', 1),
       (7, true, '77.jpg', 'uploads/77.jpg', 2),
       (8, true, '88.jpg', 'uploads/88.jpg', 2),
       (9, true, '99.jpg', 'uploads/99.jpg', 2),
       (10, true, '1010.jpg', 'uploads/1010.jpg', 2),
       (11, true, '1111.jpg', 'uploads/1111.jpg', 2),
       (12, true, '1212.png', 'uploads/1212.png', 2),
       (13, true, '1313.png', 'uploads/1313.png', 3),
       (14, true, '1414.jpg', 'uploads/1414.jpg', 3),
       (15, true, '1515.jpg', 'uploads/1515.jpg', 3),
       (16, true, '1616.jpg', 'uploads/1616.jpg', 3),
       (17, true, '1717.jpg', 'uploads/1717.jpg', 3),
       (18, true, '1818.png', 'uploads/1818.png', 3),
       (19, true, '1919.png', 'uploads/1919.png', 4),
       (20, true, '2020.png', 'uploads/2020.png', 4),
       (21, true, '2121.png', 'uploads/2121.png', 4),
       (22, true, '2222.png', 'uploads/2222.png', 4),
       (23, true, '2323.png', 'uploads/2323.png', 4),
       (24, true, '2424.png', 'uploads/2424.png', 4),
       (25, true, '2525.png', 'uploads/2525.png', 5),
       (26, true, '2626.png', 'uploads/2626.png', 5),
       (27, true, '2727.png', 'uploads/2727.png', 5),
       (28, true, '2828.png', 'uploads/2828.png', 5),
       (29, true, '2929.png', 'uploads/2929.png', 5),
       (30, true, '3030.png', 'uploads/3030.png', 5),
       (31, true, '3131.png', 'uploads/3131.png', 6),
       (32, true, '3232.png', 'uploads/3232.png', 6),
       (33, true, '3333.png', 'uploads/3333.png', 6),
       (34, true, '3434.png', 'uploads/3434.png', 6),
       (35, true, '3535.png', 'uploads/3535.png', 6),
       (36, true, '3636.png', 'uploads/3636.png', 6),
       (37, true, '3737.png', 'uploads/3737.png', 3),
       (38, true, '3838.png', 'uploads/3838.png', 5);

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


insert into tag(name, visible, occurrence_number)
values ('Wycieczka', true, 3),    --1
       ('Ciekawa', true, 5),      --2
       ('Krajoznawcze', true, 3), --3
       ('Krotka', true, 2),       --4
       ('Dluga', true, 3),        --5

       ('Warszawa', true, 2),     --6
       ('Polska', true, 2),       --7
       ('Zamki', true, 1),        --8

       ('Egipt', true, 1),        --9
       ('Kair', true, 1),         --10
       ('Gorace kraje', true, 1), --11

       ('USA', true, 2),          --12
       ('Stany', true, 2),        --13
       ('LA', true, 1),           --14

       ('Watykan', true, 1); --15


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