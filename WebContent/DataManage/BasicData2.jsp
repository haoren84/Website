<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>基础数据</title>

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
</style>

</head>
<body>
	<div class="easyui-layout" style="width: 100%; height: 100%;">
		<div
			data-options="region:'west',split:true,title:'',collapsible:true,"
			style="width: 200px; text-align: center; padding: 0px;">
			<div class="easyui-tabs" data-options="tools:'#tab-tools',fit:true">
				<div title="监测结构" data-options="iconCls:'',closable:true,"
					style="padding: 10px;">
					<!-- 测点树 -->
					<ul id="mTree"></ul>
				</div>
			</div>

			<div id="tab-tools">
				<a href="javascript:void(0)" class="easyui-linkbutton"
					data-options="plain:true,iconCls:'icon-reload'" onclick=""></a>
			</div>

		</div>


		<div
			data-options="region:'center',title:'',split:true,collapsible:true"
			style="width: auto;">

			<div id="contentTab" class="easyui-tabs" data-options="fit:true">



			</div>


		</div>

	</div>
	
	
	<script>
	
	var curleftIndex = 0;//左侧选中的序号
	
	$(function() {
		
		var action = getQueryString("action");
		
		
		if(action.indexOf("dynamicrealtime")>=0){
			curleftIndex=2;
		}else if(action.indexOf("realtime")>=0){
			
			curleftIndex=0;
			
		}else if(action.indexOf("history")>=0){
			curleftIndex=1;
		}
		
		CtrlTabContent();
	});
	
	</script>
	
	<!-- 树节点加载 -->
	<script>
	var curNode = null;//当前的操作的节点
	
	$(function() {
		
		$('#mTree').tree({
			
			url :'../DataManageRequest?action=GetDataManageMonitorTreeData',//'../DataManageRequest?action=GetDataManageMonitorTreeDataWithRelation', //
			method : 'get',
			onBeforeLoad : function(row, param) {
				if (!row) { // load top level rows
					param.monitorID = 0; // set id=0, indicate to load new page rows
				}
			},
			onBeforeExpand : function(row) {
				console.log(row);

				if (row.attributes) {
					$('#mTree').tree('options').queryParams = {
						monitorID : row.attributes.monitorID,
						monitorName:row.attributes.monitorName
					};
				} 

			},
			onExpand : function(row) {
				//节点展开
				curNode = row;
				CtrlTabContent();
			},
			onSelect : function(row) {
				//节点选中
				curNode = row;
				CtrlTabContent();
			},
			
			
		});
		
		
	});
	
	
	</script>
	
	<!-- 右侧数据的tab信息 -->
	<script>
	//创建用于tab信息的对象 
	//nTabindex:tab序号    nMonitorID:监测项目ID
	function objTabInfoItem(nTabindex, nMonitorID) {
		this.nTabindex = nTabindex;
		
		this.nMonitorID = nMonitorID;
	}

	objTabInfoItem.property = {
		constructor : objTabInfoItem,
	}
	
	var arrayTabInfo=new Array();
	
	var curTabIndex=0;//当前的tabindex
	var cutTabState=0;//当前tab数据的状态
	</script>
	
	<script>
	
	//删除当前的所有tab 和 记录 
	function DeleteAllTab(){
		if(arrayTabInfo&&arrayTabInfo.length){
			var nlength=arrayTabInfo.length;
			for(var i=nlength-1;i>=0;i--){
				var objItem=arrayTabInfo[i];
				$('#contentTab').tabs('close',objItem.nTabindex);
				arrayTabInfo.splice(i,1);
			}
		}
	}
	
	//新增记录
	function AddTabInfo(){
		if (curNode && curNode.attributes) {
			var objItem=new objTabInfoItem(curTabIndex, curNode.attributes.monitorID);
			arrayTabInfo.push(objItem);
		}
	}
	
	//检索是否存在tab
	function CheckTabInfo(){
		var res=0;
		if (curNode && curNode.attributes) {
			var nlength=arrayTabInfo.length;
			for(var i=nlength-1;i>=0;i--){
				var objItem=arrayTabInfo[i];
				if(objItem.nMonitorID==curNode.attributes.monitorID){
					res=1;
					$('#contentTab').tabs('select',objItem.nTabindex);
				}
			}
		}
		return res;
	}
	
	
	
	</script>
	
	<!-- 右侧数据内容 -->
	<script>
	
		//控制tab内容方法
		function CtrlTabContent() {
			//console.log(curleftIndex);
			if (curNode && curNode.attributes) {
				
				if(curleftIndex!=cutTabState){
					DeleteAllTab();
					curTabIndex=0;
					cutTabState=curleftIndex;
				}
				
				if(CheckTabInfo()==1){
					return;
				}
				
				//实时数据
				if (curleftIndex == 0) {
					$('#contentTab')
							.tabs(
									'add',
									{
										title : curNode.text,
										content : '<iframe src="RealTimeData2.jsp?id=22" style="width:100%;height:100%;" scrolling="yes" frameborder="0">'
												+ '</iframe>',
										closable : false
									});
					
					//var tab=$('#contentTab').tabs('getSelected'); 
					//console.log($('#contentTab').tabs('getTabIndex',tab));
					//console.log(curTabIndex);
					AddTabInfo();
					curTabIndex++;
				}
				//历史数据
				if(curleftIndex == 1){
					
					var objDataType=GetValueDataType(curNode.attributes.monitorName);
					
					if(objDataType&&objDataType.result&&objDataType.result=='0'){
						
						var objSeneor=GetSensorMeasureType(curNode.attributes.monitorName);
						
						
						if(objSeneor&&objSeneor.result&&objSeneor.result=='静力水准仪'){
							$('#contentTab')
							.tabs(
									'add',
									{
										title : curNode.text,
										content : '<iframe src="HistoryJLSZData.jsp?id=22" style="width:100%;height:100%;" scrolling="yes" frameborder="0">'
												+ '</iframe>',
										closable : false
									});
							
						}else{
							$('#contentTab')
							.tabs(
									'add',
									{
										title : curNode.text,
										content : '<iframe src="HistoryData2.jsp?id=22" style="width:100%;height:100%;" scrolling="yes" frameborder="0">'
												+ '</iframe>',
										closable : false
									});
						}
						
					}else if(objDataType&&objDataType.result&&objDataType.result=='1'){
						
						$('#contentTab')
						.tabs(
								'add',
								{
									title : curNode.text,
									content : '<iframe src="HistoryFileData.jsp?id=22" style="width:100%;height:100%;" scrolling="yes" frameborder="0">'
											+ '</iframe>',
									closable : false
								});
						
					}
					
					
					
					
					
					
					AddTabInfo();
					curTabIndex++;
					
				}
				//动态实时数据
				if (curleftIndex == 2) {
					$('#contentTab')
							.tabs(
									'add',
									{
										title : curNode.text,
										content : '<iframe src="DynamicRealTimeData.jsp?id=22" style="width:100%;height:100%;" scrolling="yes" frameborder="0">'
												+ '</iframe>',
										closable : false
									});
					
					//var tab=$('#contentTab').tabs('getSelected'); 
					//console.log($('#contentTab').tabs('getTabIndex',tab));
					//console.log(curTabIndex);
					AddTabInfo();
					curTabIndex++;
				}
			}
		}
	</script>
	
	<script>
	
	//获取传感器的测量类型
	function GetSensorMeasureType(strMonitorName){
		
		var result;
		
		$.ajax({
            url: "../DataManageRequest",
            async: false,//设置同步
            data: {
                action: "GetSensorMeasureType",
                monitorName:strMonitorName,
            },
            type: "get",
            datatype: "json",
            success: function (data) {
                var obj = eval("(" + data + ")");

                result = obj;

            },
            error: function () {
                console.log('data error!');
            },
        });

        return result;
		
	}
	
	//获取数据的存储类型
	function GetValueDataType(strMonitorName){
		
		var result;
		
		$.ajax({
            url: "../DataManageRequest",
            async: false,//设置同步
            data: {
                action: "GetValueDataType",
                monitorName:strMonitorName,
            },
            type: "get",
            datatype: "json",
            success: function (data) {
                var obj = eval("(" + data + ")");

                result = obj;

            },
            error: function () {
                console.log('data error!');
            },
        });

        return result;
	}
	
	</script>
	
</body>
</html>