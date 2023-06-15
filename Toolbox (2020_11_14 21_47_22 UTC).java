import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Toolbox {
    private ArrayList<String> prepositions;

    public Toolbox()
    {

    }

    public static ArrayList<String> option1(ArrayList<String> list) // populates a user-specified list
    // works!!!
    {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Enter a password");
        String pass = keyboard.nextLine();
        while (!pass.equals("")) {
            list.add(pass);
            System.out.println("Enter another password or press [ENTER] to begin");
            pass = keyboard.nextLine();
        }
        return list;
    }

    public static ArrayList<String> option2(ArrayList<String> list) // adds the dictionary to the list
    {
        int counter = 0;
        Scanner keyboard = new Scanner(System.in);
        loadDictionary(list);
        ArrayList<Character> ascii = new ArrayList<>();

        System.out.println("Would you like to append numbers and special characters?");
        System.out.printf("[%s]%30s\n", "Y", "Yes");
        System.out.printf("[%s]%30s\n", "N", "No");
        String opt1 = keyboard.nextLine().toLowerCase();

        System.out.println("Would you like to add permutations of letters and numbers?");
        System.out.printf("[%s]%30s\n", "Y", "Yes");
        System.out.printf("[%s]%30s\n", "N", "No");
        String opt2 = keyboard.nextLine().toLowerCase();

        if (opt1.equals("n") && opt2.equals("n"))
        {
            return list;
        }
        else
        {
            loadASCII(ascii);
            ArrayList<String> modifiedList = new ArrayList<>();

            if (opt1.equals("y"))
            {
                for (int sub = 0; sub < ascii.size(); sub ++)
                {
                    if (!Character.isLetter(ascii.get(sub)))
                    {
                        for (int index = 0; index < list.size(); index ++)
                        {
                            StringBuilder s = new StringBuilder(list.get(index));
                            s.append(ascii.get(sub));
                            modifiedList.add(String.valueOf(s));
                            System.out.println(s);
                        }
                    }
                }
            }
            if (opt2.equals("y"))
            {
                for (int index = 0; index < list.size(); index ++)
                {
                    StringBuilder s = new StringBuilder(list.get(index));

                    for (int pos = 0; pos < s.length(); pos ++)
                    {
                        if (s.charAt(pos) == 'a') {
                            s.setCharAt(pos, '4');
                            modifiedList.add(String.valueOf(s));
                            System.out.println(s);
                        }
                        if (s.charAt(pos) == 'e') {
                            s.setCharAt(pos, '3');
                            modifiedList.add(String.valueOf(s));
                            System.out.println(s);
                        }
                        if (s.charAt(s.length()-1) == 's')
                        {
                            s.setCharAt(s.length()-1, 'z');
                            modifiedList.add(String.valueOf(s));
                            System.out.println(s);
                        }
                    }
                }
            }
            return modifiedList;
        }

    }
    // works!!!

    public static ArrayList<String> option3(ArrayList<String> list) // adds permutations of the ASCII chart to a list
    {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("WARNING: This method uses recursion and may require extended run time!");
        System.out.println("Are you sure you would like to proceed?");
        System.out.printf("[%s]%30s\n", "Y", "Yes");
        System.out.printf("[%s]%30s\n", "N", "No");
        String o = keyboard.nextLine().toLowerCase();

        if (o.equals("y")) {
            ArrayList<Character> ascii = new ArrayList<>();
            loadASCII(ascii);
            // 94 printable ASCII characters

            System.out.println("Enter the upper limit of characters for this password attempt");
            System.out.println("WARNING: Your selection = 'n'. There will be 94^n permutations!!!");
            System.out.println("Crash likely for n > 3");
            String limit = keyboard.nextLine();

            int n = Integer.parseInt(limit);

            System.out.println("Generating " + Math.pow(94, n) + ") permutations. This might take awhile..."); // calls recursive method here

            try {
//                gen("", n, list, ascii);
                genRec("", n, list, ascii, 0, 0);
                System.out.println("...Finished generating permutations!");
            } catch (OutOfMemoryError e) {
                System.out.println("Out of memory!");
                System.out.println("Number of entries generated prior to crash: " + list.size());
                return list;
            }

            return list;
        } else if (o.equals("n")) // returns an effectively empty list
        {
            System.out.println("Okay, starting over...");
            list.add("");
            return list;
        } else // returns an effectively empty list
        {
            System.out.println("Invalid input. Starting over...");
            list.add("");
            return list;
        }
    }

    public static ArrayList<String> option4(ArrayList<String> list) // employs RNG to generate password combinations
    {
        Scanner keyboard = new Scanner(System.in);

        ArrayList<Character> ascii = new ArrayList<>();
        loadASCII(ascii);
        // 94 printable ASCII characters

        System.out.println("Enter a password length");
        String limit = keyboard.nextLine();
        int n = Integer.parseInt(limit);

        int max = 50_000_000;

        StringBuilder s = new StringBuilder("");
        while (s.length() < n)
        {
            s.append(" ");
        }

        System.out.println("Building passwords...");
        for (int index = 0; index < max; index++)
        {
            for (int pos = 0; pos < s.length(); pos ++)
            {
                int random = RNG(ascii.size());
                s.setCharAt(pos, ascii.get(random));
            }
            String pass = String.valueOf(s);
            list.add(pass);
        }
        return list;
    }
    public static int RNG(int rSpace) // generates a random number limited by "rSpace"
    {
        Random r = new Random();
        return r.nextInt(rSpace);
    }

    public static void gen(String str, int n, ArrayList<String> list, ArrayList<Character> ascii) {
        // n = size
        int counter = 0; // total loop iterations
        int pos = 0; // horizontal position in the string
        int index = 0; // position in the reference array (ascii)
        StringBuilder s = new StringBuilder(str);

        if (s.length() < n) // recursive case to build up the string size
        {
            gen(str + ascii.get(index), n, list, ascii);
        } else // base case, now working with a string of size "n"
        {
            list.add(str); //initial string

                while (index < ascii.size() && pos < n)
                {
                    s.setCharAt(0, ascii.get(index++));
                    if (index >= ascii.size()) // reset step
                    {
                        index = 0;
                        int cycle = 0;

                        s.setCharAt(0, ascii.get(0)); // resets first position
                        pos++; // advance to the next position
                        s.setCharAt(cycle, ascii.get(0));
                    }
                    System.out.println(s);
                    list.add(String.valueOf(s));
                    counter++;
                }
        }
    }

    public static void genRec(String s, int n, ArrayList<String> list, ArrayList<Character> ascii, int index, int counter)
    {
        if (s.length() < n)
        {
            genRec(s + String.valueOf(ascii.get(0 + 0)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 1)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 2)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 3)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 4)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 5)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 6)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 7)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 8)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 9)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 10)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 11)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 12)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 13)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 14)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 15)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 16)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 17)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 18)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 19)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 20)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 21)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 22)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 23)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 24)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 25)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 26)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 27)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 28)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 29)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 30)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 31)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 32)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 33)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 34)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 35)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 36)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 37)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 38)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 39)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 40)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 41)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 42)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 43)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 44)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 45)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 46)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 47)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 48)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 49)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 50)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 51)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 52)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 53)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 54)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 55)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 56)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 57)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 58)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 59)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 60)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 61)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 62)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 63)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 64)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 65)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 66)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 67)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 68)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 69)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 70)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 71)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 72)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 73)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 74)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 75)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 76)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 77)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 78)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 79)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 80)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 81)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 82)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 83)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 84)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 85)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 86)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 87)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 88)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 89)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 90)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 91)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 92)), n, list, ascii, index, counter);
            genRec(s + String.valueOf(ascii.get(0 + 93)), n, list, ascii, index, counter);
        }
        else
        {
                    System.out.println(s);
                    list.add(s);
        }
    }

    public static void loadDictionary(ArrayList<String> dictionary)
    {
        File f = new File("dictionary.txt");
        try {
            Scanner inputFile = new Scanner(f);
            while (inputFile.hasNextLine()) {
                String line = inputFile.nextLine();
                dictionary.add(line);
            }
            inputFile.close();
            System.out.println("done! " + dictionary.size() + " items loaded successfully");
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void loadASCII(ArrayList<Character> ascii) {
        File a = new File("ASCII.txt");

        System.out.println("loading ASCII.txt...");
        try {
            Scanner inputFile = new Scanner(a);

            while (inputFile.hasNextLine()) {
                String line = inputFile.nextLine();
                char c = line.charAt(0);
                ascii.add(c);
            }
            inputFile.close();
            System.out.println("...done!");
        } catch (FileNotFoundException e) {
            System.out.println("Could not open ASCII.txt!");
        }
    }

    public static void appendLog(String entry) {
        try {
            File f = new File("passwords.txt"); //opens the file
            Scanner inputFile = new Scanner(f);
            ArrayList<String> t = new ArrayList<>();

            while (inputFile.hasNextLine()) //populates a temporary list
            {
                String line = inputFile.nextLine();
                t.add(line); // consider incorporating a TreeSet<>() to get rid of duplicates
            }
            inputFile.close();

            t.add(entry); // adds the new password or comment to the list

            // modify to use printWriter instead later
            System.out.print("Adding content...");
            FileWriter fw = new FileWriter("passwords.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter outputFile = new PrintWriter(bw);

            for (int index = 0; index < t.size(); index++) {
                outputFile.println(t.get(index));
            }
            outputFile.close();
            System.out.println("done!");
        } catch (FileNotFoundException e) {
            System.out.println("Could not find passwords.txt!");
        } catch (IOException e) {
            System.out.println("Could not write to passwords.txt!");
        }
    }
    // works!!!

    public static void openLog() {
        File f = new File("passwords.txt");
        Scanner inputFile = null;
        try {
            inputFile = new Scanner(f);
        } catch (FileNotFoundException e) {
            System.out.println("Could not open the log!");
        }
        while (inputFile.hasNextLine()) {
            String line = inputFile.nextLine();
            System.out.println(line);
        }
        inputFile.close();
        System.out.println("<END OF REPORT>");
        System.out.println();
    }
}