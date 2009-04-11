package net.s17fabu.vip.gwt.showcase.client.content.widgets;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * @author xinghuo.yao yaoxinghuo at 126 dot com
 * @create Apr 11, 2009 12:07:27 PM
 * @description 
 */
@RemoteServiceRelativePath("clickButtonResponse")
public interface ClickButtonResponseService extends RemoteService {
	String clickButtonResponse();
}
