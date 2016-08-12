package org.sammy.example;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.apache.commons.io.IOUtils;
import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.encoders.Hex;

public class ImageApply {
	//get image format
	public static void main(String[] args) {
		String filePath="";
		
		try{
			InputStream is = new BufferedInputStream(new FileInputStream(new File(filePath)));
			byte[] imageBytes = IOUtils.toByteArray(is);
			String base64EncodedImageContent = base64EncodeImage(imageBytes);
			String formatName = getImageFormat(base64EncodedImageContent, "base64");
			System.out.println("formatName=" + formatName);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private static String getImageFormat(String encodedImageContent, String encodeType){
		String formatName = null;
		byte[] imageContent = null;
		try{
			if(encodeType.equalsIgnoreCase("base64")){
				imageContent = base64DecodeImage(encodedImageContent);
			}else if(encodeType.equalsIgnoreCase("hex")){
				imageContent = hexDecodeImage(encodedImageContent);
			}else{
				throw new Exception("Wrong EncodeType");
			}
			ImageInputStream iis = ImageIO.createImageInputStream(new ByteArrayInputStream(imageContent));
			Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
			if (iter.hasNext()) {
				ImageReader reader = iter.next();
				formatName = reader.getFormatName();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return formatName;
	}
	
	private static String base64EncodeImage(byte[] imageContent){
		return new String(Base64.encode(imageContent));
	}
	private static byte[] base64DecodeImage(String imageContent){
		return Base64.decode(imageContent);
	}
	
	private static String hexEncodeImage(byte[] imageContent){
		return new String(Hex.encode(imageContent));
	}
	
	private static byte[] hexDecodeImage(String hexEncodedImageContent){
		return Hex.decode(hexEncodedImageContent);
	}

}
