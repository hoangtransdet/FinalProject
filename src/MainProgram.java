import java.util.InputMismatchException;
import java.util.Scanner;

public class MainProgram {

    public static void main(String[] argv) {

        Scanner input;
        int value = 0;
        System.out.println("Choose the MODE:\n 1) Server\n 2) Client");

        do {
            try {

                System.out.print("Enter the Mode: ");
                input = new Scanner(System.in);
                while(!input.hasNextInt()) {
                    System.out.print("Enter the Mode: ");
                    input = new Scanner(System.in);
                }
                value = input.nextInt();

            }
            catch (InputMismatchException e) {
            }
        }
        while (value > 2 || value < 1);

        if(value == 1) {
            new Server();
        }
        else if(value == 2) {
            Client c = new Client();
        }



    }
}
