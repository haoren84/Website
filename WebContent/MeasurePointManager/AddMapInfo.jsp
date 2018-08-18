<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>设置匹配信息</title>

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
		<div data-options="region:'west',split:true" title="终端、传感器信息"
			style="width: 45%; height: 100%;">

			<table class="form_table" style="margin: 0px;">
				<tr>
					<th>终端仪器出厂编号</th>
					<td><input id="insFactoryID" placeholder="请选择终端出厂编号"
						class="easyui-textbox " type="text" required="required" /></td>
				</tr>
				<tr>
					<th>通道ID</th>
					<td><input id="chnID" placeholder="请选择终端通道ID"
						class="easyui-textbox " type="text" required="required"  /></td>
				</tr>
				<tr>
					<th>传感器</th>
					<td><input name="SensorID" placeholder="请选择传感器ID"
						id="SensorID" class="easyui-textbox " required="required" 
						type="text" /></td>
				</tr>
			</table>
		</div>

		<div data-options="region:'center',title:'工程监测项目所包含的测点信息',">
			<table id="monitorPointDATA"></table>
		</div>

		<div data-options="region:'south',border:false"
			style="text-align: right; padding: 5px;border-color: #dddddd #95B8E7 #95B8E7 #95B8E7; background: #F4F4F4;">
			<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'"
				href="javascript:void(0)"  id="btnOK" onclick="BtnAddFun()" style="width: 90px">确认</a>
			<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'"
				href="javascript:void(0)" onclick="BtnCancleFun()"
				style="width: 90px">取消</a>
		</div>
	</div>

	<script>
	
	var insFactoryID,chnID,SensorID,monitorName,monitorPtName;

	$(function() {
		
		var action = getQueryString("action");
		
		if(action=="update"){
			
			insFactoryID=getQueryString("insFactoryID");
			chnID=getQueryString("chnID");
			SensorID=getQueryString("SensorID");
			monitorName=getQueryString("monitorName");
			monitorPtName=getQueryString("monitorPtName");
			
			$('#btnOK').css('display', 'none');
		}
		
		//加载出厂编号
		
		$('#insFactoryID').combobox({
			
			url : "../RelationInsRequest?action=GetInsTerminalComboboxData",
			valueField:'insFactoryID',
		    textField:'insFactoryID',
		    onSelect:function(record){
		    	console.log(record);
		    	
		    	//选中后给通道ID加载下拉数据
		    	
		    	$('#chnID').combobox({
		    		url : "../RelationInsRequest?action=GetInsTerminalChnModelComboboxData&insFactoryID="+record.insFactoryID,
		    		valueField:'chnID',
				    textField:'chnIDName',
				    onLoadSuccess:function(){
				    	if(action=="update"){
				    		$('#chnID').combobox('setValue',chnID);
				    	}
				    },
		    	});
		    	
		    },
		    onLoadSuccess:function(){
		    	if(action=="update"){
		    		$('#insFactoryID').combobox('setValue',insFactoryID);
		    	}
		    },
			
			
		});
		
		//传感器下拉数据加载
		$('#SensorID').combobox({
			url : "../RelationInsRequest?action=GetSeneorComboboxData",
			valueField:'SensorID',
		    textField:'SensorID',
		    onLoadSuccess:function(){
		    	if(action=="update"){
		    		$('#SensorID').combobox('setValue',SensorID);
		    	}
		    },
		});
		
		//测点数据加载
		$('#monitorPointDATA').treegrid({
			
			url : '../RelationInsRequest?action=GetRelationAllMPoints',
			//url:'../Resource/jquery-easyui-1.5.4.2/demo/treegrid/treegrid_data1.json',
			method : 'get',
			idField : 'id',
			treeField : 'name',
			parentField : '_parentId',
			columns : [ [ {
				field : 'name',
				title : '名称',
				width : 180
			}, {
				field : 'type',
				title : '类型',
				width : 80
			} ] ],
			rownumbers : true,
			singleSelect : true,
			animate : true,
			collapsible : true,
			border : false,
			striped : true,
			cascadeCheck : true,
			deepCascadeCheck : true,
			onBeforeSelect:function(node){
				//console.log(node);
				
				if(action!="update"&&node&&node.isselect&&node.isselect=="true"){
					$.messager.alert('提示', '该测点已经建立过关系');
					return false;
				}
			},
			onLoadSuccess : function(row, data) {
				if(action=="update"){
					var ndataLength = data.rows.length;
					
					for (var i = 0; i < ndataLength; i++) {
						if (data.rows[i].isselect 
							&& data.rows[i].monitorname
							&& data.rows[i].isselect == "true"
							&& data.rows[i].monitorname==monitorName
							&& data.rows[i].name==monitorPtName) {
							$('#monitorPointDATA')
									.treegrid('select', data.rows[i].id);
							break;
						}
					}
				}
				
			},
		
			
		});
		

	});
	
	//获取页面数据
	function GetHtmlInfo(){
		insFactoryID=$('#insFactoryID').combobox('getValue');
		chnID=$('#chnID').combobox('getValue');
		SensorID=$('#SensorID').combobox('getValue');
		var selectnode=$('#monitorPointDATA').treegrid('getSelected');
		
		monitorName=selectnode.monitorname;
		monitorPtName=selectnode.name;
	}
	
	//取消
	function BtnCancleFun(){
		if (window.parent != null && window.parent != undefined) {
			window.parent.CloseMapInfoWin(null,null);
		}
	}
	
	//新增
	function BtnAddFun(){
		
		GetHtmlInfo();
		
		
		var addRes=JSON.parse(AddMapInfoFun());
		 
		 if(addRes&&addRes.result=="true"){
			 
			 if (window.parent != null && window.parent != undefined) {
				 window.parent.CloseMapInfoWin("add",addRes.errorMsg);
			 }
			 
		 }else{
			 $.messager.alert('提示', '新增失败');
		 }
		
		
	}
	
	//新增匹配关系
    function AddMapInfoFun(){
   	 
   	 var res=null;
   	 
   	 $.ajax({
				type : "GET",
				url : "../RelationInsRequest",
				/* data: "page=" + page + "&rows=" + rows + "&keyword=" + $('#hdKeyword').val(), */
				async : false,//设置同步
				data : {
					action : "CreatePointChnSensorRelation",
					insFactoryID:insFactoryID,
					chnID:chnID,
					SensorID:SensorID,
					monitorName:monitorName,
					monitorPtName:monitorPtName,
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