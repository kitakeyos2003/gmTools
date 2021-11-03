package gm.services;

import gm.config.RMIConfig;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA. User: jiaodongjie Date: 11-2-13 Time: 下午2:12
 */
public class GameServiceListManager {

    private static Logger log = Logger.getLogger(GameServiceListManager.class);
    /**
     * 单例
     */
    private static GameServiceListManager instance;

    /**
     * 游戏服务列表
     */
    private Map<Integer, GameService> gameServiceList;

    /**
     * 游戏地图列表 只加载一个服务器的即可
     */
    private static Map<Integer, String> gameMapList = null;

    /**
     * 任务计时器
     */
    private Timer timer;

    /**
     * 私有构造
     */
    private GameServiceListManager() {
        gameServiceList = new HashMap<Integer, GameService>();
        loadGameServiceList();
        timer = new Timer();
        GameServerRefreshTask serverRefreshTask = new GameServerRefreshTask();
        timer.schedule(serverRefreshTask, 30000, 30000);
    }

    /**
     * 获取单例
     *
     * @return
     */
    public static GameServiceListManager getInstance() {
        if (instance == null) {
            instance = new GameServiceListManager();
        }

        return instance;
    }

    private void loadGameServiceList() {
        System.out.println("load list server");
        int serverNumber = Integer.parseInt(RMIConfig.getInstance().getValue("game_server_number"));
        GameService gameService;
        for (int i = 1; i <= serverNumber; i++) {
            int serverID = Integer.parseInt(RMIConfig.getInstance().getValue("game_server_" + i + "_id"));
            String serverName = RMIConfig.getInstance().getValue("game_server_" + i + "_name");
            String rmiURL = RMIConfig.getInstance().getValue("game_server_" + i + "_rmi_url");
            gameService = new GameService(serverID, serverName, rmiURL);
            gameServiceList.put(serverID, gameService);
            if (gameService.isRunning() && (gameMapList == null || gameMapList.size() == 0)) {
                gameMapList = gameService.loadGameMapList();
            }
        }
    }

    public Map<Integer, GameService> getGameServiceList() {
        return gameServiceList;
    }

    public Map<Integer, String> getGameMapList() {
        return gameMapList;
    }

    public String getGameServiceName(int serverID) {
        GameService gameService = GameServiceListManager.getInstance().getGameServiceList().get(serverID);
        return gameService.getServerName();
    }

    /**
     * 扫描列表中的游戏服务器运行状态（正在运行的扫描是否正常，不在运行的重新获取RMI对象进行尝试）
     */
    private void scan() {
        try {
            for (GameService gameService : gameServiceList.values()) {
                if (gameService.isRunning()) {
                    gameService.checkServerStatus();
                } else {
                    gameService.lookupGameService();
                }
            }
        } catch (Exception e) {
            log.error("扫描列表中的游戏服务器运行状态  error : ", e);
        }
    }

    /**
     * 游戏服务器刷新任务
     */
    class GameServerRefreshTask extends TimerTask {

        public void run() {
            scan();
        }
    }

    public static void main(String[] args) {
        getInstance().loadGameServiceList();
        for (final GameService gameService : getInstance().getGameServiceList().values()) {
            try {
                System.out.println("game server [" + gameService.getServerName() + "]isRunning=" + gameService.isRunning());
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
