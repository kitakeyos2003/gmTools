package gm.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class GenMoneyLogExcel {
	private static Map<MoneyLogUserInfo,List<MoneyLogInfo>> maps = 
						new HashMap<MoneyLogUserInfo,List<MoneyLogInfo>>();
	
	public static void main(String[] args){
		System.out.println("start ....");
		log2Excel("G:\\tool\\moneylog\\server_1\\moneyChangeLogs","server1");
		log2Excel("G:\\tool\\moneylog\\server_2\\moneyChangeLogs","server2");
		log2Excel("G:\\tool\\moneylog\\server_3\\moneyChangeLogs","server3");
		System.out.println("end ....");
	}
	
	private static void log2Excel(String path,String servername){
		try{
			File logFileDir = new File(path);
			File[] fileList = logFileDir.listFiles();
			System.out.println("file size = " + fileList.length);

			for(File file : fileList){
				MyElementHandler myHandler = new MyElementHandler(file);
				myHandler.writeExcel(file.getPath(),servername);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}




	

