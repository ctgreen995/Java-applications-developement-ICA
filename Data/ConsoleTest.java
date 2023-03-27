package Data;

import java.util.Scanner;

/**
 * This main class is used to simulate a console application, used to test the 
 * data objects of the program.
 * 
 * @author K0170764
 */
public class ConsoleTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);

        int choice = 0;
        Basket basket = Basket.getInstance();

        while (choice != 4) {

            String menu = "Furniture menu: \n\n1. Chair\n\n2.Table\n\n3.Desk"
                    + "\n\n4. Exit\n";
            System.out.println(menu);
            System.out.println("Choose menu option: ");

            choice = scan.nextInt();

            switch (choice) {
                case 1:

                    System.out.println("\nEnter Chair ID number: ");
                    int chairId = scan.nextInt();

                    System.out.println(
                            "Choose a material, 1. OAK or 2. WALNUT: ");
                    int chairMaterialChoice = scan.nextInt();

                    Material chairMaterialType;

                    if (chairMaterialChoice == 1) {
                        chairMaterialType = Material.OAK;
                    } else {
                        chairMaterialType = Material.WALNUT;
                    }

                    System.out.println("Arm chair, 1. NO or 2. YES");
                    int armChoice = scan.nextInt();

                    Arms armType;
                    if (armChoice == 1) {

                        armType = Arms.NO;
                    } else {
                        armType = Arms.YES;
                    }

                    System.out.println("How many Chairs? ");
                    int quantity = scan.nextInt();

                    Chair chair = new Chair(chairId, chairMaterialType,
                            quantity, armType);

                    System.out.println("\n" + chair.toString() + "\n");

                    basket.add(chair);
                    break;

                case 2:
                    System.out.println("\nEnter Table ID number: ");
                    int tableId = scan.nextInt();

                    System.out.println(
                            "Choose a material, 1. OAK or 2. WALNUT: ");
                    int tableMaterialChoice = scan.nextInt();

                    Material tableMaterialType;

                    if (tableMaterialChoice == 1) {
                        tableMaterialType = Material.OAK;
                    } else {
                        tableMaterialType = Material.WALNUT;
                    }

                    System.out.println(
                            "Choose a base type, 1. Chrome or 2. Wooden: ");
                    int baseChoice = scan.nextInt();

                    Base baseType;
                    if (baseChoice == 1) {
                        baseType = Base.CHROME;
                    } else {
                        baseType = Base.WOOD;
                    }

                    System.out.println("Enter Table diameter: ");
                    int diameter = scan.nextInt();

                    System.out.println("How many Tables? ");
                    int tableQuantity = scan.nextInt();

                    Table table = new Table(tableId, tableMaterialType,
                            tableQuantity, baseType, diameter);
                    System.out.println("\n" + table.toString() + "\n");

                    basket.add(table);
                    break;

                case 3:
                    System.out.println("\nEnter Table ID number: ");
                    int deskId = scan.nextInt();

                    System.out.println(
                            "Choose a material, 1. OAK or 2. WALNUT: ");
                    int deskMaterialChoice = scan.nextInt();

                    Material deskMaterialType;

                    if (deskMaterialChoice == 1) {
                        deskMaterialType = Material.OAK;
                    } else {
                        deskMaterialType = Material.WALNUT;
                    }

                    System.out.println("How many drawers, 0 - 4: ");
                    int drawers = scan.nextInt();

                    System.out.println("Enter width: ");
                    int width = scan.nextInt();

                    System.out.println("Enter depth: ");
                    int depth = scan.nextInt();

                    System.out.println("How many desks? ");
                    int deskQuantity = scan.nextInt();

                    Desk desk = new Desk(deskId, deskMaterialType, deskQuantity,
                            drawers, width, depth);
                    System.out.println("\n" + desk.toString() + "\n");

                    basket.add(desk);
            }
        }

        System.out.println("Basket contents: \n" + basket.toString());
    }

}
