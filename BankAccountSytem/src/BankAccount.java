public class BankAccount {

    private String accountNumber;
    private String ownerName;
    private double balance;

    public BankAccount(String accountNumber, String ownerName){
        this.accountNumber = accountNumber;
        this.ownerName     = ownerName;
        this.balance       = 0.0;
    }


    public void deposit(double amount) throws InvalidAmountException {
        if (amount <= 0){
            throw new InvalidAmountException("Nieprawidłowa kwota do wpłaty: " + amount + " zł!");
        }
        this.balance += amount;
    }


    public void withdraw(double amount)
            throws InvalidAmountException, InsufficientFundsException {
        if (amount <= 0){
            throw new InvalidAmountException("Nieprawidłowa kwota do wypłaty: " + amount + " zł!");
        }
        if (amount > this.balance){
            throw new InsufficientFundsException("Brak środków na koncie, nie można wypłacić " + amount + " zł!");
        }
        this.balance -= amount;
    }


    public void transfer(BankAccount to, double amount)
            throws InvalidAmountException, InsufficientFundsException {
        // Najpierw wypłacamy z konta źródłowego
        this.withdraw(amount);
        // Następnie wpłacamy na konto docelowe
        to.deposit(amount);
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public double getBalance() {
        return balance;
    }
}
