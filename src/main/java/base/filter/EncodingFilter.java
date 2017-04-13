package base.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author shsun
 */
public class EncodingFilter implements Filter {

	private String targetEncoding = "UTF-8";

	public FilterConfig filterConfig;

	/**
	 * 
	 * @param arg0
	 * @throws ServletException
	 */
	public void init(FilterConfig arg0) throws ServletException {
		this.filterConfig = arg0;
		this.targetEncoding = arg0.getInitParameter("encoding");
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param filterChain
	 * @throws IOException
	 * @throws ServletException
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		req.setCharacterEncoding(targetEncoding);
		resp.setCharacterEncoding(targetEncoding);
		filterChain.doFilter(req, resp);
	}

	public void destroy() {
		this.filterConfig = null;
	}

	/**
	 * @param filterConfig
	 */
	public void setFilterConfig(final FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
	}
}
