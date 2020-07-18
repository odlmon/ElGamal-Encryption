package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Pair;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;

public class Controller {

    @FXML
    private ComboBox<String> cbMode;

    @FXML
    private Label tRoots;

    @FXML
    private TextField tInput;

    @FXML
    private TextField tOutput;

    @FXML
    private TextField tPrime;

    @FXML
    private Button bCheckPrime;

    @FXML
    private ComboBox<String> cbRoots;

    @FXML
    private Label tX;

    @FXML
    private TextField tXValue;

    @FXML
    private Label tK;

    @FXML
    private TextField tKValue;

    @FXML
    private Button bAccept;

    @FXML
    private TextArea taOutputSample;

    @FXML
    private Label lY;

    @FXML
    private Label amountRoots;

    private BigInteger p, g, x, y, k;

    private ArrayList<BigInteger> roots;

    private boolean isPrime = false;

    @FXML
    public void initialize() {
        ObservableList<String> cypheringModes = FXCollections.observableArrayList("Шифрование", "Дешифрование");
        cbMode.setItems(cypheringModes);
        cbMode.getSelectionModel().select("Шифрование");
        tPrime.textProperty().addListener(((observable, oldValue, newValue) -> {
            lY.setText("Значение y: ");
            cbRoots.getItems().removeAll(cbRoots.getItems());
            amountRoots.setText("Количество корней g: ");
            tXValue.setText("");
            tKValue.setText("");}));
        tXValue.textProperty().addListener(((observable, oldValue, newValue) -> {
            lY.setText("Значение y: ");
            if (!cbRoots.getSelectionModel().isEmpty() && !tKValue.getText().isEmpty() && !tXValue.getText().isEmpty()) {
                try {
                    x = new BigInteger(tXValue.getText());
                    k = new BigInteger(tKValue.getText());
                    if (x.compareTo(BigInteger.ONE) > 0 && x.compareTo(p.subtract(BigInteger.ONE)) < 0 &&
                            k.compareTo(BigInteger.ONE) > 0 && k.compareTo(p.subtract(BigInteger.ONE)) < 0 &&
                            (k.gcd(p.subtract(BigInteger.ONE)).compareTo(BigInteger.ONE) == 0)) {
                        bAccept.setDisable(false);
                        y = ElGamal.fastExp(new BigInteger(cbRoots.getSelectionModel().getSelectedItem()), x, p);
                        lY.setText("Значение y: " + y);
                    } else {
                        bAccept.setDisable(true);
                    }
                } catch (Exception ex) {

                }
            } else {
                bAccept.setDisable(true);
            }
        }));
        tKValue.textProperty().addListener(((observable, oldValue, newValue) -> {
            lY.setText("Значение y: ");
            if (!cbRoots.getSelectionModel().isEmpty() && !tKValue.getText().isEmpty() && !tXValue.getText().isEmpty()) {
                try {
                    x = new BigInteger(tXValue.getText());
                    k = new BigInteger(tKValue.getText());
                    if (x.compareTo(BigInteger.ONE) > 0 && x.compareTo(p.subtract(BigInteger.ONE)) < 0 &&
                            k.compareTo(BigInteger.ONE) > 0 && k.compareTo(p.subtract(BigInteger.ONE)) < 0 &&
                            k.gcd(p.subtract(BigInteger.ONE)).compareTo(BigInteger.ONE) == 0) {
                        bAccept.setDisable(false);
                        y = ElGamal.fastExp(new BigInteger(cbRoots.getSelectionModel().getSelectedItem()), x, p);
                        lY.setText("Значение y: " + y);
                    } else {
                        bAccept.setDisable(true);
                    }
                } catch (Exception ex) {

                }
            } else {
                bAccept.setDisable(true);
            }
        }));
    }

