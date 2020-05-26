/**
 * класс операции из выписки
 */
public class Movement {

    /**
     * номер счета
     */
    private String accountNumber;

    /**
     * валюта
     */
    private String currency;

    /**
     * информация об операции
     */
    private String infoAboutOperation;

    /**
     * поступление дс
     */
    private Double income;

    /**
     * трата
     */
    private Double expense;

    public Movement(String accountNumber, String currency,
                    String infoAboutOperation,
                    Double income, Double expense)
    {
        this.accountNumber = accountNumber;
        this.currency = currency;
        this.infoAboutOperation = infoAboutOperation;
        this.income = income;
        this.expense = expense;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getInfoAboutOperation() {
        return infoAboutOperation;
    }

    public void setInfoAboutOperation(String infoAboutOperation) {
        this.infoAboutOperation = infoAboutOperation;
    }

    public Double getIncome() {
        return income;
    }

    public void setIncome(Double income) {
        this.income = income;
    }

    public Double getExpense() {
        return expense;
    }

    public void setExpense(Double expense) {
        this.expense = expense;
    }

    @Override
    public String toString() {
        return accountNumber + " - " + " - "
                + infoAboutOperation + " - " + income + "/"
                + expense + " " + currency;
    }
}
