import Company.Company;
import People.Employee;
import People.Manager;
import People.Operator;
import People.TopManager;

import java.util.ArrayList;
import java.util.Collections;

public class Main
{
    public static void main(String[] args) {

         // Создание объекта класса Компания
        Company gromozeka = new Company("\"Gromozeka LLC\"");

        // Создание списка объектов класса Сотрудник "Оператор"
        ArrayList<Employee> operators = new ArrayList<>();
        for (int i = 0; i < gromozeka.getOperatorCount(); i++) {
            operators.add(operatorCreate());
        }
        gromozeka.hireAll(operators); // Реализация методах hireAll(), нанимающей список сотрудников

        // Создание списка объектов класса Сотрудник "Менеджер"
        ArrayList<Employee> managers = new ArrayList<>();
        for (int j = 0; j < gromozeka.getManagerCount(); j++) {
            managers.add(managerCreate());
        }
        gromozeka.hireAll(managers); // Реализация методах hireAll(), нанимающей список сотрудников

        // Создание объектов класса Сотрудник "Топ-Менеджер"\
        for (int k = 0; k < gromozeka.getTopManagerCount(); k++) {
            gromozeka.hire(topManagerCreate(gromozeka)); // реализация метода hire(), нанимающей человека по одному
        }

        //====================================================================

        // Получение данных о компании через метод класса "Сотрудник"
        gromozeka.getStaff().get((int)Math.round(gromozeka.getStaffCount() * Math.random())).getCompanyData(gromozeka);

        // Вывод списка самых низких зарплат
        getLowestSalaryList(gromozeka, 30);

        // Вывод списка самых высоких зарплат
        getTopSalaryList(gromozeka, 15);

        //====================================================================

        // Реализация метода fire(), увольняющего сотрудников
        firingPeople(gromozeka, gromozeka.getStaffCount() / 2);

        System.out.println("\nКоличество сотрудников в компании " + gromozeka.getCompanyName() + " после увольнения: " + gromozeka.getStaffCount());

        getLowestSalaryList(gromozeka, 30);
        getTopSalaryList(gromozeka, 15);

        // Получение данных о компании через метод класса "Сотрудник"
        gromozeka.getStaff().get((int)Math.round(gromozeka.getStaffCount() * Math.random())).getCompanyData(gromozeka);

        //====================================================================

        /**
         * Все то же самое с новой компанией
         */
        // Создание новой компании
        Company coleso = new Company("\"Колесо от Бабушкиной кровати Инкорпорэйтэд\"");

        // Создание штата сотрудников
        ArrayList<Employee> colesoOperators = new ArrayList<>();
        for (int i = 0; i < (coleso.getOperatorCount() / 2); i++) {
            colesoOperators.add(operatorCreate());
        }
        coleso.hireAll(colesoOperators);

        ArrayList<Employee> colesoManagers = new ArrayList<>();
        for (int i = 0; i < (coleso.getManagerCount()); i++) {
            colesoManagers.add(managerCreate());
        }
        coleso.hireAll(colesoManagers);

        for (int i = 0; i < (coleso.getTopManagerCount() * 2); i++) {
            coleso.hire(topManagerCreate(coleso));
        }

        //====================================================================

        // Получение данных любого сотрудника
        coleso.getStaff().get((int)(coleso.getStaffCount() * Math.random())).getCompanyData(coleso);

        // Список высоких зарплат
        getTopSalaryList(coleso, 20);

        // Список низких зарплат
        getLowestSalaryList(coleso, 20);

        // Проверка количества сотрудников до увольнения
        System.out.println("\nКоличество сотрудников в компании " + coleso.getCompanyName() +
                " после увольнения: " + coleso.getStaffCount() + " человек");

        // Увольнение трети сотрудников
        firingPeople(coleso, coleso.getStaffCount() / 3);

        // Проверка как сработал метод увольнения
        System.out.println("\nКоличество сотрудников в компании " + coleso.getCompanyName() +
                " после увольнения: " + coleso.getStaffCount() + " человек");

        // Заново вывод списов зарплат
        getTopSalaryList(coleso, 20);
        getLowestSalaryList(coleso, 20);

        // Получение данных любого сотрудника
        coleso.getStaff().get((int)(coleso.getStaffCount() * Math.random())).getCompanyData(coleso);
    }

    //========================================================================

    /**
     * Методы создания нового объкта класса Employee
     * @return - возвращает созданный объект
     */
    public static Employee operatorCreate() {
        return new Operator();
    }

    public static Employee managerCreate() {
        return new Manager();
    }

    public static Employee topManagerCreate(Company company) {
        return new TopManager(company);
    }

    //========================================================================

    public static void firingPeople(Company company, int count) {
        Collections.shuffle(company.getStaff());
        for (int i = 0; i < count; i++) {
            int index = (int)((company.getStaffCount() - i) * Math.random());
            company.fire(company.getStaff().get(index));
        }
        System.out.println("\nСорян, сокращение.. \n" + count + " сотрудников были уволены" );
    }

    /**
     * Методы получения списка сотрудников с самыми низкими
     * @param company - указывает компанию, список из которой необходимо получить
     * @param count - указывает размер списка
     */
    public static void getLowestSalaryList(Company company, int count) {
        System.out.println("\nСписок " + count + " самых низких зарплат по возрастанию:\n");
        for(int g = 0; g < company.getLowestSalaryStaff(count).size(); g++) {
            System.out.println(company.getLowestSalaryStaff(count).get(g).getMonthSalary() + " руб.");
        }
    }

    /**
     * Метод получения списка сотрудников с самыми высокими зарплатами
     * @param company - указывает компанию, список из которой необходимо получить
     * @param count - указывает размер списка
     */
    public static void getTopSalaryList(Company company, int count) {
        System.out.println("\nСписок " + count + " самых высоких зарплат по убыванию:\n");
        for(int g = 0; g < company.getTopSalaryStaff(count).size(); g++) {
            System.out.println(company.getTopSalaryStaff(count).get(g).getMonthSalary() + " руб.");
        }
    }
}
