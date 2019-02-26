package Gui;

import javafx.event.ActionEvent;

public interface ControllerInterface {
    void setSatzlaenge();

    void setProperties();

    void setPeriod(ActionEvent actionEvent);

    void markovGenerateButton();
}
