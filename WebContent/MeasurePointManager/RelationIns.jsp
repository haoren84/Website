<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>关联信息</title>

<link rel="stylesheet" type="text/css"
	href="../Resource/jquery-easyui-1.5.4.2/themes/gray/easyui.css">
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

.key {
	color: #999;
	font-size: 9pt;
}

.keyinfo {
	color: #333;
	font-size: 9pt;
}

.datagrid-header .datagrid-cell span {
  font-size: 12px;
  font-weight:900;
}
</style>

</head>
<body>
	<div class="easyui-panel" title="关联向导"
		style="width: 100%; height: 100%;">
		<div id="tab_guide" class="easyui-tabs"
			style="width: 100%; height: 100%;"
			data-options="plain: true,narrow: false,pill: true,
                justified: false,tabWidth:150,tabHeight:60,border:false,">

			<div
				title="<div style='line-height:30px;font-size:16px;'>1、监测项目</div><div style='line-height:20px;font-size:10px;text-align:left;'>监测项目和测点维护</div>"
				style="padding: 30px">

				<!-- <iframe scrolling="yes" frameborder="0" src="PrjGuideMonitor.jsp" style="width: 100%; height:100%;" ></iframe> -->

				<div id="div_monitorinfo">

					<div>监测项目新增，删除。以及测点维护。</div>

					<a href="#" class="easyui-linkbutton" onclick="funAddMonitor()"
						style="margin: 20px;">新增监测项目</a>

					<table id="tb_monitor"></table>

				</div>

				<div id="div_monitordetailinfo">

					<!-- <a href="#" class="easyui-linkbutton" onclick="funAddMonitorPoint()" style="margin:20px;">新增测点</a> -->
					<!-- <a href="#" class="easyui-linkbutton" onclick="" style="margin:20px;">引用测点</a> -->
					<a href="#" class="easyui-linkbutton"
						onclick="funMonitorDetailReturn()" style="margin: 20px;">返回</a>

					<div style="margin: 20px;">
						<span class="key">工程：</span><span id="prjName" class="keyInfo"></span>
						<span class="key">监测项目：</span><span id="monitorName"
							class="keyInfo"></span> <span class="key">监测方法：</span><span
							id="monitorMethord" class="keyInfo"></span> <span class="key">监测点数：</span><span
							id="monitorPointCount" class="keyInfo"></span>
					</div>

					<table id="tb_monitorpoint"></table>

				</div>

			</div>

			<%-- <div
				title="<div style='line-height:30px;font-size:16px;'>2、采集仪</div><div style='line-height:20px;font-size:10px;text-align:left;'>采集仪维护</div>"
				style="padding: 30px">

				<div id="div_instrument_acq">

					<div>采集仪新增，修改，删除。如果采集仪下有终端或者传感器不能删除。</div>
					
					<a href="#" class="easyui-linkbutton" onclick="btnAddInsAcq()"
						style="margin: 20px;">新增采集仪</a>

					<table id="tb_instrument_acq"></table>
					
					
				</div>

			</div> --%>

			<div
				title="<div style='line-height:30px;font-size:16px;'>2、终端</div><div style='line-height:20px;font-size:10px;text-align:left;'>终端维护</div>"
				style="padding: 30px">

				<div id="div_instrument_terminal">

					<div>终端新增，修改，删除。如果终端下有传感器不能删除。</div>
					
					<a href="#" class="easyui-linkbutton" onclick="btnAddTerminal()"
						style="margin: 20px;">新增终端</a>

					<table id="tb_instrument_terminal"></table>

				</div>

			</div>

			<div
				title="<div style='line-height:30px;font-size:16px;'>3、传感器</div><div style='line-height:20px;font-size:10px;text-align:left;'>传感器维护</div>"
				style="padding: 30px">

				<div id="div_instrument_seneor">
					
					<div>传感器新增，修改，删除。如果传感器下有采集数据则不能删除。</div>
					
					<a href="#" class="easyui-linkbutton" onclick="btnAddSeneor()"
						style="margin: 20px;">新增传感器</a>

					<table id="tb_seneor"></table>
					
				</div>
			</div>
			
			<div title="<div style='line-height:30px;font-size:16px;'>4、匹配设置</div><div style='line-height:10px;font-size:8px;text-align:left;'>终端，传感器和测点<br>的匹配关系</div>"
				style="padding: 30px">
			
				<div>终端、传感器、测点之间的匹配关系。</div>
				
				<a href="#" class="easyui-linkbutton" onclick="btnAddMapInfo()"
						style="margin: 20px;">创建匹配关系</a>
						
				

				<table id="tb_mapinfo"></table>
			
			</div>

		</div>
		
	</div>

	<!-- 弹框加载 -->
	<div id="w"></div>

	<!-- 监测项目部分内容 -->
	<script>
		$(function() {

			$("#div_monitordetailinfo").css('display', 'none'); //设置监测详情隐藏

			$('#tb_monitor').datagrid({
				url : '../PrjGuideResquest?action=GetAllMonitorData',
				columns : [ [ {
					field : 'monitorName',
					title : '监测项目',
					width : 100
				}, {
					field : 'monitorMethord',
					title : '监测方法',
					width : 100
				}, {
					field : 'monitorPointCount',
					title : '测点数',
					width : 100
				}, {
					field : '_operate',
					title : '操作',
					formatter : formatOper,
					width : 200
				} ] ],
				rownumbers : true,
				method : 'get',
				singleSelect : true,

			});
		});

		//监测项目的操作
		function formatOper(val, row, index) {
			return '<a href="#" rel="external nofollow" onclick="funMonitorDetail('
					+ index
					+ ')">详情</a>|<a href="#" rel="external nofollow" onclick="funDeleteMonitor('
					+ index
					+ ')">删除</a>|<a href="#" rel="external nofollow" onclick="funAddMonitorPoint('
					+ index + ')">增加测点</a>';
		}

		//测点的操作
		function formatMPointOper(val, row, index) {
			return '<a href="#" rel="external nofollow" onclick="">编辑</a>|<a href="#" rel="external nofollow" onclick="">删除</a>';
		}

		//详情
		function funMonitorDetail(index) {

			//console.log(index);

			$('#tb_monitor').datagrid('selectRow', index);
			var row = $('#tb_monitor').datagrid('getSelected');

			var prjName = decodeURI(cookie.get('prjName'));

			$("#div_monitordetailinfo").css('display', 'block'); //设置监测详情显示
			$("#div_monitorinfo").css('display', 'none'); //设置监测列表隐藏

			$('#prjName').text(prjName);
			$('#monitorName').text(row.monitorName);
			$('#monitorMethord').text(row.monitorMethord);
			$('#monitorPointCount').text(row.monitorPointCount);

			$('#tb_monitorpoint')
					.datagrid(
							{
								url : '../PrjGuideResquest?action=GetAllMonitorPointData&monitorName='
										+ row.monitorName,
								columns : [ [ {
									field : 'monitorPtName',
									title : '测点名称',
									width : 100
								}, {
									field : '_msgType',
									title : '报警类型',
									width : 100
								}, {
									field : '_remark',
									title : '备注',
									width : 100
								}, {
									field : '_x',
									title : 'X(m)',
									width : 100
								}, {
									field : '_y',
									title : 'Y(m)',
									width : 100
								}, {
									field : '_z',
									title : 'Z(m)',
									width : 100
								},
								/* {field:'_operate',title:'操作',formatter:formatMPointOper,width:200} */
								] ],
								rownumbers : true,
								method : 'get',
								singleSelect : true,

							});

			//console.log(row);
		}

		//删除监测项目
		function funDeleteMonitor(index) {
			$('#tb_monitor').datagrid('selectRow', index);
			var row = $('#tb_monitor').datagrid('getSelected');

			if (row) {
				$.messager.confirm('提示', '确认删除该监测项目?',
						function(r) {
							if (r) {
								//alert('confirmed: '+r);
								var res = DeleteMonitorPrjInfo(row.id,
										row.monitorName);

								if (res && res.result && res.result == "true") {
									$('#tb_monitor').datagrid('reload');
								} else {
									$.messager.alert('提示', '删除失败!');
								}

							}
						});
			}
		}

		//删除监测项目的请求方法
		function DeleteMonitorPrjInfo(nMonitorID, strMonitorName) {
			var result;

			$.ajax({
				type : "GET",
				url : "../TstWebRequest/TstResquest",
				/* data: "page=" + page + "&rows=" + rows + "&keyword=" + $('#hdKeyword').val(), */
				async : false,//设置同步
				data : {
					action : "DeleteMonitorInfo",
					nMonitorID : nMonitorID,
					strMonitorName : strMonitorName,
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					console.log('delete monitor error!');
				},
				success : function(data) {
					var obj = eval("(" + data + ")");

					result = obj;
				}
			});

			return result;
		}

		//监测项目-新增测点
		function funAddMonitorPoint(index) {
			$('#tb_monitor').datagrid('selectRow', index);
			var row = $('#tb_monitor').datagrid('getSelected');

			if (row) {
				$('#w')
						.window(
								{
									width : 500,
									height : 300,
									modal : true,
									content : "<iframe scrolling=\"yes\" frameborder=\"0\" src=\"../PrjManage/AddMonitorPointWin.jsp?action=add&"
											+ "&monitorName="
											+ row.monitorName
											+ "&monitorMethord="
											+ row.monitorMethord
											+ "\" style=\"width: 100%; height:100%;\"></iframe>",
									title : '新增测点',
									collapsible:false,
									minimizable:false,
									maximizable:false,
								});

				$('#w').window('open');

			}
		}

		//监测项目-新增测点--关闭
		function WinCloseFun() {

			$('#w').window('close');

			$('#tb_monitor').datagrid('reload');

		}

		//新增监测项目
		function funAddMonitor() {
			$('#w')
					.window(
							{
								width : 500,
								height : 300,
								modal : true,
								content : "<iframe scrolling=\"yes\" frameborder=\"0\" src=\"../PrjManage/AddMonitorWin.jsp?action=add\" style=\"width: 100%; height:100%;\"></iframe>",
								title : '新增监测项目',
								collapsible:false,
								minimizable:false,
								maximizable:false,
							});

			$('#w').window('open');
		}

		//关闭编辑窗口
		function CloseMonitorInfoWin(action) {
			if (action == "add") {
				//刷新监测项目数据
				$('#tb_monitor').datagrid('reload');
			}

			$('#w').window('close');
		}

		//监测详情部分-返回按钮的触发
		function funMonitorDetailReturn() {
			$("#div_monitordetailinfo").css('display', 'none'); //设置监测详情隐藏
			$("#div_monitorinfo").css('display', 'block'); //设置监测列表显示
		}
	</script>
	
	<script>
	$(function() {
		$('#tab_guide').tabs({
			onSelect:function(title,index){
				//console.log(title+' is selected');
		        //console.log(index);
		        switch(index){
		        
		        /* case 1:
		        	LoadInstrumentAcqTable();
		        	return; */
		        case 1:
		        	LoadInstrumentTerminalTable();
		        	return;
		        case 2:
		        	LoadSeneorTable();
		        	return;
		        case 3:
		        	LoadMapInfoTable();
		        	return;
		        }
		        
		    }
		})
	});
	
	</script>
</body>
</html>