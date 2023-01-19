package ru.itmo.excel;

import ru.itmo.dao.CompanyDao;
import ru.itmo.dao.EmployeeDao;
import ru.itmo.dao.PositionDao;
import ru.itmo.entity.Company;
import ru.itmo.entity.EmployeePOJO;
import ru.itmo.entity.Position;

import java.util.*;

public class DataGenerator {

    private static EmployeeDao employeeDao = new EmployeeDao();
    private static CompanyDao companyDao = new CompanyDao();
    private static PositionDao positionDao = new PositionDao();

    private static final float VARIATION_IN_PERCENT = 0.4f;

    private static final String[] NAMES = new String []{"Аглая", "Нина", "Татьяна", "Ульяна", "Сусанна", "Евгения", "Эмилия", "Юлия", "Анастасия", "Ирина", "Мария", "Василиса", "Оксана", "Наталья",
            "Инна", "Мила", "Агния", "Зоя", "Валентина", "Ксения", "Ольга", "Светлана", "Екатерина", "Арина", "Марта", "София", "Регина",
            "Кира", "Марина", "Антонина", "Дарья", "Ольга", "Александра", "Кристина", "Надежда", "Ника", "Ираида", "Варвара", "Виктория", "Аделина",
            "Антон", "Григорий", "Илья", "Лев", "Даниил", "Никита", "Петр", "Павел", "Марк", "Лука", "Леонид", "Александр", "Макар", "Иван", "Савва", "Степан", "Михаил", "Игнат", "Тихон", "Филипп", "Анатолий", "Арсений", "Ефим", "Денис",
            "Николай", "Константин", "Адам", "Валерий", "Григорий", "Валентин", "Павел", "Глеб", "Евгений", "Василий", "Семён", "Марат", "Осип", "Арсений", "Борис", "Егор", "Никита", "Мирон", "Михаил",
            "Александр", "Кирилл", "Степан", "Андрей", "Захар", "Никита", "Иван", "Леонид", "Виктор", "Георгий", "Лука", "Макар", "Ярослав", "Артём", "Даниил", "Дмитрий", "Валентин", "Адриан", "Назар", "Эрик",
    };

    private static final String[] COMPANIES = new String[]{"Вторжение", "Проклятие", "Под водой", "Просто помиловать", "(Не)идеальный мужчина", "Плохие парни навсегда", "Марафон желаний", "Ярды", "1917",
            "Кома", "Герой СамСам", "Остров фантазий", "Джентльмены", "Лёд 2", "Зов предков", "Соник в кино", "Яга. Кошмар тёмного ", "Вперёд", "Человек-невидимка", "Отель Белград", "Один вдох", "Побег из Претории",
            "Бладшот", "Счастье в конверте", "Убийства по открыткам"};


