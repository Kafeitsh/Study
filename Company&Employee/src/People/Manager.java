package People;

import Company.Company;

/**
 * Класс создания сотрудника "Менеджер"
 */
public class Manager extends Worker {

    /**
     * Хранение ссылки на company
     */
    private Company company;

    /**
     * Переменная для расчета зарплаты сотрудника "Менеджер"
     */
    private final int MANAGER_START_SALARY = (int) (40 + (30 * Math.random())) * 1000;

    /**
     * Конструктор - создание сотрудника "Менеджер"
     */
    public Manager() {
        setEmployeeReturn();
        setManagerSalary();
    }

    private void setEmployeeReturn() {
        employeeReturn = (int) (MANAGER_START_SALARY * 5 * Math.random());
    }

    /**
     * @return - возвращает сумму заработанных для компании денег
     */
    @Override
    public int getEmployeeReturn() {
        return employeeReturn;
    }

    /**
     * @return  - возвращает сумму дохода сотрудника "Менеджер"
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
        System.out.println("\nДанные о компаниии сотрудника \"Менеджер\": ");
        System.out.println("Название компании: " + getCompany().getCompanyName());
        System.out.println("Доход компании за месяц: " + getCompany().getIncome() + " рублей");
        System.out.println("\tКоличество сотрудников: " + getCompany().getStaffCount());
        System.out.println("Зарпалата сотрудника за месяц: " + getMonthSalary() + " рублей");
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    private void setManagerSalary() {
        monthSalary = getManagerStartSalarySalary() + ((getEmployeeReturn() / 20));
    }

    public int getManagerStartSalarySalary() {
        return MANAGER_START_SALARY;
    }

}
