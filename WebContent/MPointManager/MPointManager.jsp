<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>测点管理</title>

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

.datagrid-header .datagrid-cell span {
    font-size: 12px;
    font-weight:900;
}

</style>

</head>
<body>

<div class="easyui-layout" style="width: 100%; height: 100%;">
	
	<!-- <div
			data-options="region:'west',split:true,title:'',collapsible:true,"
			style="width: 200px; text-align: center; padding: 0px;">
			
			<div class="easyui-tabs" data-options="tools:'#tab-tools',fit:true">
				<div title="工程项目" data-options="iconCls:'',closable:false,"
					style="padding: 10px;">
					工程项目树
					<ul id="mTree"></ul>
				</div>
			</div>
	
	</div> -->
			
			
	<div
			data-options="region:'center',title:'',split:true,collapsible:true,border:false,"
			style="width: auto;">
			
			<div id="p" class="easyui-panel" title="筛选" 
			style="width:100%;height:70px;padding:5px;"
        	data-options="iconCls:'icon-save',closable:false,
                collapsible:false,minimizable:false,maximizable:false,border:false,">
            
            &nbsp;&nbsp;&nbsp;&nbsp;
			<input id="cc_monitorname"  class="easyui-combobox" label="监测项目：" labelPosition="left" style="width: 200px;"/>
            &nbsp;&nbsp;&nbsp;&nbsp;
			<input id="cc_mpname"  class="easyui-textbox" label="测点名称：" labelPosition="left" style="width: 200px;"/>
			&nbsp;&nbsp;&nbsp;&nbsp;
			<input id="cc_insname"  class="easyui-textbox" label="设备名称：" labelPosition="left" style="width: 200px;"/>
			&nbsp;&nbsp;&nbsp;&nbsp;
			<a id="btn_search" class="easyui-linkbutton" data-options="iconCls:'icon-search'" style="width: 80px">搜索</a>
			</div>
			
			<div  id="tab_manage" class="easyui-tabs" data-options="tools:'#tab-tools',fit:true,border:false,">
			
				<div title="测点参数设置" data-options="iconCls:'',closable:false,"
					style="padding: 10px;">
					
					<table id="tb_mppara" style="height:500px;border:none;"></table>
					
				</div>
				
				<!-- <div title="报警参数设置" data-options="iconCls:'',closable:false,"
					style="padding: 10px;">
					
					
					
				</div>
				
				<div title="关联设备" data-options="iconCls:'',closable:false,"
					style="padding: 10px;">
				</div> -->
			
			</div>
			
	</div>

