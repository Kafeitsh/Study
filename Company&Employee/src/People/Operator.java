package People;

import Company.Company;

/**
 * Класс создания сотрудника "Оператор"
 */
public class Operator extends Worker {

    /**
     * Хранение ссылки на company
     */
    private Company company;

    /**
     * Переменные для расчета зарплаты сотрудника "Оператор"
     */
    private final int OPERATOR_SALARY = (int) (35 + (35 * Math.random())) * 1000;

    /**
     * Конструктор - создание сотрудника "Оператор"
     */
    public Operator() {
        monthSalary = getOperatorSalary();
    }

    /**
     * @return - возвращает значение запрлаты сотрудника "Оператор"
     */
    @Override
    public int getMonthSalary() {
        return monthSalary;
    }

    /**
     * Метод получения данных о компании
     * @param company - указывает объект класса "Компания", методы которого используются для
     *                  присваивания значений переменным
     */
    @Override
    public void getCompanyData(Company company) {
        setCompany(company);
        System.out.println("\nДанные о компаниии сотрудника \"Оператор\": ");
        System.out.println("Название компании: " + company.getCompanyName());
        System.out.println("Доход компании за месяц: " + company.getIncome() + " рублей");
        System.out.println("\tКоличество сотрудников: " + company.getStaffCount());
        System.out.println("Зарпалата сотрудника за месяц: " + getMonthSalary() + " рублей");
    }

    /**
     * Метод возвращает сумму заработанных для компании денег, класс Operator возвращает ноль.
     */
    @Override
    public int getEmployeeReturn() {
        return 0;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public int getOperatorSalary() {
        return OPERATOR_SALARY;
    }

}
