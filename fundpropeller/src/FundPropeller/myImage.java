package FundPropeller;
import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

public class myImage {
	public static String imageToByteString(String imagePath) throws IOException {
		byte[] imageData = readImageFile(imagePath);
		String byteString = "";
        for (byte element : imageData) {
        	byteString+=element+" ";
        }
        return byteString;
	}

	private static byte[] readImageFile(String imagePath) throws IOException {
  	  Path path = Paths.get(imagePath);
        return Files.readAllBytes(path);
  }

	public static Image byteStringToImage(String byteString) throws IOException {
		String temp[] = byteString.split(" ");
        byte[]byteArray = new byte[temp.length];
        for(int i=0;i<temp.length;i++) {
        	int tem = Integer.parseInt(temp[i]);
        	byteArray[i]=(byte)tem;
        }

        Image image = byteToImage(byteArray);
        return image;
	}
	 private static Image byteToImage(byte[] imageData) throws IOException {
	        ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
	        return ImageIO.read(bis);
	 }
}