</div>

	<!-- 弹框加载 -->
	<div id="w"></div>

	<script>
	
	var strMPNameKey,strInsNameKey,strMonitorName;
	
	$(function() {
		
		/* $('#mTree').tree({
			
			url:'../MPManagerRequest?action=GetMonitorNameTree',
			method : 'get',
			onSelect : function(row) {
				
				strMonitorName=row.text;
				//节点选中
				//console.log(row);
				
				LoadData();
			},
		}); */
		
		$('#cc_monitorname').combobox({
			
			url:'../MPManagerRequest?action=GetMonitorComboData',
			method : 'get',
			valueField:'text',
		    textField:'text',
		    onLoadSuccess:function(){
		    	var data = $('#cc_monitorname').combobox('getData');
		    	if(data){
		    		$('#cc_monitorname').combobox('select',data[0].text);
		    		
		    		LoadData();
		    	}
		    },
		    onSelect:function(record){
		    	
		    	//console.log(record);//
		    	
		    	strMonitorName=record.text;
		    }
		});
		
	});
	
	//按钮触发
	$('#btn_search').bind('click', function(){
		
		LoadData();
    });
	
	//页面的数据信息
	function GetHtmlInfo(){
		strMPNameKey=$('#cc_mpname').textbox('getText');
		strInsNameKey=$('#cc_insname').textbox('getText');
	}
	
	function LoadData(){
		
		//当前tab的选中
		var tab = $('#tab_manage').tabs('getSelected');
		
		var index = $('#tab_manage').tabs('getTabIndex',tab);
		
		switch(index)
		{
		case 0:
			{LoadMPPara();}
			break;
		case 1:
			{}
			break;
		case 2:
			{}
			break;
		default:
			{}
		break;
		
		}
		
	}
	
	</script>
	
	<!-- 测点参数设置  -->
	<script>
		
		//加载测点参数的数据
		function LoadMPPara(){
			
			GetHtmlInfo();
			
			//表格的工具栏
			var tb_mppara_toolbar = [ {
				text : '关联终端和传感器',
				iconCls : 'icon-add',
				handler : function() {
					//var objSelect = $('#tb_terminal').datagrid().datagrid('getSelected'); 
					SetRelation();
				}
			}];
			
			$('#tb_mppara').datagrid({
				url:"../MPManagerRequest",
				queryParams: {
	                action: 'GetMPPageData',
	                MPNameKey:strMPNameKey,
	                InsNameKey:strInsNameKey,
	                MonitorName:strMonitorName,
	            },
				method:'get',
				rownumbers:true,
				singleSelect:true,
				pagination:true,
				fitColumns:true,
				toolbar : tb_mppara_toolbar,
				columns : [ [ {
					field : 'isused',
					title : '使用标识',
					width : 200
				}, {
					field : 'monitorName',
					title : '项目名称',
					width : 200
				}, {
					field : 'monitorPtName',
					title : '测点名称',
					width : 200
				}, {
					field : 'terminalName',
					title : '关联终端名称',
					width : 200
				}, {
					field : 'terminalType',
					title : '关联终端规格',
					width : 200
				}, {
					field : 'SensorName',
					title : '关联传感器名称',
					width : 200
				} ] ],
        		
        		
        	});
			
			var tbmpparapager = $('#tb_mppara').datagrid(
			'getPager');
			
			
			tbmpparapager.pagination({
				
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
					$('#tb_mppara').datagrid('options').pageSize = pageSize;
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
					url : "../MPManagerRequest",
					/* data: "page=" + page + "&rows=" + rows + "&keyword=" + $('#hdKeyword').val(), */
					async : false,//设置同步
					data : {
						action: 'GetMPPageData',
						strMPNameKey:strMPNameKey,
		                strInsNameKey:strMPNameKey,
		                strMonitorName:strMonitorName,
						page : page,
						rows : rows,
					},
					error : function(XMLHttpRequest, textStatus,
							errorThrown) {
						alert(textStatus);
						$.messager.progress('close');
					},
					success : function(data) {
						$('#tb_mppara').datagrid('loadData', $.parseJSON(data));
					}
				});
			};
		}
	
	</script>
	
	<script>
		
	
	
		//设置关联
		function SetRelation(){
			
			var objSelect = $('#tb_mppara').datagrid('getSelected');
			
			if(objSelect){
				
				$('#w')
				.window(
						{
							width : 600,
							height : 450,
							modal : true,
							content : "<iframe scrolling=\"yes\" frameborder=\"0\" src=\"EditMapInfo.jsp?MonitorName="
									+objSelect.monitorName+
									"&MonitorPtName="
									+objSelect.monitorPtName+
									"&insFactoryID="
									+objSelect.insFactoryID
									+"&terminalType="
									+objSelect.terminalType
									+"&chnID="
									+objSelect.chnID
									+"&SensorID="
									+objSelect.SensorID
									+ "\" style=\"width: 100%; height:100%;\"></iframe>",
							title : '创建匹配关系',
							collapsible:false,
							minimizable:false,
							maximizable:false,
						});

				$('#w').window('open');
				
			}
			
		}
		
		//关闭窗口
		function CloseEditWin(action, msg) {

			if (msg) {
				$.messager.alert('提示', msg);

				$('#tb_mppara').datagrid('reload');
			}

			$('#w').window('close');

		}
	
	</script>



</body>
</html>