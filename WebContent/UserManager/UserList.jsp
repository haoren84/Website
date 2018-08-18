<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户列表</title>

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

<!-- http://testelectron.eicp.net:8080/MonitorWeb/UserManager/UserList.jsp?projectId=2&projectName=%E6%88%BF%E5%B1%8B%E7%9B%91%E6%B5%8Bwww -->
<div class="easyui-panel" title="用户列表" style="width:100%;height:100%;">

	<!-- 项目结构窗口  -->
	<div id="win"></div>
	
	<table id="tb_User" style="width:100%;height:100%;">
	
	</table>
	
	
	<script>
	
	var tbUserToolbar = [
		{
			text : '详情',
			iconCls : 'icon-search',
			handler : function() {
				//alert('search');
				
				var objSelect = $('#tb_User').datagrid('getSelected');
				
				if(objSelect){
					DetailUserFun();
				}
			}
		},
		{
			text : '新增',
			iconCls : 'icon-add',
			handler : function() {
				/* var objSelect = $('#tbmonitor').datagrid()
						.datagrid('getSelected'); */
			 	
				var objSelect = $('#tb_User').datagrid('getSelected');
				
				if(objSelect){
					AddUserFun();
				}
			}
		} /* ,
		{
			text : '删除',
			iconCls : 'icon-remove',
			handler : function() {
				 var objSelect = $('#tbmonitor').datagrid()
						.datagrid('getSelected'); 
			   
			}
		} */  ];
		
	$(function() {
		
		$('#tb_User').datagrid({
			
			url:"../UserCtrlRequest?action=GetUserPageData",
			method:'get',
			rownumbers:true,
			singleSelect:true,
			pagination:true,
			columns : [ [ {
				field : 'username',
				title : '用户名称',
				width : 200
			} ] ],
			toolbar:tbUserToolbar
		});
		
		var tbuserpager = $('#tb_User').datagrid(
		'getPager');
		
		
		tbuserpager.pagination({
			
			pageSize : 10, //每页显示的记录条数，默认为10
			pageList : [ 10, 5, 3 ], //可以设置每页记录条数的列表
			beforePageText : '第', //页数文本框前显示的汉字
			afterPageText : '页    共 {pages} 页',
			displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录',
			//选择页的处理
			onSelectPage : function(pageNumber, pageSize) {
				//按分页的设置取数据
				getPageData(pageNumber, pageSize);
				//设置表格的pageSize属性，表格变化时按分页组件设置的pageSize显示数据
				$('#tb_User').datagrid('options').pageSize = pageSize;
			},
			//改变页显示条数的处理
			//（处理后还是走onSelectPage事件，所以设置也写到onSelectPage事件中了）
			onChangePageSize : function() {
			},
			//点击刷新的处理
			onRefresh : function(pageNumber, pageSize) {
				//按分页的设置取数据
				getPageData(pageNumber, pageSize);
			},
			
		});
		
		var getPageData = function(page, rows) {
			$.ajax({
				type : "GET",
				url : "../UserCtrlRequest",
				/* data: "page=" + page + "&rows=" + rows + "&keyword=" + $('#hdKeyword').val(), */
				async : false,//设置同步
				data : {
					action : "GetUserPageData",
					page : page,
					rows : rows,
				},
				error : function(XMLHttpRequest, textStatus,
						errorThrown) {
					alert(textStatus);
					$.messager.progress('close');
				},
				success : function(data) {
					$('#tb_User').datagrid('loadData', $.parseJSON(data));
				}
			});
		};
		
	});
	
	
	//新增用户的窗口
	function AddUserFun(){
		
		
		
		$('#win')
		.window(
				{
					width : 500,
					height : 300,
					modal : true,
					content : "<iframe scrolling=\"yes\" frameborder=\"0\" src=\"EditUser.jsp?action=add"
							+ "\" style=\"width: 100%; height:100%;\"></iframe>",
					title : '新增用户',
					collapsible:false,
					minimizable:false,
					maximizable:false,
				});

		$('#win').window('open');
	}
	
	//用户详情的窗口
	function DetailUserFun(){
		
		var objSelect = $('#tb_User').datagrid('getSelected');
		
		$('#win')
		.window(
				{
					width : 500,
					height : 300,
					modal : true,
					content : "<iframe scrolling=\"yes\" frameborder=\"0\" src=\"EditUser.jsp?action=detail"
							+ "&UserName="
						    + objSelect.username
							+ "\" style=\"width: 100%; height:100%;\"></iframe>",
					title : '用户详情',
					collapsible:false,
					minimizable:false,
					maximizable:false,
				});

		$('#win').window('open');
		
	}
	
	//删除用户
	function DeleteUserFun(){
		
		var objSelect = $('#tb_User').datagrid('getSelected');
		
		if(objSelect){
			
		}else{
			$.messager.alert('提示', '无操作对象');
		}
	}
	
	//关闭用户的编辑窗口
	function CloseUserWin(action, msg){
		
		if (msg) {
			$.messager.alert('提示', msg);
			
			$('#tb_User').datagrid('reload');
		}

		$('#win').window('close');
	}
	
	</script>

</div>
</body>
</html>