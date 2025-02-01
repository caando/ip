package duke.ui;

import java.util.List;

public interface Ui {

    public String getInput();

    public void showOutput(List<String> lines);

    public void showError(List<String> lines);

    public void showOutput(String... lines);

    public void showError(String... lines);

}
