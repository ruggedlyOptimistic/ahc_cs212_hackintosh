import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public Client()
    {
        try {
            System.out.println("Connecting to server...");
            socket = new Socket("localhost", 58999);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (Exception e) {
            System.out.println("Run the server first.");
        }
    }

    protected void finalize() {
        try {
            socket.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public String sendPassword(String pass) {
//        if (!HUSH) System.out.print("Sending: " + pass);
        out.println(pass);
        String result = null;
        try {
            result = in.readLine();
//            if (!HUSH)
//            {
//                if (result.equals("no"))
//                    System.out.println(" (wrong password)");
                if (result.equals("yes"))
                {
//                    System.out.println(" (CORRECT!)");
//                    System.out.println();
                    System.out.print("logging a password...");
                    Toolbox.appendLog(pass);
                }

//            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    //change this to true when running your code an extended time
//    public static boolean HUSH = false;

    public static void main(String[] args)
    {
        Scanner keyboard = new Scanner(System.in);
        Client me = new Client();

        System.out.println("Welcome whiteHat");
//        System.out.println("HUSH set to " + HUSH);
        System.out.println("Please select an option from the list:");
        System.out.println();
        System.out.printf("[%s]%30s\n", "I", "Add New Comment");
        System.out.printf("[%s]%30s\n", "D", "Display Progress Log");
        System.out.println();
        System.out.printf("[%d]%30s\n", 0, "Exit Utility");
        System.out.printf("[%d]%30s\n", 1, "User Specified List");
        System.out.printf("[%d]%30s\n", 2, "Dictionary");
        System.out.printf("[%d]%30s\n", 3, "ASCII Permutations");
        System.out.printf("[%d]%30s\n", 4, "Random ASCII Permutations");
        String opt = keyboard.nextLine().toLowerCase(); //initial selection
//        System.out.println("Would you like to display the content?");
//        System.out.println();
//        System.out.printf("[%s]%30s\n", "Y", "Yes");
//        System.out.printf("[%s]%30s\n", "N", "No");
//        String b = keyboard.nextLine().toLowerCase();
//        boolean boo;

        while (!opt.equals("0"))
        {
            ArrayList<String> tempList = new ArrayList<>(); // tempList must be re-created upon each loop iteration!

            if (opt.equals("i"))
            {
                System.out.println("Enter comment:");
                StringBuilder comment = new StringBuilder("<begin comment>   ");
                String notes = keyboard.nextLine();
                comment.append(notes);
                comment.append("     <end comment>");

                System.out.println("Opening progress log...");
                Toolbox.appendLog(String.valueOf(comment));
            }
            else if (opt.equals("d"))
            {
                System.out.println();
                System.out.println("Retrieving list...");
                Toolbox.openLog();
            }
            else if (opt.equals("1")) //User chooses to manually enter a password attempt
            {
                Toolbox.option1(tempList);
            }
            else if (opt.equals("2")) // implements a dictionary attack
            {
                Toolbox.option2(tempList);
            }
            else if (opt.equals("3"))
            {
                Toolbox.option3(tempList);
            }
            else if (opt.equals("4"))
            {
                Toolbox.option4(tempList);
            }
            if (!tempList.isEmpty())
            {
                System.out.println("Trying " + tempList.size() + " passwords...");
                for (String tempListItem : tempList)
                {
                    me.sendPassword(tempListItem);
                }
                System.out.println("done!");
            }

//            System.out.println("HUSH set to " + HUSH);
            System.out.println("Please select an option from the list."); // recurring selection
            System.out.println();
            System.out.printf("[%s]%30s\n", "I", "Add New Comment");
            System.out.printf("[%s]%30s\n", "D", "Display Progress Log");
            System.out.println();
            System.out.printf("[%d]%30s\n", 0, "Exit Utility");
            System.out.printf("[%d]%30s\n", 1, "User Specified List");
            System.out.printf("[%d]%30s\n", 2, "Dictionary");
            System.out.printf("[%d]%30s\n", 3, "ASCII Permutations");
            System.out.printf("[%d]%30s\n", 4, "Random ASCII Permutations");
            opt = keyboard.nextLine().toLowerCase();
        }
    }
}