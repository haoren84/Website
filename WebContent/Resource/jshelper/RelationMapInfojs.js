/**
 * 匹配关系的操作
 */


//匹配信息的操作栏
function formatOperMapInfo(val, row, index) {
	return '<a href="#" rel="external nofollow" onclick="DetailMapInfo('
			+ index
			+ ')">详情</a>|<a href="#" rel="external nofollow" onclick="DeleteMapInfo('
			+ index
			+ ')">删除</a>';
}

//加载匹配信息
function LoadMapInfoTable(){
	
	$('#tb_mapinfo').datagrid({
		url : '../RelationInsRequest?action=GetAllPointChnSensorRelationModel',
		columns : [ [ {
			field : 'insFactoryID',
			title : '终端出厂编号',
			width : 100
		}, {
			field : 'chnIDName',
			title : '通道ID',
			width : 100
		}, {
			field : 'SensorID',
			title : '传感器',
			width : 100
		}, {
			field : 'monitorName',
			title : '监测项目',
			width : 100
		},{
			field : 'monitorPtName',
			title : '测点名称',
			width : 100
		}, {
			field : '_operate',
			title : '操作',
			formatter : formatOperMapInfo,
			width : 200
		} ] ],
		rownumbers : true,
		method : 'get',
		singleSelect : true,
	});
	
}

//匹配关系新增
function btnAddMapInfo() {
	
	$('#w')
	.window(
			{
				width : 800,
				height : 600,
				modal : true,
				content : "<iframe scrolling=\"yes\" frameborder=\"0\" src=\"AddMapInfo.jsp?action=add&"
						+ "\" style=\"width: 100%; height:100%;\"></iframe>",
				title : '新增匹配关系',
				collapsible:false,
				minimizable:false,
				maximizable:false,
			});

	$('#w').window('open');	
}


//详情
function DetailMapInfo(index) {
	
	$('#tb_mapinfo').datagrid('selectRow', index);

	var row = $('#tb_mapinfo').datagrid('getSelected');
	
	if (row) {
		$('#w')
				.window(
						{
							width : 800,
							height : 600,
							modal : true,
							content : "<iframe scrolling=\"yes\" frameborder=\"0\" src=\"AddMapInfo.jsp?action=update&"
									+ "&nAutoID="
									+ row.id
									+ "&insFactoryID="
									+ row.insFactoryID
									+ "&chnID="
									+ row.chnID
									+ "&SensorID="
									+ row.SensorID
									+ "&monitorName="
									+ row.monitorName
									+ "&monitorPtName="
									+ row.monitorPtName
									+ "\" style=\"width: 100%; height:100%;\"></iframe>",
							title : '匹配关系详情',
							collapsible:false,
							minimizable:false,
							maximizable:false,
						});
		
		$('#w').window('open');
	}
}

//删除
function DeleteMapInfo(index) {
	
	$('#tb_mapinfo').datagrid('selectRow', index);

	var row = $('#tb_mapinfo').datagrid('getSelected');
	
	if(row){
		$.messager.confirm('提示', '确认删除该匹配信息?',
				function(r) {
					if (r) {
						
						$.ajax({
							type : "GET",
							url : "../RelationInsRequest",
							/* data: "page=" + page + "&rows=" + rows + "&keyword=" + $('#hdKeyword').val(), */
							async : false,//设置同步
							data : {
								action : "DeletePointChnSensorRelationByID",
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
									
									$('#tb_mapinfo').datagrid('reload');
								}
								
							}
						});
						
					}
				});
	}
}

//关闭窗口
function CloseMapInfoWin(action, msg) {

	if (msg) {
		$.messager.alert('提示', msg);
		
		$('#tb_mapinfo').datagrid('reload');
	}

	$('#w').window('close');
	
}


