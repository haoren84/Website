/**
 * 字典监测项目类型js
 */


$(function() {
	
	$('#tb_monitortype').treegrid({
		url : '../DictionaryRequest?action=GetDicMonitorTreeGridData',
		idField : 'id',
		treeField : 'DicValue',
		columns : [ [ {
			field : 'DicValue',
			title : '监测类型名称',
			width : 200
		},{
			field : '_operate',
			title : '操作',
			formatter : formatOperMonitorDict,
			width : 100
		} ] ],
		rownumbers : true,
		method : 'get',
		singleSelect : true,
		onBeforeLoad : function(row, param) {
			if (!row) { // load top level rows
				param.DicParentID = 0; // set id=0, indicate to load new page rows
			}
		},
		onBeforeExpand : function(row) {
			//console.log(row);

			if (row.attributes) {
				$('#tb_monitortype').treegrid('options').queryParams = {
					DicParentID : row.attributes.autoid,
				};
			} 

		},
	});
	
});


//操作栏
function formatOperMonitorDict(val, row, index){
	
	if(row.attributes&&row.attributes.ParentID==0){
		return '<a href="#" rel="external nofollow" onclick="AddMonitorTypeItem('
		+ row.id
		+ ')">新增</a>';
	}
	
	return '<a href="#" rel="external nofollow" onclick="UpdateMonitorTypeItem('
	+ row.id
	+ ')">详情</a>|<a href="#" rel="external nofollow" onclick="DeleteMonitorTypeItem('
	+ row.id
	+ ')">删除</a>';
}

//新增监测方法
function AddMonitorTypeItem(index){
	$('#tb_monitortype').treegrid('select', index);

	var row = $('#tb_monitortype').treegrid('getSelected');
	
	$('#win_monitor')
	.window(
			{
				width : 500,
				height : 300,
				modal : true,
				content : "<iframe scrolling=\"yes\" frameborder=\"0\" src=\"EditMonitorTypeDic.jsp?action=add"
						+ "&autoid="
					    + row.attributes.autoid
					    + "&DicValue="
					    + row.attributes.DicValue
						+ "\" style=\"width: 100%; height:100%;\"></iframe>",
				title : '新增监测类型的方法',
				collapsible:false,
				minimizable:false,
				maximizable:false,
			});

	$('#win_monitor').window('open');
}

//修改监测方法
function UpdateMonitorTypeItem(index){
	$('#tb_monitortype').treegrid('select', index);

	var row = $('#tb_monitortype').treegrid('getSelected');
	
	$('#win_monitor')
	.window(
			{
				width : 500,
				height : 300,
				modal : true,
				content : "<iframe scrolling=\"yes\" frameborder=\"0\" src=\"EditMonitorTypeDic.jsp?action=update"
						+ "&autoid="
					    + row.attributes.autoid
					    + "&DicValue="
					    + row.attributes.DicValue
					    + "&ParentDic="
					    + row.attributes.ParentDic
					    + "&ParentID="
					    + row.attributes.ParentID
					    + "&isUsed="
					    + row.attributes.isUsed
						+ "\" style=\"width: 100%; height:100%;\"></iframe>",
				title : '修改监测类型的方法',
				collapsible:false,
				minimizable:false,
				maximizable:false,
			});

	$('#win_monitor').window('open');
}

//删除监测方法
function DeleteMonitorTypeItem(index){
	$('#tb_monitortype').treegrid('select', index);

	var row = $('#tb_monitortype').treegrid('getSelected');
	
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
								action : "DeleteDicMonitorInfo",
								DicID: row.attributes.autoid,
								ParentID:row.attributes.ParentID,
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
									
									$('#tb_monitortype').treegrid('reload');
								}
								
							}
						});
						
					}
				});
		
		
	}
}

//关闭窗口
function CloseMonitorDictWin(action, msg) {

	if (msg) {
		$.messager.alert('提示', msg);
		
		$('#tb_monitortype').treegrid('reload');
	}

	$('#win_monitor').window('close');
	
}

//刷新树结构
function ReflashTreeGrid(){
	$('#win_monitor').treegrid('reload');
}

//新增监测类型
function funAddMonitorType(){
	
	$('#win_monitor')
	.window(
			{
				width : 500,
				height : 300,
				modal : true,
				content : "<iframe scrolling=\"yes\" frameborder=\"0\" src=\"EditMonitorTypeDic.jsp?action=add"
						+ "&autoid="
					    + 1
					    + "&DicValue="
					    + "监测类型"
						+ "\" style=\"width: 100%; height:100%;\"></iframe>",
				title : '新增监测类型',
				collapsible:false,
				minimizable:false,
				maximizable:false,
			});

	$('#win_monitor').window('open');
	
}













































