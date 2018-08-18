<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增测点数</title>

<link rel="stylesheet" type="text/css"
	href="../Resource/jquery-easyui-1.5.4.2/themes/metro/easyui.css">
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
	<!-- <table class="form_table">
		<tr>
			<th style="color: red;">监测项目：</th>
			<td><input class="easyui-textbox" name="monitorType.name"
				id="monitorName" required="required"
				style="width: 142px;" readonly="true"></td>
		</tr>
		<tr>
			<th style="color: red;">监测方法:</th>
			<td><input class="easyui-textbox" name="monitorMethod.name"
				id="monitorMethord"
				required="required" style="width: 142px;" readonly="true"></td>
		</tr>
		<tr>
			<th style="color: red;">添加测点数 :</th>
			<td><input id="pointNumber" type="text"
				class="easyui-textbox easyui-validatebox" required="required" /></td>
		</tr>
		<tr>
			<td colspan="2" align="center">
				<div>
					<a class="easyui-linkbutton" data-options="iconCls:'icon-save'"
						style="width: 60px;" href="#" id="btnok">保存</a> <a
						class="easyui-linkbutton" data-options="iconCls:'icon-cancel'"
						style="width: 60px;" href="#" id="btncancle">取消</a>
				</div>
			</td>
		</tr>
	</table> -->

	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'center'" style="padding: 10px;">
			<table class="form_table">
				<tr>
					<th style="">监测项目：</th>
					<td><input class="easyui-textbox" name="monitorType.name"
						id="monitorName" required="required" style="width: 142px;"
						readonly="true"></td>
				</tr>
				<tr>
					<th style="">监测方法:</th>
					<td><input class="easyui-textbox" name="monitorMethod.name"
						id="monitorMethord" required="required" style="width: 142px;"
						readonly="true"></td>
				</tr>
				<tr>
					<th style="">添加测点数 :</th>
					<td><input id="pointNumber" type="text"
						class="easyui-textbox easyui-validatebox" required="required" /></td>
				</tr>
			</table>
		</div>
		<div data-options="region:'south',border:false"
			style="text-align: right; padding: 5px;border-color:#dddddd #95B8E7 #95B8E7 #95B8E7;background:#F4F4F4;">
			<a class="easyui-linkbutton" data-options="iconCls:'icon-save'"
				style="width: 90px;" href="#" id="btnok">保存</a> <a
				class="easyui-linkbutton" data-options="iconCls:'icon-cancel'"
				style="width: 90px;" href="#" id="btncancle">取消</a>
		</div>
	</div>




	<script>
		$(function() {

			//获取路径带来的数值

			var monitorName = getQueryString("monitorName");
			var monitorMethord = getQueryString("monitorMethord");

			if (monitorName && monitorMethord) {
				$('#monitorName').textbox('setValue', monitorName);
				$('#monitorMethord').textbox('setValue', monitorMethord);
			}

			//确认按钮
			$('#btnok').click(function() {

				var addPointCount = $('#pointNumber').textbox('getValue');

				var res = AddMonitorPoint(monitorName, addPointCount);

				if (window.parent != null && window.parent != undefined) {
					window.parent.WinCloseFun();
				}
			});

			//取消按钮
			$('#btncancle').click(function() {
				if (window.parent != null && window.parent != undefined) {
					window.parent.WinCloseFun();
				}
			});

		});

		function AddMonitorPoint(monitorName, addPointCount) {

			var result;

			$.ajax({
				url : "../TstWebRequest/TstResquest",
				async : false,//设置同步
				data : {
					action : "AddMonitorByAddCount",
					monitorName : monitorName,
					addPointCount : addPointCount,
				},
				type : "get",
				datatype : "json",
				success : function(data) {
					var obj = eval("(" + data + ")");

					result = obj;

				},
				error : function() {
					console.log('addpoint data error!');
				},
			});

			return result;
		}
	</script>

</body>
</html>