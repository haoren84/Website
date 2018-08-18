<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>实时数据</title>

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

table {
            margin-top: 50px;
            margin-left: 15px;
            background-color: #fff;
            border-collapse: collapse;
            color: #666;
            font-size: 14px;
            font-weight: 400;
            font-style: normal;
            font-family: "Helvetica Neue", Helvetica, "PingFang SC", 微软雅黑, Tahoma, Arial, sans-serif;
        }

            table caption {
                height: 28px;
                background-color: #f2f2f2;
                color: black;
                font-weight: bold;
            }

                table caption p {
                    margin: 5px;
                }

            table tr {
            }

                table tr td {
                    height: 28px;
                    width: 150px;
                    border: 1px solid #e2e2e2;
                }
</style>

</head>
<body>

	<div id="titlepanel" class="easyui-panel" title="Basic Panel"
		style="width: 100%; height: 100%;" data-options="border:false,">

		<table id="tb_realtime" >

			<caption>监测项目</caption>
			<tr>
				<td>测点名称</td>
				<td>应变01</td>
				<td>应变02</td>
				<td>应变03</td>
				<td>应变04</td>
				<td>应变05</td>
				<td>应变06</td>
			</tr>
			<tr>
				<td>测量值（应变|单位）</td>
				<td>应变01</td>
				<td>应变02</td>
				<td>应变03</td>
				<td>应变04</td>
				<td>应变05</td>
				<td>应变06</td>
			</tr>

		</table>

	</div>

	<!-- 数据加载 -->
	<script>
		
	$(function() {
		
		if (window.parent != null && window.parent != undefined) {
			
			//根据父页面的数据确定加载的数据内容
			
			if(window.parent.curNode&&window.parent.curNode.attributes){
				
				$('#titlepanel').panel({
					
					title:'节点信息:'+window.parent.curNode.text,
					
				});
				
				LoadTableData(window.parent.curNode.attributes.monitorID,window.parent.curNode.attributes.monitorName);
				
			}
			
		}
		
	});
	
	function GetRealTimeData(monitorID,monitorName){
		 
		var result;

         $.ajax({
             url: "../DataManageRequest",
             async: false,//设置同步
             data: {
                 action: "GetDataManageRealtimeDataByMonitorName",
                 monitorName:monitorName,
                 monitorID:monitorID,
             },
             type: "get",
             datatype: "json",
             success: function (data) {
                 var obj = eval("(" + data + ")");

                 result = obj;

             },
             error: function () {
                 console.log('realtime data error!');
             },
         });

         return result;
	}
	
	
	function LoadTableData(monitorID,monitorName){
		
		var objRealTime=GetRealTimeData(window.parent.curNode.attributes.monitorID,window.parent.curNode.attributes.monitorName);
		
		//拼接页面的表格数据
		
		
		if(objRealTime){
			
			var nObjLength=objRealTime.length;
			
			var count=0;
			
			if(nObjLength>0){
				
				var strCaption = "<caption><p>监测项目：“" + objRealTime[0].monitorName + "”的最新数据</p></caption>";//标题
				
				if(objRealTime[0].realData&&objRealTime[0].realData.length>0){
					
					strCaption = "<caption><p>监测项目：“" + objRealTime[0].monitorName + "”在"+objRealTime[0].realData[0].SampleTime+"的采集数据</p></caption>";
					
				}
				
				document.getElementById("tb_realtime").innerHTML = strCaption;
				
				var strTr0 = "<tr style='coloc:black;font-weight:bold;'><td>测点名称</td>";
	            var strTr1 = "<tr><td>测量值</td>";
	            
	            for (var i = 0; i < nObjLength; i++) {
	            	
	            	if(objRealTime[i].realData){
	            		
	            		var nDataLength=objRealTime[i].realData.length;
	            		
	            		if(nDataLength>0){
	            			
	            			for(var j=0;j<nDataLength;j++){
	            				
	            				strTr0 += "<td>" + objRealTime[i].realData[j].monitorPtName + "</td>";
	            				strTr1 += "<td>" + objRealTime[i].realData[j].SensorValueName +":"+objRealTime[i].realData[j].SensorEUValue+ "</td>";
	            				
	            				count++;
	            				
	            				if(count==4||i==nObjLength-1){
	    		            		
	    			            	count=0;
	    			            	
	    			            	strTr0 += "</tr>";
	    		                    strTr1 += "</tr>";
	    		                    
	    		                    document.getElementById("tb_realtime").innerHTML += strTr0;
	    		                    document.getElementById("tb_realtime").innerHTML += strTr1;
	    		                    
	    		                    strTr0 = "<tr style='coloc:black;font-weight:bold;'><td>测点名称</td>";
	    		    	            strTr1 = "<tr><td>测量值</td>";
	    			            }
	            			}
	            		}else{
	            			strTr0 += "<td>" + objRealTime[i].mpointName + "</td>";
		            		strTr1 += "<td>暂无</td>";
		            		count++;
		            		
		            		if(count==4||i==nObjLength-1){
			            		
				            	count=0;
				            	
				            	strTr0 += "</tr>";
			                    strTr1 += "</tr>";
			                    
			                    document.getElementById("tb_realtime").innerHTML += strTr0;
			                    document.getElementById("tb_realtime").innerHTML += strTr1;
			                    
			                    strTr0 = "<tr style='coloc:black;font-weight:bold;'><td>测点名称</td>";
			    	            strTr1 = "<tr><td>测量值</td>";
				            }
	            		}
	            		
	            		
	            		
	            	}else{
	            		
	            		strTr0 += "<td>" + objRealTime[i].mpointName + "</td>";
	            		strTr1 += "<td>暂无</td>";
	            		count++;
	            		
	            		if(count==4||i==nObjLength-1){
		            		
			            	count=0;
			            	
			            	strTr0 += "</tr>";
		                    strTr1 += "</tr>";
		                    
		                    document.getElementById("tb_realtime").innerHTML += strTr0;
		                    document.getElementById("tb_realtime").innerHTML += strTr1;
		                    
		                    strTr0 = "<tr style='coloc:black;font-weight:bold;'><td>测点名称</td>";
		    	            strTr1 = "<tr><td>测量值</td>";
			            }
	            	}
	            	
	            	
	            }
	            
	            
				
			}
			
			
		}
		
	}
	
	</script>

</body>
</html>