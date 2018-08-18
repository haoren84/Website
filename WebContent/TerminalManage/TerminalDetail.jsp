<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>采集仪概况</title>

<link rel="stylesheet" type="text/css"
	href="../Resource/jquery-easyui-1.5.4.2/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="../Resource/jquery-easyui-1.5.4.2/themes/icon.css">

<script src="../Resource/jquery-easyui-1.5.4.2/jquery.min.js"></script>
<script src="../Resource/jquery-easyui-1.5.4.2/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="../Resource/jquery-easyui-1.5.4.2/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="../Resource/jshelper/commonfun.js"></script>
<script type="text/javascript" src="../Resource/jshelper/RelationAcqjs.js"></script>
<script type="text/javascript" src="../Resource/jshelper/RelationTerminaljs.js"></script>
<script type="text/javascript" src="../Resource/jshelper/RelationSeneorjs.js"></script>
<script type="text/javascript" src="../Resource/jshelper/RelationMapInfojs.js?id=111"></script>

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

#search input{
    		margin-left: 5px;
    		margin-right: 20px;
    	}
</style>


</head>
<body>
<div id="titlepanel" class="easyui-panel" title="采集仪概况"
		style="width: 100%; height: 100%;" data-options="border:false,">
		<!-- 筛选区域 -->
		<div class="easyui-accordion" style="width: 100%; height: 80px;"
			data-options="border:false,"
		>
			<div title="筛选条件"
				data-options="iconCls:'icon-search',collapsed:false,collapsible:false"
				style="padding: 10px;">
				 <input id="cc_ternimalname"  class="easyui-textbox" label="终端名称：" labelPosition="left" style="width: 200px;">
            	 <input id="cc_measuretype"  class="easyui-textbox" label="终端类型：" labelPosition="left" style="width: 200px;">
                 <a id="btn_search" class="easyui-linkbutton" data-options="iconCls:'icon-search'" style="width: 80px">搜索</a>
			</div>
		</div>
		
		<!-- 数据显示区域-->
		<div id="ac_datadisplay" class="easyui-accordion" style="width: 100%;" data-options="border:false,">
			
			<div title="查看"
				data-options="iconCls:'icon-large-chart',collapsed:false,collapsible:false,border:false,"
				style="padding: 0px;width: 100%;height:550px;border:none;" >
				<table id="tb_terminal" style="height:500px;border:none;">
                    
            	</table>
			</div>
		</div>
