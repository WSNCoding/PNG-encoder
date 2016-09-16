package imageReaderThing;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
public class Gui extends Frame implements ActionListener{
	private Label Codelbl;
	private TextField MessageIn;
	private Button Close;
	private Button Submit;
	private TextField Console;
	private String FileGiven;
	private Label Select;
	private TextField FileChosen; 
	private Button ChooseFile;
	File selectedFile;
	PngSecret SecretThing;
	
public String Return(String a){
	System.out.println("Message Typed is" + a);
	return a;
}
public String Explorer(){
	JFileChooser fileChooser = new JFileChooser();
	FileNameExtensionFilter filter = new FileNameExtensionFilter("Png File","png");
	fileChooser.setFileFilter(filter);
	fileChooser.setCurrentDirectory(new File(System.getProperty("user.home") + System.getProperty("file.separator")+ "Pictures"));
	int result = fileChooser.showOpenDialog(null);
	if (result == JFileChooser.APPROVE_OPTION) {
	    selectedFile = fileChooser.getSelectedFile();
	    System.out.println("Selected file: " + selectedFile.getAbsolutePath());
	}
	System.out.println("POP KEYS");
	return selectedFile.getAbsolutePath();	
}
	
	public Gui() {
		
		
		FileGiven = Explorer();
		setLayout(new FlowLayout());
		
		// File Label:
		Select = new Label("File Chosen");
		add(Select);
		
		//Add File Chosen display thing
		FileChosen = new TextField(FileGiven,50); 
		FileChosen.setEditable(false);
		add(FileChosen);
		
		//Add change directory btn
		ChooseFile = new Button("...");
		ChooseFile.addActionListener(this);
		add(ChooseFile);
		
		//message enter Label  
		Codelbl = new Label("Enter Message :: ");
		add(Codelbl);
		  
		 //Enter Message Box
		 MessageIn = new TextField(10); 
		 add(MessageIn);
		
		 
		 //Submit
		 Submit = new Button("Submit Message");
		 add(Submit);
		 Submit.addActionListener(this);
		
		 
		 //Console Label
		 Console = new TextField("Encode Data in To Image",75);
		 Console.setEditable(false);
		 add(Console);
		 
		 //Close Button
		 Close = new Button("Close");
		 add(Close);
		 Close.addActionListener(this);
		 
		 setVisible(true);
		 setSize(612,256);
		 setTitle("PNGN8R");
		System.out.println("YOYOYYO");
	}

	@Override
	public void actionPerformed(ActionEvent e){
		// TODO Get Drunk on fanta
		if(e.getSource() == Close){
			System.out.println("PKMOLN");
			dispose();
			System.exit(0);
		}
		else if(e.getSource() == ChooseFile){
			FileGiven = Explorer();
			FileChosen.setText(FileGiven);
			
		}
		else if(e.getSource() == Submit && MessageIn.getText() != ""){
			Return(MessageIn.getText());
			PngSecret SecretThing = new PngSecret(FileGiven);
			if(SecretThing.SecretData == false){	
				SecretThing.writeFile(MessageIn.getText());
				Console.setText("File was Written to.Editing with an editor will delete the message");
			}
			else{
			Console.setText("Secret Message Found: " + SecretThing.SecretMessage);
			}
		}
				
	}
}
