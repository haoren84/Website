<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>文件格式的历史数据页面</title>

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
		
		
		<div class="easyui-layout" style="width:100%;height:500px;">
			
			<div data-options="region:'west',title:'文件数据',split:true,collapsible:false," style="width:50%;">
				<table id="tb_historyfiledata" style="width: 100%; height: 100%;">
					
				</table>
			
			</div>
			
			<div data-options="region:'center',title:'数据显示',split:true,collapsible:false," style="width:50%;">
			<div id="datacontainer" style="width: 100%; height: 400px;"></div>
			</div>
		
		</div>
	
		
		<!-- 文件表格 
		<table id="tb_historyfiledata" style="width: 50%; height: 500px;"></table>-->
		
		<!-- 折线显示 
		<div id="datacontainer" style="width: 50%; height: 500px;"></div>-->
		
		
</div>

	<script>
		
	$(function() {
		
		var curDate = new Date();

        var preDate = new Date(curDate.getTime() - 24 * 60 * 60 * 1000); //前一天
        
      //设置默认时间
		$('#dt_starttime').datetimebox({
			value:getFormatDate(preDate),
		});
        
		$('#dt_endtime').datetimebox({
			value:getFormatDate(curDate),
		});
		
		$('#btn_search').bind('click', function(){
			//LoadChart();
			LoadData();
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
		
		
	});


	</script>
	
	<script>
	
	//加载数据
	function LoadData(){
		
		LoadHistoryTable();
		
	}
	
	var tb_toolbar=[
		{
			text : '查看文件数据',
			iconCls : 'icon-search',
			handler : function() {
				 var objSelect = $('#tb_historyfiledata').datagrid()
						.datagrid('getSelected'); 
				 
				 console.log(objSelect);
			 //MonitorTable_AddMonitorPointBtn();
			 
				 ReadFileDataChart();
			}
		}
	]; 
	
	//加载历史表格
	function LoadHistoryTable(){
		
		//获取页面的信息
		var monitorName=window.parent.curNode.attributes.monitorName;//监测项目名称
		var mpointName=$('#cc_mpoint').combobox('getText');//测点名称
		var sensorValueName=$('#cc_sensorvalue').combobox('getText');//测量量名称
		var startTime=$('#dt_starttime').datetimebox('getValue');
		var endTime=$('#dt_endtime').datetimebox('getValue');
		
		$('#tb_historyfiledata').datagrid({
			url:"../DataManageRequest",
			queryParams: {
                action: 'GetHistoryFileTableData',
                monitorName:monitorName,
                mpointName:mpointName,
                sensorValueName:sensorValueName,
                startTime:startTime,
                endTime:endTime
            },
			method:'get',
			toolbar:tb_toolbar,
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
				field : 'SensorEUValueFilePath',
				title : '采样文件地址',
				width : 100
			}, {
				field : 'SampleTime',
				title : '采样时间',
				width : 100
			} ] ],
		});
		
		var tbhistorypager = $('#tb_historyfiledata').datagrid(
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
				$('#tb_historyfiledata').datagrid('options').pageSize = pageSize;
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
					action : "GetHistoryFileTableData",
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
					$('#tb_historyfiledata').datagrid('loadData', $.parseJSON(data));
				}
			});
		};
		
		
	}
	
	</script>
	
	<!-- 绘图相关 -->
	<script>
	
	var myChart=echarts.init(document.getElementById('datacontainer'));
	
	//动态chart的option
    function InitDynamicOption(dataSampleTime) {
		
    	var option = {
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

                                paramsResult += para.seriesName + '|X:' + dataSampleTime + ',Y:' + para.value[1] + "</br>";

                            }

                            return paramsResult;

                            //var para = params[0];

                            //return para.seriesName + ':' + para.value[1];
                        }
                    },
                    axisPointer: {
                        animation: false
                    }
                },
                dataZoom: [
                    {
                        type: 'slider',
                        show: true,
                        xAxisIndex: [0],
                        start: 0,
                        end: 100,
                        filterMode: 'none',
                    },
                ],
                xAxis: {
                    type: 'value',
                    show: true,
                    //maxInterval: 1000,//x轴最大间隔大小设为1000ms
                    //minInterval: 1,//x轴最小间隔大小设为1ms
                    axisTick: {
                        show: false,
                    },
                    axisLabel: {
                        show: false,
                    },
                    splitLine: {
                        show: false
                    }
                },
                yAxis: {
                    type: 'value',
                    scale: true,
                    boundaryGap: ['20%', '20%'],
                    splitLine: {
                        show: false
                    }
                },
                series: []
            };

            return option;
		
	}
		
	//读取文件数据
	function ReadFileDataChart(){
		
		var fileObj=GetChnFilePath();
		
		if(fileObj&&fileObj.result&&fileObj.result!="nofile"){
			
			//根据解压的文件地址读取数据
			
			var objSelect = $('#tb_historyfiledata').datagrid()
			.datagrid('getSelected'); 
			
			var myoption=InitDynamicOption(objSelect.SampleTime);
			
			var chartData=GetFileChartData(fileObj.result);
			
			if(chartData&&chartData.total){
				
				myChart.clear();
				
				var serie = {
                        type: 'line',
                        showSymbol: false,
                        hoverAnimation: false,
                        data: [],
                        name: objSelect.monitorPtName,
                    }
				
				myoption.series.push(serie);

				myoption.legend.data.push(objSelect.monitorPtName);
				
				var seriesdata = new Array(chartData.total);
				
				for (var j = 0; j < chartData.total; j++) {
	                var point = new Array(2);
	                point[0] = chartData.sampledata[j].sampletime;
	                point[1] = parseFloat(chartData.sampledata[j].samplevalue);
	                seriesdata[j] = point;
	            }
				
				myoption.series[0].data=seriesdata;
				
				myChart.setOption(myoption);
				
			}
			
		}
		
	}
	
	//获取对应的tim文件
	function GetChnFilePath(){
		
		var objSelect = $('#tb_historyfiledata').datagrid()
		.datagrid('getSelected'); 
		
		if(objSelect){
			
			var result;
			
			$.ajax({
	            url: "../DataManageRequest",
	            async: false,//设置同步
	            data: {
	                action: "GetHistoryFilePath",
	                chnID:objSelect.chnID,
	                SeneorEUValueFilePath:objSelect.SensorEUValueFilePath,
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
	}
	
	//获取文件的chart数据
	function GetFileChartData(strChartFile){
		
		var result;
		
		$.ajax({
            url: "../DataManageRequest",
            async: false,//设置同步
            data: {
                action: "GetHistoryFileChartData",
                ChartFilePath:strChartFile,
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