</div>

	<!-- 弹框加载 -->
	<div id="w"></div>


 <script>
        
        
        
        $(function() {
        	
        	/* //采集仪下拉框数据
        	$('#cc_ternimalname').combobox({
        		
        		url : '../TerminalRequest?action=GetTerminalNameComboData',
        		valueField:'id',
				textField:'text',
				onLoadSuccess:function(none){
					if(none&&none.length){
						$('#cc_ternimalname').combobox('select',none[0].id);
					}
					
				}
        		
        	});
        	
        	//类型下拉框数据
			$('#cc_measuretype').combobox({
        		
				url : "../DictionaryComboRequest?action=GetMeasureTypeComboWithAll",
				valueField: 'id',
	            textField: 'dicvalue',
				onLoadSuccess:function(none){
					if(none&&none.length){
						$('#cc_measuretype').combobox('select',none[0].id);
						//加载表格数据
			        	LoadTable();
					}
					
				}
        		
        	}); */
        	
        	//按钮触发
        	$('#btn_search').bind('click', function(){
				
				LoadTable();
		    });
        	
        	
        	
        	LoadTable();
        });
        
        //表格的工具栏
        var tb_termianl_toolbar=[
        		{
					text : '新增',
					iconCls : 'icon-add',
					handler : function() {
					//var objSelect = $('#tb_terminal').datagrid().datagrid('getSelected'); 
						 
					//console.log(objSelect);
					 //MonitorTable_AddMonitorPointBtn();
					 
						 AddTerminal();
								
					}
				},
				{
					text : '详情',
					iconCls : 'icon-search',
					handler : function() {
						//alert('search');
						UpdateTerminal();
					}
				},
				{
					text : '删除',
					iconCls : 'icon-remove',
					handler : function() {
						/* var objSelect = $('#tbmonitor').datagrid()
								.datagrid('getSelected'); */
						DeleteTerminal();
					}
				}
        ];
        
        //加载表格数据
        function LoadTable(){
        	
        	var terminalName=$('#cc_ternimalname').textbox('getValue');
        	var terminalType=$('#cc_measuretype').textbox('getValue');
        	
			$('#tb_terminal').datagrid({
        		
				url:"../InsTerminalRequest",
				queryParams: {
	                action: 'GetTerminalPageData',
	                terminalName:terminalName,
	                terminalType:terminalType,
	            },
				method:'get',
				toolbar:tb_termianl_toolbar,
				rownumbers:true,
				singleSelect:true,
				pagination:true,
				fitColumns:true,
				columns : [ [ {
					field : 'insFactoryID',
					title : '终端出厂编号',
					width : 200
				}, {
					field : 'terminalName',
					title : '终端名称',
					width : 100
				}, {
					field : 'terminalType',
					title : '终端类型',
					width : 100
				}, {
					field : 'terminalDesc',
					title : '终端描述',
					width : 100
				}, {
					field : 'insID',
					title : '机号',
					width : 100
				}, {
					field : 'insChnCount',
					title : '通道个数',
					width : 100
				}, {
					field : 'netID',
					title : '网络编号',
					width : 100
				} ] ],
        		pagerFilter : pagerFilter
        	});
			/**
			 * FunctionName 分页过滤器
			 */
			function pagerFilter(data){            
				if (typeof data.length == 'number' && typeof data.splice == 'function'){// is array                
					data = {                   
						total: data.length,                   
						rows: data               
					}            
				}        
				var dg = $(this);         
				var opts = dg.datagrid('options');          
				var pager = dg.datagrid('getPager');          
				pager.pagination({                
					onSelectPage:function(pageNum, pageSize){                 
						opts.pageNumber = pageNum;                   
						opts.pageSize = pageSize;                
						pager.pagination('refresh',{pageNumber:pageNum,pageSize:pageSize});                  
						dg.datagrid('loadData',data);                
					}          
				});           
				if (!data.originalRows){               
					data.originalRows = (data.rows);       
				}         
				var start = (opts.pageNumber-1)*parseInt(opts.pageSize);          
				var end = start + parseInt(opts.pageSize);        
				data.rows = (data.originalRows.slice(start, end));         
				return data;       
			}
			/*
			var tbterminalpager = $('#tb_terminal').datagrid(
			'getPager');
			
			
			tbterminalpager.pagination({
				
				pageSize : 10, //每页显示的记录条数，默认为10
				pageList : [ 10, 5, 3 ], //可以设置每页记录条数的列表
				beforePageText : '第', //页数文本框前显示的汉字
				afterPageText : '页    共 {pages} 页',
				displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录',
				//选择页的处理
				onSelectPage : function(pageNumber, pageSize) {
					//按分页的设置取数据
					getPageData(pageNumber, pageSize);
					//设置表格的pageSize属性，表格变化时按分页组件设置的pageSize显示数据
					$('#tb_terminal').datagrid('options').pageSize = pageSize;
				},
				//改变页显示条数的处理
				//（处理后还是走onSelectPage事件，所以设置也写到onSelectPage事件中了）
				onChangePageSize : function() {
				},
				//点击刷新的处理
				onRefresh : function(pageNumber, pageSize) {
					//按分页的设置取数据
					getPageData(pageNumber, pageSize);
				},
				
			});
			
			var getPageData = function(page, rows) {
				$.ajax({
					type : "GET",
					url : "../TerminalRequest",
					// data: "page=" + page + "&rows=" + rows + "&keyword=" + $('#hdKeyword').val(), 
					async : false,//设置同步
					data : {
						action: 'GetTerminalPageData',
		                insFactoryID:insFactoryID,
		                insType:insType,
						page : page,
						rows : rows,
					},
					error : function(XMLHttpRequest, textStatus,
							errorThrown) {
						alert(textStatus);
						$.messager.progress('close');
					},
					success : function(data) {
						$('#tb_terminal').datagrid('loadData', $.parseJSON(data));
					}
				});
			};
        	*/
        }
        
        
    </script>

	<script>
	
	//新增终端
	function AddTerminal(){
		$('#w')
		.window(
				{
					width : 600,
					height : 450,
					modal : true,
					content : "<iframe scrolling=\"yes\" frameborder=\"0\" src=\"EditTerminal.jsp?action=add"
							+ "\" style=\"width: 100%; height:100%;\"></iframe>",
					title : '新增终端',
					collapsible:false,
					minimizable:false,
					maximizable:false,
				});

		$('#w').window('open');
	}
	
	//修改终端
	function UpdateTerminal(){
		
		var objSelect = $('#tb_terminal').datagrid().datagrid('getSelected'); 
		
		if(!objSelect){
			return;
		}
		
		$('#w')
		.window(
				{
					width : 600,
					height : 450,
					modal : true,
					content : "<iframe scrolling=\"yes\" frameborder=\"0\" src=\"EditTerminal.jsp?action=update"
							+"&autoid="
							+objSelect.autoid
							+"&insFactoryID="
							+objSelect.insFactoryID
							+"&insID="
							+objSelect.insID
							+"&insType="
							+objSelect.insType
							+"&insChnCount="
							+objSelect.insChnCount
							+"&terminalName="
							+objSelect.terminalName
							+"&terminalType="
							+objSelect.terminalType
							+"&terminalDesc="
							+objSelect.terminalDesc
							+"&netID="
							+objSelect.netID
							+ "\" style=\"width: 100%; height:100%;\"></iframe>",
					title : '修改终端',
					collapsible:false,
					minimizable:false,
					maximizable:false,
				});

		$('#w').window('open');
		
	}
	
	//删除终端
	function DeleteTerminal(){
		
		var objSelect = $('#tb_terminal').datagrid().datagrid('getSelected'); 
		
		if(objSelect){
			
			$.messager.confirm('提示', '确认删除该终端?',
					function(r) {
						if (r) {
							
							$.ajax({
								type : "GET",
								url : "../InsTerminalRequest",
								async : false,//设置同步
								data : {
									action : "CheckTerminalMap",
									insFactoryID: objSelect.insFactoryID,
								},
								error : function(XMLHttpRequest, textStatus, errorThrown) {
									//alert(textStatus);
									//$.messager.progress('close');
									$.messager.alert('提示', 'error');
								},
								success : function(data) {
									
									var checkRes=JSON.parse(data);
									
									if(checkRes&&checkRes.result=="true"){
										
										$.messager.alert('提示', '建立匹配关系的终端无法删除');
										
									}else if(checkRes&&checkRes.result=="false"){
										
										$.ajax({
											type : "GET",
											url : "../InsTerminalRequest",
											/* data: "page=" + page + "&rows=" + rows + "&keyword=" + $('#hdKeyword').val(), */
											async : false,//设置同步
											data : {
												action : "DeleteTerminal",
												autoid: objSelect.autoid,
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
													
													$('#tb_terminal').datagrid('reload');
												}
												
											}
										});
										
									}
									
								}
								
							});
						}
			})
			
		}
		
	}
	
	//关闭窗口
	function CloseEditWin(action, msg) {

		if (msg) {
			$.messager.alert('提示', msg);
			
			$('#tb_terminal').datagrid('reload');
		}

		$('#w').window('close');
		
		
	}
	
	</script>
	
	
	</body>
</html>