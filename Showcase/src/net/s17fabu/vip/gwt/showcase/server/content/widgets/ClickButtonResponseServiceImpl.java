package net.s17fabu.vip.gwt.showcase.server.content.widgets;

import net.s17fabu.vip.gwt.showcase.client.content.widgets.ClickButtonResponseService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * @author xinghuo.yao yaoxinghuo at 126 dot com
 * @create Apr 11, 2009 12:10:09 PM
 * @description 
 */
public class ClickButtonResponseServiceImpl extends RemoteServiceServlet implements ClickButtonResponseService{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3947057589320066393L;

	@Override
	public String clickButtonResponse() {
		String serverInfo = getServletContext().getServerInfo();
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");
		return "Hello!<br><br>I am running " + serverInfo
				+ ".<br><br>It looks like you are using:<br>" + userAgent;
	}
}
