<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>传感器</title>

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
	font-weight: 900;
}

#search input {
	margin-left: 5px;
	margin-right: 20px;
}
</style>
</head>
<body>
	<div id="titlepanel" class="easyui-panel" title="传感器"
		style="width: 100%; height: 100%;" data-options="border:false,">
		<!-- 筛选区域 -->
		<div class="easyui-accordion" style="width: 100%; height: 80px;"
			data-options="border:false,">
			<div title="筛选条件"
				data-options="iconCls:'icon-search',collapsed:false,collapsible:false"
				style="padding: 10px;">
				<input id="sensorName" class="easyui-textbox" label="传感器名称："
					labelPosition="left" style="width: 240px;"> <input
					id="sensorType" class="easyui-textbox" label="传感器规格型号："
					labelPosition="left" style="width: 200px;"> <a
					id="btn_search" class="easyui-linkbutton"
					data-options="iconCls:'icon-search'" style="width: 80px">搜索</a>
			</div>
		</div>

		<!-- 数据显示区域  -->
		<div id="ac_datadisplay" class="easyui-accordion" style="width: 100%;"
			data-options="border:false,">

			<div title="设置"
				data-options="iconCls:'icon-large-chart',collapsed:false,collapsible:false,border:false,"
				style="padding: 0px; width: 100%; height: 550px; border: none;">
				<table id="tb_sensor" style="height:500px;border:none;">

				</table>
			</div>
		</div>
	</div>
	
	<!-- 弹框加载 -->
	<div id="w"></div>
	
	<script>
		$("#search").layout({
			region : 'north',
			height : 40,
		});

		$(function() {
			// 传感器编号下拉框数据
			/* $('#sensorId').combobox({
				url : '../SensorRequest?action=GetSensorIdComboData',
				valueField:'id',
				textField:'text',
				onLoadSuccess:function(none){
					if(none&&none.length){
						$('#sensorId').combobox('select',none[0].id);
					}
				}
			}); */

			// 类型下拉框数据
			/* $('#sensorType').combobox({
				
				url : "../SensorRequest?action=GetSensorTypeComboData",
				valueField: 'id',
			    textField: 'text',
				onLoadSuccess:function(none){
					if(none&&none.length){
						$('#sensorType').combobox('select',none[0].id);
						//加载表格数据
			        	LoadTable();
					}
				}
			}); */

			//按钮触发
			$('#btn_search').bind('click', function() {
				LoadTable();
			});
			
			LoadTable();
		});

		//表格的工具栏
		var tb_seneor_toolbar = [ {
			text : '新增',
			iconCls : 'icon-add',
			handler : function() {
				//var objSelect = $('#tb_terminal').datagrid().datagrid('getSelected'); 

				//console.log(objSelect);
				//MonitorTable_AddMonitorPointBtn();
				AddSensor();

			}
		}, {
			text : '详情',
			iconCls : 'icon-search',
			handler : function() {
				//alert('search');
				UpdateSensor();
			}
		}, {
			text : '删除',
			iconCls : 'icon-remove',
			handler : function() {
				/* var objSelect = $('#tbmonitor').datagrid()
						.datagrid('getSelected'); */
						DeleteSensor();
			}
		} ];

		//加载表格数据
		function LoadTable() {

			var sensorName = $('#sensorName').textbox('getText');
			var sensorType = $('#sensorType').textbox('getText');

			$('#tb_sensor').datagrid({

				url : "../SensorRequest",
				queryParams : {
					action : 'GetSeneorSelectPage',
					SensorName : sensorName,
					SensorType : sensorType,
				},
				method : 'get',
				toolbar : tb_seneor_toolbar,
				rownumbers : true,
				singleSelect : true,
				pagination : true,
				fitColumns : true,
				columns : [ [ {
					field : 'SensorName',
					title : '传感器名称',
					width : 200
				}, {
					field : 'SensorSpec',
					title : '传感器规格型号',
					width : 200
				}, {
					field : 'SensorFactory',
					title : '生产厂商',
					width : 150
				}, {
					field : 'SensorDesc',
					title : '传感器描述',
					width : 350
				}, {
					field : 'SensorPara',
					title : '传感器参数',
					width : 150
				} ] ],
			});

			var tbSensorPager = $('#tb_sensor').datagrid('getPager');

			tbSensorPager.pagination({

				pageSize : 10, // 每页显示的记录条数，默认为10
				pageList : [ 10, 5, 3 ], // 可以设置每页记录条数的列表
				beforePageText : '第', // 页数文本框前显示的汉字
				afterPageText : '页    共 {pages} 页',
				displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录',
				// 选择页的处理
				onSelectPage : function(pageNumber, pageSize) {
					// 按分页的设置取数据
					getPageData(pageNumber, pageSize);
					// 设置表格的pageSize属性，表格变化时按分页组件设置的pageSize显示数据
					$('#tb_sensor').datagrid('options').pageSize = pageSize;
				},
				// 改变页显示条数的处理
				// （处理后还是走onSelectPage事件，所以设置也写到onSelectPage事件中了）
				onChangePageSize : function() {
				},
				// 点击刷新的处理
				onRefresh : function(pageNumber, pageSize) {
					// 按分页的设置取数据
					getPageData(pageNumber, pageSize);
				},
			});

			var getPageData = function(page, rows) {
				$
						.ajax({
							type : "GET",
							url : "../SensorRequest",
							/* data: "page=" + page + "&rows=" + rows + "&keyword=" + $('#hdKeyword').val(), */
							async : false,//设置同步
							data : {
								action : 'GetSensorPageData',
								SensorID : SensorID,
								SensorMeasureType : SensorMeasureType,
								page : page,
								rows : rows,
							},
							error : function(XMLHttpRequest, textStatus,
									errorThrown) {
								alert(textStatus);
								$.messager.progress('close');
							},
							success : function(data) {
								$('#tb_sensor').datagrid('loadData',
										$.parseJSON(data));
							}
						});
			};
		}
		
		//新增传感器
		function AddSensor(){
			
			$('#w')
			.window(
					{
						width : 600,
						height : 450,
						modal : true,
						content : "<iframe scrolling=\"yes\" frameborder=\"0\" src=\"EditSensor.jsp?action=add"
								+ "\" style=\"width: 100%; height:100%;\"></iframe>",
						title : '新增传感器',
						collapsible:false,
						minimizable:false,
						maximizable:false,
					});

			$('#w').window('open');
			
		}
		
		//修改传感器
		function UpdateSensor(){
			var objSelect = $('#tb_sensor').datagrid().datagrid('getSelected');
			
			if(!objSelect){
				return;
			}
			
			// - _ . ! ~ * ’ ( )%
			var strDesc=objSelect.SensorDesc.replace('-','').replace('_','')
			.replace('.','').replace('!','').replace('~','').replace('*','')
			.replace('’','').replace('(','').replace(')','').replace('%','');
			
			$('#w')
			.window(
					{
						width : 600,
						height : 450,
						modal : true,
						content : "<iframe scrolling=\"yes\" frameborder=\"0\" src=\"EditSensor.jsp?action=update"
								+"&autoid="
								+objSelect.autoid
								+"&sensorID="
								+objSelect.SensorID
								+"&sensorName="
								+objSelect.SensorName
								+"&sensorSpec="
								+objSelect.SensorSpec
								+"&sensorFactory="
								+objSelect.SensorFactory
								+"&sensorDesc="
								+strDesc
								+"&sensorMeasureType="
								+objSelect.SensorMeasureType
								+"&param1="
								+objSelect.Param1
								+"&param2="
								+objSelect.Param2
								+"&param3="
								+objSelect.Param3
								+"&param4="
								+objSelect.Param4
								+"&param5="
								+objSelect.Param5
								+"&param6="
								+objSelect.Param6
								+"&param7="
								+objSelect.Param7
								+"&param8="
								+objSelect.Param8
								+ "\" style=\"width: 100%; height:100%;\"></iframe>",
						title : '修改传感器',
						collapsible:false,
						minimizable:false,
						maximizable:false,
					});

			$('#w').window('open');
		}
		
		//删除传感器
		function DeleteSensor(){
			var objSelect = $('#tb_sensor').datagrid().datagrid('getSelected');
			
			if(objSelect){
				
				$.messager.confirm('提示', '确认删除该传感器?',
						function(r) {
							if (r) {
								
								$.ajax({
									type : "GET",
									url : "../SensorRequest",
									async : false,//设置同步
									data : {
										action : "CheckSensorMap",
										SensorID: objSelect.SensorID,
									},
									error : function(XMLHttpRequest, textStatus, errorThrown) {
										//alert(textStatus);
										//$.messager.progress('close');
										$.messager.alert('提示', 'error');
									},
									success : function(data) {
										
										var checkRes=JSON.parse(data);
										
										if(checkRes&&checkRes.result=="true"){
											
											$.messager.alert('提示', '建立匹配关系的传感器无法删除');
											
										}else if(checkRes&&checkRes.result=="false"){
											
											$.ajax({
												type : "GET",
												url : "../SensorRequest",
												/* data: "page=" + page + "&rows=" + rows + "&keyword=" + $('#hdKeyword').val(), */
												async : false,//设置同步
												data : {
													action : "DeleteSensor",
													SensorID: objSelect.SensorID,
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
														
														$('#tb_sensor').datagrid('reload');
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

				$('#tb_sensor').datagrid('reload');
			}

			$('#w').window('close');

		}
	</script>
</body>
</html>