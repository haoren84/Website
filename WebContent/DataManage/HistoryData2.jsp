<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>历史数据总揽</title>

<link rel="stylesheet" type="text/css"
	href="../Resource/jquery-easyui-1.5.4.2/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="../Resource/jquery-easyui-1.5.4.2/themes/icon.css">

<script src="../Resource/jquery-easyui-1.5.4.2/jquery.min.js"></script>
<script src="../Resource/jquery-easyui-1.5.4.2/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="../Resource/jquery-easyui-1.5.4.2/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="../Resource/jshelper/commonfun.js"></script>
<script type="text/javascript" src="../Resource/jshelper/echarts.js"></script>

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
<!-- http://testelectron.eicp.net:8080/MonitorWeb/DataManage/HistoryData.jsp?projectId=4&projectName=%E6%88%BF%E5%B1%8B%E7%9B%91%E6%B5%8Bwww -->
<body>

	<div id="titlepanel" class="easyui-panel" title="Basic Panel"
		style="width: 100%; height: 100%;" data-options="border:false,">
		<!-- 筛选区域 -->
		<div class="easyui-accordion" style="width: 100%; height: 80px;"
			data-options="border:false,"
		>
			<div title="筛选条件"
				data-options="iconCls:'icon-search',collapsed:false,collapsible:false"
				style="padding: 10px;">
				<!-- <input class="easyui-searchbox" prompt="Enter something here" style="width:300px;height:25px;"> -->
				<input id="cc_mpoint" class="easyui-combobox" name="dept"
					label="测点：" labelPosition="left" style="width: 200px;"> 
				&nbsp;&nbsp;&nbsp;&nbsp;
				<input
					id="cc_sensorvalue" class="easyui-combobox" name="dept"
					label="测量量：" labelPosition="left" style="width: 200px;"> 
				&nbsp;&nbsp;&nbsp;&nbsp;
				<input
					class="easyui-datetimebox" label="开始时间:" labelPosition="left"
					style="width: 240px;" id="dt_starttime"> 
				&nbsp;&nbsp;&nbsp;&nbsp;
				<input class="easyui-datetimebox"
					label="结束时间:" labelPosition="left" style="width: 240px;" id="dt_endtime">
				&nbsp;&nbsp;&nbsp;&nbsp;
				<a
					href="#" class="easyui-linkbutton" id="btn_search"
					data-options="iconCls:'icon-search'" style="width: 80px">查询</a>
			</div>
		</div>

		<!-- 数据显示区域-->
		<div id="ac_datadisplay" class="easyui-accordion" style="width: 100%;" data-options="border:false,">

			<!-- <div title="数据显示"
				data-options="iconCls:'icon-large-chart',collapsed:false,collapsible:false,border:false,"
				style="padding: 0px;width: 100%;height:500px;border:none;" > -->
				
			<div title="数据-折线显示"
				data-options="iconCls:'icon-large-chart',collapsed:true,collapsible:true,border:false,"
				style="padding: 0px;width: 100%;height:500px;border:none;" >

				<div style="width: 100%; height: 100%;">
				<div id="datacontainer" style="width: 100%; height: 400px;"></div>
				</div>

			</div>
			
			<div title="数据-表格显示"
				data-options="iconCls:'icon-large-smartart',collapsed:true,collapsible:true,border:false,"
				style="padding: 0px;width: 100%;height:500px;border:none;" >

				<table id="tb_historydata" style="width: 100%; height: 100%;">
					
				</table>

			</div>

		</div>


	</div>

	<!-- 初始查询条件数据加载 -->
	<script>
	
		$(function() {
			
			$('#btn_search').bind('click', function(){
				//LoadChart();
				LoadData();
		    });
			
			var curDate = new Date();

            var preDate = new Date(curDate.getTime() - 24 * 60 * 60 * 1000); //前一天
            
			//设置默认时间
			$('#dt_starttime').datetimebox({
				value:getFormatDate(preDate),
			});
            
			$('#dt_endtime').datetimebox({
				value:getFormatDate(curDate),
			});
			

			if (window.parent != null && window.parent != undefined) {

				//根据父页面的数据确定加载的数据内容

				if (window.parent.curNode && window.parent.curNode.attributes) {

					$('#titlepanel').panel({

						title : '节点信息:' + window.parent.curNode.text,

					});

					//加载测点数据

					
					var nMonitorID = window.parent.curNode.attributes.monitorID;
					var strMonotorName=window.parent.curNode.attributes.monitorName;

					//获取对应的测点信息

					$('#cc_mpoint')
							.combobox(
									{
										url : '../DataManageRequest?action=GetMPointComboDataByMonitorIDAndMonitorName&monitorName='
												+ strMonotorName
												+ '&monitorID='
												+ nMonitorID,
										valueField:'id',
										textField:'text',
										onSelect:function(record){
											//console.log(record);
											//更新测量量数据
											
											$('#cc_sensorvalue').combobox({
												url:'../DataManageRequest',
												queryParams:{
													action:'GetSensorValueNameComboData',
													monitorName:window.parent.curNode.attributes.monitorName,
													mpointName:record.text
												},
												valueField:'id',
												textField:'text',
												onLoadSuccess:function(none){
													if(none&&none.length){
														
														$('#cc_sensorvalue').combobox('select',none[0].id);
														
													}
													
													//LoadChart();
													LoadData();
												}
											});
										},
										onLoadSuccess:function(none){
											//console.log(none);
											
											if(none&&none.length){
												$('#cc_mpoint').combobox('select',none[0].id);
											}
											
										}
									
									});
				}

			}
			
			/* InitChart();
			InitOption();
			
			var serie = {
	                type: 'line',
	                showSymbol: false,
	                hoverAnimation: false,
	                data: [],
	                name: '采样数据',
	            }

	         option.series.push(serie);

	         option.legend.data.push('采样数据');
	         
	         var seriesdata = new Array();
				
		     option.series[0].data=seriesdata;
				
			 myChart.setOption(option); */
			 
			$('#ac_datadisplay').accordion({
				onSelect:function(title,index){
					LoadData();
				}
			});

		});
	</script>
	
	<script>
	//获取选择的测点名称
	function GetCurSelectMPointName(){
		var mpointName=null;
		
		var mpointComboID=$('#cc_mpoint').combobox('getValue');//测点名称
		
		var mpointComboData=$('#cc_mpoint').combobox('getData');
		
		if(mpointComboID&&mpointComboData&&mpointComboData.length){
			
			var nlength=mpointComboData.length;
			
			for(var i=0;i<nlength;i++){
				
				if(mpointComboData[i].id==mpointComboID){
					mpointName=mpointComboData[i].text;
					return mpointName;
				}
			}
		}
		return mpointName;
	}
	
	//获取选中的测量量
	function GetCurSelectSensorValueName(){
		var sensorValueName=null;
		
		var curid=$('#cc_sensorvalue').combobox('getValue');
		
		var ComboData=$('#cc_sensorvalue').combobox('getData');
		
		if(curid&&ComboData&&ComboData.length){
		var nlength=ComboData.length;
			
			for(var i=0;i<nlength;i++){
				
				if(ComboData[i].id==curid){
					sensorValueName=ComboData[i].text;
					return sensorValueName;
				}
			}
		}
		
		return sensorValueName;
	}
	</script>

	<!-- 判断加载数据 -->
	<script>
	
	function LoadData(){
		
		//判断那个是下拉的
		
		var index=$('#ac_datadisplay').accordion('getSelected');
		
		//console.log("one:"+index);
		
		var nIndex=$('#ac_datadisplay').accordion('getPanelIndex',index);
		
		//console.log("one:"+nIndex);
		
		if(nIndex<0){
			
			//默认加载第一个
			
			$('#ac_datadisplay').accordion('select',0);
			
			LoadChart();
			
		}else if(nIndex==0){
			
			LoadChart();
			
		}else if(nIndex==1){
			
			LoadTable();
			
		}
		
		
		
		
		
	}
	
	</script>
	
	<!-- 加载表格数据 -->
	<script>
	
	function LoadTable(){
		
		//获取页面的信息
		var monitorName=window.parent.curNode.attributes.monitorName;//监测项目名称
		var mpointName=GetCurSelectMPointName();//测点名称
		var sensorValueName=GetCurSelectSensorValueName();//$('#cc_sensorvalue').combobox('getValue');//测量量名称
		var startTime=$('#dt_starttime').datetimebox('getValue');
		var endTime=$('#dt_endtime').datetimebox('getValue');
		
		$('#tb_historydata').datagrid({
			url:"../DataManageRequest",
			queryParams: {
                action: 'GetHistorySamplePageData',
                monitorName:monitorName,
                mpointName:mpointName,
                sensorValueName:sensorValueName,
                startTime:startTime,
                endTime:endTime
            },
			method:'get',
			rownumbers:true,
			singleSelect:true,
			pagination:true,
			fitColumns:true,
			columns : [ [ {
				field : 'monitorName',
				title : '监测项目名称',
				width : 200
			}, {
				field : 'monitorPtName',
				title : '测点名称',
				width : 100
			}, {
				field : 'SensorEUValue',
				title : '采样值',
				width : 100
			}, {
				field : 'SampleTime',
				title : '采样时间',
				width : 100
			} ] ],
		});
		
		var tbhistorypager = $('#tb_historydata').datagrid(
		'getPager');
		
		
		tbhistorypager.pagination({
			
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
				$('#tb_historydata').datagrid('options').pageSize = pageSize;
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
				url : "../DataManageRequest",
				/* data: "page=" + page + "&rows=" + rows + "&keyword=" + $('#hdKeyword').val(), */
				async : false,//设置同步
				data : {
					action : "GetHistorySamplePageData",
					monitorName:monitorName,
		            mpointName:mpointName,
		            sensorValueName:sensorValueName,
		            startTime:startTime,
		            endTime:endTime,
					page : page,
					rows : rows,
				},
				error : function(XMLHttpRequest, textStatus,
						errorThrown) {
					alert(textStatus);
					$.messager.progress('close');
				},
				success : function(data) {
					$('#tb_historydata').datagrid('loadData', $.parseJSON(data));
				}
			});
		};
	}
	
	</script>


	<!-- 加载图表数据 -->
	<script>
	
	
	
	var myChart=null;
	var option=null;
	
	//初始化
	function InitChart(){
		
		myChart = echarts.init(document.getElementById('datacontainer'));
		
		
	}
	
	function InitOption(){
		option = {
                title: {
                    text: '数据趋势'
                },
                legend: {
                    data: [],
                },
                tooltip: {
                    trigger: 'axis',
                    formatter: function (params) {

                        if (params.length > 0) {

                            //console.log(params);

                            var paramsLength = params.length;

                            if (paramsLength == undefined) return "";

                            var paramsResult = "";

                            for (var i = 0; i < paramsLength; i++) {

                                var para = params[i];

                                paramsResult += para.seriesName + '|X:' + para.value[0] + ',Y:' + para.value[1] + "</br>";

                            }

                            return paramsResult;

                            //var para = params[0];

                            //return para.seriesName + '-time:' + para.value[0] + ',sample:' + para.value[1];
                        }
                    },
                    axisPointer: {
                        animation: false
                    }
                },
                toolbox:{
                	feature: {
                		myTool1: {
                            show: true,
                            title: '切换样式',
                            icon: 'path://M30.9,53.2C16.8,53.2,5.3,41.7,5.3,27.6S16.8,2,30.9,2C45,2,56.4,13.5,56.4,27.6S45,53.2,30.9,53.2z M30.9,3.5C17.6,3.5,6.8,14.4,6.8,27.6c0,13.3,10.8,24.1,24.101,24.1C44.2,51.7,55,40.9,55,27.6C54.9,14.4,44.1,3.5,30.9,3.5z M36.9,35.8c0,0.601-0.4,1-0.9,1h-1.3c-0.5,0-0.9-0.399-0.9-1V19.5c0-0.6,0.4-1,0.9-1H36c0.5,0,0.9,0.4,0.9,1V35.8z M27.8,35.8 c0,0.601-0.4,1-0.9,1h-1.3c-0.5,0-0.9-0.399-0.9-1V19.5c0-0.6,0.4-1,0.9-1H27c0.5,0,0.9,0.4,0.9,1L27.8,35.8L27.8,35.8z',
                            onclick: function (){
                            	
                            	var curoption=myChart.getOption();
                            	
                            	/* if(curoption&&curoption.series&&curoption.series.length&&curoption.series.length>0){
                            		
                            		if(curoption.series[0].lineStyle.type=='solid'){
                            			curoption.series[0].lineStyle.type='dashed';
                            			curoption.series[0].lineStyle.normal.type="dashed";
                            		}else{
                            			curoption.series[0].lineStyle.type='solid';
                            			curoption.series[0].lineStyle.normal.type="solid";
                            		} */
                            		
                            		if(curoption&&curoption.series&&curoption.series.length&&curoption.series.length>0){
                            			
                            			if(curoption.series[0].showSymbol){
                            				curoption.series[0].showSymbol=false;
                            			}else{
                            				curoption.series[0].showSymbol=true;
                            			}	
                            			
                            			myChart.setOption(curoption);
                            		}
                            		
                            		
                            		
                            	
                            }
                        },
                        dataZoom: {
                            yAxisIndex: 'none'
                        },
                        /* dataView: {readOnly: false}, */
                        restore: {},
                        
                	},
                	left:'70%',
                },
                xAxis: {
                    type: 'time',
                    splitLine: {
                        show: false
                    }
                },
                yAxis: {
                    type: 'value',
                    boundaryGap: [0, '100%'],
                    splitLine: {
                        show: false
                    }
                },
                series: []
            };
	}
	
	//加载图表
	function LoadChart(){
		
		if(!myChart){
			InitChart();
		}
		
		InitOption();
		
		//清空
		myChart.clear();
		
		var strDataName=GetCurSelectMPointName()+":"+GetCurSelectSensorValueName();
		
		var serie = {
                type: 'line',
                showSymbol: true,
                hoverAnimation: false,
                lineStyle:{
                	type:'solid',
                },
                data: [],
                name: strDataName,
            }

         option.series.push(serie);

         option.legend.data.push(strDataName);
		
		//赋值
		
		var objChartData=GetHistoryChartData();
		
		if(objChartData&&objChartData.length){
			
			var nDataLength=objChartData.length;
			
			var seriesdata = new Array(nDataLength);
			
			for (var j = 0; j < nDataLength; j++) {
                var point = new Array(2);
                point[0] = objChartData[j].SampleTime;
                point[1] = parseFloat(objChartData[j].SampleValue);
                seriesdata[j] = point;
            }
			
			option.series[0].data=seriesdata;
		}
		
		myChart.setOption(option);
		
	}
	
	//获取历史数据的图表数据
	function GetHistoryChartData(){
		
		//获取页面的信息
		var monitorName=window.parent.curNode.attributes.monitorName;//监测项目名称
		var mpointName=GetCurSelectMPointName();//测点名称
		var sensorValueName=GetCurSelectSensorValueName();//$('#cc_sensorvalue').combobox('getValue');//测量量名称
		var startTime=$('#dt_starttime').datetimebox('getValue');
		var endTime=$('#dt_endtime').datetimebox('getValue');
		
		var result;
		
		$.ajax({
            url: "../DataManageRequest",
            async: false,//设置同步
            data: {
                action: "GetHistoryChartData",
                monitorName:monitorName,
                mpointName:mpointName,
                sensorValueName:sensorValueName,
                startTime:startTime,
                endTime:endTime
            },
            type: "get",
            datatype: "json",
            success: function (data) {
                var obj = eval("(" + data + ")");

                result = obj;

            },
            error: function () {
                console.log('history data error!');
            },
        });

        return result;
	}
	
	</script>




</body>
</html>