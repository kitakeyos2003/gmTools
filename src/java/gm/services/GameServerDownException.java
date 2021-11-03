package gm.services;

/**
 * Created by IntelliJ IDEA.
 * User: jiaodongjie
 * Date: 11-2-13
 * Time: 下午2:39
 */
public class GameServerDownException extends Exception
{
    /**
     *
     */
    private static final long serialVersionUID = 100002;

    /**
     *
     */
    public GameServerDownException()
    {
        // TODO Auto-generated constructor stub
        super("服务器已关闭");
    }
}
