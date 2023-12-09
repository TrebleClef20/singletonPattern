import java.util.Scanner;

public class PagibigOffice {
    public static void main(String[] args) {

        CentralizedQueuingSystem cqs = CentralizedQueuingSystem.getInstance();
        Scanner scan = new Scanner(System.in);

        while (true) {
            System.out.println(cqs.displayStatus());

            System.out.println("""

                    Select From the Options Below
                    1: Obtain a Queue Number
                    2: Reset a Queue Number
                    3: Insert 10 Queue Numbers
                    4: Exit
                    """);
            System.out.print("[1-3]: ");

            String s;
            while (!(s = scan.nextLine().trim()).matches("\\d+")) {
                System.out.println("enter only integer");
                System.out.print("[1-3]: ");
            }
            int selection = Integer.parseInt(s);

            System.out.println();
            switch (selection) {
                case 1:
                    int num = cqs.obtainQueueNumber();

                    System.out.println("Obtained Queue Number: " + String.format("%02d", num));
                    System.out.print("Adding to Queue\n");

                    cqs.automateAssign();
                    break;
                case 2:
                    System.out.print("[Enter number to remove]: ");

                    while (!(s = scan.nextLine().trim()).matches("\\d+")) {
                        System.out.println("enter only integer");
                        System.out.print("[Enter number to remove]: ");
                    }
                    int queueNumber = Integer.parseInt(s);
                    cqs.resetNumber(queueNumber);
                    cqs.automateAssign();
                    break;
                case 3:
                    // create sample Visitors
                    for (int i = 0; i < 10; i++) {
                        cqs.obtainQueueNumber();
                    }
                    cqs.automateAssign();
                    break;
                case 4:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid input, try again");
                    break;
            }
        }
    }
}
