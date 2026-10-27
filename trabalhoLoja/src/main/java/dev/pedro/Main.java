package dev.pedro;
import dev.pedro.components.MenuGeral;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        MenuGeral.exibir();

        System.out.println("Digite o nome do cliente: ");
        sc.nextLine();
    }
}
