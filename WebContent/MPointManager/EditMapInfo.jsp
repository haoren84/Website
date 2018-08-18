<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑匹配信息</title>

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
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'center'" style="padding: 10px;">
			<div id="dlg" style="width: 100%;">
				<form id="fm" method="post" novalidate style="margin: 0; padding: 0">
					<div style="margin-bottom: 10px">
						<input id="insFactoryID" class="easyui-combogrid" required="true"
							label="终端名称:" labelWidth="150px" style="width: 100%">
					</div>
					<div style="margin-bottom: 10px">
						<input id="terminalType" class="easyui-textbox" required="true"
							label="终端规格型号:" labelWidth="150px" style="width: 100%">
					</div>
					<div style="margin-bottom: 10px">
						<input id="chnID" class="easyui-combobox" required="true"
							label="通道号:" labelWidth="150px" style="width: 100%">
					</div>
					<div style="margin-bottom: 10px">
						<input id="sensorName" class="easyui-combogrid" required="true"
							label="传感器名称:" labelWidth="150px" style="width: 100%">
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
	
	var monitorName,monitorPtName,insFactoryID,chnID,SensorID,terminalType;
	var action;
		
	$(function() {
		
		monitorName=getQueryString("monitorName");
		monitorPtName=getQueryString("monitorPtName");
		insFactoryID=getQueryString("insFactoryID");
		chnID=getQueryString("chnID");
		SensorID=getQueryString("SensorID");
		terminalType=getQueryString("terminalType");
		
		if(!insFactoryID||insFactoryID=='NULL'){
			action="add";
		}else{
			action="update";
		}
		
		
		$('#insFactoryID').combogrid({
			
			url : "../MPManagerRequest",
			queryParams : {
				action : 'GetTerminalComboPageData',
			},
			panelWidth : 400,
			idField : 'insFactoryID', //ID字段  
			textField : 'terminalName', //显示的字段
			fitColumns : true,
			striped : true,
			editable : true,
			pagination : true, //是否分页
			rownumbers : true, //序号
			collapsible : false, //是否可折叠的
			singleSelect:true,
			method : 'get',
			columns :[ [ {
				field : 'insFactoryID',
				title : '出厂编号',
				width : 100,
				//hidden : true
			}, {
				field : 'terminalName',
				title : '终端名称',
				width : 150
			}, {
				field : 'terminalType',
				title : '终端规格型号',
				width : 150
			}, {
				field : 'insID',
				title : '终端机号',
				width : 50
			} ] ],
			onLoadSuccess:function(data){
				
				if(action=="update"){
					$('#insFactoryID').combogrid('setValue',insFactoryID);
					
					$('#chnID').combobox({
						
						url : "../MPManagerRequest?action=GetTerminalChnComboData&insFactoryID="+insFactoryID,
			    		valueField:'chnID',
					    textField:'chnIDName',
					    onLoadSuccess:function(){
					    	if(action=="update"){
					    		$('#chnID').combobox('setValue',chnID);
					    	}
					    },
						
					});
					
					$('#terminalType').textbox('setText',terminalType);
				}
				
			},
			onSelect:function(record){
				var g = $('#insFactoryID').combogrid('grid');	// get datagrid object
				var r = g.datagrid('getSelected');	// get the selected row
				
				if(r){
					
					$('#terminalType').textbox('setText',r.terminalType);
					
				}
				
				if(r){
					$('#chnID').combobox({
						
						url : "../MPManagerRequest?action=GetTerminalChnComboData&insFactoryID="+r.insFactoryID,
			    		valueField:'chnID',
					    textField:'chnIDName',
					    onLoadSuccess:function(){
					    },
						
					});
				}
				
			}
		});
		
		//取得分页组件对象
		var terminalpager = $('#insFactoryID').combogrid('grid')
				.datagrid('getPager');
		
		if (terminalpager) {
			$(terminalpager)
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
									$('#insFactoryID')
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
									getterminalData(pageNumber, pageSize);
									//将隐藏域中存放的查询条件显示在combogrid的文本框中
									/* $('#cg').combogrid("setValue", $('#hdKeyword').val());
									$('#txtGender').val(''); */
								}
							});
		}

		var getterminalData = function(page, rows) {
			$.ajax({
				type : "GET",
				url : "../MPManagerRequest",
				/* data: "page=" + page + "&rows=" + rows + "&keyword=" + $('#hdKeyword').val(), */
				async : false,//设置同步
				data : {
					action : "GetTerminalComboPageData",
					page : page,
					rows : rows,
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					alert(textStatus);
					$.messager.progress('close');
				},
				success : function(data) {
					$('#insFactoryID').combogrid('grid')
							.datagrid('loadData', $.parseJSON(data));
				}
			});
		};
		
		
		//传感器数据加载---------------------------------------
		
		
		$('#sensorName').combogrid({
			url : "../MPManagerRequest",
			queryParams : {
				action : 'GetSeneorComboPageData',
			},
			panelWidth : 400,
			idField : 'SensorID', //ID字段  
			textField : 'SensorName', //显示的字段
			fitColumns : true,
			striped : true,
			editable : true,
			pagination : true, //是否分页
			rownumbers : true, //序号
			collapsible : false, //是否可折叠的
			singleSelect:true,
			method : 'get',
			columns :[ [ {
				field : 'SensorName',
				title : '传感器名字',
				width : 100,
			}, {
				field : 'SensorType',
				title : '传感器规格型号',
				width : 150
			} ] ],
			onLoadSuccess:function(data){
				if(action=="update"){
		    		$('#sensorName').combogrid('setValue',SensorID);
		    	}
			},
			onSelect:function(record){
				
			}
				
		});
		
		//取得分页组件对象
		var sensorpager = $('#sensorName').combogrid('grid')
				.datagrid('getPager');
		
		if (sensorpager) {
			$(sensorpager)
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
									$('#sensorName')
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
									getsensorData(pageNumber, pageSize);
									//将隐藏域中存放的查询条件显示在combogrid的文本框中
									/* $('#cg').combogrid("setValue", $('#hdKeyword').val());
									$('#txtGender').val(''); */
								}
							});
		}

		var getsensorData = function(page, rows) {
			$.ajax({
				type : "GET",
				url : "../MPManagerRequest",
				/* data: "page=" + page + "&rows=" + rows + "&keyword=" + $('#hdKeyword').val(), */
				async : false,//设置同步
				data : {
					action : "GetSeneorComboPageData",
					page : page,
					rows : rows,
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					alert(textStatus);
					$.messager.progress('close');
				},
				success : function(data) {
					$('#sensorName').combogrid('grid')
							.datagrid('loadData', $.parseJSON(data));
				}
			});
		};
		
		if(action=="add"){
			
			$('#btnOK').click(function() {
				
				var addRes = JSON.parse(AddMapInfo());

				if (addRes && addRes.result == "true") {

					if (window.parent != null
							&& window.parent != undefined) {
						window.parent.CloseEditWin("add",
								addRes.errorMsg);
					}

				} else {
					$.messager.alert('提示', '匹配失败');
				}
			})
		}else if(action=="update"){
			
			$('#btnOK').click(
					function() {
						
						var updateRes = JSON.parse(UpdateMapInfo());

						if (updateRes && updateRes.result == "true") {

							if (window.parent != null
									&& window.parent != undefined) {
								window.parent.CloseEditWin("update",
										updateRes.errorMsg);
							}

						} else {
							$.messager.alert('提示', '匹配修改失败');
						}
						
						
					});
			
		}
		
		
	});
	
	//取消按钮
	function BtnCancle(){
		if (window.parent != null && window.parent != undefined) {
			window.parent.CloseEditWin(null, null);
		}
	}
	
	</script>
	
	<script>
	
	//创建新的匹配关系
	function AddMapInfo(){
		var res = null;

		$.ajax({
			type : "GET",
			url : "../MPManagerRequest",
			/* data: "page=" + page + "&rows=" + rows + "&keyword=" + $('#hdKeyword').val(), */
			async : false,//设置同步
			data : {
				action : "AddMapInfo",
				insFactoryID : $('#insFactoryID').combogrid('getValue'),
				monitorName:monitorName,
				monitorPtName:monitorPtName,
				SensorID: $('#sensorName').combogrid('getValue'),
				chnID:$('#chnID').combobox('getValue'),
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
	
	//修改匹配关系
	function UpdateMapInfo(){
		
		var res = null;
		
		$.ajax({
			type : "GET",
			url : "../MPManagerRequest",
			/* data: "page=" + page + "&rows=" + rows + "&keyword=" + $('#hdKeyword').val(), */
			async : false,//设置同步
			data : {
				action : "UpdateMapInfo",
				insFactoryID : $('#insFactoryID').combogrid('getValue'),
				monitorName:monitorName,
				monitorPtName:monitorPtName,
				SensorID: $('#sensorName').combogrid('getValue'),
				chnID:$('#chnID').combobox('getValue'),
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