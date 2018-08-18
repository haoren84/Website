<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>工程结构</title>

<link rel="stylesheet" type="text/css"
	href="../Resource/jquery-easyui-1.5.4.2/themes/default/easyui.css">
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
	<!-- http://testelectron.eicp.net:8080/MonitorWeb/PrjManage/PrjStruct.jsp?projectId=2&projectName=%E6%88%BF%E5%B1%8B%E7%9B%91%E6%B5%8Bwww -->
	<div class="easyui-panel" title="工程结构"
		style="width: 100%; height: 100%;">

		<!-- 项目结构窗口  -->
		<div id="win"></div>

		<table id="tb_struct" style="width: 100%; height: 100%;"></table>

	</div>



	<!-- 加载表格数据 -->
	<script>
		var tbStructToolbar = [ {
			text : '添加',
			iconCls : 'icon-add',
			handler : function() {
				AddStruct();
			}
		}, {
			text : '编辑',
			iconCls : 'icon-edit',
			handler : function() {
				UpdateStruct();
			}
		}, {
			text : '删除',
			iconCls : 'icon-remove',
			handler : function() {
				DeleteStruct();
			}
		} ];

		$(function() {
			//结构的树表格
			$('#tb_struct').treegrid({
				url : '../PrjStructResquest?action=GetStructData',
				idField : 'id',
				treeField : 'structName',
				columns : [ [ {
					field : 'structName',
					title : '名称',
					width : 180
				}, {
					field : 'curprjname',
					title : '工程',
					width : 80
				}, {
					field : 'structCode',
					title : '编码',
					width : 80
				}, {
					field : 'structType',
					title : '类型',
					width : 80
				}, {
					field : 'structAddress',
					title : '地址',
					width : 80
				}, {
					field : 'structRemark',
					title : '描述',
					width : 80
				} ] ],
				rownumbers : true,
				pagination : true,
				pageSize : 10,
				pageList : [ 5, 10, 20 ],
				onBeforeLoad : function(row, param) {
					if (!row) { // load top level rows
						param.id = 0; // set id=0, indicate to load new page rows
					}
				},
				toolbar : tbStructToolbar,
			});
		});
	</script>

	<!-- 新增结构的弹框 -->
	<script>
		function AddStruct() {
			$('#win')
					.window(
							{
								width : 1000,
								height : 700,
								modal : true,
								content : "<iframe scrolling=\"yes\" frameborder=\"0\" src=\"AddPrjStructWin.jsp?action=add\" style=\"width: 100%; height:100%;\"></iframe>",
								title : '新增结构',

							});

			$('#win').window('open');
		}

		//关闭新增弹框的方法
		function CloseAddStruct(actionInfo, actionMsg) {

			if (actionMsg != null && actionMsg != "" && actionMsg != undefined) {
				$.messager.alert('提示', actionMsg);
			}
			$('#win').window('close');

			if (actionInfo == "add" || actionInfo == "update") {
				$('#tb_struct').treegrid('reload');
			}
		}
	</script>

	<!-- 修改结构的弹框 -->
	<script>
		function UpdateStruct() {

			var objSelect = $('#tb_struct').treegrid('getSelected');

			if (objSelect) {

				$('#win')
						.window(
								{
									width : 1000,
									height : 700,
									modal : true,
									content : "<iframe scrolling=\"yes\" frameborder=\"0\" src=\"UpdatePrjStructWin.jsp?action=update"
											+ "&id="
											+ objSelect.id
											+ "&curprjname="
											+ objSelect.curprjname
											+ "&structAddress="
											+ objSelect.structAddress
											+ "&structCode="
											+ objSelect.structCode
											+ "&structName="
											+ objSelect.structName
											+ "&structParent="
											+ objSelect.structParent
											+ "&structRemark="
											+ objSelect.structRemark
											+ "&structType="
											+ objSelect.structType
											+ "\" style=\"width: 100%; height:100%;\"></iframe>",
									title : '修改结构',

								});

				$('#win').window('open');
			}
		}
	</script>

	<!-- 删除结构的方法  -->
	<script>
		function DeleteStruct() {

			var objSelect = $('#tb_struct').treegrid('getSelected');

			if (!objSelect) {
				return;
			}
			
			var resJson=JSON.parse(DeleteStructFun(objSelect.id));
			
			if(resJson&&resJson.result=="true"){
				
				$.messager.alert('提示', resJson.errorMsg);
				
				$('#tb_struct').treegrid('reload');
			}else{
				$.messager.alert('提示', '删除失败');
			}

		}

		function DeleteStructFun(structID) {
			var res = null;

			$.ajax({
				type : "GET",
				url : "../PrjStructResquest",
				/* data: "page=" + page + "&rows=" + rows + "&keyword=" + $('#hdKeyword').val(), */
				async : false,//设置同步
				data : {
					action : "DeleteStruct",
					structID:structID,
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