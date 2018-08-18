<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>监测项目详情</title>

<link rel="stylesheet"
	href="../Resource/bootstrap-3.3.7-dist/css/bootstrap.min.css">

<link rel="stylesheet" type="text/css"
	href="../Resource/jquery-easyui-1.5.4.2/themes/metro/easyui.css">
<link rel="stylesheet" type="text/css"
	href="../Resource/jquery-easyui-1.5.4.2/themes/icon.css">

<script src="../Resource/jquery-easyui-1.5.4.2/jquery.min.js"></script>
<script src="../Resource/jquery-easyui-1.5.4.2/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="../Resource/jquery-easyui-1.5.4.2/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="../Resource/jshelper/commonfun.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="../Resource/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>

<style type="text/css">
html {
	height: 100%
}

body {
	height: 100%;
	margin: 0px;
	padding: 0px
}

.datagrid-header .datagrid-cell span {
  font-size: 12px;
  font-weight:900;
}
</style>

</head>
<body>

	<table class="form_table" style="margin: 5px;">

		<tr>
			<th style="color: red;font-family:Arial;font-size:12px;font-weight:400;">监测项目:</th>
			<td><input id="monitorName" type="text" class="easyui-textbox "
				required="required" readonly="readonly" /></td>
			<th style="color: red;font-family:Arial;font-size:12px;font-weight:400;">监测方法:</th>
			<td><input id="monitorMethord" type="text"
				class="easyui-textbox " required="required" readonly="readonly" /></td>
		</tr>
		<tr>
			<th style="color: red;font-family:Arial;font-size:12px;font-weight:400;">测点数:</th>
			<td><input id="pointCount" type="text" class="easyui-textbox "
				required="required" readonly="readonly" /></td>
			<th style="color: red;font-family:Arial;font-size:12px;font-weight:400;">测点前缀:</th>
			<td><input id="pointPrefix" type="text" class="easyui-textbox "
				required="required" readonly="readonly" /></td>
		</tr>
		<tr>
			<th style="font-family:Arial;font-size:12px;font-weight:400;">测点起始编号:</th>
			<td><input id="pointStart" type="text" class="easyui-textbox "
				readonly="readonly" /></td>
			<th style="font-family:Arial;font-size:12px;font-weight:400;">测点结束编号:</th>
			<td><input id="pointEnd" type="text" class="easyui-textbox "
				readonly="readonly" /></td>
		</tr>

	</table>
	
	

	<!-- tabs -->

	<div class="easyui-tabs" style="width: 100%;">

		<!-- <div title="报警参数" style="padding: 10px">

			<div id="tb" style="height: auto">
				<a href="javascript:void(0)" class="easyui-linkbutton"
					data-options="iconCls:'icon-add',plain:true" onclick="append()">新增</a>
				<a href="javascript:void(0)" class="easyui-linkbutton"
					data-options="iconCls:'icon-remove',plain:true"
					onclick="removeit()">删除</a> <a href="javascript:void(0)"
					class="easyui-linkbutton"
					data-options="iconCls:'icon-save',plain:true" onclick="accept()">保存</a>
				<a href="javascript:void(0)" class="easyui-linkbutton"
					data-options="iconCls:'icon-undo',plain:true" onclick="reject()">Reject</a>
				<a href="javascript:void(0)" class="easyui-linkbutton"
					data-options="iconCls:'icon-search',plain:true"
					onclick="getChanges()">GetChanges</a>
			</div>

			<table id="tb_alarm" class="easyui-datagrid"
				style="width: 700px; height: 300px"
				data-options="rownumbers:true,singleSelect:true,url:'',method:'get',
							toolbar: '#tb',onClickCell: onClickCell,
                			onEndEdit: onEndEdit">
				<thead>
					<tr>
						<th data-options="field:'alarmType',width:80" rowspan="2">报警类型</th>
						<th colspan="3">累计变化</th>
						<th colspan="3">变化速率</th>
					</tr>
					<tr>
						<th data-options="field:'accumulate.earlyalarm',width:80,">预警值</th>
						<th data-options="field:'accumulate.alarm',width:80,">报警值</th>
						<th data-options="field:'accumulate.control',width:80,">控制值</th>
						<th data-options="field:'rate.earlyalarm',width:80,">预警值</th>
						<th data-options="field:'rate.alarm',width:80,">报警值</th>
						<th data-options="field:'rate.control',width:80,">控制值</th>
					</tr>
				</thead>
			</table>

			<script type="text/javascript">
				var editIndex = undefined;
				function endEditing() {
					if (editIndex == undefined) {
						return true
					}
					if ($('#tb_alarm').datagrid('validateRow', editIndex)) {
						$('#tb_alarm').datagrid('endEdit', editIndex);
						editIndex = undefined;
						return true;
					} else {
						return false;
					}
				}
				function onClickCell(index, field) {
					if (editIndex != index) {
						if (endEditing()) {
							$('#tb_alarm').datagrid('selectRow', index).datagrid(
									'beginEdit', index);
							var ed = $('#tb_alarm').datagrid('getEditor', {
								index : index,
								field : field
							});
							if (ed) {
								($(ed.target).data('textbox') ? $(ed.target)
										.textbox('textbox') : $(ed.target))
										.focus();
							}
							editIndex = index;
						} else {
							setTimeout(function() {
								$('#tb_alarm').datagrid('selectRow', editIndex);
							}, 0);
						}
					}
				}
				function onEndEdit(index, row) {
					var ed = $(this).datagrid('getEditor', {
						index : index,
						field : 'productid'
					});
					row.productname = $(ed.target).combobox('getText');
				}
				function append() {
					if (endEditing()) {
						$('#tb_alarm').datagrid('appendRow', {
							//status : 'P'
						});
						editIndex = $('#tb_alarm').datagrid('getRows').length - 1;
						$('#tb_alarm').datagrid('selectRow', editIndex).datagrid(
								'beginEdit', editIndex);
					}
				}
				function removeit() {
					if (editIndex == undefined) {
						return
					}
					$('#tb_alarm').datagrid('cancelEdit', editIndex).datagrid(
							'deleteRow', editIndex);
					editIndex = undefined;
				}
				function accept() {
					if (endEditing()) {
						$('#tb_alarm').datagrid('acceptChanges');
					}
				}
				function reject() {
					$('#tb_alarm').datagrid('rejectChanges');
					editIndex = undefined;
				}
				function getChanges() {
					var rows = $('#tb_alarm').datagrid('getChanges');
					alert(rows.length + ' rows are changed!');
				}
			</script>
		</div> -->

		<div title="测点参数" style="padding: 10px">
			<div id="pointtb" style="height: auto">
				
				<a href="javascript:void(0)" class="easyui-linkbutton"
					data-options="iconCls:'icon-add',plain:true"
					onclick="AddMonitorPoint()">新增测点</a>
			
				<!-- <a href="javascript:void(0)" class="easyui-linkbutton"
					data-options="iconCls:'icon-add',plain:true"
					onclick="appendpoint()">新增</a> <a href="javascript:void(0)"
					class="easyui-linkbutton"
					data-options="iconCls:'icon-remove',plain:true"
					onclick="removeitpoint()">删除</a> <a href="javascript:void(0)"
					class="easyui-linkbutton"
					data-options="iconCls:'icon-save',plain:true"
					onclick="acceptpoint()">保存</a> --> <!-- <a href="javascript:void(0)"
					class="easyui-linkbutton"
					data-options="iconCls:'icon-undo',plain:true"
					onclick="rejectpoint()">Reject</a> <a href="javascript:void(0)"
					class="easyui-linkbutton"
					data-options="iconCls:'icon-search',plain:true"
					onclick="getChangespoint()">GetChanges</a> <span>温馨提示：测点编辑框可以批量保存。编辑后请注意保存，为空默认不报警!可以不填.</span> -->
			</div>

			<table id="tb_point" class="easyui-datagrid"
				style="width: 700px; height: 300px">
				<!-- <thead>
					<tr>
						<th data-options="field:'pointName',width:80">测点</th>
						<th data-options="field:'pointX',width:80">X(m)</th>
						<th data-options="field:'pointY',width:80,">Y(m)</th>
						<th data-options="field:'pointDeep',width:80,">x(深或高)(m)</th>
						<th data-options="field:'pointAlarm',width:80">报警类型</th>
						<th data-options="field:'pointRemark',width:100,">备注</th>
					</tr>
				</thead> -->
			</table>
			
			

			<script type="text/javascript">
				var pointeditIndex = undefined;
				function pointendEditing() {
					if (pointeditIndex == undefined) {
						return true
					}
					if ($('#tb_point').datagrid('validateRow', pointeditIndex)) {
						$('#tb_point').datagrid('endEdit', pointeditIndex);
						pointeditIndex = undefined;
						return true;
					} else {
						return false;
					}
				}
				function onPointClickCell(index, field) {
					if (pointeditIndex != index) {
						if (pointendEditing()) {
							$('#tb_point').datagrid('selectRow', index).datagrid(
									'beginEdit', index);
							var ed = $('#tb_point').datagrid('getEditor', {
								index : index,
								field : field
							});
							if (ed) {
								($(ed.target).data('textbox') ? $(ed.target)
										.textbox('textbox') : $(ed.target))
										.focus();
							}
							pointeditIndex = index;
						} else {
							setTimeout(function() {
								$('#tb_point').datagrid('selectRow', pointeditIndex);
							}, 0);
						}
					}
				}
				function onPointEndEdit(index, row) {
					var ed = $(this).datagrid('getEditor', {
						index : index,
						field : 'productid'
					});
					//row.productname = $(ed.target).combobox('getText');
				}
				function appendpoint() {
					if (pointendEditing()) {
						$('#tb_point').datagrid('appendRow', {
							//status : 'P'
						});
						pointeditIndex = $('#tb_point').datagrid('getRows').length - 1;
						$('#tb_point').datagrid('selectRow', pointeditIndex).datagrid(
								'beginEdit', pointeditIndex);
					}
				}
				function removeitpoint() {
					if (pointeditIndex == undefined) {
						return
					}
					$('#tb_point').datagrid('cancelEdit', pointeditIndex).datagrid(
							'deleteRow', pointeditIndex);
					pointeditIndex = undefined;
				}
				function acceptpoint() {
					if (pointendEditing()) {
						$('#tb_point').datagrid('acceptChanges');
					}
				}
				function rejectpoint() {
					$('#tb_point').datagrid('rejectChanges');
					pointeditIndex = undefined;
				}
				function getChangespoint() {
					var rows = $('#tb_point').datagrid('getChanges');
					alert(rows.length + ' rows are changed!');
				}
			</script>


		</div>

	</div>
	
	<!-- 新增测点弹框引用 -->
	<div id="win"></div>
	<script>
		function WinCloseFun(){
			
			$('#tb_point').datagrid('reload');
			
			$('#win').window('close');
		}
	
	</script>
	

	<!-- 加载页面信息 -->
	<script>
	//获取对应的monitorid
	var monitorid = getQueryString("monitorid");

	var monitorName = getQueryString("monitorName"), monitorMethord = getQueryString("monitorMethord"), 
	monitorPointCount = getQueryString("monitorPointCount"), 
	monitorPointPrefix = getQueryString("monitorPointPrefix"), 
	monitorPointStartID = getQueryString("monitorPointStartID"), 
	monitorPointEndID = getQueryString("monitorPointEndID");
	
	console.log(monitorName);
	
	function LoadHtmlInfo(){
		
		if(monitorName!=null&&monitorName!=undefined){
			$('#monitorName').textbox('setValue',monitorName);
			$('#monitorMethord').textbox('setValue',monitorMethord);
			$('#pointCount').textbox('setValue',monitorPointCount);
			$('#pointPrefix').textbox('setValue',monitorPointPrefix);
			$('#pointStart').textbox('setValue',monitorPointStartID);
			$('#pointEnd').textbox('setValue',monitorPointEndID);
		}
		
	}
	
	$(function() {
		
		//加载页面信息
		
		LoadHtmlInfo();
		
		
		
		LoadPointTable();
	});
	
	
	function LoadPointTable(){
		$('#tb_point').datagrid({
			rownumbers:true,singleSelect:true,
			url:'../TstWebRequest/TstResquest?action=GetAllMonitorPointData',
			queryParams:{monitorName:monitorName},
			method:'get',
			toolbar: '#pointtb',onClickCell: onPointClickCell,
			onEndEdit: onPointEndEdit,
			
            columns:[[{field:'monitorPtName',title:'测点',width:80},

            {field:'pointX',title:'X(m)',width:80},

            {field:'pointY',title:'Y(m)',width:80},

            {field:'pointDeep',title:'x(深或高)(m)',width:80},

            {field:'pointAlarm',title:'报警类型',width:100},

            {field:'pointRemark',title:'备注',width:100}

            ]]
		});
		
		 //$("#tb_point").datagrid('reload'); 
	}
		
	</script>
	
	<script>
	//新增测点		
	function AddMonitorPoint(){
		
		$('#win').window({
			width:500,
		    height:300,
		    modal:true,
		    content:"<iframe scrolling=\"yes\" frameborder=\"0\" src=\"AddMonitorPointWin.jsp?action=add&"
		    		+"&monitorName="+ monitorName
			   	   	+"&monitorMethord="+ monitorMethord
		    		+"\" style=\"width: 100%; height:100%;\"></iframe>",
		    title:'添加测点',
			
		});
		
	}
	
	</script>

</body>
</html>