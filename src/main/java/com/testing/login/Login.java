package com.testing.login;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.testing.mysql.ConnectMysql;
import com.testing.mysql.UseMysql;

/**
 * Servlet implementation class Login
 */
//注解表示当前类在调用时的接口类名
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Login() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// 设置请求和返回的编码
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		// 获取接口请求中的参数
		// getparameter方法中的参数，就是键名
		String loginName = request.getParameter("user");
		String password = request.getParameter("pwd");
		// 匹配特殊字符的正则表达式格式
		String specregex = "[^a-zA-Z0-9_@\\-]";
		// 基于特殊字符创建模板
		Pattern specWord = Pattern.compile(specregex);
		// 基于模板创建匹配器来进行字符串的匹配
		Matcher userM = specWord.matcher(loginName);
		Matcher pwdM = specWord.matcher(password);
		System.out.println(loginName + password);
		String loginMsg = "{";
		// 引入库之后，实例化loginSample类对象，调用login方法验证用户名和密码
//		LoginSample ls=new LoginSample();
//		boolean result=ls.login(loginName, password);
		// 基于jdbc连接数据库进行查询，得到查询结果，来判断是否登录成功。
		if ((loginName != null && password != null) && (loginName.length() != 0 && password.length() != 0)) {
			if (loginName.length() > 2 && loginName.length() < 17 && password.length() > 2 && password.length() < 17) {
				if (!userM.find() && !pwdM.find()) {
					ConnectMysql conMysql = new ConnectMysql();
					UseMysql querySql = new UseMysql(conMysql.conn);
					boolean result = querySql.Login(loginName, password);
					// 基于登录结果完成返回信息的拼接，拼接成一个json格式

					if (result) {
						loginMsg += "\"msg\":\"恭喜您get方法登录成功！\"}";
					} else {
						loginMsg += "\"msg\":\"get方法登录失败！请检查用户名密码！\"}";
					} // 数据库验证的判断结束
				} // 判断是否含有特殊字符
				else {
					loginMsg += "\"msg\":\"输入非法！用户名密码不能包含特殊字符\"}";
				}
			} // 判断用户名密码长度
			else {
				loginMsg += "\"msg\":\"输入非法！用户名密码必须是3到16位\"}";
			}
		} // 判断用户名和密码是否为空
		else {
			loginMsg += "\"msg\":\"输入非法！用户名密码不能为空\"}";
		} // 为空判断结束

//		response.getWriter().append("恭喜您调用get方法成功，返回信息是").append(loginMsg);
		response.getWriter().append(loginMsg);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		String sessionId=request.getSession().getId();
		//设置session的有效周期（也就是前台登记的入住信息）
		request.getSession().setMaxInactiveInterval(300);
		System.out.println("本次访问的sessionId是"+sessionId);
		// 获取接口请求中的参数
		// getparameter方法中的参数，就是键名
		String loginName = request.getParameter("user");
		String password = request.getParameter("pwd");
		// 匹配特殊字符的正则表达式格式
		String specregex = "[^a-zA-Z0-9_@\\-]";
		// 基于特殊字符创建模板
		Pattern specWord = Pattern.compile(specregex);
		// 基于模板创建匹配器来进行字符串的匹配
		Matcher userM = specWord.matcher(loginName);
		Matcher pwdM = specWord.matcher(password);
		System.out.println(loginName + password);
		String loginMsg = "{";
		// 引入库之后，实例化loginSample类对象，调用login方法验证用户名和密码
//		LoginSample ls=new LoginSample();
//		boolean result=ls.login(loginName, password);
		// 基于jdbc连接数据库进行查询，得到查询结果，来判断是否登录成功。
		if ((loginName != null && password != null) && (loginName.length() != 0 && password.length() != 0)) {
			if (loginName.length() > 2 && loginName.length() < 17 && password.length() > 2 && password.length() < 17) {
				if (!userM.find() && !pwdM.find()) {
					//基于session中的信息判断是否已经有用户登录了，如果有用户登陆过了，那么session中就能够查到loginName的信息
					if(request.getSession().getAttribute("loginName")==null) {
					ConnectMysql conMysql = new ConnectMysql();
					UseMysql querySql = new UseMysql(conMysql.conn);
					boolean result = querySql.Login(loginName, password);
					// 基于登录结果完成返回信息的拼接，拼接成一个json格式

					if (result) {
						loginMsg += "\"msg\":\"恭喜您post方法登录成功！\"}";
						//完成登录之后，在服务器上记录一下session中的信息，将登录名存到session里
						request.getSession().setAttribute("loginName", loginName);
						//添加一个cookie，返回给客户端，记录下本次session的JsessionId,从而使用servlet的cookie机制，自动完成cookie校验
						Cookie ssID=new Cookie("JSESSIONID",sessionId);
						//设置方
						ssID.setMaxAge(300);
						//返回cookie给客户端
						response.addCookie(ssID);
						
					} else {
						loginMsg += "\"msg\":\"post方法登录失败！请检查用户名密码！\"}";
					} // 数据库验证的判断结束
					}
					else {
						//判断当前session中记录的对象是否是正在登录的用户
						if(request.getSession().getAttribute("loginName").equals(loginName)) {
							loginMsg += "\"msg\":\"该用户已经登录过了，不能再次登录\"}";
						}
						else {
							loginMsg += "\"msg\":\"已经有其它用户登录了，不能再次登录\"}";
						}
					}
				} // 判断是否含有特殊字符
				else {
					loginMsg += "\"msg\":\"输入非法！用户名密码不能包含特殊字符\"}";
				}
			} // 判断用户名密码长度
			else {
				loginMsg += "\"msg\":\"输入非法！用户名密码必须是3到16位\"}";
			}
		} // 判断用户名和密码是否为空
		else {
			loginMsg += "\"msg\":\"输入非法！用户名密码不能为空\"}";
		} // 为空判断结束

//		response.getWriter().append("恭喜您调用post方法成功，返回信息是").append(loginMsg);
		response.getWriter().append(loginMsg);
		System.out.println(loginMsg);
	}

}
