<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>字典控制</title>

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

<!-- http://testelectron.eicp.net:8080/MonitorWeb/SystemManage/DictionaryInfo.jsp -->
<div class="easyui-panel" title="字典管理" style="width:100%;height:100%;">

	<!-- 项目结构窗口  -->
	<div id="win"></div>
	
	<table id="tb_Dictionary" style="width:100%;height:100%;">
	
	</table>

</div>

	<!-- 加载 -->
	<script>
	//操作栏
	function formatOperDict(val, row, index){
		
		if(row.attributes&&row.attributes.ParentID==0){
			return '<a href="#" rel="external nofollow" onclick="AddDictionaryItem('
			+ row.id
			+ ')">新增</a>|<a href="#" rel="external nofollow" onclick="EditDictionaryUsed('
			+ row.id
			+ ')">详情</a>';
		}
		
		return '<a href="#" rel="external nofollow" onclick="UpdateDictionaryItem('
		+ row.id
		+ ')">详情</a>|<a href="#" rel="external nofollow" onclick="DeleteDictionaryItem('
		+ row.id
		+ ')">删除</a>';
	}
	
	$(function() {
		$('#tb_Dictionary').treegrid({
			url : '../DictionaryRequest?action=GetDicTreeGridData',
			idField : 'id',
			treeField : 'DicValue',
			columns : [ [ {
				field : 'DicValue',
				title : '字典名称',
				width : 200
			},{
				field : 'UseState',
				title : '状态',
				width : 100
			},{
				field : '_operate',
				title : '操作',
				formatter : formatOperDict,
				width : 100
			} ] ],
			rownumbers : true,
			method : 'get',
			singleSelect : true,
			onBeforeLoad : function(row, param) {
				if (!row) { // load top level rows
					param.DicParentID = 0; // set id=0, indicate to load new page rows
				}
			},
			onBeforeExpand : function(row) {
				//console.log(row);

				if (row.attributes) {
					$('#tb_Dictionary').treegrid('options').queryParams = {
						DicParentID : row.attributes.autoid,
					};
				} 

			},
		});
		
		
	});
	
	//新增字典子值
	function AddDictionaryItem(index){
		
		$('#tb_Dictionary').treegrid('select', index);

		var row = $('#tb_Dictionary').treegrid('getSelected');
		
		//console.log(row);
		
		$('#win')
		.window(
				{
					width : 500,
					height : 300,
					modal : true,
					content : "<iframe scrolling=\"yes\" frameborder=\"0\" src=\"AddDictionary.jsp?action=add"
							+ "&autoid="
						    + row.attributes.autoid
						    + "&DicValue="
						    + row.attributes.DicValue
							+ "\" style=\"width: 100%; height:100%;\"></iframe>",
					title : '新增字典信息',
					collapsible:false,
					minimizable:false,
					maximizable:false,
				});

		$('#win').window('open');
	}
	
	//修改字典子值
	function UpdateDictionaryItem(index){
		
		$('#tb_Dictionary').treegrid('select', index);

		var row = $('#tb_Dictionary').treegrid('getSelected');
		
		$('#win')
		.window(
				{
					width : 500,
					height : 300,
					modal : true,
					content : "<iframe scrolling=\"yes\" frameborder=\"0\" src=\"AddDictionary.jsp?action=update"
							+ "&autoid="
						    + row.attributes.autoid
						    + "&DicValue="
						    + row.attributes.DicValue
						    + "&ParentDic="
						    + row.attributes.ParentDic
						    + "&ParentID="
						    + row.attributes.ParentID
						    + "&isUsed="
						    + row.attributes.isUsed
							+ "\" style=\"width: 100%; height:100%;\"></iframe>",
					title : '修改字典信息',
					collapsible:false,
					minimizable:false,
					maximizable:false,
				});

		$('#win').window('open');
	}
	
	
	//编辑字典的可用状态
	function EditDictionaryUsed(index){
		
		$('#tb_Dictionary').treegrid('select', index);

		var row = $('#tb_Dictionary').treegrid('getSelected');
		
		$('#win')
		.window(
				{
					width : 500,
					height : 300,
					modal : true,
					content : "<iframe scrolling=\"yes\" frameborder=\"0\" src=\"EditDictionaryState.jsp?action=add"
							+ "&nParentID="
					    	+ row.attributes.autoid
							+ "\" style=\"width: 100%; height:100%;\"></iframe>",
					title : '字典状态',
					collapsible:false,
					minimizable:false,
					maximizable:false,
				});

		$('#win').window('open');
	}
	
	//删除字典子项
	function DeleteDictionaryItem(index){
		
		$('#tb_Dictionary').treegrid('select', index);

		var row = $('#tb_Dictionary').treegrid('getSelected');
		
		if(row){
			
			$.messager.confirm('提示', '确认删除该字典项?',
					function(r) {
						if (r) {
							
							$.ajax({
								type : "GET",
								url : "../DictionaryRequest",
								/* data: "page=" + page + "&rows=" + rows + "&keyword=" + $('#hdKeyword').val(), */
								async : false,//设置同步
								data : {
									action : "DeleteDicInfo",
									DicID: row.attributes.autoid,
								},
								error : function(XMLHttpRequest, textStatus, errorThrown) {
									//alert(textStatus);
									//$.messager.progress('close');
									$.messager.alert('提示', 'error');
								},
								success : function(data) {
									
									var deleteRes=JSON.parse(data);
									
									if(deleteRes&&deleteRes.result=="true"){
										
										$.messager.alert('提示', '删除成功');
										
										$('#tb_Dictionary').treegrid('reload');
									}
									
								}
							});
							
						}
					});
			
			
		}
		
	}
	
	
	// 关闭窗口
	function CloseDictWin(action, msg) {

		if (msg) {
			$.messager.alert('提示', msg);
			
			$('#tb_Dictionary').treegrid('reload');
		}

		$('#win').window('close');
		
	}
	
	//刷新树结构
	function ReflashTreeGrid(){
		$('#tb_Dictionary').treegrid('reload');
	}
	
	
	
	
	
	
	</script>

</body>
</html>