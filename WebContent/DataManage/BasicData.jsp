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

<!-- 列表按钮样式 -->
<style type="text/css">
#ul_leftmenu {
	list-style: none;
	text-align: center;
	width: 98px;
	font-size: 12px;
	margin: 0px;
	padding: 20px 0px 0px 0px;
}

#ul_leftmenu li {
	text-align: center;
	margin: 0px;
	padding: 10px 0px 0px 0px;
	width: 98px;
	height: 88px;
	cursor: pointer;
}

#ul_leftmenu li a {
	margin: 0px;
	padding: 0px;
	width: 80px;
	height: 88px;
}

#ul_leftmenu li a img {
	margin: 0px;
	padding: 0px;
	width: 32px;
	height: 32px;
}

#ul_leftmenu li a h2 {
	font-size: 12px;
}
</style>

</head>

<!-- http://testelectron.eicp.net:8080/MonitorWeb/DataManage/BasicData.jsp?projectId=2&projectName=%E6%88%BF%E5%B1%8B%E7%9B%91%E6%B5%8Bwww -->
<body>
	<div class="easyui-layout" style="width: 100%; height: 100%;">

		<div
			data-options="region:'west',split:false,title:'',collapsible:false,"
			style="width: 100px; text-align: center; padding: 0px;">

			<ul id="ul_leftmenu">
				<li><a> <img
						src="../Resource/Images/icon_DATA_BASIS_REALTIME_DATA.png" />
						<h2>当前数据</h2>
				</a></li>
				<li><a> <img
						src="../Resource/Images/icon_DATA_BASIS_HIS_DATA.png" />
						<h2>历史数据</h2>
				</a></li>
				<li><a> <img
						src="../Resource/Images/icon_DATA_BASIS_EXTRUMN_DATA.png" />
						<h2>极值数据</h2>
				</a></li>
				<li><a> <img
						src="../Resource/Images/icon_DATA_BASIS_ALARM_DATA.png" />
						<h2>报警数据</h2>
				</a></li>
				<li><a> <img
						src="../Resource/Images/icon_DATA_BASIS_RAW_DATA.png" />
						<h2>原始数据</h2>
				</a></li>
				<li><a> <img
						src="../Resource/Images/icon_DATA_BASIS_ANALYSIS_DATA.png" />
						<h2>数据分析</h2>
				</a></li>
			</ul>

		</div>

		<div
			data-options="region:'center',title:'',split:true,collapsible:true"
			style="width: 100px;">

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
			data-options="region:'east',split:true,title:'',collapsible:true,"
			style="width: 80%">

			<div id="contentTab" class="easyui-tabs" data-options="fit:true">



			</div>

		</div>

	</div>

	<!-- 左侧选中事件 -->
	<script>
		var curleftIndex = 0;//左侧选中的序号

		$(function() {

			$('#ul_leftmenu li').click(function() {
				//console.log(this);
				//console.log($(this));
				//console.log($(this).index());
				curleftIndex = $(this).index();
				//border 2px solid red

				//清空ul li下面的border

				$('#ul_leftmenu li').css("border", "none");

				$(this).css("border-top", "1px solid #63B8FF");
				$(this).css("border-bottom", "1px solid #63B8FF");

				CtrlTabContent();
			});

		})
	</script>

	<!-- 树节点加载 -->
	<script>
		var curNode = null;//当前的操作的节点

		$(function() {

			$('#mTree').tree({
				url : '../DataManageRequest?action=DataManageStructTree',
				method : 'get',
				onBeforeLoad : function(row, param) {
					if (!row) { // load top level rows
						param.structID = 0; // set id=0, indicate to load new page rows
					}
				},
				onBeforeExpand : function(row) {
					console.log(row);

					if (row.attributes) {
						$('#mTree').tree('options').queryParams = {
							structID : row.attributes.structID,
							monitorID : row.attributes.monitorID
						};
					} else {
						$('#mTree').tree('options').queryParams = {
							structID : row.id
						}
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
	//nTabindex:tab序号   nStructID:结构ID   nMonitorID:监测项目ID
	function objTabInfoItem(nTabindex, nStructID, nMonitorID) {
		this.nTabindex = nTabindex;
		this.nStructID = nStructID;
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
			var objItem=new objTabInfoItem(curTabIndex, curNode.attributes.structID, curNode.attributes.monitorID);
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
				if(objItem.nStructID==curNode.attributes.structID&&objItem.nMonitorID==curNode.attributes.monitorID){
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
										title : 'Tab' + curNode.text,
										content : '<iframe src="RealTimeData.jsp" style="width:100%;height:100%;" scrolling="yes" frameborder="0">'
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
					
					$('#contentTab')
					.tabs(
							'add',
							{
								title : 'Tab' + curNode.text,
								content : '<iframe src="HistoryData.jsp" style="width:100%;height:100%;" scrolling="yes" frameborder="0">'
										+ '</iframe>',
								closable : false
							});
					
					AddTabInfo();
					curTabIndex++;
				}
			}
		}
	</script>

	<!-- 数据获取 -->



</body>
</html>