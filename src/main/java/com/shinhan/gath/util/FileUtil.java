package com.shinhan.gath.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class FileUtil {
	static String path = "./";
	
	public static void fileUpload(String fileName) {
		fileName = fileName.trim();
		String[] strArr = fileName.split("/");
		File orgFile = new File(fileName);
		File outFile = new File(path + strArr[strArr.length - 1]);
		
		try {
			Files.copy(orgFile.toPath(), outFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
			System.out.println("파일 업로드가 완료되었습니다.");
		} catch (IOException e) {
			System.out.println("파일 업로드에 실패했습니다.");
		}
	}
	
	public static void fileDownload(String fileName) {

		String[] strArr = fileName.split("/");
		File orgFile = new File(path + strArr[strArr.length - 1]);
		File outFile = new File("C:/Users/" + System.getProperty("user.name")+ "/Downloads/" + strArr[strArr.length - 1]);
		
		
		try {
			Files.copy(orgFile.toPath(), outFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
			System.out.println("파일 다운로드가 완료되었습니다.");
		} catch (IOException e) {
			System.out.println("파일 다운로드에 실패했습니다.");
		}
	}
}
