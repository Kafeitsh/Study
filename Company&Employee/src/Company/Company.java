package Company;

import People.Employee;

import java.util.*;

public class Company
{
    /**
     * Константы - количество сотрудников разных типов
     */
    private final int OPERATOR_COUNT = 180;
    private final int MANAGER_COUNT = 80;
    private final int TOP_MANAGER_COUNT = 10;

    /**
     * Переменные необходмые для работы класса
     */
    private int income; // доход компании за месяц
    private String companyName; // строчное название компании
    private List<Employee> staff = new ArrayList<>(); // список сотрудников компании

    /**
     * Конструктор - создание объекта Company
     * @param companyName - присвает строчное название компании
     */
    public Company(String companyName) {
        this.companyName = companyName;
    }

    /**
     * @return - возвращает строчное название компании
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Метод нанимает одного сотрудника
     * @param employee - указывает тип нанимаемого сотрудника
     */
    public void hire(Employee employee) {
        staff.add(employee);
        income += employee.getEmployeeReturn();
    }

    /**
     * Метод нанимает список сотрудников
     * @param list - указывает список сотрудников
     */
    public void hireAll(List<Employee> list) {
        staff.addAll(list);
        for (Employee employee : list) {
            income += employee.getEmployeeReturn();
        }
    }

    /**
     * Метод увлольняет одного сотрудника
     * @param employee - указывает тип увольняемого сотрудника
     */
    public void fire(Employee employee) {
        staff.remove(employee);
    }

    /**
     * @return - возвращает доход компании за последний месяц
     */
    public int getIncome() {
        return income;
    }

    /**
     * @return - возвращает количество сотрудников
     */
    public int getStaffCount() {
        return staff.size();
    }

    /**
     * @return - возвращает список сотрудников
     */
    public List<Employee> getStaff() {
        return staff;
    }

    /**
     * @return - возвращает количество нанимаемых сотрудников класса "Оператор"
     */
    public int getOperatorCount() {
        return OPERATOR_COUNT;
    }

    /**
     * @return - возвращает количество нанимаемых сотрудников класса "Менеджер"
     */
    public int getManagerCount() {
        return MANAGER_COUNT;
    }

    /**
     * @return - возвращает количество нанимаемых сотрудников класса "Топ-Менеджер"
     */
    public int getTopManagerCount() {
        return TOP_MANAGER_COUNT;
    }

    /**
     * Получение списка сотрудников с самыми высокими зарлатами
     * @param count - количество сотрудников в списке
     * @return - возвращает список
     */
    public ArrayList<Employee> getTopSalaryStaff(int count) {
            staff.sort(Comparator.comparing(Employee::getMonthSalary));
            ArrayList<Employee> topSalaryStaff = new ArrayList<>();
        if (count <= getStaffCount()) {
            for (int i = getStaffCount(); i > (getStaffCount() - count); i--) {
                topSalaryStaff.add(staff.get(i - 1));
            }
        } else {
            count = getStaffCount();
            for (int i = getStaffCount(); i > (getStaffCount() - count); i--) {
                topSalaryStaff.add(staff.get(i - 1));
            }
        }
        return topSalaryStaff;
    }

    /**
     * Получение списка сотрудников с самыми низкими зарлатами
     * @param count - количество сотрудников в списке
     * @return - возвращает список
     */
    public ArrayList<Employee> getLowestSalaryStaff(int count) {
        staff.sort(Comparator.comparing(Employee::getMonthSalary));
        ArrayList<Employee> lowestSalaryStaff = new ArrayList<>();
        if (count <= getStaffCount()) {
            for (int i = 0; i < count; i++) {
                lowestSalaryStaff.add(staff.get(i));
            }
        } else {
            count = getStaffCount();
            for (int i = 0; i < count; i++) {
                lowestSalaryStaff.add(staff.get(i));
            }
        }
        return lowestSalaryStaff;
    }
}
