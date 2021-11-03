package gm.config;

import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: jiaodongjie
 * Date: 11-2-12
 * Time: 下午4:25
 */
public class RMIConfig {
    private static Logger log = Logger.getLogger(RMIConfig.class);
    private static final String FILE_PATH = "/config.properties";

    private static RMIConfig config;

    private static Map<String,String> configMap = new HashMap<String,String>();

    private RMIConfig(){}

    public static RMIConfig getInstance(){
        if(config == null){
            config = new RMIConfig();
            load();
        }
        return config;
    }

    public void reload(){
        load();
    }

    private static void load(){
        InputStream fis = null;
        try{
//            fis = new FileInputStream(FILE_PATH);
            fis = getInstance().getClass().getResourceAsStream(FILE_PATH);
            Properties properties = new Properties();
            properties.load(new InputStreamReader(fis,"UTF-8"));

            Enumeration<Object> enu = properties.keys();
            String key;
            String value;
            while (enu.hasMoreElements()){
                key = (String)enu.nextElement();
                value = properties.getProperty(key);
                log.debug("config key="+key+",value="+value);
                configMap.put(key,value);
            }
            properties.clear();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally
        {
            if (fis != null)
            {
                try
                {
                    fis.close();
                    fis = null;
                }
                catch (Exception ioe)
                {

                }
            }
        }
    }

    public String getValue(String key){
        return configMap.get(key);
    }

    /**
     * 要查看多个服务器的数据
     * 根据服务器ID 获取RMIURL
     * @param serverID
     * @return
     */
    public String getRMIUrl(int serverID){
        return getValue("game_server_"+serverID+"_rmi_url");
    }

    public String getServerName(int serverID){
    	/*String name = null;
        try {
			name = new String(getValue("game_server_"+serverID+"_name").getBytes("ISO8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		return getValue("game_server_"+serverID+"_name");
    }
    
    public String getDBUsername(){
    	return getValue("db_username");
    }
    
    public String getDBPwd(){
    	return getValue("db_password");
    }
    
    public String getDBURL(){
    	return getValue("db_url");
    }
}
