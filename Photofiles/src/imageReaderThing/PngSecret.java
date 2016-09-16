package imageReaderThing;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
public class PngSecret {
boolean SecretData;//If it hassecret DAata
String fileName; //Filename
long SecretPhraseLocation;
	String SecretMessage;
public void ScanImageFile(){

	SecretData  = false;

	long FileSize = 0;
	long CurrentPointer = 0;
	File InputFile = new File(fileName);

	if(! InputFile.exists()){
		System.out.println("You lied your file does not exist");
		return;
	}
	FileSize = InputFile.length();
	try {
		
		FileInputStream InStream = new FileInputStream(InputFile);
		//DEfine Chunklength AND Blocklength to find data area thung
		byte[] BlockLength = new byte[4];
		byte[] ChunkType = new byte[4];
		
		InStream.skip(8); // skip header (8 bytes)
		CurrentPointer +=8;
		while(CurrentPointer < FileSize){
			InStream.read(BlockLength,0,4);
			InStream.read(ChunkType,0,4);
			int blockLen = ByteBuffer.wrap(BlockLength).getInt();
			String Chunkstr = new String(ChunkType,"UTF-8");
			System.out.println("Chunk =======" + Chunkstr+"   The length ====== "+blockLen);

			if(Chunkstr.compareTo("dATA") == 0){
				SecretPhraseLocation = CurrentPointer;
				SecretData = true;				
				System.out.println("There is a hidden thing in the File");
				byte[] MessageCode = new byte[blockLen];
				InStream.read(MessageCode, 0, blockLen);
				SecretMessage = new String(MessageCode, "UTF-8");
				System.out.println("THE SECRET MESSAGE Is= "+SecretMessage);
				CurrentPointer = FileSize;
			}
			InStream.skip(blockLen+4);
			CurrentPointer +=12+blockLen;
		}
		
		
	} catch (IOException e) {} 
}

public void writeFile(String SecretMessage){
try {
	File OutputFile= new File(fileName);
	FileOutputStream fos = new FileOutputStream(OutputFile,true); // true= append to file
	

    
	String DataLine = "dATA"+SecretMessage+"dATA";
	byte[] bytesArray = DataLine.getBytes();
	FileChannel Fch = fos.getChannel();
	
	long CurrentPointer = OutputFile.length();
	if (SecretData)
		CurrentPointer = SecretPhraseLocation;
	
	ByteBuffer BlockLength = ByteBuffer.allocate(4).putInt(SecretMessage.length());
	Fch.position(CurrentPointer);
	ByteBuffer buf = ByteBuffer.allocate(DataLine.length());
	
	BlockLength.flip();
	Fch.write(BlockLength);
	
	buf.flip();
	Fch.write(buf);

	ByteBuffer data = ByteBuffer.allocate(bytesArray.length);
	data.put(bytesArray);
	data.flip();
	while(data.hasRemaining()){
		Fch.write(data);
	}
	
	Fch.close();
	fos.close();
	System.out.println("File Written Succesfully --- Hopefully");
	
	
	
	
} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace(System.err);
}  


}

public PngSecret(String FilePath){
	fileName = FilePath;
	SecretData  = false;
	ScanImageFile();
	
	
}


}