    private static final String[] POSITIONS = new String[]{"Бармен", "Библиотекарь", "Витолье", "Горничная", "Грузчик", "Доула (профессия)", "Кладовщик", "Кнопочник ", "Крупье", "Лифтёр", "Мастер маникюра",
            "Менеджер", "Мерчандайзер", "Метрдотель", "Няня", "Оператор коллцентра", "Официант", "Парикмахер", "Портной", "Портье", "Почтальон", "Продавец", "Сиделка", "Сапожник", "Сомелье", "Телемастер", "Торседор",
            "Упаковщик", "Флорист", "Швейцар", "Дизайнер рекламы", "Каскадёр", "Кинодраматург", "Киномеханик", "Кинооператор", "Кинорежиссер", "Оператор кино и телевидения", "Постановщик трюков", "Продюсер", "Сценарист",
            "Критик", "Актёр", "Артист цирка", "Архитектор", "Балетмейстер", "Балерина", "Брейдер", "Вокалист", "Визажист", "Геймдизайнер", "Гитарист", "Гример", "Диджей", "Дизайнер", "Дирижёр", "Декоратор", "Журналист",
            "Звукорежиссёр", "Златокузнец", "Изобретатель", "Иллюстратор", "Имиджмейкер", "Инженер", "Композитор", "Кондитер", "Мастер маникюра", "Мастер педикюра", "Манекенщица", "Модель", "Модельер", "Музыкант",
            "Парфюмер", "Писатель", "Поэт", "Повар", "Программист", "Режиссёр", "Реставратор", "Скульптор", "Стилист", "Танцор", "Татуировщик", "Флорист", "Фотограф", "Фотомодель", "Хореограф", "Художник", "Ювелир", "Художник по свету",
            "Артиллерист", "Авиационный техник", "Баталер", "Борт-инженер", "Борт-механик", "Борт-радист", "Борт-стрелок", "Военный дознаватель", "Военный переводчик", "Военный консультант", "Военно-полевой хирург", "Военный полицейский", "Военный прокурор", "Военный судья",
            "Военный юрист", "Водолаз", "Воспитатель", "Гренадер", "Горнострелок", "Гранатомётчик", "Десантник", "Диверсант", "Заряжающий", "Интендант", "Зенитчик", "Кавалерист", "Канонир", "Каптенармус", "Командир", "Комендант", "Корректировщик", "Лётчик", "Механик-Водитель",
            "Маркитант", "Мотострелок", "Морской пехотинец", "Наводчик орудия", "Начальник военного оркестра", "Начальник гаупвахты", "Начальник службы", "Начальник склада", "Начальник штаба", "Огнемётчик", "Особист", "Оператор вооружения", "Оператор РЛС", "Пограничник", "Подводник",
            "Пулемётчик", "Разведчик", "Радист", "Радиотелефонист", "Ракетчик", "Автоугонщик", "Аферист", "Барыга", "Барсеточник", "Браконьер", "Бутлегер", "Вор", "Влад кабась", "Грабитель", "Домушник", "Карманник", "Катала", "Киллер", "Кидала", "Клофелинщик",
            "Контрабандист", "Коррупционер", "Медвежатник", "Мошенник", "Напёрсточник", "Наркоделец", "Пират", "Разбойник", "Рейдер", "Рекитёр", "Скотокрад", "Сутенёр", "Фальшивомонетчик", "Форточник", "Хакер", "Чёрный археолог", "Чёрный риэлтор", "Чёрный копатель", "Цеховик",
            "Шантажист", "Щипач", "Шулер", "Колхозник", "Дипломат", "Дипломатический работник", "Кинолог", "Организатор свадеб", "Переводчик", "Промышленный альпинист", "Безработный", "Сапёр", "Связист", "Секретчик", "Старшина", "Стрелок", "Снайпер", "Танкист", "Техник", "Топограф",
            "Тыловик", "Фельдшер", "Финансист", "Фортификатор", "Фуражир", "Химик", "Шифровальщик", "Штурман"};

//    private static  List<EmployeePOJO> generateEmployees(){
//
//    }

    private static List<Company> generateCompanies(int companyQuantity, int positionQuantity){
        List<Company> companies = new LinkedList<>();
        companyQuantity = countRandQuantity(companyQuantity);
        int min = Math.min(companyQuantity, COMPANIES.length);
        int averageLen = POSITIONS.length / min;

        List<String> initialCompanies = Arrays.asList(COMPANIES);
        Collections.shuffle(initialCompanies);

        List<String> initialPositions = Arrays.asList(POSITIONS);
        Collections.shuffle(initialPositions);

        for(int i=0; i< min; i++){
            Company currCompany = new Company();
            currCompany.setCompanyName(initialCompanies.get(i));
            currCompany.setPositions(generatePositions(positionQuantity, averageLen, i, initialPositions, currCompany ));
            companies.add(currCompany);
        }
        return companies;

    }

    // unique positions for EACH company
    private static Set<Position> generatePositions(int positionQuantity, int averageLen, int idxOfCompany, List<String> initialPositions, Company currCompany){
        positionQuantity = countRandQuantity(positionQuantity);
        int begin = idxOfCompany * averageLen; // searching from
        int end = Math.min((idxOfCompany+1) * averageLen - 1, initialPositions.size()); // to
        Set<Position> positions = new HashSet<>();
        for (int i=0; i< positionQuantity; i++){
            positions.add(new Position(initialPositions.get(begin + i), currCompany));
        }
        return positions;

    }

    private static int countRandQuantity(int initialQuantity){
        int difference = (int) (initialQuantity * VARIATION_IN_PERCENT * 2 * Math.random() - initialQuantity * VARIATION_IN_PERCENT);
        return initialQuantity + difference;
    }


    public static void main(String[] args) {

//        List<Company> companies = generateCompanies(6, 10);
//        companies.forEach(company -> companyDao.save(company));


    }

    public static void generateAndSaveCompanies(){
        List<Company> companies = generateCompanies(6, 10);
        companies.forEach(company -> companyDao.save(company));
    }


}
