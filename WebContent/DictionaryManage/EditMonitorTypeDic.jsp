<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>监测方法的编辑页面</title>

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
						<input id="DicValue" class="easyui-textbox" required="true"
							label="字典名称:" labelWidth="150px" style="width: 100%">
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
		
	var strDicValue, strParentDic, nParentID, nAutoID, nIsUsed;
	
	$(function() {
		
		var action = getQueryString("action");

		if (action == "add") {
			
			strParentDic = getQueryString("DicValue");
			nParentID = getQueryString("autoid");
			nIsUsed="1";
			
			$('#btnOK').click(function() {
				
				GetHtmlInfo();
				
				var addRes = JSON.parse(AddDictionaryFun());
				
				if (addRes && addRes.result == "true") {

					if (window.parent != null
							&& window.parent != undefined) {
						window.parent.CloseMonitorDictWin("add",
								addRes.errorMsg);
					}

				} else {
					$.messager.alert('提示', '新增失败');
				}
				
			});
			
		}else if(action=="update"){
			
			strDicValue=getQueryString("DicValue");
			strParentDic=getQueryString("ParentDic");
			nParentID=getQueryString("ParentID");
			nAutoID = getQueryString("autoid");
			nIsUsed=getQueryString("isUsed");
			
			$('#DicValue').textbox('setValue',strDicValue);
			
			$('#btnOK').click(function() {
				
				GetHtmlInfo();
				
				var updateRes = JSON.parse(UpdateDictionaryFun());

				if (updateRes && updateRes.result == "true") {

					if (window.parent != null
							&& window.parent != undefined) {
						window.parent.CloseMonitorDictWin("update",
								updateRes.errorMsg);
					}

				} else {
					$.messager.alert('提示', '修改失败');
				}
				
			});
		}
		
	});
	
	//取消按钮
	function BtnCancle() {
		if (window.parent != null && window.parent != undefined) {
			window.parent.CloseMonitorDictWin(null, null);
		}
	}

	//页面数据
	function GetHtmlInfo() {
		strDicValue = $('#DicValue').textbox('getValue');
	}
	
	//新增字典的请求方法
	function AddDictionaryFun() {

		var res = null;

		$.ajax({
			type : "GET",
			url : "../DictionaryRequest",
			/* data: "page=" + page + "&rows=" + rows + "&keyword=" + $('#hdKeyword').val(), */
			async : false,//设置同步
			data : {
				action : "CreateDicMonitorInfo",
				DicValue : strDicValue,
				isUsed : nIsUsed,
				ParentDic : strParentDic,
				ParentID : nParentID,
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

	//修改字典的请求方法
	function UpdateDictionaryFun() {
		var res = null;

		$.ajax({
			type : "GET",
			url : "../DictionaryRequest",
			/* data: "page=" + page + "&rows=" + rows + "&keyword=" + $('#hdKeyword').val(), */
			async : false,//设置同步
			data : {
				action : "UpdateDicMonitorInfo",
				DicID : nAutoID,
				DicValue : strDicValue,
				isUsed : nIsUsed,
				ParentDic : strParentDic,
				ParentID : nParentID,
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