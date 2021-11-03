package gm.servlet;

import gm.db.DBManager;
import gm.services.GameServiceListManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: jiaodj
 * Date: 11-3-24
 * Time: 上午11:05
 * 用于关闭server时关闭数据库的连接
 */
public class DBServlet extends HttpServlet{
    private static Logger log = Logger.getLogger(DBServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    public void destroy() {
        log.info("DBServlet destroy ..");
        DBManager.getInstance().destroy();
        super.destroy();
    }

    @Override
    public void init() throws ServletException {
        log.info("DBServlet init ...");
        super.init();
        //System.setProperty("java.rmi.server.hostname","127.0.0.1");
        DBManager.getInstance();
    }
}
