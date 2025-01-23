/**
 * The Duke class represents a java simple application that will
 * progressively gain more features through the course of the ip
 * project.
 */
public class Duke {

    private static final String LINE_SEPARATOR = "____________________________________________________________";

    private static void start() {
        System.out.println(LINE_SEPARATOR);
    }

    private static void greet() {
        System.out.println(" Hello! I'm Mr Meeseeks");
        System.out.println(" What can I do for you?");
        System.out.println(LINE_SEPARATOR);
    }

    private static void bye() {
        System.out.println(" Bye. Hope to see you again soon!");
        System.out.println(LINE_SEPARATOR);
    }

    public static void main(String[] args) {
        start();
        greet();
        bye();
    }
}
