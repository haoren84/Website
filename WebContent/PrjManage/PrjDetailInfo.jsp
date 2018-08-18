<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>工程信息</title>

<link rel="stylesheet"
	href="../Resource/bootstrap-3.3.7-dist/css/bootstrap.min.css">

<link rel="stylesheet" type="text/css"
	href="../Resource/jquery-easyui-1.5.4.2/themes/gray/easyui.css">
<link rel="stylesheet" type="text/css"
	href="../Resource/jquery-easyui-1.5.4.2/themes/icon.css">

<script src="../Resource/jquery-easyui-1.5.4.2/jquery.min.js"></script>
<script src="../Resource/jquery-easyui-1.5.4.2/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="../Resource/jquery-easyui-1.5.4.2/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="../Resource/jshelper/commonfun.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="../Resource/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>

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
	font-size: 10pt;
	min-width: 250px;
}

.datagrid-header .datagrid-cell span {
  font-size: 12px;
  font-weight:900;
}
</style>
</head>
<body>

	<!-- Header -->
	<nav class="navbar navbar-default navbar-static-top"
		style="margin-bottom:0px;">
	<div class="container-fluid">
		<div style="margin-top: 10px;">
			<button id="btn_edit" onclick="fun_btn_EditPrj()" class="glyphicon glyphicon-edit btn btn-info btn-sm">编辑</button>
			<!-- <button id="btn_close" class="glyphicon glyphicon-off btn btn-default btn-sm">关闭</button> -->
		</div>
	</div>
	</nav>

	<!-- infotable -->

	<div id="info" style="margin-left: 25px; height: 30%;">

		<div id="infoheader">
			<h4 class="glyphicon glyphicon-star">
				<span style="font-weight: bold;" id="prjnamevalue">工程名称</span>
			</h4>
		</div>

		<div id="infocontent" style="margin-left: 25px;margin-top:30px;">

			<table style="border-collapse:separate;border-spacing:10px;">
				<tr>
					<td class="key">工程类别：</td>
					<td class="keyinfo" id="prjtypevalue" style="display:none;">房屋</td>
					<td class="keyinfo" id="prjtypedisplayvalue">房屋</td>
					<td class="key" style="display:none;">工程区域：</td>
					<td class="keyinfo" id="prjzoomvalue" style="display:none;">贵州省贵阳市清镇市云站路</td>
					<td class="key">工程地址：</td>
					<td class="keyinfo" id="prjaddressvalue">贵州省贵阳市清镇市云站路</td>
				</tr>

				<tr style="display:none;">
					
					<td class="key" >监测负责人：</td>
					<td class="keyinfo" id="prjmanagervalue">test</td>
				</tr>

				<tr>
					<td class="key">经度：</td>
					<td class="keyinfo" id="prjlngvalue">房屋</td>
					<td class="key">纬度：</td>
					<td class="keyinfo" id="prjlatvalue">贵州省贵阳市清镇市云站路</td>
				</tr>

				<tr>
					<td class="key">工程简介：</td>
					<td class="keyinfo" id="prjremarkvalue">房屋</td>
				</tr>
			</table>
		</div>


	</div>
	
	<!-- 弹框引用 -->
	<div id="win"></div>
	<script>
		function WinCloseFun(){
			$('#win').window('close');
		}
	
	</script>

	<!-- tabs -->

	<div class="easyui-tabs" style="width: 100%;">

		<div title="监测项目" style="padding-left: 25px; padding-top: 5px;">

			<!-- 新增监测项目窗口  -->
			<div id="w" class="easyui-window" title="新增监测项目"
				data-options="width:500,height:350,modal:true,closed:true,iconCls:'icon-save',title:'新增监测项目',resizable:false,"
				>
				<iframe scrolling="yes" frameborder="0"
					src="AddMonitorWin.jsp?action=add&id=111"
					style="width: 100%; height: 100%;"></iframe>
			</div>
			
			<!-- 监测项目详情窗口 -->
			<div id="monitorDetailWin" class="easyui-window" title="监测项目详情"
				data-options="modal:true,closed:true,iconCls:'icon-save',title:'监测项目详情'"
				style="width: 750px; height: 550px;">
			</div>

			<!-- <button class="btn btn-default" onclick="BtnAddMonitor()">新增监测项目</button> -->

			<div style="padding-top: 10px;">

				<table id="tbmonitor" title="监测项目"
					style="width: 750px; height: 350px;"
					data-options="rownumbers:true,singleSelect:true,pagination:true,toolbar:tbmonitorToolbar,
					url:'../TstWebRequest/TstResquest?action=GetMonitorPrjPageData',method:'get'">
					<thead>
						<tr>
							<th data-options="field:'monitorName',width:150">监测项目</th>
							<th data-options="field:'monitorMethord',width:150">监测方法</th>
							<th data-options="field:'monitorPointCount',width:100,">测点数</th>
							<th data-options="field:'monitorPointPrefix',width:100,">测点前缀</th>
							<th data-options="field:'monitorPointStartID',width:100">测点起始编号</th>
							<th data-options="field:'monitorPointEndID',width:100,">测点结束编号</th>
						</tr>
					</thead>
				</table>

				<script>
					var tbmonitorToolbar = [
							{
								text : '新增',
								iconCls : 'icon-add',
								handler : function() {
									/* var objSelect = $('#tbmonitor').datagrid()
											.datagrid('getSelected'); */
								 //MonitorTable_AddMonitorPointBtn();
											BtnAddMonitor();
								}
							},
							{
								text : '详情',
								iconCls : 'icon-search',
								handler : function() {
									//alert('search');
									MonitorTable_DetailBtn();
								}
							},
							{
								text : '删除',
								iconCls : 'icon-remove',
								handler : function() {
									/* var objSelect = $('#tbmonitor').datagrid()
											.datagrid('getSelected'); */
								    MonitorTable_DleteBtn();
								}
							}/*,
							{
								text : '添加测点',
								iconCls : 'icon-mini-add',
								handler : function() {
									/* var objSelect = $('#tbmonitor').datagrid()
											.datagrid('getSelected'); */
											/*MonitorTable_AddMonitorPointBtn();
								}
							}*/
							 ];

					var tbmonitorpager = $('#tbmonitor').datagrid().datagrid(
							'getPager');
					tbmonitorpager
							.pagination({
								pageSize : 10, //每页显示的记录条数，默认为10
								pageList : [ 10, 5, 3 ], //可以设置每页记录条数的列表
								beforePageText : '第', //页数文本框前显示的汉字
								afterPageText : '页    共 {pages} 页',
								displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录',
								//选择页的处理
								onSelectPage : function(pageNumber, pageSize) {
									//按分页的设置取数据
									getMonitorData(pageNumber, pageSize);
									//设置表格的pageSize属性，表格变化时按分页组件设置的pageSize显示数据
									$('#tbmonitor').datagrid('options').pageSize = pageSize;
								},
								//改变页显示条数的处理
								//（处理后还是走onSelectPage事件，所以设置也写到onSelectPage事件中了）
								onChangePageSize : function() {
								},
								//点击刷新的处理
								onRefresh : function(pageNumber, pageSize) {
									//按分页的设置取数据
									getMonitorData(pageNumber, pageSize);
								},
							/*buttons:[{
								text:'详情',
							    iconCls:'icon-search',
							    handler:function(){
							        //alert('search');
							    	var objSelect=$('#tbmonitor').datagrid().datagrid('getSelected');
							    }
							},{
								text:'添加测点',
							    iconCls:'icon-add',
							    handler:function(){
							    	var objSelect=$('#tbmonitor').datagrid().datagrid('getSelected');
							    }
							},{
								text:'删除',
							    iconCls:'icon-remove',
							    handler:function(){
							    	var objSelect=$('#tbmonitor').datagrid().datagrid('getSelected');
							    }
							}]*/
							});

					var getMonitorData = function(page, rows) {
						$.ajax({
							type : "GET",
							url : "../TstWebRequest/TstResquest",
							/* data: "page=" + page + "&rows=" + rows + "&keyword=" + $('#hdKeyword').val(), */
							async : false,//设置同步
							data : {
								action : "GetMonitorPrjPageData",
								page : page,
								rows : rows,
							},
							error : function(XMLHttpRequest, textStatus,
									errorThrown) {
								alert(textStatus);
								$.messager.progress('close');
							},
							success : function(data) {
								$('#tbmonitor').datagrid('loadData', $.parseJSON(data));
							}
						});
					};
				</script>

			</div>

		</div>

