/**
 * 关联信息的操作js
 */
// 采集仪的表格的操作栏目
function formatOperAcq(val, row, index) {
	return '<a href="#" rel="external nofollow" onclick="DetailInsAcq(' + index
			+ ')">详情</a>|<a href="#" rel="external nofollow" onclick="DeleteInsAcq('
			+ index + ')">删除</a>';
}

// 加载采集仪的表格数据
function LoadInstrumentAcqTable() {
	$('#tb_instrument_acq').datagrid({
		url : '../RelationInsRequest?action=GetAllInsAcqData',
		columns : [ [ {
			field : 'insFactoryID',
			title : '出厂编号',
			width : 100
		}, {
			field : 'insNetID',
			title : '网络ID',
			width : 100
		}, {
			field : 'serverAddr',
			title : '服务器地址',
			width : 100
		}, {
			field : 'serverPort',
			title : '服务器端口',
			width : 100
		}, {
			field : '_operate',
			title : '操作',
			formatter : formatOperAcq,
			width : 200
		} ] ],
		rownumbers : true,
		method : 'get',
		singleSelect : true,
	});
}

// 采集仪新增
function btnAddInsAcq() {
	$('#w')
			.window(
					{
						width : 500,
						height : 300,
						modal : true,
						content : "<iframe scrolling=\"yes\" frameborder=\"0\" src=\"AddInsAcq.jsp?action=add&"
								+ "\" style=\"width: 100%; height:100%;\"></iframe>",
						title : '新增采集仪',
						collapsible:false,
						minimizable:false,
						maximizable:false,
					});

	$('#w').window('open');
}

// 详情
function DetailInsAcq(index) {

	$('#tb_instrument_acq').datagrid('selectRow', index);

	var row = $('#tb_instrument_acq').datagrid('getSelected');

	if (row) {
		$('#w')
				.window(
						{
							width : 500,
							height : 300,
							modal : true,
							content : "<iframe scrolling=\"yes\" frameborder=\"0\" src=\"AddInsAcq.jsp?action=update&"
									+ "&nAutoID="
									+ row.id
									+ "&insFactoryID="
									+ row.insFactoryID
									+ "&insNetID="
									+ row.insNetID
									+ "&serverAddr="
									+ row.serverAddr
									+ "&serverPort="
									+ row.serverPort
									+ "\" style=\"width: 100%; height:100%;\"></iframe>",
							title : '采集仪详情',
							collapsible:false,
							minimizable:false,
							maximizable:false,
						});
		
		$('#w').window('open');
	}
}

// 删除
function DeleteInsAcq(index) {

	$('#tb_instrument_acq').datagrid('selectRow', index);

	var row = $('#tb_instrument_acq').datagrid('getSelected');
	
	if(row){
		$.messager.confirm('提示', '确认删除该采集仪?',
				function(r) {
					if (r) {
						
						$.ajax({
							type : "GET",
							url : "../RelationInsRequest",
							/* data: "page=" + page + "&rows=" + rows + "&keyword=" + $('#hdKeyword').val(), */
							async : false,//设置同步
							data : {
								action : "DeleteInstrumentAcq",
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
									
									$('#tb_instrument_acq').datagrid('reload');
								}
								
							}
						});
						
					}
				});
	}
}

// 关闭窗口
function CloseInsAcqWin(action, msg) {

	if (msg) {
		$.messager.alert('提示', msg);
		
		$('#tb_instrument_acq').datagrid('reload');
	}

	$('#w').window('close');
	
}
