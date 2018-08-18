/**
 * 关联信息的操作js
 */


//传感器的表格的操作栏目
function formatOperSeneor(val, row, index) {
	return '<a href="#" rel="external nofollow" onclick="DetailSeneor('
			+ index
			+ ')">详情</a>|<a href="#" rel="external nofollow" onclick="DeleteSeneor('
			+ index
			+ ')">删除</a>';
}

//加载传感器的表格数据
function LoadSeneorTable(){
	$('#tb_seneor').datagrid({
		url : '../RelationInsRequest?action=GetAllSeneorData',
		columns : [ [ {
			field : 'SensorID',
			title : '传感器名称',
			width : 100
		}, {
			field : 'SensorMeasureType',
			title : '传感器测量类型',
			width : 100
		}, {
			field : 'SensorSpec',
			title : '传感器规格',
			width : 100
		}, {
			field : 'SensorFactory',
			title : '传感器生产厂家',
			width : 100
		},{
			field : 'Param1',
			title : '传感器指标1',
			width : 100
		},{
			field : 'Param2',
			title : '传感器指标2',
			width : 100
		},{
			field : 'Param3',
			title : '传感器指标3',
			width : 100
		},{
			field : 'Param4',
			title : '传感器指标4',
			width : 100
		},{
			field : 'Param5',
			title : '传感器指标5',
			width : 100
		},{
			field : 'Param6',
			title : '传感器指标6',
			width : 100
		},{
			field : 'Param7',
			title : '传感器指标7',
			width : 100
		},{
			field : 'Param8',
			title : '传感器指标8',
			width : 100
		}, {
			field : '_operate',
			title : '操作',
			formatter : formatOperSeneor,
			width : 100
		} ] ],
		rownumbers : true,
		method : 'get',
		singleSelect : true,
	});
}

//新增传感器
function btnAddSeneor(){
	$('#w')
	.window(
			{
				width : 500,
				height : 600,
				modal : true,
				content : "<iframe scrolling=\"yes\" frameborder=\"0\" src=\"AddSeneor.jsp?action=add&"
						+ "\" style=\"width: 100%; height:100%;\"></iframe>",
				title : '新增传感器',
				collapsible:false,
				minimizable:false,
				maximizable:false,
			});

	$('#w').window('open');
}

//详情
function DetailSeneor(index) {

	$('#tb_seneor').datagrid('selectRow', index);

	var row = $('#tb_seneor').datagrid('getSelected');

	if (row) {
		$('#w')
				.window(
						{
							width : 500,
							height : 600,
							modal : true,
							content : "<iframe scrolling=\"yes\" frameborder=\"0\" src=\"AddSeneor.jsp?action=update&"
									+ "&nAutoID="
									+ row.id
									+ "&SensorID="
									+ row.SensorID
									+ "&SensorMeasureType="
									+ row.SensorMeasureType
									+ "&SensorSpec="
									+ row.SensorSpec
									+ "&SensorFactory="
									+ row.SensorFactory
									+ "&Param1="
									+ row.Param1
									+ "&Param2="
									+ row.Param2
									+ "&Param3="
									+ row.Param3
									+ "&Param4="
									+ row.Param4
									+ "&Param5="
									+ row.Param5
									+ "&Param6="
									+ row.Param6
									+ "&Param7="
									+ row.Param7
									+ "&Param8="
									+ row.Param8
									+ "\" style=\"width: 100%; height:100%;\"></iframe>",
							title : '新增采集仪',
							collapsible:false,
							minimizable:false,
							maximizable:false,
						});
		
		$('#w').window('open');
	}
}

//删除
function DeleteSeneor(index) {

	$('#tb_seneor').datagrid('selectRow', index);

	var row = $('#tb_seneor').datagrid('getSelected');
	
	if(row){
		$.messager.confirm('提示', '确认删除该传感器?',
				function(r) {
					if (r) {
						
						$.ajax({
							type : "GET",
							url : "../RelationInsRequest",
							/* data: "page=" + page + "&rows=" + rows + "&keyword=" + $('#hdKeyword').val(), */
							async : false,//设置同步
							data : {
								action : "DeleteSeneor",
								nAutoID: row.id,
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
									
									$('#tb_seneor').datagrid('reload');
								}
								
							}
						});
						
					}
				});
	}
}

// 关闭窗口
function CloseSeneorWin(action, msg) {

	if (msg) {
		$.messager.alert('提示', msg);
		
		$('#tb_seneor').datagrid('reload');
	}

	$('#w').window('close');
	
}




