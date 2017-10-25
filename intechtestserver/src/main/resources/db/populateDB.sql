DELETE FROM users_content;
DELETE FROM contents;
DELETE FROM transitions;
DELETE FROM states;


INSERT INTO states VALUES
  (1, 'INIT', '', TRUE),
  (2, 'MENU_0', 'Выберите раздел: 1 - для просмотра контента, 2 - для входа в ЛК, 3 - раздел инфо', FALSE),
  (3, 'MENU_1',
   'Каталог контента. Для начала прослушивания мелодий выберите категорию: Популярные - 1, Новинки - 2, Хиты - 3. Для выхода нажмите *',
   FALSE),
  (4, 'MENU_2',
   'Для получения информации о возможностях услуги нажмите 1, для того, чтобы узнать стоимость - нажмите 2', FALSE),
  (5, 'MENU_3',
   'Сервис позволяет прослушать контент и купить его!!! Выход в основное меню - *, # - вернуться в предыдущее меню',
   FALSE),
  (6, 'MENU_4',
   'Весь контент стоит 100 рублей. Для выхода в меню информации об услуге - 1, в основное меню - *, # - вернуться в предыдущее меню',
   FALSE),
  (7, 'LISTEN_1',
   'Прослушивание осуществляется в непрерывном порядке. При проигрывании мелодии нажмите 1 для перехода к следующей, 2 - для покупки данной мелодии. Для выхода в основное меню - нажмите *. Для выхода в предыдущее меню - #',
   FALSE),
  (8, 'LISTEN_2',
   'Вы находитесь в меню управления услугой и редактирования персональной фонотеки. При проигрывании мелодии нажмите 1 для перехода к следующей, 2 - для удаления данной мелодии. Для выхода в основное меню - нажмите *',
   FALSE);

INSERT INTO contents VALUES
  (1, 'Popular: content - 1', 'This is example of popular content 1 with duration 10 sec', 'POPULAR', 10),
  (2, 'Popular: content - 2', 'This is example of popular content 2 with duration 10 sec', 'POPULAR', 10),
  (3, 'Popular: content - 3', 'This is example of popular content 3 with duration 10 sec', 'POPULAR', 10),
  (4, 'Novelties: content - 1', 'This is example of novelties content 1 with duration 10 sec', 'NOVELTIES', 10),
  (5, 'Novelties: content - 2', 'This is example of novelties content 2 with duration 10 sec', 'NOVELTIES', 10),
  (6, 'Novelties: content - 3', 'This is example of novelties content 3 with duration 10 sec', 'NOVELTIES', 10),
  (7, 'Hits: content - 1', 'This is example of hits content 1 with duration 10 sec', 'HITS', 10),
  (8, 'Hits: content - 2', 'This is example of hits content 2 with duration 10 sec', 'HITS', 10),
  (9, 'Hits: content - 3', 'This is example of hits content 3 with duration 10 sec', 'HITS', 10);

INSERT INTO transitions VALUES
  (1, 'LOGIN', 1, 2, 'NOP'),
  (2, 'ONE', 2, 3, 'NOP'),
  (3, 'TWO', 2, 8, 'PLAYLK'),
  (4, 'THREE', 2, 4, 'NOP'),
  (5, 'AST', 3, 2, 'NOP'),
  (6, 'ONE', 3, 7, 'PLAY?POPULAR'),
  (7, 'TWO', 3, 7, 'PLAY?NOVELTIES'),
  (8, 'THREE', 3, 7, 'PLAY?HITS'),
  (9, 'ONE', 7, 7, 'NEXT'),
  (10, 'TWO', 7, 7, 'BUY'),
  (11, 'AST', 7, 2, 'STOP'),
  (12, 'SHARP', 7, 3, 'STOP'),
  (13, 'AST', 8, 2, 'STOP'),
  (14, 'ONE', 8, 8, 'NEXT'),
  (15, 'TWO', 8, 8, 'REMOVE'),
  (16, 'ONE', 4, 5, 'NOP'),
  (17, 'TWO', 4, 6, 'NOP'),
  (18, 'SHARP', 5, 4, 'NOP'),
  (19, 'AST', 5, 2, 'NOP'),
  (20, 'SHARP', 6, 4, 'NOP'),
  (21, 'AST', 6, 2, 'NOP');

INSERT INTO users_content VALUES
  (123, 1);
