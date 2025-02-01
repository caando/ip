package duke.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Cli implements Ui {
    private static final String LINE_SEPARATOR = "____________________________________________________________";

    public Cli() {
        showLine(LINE_SEPARATOR);
        showLine("Hello! I'm Mr Meeseeks");
        showLine("What can I do for you?");
        showLine(LINE_SEPARATOR);
    }

    private void showLine(String line) {
        System.out.println(String.format("    %s", line));
    }

    public void close() {
        showLine("Bye. Hope to see you again soon!");
        showLine(LINE_SEPARATOR);
    }

    @Override
    public String getInput() {
        String input;
        try (Scanner scanner = new Scanner(System.in)) {
            input = scanner.nextLine().trim();
        }
        showLine(LINE_SEPARATOR);
        return input;
    }

    @Override
    public void showOutput(List<String> lines) {
        for (String line : lines) {
            showLine(line);
        }
        showLine(LINE_SEPARATOR);
    }

    @Override
    public void showError(List<String> lines) {
        for (String line : lines) {
            showLine(String.format("OOPS!!! %s", line));
        }
        showLine(LINE_SEPARATOR);
    }

    @Override
    public void showOutput(String... lines) {
        showOutput(new ArrayList<>(Arrays.asList(lines)));
    }

    @Override
    public void showError(String... lines) {
        showError(new ArrayList<>(Arrays.asList(lines)));
    }
}
