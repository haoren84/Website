<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>工程向导</title>

<link rel="stylesheet" type="text/css"
	href="../Resource/jquery-easyui-1.5.4.2/themes/gray/easyui.css">
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

.key {
	color: #999;
	font-size: 9pt;
}

.keyinfo {
	color: #333;
	font-size: 9pt;
}
</style>

</head>
<body>
<!-- http://testelectron.eicp.net:8080/MonitorWeb/PrjManage/PrjGuide.jsp?projectId=2&projectName=%E6%88%BF%E5%B1%8B%E7%9B%91%E6%B5%8Bwww -->

	<div class="easyui-panel" title="工程向导" style="width:100%;height:100%;">
		<div id="tab_guide" class="easyui-tabs" style="width:100%;height:100%;" 
		data-options="plain: true,narrow: false,pill: true,
                justified: false,tabWidth:150,tabHeight:60,border:false,">
		
		 <div title="<div style='line-height:30px;font-size:16px;'>1、监测项目</div><div style='line-height:20px;font-size:10px;text-align:left;'>监测项目和测点维护</div>" style="padding:30px">
		 	
		 	<!-- <iframe scrolling="yes" frameborder="0" src="PrjGuideMonitor.jsp" style="width: 100%; height:100%;" ></iframe> -->
		 	
		 	<div id="div_monitorinfo">
		 		
		 		<div>监测项目新增，删除。以及测点维护。</div>
		 		
		 		<a href="#" class="easyui-linkbutton" onclick="funAddMonitor()" style="margin:20px;">新增监测项目</a>
		 		
		 		<table id="tb_monitor"></table>
		 		
		 	</div>
		 	
		 	<div id="div_monitordetailinfo">
		 		
		 		<a href="#" class="easyui-linkbutton" onclick="" style="margin:20px;">新增测点</a>
		 		<a href="#" class="easyui-linkbutton" onclick="" style="margin:20px;">引用测点</a>
		 		<a href="#" class="easyui-linkbutton" onclick="funMonitorDetailReturn()" style="margin:20px;">返回</a>
		 		
		 		<div style="margin:20px;">
		 			<span class="key">工程：</span><span id="prjName" class="keyInfo"></span>
		 			<span class="key">监测项目：</span><span id="monitorName" class="keyInfo"></span>
		 			<span class="key">监测方法：</span><span id="monitorMethord" class="keyInfo"></span>
		 			<span class="key">监测点数：</span><span id="monitorPointCount" class="keyInfo"></span>
		 		</div>
		 		
		 		<table id="tb_monitorpoint"></table>
		 		
		 	</div>
		 	
		 </div>
		 
		 <div title="<div style='line-height:30px;font-size:16px;'>2、采集仪</div><div style='line-height:20px;font-size:10px;text-align:left;'>采集仪维护</div>" style="padding:30px">
		 	
		 </div>
		 
		 <div title="<div style='line-height:30px;font-size:16px;'>3、终端</div><div style='line-height:20px;font-size:10px;text-align:left;'>终端维护</div>" style="padding:30px">
		 	
		 </div>
		
		<div title="<div style='line-height:30px;font-size:16px;'>4、传感器</div><div style='line-height:20px;font-size:10px;text-align:left;'>传感器维护</div>" style="padding:30px">
		
			
		 </div>
		
		</div>
	</div>
	
	<!-- 弹框加载 -->
	<div id="w"></div>

	<!-- 监测项目部分内容 -->
	<script>
	$(function() {
		
	$("#div_monitordetailinfo").css('display','none'); //设置监测详情隐藏
		
	$('#tb_monitor').datagrid({
	    url:'../PrjGuideResquest?action=GetAllMonitorData',
	    columns:[[
	        {field:'monitorName',title:'监测项目',width:100},
	        {field:'monitorMethord',title:'监测方法',width:100},
	        {field:'monitorPointCount',title:'测点数',width:100},
	        {field:'_operate',title:'操作',formatter:formatOper,width:200}
	    ]],
	    rownumbers:true,
	    method:'get',
	    singleSelect:true,
	    
	});
	});
	
	//监测项目的操作
    function formatOper(val,row,index){
    	return '<a href="#" rel="external nofollow" onclick="funMonitorDetail('+index+')">详情</a>|<a href="#" rel="external nofollow" onclick="">删除</a>|<a href="#" rel="external nofollow" onclick="">增加测点</a>|<a href="#" rel="external nofollow" onclick="">引用测点</a>';
    }
	
	//测点的操作
	function formatMPointOper(val,row,index){
		return '<a href="#" rel="external nofollow" onclick="">编辑</a>|<a href="#" rel="external nofollow" onclick="">删除</a>';
	}
    
    //详情
    function funMonitorDetail(index){
    	
    	//console.log(index);
    	
    	$('#tb_monitor').datagrid('selectRow',index);
    	var row = $('#tb_monitor').datagrid('getSelected');
    	
    	var prjName=decodeURI(cookie.get('prjName'));
    	
    	$("#div_monitordetailinfo").css('display','block'); //设置监测详情显示
    	$("#div_monitorinfo").css('display','none'); //设置监测列表隐藏
    	
    	$('#prjName').text(prjName);
    	$('#monitorName').text(row.monitorName);
    	$('#monitorMethord').text(row.monitorMethord);
    	$('#monitorPointCount').text(row.monitorPointCount);
    	
    	$('#tb_monitorpoint').datagrid({
    	    url:'../PrjGuideResquest?action=GetAllMonitorPointData&monitorName='+row.monitorName,
    	    columns:[[
    	        {field:'monitorPtName',title:'测点名称',width:100},
    	        {field:'_msgType',title:'报警类型',width:100},
    	        {field:'_remark',title:'备注',width:100},
    	        {field:'_x',title:'X(m)',width:100},
    	        {field:'_y',title:'Y(m)',width:100},
    	        {field:'_z',title:'Z(m)',width:100},
    	        {field:'_operate',title:'操作',formatter:formatMPointOper,width:200}
    	    ]],
    	    rownumbers:true,
    	    method:'get',
    	    singleSelect:true,
    	    
    	});
    	
    	//console.log(row);
    }
    //新增监测项目
    function funAddMonitor(){
    	$('#w').window({
		    width:350,
		    height:350,
		    modal:true,
		    content:"<iframe scrolling=\"yes\" frameborder=\"0\" src=\"AddMonitorWin.jsp?action=add\" style=\"width: 100%; height:100%;\"></iframe>",
		    title:'新增结构',
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
    function funMonitorDetailReturn(){
    	$("#div_monitordetailinfo").css('display','none'); //设置监测详情隐藏
    	$("#div_monitorinfo").css('display','block'); //设置监测列表显示
    }
    


	</script>



</body>
</html>