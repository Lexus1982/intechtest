package me.alexand.intech.server.util;

import me.alexand.intech.server.model.Content;
import me.alexand.intech.server.model.FSMState;
import me.alexand.intech.server.model.Transition;

import static me.alexand.intech.server.model.ContentCategory.*;
import static me.alexand.intech.server.model.FSMEvent.*;

/**
 * @author asidorov84@gmail.com
 */
public interface MockData {
    FSMState INIT = new FSMState(1, "INIT", "", true);
    FSMState MENU_0 = new FSMState(2, "MENU_0", "Выберите раздел: 1 - для просмотра контента, 2 - для входа в ЛК, 3 - раздел инфо", false);
    FSMState MENU_1 = new FSMState(3, "MENU_1", "Каталог контента. Для начала прослушивания мелодий выберите категорию:" +
            " Популярные - 1, Новинки - 2, Хиты - 3. Для выхода нажмите *", false);
    FSMState MENU_2 = new FSMState(4, "MENU_2", "Для получения информации о возможностях услуги нажмите 1, для того, чтобы узнать" +
            " стоимость - нажмите 2", false);
    FSMState MENU_3 = new FSMState(5, "MENU_3", "Сервис позволяет прослушать контент и купить его!!! Выход в основное меню - *," +
            " # - вернуться в предыдущее меню", false);
    FSMState MENU_4 = new FSMState(6, "MENU_4", "Весь контент стоит 100 рублей. Для выхода в меню информации об услуге - 1," +
            " в основное меню - *, # - вернуться в предыдущее меню", false);
    FSMState LISTEN_1 = new FSMState(7, "LISTEN_1", "Прослушивание осуществляется в непрерывном порядке. При проигрывании мелодии" +
            " нажмите 1 для перехода к следующей, 2 - для покупки данной мелодии. Для выхода в основное меню - нажмите" +
            " *. Для выхода в предыдущее меню - #", false);
    FSMState LISTEN_2 = new FSMState(8, "LISTEN_2", "Вы находитесь в меню управления услугой и редактирования персональной фонотеки." +
            " При проигрывании мелодии нажмите 1 для перехода к следующей, 2 - для удаления данной мелодии. Для выхода" +
            " в основное меню - нажмите *", false);//Личный кабинет

    Transition TRANSITION1 = new Transition(1, LOGIN, INIT, MENU_0, "NOP");

    Transition TRANSITION2 = new Transition(2, ONE, MENU_0, MENU_1, "NOP");
    Transition TRANSITION3 = new Transition(3, TWO, MENU_0, LISTEN_2, "PLAYLK");
    Transition TRANSITION4 = new Transition(4, THREE, MENU_0, MENU_2, "NOP");

    Transition TRANSITION5 = new Transition(5, AST, MENU_1, MENU_0, "NOP");
    Transition TRANSITION6 = new Transition(6, ONE, MENU_1, LISTEN_1, "PLAY?POPULAR");
    Transition TRANSITION7 = new Transition(7, TWO, MENU_1, LISTEN_1, "PLAY?NOVELTIES");
    Transition TRANSITION8 = new Transition(8, THREE, MENU_1, LISTEN_1, "PLAY?HITS");

    Transition TRANSITION9 = new Transition(9, ONE, LISTEN_1, LISTEN_1, "NEXT");
    Transition TRANSITION10 = new Transition(10, TWO, LISTEN_1, LISTEN_1, "BUY");
    Transition TRANSITION11 = new Transition(11, AST, LISTEN_1, MENU_0, "STOP");
    Transition TRANSITION12 = new Transition(12, SHARP, LISTEN_1, MENU_1, "STOP");

    Transition TRANSITION13 = new Transition(13, AST, LISTEN_2, MENU_0, "STOP");
    Transition TRANSITION14 = new Transition(14, ONE, LISTEN_2, LISTEN_2, "NEXT");
    Transition TRANSITION15 = new Transition(15, TWO, LISTEN_2, LISTEN_2, "REMOVE");

    Transition TRANSITION16 = new Transition(16, ONE, MENU_2, MENU_3, "NOP");
    Transition TRANSITION17 = new Transition(17, TWO, MENU_2, MENU_4, "NOP");

    Transition TRANSITION18 = new Transition(18, SHARP, MENU_3, MENU_2, "NOP");
    Transition TRANSITION19 = new Transition(19, AST, MENU_3, MENU_0, "NOP");

    Transition TRANSITION20 = new Transition(20, SHARP, MENU_4, MENU_2, "NOP");
    Transition TRANSITION21 = new Transition(21, AST, MENU_4, MENU_0, "NOP");

    Content POP_CONTENT_1 = new Content(1, "Popular: content - 1", "This is example of popular content 1 with duration 10 sec", POPULAR, 10);
    Content POP_CONTENT_2 = new Content(2, "Popular: content - 2", "This is example of popular content 2 with duration 10 sec", POPULAR, 10);
    Content POP_CONTENT_3 = new Content(3, "Popular: content - 3", "This is example of popular content 3 with duration 10 sec", POPULAR, 10);
    Content NOVELTIES_CONTENT_1 = new Content(4, "Novelties: content - 1", "This is example of novelties content 1 with duration 10 sec", NOVELTIES, 10);
    Content NOVELTIES_CONTENT_2 = new Content(5, "Novelties: content - 2", "This is example of novelties content 2 with duration 10 sec", NOVELTIES, 10);
    Content NOVELTIES_CONTENT_3 = new Content(6, "Novelties: content - 3", "This is example of novelties content 3 with duration 10 sec", NOVELTIES, 10);
    Content HITS_CONTENT_1 = new Content(7, "Hits: content - 1", "This is example of hits content 1 with duration 10 sec", HITS, 10);
    Content HITS_CONTENT_2 = new Content(8, "Hits: content - 2", "This is example of hits content 2 with duration 10 sec", HITS, 10);
    Content HITS_CONTENT_3 = new Content(9, "Hits: content - 3", "This is example of hits content 3 with duration 10 sec", HITS, 10);
}
