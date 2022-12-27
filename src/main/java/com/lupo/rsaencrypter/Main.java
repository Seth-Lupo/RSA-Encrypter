package com.lupo.rsaencrypter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {
		
		public static Stage mainStage;
		public static Stage encrypterStage;
		public static Stage decrypterStage;
		public static Stage loaderStage;
		public static MainController mainController;
		public static EncrypterController encrypterController;
		public static DecrypterController decrypterController;
		public FXMLLoader loader;
		public boolean isLoading = false;
		
		public Image icon;
			
		public static BigInteger[] privateKey, publicKey;
		public static String appdataDir;
	
	public static void main(String[] args) {
		
		launch();
		
	}


	public void start(Stage primaryStage) throws InterruptedException, IOException, ParseException{
		
		appdataDir = System.getenv("APPDATA").replace("\\", "/");
		
		Thread t = new Thread(() -> {
			try {
				
				System.out.println("Hello");
				gatherKeys();
				
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				Platform.runLater(new Runnable() 
		        {
		            @Override
		            public void run() 
		            {
		            	try {
							loadGUI(primaryStage);
						} catch (IOException e) {
							e.printStackTrace();
						}
		            }
		        });
				
			
			} catch (IOException | ParseException e1) {

				e1.printStackTrace();
			}
		});
		
		icon = new Image(Main.class.getResourceAsStream("logo.png"));
		
		loader = new FXMLLoader((getClass().getResource("loading.fxml")));
		
		primaryStage.getIcons().add(icon);
		primaryStage.setScene(new Scene((Pane) loader.load()));
		primaryStage.show();
		primaryStage.setTitle("Loading...");
		t.start();
		
		
		
					
				
					
	
		
	}	
	
	protected void loadGUI(Stage primaryStage) throws IOException {
		
		
		loader = new FXMLLoader((getClass().getResource("encrypter.fxml")));
		encrypterStage = new Stage();
		encrypterStage.setScene(new Scene((Pane) loader.load()));
		encrypterController = loader.getController();
		
		encrypterStage.getIcons().add(icon);
		
		loader = new FXMLLoader((getClass().getResource("decrypter.fxml")));
		decrypterStage = new Stage();
		decrypterStage.setScene(new Scene((Pane) loader.load()));
		decrypterController = loader.getController();
		
		decrypterStage.getIcons().add(icon);
		
		loader = new FXMLLoader((getClass().getResource("main.fxml")));
		primaryStage.setScene(new Scene((Pane) loader.load()));
		mainController = loader.getController();
		
		
		
		primaryStage.setTitle("RSA Encrypter");
		
		primaryStage.setResizable(false);
		
		primaryStage.setOnCloseRequest(e -> {
			
			Platform.exit();
			
		});
		
	}


	public void gatherKeys() throws IOException, ParseException {
		
		File baseDir = new File(appdataDir + "/RSAEncrypter/");
		baseDir.mkdir();
		
		try {
			
			JSONParser jsonParser = new JSONParser();
			FileReader reader = new FileReader(appdataDir + "/RSAEncrypter/keys.json");
	        JSONObject keysJSON = (JSONObject) jsonParser.parse(reader);
	        
	        JSONArray publicKeyJSON = (JSONArray) keysJSON.get("public");
	        JSONArray privateKeyJSON = (JSONArray) keysJSON.get("private");
	        
	        publicKey = new BigInteger[]{
	        		
	        		Base62.decodeInt((String) publicKeyJSON.get(0)),
	        		Base62.decodeInt((String) publicKeyJSON.get(1)),
	        
	        };
	        
	        privateKey = new BigInteger[]{
	        		
	        		Base62.decodeInt((String) privateKeyJSON.get(0)),
	        		Base62.decodeInt((String) privateKeyJSON.get(1)),
	        
	        };
	        
	        
	        
	        isLoading = false;
	        
	        
        

		} catch (FileNotFoundException e) {
			
			JSONObject keysJSON = new JSONObject();
			
			BigInteger[][] keyArray = RSA.generateKeys();
		
			JSONArray publicKeyJSON = new JSONArray();
			publicKeyJSON.add(Base62.encodeInt(keyArray[0][0]));
			publicKeyJSON.add(Base62.encodeInt(keyArray[0][1]));
			
			JSONArray privateKeyJSON = new JSONArray();
			privateKeyJSON.add(Base62.encodeInt(keyArray[1][0]));
			privateKeyJSON.add(Base62.encodeInt(keyArray[1][1]));
			
			keysJSON.put("public", publicKeyJSON);
			keysJSON.put("private", privateKeyJSON);
	        
	        try (FileWriter file = new FileWriter(appdataDir + "/RSAEncrypter/keys.json")) {
	            file.write(keysJSON.toJSONString());
	        } catch (IOException e1) {
	            e.printStackTrace();
	        }
	        
	        publicKey = new BigInteger[]{
	        		
	        		keyArray[0][0],
	        		keyArray[0][1],
	        
	        };
	        
	        privateKey = new BigInteger[]{
	        		
	        		keyArray[1][0],
	        		keyArray[1][1],
	        
	        };
	        
	        isLoading = false;
	        
	        
	        
		}
		
	}
			
}
	