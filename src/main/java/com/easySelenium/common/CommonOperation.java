package com.easySelenium.common;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;


/**
 * TODO Put here a description of what this class does.
 *
 * @author ssamaji.
 *         Created May 25, 2018.
 */
public class CommonOperation {
	/**
	 * Method to create new screenshotFolder
	 * TODO Put here a description of what this method does.
	 *
	 * @return
	 */
	public static File screenshotFolder(){
		String homeDirectoryPath=System.getProperty("user.dir");
		File images=new File(homeDirectoryPath, "UIIMAGES");
		images.mkdirs();
		return images;
	}
	
	/**
	 * Method to take screenshot using Robot class
	 * TODO Put here a description of what this method does.
	 *
	 * @throws AWTException
	 * @throws IOException
	 */
	public static File takeScreenshotRobot() throws AWTException, IOException{
		SimpleDateFormat s= new SimpleDateFormat("DD-MM-YYYY_hh_mm_ss");
		String res= s.format(new Date());
		File images=screenshotFolder();
		String imageName = String.format("Image_%d.png", res);
		// get current desktop size
		Dimension d= Toolkit.getDefaultToolkit().getScreenSize();
		// desktop area
		Rectangle screeenRect= new Rectangle(d);
		//take that area screen shot
		Robot r= new Robot();
		File imageFile = new File(images, imageName);
		BufferedImage img = r.createScreenCapture(screeenRect);
		ImageIO.write(img, "jpg", imageFile);
		
		return imageFile;
	}
	
//	public static File takeScreenshot(){
//		//File screenshot=		
//	}

}
