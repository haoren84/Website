<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑监测项目</title>

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
			<td><input class="easyui-combobox easyui-validatebox"
				name="monitorType.id"
				id="monitorTypeDATA_PROJECT_INFO_monitoritemDlg"
				style="width: 142px;" required="required"></td>
		</tr>
		<tr>
			<th style="color: red;">监测方法:</th>
			<td><input class="easyui-combobox easyui-validatebox"
				name="monitorMethod.id"
				id="monitorMethodDATA_PROJECT_INFO_monitoritemDlg"
				required="required" style="width: 142px;"></td>
		</tr>
		<tr>
			<th style="color: red;">测点数 :</th>
			<td><input name="pointCount" id="pointCount" type="text"
				class="easyui-textbox easyui-validatebox" required="required" /></td>
		</tr>
		<tr>
			<th>测点前缀:</th>
			<td><input id="pointPrefix" type="text"
				class="easyui-textbox easyui-validatebox" /></td>
		</tr>
		<tr>
			<th>测点起始编号 :</th>
			<td><input id="pointStart" type="text"
				class="easyui-textbox easyui-validatebox" /></td>
		</tr>
		<tr>
			<th>测点结束编号 :</th>
			<td><input id="pointEnd" type="text"
				class="easyui-textbox easyui-validatebox" /></td>
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
			<div id="dlgfm" style="width: 100%">
					<form id="fm" method="post" novalidate
					style="margin: 0; padding: 20px 50px">
					
					<div style="margin-bottom: 10px">
						<input class="easyui-combobox easyui-validatebox"
						name="monitorType.id"
						id="monitorTypeDATA_PROJECT_INFO_monitoritemDlg"
						style="width: 100%;" label="监测项目:" required="required" >
					</div>
					
					<div style="margin-bottom: 10px">
						<input class="easyui-combobox easyui-validatebox"
						name="monitorMethod.id"
						id="monitorMethodDATA_PROJECT_INFO_monitoritemDlg"
						required="required" label="监测方法:" style="width: 100%;" >
					</div>
					
					<div style="margin-bottom: 10px">
						<input name="pointCount" id="pointCount" type="text"
						class="easyui-textbox easyui-validatebox" label="测点数:" required="required" style="width: 100%;" />
					</div>
					
					<div style="margin-bottom: 10px">
						<input id="pointPrefix" type="text"
						class="easyui-textbox easyui-validatebox" label="测点前缀:" required="true" style="width: 100%;"
						data-options="validType:'length[0,10]',missingMessage:'长度不得超过10'" />
					</div>
					
					<div style="margin-bottom: 10px">
						<input id="pointStart" type="text"
						class="easyui-textbox easyui-validatebox" label="测点起始编号:" required="required" style="width: 100%;" />
					</div>
					
					<!-- <div style="margin-bottom: 10px">
						<input id="pointPrefix" type="text"
						class="easyui-textbox easyui-validatebox" label="测点结束编号:" required="required" style="width: 100%;" />
					</div> -->
					
					</form>
		    </div>
		
			<!-- <table class="form_table">
				<tr>
					<th style="">监测项目：</th>
					<td><input class="easyui-combobox easyui-validatebox"
						name="monitorType.id"
						id="monitorTypeDATA_PROJECT_INFO_monitoritemDlg"
						style="width: 142px;" required="required"></td>
				</tr>
				<tr>
					<th style="">监测方法:</th>
					<td><input class="easyui-combobox easyui-validatebox"
						name="monitorMethod.id"
						id="monitorMethodDATA_PROJECT_INFO_monitoritemDlg"
						required="required" style="width: 142px;"></td>
				</tr>
				<tr>
					<th style="">测点数 :</th>
					<td><input name="pointCount" id="pointCount" type="text"
						class="easyui-textbox easyui-validatebox" required="required" /></td>
				</tr>
				<tr>
					<th>测点前缀:</th>
					<td><input id="pointPrefix" type="text"
						class="easyui-textbox easyui-validatebox" /></td>
				</tr>
				<tr>
					<th>测点起始编号 :</th>
					<td><input id="pointStart" type="text"
						class="easyui-textbox easyui-validatebox" /></td>
				</tr>
				<tr>
					<th>测点结束编号 :</th>
					<td><input id="pointEnd" type="text"
						class="easyui-textbox easyui-validatebox" /></td>
				</tr>
			</table> -->
		</div>
		<div data-options="region:'south',border:false"
			style="text-align: right; padding: 5px;border-color:#dddddd #95B8E7 #95B8E7 #95B8E7;background:#F4F4F4;">
			<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'"
				style="width: 90px;" href="#" id="btnok">保存</a> <a
				class="easyui-linkbutton" data-options="iconCls:'icon-cancel'"
				style="width: 90px;" href="#" id="btncancle">取消</a>
		</div>
	</div>



	<script>
		var monitorName, monitorMethord, monitorPointCount, monitorPointPrefix, monitorPointStartID, monitorPointEndID;

		//读取页面的信息
		function GetHtmlInfo() {
			monitorName = $("#monitorTypeDATA_PROJECT_INFO_monitoritemDlg")
					.combobox('getValue');
			monitorMethord = $("#monitorMethodDATA_PROJECT_INFO_monitoritemDlg")
					.combobox('getValue');
			monitorPointCount = $("#pointCount").textbox('getValue');
			monitorPointPrefix = $("#pointPrefix").textbox('getValue');
			monitorPointStartID = $("#pointStart").textbox('getValue');
			/* monitorPointEndID = $("#pointEnd").textbox('getValue'); */
			monitorPointEndID=parseInt(monitorPointStartID)+parseInt(monitorPointCount)-1;
		}
	</script>

	<!-- 按钮处理 -->
	<script>
		$(function() {

			var action = getQueryString("action");

			if (action == "add") {
				//新增页面的操作
				$('#btnok').click(function() {

					//读取页面的信息，调用保存方法
					var res = AddMonitorPrjInfo();

					if(res&&res.result=="false"){
						$.messager.alert('提示','新增失败，监测项目名称不能重复!');
						return;
					}
					
					if (window.parent != null && window.parent != undefined) {
						window.parent.CloseMonitorInfoWin("add");
					}

				});

				$('#btncancle').click(function() {
					//调用父页面的关闭方法
					if (window.parent != null && window.parent != undefined) {
						window.parent.CloseMonitorInfoWin("cancle");
					}
				});

			} else if (action == "update") {

				//修改页面的操作
				$('#btnok').click(function() {

				});

				$('#btncancle').click(function() {
					//调用父页面的关闭方法
					if (window.parent != null && window.parent != undefined) {
						window.parent.CloseMonitorInfoWin("cancle");
					}
				});

			}

		});

		//新增监测项目
		function AddMonitorPrjInfo() {

			GetHtmlInfo();
			
			if(ComputeStrLength(monitorPointPrefix)>10){
				
				$.messager.alert('提示','测点前缀超过了限制!');
				
				return;
				
			}
			
			if(monitorPointCount<1||monitorPointCount>1000){
				
				$.messager.alert('提示','测点数超出范围!');
				
				return;
			}

			var res = null;

			$.ajax({
				type : "GET",
				url : "../TstWebRequest/TstResquest",
				/* data: "page=" + page + "&rows=" + rows + "&keyword=" + $('#hdKeyword').val(), */
				async : false,//设置同步
				data : {
					action : "AddMonitorPrjInfo",
					monitorName : monitorName,
					monitorMethord : monitorMethord,
					monitorPointCount : monitorPointCount,
					monitorPointPrefix : monitorPointPrefix,
					monitorPointStartID : monitorPointStartID,
					monitorPointEndID : monitorPointEndID,
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					console.log('add error');
				},
				success : function(data) {
					res = JSON.parse(data);
				}
			});
			return res;

		}
	</script>

	<!-- 监测项目下拉的表格信息 -->
	<script>
		$(function() {
			$('#monitorTypeDATA_PROJECT_INFO_monitoritemDlg')
					.combogrid(
							{
								panelWidth : 400,
								idField : 'monitorTypeName', //ID字段  
								textField : 'monitorTypeName', //显示的字段  
								url : "../TstWebRequest/TstResquest?action=GetMonitorTypePageData",
								fitColumns : true,
								striped : true,
								editable : true,
								pagination : true, //是否分页
								rownumbers : true, //序号
								collapsible : false, //是否可折叠的
								/* multiple:true, */
								/* fit: true, *///自动大小
								method : 'get',
								columns : [ [ {
									field : 'monitorTypeName',
									title : '测量类型',
									width : 150
								} ] ],
								onChange : function(newValue, oldValue) {
									console.log(newValue);
									monitorName = newValue;
									MonitorMethodFun();
								}

							});

			//取得分页组件对象
			var pager = $('#monitorTypeDATA_PROJECT_INFO_monitoritemDlg')
					.combogrid('grid').datagrid('getPager');

			if (pager) {
				$(pager)
						.pagination(
								{
									pageSize : 10, //每页显示的记录条数，默认为10
									pageList : [ 10, 5, 3 ], //可以设置每页记录条数的列表
									beforePageText : '第', //页数文本框前显示的汉字
									afterPageText : '页    共 {pages} 页',
									displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录',
									//选择页的处理
									onSelectPage : function(pageNumber,
											pageSize) {
										//按分页的设置取数据
										getData(pageNumber, pageSize);
										//设置表格的pageSize属性，表格变化时按分页组件设置的pageSize显示数据
										$(
												'#monitorTypeDATA_PROJECT_INFO_monitoritemDlg')
												.combogrid("grid").datagrid(
														'options').pageSize = pageSize;
										//将隐藏域中存放的查询条件显示在combogrid的文本框中
										/* $('#cg').combogrid("setValue", $('#hdKeyword').val());
										$('#txtGender').val(''); */
									},
									//改变页显示条数的处理
									//（处理后还是走onSelectPage事件，所以设置也写到onSelectPage事件中了）
									onChangePageSize : function() {
									},
									//点击刷新的处理
									onRefresh : function(pageNumber, pageSize) {
										//按分页的设置取数据
										getData(pageNumber, pageSize);
										//将隐藏域中存放的查询条件显示在combogrid的文本框中
										/* $('#cg').combogrid("setValue", $('#hdKeyword').val());
										$('#txtGender').val(''); */
									}
								});
			}

			var getData = function(page, rows) {
				$.ajax({
					type : "GET",
					url : "../TstWebRequest/TstResquest",
					/* data: "page=" + page + "&rows=" + rows + "&keyword=" + $('#hdKeyword').val(), */
					async : false,//设置同步
					data : {
						action : "GetMonitorTypePageData",
						page : page,
						rows : rows,
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						alert(textStatus);
						$.messager.progress('close');
					},
					success : function(data) {
						$('#monitorTypeDATA_PROJECT_INFO_monitoritemDlg')
								.combogrid('grid').datagrid('loadData',
										$.parseJSON(data));
					}
				});
			};
		});
	</script>

	<!--监测方法下拉  -->
	<script>
		function MonitorMethodFun() {
			$('#monitorMethodDATA_PROJECT_INFO_monitoritemDlg')
					.combogrid(
							{
								panelWidth : 400,
								idField : 'monitorTypeMethordName', //ID字段  
								textField : 'monitorTypeMethordName', //显示的字段  
								url : "../TstWebRequest/TstResquest?action=GetMonitorMethordPageData&monitorTypeName="
										+ monitorName,
								fitColumns : true,
								striped : true,
								editable : true,
								pagination : true, //是否分页
								rownumbers : true, //序号
								collapsible : false, //是否可折叠的
								/* multiple:true, */
								/* fit: true, *///自动大小
								method : 'get',
								columns : [ [ {
									field : 'monitorTypeMethordName',
									title : '测量方法',
									width : 150
								} ] ],

							});

			//取得分页组件对象
			var Methordpager = $(
					'#monitorMethodDATA_PROJECT_INFO_monitoritemDlg')
					.combogrid('grid').datagrid('getPager');

			if (Methordpager) {
				$(Methordpager)
						.pagination(
								{
									pageSize : 10, //每页显示的记录条数，默认为10
									pageList : [ 10, 5, 3 ], //可以设置每页记录条数的列表
									beforePageText : '第', //页数文本框前显示的汉字
									afterPageText : '页    共 {pages} 页',
									displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录',
									//选择页的处理
									onSelectPage : function(pageNumber,
											pageSize) {
										//按分页的设置取数据
										getMethordData(pageNumber, pageSize);
										//设置表格的pageSize属性，表格变化时按分页组件设置的pageSize显示数据
										$(
												'#monitorMethodDATA_PROJECT_INFO_monitoritemDlg')
												.combogrid("grid").datagrid(
														'options').pageSize = pageSize;
										//将隐藏域中存放的查询条件显示在combogrid的文本框中
										/* $('#cg').combogrid("setValue", $('#hdKeyword').val());
										$('#txtGender').val(''); */
									},
									//改变页显示条数的处理
									//（处理后还是走onSelectPage事件，所以设置也写到onSelectPage事件中了）
									onChangePageSize : function() {
									},
									//点击刷新的处理
									onRefresh : function(pageNumber, pageSize) {
										//按分页的设置取数据
										getMethordData(pageNumber, pageSize);
										//将隐藏域中存放的查询条件显示在combogrid的文本框中
										/* $('#cg').combogrid("setValue", $('#hdKeyword').val());
										$('#txtGender').val(''); */
									}
								});
			}

			var getMethordData = function(page, rows) {

				$.ajax({
					type : "GET",
					url : "../TstWebRequest/TstResquest",
					/* data: "page=" + page + "&rows=" + rows + "&keyword=" + $('#hdKeyword').val(), */
					async : false,//设置同步
					data : {
						action : "GetMonitorMethordPageData",
						page : page,
						rows : rows,
						monitorTypeName : monitorName,
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						alert(textStatus);
						$.messager.progress('close');
					},
					success : function(data) {
						$('#monitorMethodDATA_PROJECT_INFO_monitoritemDlg')
								.combogrid('grid').datagrid('loadData',
										$.parseJSON(data));
					}
				});
			};
		}

		$(function() {

		});
	</script>

</body>
</html>