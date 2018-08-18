<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>动态实时数据</title>

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
		<input id="cc_sensorvalue" class="easyui-combobox" name="dept"
					label="测量量：" labelPosition="left" style="width: 200px;"> 
				
		</div>
		
		</div>
		
		<!-- 数据显示区域-->
		<div id="ac_datadisplay" class="easyui-accordion" style="width: 100%;" data-options="border:false,">
		
			<div title="数据-动态折线显示"
				data-options="iconCls:'icon-large-chart',collapsed:false,collapsible:false,border:false,"
				style="padding: 0px;width: 100%;height:500px;border:none;" >

				<div style="width: 100%; height: 100%;">
				<div id="datacontainer" style="width: 100%; height: 400px;"></div>
				</div>

			</div>
		
		</div>
	</div>
	
	<script>
	
	var monitorName,mpointName,sensorValueName;
	var startTime=new Date(new Date().getTime() - 30 * 1000);
	var endTime;
	var myChart = echarts.init(document.getElementById('datacontainer'));
	var curCount;//当前记录的数据个数
	var recordTime = "";
	
	
	$(function() {
		
		if (window.parent != null && window.parent != undefined) {
			
			$('#titlepanel').panel({

				title : "实时数据："+window.parent.curNode.text,

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
		
		
	});
		
	</script>
	
	<script>
	
	//chart的option
    function InitOption() {
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

                            //console.log(getFormatDate(new Date(curTime.getTime() + parseInt(para.value[0]))));

                            //paramsResult += para.seriesName+ '|X:' + para.value[0] + ',Y:' + para.value[1] + "</br>";
                            paramsResult += para.seriesName + '|X:' + getFormatDate(new Date(recordTime.getTime() + parseInt(para.value[0])*20)) + ',Y:' + para.value[1] + "</br>";
                            
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
            grid: {
                show:true,

            },
            xAxis: {
                type: 'value',
                name: '',
                nameLocation: 'middle',
                splitLine: {
                    show: true,
                },
                max: '',
                axisLabel: {
                    show:false,
                },
            },
            yAxis: {
                type: 'value',
                name: '',
                boundaryGap: ['50%', '50%'],
                splitLine: {
                    show: true,
                },
                min: function(value) {
                    return value.min * 0.8;
                },
                max: function(value) {
                    return value.max * 1.2;
                },
                splitNumber:10,
                //maxInterval: (max-min)/10,
                //minInterval: (max-min)/10,
                axisTick: {

                },
                axisLabel: {

                    showMinLabel:false,
                    showMaxLabel:false,
                    //formatter: function (value, index) {



                    //}
                },
                
            },
            series: []
        };

        return option;
    }
	
	</script>
	
	<script>
	
	//页面加载的信息
	function LoadHtmlInfo(){
		
		monitorName=window.parent.curNode.attributes.monitorName;//监测项目名称
		mpointName=$('#cc_mpoint').combobox('getText');
		sensorValueName=$('#cc_sensorvalue').combobox('getText');
		
	}
	
	function GetSampleData(){
		
		var dtCurTime = new Date(startTime);

		endTime = getFormatDate(new Date(dtCurTime.getTime() + 5 * 1000));
		
		 var result;
		 
		 $.ajax({
             url: "../DataManageRequest",
             async: false,//设置同步
             data: {
                 action: "GetRealTimeDynamicChartData",
                 monitorName: monitorName,
                 mpointName: mpointName,
                 sensorValueName: sensorValueName,
                 startTime: getFormatDate(dtCurTime),
                 endTime:endTime,
                 curCount:curCount,
             },
             type: "get",
             datatype: "json",
             success: function (data) {
                 var obj = eval("(" + data + ")");

                 result = obj;

             },
             error: function () {
                 console.log('cols data error!');
             },
         });

		 startTime = new Date(endTime);

         return result;
		
	}
	
	//加载数据
	function LoadData(){
		
		LoadHtmlInfo();
		
		curCount=0;
		
		var option = InitOption();
		
		var strDataName = monitorName;
		
		var serie = {
                type: 'line',
                showSymbol: false,
                hoverAnimation: false,
                data: [],
                name: strDataName,
            }

            option.series.push(serie);

            option.legend.data.push(strDataName);
            
          //开始创建新的chart

           myChart.clear();
          
          //设置x轴的范围
           
           option.xAxis.max = 200;
          
           recordTime = new Date(startTime);//记录时间
          
           option.xAxis.name = getFormatDate(recordTime) + '-' + getFormatDate(startTime); 
           
           var objRealSampleData = GetSampleData();
           
           if (objRealSampleData && objRealSampleData.length) {
        	   
        	   var nDataLength = objRealSampleData.length;

               var nSeriesLength = option.series.length;

               for (var i = 0; i < nDataLength; i++) {

                   //var sampledata = new Array();
                   var point = new Array(2);
                   point[0] = objRealSampleData[i].SampleTime;
                   point[1] = parseFloat(objRealSampleData[i].SampleValue);
                   //sampledata.push(point);
                   
                   for (var j = 0; j < nSeriesLength; j++) {

                       var itemName = mpointName;

                       option.series[j].data.push(point);
                    	   
                       curCount++;
						 
                   }
               }
        	   
        	   
           }
           
           myChart.setOption(option);
            
	}
	
	</script>
	
	<script>
	
	var nTimeCount=0;
	
	//定时刷新
    setInterval(function () {
    	
    	if(nTimeCount>3)
    	{
    	var option = myChart.getOption();

        var objRealSampleData = GetSampleData();

        if (objRealSampleData && objRealSampleData.length) {

            //判断是否超载

            var strCurMax = option.xAxis[0].max; //new Date(option.xAxis[0].max);

            var strDataTime = objRealSampleData[0].SampleTime;//new Date(objRealSampleData[0].strSampleTime);

            if (strCurMax < strDataTime) {

                ReflashData();

                return;

            }

            

            var nDataLength = objRealSampleData.length;

            var nSeriesLength = option.series.length;

            for (var i = 0; i < nDataLength; i++) {

                //var sampledata = new Array();
                var point = new Array(2);
                point[0] = objRealSampleData[i].SampleTime;
                point[1] = parseFloat(objRealSampleData[i].SampleValue);
                //sampledata.push(point);

                for (var j = 0; j < nSeriesLength; j++) {

                    
                    	var itemName = mpointName;

                    
                        option.series[j].data.push(point);

                        curCount++;

                        
                }
            }

        }

        option.xAxis[0].name = getFormatDate(recordTime) + '-' + getFormatDate(startTime);

        myChart.setOption(option);
        
        nTimeCount=0;
        
        }else{
        	
        	nTimeCount++;
        	
        	}

        

        
    }, 3000);
	
	</script>

</body>
</html>