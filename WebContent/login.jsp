<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>云平台管理系统-登录</title>
<link href="Resource/login/login.css" rel="stylesheet" type="text/css" />

<link rel="stylesheet" type="text/css"
	href="Resource/jquery-easyui-1.5.4.2/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="Resource/jquery-easyui-1.5.4.2/themes/icon.css">

<script src="Resource/jquery-easyui-1.5.4.2/jquery.min.js"></script>
<script src="Resource/jquery-easyui-1.5.4.2/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="Resource/jquery-easyui-1.5.4.2/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="Resource/jshelper/commonfun.js"></script>

<link rel = "Shortcut Icon" href="Resource/Images/favicon.ico"/> 


</head>
<body onkeydown="keyLogin()">

	<div class="second_body">
    	<form >
        	<div class="logo"></div>
            <div class="title-zh">云平台管理系统</div>
            <div class="title-en" style=""> &nbsp;&nbsp;  Cloud &nbsp;&nbsp;  Manage &nbsp;&nbsp;  System</div>
            
            <table border="0" style="width:300px;">
            	<tr>
                	<td style="white-space:nowrap; padding-bottom: 5px;width:55px;">用户名：</td>
                    <td colspan="2"><input type="text" id="userCode" class="login"  /></td>
                </tr>
                <tr>
                    <td class="lable" style="white-space:nowrap; letter-spacing: 0.5em; vertical-align: middle">密码：</td>
                    <td colspan="2"><input type="password" id="password" class="login" /></td>
                </tr>
                <!-- <tr>
                    <td></td>
                    <td colspan="2"><input type="checkbox" /><span>系统记住我</span></td>
                </tr> -->
                <tr>
                    <td colspan="3" style="text-align:center">
                        <input type="button" value="登录" class="login_button" id="login" />
                        <input type="button" value="重置" class="reset_botton" id="reset" />
                    </td>
                </tr>
            </table>
        </form>
    </div>
	
	<script>
		
		$(function(){
			
			$('#login').click(function(){
				
				var loginRes=JSON.parse(LoginFun());
				
				if(loginRes&&loginRes.result == "true"){
					
					//记录下cookie
					
					
					cookie.set('UserName', encodeURI($('#userCode').val()), 1);
					
					//跳转到页面
					
					window.location.href = "MapIndex.jsp";
					
				}else{
					
					$.messager.alert('提示', '用户登录验证失败');
					
				}
				
				
			})
			
			
			
			$('#reset').click(function(){
				
				$('#userCode').val('');
				
				$('#password').val('')
			})
			
			
		})
		
		//登录方法
		function LoginFun(){
			
			var UserName=$('#userCode').val(),UserPwd=$('#password').val();
			
			if(UserName==''||UserPwd==''){
				//$.messager.alert('提示', 'error');
				return;
			}
			
			var res = null;

			$.ajax({
				type : "GET",
				url : "UserCtrlRequest",
				/* data: "page=" + page + "&rows=" + rows + "&keyword=" + $('#hdKeyword').val(), */
				async : false,//设置同步
				data : {
					action : "UserLogin",
					UserName:UserName,
					UserPwd:UserPwd,
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					//alert(textStatus);
					//$.messager.progress('close');
					$.messager.alert('提示', 'error');
				},
				success : function(data) {
					res = data;
				}
			});

			return res;
			
			
		}
		
		function keyLogin(){
			
			if (event.keyCode==13)
				$('#login').click();
			
		}
		
	

	</script>

</body>
</html>