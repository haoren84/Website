<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增采集仪</title>

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
	<!-- <div id="dlg" style="width:100%">
          <form id="fm" method="post" novalidate style="margin:0;padding:0;">
              <div style="margin-bottom:10px">
                  <input id="insFactoryID" class="easyui-textbox" required="true" label="出厂编号:" labelWidth="150px" style="width:100%">
              </div>
              <div style="margin-bottom:10px">
                  <input id="insNetID" class="easyui-textbox" required="true" label="网络ID:" labelWidth="150px" style="width:100%">
              </div>
              <div style="margin-bottom:10px">
                  <input id="serverAddr" class="easyui-textbox" required="true" label="服务器地址:" labelWidth="150px" style="width:100%">
              </div>
              <div style="margin-bottom:10px">
                  <input id="serverPort" class="easyui-numberbox" required="true" label="服务器端口:" labelWidth="150px" style="width:100%">
              </div>
          </form>
     </div>
     
     <div id="dlg-buttons" style="text-align:center;">
          <a href="javascript:void(0)" class="easyui-linkbutton c6" id="btnOK" iconCls="icon-ok" style="width:90px">确认</a>
          <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="BtnCancle()" style="width:90px">取消</a>
      </div> -->

	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'center'" style="padding: 10px;">
			<div id="dlg" style="width: 100%">
				<form id="fm" method="post" novalidate
					style="margin: 0; padding: 0;">
					<div style="margin-bottom: 10px">
						<input id="insFactoryID" class="easyui-textbox" required="true"
							label="出厂编号:" labelWidth="150px" style="width: 100%">
					</div>
					<div style="margin-bottom: 10px">
						<input id="insNetID" class="easyui-textbox" required="true"
							label="网络ID:" labelWidth="150px" style="width: 100%">
					</div>
					<div style="margin-bottom: 10px">
						<input id="serverAddr" class="easyui-textbox" required="true"
							label="服务器地址:" labelWidth="150px" style="width: 100%">
					</div>
					<div style="margin-bottom: 10px">
						<input id="serverPort" class="easyui-numberbox" required="true"
							label="服务器端口:" labelWidth="150px" style="width: 100%">
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
		var nAutoID, insFactoryID, insNetID, serverAddr, serverPort;

		$(function() {

			var action = getQueryString("action");

			if (action == "add") {

				$('#btnOK').click(
						function() {
							//新增采集仪

							GetHtmlInfo();

							var addRes = JSON.parse(AddInsAcqFun());

							if (addRes && addRes.result == "true") {

								if (window.parent != null
										&& window.parent != undefined) {
									window.parent.CloseInsAcqWin("add",
											addRes.errorMsg);
								}

							} else {
								$.messager.alert('提示', '新增失败');
							}

						});

			} else if (action == "update") {

				nAutoID = getQueryString("nAutoID");
				insFactoryID = getQueryString("insFactoryID");
				insNetID = getQueryString("insNetID");
				serverAddr = getQueryString("serverAddr");
				serverPort = getQueryString("serverPort");

				$('#insFactoryID').textbox('setValue', insFactoryID);
				$('#insNetID').textbox('setValue', insNetID);
				$('#serverAddr').textbox('setValue', serverAddr);
				$('#serverPort').textbox('setValue', serverPort);

				$('#btnOK').click(
						function() {
							//修改采集仪

							GetHtmlInfo();

							var updateRes = JSON.parse(UpdateInsAcqFun());

							if (updateRes && updateRes.result == "true") {

								if (window.parent != null
										&& window.parent != undefined) {
									window.parent.CloseInsAcqWin("update",
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
				window.parent.CloseInsAcqWin(null, null);
			}
		}

		//获取页面的数据
		function GetHtmlInfo() {
			insFactoryID = $('#insFactoryID').textbox('getValue');
			insNetID = $('#insNetID').textbox('getValue');
			serverAddr = $('#serverAddr').textbox('getValue');
			serverPort = $('#serverPort').textbox('getValue');
		}

		//新增采集仪
		function AddInsAcqFun() {

			var res = null;

			$.ajax({
				type : "GET",
				url : "../RelationInsRequest",
				/* data: "page=" + page + "&rows=" + rows + "&keyword=" + $('#hdKeyword').val(), */
				async : false,//设置同步
				data : {
					action : "CreateInstrumentAcq",
					insFactoryID : insFactoryID,
					insNetID : insNetID,
					serverAddr : serverAddr,
					serverPort : serverPort
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

		//修改采集仪
		function UpdateInsAcqFun() {
			var res = null;

			$.ajax({
				type : "GET",
				url : "../RelationInsRequest",
				/* data: "page=" + page + "&rows=" + rows + "&keyword=" + $('#hdKeyword').val(), */
				async : false,//设置同步
				data : {
					action : "UpdateInstrumentAcq",
					nAutoID : nAutoID,
					insFactoryID : insFactoryID,
					insNetID : insNetID,
					serverAddr : serverAddr,
					serverPort : serverPort
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