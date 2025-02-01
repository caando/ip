package duke.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Cli implements Ui {

    private final Scanner scanner;

    public Cli() {
        scanner = new Scanner(System.in);
        showLineSeparator();
        showLine("Hello! I'm Mr Meeseeks");
        showLine("What can I do for you?");
        showLineSeparator();
    }

    private void showLineSeparator() {
        System.out.println("   _____________________________________________________________________________");
    }

    private void showLine(String line) {
        System.out.println(String.format("    %s", line));
    }

    @Override
    public String getInput() {
        String input = scanner.nextLine().trim();
        showLineSeparator();
        return input;
    }

    @Override
    public void showOutput(List<String> lines) {
        for (String line : lines) {
            showLine(line);
        }
        showLineSeparator();
    }

    @Override
    public void showOutput(String... lines) {
        showOutput(new ArrayList<>(Arrays.asList(lines)));
    }

    @Override
    public void showError(List<String> lines) {
        for (String line : lines) {
            showLine(String.format("OOPS!!! %s", line));
        }
        showLineSeparator();
    }

    @Override
    public void showError(String... lines) {
        showError(new ArrayList<>(Arrays.asList(lines)));
    }

    @Override
    public void close() {
        showLine("Bye. Hope to see you again soon!");
        showLineSeparator();
    }
}
