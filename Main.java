import java.util.*;
import java.io.*;
import java.lang.reflect.Array;

class Main {
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_RESET = "\u001B[0m";

    public static void main(String[] args) {

        Dictionary<Integer, String> hangmen = new Hashtable<>();
        hangmen.put(0, "______\n|   _o_\n|    |\n|   / \\\n|\n|");
        hangmen.put(1, "______\n|   _o_\n|    |\n|   /\n|\n|");
        hangmen.put(2, "______\n|   _o_\n|    |\n|\n|\n|\n");
        hangmen.put(3, "______\n|   _o_\n|\n|\n|\n|");
        hangmen.put(4, "______\n|    o\n|\n|\n|\n|");
        hangmen.put(5, "______\n|\n|\n|\n|\n|");

        var data = new ArrayList<String>();
        data = getData();

        Random random = new Random();

        Scanner sc = new Scanner(System.in);

        for (int i = 0; i < 6; i++) {
            System.out.println(hangmen.get(i));
        }

        while (true) {
            System.out.println(ANSI_CYAN + "\nJOGO DA FORCA!" + ANSI_RESET);
            System.out.println("você pode digitar a palavra inteira - serao palavras ou frases - nao tem acentos ou \"ç\"\n");

            int lives = 5;
            int randomIndex = random.nextInt(data.size());
            String word = data.get(randomIndex).toUpperCase();

            int n = word.length();

            var hidden = new ArrayList<String>();
            var fails = new ArrayList<String>();

            for (int i = 0; i < n; i++) {
                if (word.charAt(i) == ' ') {
                    hidden.add(" ");
                } else {
                    hidden.add("_");
                }
            }
            
            System.out.println(String.join(" ", hidden));

            while (hidden.contains("_")) {
                System.out.println(hangmen.get(lives));

                System.out.print("Digite uma letra: ");
                var letter = sc.nextLine().toUpperCase();

                if (letter.equals(word)) {
                    for (int i = 0; i < word.length(); i++) {
                        hidden.set(i, String.valueOf(letter.charAt(i)));
                        System.out.println();
                        System.out.println(String.join(" ", hidden));
                    }
                }
                
                else if (fails.contains(letter) || hidden.contains(letter)) {
                    System.out.println(ANSI_PURPLE + "Você já tentou essa letra!" + ANSI_RESET);
                    if (fails.contains(letter)) {
                        System.out.println(String.join(" ", fails));
                    }
                }

                else if (word.contains(letter)) {
                    for (int i = 0; i < word.length(); i++) {
                        if (word.charAt(i) == letter.charAt(0)) {
                            hidden.set(i, letter);
                        }
                    }
                }
            }
        }

    }

    public static ArrayList<String> getData() {
        var list = new ArrayList<String>();

        try (BufferedReader reader = new BufferedReader(new FileReader("Data.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                list.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }
}