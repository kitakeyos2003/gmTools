package gm.filter;

import javax.servlet.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by IntelliJ IDEA.
 * User: jiaodongjie
 * Date: 11-2-12
 * Time: 上午10:35
 */
public class CharactorEncodingFilter implements Filter {
    private static final String DEFAULT_ENCODING = "UTF-8";
    private String encoding;
    public void init(FilterConfig filterConfig) throws ServletException {
        encoding = filterConfig.getInitParameter("encoding");
        if(encoding == null || encoding.trim().equals("")){
            encoding = DEFAULT_ENCODING;
        }
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        try{
            servletRequest.setCharacterEncoding(encoding);
        }catch (UnsupportedEncodingException e){
            servletRequest.setCharacterEncoding(DEFAULT_ENCODING);
        }
    }

    public void destroy() {

    }
}
