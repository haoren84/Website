/**
 * 字典-测量类型
 */

$(function() {
	
	$('#tb_meauretype').datagrid({
		
		url : '../DictionaryRequest?action=GetDicMeasureType',
		columns : [ [ {
			field : 'dicvalue',
			title : '测量类型',
			width : 100
		}, {
			field : '_operate',
			title : '操作',
			formatter : measureformatOper,
			width : 200
		} ] ],
		rownumbers : true,
		method : 'get',
		singleSelect : true,
		
	});
	
	
});

//测量类型的操作栏
function measureformatOper(val, row, index) {
	return '<a href="#" rel="external nofollow" onclick="funUpdateMeasure(' + index
	+ ')">修改</a>|<a href="#" rel="external nofollow" onclick="funDeleteMeasure('
			+ index
			+ ')">删除</a>';
}

//新增测量类型
function funAddMeasure(){
	
	$('#win_monitor')
	.window(
			{
				width : 500,
				height : 300,
				modal : true,
				content : "<iframe scrolling=\"yes\" frameborder=\"0\" src=\"EditMeasureTypeDic.jsp?action=add"
						+ "&autoid="
					    + 2
					    + "&DicValue="
					    + "测量类型"
						+ "\" style=\"width: 100%; height:100%;\"></iframe>",
				title : '新增测量类型',
				collapsible:false,
				minimizable:false,
				maximizable:false,
			});

	$('#win_monitor').window('open');
	
}

//修改测量类型
function funUpdateMeasure(index){
	if(index==null||index==undefined){
		return;
	}
	
	$('#tb_meauretype').datagrid('selectRow', index);
	var row = $('#tb_meauretype').datagrid('getSelected');
	
	if(row){
		
		$('#win_monitor')
		.window(
				{
					width : 500,
					height : 300,
					modal : true,
					content : "<iframe scrolling=\"yes\" frameborder=\"0\" src=\"EditMeasureTypeDic.jsp?action=update"
							+ "&autoid="
						    + row.autoid
						    + "&DicValue="
						    + row.dicvalue
						    + "&ParentDic="
						    + row.parentvalue
						    + "&ParentID="
						    + row.parentid
						    + "&isUsed="
						    + 1
							+ "\" style=\"width: 100%; height:100%;\"></iframe>",
					title : '修改测量类型',
					collapsible:false,
					minimizable:false,
					maximizable:false,
				});

		$('#win_monitor').window('open');
		
	}
}

function funDeleteMeasure(index){
	if(index==null||index==undefined){
		return;
	}
	
	$('#tb_meauretype').datagrid('selectRow', index);
	var row = $('#tb_meauretype').datagrid('getSelected');
	
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
									
									$('#tb_meauretype').datagrid('reload');
								}
								
							}
						});
						
					}
				});
		
	}
}

//关闭窗口
function CloseMeasureDictWin(action, msg) {

	if (msg) {
		$.messager.alert('提示', msg);
		
		$('#tb_meauretype').datagrid('reload');
	}

	$('#win_monitor').window('close');
	
}

