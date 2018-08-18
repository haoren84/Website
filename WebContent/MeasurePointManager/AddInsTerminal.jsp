<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增终端</title>

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
	<!-- <div id="dlg" style="width: 100%;">
		<form id="fm" method="post" novalidate
			style="margin: 0; padding: 20px 50px">
			<div style="margin-bottom: 10px">
				<input id="insFactoryID" class="easyui-textbox" required="true"
					label="出厂编号:" labelWidth="150px" style="width: 100%">
			</div>
			<div style="margin-bottom: 10px">
				<input id="insID" class="easyui-numberbox" required="true"
					label="机号:" labelWidth="150px" style="width: 100%">
			</div>
			<div style="margin-bottom: 10px">
				<input id="insType" class="easyui-numberbox" required="true"
					label="仪器类型:" labelWidth="150px" style="width: 100%">
			</div>
			<div style="margin-bottom: 10px">
				<input id="insChnCount" class="easyui-numberbox" required="true"
					label="通道个数:" labelWidth="150px" style="width: 100%">
			</div>
		</form>
	</div>

	<div id="dlg-buttons" style="text-align:center;">
		<a href="javascript:void(0)" class="easyui-linkbutton c6"
			iconCls="icon-ok" id="btnOK" style="width: 90px">保存</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-cancel" onclick="BtnCancle()" style="width: 90px">取消</a>
	</div> -->

	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'center'" style="padding: 10px;">
			<div id="dlg" style="width: 100%;">
				<form id="fm" method="post" novalidate
					style="margin: 0; padding: 0">
					<div style="margin-bottom: 10px">
						<input id="insFactoryID" class="easyui-combobox" required="true"
							label="出厂编号:" labelWidth="150px" style="width: 100%">
					</div>
					<div style="margin-bottom: 10px">
						<input id="insID" class="easyui-numberbox" required="true"
							label="机号:" labelWidth="150px" style="width: 100%">
					</div>
					<div style="margin-bottom: 10px">
						<!-- <input id="insType" class="easyui-textbox" required="true"
							label="仪器类型:" labelWidth="150px" style="width: 100%"> -->
						
						<select id="insType" class="easyui-combobox" label="仪器类型:" 
								labelWidth="150px" style="width: 100%">
								
							<option value="1">应变</option>	
							<option value="2">桥式传感器</option>
							<option value="3">温度</option>
							<option value="4">倾角</option>
							<option value="5">电流</option>
							<option value="6">索力</option>
							<option value="7">振弦</option>
							<option value="8">静力水准仪</option>
							<option value="9">风速</option>
							<option value="10">位移</option>
							<option value="11">测斜仪</option>
							<option value="12">沉降仪</option>
							<option value="13">挠度</option>
							<option value="14">裂缝</option>
						</select>
							
					</div>
					<div style="margin-bottom: 10px">
						<input id="insChnCount" class="easyui-numberbox" required="true"
							label="通道个数:" labelWidth="150px" style="width: 100%">
					</div>
				</form>
			</div>
		</div>
		<div data-options="region:'south',border:false"
			style="text-align: right; padding: 5px; border-color: #dddddd #95B8E7 #95B8E7 #95B8E7; background: #F4F4F4;">
			<a href="javascript:void(0)" class="easyui-linkbutton c6"
				iconCls="icon-ok" id="btnOK" style="width: 90px">保存</a> <a
				href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-cancel" onclick="BtnCancle()" style="width: 90px">取消</a>
		</div>
	</div>



	<script>
		var nAutoID, insFactoryID, insID, insType, insChnCount;

		$(function() {

			var action = getQueryString("action");

			if (action == "add") {
				
				$('#btnOK').click(
						function() {

							GetHtmlInfo();

							var addRes = JSON.parse(AddInsTerminalFun());

							if (addRes && addRes.result == "true") {

								if (window.parent != null
										&& window.parent != undefined) {
									window.parent.CloseInsTerminalWin("add",
											addRes.errorMsg);
								}

							} else {
								$.messager.alert('提示', '新增失败');
							}
						});
				
				
				
				$('#insFactoryID').combobox({
					url : "../RelationInsRequest?action=GetFactoryIDComboboxData",
					valueField: 'text',
	                textField: 'text',
				});
				
				
				
			} else if (action == "update") {

				nAutoID = getQueryString("nAutoID");
				insFactoryID = getQueryString("insFactoryID");
				insID = getQueryString("insID");
				insType = getQueryString("insType");
				insChnCount = getQueryString("insChnCount");

				/* $('#insFactoryID').textbox('setValue', insFactoryID); */
				$('#insID').textbox('setValue', insID);
				$('#insType').combobox('setValue', insType);
				$('#insChnCount').textbox('setValue', insChnCount);

				$('#btnOK').click(
						function() {

							GetHtmlInfo();

							var updateRes = JSON.parse(UpdateInsTerminalFun());

							if (updateRes && updateRes.result == "true") {

								if (window.parent != null
										&& window.parent != undefined) {
									window.parent.CloseInsTerminalWin("update",
											updateRes.errorMsg);
								}

							} else {
								$.messager.alert('提示', '修改失败');
							}
						});
				
				$('#insFactoryID').combobox({
					url : "../RelationInsRequest?action=GetFactoryIDComboboxData",
					valueField: 'text',
	                textField: 'text',
	                onLoadSuccess: function (none) {
	                    if (none && none.length) {
	                        $('#insFactoryID').combobox('select', insFactoryID);
	                    }
	                },
				});
				
			}

		});

		//取消按钮
		function BtnCancle() {

			if (window.parent != null && window.parent != undefined) {
				window.parent.CloseInsTerminalWin(null, null);
			}

		}

		//获取页面的数据
		function GetHtmlInfo() {
			insFactoryID = $('#insFactoryID').combobox('getValue');
			insID = $('#insID').textbox('getValue');
			insType = $('#insType').combobox('getValue');
			insChnCount = $('#insChnCount').textbox('getValue');
		}

		//新增终端
		function AddInsTerminalFun() {

			var res = null;

			$.ajax({
				type : "GET",
				url : "../RelationInsRequest",
				/* data: "page=" + page + "&rows=" + rows + "&keyword=" + $('#hdKeyword').val(), */
				async : false,//设置同步
				data : {
					action : "CreateInsTerminal",
					insFactoryID : insFactoryID,
					insID : insID,
					insType : insType,
					insChnCount : insChnCount,
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

		//修改终端
		function UpdateInsTerminalFun() {
			var res = null;

			$.ajax({
				type : "GET",
				url : "../RelationInsRequest",
				/* data: "page=" + page + "&rows=" + rows + "&keyword=" + $('#hdKeyword').val(), */
				async : false,//设置同步
				data : {
					action : "UpdateInsTerminal",
					nAutoID : nAutoID,
					insFactoryID : insFactoryID,
					insID : insID,
					insType : insType,
					insChnCount : insChnCount,
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