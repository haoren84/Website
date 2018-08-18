<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑传感器信息</title>

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
						<input id="sensorName" class="easyui-combogrid" required="true"
							label="传感器名称:" labelWidth="150px" style="width: 100%">
					</div>
					<div style="margin-bottom: 10px">
						<input id="sensorSpec" class="easyui-textbox" required="true"
							label="传感器规格:" labelWidth="150px" style="width: 100%">
					</div>
					<div style="margin-bottom: 10px">
						<input id="sensorFactory" class="easyui-textbox" required="true"
							label="传感器厂商:" labelWidth="150px" style="width: 100%">
					</div>
					<div style="margin-bottom: 10px"  id="Param1Div">
						<input id="Param1" class="easyui-numberbox" required="true"
							label="传感器参数:" labelWidth="150px" style="width: 100%">
					</div>
					<div style="margin-bottom: 10px" id="Param2Div">
						<input id="Param2" class="easyui-numberbox" required="true"
							label="传感器参数:" labelWidth="150px" style="width: 100%">
					</div>
					<div style="margin-bottom: 10px" id="Param3Div">
						<input id="Param3" class="easyui-numberbox" required="true"
							label="传感器参数:" labelWidth="150px" style="width: 100%">
					</div>
					<div style="margin-bottom: 10px" id="Param4Div">
						<input id="Param4" class="easyui-numberbox" required="true"
							label="传感器参数:" labelWidth="150px" style="width: 100%">
					</div>
					<div style="margin-bottom: 10px" id="Param5Div">
						<input id="Param5" class="easyui-numberbox" required="true"
							label="传感器参数:" labelWidth="150px" style="width: 100%">
					</div>
					<div style="margin-bottom: 10px" id="Param6Div">
						<input id="Param6" class="easyui-numberbox" required="true"
							label="传感器参数:" labelWidth="150px" style="width: 100%">
					</div>
					<div style="margin-bottom: 10px" id="Param7Div">
						<input id="Param7" class="easyui-numberbox" required="true"
							label="传感器参数:" labelWidth="150px" style="width: 100%">
					</div>
					<div style="margin-bottom: 10px" id="Param8Div">
						<input id="Param8" class="easyui-numberbox" required="true"
							label="传感器参数:" labelWidth="150px" style="width: 100%">
					</div>
					
					<div style="margin-bottom: 10px">
						<input id="sensorDesc" class="easyui-textbox" required="true"
							label="传感器描述:" labelWidth="150px" style="width: 100%"
							multiline="true">
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
	var nAutoID,sensorID,sensorName,sensorSpec,sensorFactory,seneorDesc,sensorMeasureType;
	var param1,param2,param3,param4,param5,param6,param7,param8;
	var action;

	$(function() {
		
		//参数框隐藏
		
		$("#Param1Div").hide();
		$("#Param2Div").hide();
		$("#Param3Div").hide();
		$("#Param4Div").hide();
		$("#Param5Div").hide();
		$("#Param6Div").hide();
		$("#Param7Div").hide();
		$("#Param8Div").hide();
		
		
		action = getQueryString("action");
		
		$('#sensorName').combogrid({
			
			url : "../SensorRequest",
			queryParams : {
				action : 'GetSeneorNameComboPageData',
			},
			panelWidth : 400,
			idField : 'autoid', //ID字段  
			textField : 'sensorName', //显示的字段
			fitColumns : true,
			striped : true,
			editable : true,
			pagination : true, //是否分页
			rownumbers : true, //序号
			collapsible : false, //是否可折叠的
			singleSelect:true,
			method : 'get',
			columns : [ [ {
				field : 'sensorName',
				title : '传感器名字',
				width : 100,
			}, {
				field : 'sensorType',
				title : '传感器规格型号',
				width : 150
			} ] ],
			onLoadSuccess:function(data){
				
				if(action=="update"){
					
					nAutoID=getQueryString("autoid");
					sensorID=getQueryString("sensorID");
					sensorName=getQueryString("sensorName");
					sensorSpec=getQueryString("sensorSpec");
					sensorFactory=getQueryString("sensorFactory");
					seneorDesc=getQueryString("sensorDesc");
					sensorMeasureType=getQueryString("sensorMeasureType");
					param1=getQueryString("param1");
					param2=getQueryString("param2");
					param3=getQueryString("param3");
					param4=getQueryString("param4");
					param5=getQueryString("param5");
					param6=getQueryString("param6");
					param7=getQueryString("param7");
					param8=getQueryString("param8");
					
					//页面赋值
					
					$('#sensorName').combogrid('setText',sensorName);
					$('#sensorSpec').textbox('setValue',sensorSpec);
					$('#sensorDesc').textbox('setValue',seneorDesc);
					$('#sensorFactory').textbox('setValue',sensorFactory);
					
					if(sensorName.indexOf("振弦")>0){
						$("#Param1Div").show();
						$("#Param2Div").show();
						$("#Param3Div").hide();
						$("#Param4Div").hide();
						$("#Param5Div").hide();
						$("#Param6Div").hide();
						$("#Param7Div").hide();
						$("#Param8Div").hide();
						
						$("#Param1").numberbox('setValue',param1);
						$("#Param2").numberbox('setValue',param2);
					}else if(sensorName.indexOf("应变")>0){
						$("#Param1Div").show();
						$("#Param2Div").show();
						$("#Param3Div").hide();
						$("#Param4Div").hide();
						$("#Param5Div").hide();
						$("#Param6Div").hide();
						$("#Param7Div").hide();
						$("#Param8Div").hide();
						
						$("#Param1").numberbox('setValue',param1);
						$("#Param2").numberbox('setValue',param2);
					}else if(sensorName.indexOf("温度")>0){
						$("#Param1Div").show();
						$("#Param2Div").hide();
						$("#Param3Div").hide();
						$("#Param4Div").hide();
						$("#Param5Div").hide();
						$("#Param6Div").hide();
						$("#Param7Div").hide();
						$("#Param8Div").hide();
						
						$("#Param1").numberbox('setValue',param1);
					}else{
						$("#Param1Div").show();
						$("#Param2Div").hide();
						$("#Param3Div").hide();
						$("#Param4Div").hide();
						$("#Param5Div").hide();
						$("#Param6Div").hide();
						$("#Param7Div").hide();
						$("#Param8Div").hide();
						
						$("#Param1").numberbox('setValue',param1);
					}
				}
				
			},
			onSelect:function(record){
				var g = $('#sensorName').combogrid('grid');	// get datagrid object
				var r = g.datagrid('getSelected');	// get the selected row
				
				if(r){
					
					$('#sensorSpec').textbox('setValue',r.sensorType);
					$('#sensorDesc').textbox('setValue',r.sensorDesc);
					$('#sensorFactory').textbox('setValue',"泰之特");
					
					sensorName=r.sensorName;
					sensorSpec=r.sensorType;
					sensorFactory="泰之特";
					seneorDesc=r.sensorDesc;
					
					if(sensorName.indexOf("振弦")>0){
						$("#Param1Div").show();
						$("#Param2Div").show();
						$("#Param3Div").hide();
						$("#Param4Div").hide();
						$("#Param5Div").hide();
						$("#Param6Div").hide();
						$("#Param7Div").hide();
						$("#Param8Div").hide();
					}else if(sensorName.indexOf("应变")>0){
						$("#Param1Div").show();
						$("#Param2Div").show();
						$("#Param3Div").hide();
						$("#Param4Div").hide();
						$("#Param5Div").hide();
						$("#Param6Div").hide();
						$("#Param7Div").hide();
						$("#Param8Div").hide();
					}else if(sensorName.indexOf("温度")>0){
						$("#Param1Div").show();
						$("#Param2Div").hide();
						$("#Param3Div").hide();
						$("#Param4Div").hide();
						$("#Param5Div").hide();
						$("#Param6Div").hide();
						$("#Param7Div").hide();
						$("#Param8Div").hide();
					}else{
						$("#Param1Div").show();
						$("#Param2Div").hide();
						$("#Param3Div").hide();
						$("#Param4Div").hide();
						$("#Param5Div").hide();
						$("#Param6Div").hide();
						$("#Param7Div").hide();
						$("#Param8Div").hide();
					}
					
				}
			},
		});
		
		//取得分页组件对象
		var pager = $('#sensorName').combogrid('grid')
				.datagrid('getPager');
		
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
				url : "../SensorRequest",
				/* data: "page=" + page + "&rows=" + rows + "&keyword=" + $('#hdKeyword').val(), */
				async : false,//设置同步
				data : {
					action : "GetSeneorNameComboPageData",
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
		
		
		if (action == "add") {
			$('#btnOK').click(
					function() {
						var addRes = JSON.parse(AddSensor());

						if (addRes && addRes.result == "true") {

							if (window.parent != null
									&& window.parent != undefined) {
								window.parent.CloseEditWin("add",
										addRes.errorMsg);
							}

						} else {
							$.messager.alert('提示', '新增失败');
						}
					});
			
		}else if(action=="update"){
			
			$('#btnOK').click(
					function() {
						
						var updateRes = JSON.parse(UpdateSensor());

						if (updateRes && updateRes.result == "true") {

							if (window.parent != null
									&& window.parent != undefined) {
								window.parent.CloseEditWin("update",
										updateRes.errorMsg);
							}

						} else {
							$.messager.alert('提示', '修改失败');
						}
						
						
					});
			
		}
	});
		
	
	function BtnCancle(){
		
		if (window.parent != null && window.parent != undefined) {
			window.parent.CloseEditWin(null, null);
		}
		
	}
	</script>
	
	<script>
	
	//新增传感器
	function AddSensor(){
		
		var res = null;

		$.ajax({
			type : "GET",
			url : "../SensorRequest",
			/* data: "page=" + page + "&rows=" + rows + "&keyword=" + $('#hdKeyword').val(), */
			async : false,//设置同步
			data : {
				action : "AddSensor",
				
				SensorSpec:sensorSpec,
				SensorFactory:sensorFactory,
				Param1:$("#Param1").numberbox('getValue'),
				Param2:$("#Param2").numberbox('getValue'),
				Param3:$("#Param3").numberbox('getValue'),
				Param4:$("#Param4").numberbox('getValue'),
				Param5:$("#Param5").numberbox('getValue'),
				Param6:$("#Param6").numberbox('getValue'),
				Param7:$("#Param7").numberbox('getValue'),
				Param8:$("#Param8").numberbox('getValue'),
				SensorName:sensorName,
				SensorDesc:seneorDesc,
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
	
	//修改传感器
	function UpdateSensor(){
		
		var res = null;

		$.ajax({
			type : "GET",
			url : "../SensorRequest",
			/* data: "page=" + page + "&rows=" + rows + "&keyword=" + $('#hdKeyword').val(), */
			async : false,//设置同步
			data : {
				action : "UpdateSensor",
				autoid:nAutoID,
				SensorID:sensorID,
				SensorSpec:sensorSpec,
				SensorFactory:sensorFactory,
				Param1:$("#Param1").numberbox('getValue'),
				Param2:$("#Param2").numberbox('getValue'),
				Param3:$("#Param3").numberbox('getValue'),
				Param4:$("#Param4").numberbox('getValue'),
				Param5:$("#Param5").numberbox('getValue'),
				Param6:$("#Param6").numberbox('getValue'),
				Param7:$("#Param7").numberbox('getValue'),
				Param8:$("#Param8").numberbox('getValue'),
				SensorName:sensorName,
				SensorDesc:seneorDesc,
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