<!-- 
		<div title="单位信息" style="padding-left: 25px; padding-top: 5px;">
			
			<button class="btn btn-default" >新增单位信息</button>
			
			<div style="padding-top: 10px;">
				<table id="tbunit" title="单位信息" class="easyui-datagrid"
					style="width: 750px; height: 350px;"
					>
					<thead>
						<tr>
							<th data-options="field:'monitorName',width:150">单位类型</th>
							<th data-options="field:'monitorMethord',width:150">单位</th>
							<th data-options="field:'monitorPointCount',width:100,">联系人</th>
							<th data-options="field:'monitorPointPrefix',width:100,">联系电话</th>
						</tr>
					</thead>
				</table>
			</div>
			
		</div>

		<div title="工程资料" style="padding-left: 25px; padding-top: 5px;">
			<button class="btn btn-default" >新增工程资料</button>
			
			<div style="padding-top: 10px;">
				<table id="tbengineer" title="工程资料" class="easyui-datagrid"
					style="width: 750px; height: 350px;"
					>
					<thead>
						<tr>
							<th data-options="field:'monitorName',width:150">类型</th>
							<th data-options="field:'monitorMethord',width:150">备注</th>
							<th data-options="field:'monitorPointCount',width:100,">文件</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>

		<div title="短信人员" style="padding-left: 25px; padding-top: 5px;">
			
			<button class="btn btn-default" >新增短信人员</button>
			
			<div style="padding-top: 10px;">
				<table id="tbSMS" title="短信人员" class="easyui-datagrid"
					style="width: 750px; height: 350px;"
					>
					<thead>
						<tr>
							<th data-options="field:'monitorName',width:150">名称</th>
							<th data-options="field:'monitorMethord',width:150">电话</th>
							<th data-options="field:'monitorPointCount',width:80,">是否启用</th>
							<th data-options="field:'monitorMethord1',width:150">备注</th>
						</tr>
					</thead>
				</table>
			</div>
			
		</div>
	 -->

	</div>
	
	<!-- 编辑工程 -->
	<div id="prjwin" class="easyui-window" title="编辑工程"
	data-options="modal:true,closed:true,iconCls:'icon-save',title:'编辑工程',collapsible:false,minimizable:false,maximizable:false,resizable:false,"
		style="width: 750px; height: 400px; "
	>
		<div class="easyui-layout" data-options="fit:true">
			
			<div data-options="region:'center'" style="padding: 10px;">
				
				<div id="dlgfm" style="width: 100%">
				
					<form id="fm" method="post" novalidate
					style="margin: 0; padding: 20px 50px">
					
					<div style="margin-bottom: 10px">
						<input id="prjname" class="easyui-textbox" required="true"
							label="工程名称:" style="width: 100%">
					</div>
					
					<div style="margin-bottom: 10px">
						<!-- <select id="categorymain_gis_project" class="easyui-combobox"
							required="true" label="工程类型:" style="width: 100%" data-options="editable:false">
							<option value="1">隧道</option>
							<option value="2">房屋</option>
							<option value="3">地铁</option>
							<option value="4">基坑</option>
							<option value="5">桥梁</option>
						</select> -->
						
						<input id="categorymain_gis_project" class="easyui-combobox" required="true"
							label="工程类型:" style="width: 100%">
						
					</div>
					
					<div style="margin-bottom: 10px">
						<input id="prjaddress" class="easyui-textbox" required="true"
							label="工程地址:" style="width: 100%">
					</div>
					
					<div style="margin-bottom: 10px">
						<input id="longitude" name="longitude" type="text"
				style="width: 180px;" class="easyui-textbox " readonly="readonly"
				value="" label="经纬度:" required="true" /> <input id="latitude" name="latitude" type="text"
				style="width: 90px;" class="easyui-textbox " readonly="readonly"
				value="" required="true" /> <a class="easyui-linkbutton l-btn" plain="true"
				iconCls="icon-search" id="getPositionmain_gis_project"
				href="javascript:void(0);">定位</a>
					</div>
					
					<div style="margin-bottom: 10px">
					<textarea class="easyui-textbox" name="comments"
					id="remark" style="width: 100%; height: 55px;" label="工程简介:"></textarea>
					</div>
					
					
					</form>
				
				</div>
				
			</div>
			
			<div data-options="region:'south',border:false"
			style="text-align: right; padding: 5px; border-color: #dddddd #95B8E7 #95B8E7 #95B8E7; background: #F4F4F4;">
			<a href="javascript:void(0)" class="easyui-linkbutton c6"
				iconCls="icon-ok" id="btnOK" style="width: 90px">保存</a> <a
				href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-cancel" onclick="CloseEditPrjWin('cancle')" style="width: 90px">取消</a>

			</div>
		
		</div>
		
		
	</div>
	
	<!-- 地图选择页面 -->
	<div id="mapw"></div>
	<!-- 地图选择页面触发  -->
	<script>
		
	//保存
	function SelectMapPoint(x, y) {
		$('#longitude').textbox("setValue", x);
		$('#latitude').textbox("setValue", y);
	}

	//关闭地图选择
	function CloseMapSelect() {
		$('#mapw').window('close');
	}
	
	</script>
	
	
	<!-- 工程编辑框信息 -->
	<script>
	
	$("#categorymain_gis_project").combobox({
		url : "../DictionaryComboRequest?action=GetPrjTypeCombo",
		valueField: 'id',
        textField: 'dicvalue',
	});
	
		
	function fun_btn_EditPrj(){
		//赋值
		
		$("#prjname").textbox('setValue',$('#prjnamevalue').html());
		$("#categorymain_gis_project").combobox("setValue",$('#prjtypevalue').html());
		$("#prjaddress").textbox('setValue',$('#prjaddressvalue').html());
		$('#longitude').textbox("setValue",$('#prjlngvalue').html());
		$('#latitude').textbox("setValue",$('#prjlatvalue').html());
		$('#remark').textbox("setValue",$('#prjremarkvalue').html());
		
		$('#prjname').textbox('textbox').attr('readonly',true); 
		
		$('#prjwin').window('open');
		
	}
	
	$('#getPositionmain_gis_project').click(function() {
		
		var top=0;
		var left=0;
		
		$('#mapw').window({
			width:700,
			height:400,
			modal:true,
			closed:true,
			inline:false,
			collapsible:false,minimizable:false,maximizable:false,closable:false,
            content:"<iframe scrolling=\"yes\" frameborder=\"0\" src=\"../MapSelect.jsp"
	    		+"\" style=\"width: 100%; height:100%;\"></iframe>",
	    	title:'经纬度选择',
		});
		
		$('#mapw').window('open');
		
	});
	
	//触发修改保存事件
	$('#btnOK').click(function() {
		$.ajax({
			type : "GET",
			url : "../TstWebRequest/TstResquest",
			/* data: "page=" + page + "&rows=" + rows + "&keyword=" + $('#hdKeyword').val(), */
			async : false,//设置同步
			data : {
				action : "UpdatePrjInfo",
				prjID:cookie.get('prjID'),
				prjName:$("#prjname").textbox('getValue'),
				prjAddress:$("#prjaddress").textbox('getValue'),
				PrjManager:'0',
				PrjRemark:$('#remark').textbox("getValue"),
				prjType:$("#categorymain_gis_project").combobox("getValue"),
				prjLng:$('#longitude').textbox("getValue"),
				PrjLat:$('#latitude').textbox("getValue"),
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				
				console.log('update prj error');
			},
			success : function(data) {
				
				var res=JSON.parse(data);
				
				if(!res||!res.result||res.result!='true'){
					$.messager.alert('提示','修改失败!');
					return;
				}
				
				CloseEditPrjWin("update");
				
			}
		});
	})
	
	
	function CloseEditPrjWin(action){
		
		$('#prjwin').window('close');
		
		if(action=="update"){
			//ReflashHtmlData();
			location.reload();
		}
	}
	
	</script>
	


	

	<script>
		var prjID = getQueryString("projectId");
		var prjName = getQueryString("projectName");
	</script>
	
	<!-- 编辑和关闭按钮触发 -->
	<script>
		//编辑工程信息
		function fun_btn_EditPrj_old(){
			
			$('#win').css("width","750px");
			$('#win').css("height","500px");
			//弹框
			$('#win').window({
				closed:true,
				iconCls:'icon-save',
			    modal:true,
			    content:"<iframe scrolling=\"yes\" frameborder=\"0\" src=\"../EditSlnInfo.jsp?action=update"
			    		+"&prjnamevalue="+$('#prjnamevalue').html()
			    		+"&prjtypevalue="+$('#prjtypevalue').html()
			    		+"&prjzoomvalue="+$('#prjzoomvalue').html()
			    		+"&prjaddressvalue="+$('#prjaddressvalue').html()
			    		+"&prjmanagervalue="+$('#prjmanagervalue').html()
			    		+"&prjlngvalue="+$('#prjlngvalue').html()
			    		+"&prjlatvalue="+$('#prjlatvalue').html()
			    		+"&prjremarkvalue="+$('#prjremarkvalue').html()
			    		+"\" style=\"width: 100%; height:90%;\"></iframe>",
			    title:'编辑工程',
			});
			$('#win').window('open');
		}
		
		//编辑成功，刷新页面
		function EditPrjSuccess(){
			location.reload();
		}
		
	    //关闭工程信息
		function fun_btn_ClosePrj(){
			
		}
	
	</script>

	<script>
		function GetPrjInfo() {

			var result;

			$.ajax({
				type : "GET",
				url : "../TstWebRequest/TstResquest",
				/* data: "page=" + page + "&rows=" + rows + "&keyword=" + $('#hdKeyword').val(), */
				async : false,//设置同步
				data : {
					action : "GetPrjInfoByID",
					prjID : cookie.get('prjID'),
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					console.log('data error!');
				},
				success : function(data) {
					var obj = eval("(" + data + ")");

					result = obj;
				}
			});

			return result;
		}
		
		

		//加载表格中的信息
		function LoadInfoTable() {

			var objPrjInfo = GetPrjInfo();

			if (objPrjInfo == null || objPrjInfo == undefined) {
				return;
			}

			$('#prjnamevalue').html(objPrjInfo.prjname);
			$('#prjtypevalue').html(objPrjInfo.prjtype);
			
			/* if(objPrjInfo.prjtype=='1'){
				$('#prjtypedisplayvalue').html('隧道');
			}else if(objPrjInfo.prjtype=='2'){
				$('#prjtypedisplayvalue').html('房屋');
			}else if(objPrjInfo.prjtype=='3'){
				$('#prjtypedisplayvalue').html('地铁');
			}else if(objPrjInfo.prjtype=='4'){
				$('#prjtypedisplayvalue').html('基坑');
			}else if(objPrjInfo.prjtype=='5'){
				$('#prjtypedisplayvalue').html('桥梁');
			} */
			
			//根据字典的数据去获取值
			
			$.ajax({
				type : "GET",
				url : "../DictionaryComboRequest?action=GetPrjTypeCombo",
				/* data: "page=" + page + "&rows=" + rows + "&keyword=" + $('#hdKeyword').val(), */
				async : true,//设置同步
				data : {
					
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					console.log('delete monitor error!');
				},
				success : function(data) {
					
					var objPrjType = eval("(" + data + ")");
					
					if(objPrjType){
						
						//console.log(objPrjType);
						
						var nObjLength=objPrjType.length;
						
						for(var i=0;i<nObjLength;i++){
							
							var objItem=objPrjType[i];
							
							if(objItem.id==objPrjInfo.prjtype){
								
								$('#prjtypedisplayvalue').html(objItem.dicvalue);
								
							}
							
						}
						
					}
					
				}
			});
			
			
				
			
			$('#prjzoomvalue').html(objPrjInfo.prjname);
			$('#prjaddressvalue').html(objPrjInfo.prjaddress);
			$('#prjmanagervalue').html(objPrjInfo.prjmanager);
			$('#prjlngvalue').html(objPrjInfo.prjlng);
			$('#prjlatvalue').html(objPrjInfo.prjlat);
			$('#prjremarkvalue').html(objPrjInfo.remark);

		}

		LoadInfoTable();
		
		
		
	</script>

	<!-- 监测项目相关方法 -->
	<script>
		//打开编辑窗口 新增用
		function BtnAddMonitor() {
			//$('#w').window('open');
			
			$('#w')
			.window(
					{
						width : 600,
						height : 350,
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
				$('#tbmonitor').datagrid('reload');
			}

			$('#w').window('close');
		}
		
		

		//监测项目表格详情按钮
		function MonitorTable_DetailBtn() {
			var objSelect = $('#tbmonitor').datagrid().datagrid('getSelected');
			
			if(objSelect==null||objSelect==undefined){
				return;
			}else{
			   $('#monitorDetailWin').window({content:"<iframe scrolling=\"yes\" frameborder=\"0\" src=\"MonitorDetailInfo.jsp?monitorid="
					   	   +objSelect.id
					   	   +"&monitorName="+objSelect.monitorName
					   	   +"&monitorMethord="+objSelect.monitorMethord
					   	   +"&monitorPointCount="+objSelect.monitorPointCount
					   	   +"&monitorPointPrefix="+objSelect.monitorPointPrefix
					   	   +"&monitorPointStartID="+objSelect.monitorPointStartID
					   	   +"&monitorPointEndID="+objSelect.monitorPointEndID
						   +"\" style=\"width: 100%; height: 100%;\"></iframe>"});
			   
			   console.log($('#monitorDetailWin'));
			   
			   $('#monitorDetailWin').window('open');
			}
		}

		//监测项目表格添加监测测点按钮
		function MonitorTable_AddMonitorPointBtn() {
			var objSelect = $('#tbmonitor').datagrid().datagrid('getSelected');
			
			if(objSelect){
				
				$('#win').window({
					width:500,
				    height:300,
				    modal:true,
				    content:"<iframe scrolling=\"yes\" frameborder=\"0\" src=\"AddMonitorPointWin.jsp?action=add&"
				    		+"&monitorName="+objSelect.monitorName
					   	   	+"&monitorMethord="+objSelect.monitorMethord
				    		+"\" style=\"width: 100%; height:100%;\"></iframe>",
				    title:'添加测点',
					
				});
				
				$('#win').window('open');
				
				 console.log($('#win'));
			}
			
		}
		
		//监测项目表格删除按钮
		function MonitorTable_DleteBtn() {
			var objSelect = $('#tbmonitor').datagrid().datagrid('getSelected');
			
			if(objSelect){
			$.messager.confirm('提示', '确认删除该监测项目?', function(r){
                if (r){
                    //alert('confirmed: '+r);
                    var res=DeleteMonitorPrjInfo(objSelect.id,objSelect.monitorName);
                    
                    if(res&&res.result&&res.result=="true"){
                    	$('#tbmonitor').datagrid('reload');
                    }else{
                    	$.messager.alert('提示','删除失败,已存在关联关系的监测项目禁止删除!');
                    }
                    
                }
            });
			}
			
		}
		
		//删除监测项目
		function DeleteMonitorPrjInfo(nMonitorID,strMonitorName){
			var result;

			$.ajax({
				type : "GET",
				url : "../TstWebRequest/TstResquest",
				/* data: "page=" + page + "&rows=" + rows + "&keyword=" + $('#hdKeyword').val(), */
				async : false,//设置同步
				data : {
					action : "DeleteMonitorInfo",
					nMonitorID : nMonitorID,
					strMonitorName:strMonitorName,
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
	</script>



</body>
</html>