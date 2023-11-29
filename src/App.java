/*
 * ENI Ecole Informatique project
 * Paul TANGUY (paul.tanguy2022@campus-eni.fr)
 * Maximot
 * App.java
 */


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class App {
    public static boolean areArraysEqual(char arrayOne[], char arrayTwo[]) {
        List<Character> listOne = new ArrayList<Character>();
        List<Character> listTwo = new ArrayList<Character>();

        for (char c : arrayOne) {
            listOne.add(c);
        }
        for (char c : arrayTwo) {
            listTwo.add(c);
        }

        return listOne.equals(listTwo);
    }

    public static boolean isInputValid(char shuffled[], char input[]) {
        List<Character> shuffledList = new ArrayList<Character>();
        List<Character> inputList = new ArrayList<Character>();

        for (char c : shuffled) {
            shuffledList.add(c);
        }
        for (char c : input) {
            inputList.add(c);
        }

        Collections.sort(shuffledList);
        Collections.sort(inputList);

        return shuffledList.equals(inputList);
    }

    public static void show(char letters[]) {
        for (char c : letters) {
            System.out.print(c);
        }
    }

    public static char[] shuffle(char letters[]) {
        List<Character> javaIsDumb = new ArrayList<Character>();
        char shuffled[];
        int i = 0;

        for (char c : letters) {
            javaIsDumb.add(c);
        }
        Collections.shuffle(javaIsDumb);
        shuffled = new char[javaIsDumb.size()];
        for (char c : javaIsDumb) {
            shuffled[i++] = c;
        }
        return shuffled;
    }

    public static char[] generateWord() throws FileNotFoundException {
        Random random = new Random();
        Scanner scanner = new Scanner(new File("assets/dictionary.txt"));
        String line;
        String result = "";
        char letters[];
        int upperBound = 0;

        while (scanner.hasNextLine()) {
            upperBound += 1;
            line = scanner.nextLine();

            if (random.nextInt(upperBound) == 0) {
                result = line;
            }
        }
        letters = result.toUpperCase().toCharArray();
        scanner.close();

        return letters;
    }

    public static int playOnce(Scanner stdinScanner, int credits) throws FileNotFoundException {
        char letters[] = generateWord();
        char shuffled[] = shuffle(letters);
        char userInput[] = {};
        int lives = 4;

        System.out.println("Here are your letters:");
        System.out.print("[");
        show(shuffled);
        System.out.println("]");
        System.out.println("What's the word?");

        while (!areArraysEqual(letters, userInput)) {
            System.out.print("> ");
            userInput = stdinScanner.nextLine().toUpperCase().toCharArray();

            if (areArraysEqual(letters, userInput)) {
                break;
            }

            if (lives > 0) {
                System.out.println(isInputValid(shuffled, userInput) ? "This is not the right word." : "Bad input, please use every provided letters.");
            } else {
                System.out.print("You lost! the word was ");
                show(letters);
                System.out.println();

                return credits - letters.length;
            }
            System.out.printf("You have %d tries left.\n", lives);
            lives -= 1;
        }

        System.out.printf("You won! you found the word in %d trie(s)\n", 5 - lives);
        return credits + letters.length;
    }

    public static void main(String args[]) throws Exception {
        System.out.printf("Welcome to Maximot(tm)\n");
        int credits = 50;
        boolean userIsPlaying = true;
        Scanner stdinScanner = new Scanner(System.in);
        String userInput = "";

        while (userIsPlaying) {
            credits = playOnce(stdinScanner, credits);
            System.out.printf("Current balance: %d credits\n", credits);
            System.out.print("Replay? [y/n]\n> ");
            userInput = stdinScanner.nextLine().toUpperCase();

            userIsPlaying = userInput.equals("Y") || userInput.equals("");
        }
        if (credits < 50) {
            System.out.printf("You lost %d credits in this run...\n", 50 - credits);
        } else {
            System.out.printf("You won %d credits in this run!\n", credits - 50);
        }
        stdinScanner.close();
    }
}