    @FXML
    void modeChange(ActionEvent event) {
        bAccept.setDisable(true);
        tXValue.setText("");
        tKValue.setText("");
        taOutputSample.setText("");
        boolean visibleMode = true;
        if (cbMode.getSelectionModel().getSelectedItem().equals("Дешифрование"))
            visibleMode = false;
        tRoots.setVisible(visibleMode);
        cbRoots.setVisible(visibleMode);
        amountRoots.setVisible(visibleMode);
        tK.setVisible(visibleMode);
        tKValue.setVisible(visibleMode);
        taOutputSample.setVisible(visibleMode);
        lY.setVisible(visibleMode);
        if (cbMode.getSelectionModel().getSelectedItem().equals("Шифрование")) { //проверить п на простоту и к как обычно
            tXValue.textProperty().addListener(((observable, oldValue, newValue) -> {
                lY.setText("Значение y: ");
                if (!cbRoots.getSelectionModel().isEmpty() && !tKValue.getText().isEmpty() && !tXValue.getText().isEmpty()) {
                    try {
                        x = new BigInteger(tXValue.getText());
                        k = new BigInteger(tKValue.getText());
                        if (x.compareTo(BigInteger.ONE) > 0 && x.compareTo(p.subtract(BigInteger.ONE)) < 0 &&
                                k.compareTo(BigInteger.ONE) > 0 && k.compareTo(p.subtract(BigInteger.ONE)) < 0 &&
                                (k.gcd(p.subtract(BigInteger.ONE)).compareTo(BigInteger.ONE) == 0)) {
                            bAccept.setDisable(false);
                            y = ElGamal.fastExp(new BigInteger(cbRoots.getSelectionModel().getSelectedItem()), x, p);
                            lY.setText("Значение y: " + y);
                        } else {
                            bAccept.setDisable(true);
                        }
                    } catch (Exception ex) {

                    }
                } else {
                    bAccept.setDisable(true);
                }
            }));
            tPrime.textProperty().addListener(((observable, oldValue, newValue) -> {
                lY.setText("Значение y: ");
                cbRoots.getItems().removeAll(cbRoots.getItems());
                amountRoots.setText("Количество корней g: ");
                tXValue.setText("");
                tKValue.setText("");}));
        } else if (cbMode.getSelectionModel().getSelectedItem().equals("Дешифрование")) {
            tXValue.textProperty().addListener(((observable, oldValue, newValue) -> {
                lY.setText("Значение y: ");
                if (!tPrime.getText().isEmpty() && !tXValue.getText().isEmpty()) {
                    try {
                        x = new BigInteger(tXValue.getText());
                        if (x.compareTo(BigInteger.ONE) > 0 && x.compareTo(p.subtract(BigInteger.ONE)) < 0 && isPrime) {
                            bAccept.setDisable(false);
                        } else {
                            bAccept.setDisable(true);
                        }
                    } catch (Exception ex) {

                    }
                } else {
                    bAccept.setDisable(true);
                }
            }));
            tPrime.textProperty().addListener(((observable, oldValue, newValue) -> {
                isPrime = false;
                cbRoots.getItems().removeAll(cbRoots.getItems());
                tXValue.setText("");
                tKValue.setText("");
                amountRoots.setText("Количество корней g: ");
                lY.setText("Значение y: ");
                bAccept.setDisable(true);
            }));
        }
    }

    @FXML
    void primeChange(ActionEvent event) {

    }

    @FXML
    void checkPrime(ActionEvent event) {
        cbRoots.getItems().removeAll(cbRoots.getItems());
        tXValue.setText("");
        tKValue.setText("");
        lY.setText("Значение y: ");
        amountRoots.setText("Количество корней g: ");
        bAccept.setDisable(true);
        String input = tPrime.getText();
        if (!input.isEmpty()) {
            try {
                p = new BigInteger(input);
                if (ElGamal.isPrime(p) && p.compareTo(BigInteger.valueOf(255)) > 0) {
                    isPrime = true;
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Внимание");
                    alert.setHeaderText("Ввод простого числа");
                    alert.setContentText("Число является простым.");
                    alert.showAndWait();
                    roots = ElGamal.getRoots(p);
                    amountRoots.setText("Количество корней g: " + roots.size());
                    int count = 0;
                    for (BigInteger element: roots) {
                        cbRoots.getItems().add(element.toString());
                        count++;
                        if (count > 10000)
                            break;
                    }
                } else {
                    isPrime = false;
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Внимание");
                    alert.setHeaderText("Ввод простого числа");
                    if (p.compareTo(BigInteger.valueOf(255)) > 0) {
                        alert.setContentText("Введено не простое число!");
                    } else if (p.compareTo(BigInteger.valueOf(255)) <= 0) {
                        alert.setContentText("Введено число меньше либо равно 255");
                    }
                    alert.showAndWait();
                }
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Внимание");
                alert.setHeaderText("Ввод простого числа");
                alert.setContentText("Введено не число, проверьте ввод!");
                alert.showAndWait();
            }
        }
    }

