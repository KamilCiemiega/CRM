import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;

public class BankApplication {


    private static Map<String, BankAccount> accounts = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);


        accounts.put("1111", new BankAccount("1111", "Robert"));
        accounts.put("2222", new BankAccount("2222", "Filip"));
        accounts.put("3333", new BankAccount("3333", "Patryk"));


        while (true){
            System.out.println("Wybierz akcję:");
            System.out.println("1 - Wpłata");
            System.out.println("2 - Wypłata");
            System.out.println("3 - Przelew");
            System.out.println("4 - Wyjście");

            int nb = scanner.nextInt();

            try {
                switch (nb) {
                    case 1:
                        deposit(scanner);
                        break;
                    case 2:
                        withdraw(scanner);
                        break;
                    case 3:
                        transfer(scanner);
                        break;
                    case 4:
                        System.out.println("Zakończono działanie programu.");
                        return;
                    default:
                        System.out.println("Nieprawidłowa opcja w menu!");
                }
            } catch (Exception ex) {

                System.out.println("Błąd: " + ex.getMessage());
            }
        }
    }


    private static void deposit(Scanner scanner)
            throws AccountNotFoundException, InvalidAmountException
    {
        System.out.print("Podaj numer konta do wpłaty: ");
        String accountNumber = scanner.next();

        BankAccount account = accounts.get(accountNumber);
        if (account == null){
            throw new AccountNotFoundException(
                    "Brak konta o numerze [" + accountNumber + "]!"
            );
        }
        System.out.print("Podaj kwotę do wpłaty: ");
        double amount = scanner.nextDouble();

        account.deposit(amount);
        System.out.println("Wpłacono " + amount + " zł.");
        System.out.println("Nowy stan konta ["
                + account.getAccountNumber() + "]: "
                + account.getBalance() + " zł.");
    }


    private static void withdraw(Scanner scanner)
            throws AccountNotFoundException, InvalidAmountException, InsufficientFundsException
    {
        System.out.print("Podaj numer konta do wypłaty: ");
        String accountNumber = scanner.next();

        BankAccount account = accounts.get(accountNumber);
        if (account == null){
            throw new AccountNotFoundException(
                    "Brak konta o numerze [" + accountNumber + "]!"
            );
        }
        System.out.print("Podaj kwotę do wypłaty: ");
        double amount = scanner.nextDouble();

        account.withdraw(amount);
        System.out.println("Wypłacono " + amount + " zł.");
        System.out.println("Nowy stan konta ["
                + account.getAccountNumber() + "]: "
                + account.getBalance() + " zł.");
    }


    private static void transfer(Scanner scanner)
            throws AccountNotFoundException, InvalidAmountException, InsufficientFundsException
    {
        System.out.print("Podaj numer konta (z którego przelewamy): ");
        String fromAccountNumber = scanner.next();
        BankAccount fromAccount = accounts.get(fromAccountNumber);
        if (fromAccount == null){
            throw new AccountNotFoundException(
                    "Brak konta o numerze [" + fromAccountNumber + "]!"
            );
        }

        System.out.print("Podaj numer konta docelowego: ");
        String toAccountNumber = scanner.next();
        BankAccount toAccount = accounts.get(toAccountNumber);
        if (toAccount == null){
            throw new AccountNotFoundException(
                    "Brak konta o numerze [" + toAccountNumber + "]!"
            );
        }

        System.out.print("Podaj kwotę do przelewu: ");
        double amount = scanner.nextDouble();


        fromAccount.transfer(toAccount, amount);
        System.out.println("Przelano " + amount + " zł z konta ["
                + fromAccountNumber + "] na konto [" + toAccountNumber + "].");

        System.out.println("Nowy stan konta [" + fromAccountNumber
                + "]: " + fromAccount.getBalance() + " zł.");
        System.out.println("Nowy stan konta [" + toAccountNumber
                + "]: " + toAccount.getBalance() + " zł.");
    }

}
