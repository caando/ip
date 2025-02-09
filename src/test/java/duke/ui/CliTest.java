package duke.ui;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CliTest {

    private InputStream mockInputStream;
    private PrintStream mockPrintStream;
    private ByteArrayOutputStream byteArrayOutputStream;
    private Cli cli;

    @BeforeEach
    public void setUp() {
        // Mock InputStream and PrintStream
        mockInputStream = new ByteArrayInputStream("Test input".getBytes());
        byteArrayOutputStream = new ByteArrayOutputStream();
        mockPrintStream = new PrintStream(byteArrayOutputStream);

        // Initialize the Cli object
        cli = new Cli(mockInputStream, mockPrintStream);
    }

    @Test
    public void testGetInput() {
        // Act
        String input = cli.getInput();

        // Assert
        Assertions.assertEquals("Test input", input);
    }

    @Test
    public void testShowOutput_withList() {
        // Act
        cli.showOutput(Arrays.asList("Line 1", "Line 2", "Line 3"));

        // Assert
        String expectedOutput = "    Line 1\n"
                + "    Line 2\n"
                + "    Line 3\n"
                + "   _____________________________________________________________________________\n";
        String normalizedExpected = expectedOutput.replace("\n", System.lineSeparator());
        Assertions.assertEquals(normalizedExpected, byteArrayOutputStream.toString());
    }

    @Test
    public void testShowOutput_withVarargs() {
        // Act
        cli.showOutput("Line 1", "Line 2");

        // Assert
        String expectedOutput = "    Line 1\n"
                + "    Line 2\n"
                + "   _____________________________________________________________________________\n";
        String normalizedExpected = expectedOutput.replace("\n", System.lineSeparator());
        Assertions.assertEquals(normalizedExpected, byteArrayOutputStream.toString());
    }

    @Test
    public void testShowError_withList() {
        // Act
        cli.showError(Arrays.asList("Error 1", "Error 2"));

        // Assert
        String expectedOutput = "    OOPS!!! Error 1\n"
                + "    OOPS!!! Error 2\n"
                + "   _____________________________________________________________________________\n";
        String normalizedExpected = expectedOutput.replace("\n", System.lineSeparator());
        Assertions.assertEquals(normalizedExpected, byteArrayOutputStream.toString());
    }

    @Test
    public void testShowError_withVarargs() {
        // Act
        cli.showError("Error 1", "Error 2");

        // Assert
        String expectedOutput = "    OOPS!!! Error 1\n"
                + "    OOPS!!! Error 2\n"
                + "   _____________________________________________________________________________\n";
        String normalizedExpected = expectedOutput.replace("\n", System.lineSeparator());
        Assertions.assertEquals(normalizedExpected, byteArrayOutputStream.toString());
    }

    @Test
    public void testStart() {
        // Act
        cli.start();

        // Assert
        String expectedOutput = "   _____________________________________________________________________________\n"
                + "    Hello! I'm Mr Meeseeks\n"
                + "    What can I do for you?\n"
                + "   _____________________________________________________________________________\n";
        String normalizedExpected = expectedOutput.replace("\n", System.lineSeparator());
        Assertions.assertEquals(normalizedExpected, byteArrayOutputStream.toString());
    }

    @Test
    public void testClose() {
        // Act
        cli.close();

        // Assert
        String expectedOutput = "    Bye. Hope to see you again soon!\n"
                + "   _____________________________________________________________________________\n";
        String normalizedExpected = expectedOutput.replace("\n", System.lineSeparator());
        Assertions.assertEquals(normalizedExpected, byteArrayOutputStream.toString());
    }
}
