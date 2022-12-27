package com.lupo.rsaencrypter;

import com.jfoenix.controls.JFXTextArea;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class DecrypterController {

    @FXML
    private JFXTextArea encryptedTextArea;

    @FXML
    private JFXTextArea decryptedTextArea;

    @FXML
    void decryptMessage(ActionEvent event) {
    	
    	decryptedTextArea.setText(RSA.decryptMessage(encryptedTextArea.getText()));
    	
    }

}
