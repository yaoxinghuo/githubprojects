<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>index.jsp</title>

<link type="text/css" rel="stylesheet" href="css/uploadify.css">

<script type="text/javascript" src="js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="js/jquery.json-2.4.min.js"></script>
<script type="text/javascript" src="js/jquery.uploadify.min.js"></script>
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
		
		$("#file_upload")
				.uploadify(
						{
							'swf'      : 'images/uploadify.swf',
							'uploader' : 'upload/fileUpload',
							'fileTypeDesc' : 'Image Files',
							'fileTypeExts' : '*.jpg;*.jpeg;*.png;*.gif;*.txt',
							'multi' : false,
							//'buttonImage' : 'img/liulan1.png',
							'buttonText' : '浏览...',
							//'queueID' : 'fileQueue',
							'fileSizeLimit' : 10485760,
							//这里可以上传SESSION VALUE这些来验证身份，flash中得不到session这个是个flash的bug
							'formData'      : {'someKey' : 'someValue', 'someOtherKey' : 1},
							onUploadError : function(file, errorCode, errorMsg, errorString) {
								alert('The file ' + file.name + ' could not be uploaded: ' + errorString);
							},
							'onUploadSuccess' : function(file, data, response) {
								var result = $.evalJSON(data);
								if(result.result)
									alert("成功: "+file.name+"，信息:"+result.message);
								else
									alert("失败: "+file.name+"，信息:"+result.message);
					            
					        }

						});
	});
</script>
</head>
<body>
	输入验证码：
	<img alt="captcha" src="captcha.jpg" id="captcha">(点击刷新验证码)
	
	<input type="button" value="测试JSON" id="userjson" />
	
	<form>
		<div id="queue"></div>
		<input id="file_upload" name="file_upload" type="file" multiple="true">
	</form>
</body>
</html>