    @FXML
    void checkChange(ActionEvent event) {
        if (!cbRoots.getSelectionModel().isEmpty() && !tKValue.getText().isEmpty() && !tXValue.getText().isEmpty()) {
            try {
                x = new BigInteger(tXValue.getText());
                k = new BigInteger(tKValue.getText());
                if (x.compareTo(BigInteger.ONE) > 0 && x.compareTo(p.subtract(BigInteger.ONE)) < 0 &&
                        k.compareTo(BigInteger.ONE) > 0 && k.compareTo(p.subtract(BigInteger.ONE)) < 0 &&
                        k.gcd(p.subtract(BigInteger.ONE)).compareTo(BigInteger.ONE) == 0) {
                    bAccept.setDisable(false);
                    y = ElGamal.fastExp(new BigInteger(cbRoots.getSelectionModel().getSelectedItem()), x, p);
                    lY.setText("Значение y: " + y);
                } else {
                    bAccept.setDisable(true);
                }
            } catch (Exception ex) {

            }
        } else {
            bAccept.setDisable(true);
        }
    }

    private void encryptWithFiles(String plainFile, String cypherFile, BigInteger p, BigInteger g, BigInteger y,
                                        BigInteger k) {
        BigInteger plainText;
        Pair<BigInteger, BigInteger> cypherText;
        int amount = 0, row = 0;
        try (BufferedInputStream bufferedInputStream =
                     new BufferedInputStream(new FileInputStream(plainFile));
             BufferedOutputStream bufferedOutputStream =
                     new BufferedOutputStream(new FileOutputStream(cypherFile))) {
            while (((plainText = BigInteger.valueOf(bufferedInputStream.read())).compareTo(BigInteger.valueOf(-1))) != 0) {
                cypherText = ElGamal.encrypt(plainText, p, g, y, k);
                if (amount < 200) {
                    taOutputSample.appendText(cypherText.getKey() + " " + cypherText.getValue() + " ");
                    if (row == 3) {
                        row = 0;
                        taOutputSample.appendText("\n");
                    }
                    amount ++;
                    row++;
                }
                byte[] aPart = new byte[8], bPart = new byte[8];
                Arrays.fill(aPart, (byte) 0);
                Arrays.fill(bPart, (byte) 0);
                byte[] aTemp = cypherText.getKey().toByteArray();
                byte[] bTemp = cypherText.getValue().toByteArray();
                int counter = 0;
                for (int i = aPart.length - aTemp.length; i < 8; i++) {
                    aPart[i] = aTemp[counter++];
                }
                counter = 0;
                for (int i = bPart.length - bTemp.length; i < 8; i++) {
                    bPart[i] = bTemp[counter++];
                }
                bufferedOutputStream.write(aPart);
                bufferedOutputStream.write(bPart);
            }
        } catch (IOException ex) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Внимание");
            alert.setHeaderText("Имя файла");
            alert.setContentText("Некорректное имя файла!");
            alert.showAndWait();
        }
    }

    private void decryptWithFiles(String cypherFile, String plainFile, BigInteger x, BigInteger p) {
        BigInteger plainText;
        Pair<BigInteger, BigInteger> cypherText;
        byte[] inputArray = new byte[16];
        try (BufferedInputStream bufferedInputStream =
                     new BufferedInputStream(new FileInputStream(cypherFile), 16);
             BufferedOutputStream bufferedOutputStream =
                     new BufferedOutputStream(new FileOutputStream(plainFile))) {
            while (bufferedInputStream.read(inputArray, 0, 16) != -1) {
                cypherText = new Pair<>(new BigInteger(Arrays.copyOfRange(inputArray,0, 8)),
                        new BigInteger(Arrays.copyOfRange(inputArray, 8, 16)));
                plainText = ElGamal.decrypt(cypherText, x, p);
                bufferedOutputStream.write(plainText.byteValue());
            }
        } catch (IOException ex) {
            System.out.println("Ошибка");
        }
    }

    @FXML
    void acceptAction(ActionEvent event) {
        if (tInput.getText().isEmpty() || tOutput.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Внимание");
            alert.setHeaderText("Подтверждение действия");
            alert.setContentText("Файл не введен!");
            alert.showAndWait();
        } else {
            if (cbMode.getSelectionModel().getSelectedItem().equals("Шифрование")) {
                g = new BigInteger(cbRoots.getSelectionModel().getSelectedItem());
                encryptWithFiles(tInput.getText(), tOutput.getText(), p, g, y, k);
            } else if (cbMode.getSelectionModel().getSelectedItem().equals("Дешифрование")) {
                decryptWithFiles(tInput.getText(), tOutput.getText(), x, p);
            }
        }
    }

}



