<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="js/jquery.json-2.4.min.js"></script>
<script type="text/javascript">
	$(function() {
		$("#captcha").click(refreshcaptcha);
		
		$("#userjson").click(userjson);

		function refreshcaptcha() {
			$("#captcha").attr("src", "captcha.jpg?" + Math.random());
		}

		function userjson() {
			var cfg = {
				url : 'account/userjson',
				type : 'POST',
				data : JSON.stringify({
					username : 'terrynow',
					password : 'password',
					mobile : '13818881888'
				}),
				dataType : 'json',
				contentType : 'application/json;charset=UTF-8',
				success : function(result) {
					alert(result.success);
				}
			};
			$.ajax(cfg);
		}
	});
</script>
</head>
<body>
	输入验证码：
	<img alt="captcha" src="captcha.jpg" id="captcha">(点击刷新验证码)
	
	<input type="button" value="测试JSON" id="userjson" />
</body>
</html>