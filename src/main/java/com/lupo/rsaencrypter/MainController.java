package com.lupo.rsaencrypter;

import com.jfoenix.controls.JFXTextArea;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class MainController {
	
	@FXML
	public void initialize() {
		
		String keyPart1 = Base62.encodeInt(Main.publicKey[0]);
		String keyPart2 = Base62.encodeInt(Main.publicKey[1]);
		
		publicKeyTextArea.setText("[" + keyPart1 + ":" + keyPart2 + "]");
		
	}
	
	
	
    @FXML
    private JFXTextArea publicKeyTextArea;

    @FXML
    void openDecrypter(ActionEvent event) {
    	
    	Main.decrypterStage.show();	
    	
    }

    @FXML
    void openEncrypter(ActionEvent event) {
    	
    	Main.encrypterStage.show();	
    	
    }
    
    

}
