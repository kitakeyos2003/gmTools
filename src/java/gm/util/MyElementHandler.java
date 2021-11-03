package gm.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.ElementHandler;
import org.dom4j.ElementPath;
import org.dom4j.io.SAXReader;

/**
 * 这个类处理大的xml文件
 * @author jiaodongjie
 *
 */
public class MyElementHandler implements ElementHandler{

	MoneyLogInfo info = null;
	
	List<MoneyLogInfo> infolist = new ArrayList<MoneyLogInfo>();
	
	public MyElementHandler(File file){
		try {
			InputStream is = new FileInputStream(file);
			
			SAXReader reader = new SAXReader();
			reader.addHandler("/root/moneyChangeLog", this);//设置要处理的节点
//			reader.setDefaultHandler(this);
			reader.read(is);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void writeExcel(String filename,String servername){
		try{
		System.out.println("writeExcel start ...");
		filename = filename+"-"+servername+".xls";
		WritableWorkbook wwb = Workbook.createWorkbook(new File(filename));
        WritableSheet ws = wwb.createSheet("玩家金币变化", 0);
        String[] titles = {"时间","玩家昵称","金币变化","原因"};
        
        for(int i=0; i<infolist.size(); i++){
        	MoneyLogInfo info = infolist.get(i);
        	for(int j=0; j<4; j++){//列
    			String str = "";
    			if(i==0){
    				 str = titles[j];
                     Label label = new Label(j, i, str);
                     ws.addCell(label);
    			}else{
    				if(j == 2){
    					jxl.write.Number labelN = new jxl.write.Number(j, i, info.getChangeMoney());
                        ws.addCell(labelN);
        			}else{
            			switch(j){
            				case 0: str = info.getTime(); break;
            				case 1: str = info.getNickname();break;
            				case 3: str = info.getCause();break;
            			}
            			Label label = new Label(j, i, str);
                        ws.addCell(label);
        			}
    			}

    		}
        }
        wwb.write();
        wwb.close();
		}catch(IOException e){
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 读取完设置好的节点后，处理
	 */
	@Override
	public void onEnd(ElementPath ep) {
		Element sub = ep.getCurrent();
		
		String date = sub.elementTextTrim("date");
		int accountid = Integer.parseInt(sub.elementTextTrim("accountID"));
		int userid = Integer.parseInt(sub.elementTextTrim("userID"));
		int gameid = Integer.parseInt(sub.elementTextTrim("game_id"));
		int serverid = Integer.parseInt(sub.elementTextTrim("server_id"));
		String nickname = sub.elementTextTrim("nickname");
		String cause = sub.elementTextTrim("cause");
		int changeMoney = Integer.parseInt(sub.elementTextTrim("number"));

		info = new MoneyLogInfo(null,nickname,cause,changeMoney,date);
		
		sub.detach();//把当前节点从内存删除
		
		infolist.add(info);
	
	}

	@Override
	public void onStart(ElementPath arg0) {
		// TODO Auto-generated method stub
		
	}

}
