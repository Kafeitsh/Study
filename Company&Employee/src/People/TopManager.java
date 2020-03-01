package People;

import Company.Company;

/**
 * Класс создания сотрудника "Топ-Менеджер"
 */
public class TopManager extends Worker {

    /**
     * Хранение ссылки на company
     */
    private Company company;

    /**
     * Переменные для получения зарплаты сотрудника "Топ-Менеджер"
     */
    private final int TOP_MANAGER_SALARY = (int) (60 + (40 * Math.random())) * 1000;
    private final int TOP_MANAGER_SALARY_BONUS_CONDITION = 10000000;

    /**
     * Конструктор - создание сотрудника "Топ-Менеджер"
     */
    public TopManager (Company company) {
        setCompany(company);
        setTopManagerSalary(getCompany());
    }

    /**
     * @return  - возвращает сумму дохода сотрудника "Топ-Менеджер"
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
        System.out.println("\nДанные о компаниии сотрудника \"Топ-Менеджер\": ");
        System.out.println("Название компании: " + getCompany().getCompanyName());
        System.out.println("Доход компании за месяц: " + getCompany().getIncome() + " рублей");
        System.out.println("\tКоличество сотрудников: " + getCompany().getStaffCount());
        System.out.println("Зарпалата сотрудника за месяц: " + getMonthSalary() + " рублей");
    }

    /**
     * Метод возвращает сумму заработанных для компании денег, класс TopManager возвращает ноль.
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

    private void setTopManagerSalary(Company company) {
        if(company.getIncome() >= TOP_MANAGER_SALARY_BONUS_CONDITION) {
            monthSalary = getTopManagerSalary() + getTopManagerSalary() + (getTopManagerSalary() / 2);
        } else {
            monthSalary = getTopManagerSalary();
        }
    }

    private int getTopManagerSalary() {
        return TOP_MANAGER_SALARY;
    }

}
