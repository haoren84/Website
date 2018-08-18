<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户编辑</title>

<link rel="stylesheet" type="text/css"
	href="../Resource/jquery-easyui-1.5.4.2/themes/gray/easyui.css">
<link rel="stylesheet" type="text/css"
	href="../Resource/jquery-easyui-1.5.4.2/themes/icon.css">

<script src="../Resource/jquery-easyui-1.5.4.2/jquery.min.js"></script>
<script src="../Resource/jquery-easyui-1.5.4.2/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="../Resource/jquery-easyui-1.5.4.2/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="../Resource/jshelper/commonfun.js"></script>

<style type="text/css">
html {
	height: 100%
}

body {
	height: 100%;
	margin: 0px;
	padding: 0px
}

</style>
</head>
<body>

	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'center'" style="padding: 10px;">
			<div id="dlg" style="width: 100%">
				<form id="fm" method="post" novalidate
					style="margin: 0; padding: 0;">
					<div style="margin-bottom: 10px">
						<input id="UserName" class="easyui-textbox" required="true"
							label="用户名称:" labelWidth="150px" style="width: 100%" validType="length[0,30]" missingMessage="长度不超过30">
					</div>
					<div style="margin-bottom: 10px" id="UserPwdDiv">
						<input id="UserPwd" class="easyui-passwordbox easyui-validatebox" prompt="Password" required="true"
							label="用户密码:" labelWidth="150px" style="width: 100%" validType="length[0,30]" missingMessage="长度不超过30">
					</div>
					<div style="margin-bottom: 10px" id="UserPwd2Div">
						<input id="UserPwd2" class="easyui-passwordbox easyui-validatebox" prompt="Password" required="true"
							label="用户密码确认:" labelWidth="150px" style="width: 100%" validType="equals['#UserPwd']" missingMessage="请确认你的密码">
					</div>
				</form>
			</div>
		</div>
		<div data-options="region:'south',border:false"
			style="text-align: right; padding: 5px; border-color: #dddddd #95B8E7 #95B8E7 #95B8E7; background: #F4F4F4;">
			<a href="javascript:void(0)" class="easyui-linkbutton c6" id="btnOK"
				iconCls="icon-ok" style="width: 90px">确认</a> <a
				href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-cancel" onclick="BtnCancle()" style="width: 90px">取消</a>
		</div>
	</div>
	
	<script>
	
	var UserName,UserPwd,UserPwd2,nAutoID;
	
	$(function() {
		
		var action = getQueryString("action");
		
		if(action=="add"){
			
			$('#UserPwd2Div').css("visibility", "hidden"); 
			
			$('#btnOK').click(function(){
				
				var actionRes=JSON.parse(AddUserFun());
				
				if(actionRes&&actionRes.result == "true"){
					
					if (window.parent != null
							&& window.parent != undefined) {
						window.parent.CloseUserWin("add",
								actionRes.errorMsg);
					}
					
				}else {
					$.messager.alert('提示', '新增失败');
				}
				
				
			});
			
		}else if(action=="detail"){
			
			$('#UserPwdDiv').css("visibility", "hidden"); 
			
			$('#UserPwd2Div').css("visibility", "hidden");
			
			UserName=getQueryString("UserName");
			
			$('#UserName').textbox('setValue',UserName);
			
			$('#UserName').textbox('readonly',true);
			
			$('#btnOK').click(function(){
				
				if (window.parent != null && window.parent != undefined) {
					window.parent.CloseUserWin(null, null);
				}
				
			});
			
		}else if(action=="update"){
			
			UserName=getQueryString("UserName");
			
			$('#UserName').textbox('setValue',UserName);
			
			$('#UserPwd2Div').css("visibility", "hidden");
			
			$('#UserName').textbox('readonly',true);
			
			$('#btnOK').click(function(){
				
				var actionRes=JSON.parse(UpdateUserFun());
				
				if(actionRes&&actionRes.result == "true"){
					
					if (window.parent != null
							&& window.parent != undefined) {
						window.parent.CloseUserWin("update",
								actionRes.errorMsg);
					}
					
				}else {
					$.messager.alert('提示', '修改失败');
				}
				
			});
			
		}
		
		
	});
	$.extend($.fn.validatebox.defaults.rules, {
	    equals: {
	        validator: function(value,param){
	            return value == $(param[0]).val();
	        },
	        message: '两次密码不相同.'
	    }
	});
	
	//取消按钮
	function BtnCancle() {
		if (window.parent != null && window.parent != undefined) {
			window.parent.CloseUserWin(null, null);
		}
	}
	
	//获取页面数据信息
	function GetHtmlInfo(){
		
		UserName=$('#UserName').textbox('getValue');
		UserPwd=$('#UserPwd').textbox('getValue');
		UserPwd2=$('#UserPwd2').textbox('getValue');
	}
	
	//新增用户的方法
	function AddUserFun(){
		
		GetHtmlInfo();
		
		var res = null;

		$.ajax({
			type : "GET",
			url : "../UserCtrlRequest",
			/* data: "page=" + page + "&rows=" + rows + "&keyword=" + $('#hdKeyword').val(), */
			async : false,//设置同步
			data : {
				action : "AddUser",
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
	
	//修改用户方法
	function UpdateUserFun(){
		GetHtmlInfo();
		
		var res = null;

		$.ajax({
			type : "GET",
			url : "../UserCtrlRequest",
			/* data: "page=" + page + "&rows=" + rows + "&keyword=" + $('#hdKeyword').val(), */
			async : false,//设置同步
			data : {
				action : "UpdateUserByUserName",
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
	
	</script>
	
</body>
</html>