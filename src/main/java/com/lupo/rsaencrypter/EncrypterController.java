package com.lupo.rsaencrypter;

import com.jfoenix.controls.JFXTextArea;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class EncrypterController {

    @FXML
    private JFXTextArea encryptedTextArea;

    @FXML
    private JFXTextArea headerTextArea;

    @FXML
    private JFXTextArea keyTextArea;

    @FXML
    private JFXTextArea messageTextArea;
    
    @FXML
    void initialize() {
    	
    	addTextLimiter(messageTextArea, 1000);
    	
    	
    }

    @FXML
    void encryptMessage(ActionEvent event) {
    	
    	encryptedTextArea.setText(RSA.encryptMessage(
    			
    		messageTextArea.getText(),
    		keyTextArea.getText(),
    		headerTextArea.getText()
    		
    			
    	));
    	
    }
    
    public static void addTextLimiter(final TextArea tf, final int maxLength) {
        tf.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                if (tf.getText().length() > maxLength) {
                    String s = tf.getText().substring(0, maxLength);
                    tf.setText(s);
                }
            }
        });
    }

}
