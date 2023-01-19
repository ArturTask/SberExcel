package ru.itmo.excel;

import ru.itmo.dao.CompanyDao;
import ru.itmo.dao.EmployeeDao;
import ru.itmo.dao.PositionDao;
import ru.itmo.entity.Company;
import ru.itmo.entity.EmployeePOJO;
import ru.itmo.entity.Position;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

import static ru.itmo.excel.ExcelManager.postEmployees;

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
    private static final String[] LAST_NAMES = new String []{"Acker", "Agnello", "Alinsky", "Aphelion", "Bartley", "Bixby", "Bobusic", "Bonneville", "Botkin", "Brager", "Brubaker", "Burris", "Butterworth", "Ajax",
            "Berrycloth", "Birdwhistle", "Bread", "Bythesea", "Dankworth", "Edevane", "Fensby", "Gastrell", "Loughty", "MacQuoid", "Miracle", "Pussmaid", "Relish", "Sallow", "Slora", "Spinster", "Villin", "Abreo", "Agnor", "Alvin", "Auclair",
            "Anouilh", "Bain", "Barrere", "Bassett", "Beauregard", "Brassard", "Brierman", "Cellier", "Dardar", "De", "Ebersol", "Ekker", "Escoffier", "Etter", "Fawzi", "Fisk", "Flammia", "Floquet", "Fonua", "Fukushima", "Furyk", "Freed", "Fontana",
            "Gaumond", "Hanlon", "Houde", "Hubert", "Jessup", "Joubert", "Lafitte", "Machal", "Mangiarotti", "Ozanne", "Palomer", "Paquet", "Prevost", "Tasse", "Varville", "Bardot", "Bellamy", "Blaine", "Cassidy", "Caparasso", "Channing", "Cienfuegos",
            "Dracula", "Escarra", "Falaguerra", "Gow", "Gushiken", "Heroux", "Homa", "Igarashi", "Ingannamorte", "Jurado", "La", "Lenoir", "Madigan", "Malfatto", "Martel", "Methadonna", "Pelagatti", "Proulx", "Rossingol", "Seisdedos", "Varon", "Voland",
            "Webster", "Villalobos"};

    private static final String[] COMPANIES = new String[]{"Вторжение", "Проклятие", "Под водой", "Просто помиловать", "(Не)идеальный мужчина", "Плохие парни навсегда", "Марафон желаний", "Ярды", "1917",
            "Кома", "Герой СамСам", "Остров фантазий", "Джентльмены", "Лёд 2", "Зов предков", "Соник в кино", "Яга. Кошмар тёмного ", "Вперёд", "Человек-невидимка", "Отель Белград", "Один вдох", "Побег из Претории",
            "Бладшот", "Счастье в конверте", "Убийства по открыткам"};


    private static final String[] POSITIONS = new String[]{"Бармен", "Библиотекарь", "Витолье", "Горничная", "Грузчик", "Доула (профессия)", "Кладовщик", "Кнопочник ", "Крупье", "Лифтёр", "Мастер маникюра",
            "Менеджер", "Мерчандайзер", "Метрдотель", "Няня", "Оператор коллцентра", "Официант", "Парикмахер", "Портной", "Портье", "Почтальон", "Продавец", "Сиделка", "Сапожник", "Сомелье", "Телемастер", "Торседор",
            "Упаковщик", "Флорист", "Швейцар", "Дизайнер рекламы", "Каскадёр", "Кинодраматург", "Киномеханик", "Кинооператор", "Кинорежиссер", "Оператор кино и телевидения", "Постановщик трюков", "Продюсер", "Сценарист",
            "Критик", "Актёр", "Артист цирка", "Архитектор", "Балетмейстер", "Балерина", "Брейдер", "Вокалист", "Визажист", "Геймдизайнер", "Гитарист", "Гример", "Диджей", "Дизайнер", "Дирижёр", "Декоратор", "Журналист",
            "Звукорежиссёр", "Златокузнец", "Изобретатель", "Иллюстратор", "Имиджмейкер", "Инженер", "Композитор", "Кондитер", "Мастер педикюра", "Манекенщица", "Модель", "Модельер", "Музыкант",
            "Парфюмер", "Писатель", "Поэт", "Повар", "Программист", "Режиссёр", "Реставратор", "Скульптор", "Стилист", "Танцор", "Татуировщик", "Фотограф", "Фотомодель", "Хореограф", "Художник", "Ювелир", "Художник по свету",
            "Артиллерист", "Авиационный техник", "Баталер", "Борт-инженер", "Борт-механик", "Борт-радист", "Борт-стрелок", "Военный дознаватель", "Военный переводчик", "Военный консультант", "Военно-полевой хирург", "Военный полицейский", "Военный прокурор", "Военный судья",
            "Военный юрист", "Водолаз", "Воспитатель", "Гренадер", "Горнострелок", "Гранатомётчик", "Десантник", "Диверсант", "Заряжающий", "Интендант", "Зенитчик", "Кавалерист", "Канонир", "Каптенармус", "Командир", "Комендант", "Корректировщик", "Лётчик", "Механик-Водитель",
            "Маркитант", "Мотострелок", "Морской пехотинец", "Наводчик орудия", "Начальник военного оркестра", "Начальник гаупвахты", "Начальник службы", "Начальник склада", "Начальник штаба", "Огнемётчик", "Особист", "Оператор вооружения", "Оператор РЛС", "Пограничник", "Подводник",
            "Пулемётчик", "Разведчик", "Радист", "Радиотелефонист", "Ракетчик", "Автоугонщик", "Аферист", "Барыга", "Барсеточник", "Браконьер", "Бутлегер", "Вор", "Влад кабась", "Грабитель", "Домушник", "Карманник", "Катала", "Киллер", "Кидала", "Клофелинщик",
            "Контрабандист", "Коррупционер", "Медвежатник", "Мошенник", "Напёрсточник", "Наркоделец", "Пират", "Разбойник", "Рейдер", "Рекитёр", "Скотокрад", "Сутенёр", "Фальшивомонетчик", "Форточник", "Хакер", "Чёрный археолог", "Чёрный риэлтор", "Чёрный копатель", "Цеховик",
            "Шантажист", "Щипач", "Шулер", "Колхозник", "Дипломат", "Дипломатический работник", "Кинолог", "Организатор свадеб", "Переводчик", "Промышленный альпинист", "Безработный", "Сапёр", "Связист", "Секретчик", "Старшина", "Стрелок", "Снайпер", "Танкист", "Техник", "Топограф",
            "Тыловик", "Фельдшер", "Финансист", "Фортификатор", "Фуражир", "Химик", "Шифровальщик", "Штурман"};


    public static void main(String[] args) {

        generateAndSaveCompanies(10, 20);
        System.out.println("Generating employees it may take a little...");
        generateAndSaveEmployees("test.xlsx", 0, 50000);
        List<Company> allCompanies = companyDao.getAllCompanies();
        System.out.println();

    }

    public static void generateAndSaveCompanies(int companyQuantity, int positionQuantity){
        List<Company> companies = generateCompanies(companyQuantity, positionQuantity);
        companies.forEach(company -> companyDao.save(company));
    }

    public static void generateAndSaveEmployees(String path, int sheetIdx, int quantity){
        List<EmployeePOJO> employeePOJOS = generateEmployees(quantity);
        System.out.println("Finished Generating - " + new SimpleDateFormat("HH:mm:ss").format(new Date())) ;
        try {
            postEmployees(path, sheetIdx, employeePOJOS);
            System.out.println("Finished successfully - "+ new SimpleDateFormat("HH:mm:ss").format(new Date()));
        } catch (IOException e) {
            System.out.println("Ошибка в модуле DataGenerator при сохранении сотрудников");
        }
    }

    private static  List<EmployeePOJO> generateEmployees(int quantity){
        List<EmployeePOJO> employees = new LinkedList<>();
        for(int i=0; i< quantity; i++){
            employees.add(generateEmployee(i));
        }
        return employees;
    }

    private static EmployeePOJO generateEmployee(int idx){

        EmployeePOJO employee = new EmployeePOJO();
        if (Math.random()<0.95){
            employee.setId(idx);
        }
        else { // 5% fault
            employee.setId(idx+1);
        }
        employee.setName(NAMES[(int) (Math.random()*NAMES.length)]);
        employee.setLastName(LAST_NAMES[(int) (Math.random()*LAST_NAMES.length)]);
        employee.setBirthday(LocalDate.now().minusYears((long) (Math.random()* 100)).minusWeeks((long) (Math.random()*20)));
        employee.setCompany(COMPANIES[(int) (Math.random()*COMPANIES.length)]);
        employee.setPositionAtWork(POSITIONS[(int) (Math.random()*POSITIONS.length)]);
        employee.setSalary((float) (Math.random()*1000000));
        return employee;

    }

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
        int end = Math.min((idxOfCompany+1) * averageLen - 1, initialPositions.size() - 1); // to
        Set<Position> positions = new HashSet<>();
        for (int i=0; i< positionQuantity; i++){
            if (begin+i > end){
                break;
            }
            positions.add(new Position(initialPositions.get(begin + i), currCompany));
        }
        return positions;

    }

    private static int countRandQuantity(int initialQuantity){
        int difference = (int) (initialQuantity * VARIATION_IN_PERCENT * 2 * Math.random() - initialQuantity * VARIATION_IN_PERCENT);
        return initialQuantity + difference;
    }



}
