package com.easySelenium.generic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.lf5.LogLevel;
import org.testng.Reporter;

import com.easySelenium.common.CommonConstant;

/**
 * TODO Put here a description of what this class does.
 *
 * @author ssamaji.
 *         Created Jun 21, 2018.
 */
public class FileOperations {
	private static List<String> m_fileList = new ArrayList<>();
	private static String m_sourceFolder;

	public static boolean createDirectory(String filePath) {
		return new File(filePath).mkdirs();
	}

	public static Properties getProperties(String fileName) throws IOException {
		return getPropertyFileDetails(fileName);
	}
	public static String getPropertyValue(String fileName, String property) throws IOException {
		return getProperties(fileName).getProperty(property);
	}
	
	/*
	 * Method will return property file details.
	 */
	private static Properties getPropertyFileDetails(String fileName) throws IOException {
		InputStream inputStream = null;
		final Properties properties = new Properties();
		try {

			inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);

			if (inputStream != null) {

				properties.load(inputStream);
				return properties;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return properties;
		} finally {
			if (inputStream != null)
				inputStream.close();
		}
		return properties;
	}

	public static void removeFilesOlderThanNDays(String fileName, int daysBack) {
		File file = new File(fileName);
		if (file.exists()) {
			final File[] listFiles = file.listFiles();
			final long purgeTime = System.currentTimeMillis() - (daysBack * 24L * 60L * 60L * 1000L);
			for (File listFile : listFiles) {
				if (listFile.lastModified() < purgeTime) {
					deleteFile(listFile);
				}
			}
		}
	}	
	public static void deleteFile(File file) {
		if (file.isDirectory()) {
			// directory is empty, then delete it
			if (file.list().length == 0) {
				file.delete();
				//Log.writeMessage(FileOperations.class.getName(), "Directory is deleted : " + file.getAbsolutePath());
			} else {
				// list all the directory contents
				String files[] = file.list();
				for (String temp : files) {
					// construct the file structure
					File fileDelete = new File(file, temp);
					// recursive delete
					deleteFile(fileDelete);
				}
				// check the directory again, if empty then delete it
				if (file.list().length == 0) {
					file.delete();
				//	Log.writeMessage(FileOperations.class.getName(),
				//			"Directory is deleted : " + file.getAbsolutePath());
				}
			}
		} else {
			// if file, then delete it
			file.delete();
			//Log.writeMessage(FileOperations.class.getName(), "File is deleted : " + file.getAbsolutePath());
		}
	}

	public static boolean deleteFile(String path, String format) {
		return deleteFile(path, format, CommonConstant.StartWith);
	}

	/**
	 * Method to delete the file with specified format and specific folder
	 * 
	 * @param path
	 * @param format
	 * @param filterType
	 * @return -> True/False
	 */
	public static boolean deleteFile(String path, String format, String filterType) {
		List<Boolean> results = new ArrayList<Boolean>();
		try {
			File mainFolder = new File(path);
			if (mainFolder.exists()) {
				File[] innerFiles = mainFolder.listFiles();
				for (File innerFile : innerFiles) {
					String fileName = innerFile.getName();
					boolean isDelete = false;
					switch (filterType) {
					case CommonConstant.Contains:
						isDelete = fileName.contains(format);
						break;
					case CommonConstant.EndWith:
						isDelete = fileName.endsWith(format);
						break;
					case CommonConstant.StartWith:
						isDelete = fileName.startsWith(format);
						break;
					case CommonConstant.Equals:
						isDelete = fileName.equals(format);
						break;
					}
					if (isDelete) {
						if (innerFile.isDirectory()) {
							deleteFile(innerFile);
							results.add(true);
						} else {
							results.add(innerFile.delete());
						}
					}
				}
			} else {
				//Log.writeMessage(LogLevel.INFO, CLASS_NAME, String.format("%s, folder does not exists.", path));
			}
			return results.contains(false) ? false : true;
		} catch (Exception e) {
			//Log.writeMessage(LogLevel.ERROR, CLASS_NAME, e.toString());
		}
		return false;
	}
	
	public static File getScreenshotFolder(){
		try {
			return new File(CommonConstant.SNAP_PATH);
		} catch (NullPointerException e) {
			Reporter.log("Error while building screenshot path ");
			return null;
		}
	}
	
	public static void copyFile(File sourceFile , File destinationFile ){
		try {
			FileUtils.copyFile(sourceFile,destinationFile);
		} catch (IOException exception) {
			// TODO Auto-generated catch-block stub.
			exception.printStackTrace();
		}
	}
	
	public static void zipFolder(String source_folder) {
		m_sourceFolder = source_folder.replace("//", "/");
		String m_zipFolder = String.format("%s.zip",m_sourceFolder);
		generateFileList(new File(source_folder));
        zipIt(m_zipFolder);
    }

    public static void zipIt(String zipFile) {
        byte[] buffer = new byte[1024];
		try (FileOutputStream fileOutputStream = new FileOutputStream(zipFile)) {
			try (ZipOutputStream zipOutputStream = new ZipOutputStream (fileOutputStream)){
				for (String file : m_fileList) {
					ZipEntry zipEntry = new ZipEntry(file);
					zipOutputStream.putNextEntry(zipEntry);
					try (FileInputStream fileInputStream = new FileInputStream(
							Paths.get(m_sourceFolder, file).toString())){
						int fileLength;
						while ((fileLength = fileInputStream.read(buffer)) > 0) {
							zipOutputStream.write(buffer, 0, fileLength);
						}
					}
				}
			}
		} catch (Exception e) {
			Log.error(e.toString());
		}
	}

    public static void generateFileList(File node ) {
    	// add file only
        if (node.isFile()) {
        	m_fileList.add(node.getPath());
        } else {
			generateFileList(node , node);
		}
	}

    private static void generateFileList(File node , File root) {
		//add file only
		if (node.isFile()){
			m_fileList.add(root.toPath().relativize(node.toPath()).toString());
		}
		if (node.isDirectory()){
			String[] subNote = node.list();
			for (String fileName : subNote) {
				generateFileList(new File(node , fileName), root);
			}
		}
	}
}
