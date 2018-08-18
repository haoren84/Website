/**
 * 关联信息的操作js
 */


//终端的表格的操作栏目
function formatOperTerminal(val, row, index) {
	return '<a href="#" rel="external nofollow" onclick="DetailInsTerminal('
			+ index
			+ ')">详情</a>|<a href="#" rel="external nofollow" onclick="DeleteInsTerminal('
			+ index
			+ ')">删除</a>';
}

//加载终端的表格数据
function LoadInstrumentTerminalTable(){
	$('#tb_instrument_terminal').datagrid({
		url : '../RelationInsRequest?action=GetAllInsTerminalData',
		columns : [ [ {
			field : 'insFactoryID',
			title : '出厂编号',
			width : 100
		}, {
			field : 'insType',
			title : '仪器类型',
			width : 100
		}, {
			field : 'insChnCount',
			title : '通道个数',
			width : 100
		}, {
			field : 'insID',
			title : '机号',
			width : 100
		}, {
			field : '_operate',
			title : '操作',
			formatter : formatOperTerminal,
			width : 200
		} ] ],
		rownumbers : true,
		method : 'get',
		singleSelect : true,
	});
}

//新增终端
function btnAddTerminal(){
	$('#w')
	.window(
			{
				width : 500,
				height : 300,
				modal : true,
				content : "<iframe scrolling=\"yes\" frameborder=\"0\" src=\"AddInsTerminal.jsp?action=add"
						+ "\" style=\"width: 100%; height:100%;\"></iframe>",
				title : '新增终端',
				collapsible:false,
				minimizable:false,
				maximizable:false,
			});

	$('#w').window('open');
}

//终端详情
function DetailInsTerminal(index) {
	
	$('#tb_instrument_terminal').datagrid('selectRow', index);

	var row = $('#tb_instrument_terminal').datagrid('getSelected');
	
	if(row){
		$('#w')
		.window(
				{
					width : 500,
					height : 300,
					modal : true,
					content : "<iframe scrolling=\"yes\" frameborder=\"0\" src=\"AddInsTerminal.jsp?action=update&"
							+ "&nAutoID="
							+ row.id
							+ "&insFactoryID="
							+ row.insFactoryID
							+ "&insType="
							+ row.insType
							+ "&insChnCount="
							+ row.insChnCount
							+ "&insID="
							+ row.insID
							+ "\" style=\"width: 100%; height:100%;\"></iframe>",
					title : '终端详情',
					collapsible:false,
					minimizable:false,
					maximizable:false,
				});

		$('#w').window('open');
	}
	
}

//删除终端
function DeleteInsTerminal(index) {
	
	$('#tb_instrument_terminal').datagrid('selectRow', index);

	var row = $('#tb_instrument_terminal').datagrid('getSelected');
	
	if(row){
		
		$.messager.confirm('提示', '确认删除该终端?',
				function(r) {
					if (r) {
						
						$.ajax({
							type : "GET",
							url : "../RelationInsRequest",
							/* data: "page=" + page + "&rows=" + rows + "&keyword=" + $('#hdKeyword').val(), */
							async : false,//设置同步
							data : {
								action : "DeleteInsTerminal",
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
									
									$('#tb_instrument_terminal').datagrid('reload');
								}
								
							}
						});
						
					}
		})
	}
}

//关闭窗口
function CloseInsTerminalWin(action, msg) {

	if (msg) {
		$.messager.alert('提示', msg);
		
		$('#tb_instrument_terminal').datagrid('reload');
	}

	$('#w').window('close');
	
	
}




