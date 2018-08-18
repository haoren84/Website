<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>字典信息</title>

<link rel="stylesheet" type="text/css"
	href="../Resource/jquery-easyui-1.5.4.2/themes/gray/easyui.css">
<link rel="stylesheet" type="text/css"
	href="../Resource/jquery-easyui-1.5.4.2/themes/icon.css">

<script src="../Resource/jquery-easyui-1.5.4.2/jquery.min.js"></script>
<script src="../Resource/jquery-easyui-1.5.4.2/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="../Resource/jquery-easyui-1.5.4.2/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="../Resource/jshelper/commonfun.js"></script>
<script type="text/javascript" src="../Resource/jshelper/DicMonitorTypejs.js"></script>
<script type="text/javascript" src="../Resource/jshelper/DicMeasureTypejs.js"></script>
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
<div class="easyui-panel" title="字典信息"
		style="width: 100%; height: 100%;">
		<div id="tab_dictionary" class="easyui-tabs"
			style="width: 100%; height: 100%;"
			data-options="border:false,"
			>
			
			<div title="工程类型">
				
				<a href="#" class="easyui-linkbutton" onclick="funAddPrj()"
						style="margin: 20px;">新增工程类型</a>
				
				<table id="tb_prjtype"></table>
				
			</div>
			
			<div title="监测项目类型">
			
				<a href="#" class="easyui-linkbutton" onclick="funAddMonitorType()"
						style="margin: 20px;">新增监测项目类型</a>
				
				<table id="tb_monitortype"></table>
				
			</div>
			
			<div title="测量类型">
			
				<a href="#" class="easyui-linkbutton" onclick="funAddMeasure()"
						style="margin: 20px;">新增测量类型</a>
				
				<table id="tb_meauretype"></table>
			
			</div>
			
			
		</div>
		
</div>

<!-- 工程类型弹框 -->
<div id="win_prj" class="easyui-window" 
	data-options="modal:true,closed:true,iconCls:'icon-save',title:'新建工程',collapsible:false,minimizable:false,maximizable:false,"
	style="width: 750px; height: 400px; "
>
	<div class="easyui-layout" data-options="fit:true">
		
		<div data-options="region:'center'" style="padding: 10px;">
				
				<div id="dlgfm" style="width: 100%">
				
					<form id="fm" method="post" novalidate
					style="margin: 0; padding: 20px 50px">
					
					<div style="margin-bottom: 10px">
						<input id="prjtypename" class="easyui-textbox" required="true"
							label="工程类型名称:" style="width: 100%">
					</div>
					
					</form>
					</div>
					</div>
	<div data-options="region:'south',border:false"
			style="text-align: right; padding: 5px; border-color: #dddddd #95B8E7 #95B8E7 #95B8E7; background: #F4F4F4;">
			<a href="javascript:void(0)" class="easyui-linkbutton c6"
				iconCls="icon-ok" id="btnPrjTypeOK" style="width: 90px">保存</a> <a
				href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-cancel" onclick="CloseEditPrjWin()" style="width: 90px">取消</a>

			</div>
	
	</div>
	

</div>

	<script>
	
	var prjState='add';
	
	var updateRow;//记录修改行的信息
	
	$(function() {
		
		$('#tb_prjtype').datagrid({
			url : '../DictionaryRequest?action=GetDicPrjType',
			columns : [ [ {
				field : 'dicvalue',
				title : '工程类型',
				width : 100
			}, {
				field : '_operate',
				title : '操作',
				formatter : prjformatOper,
				width : 200
			} ] ],
			rownumbers : true,
			method : 'get',
			singleSelect : true,
			
		});
		
	});
	
	//工程类型的操作栏
	function prjformatOper(val, row, index) {
		return '<a href="#" rel="external nofollow" onclick="funUpdatePrj(' + index
		+ ')">修改</a>|<a href="#" rel="external nofollow" onclick="funDeletePrj('
				+ index
				+ ')">删除</a>';
	}
	
	
	//新增工程类型弹框
	function funAddPrj(){
		
		prjState='add';
		
		$('#prjtypename').textbox('setValue','');
		
		$('#win_prj').window({title:'新增工程类型',});
		
		$('#win_prj').window('open');
	}
	
	//触发保存事件
	$('#btnPrjTypeOK').click(function() {
		
		if(prjState=='add'){
			$.ajax({
				type : "GET",
				url : "../DictionaryRequest",
				/* data: "page=" + page + "&rows=" + rows + "&keyword=" + $('#hdKeyword').val(), */
				async : false,//设置同步
				data : {
					action : "CreateDicInfo",
					DicValue : $('#prjtypename').textbox('getValue'),
					isUsed : '1',
					ParentDic : '工程类型',
					ParentID : '1',
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					//alert(textStatus);
					//$.messager.progress('close');
					$.messager.alert('提示', 'error');
				},
				success : function(data) {
					var res = JSON.parse(data);
					
					if(res&&res.result){
						if(res.result=='true'){
							
							$.messager.alert('提示', '新增成功');
							
							$('#tb_prjtype').datagrid('reload');
							
						}else if(res.result=='false'){
							
							$.messager.alert('提示', res.errorMsg);
						}
						
						$('#win_prj').window('close');
						
					}else{
						$.messager.alert('提示', '新增失败');
					}
					
				}
			});
		}else if(prjState=='update'){
			//console.log(updateRow);
			
			$.ajax({
				type : "GET",
				url : "../DictionaryRequest",
				/* data: "page=" + page + "&rows=" + rows + "&keyword=" + $('#hdKeyword').val(), */
				async : false,//设置同步
				data : {
					action : "UpdateDicInfo",
					DicID : updateRow.autoid,
					DicValue : $('#prjtypename').textbox('getValue'),
					isUsed : '1',
					ParentDic : updateRow.parentvalue,
					ParentID : updateRow.parentid,
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					//alert(textStatus);
					//$.messager.progress('close');
					$.messager.alert('提示', 'error');
				},
				success : function(data) {
					var res = JSON.parse(data);
					
					if(res&&res.result){
						if(res.result=='true'){
							
							$.messager.alert('提示', '修改成功');
							
							$('#tb_prjtype').datagrid('reload');
							
						}else if(res.result=='false'){
							
							$.messager.alert('提示', res.errorMsg);
						}
						
						$('#win_prj').window('close');
						
					}else{
						$.messager.alert('提示', '修改失败');
					}
				}
			});
			
		}
		
		
	});
	
	//关闭弹框
	function CloseEditPrjWin(){
		$('#win_prj').window('close');
	}
	
	//修改工程类型
	function funUpdatePrj(index){
		
		if(index==null||index==undefined){
			return;
		}
		
		$('#tb_prjtype').datagrid('selectRow', index);
		var row = $('#tb_prjtype').datagrid('getSelected');
		
		if(row){
			
			prjState='update';
			
			updateRow=row;
			
			$('#prjtypename').textbox('setValue',row.dicvalue);
			
			$('#win_prj').window({title:'修改工程类型',});
			
			$('#win_prj').window('open');
			
		}
		
		
		
	}
	
	//删除工程类型
	function funDeletePrj(index){
		
		if(index==null||index==undefined){
			return;
		}
		
		$('#tb_prjtype').datagrid('selectRow', index);
		var row = $('#tb_prjtype').datagrid('getSelected');
		
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
									DicID: row.autoid,
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
										
										$('#tb_prjtype').datagrid('reload');
									}
									
								}
							});
							
						}
					});
			
			
		}
		
		
	}
	
	
	
	
	
	
	
	</script>


<!-- 监测项目类型弹框  -->
<div id="win_monitor"></div>




</body>
</html>