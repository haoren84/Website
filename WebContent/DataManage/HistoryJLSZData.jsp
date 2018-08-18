<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>静力水准仪的历史数据</title>

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

						title : '节点信息:' + window.parent.curNode.attributes.monitorName,

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

		});
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
	
	<!-- 获取表格数据的列的数据信息 -->
	<script>
	
	var array =[];
	var columns=[];
	
	//获取静力水准仪的测点
	function GetJLSZMPointName(monitorName){
		
		var obj=null;
		
		$.ajax({
            url: "../DataManageRequest",
            async: false,//设置同步
            data: {
                action: "GetJLSZMPointName",
                monitorName:monitorName,
            },
            type: "get",
            datatype: "json",
            success: function (data) {
                obj = eval("(" + data + ")");
            },
            error: function () {
                console.log('data error!');
            },
        });
		
		return obj;		
	}
	
	//设置静力水准仪的columns
	function SetJLSZColumns(monitorName){
		
		var obj=GetJLSZMPointName(monitorName);
		
		if(obj){
			
			array =[];
			columns=[];
        	
        	var nObjLength=obj.length;
        	
        	for(var i=0;i<nObjLength+2;i++){
        		
        		array.push({field:'',title:'',width:''});
        		
        	}
        	
        	columns.push(array);
        	
        	//监测项目名称
        	columns[0][0].field='monitorName';
        	columns[0][0].title='监测项目名称';
        	columns[0][0].width='200';
        	
        	//采样时间
        	columns[0][1].field='SampleTime';
        	columns[0][1].title='采样时间';
        	columns[0][1].width='200';
        	
        	//测点
        	for(var i=2;i<nObjLength+2;i++){
        		columns[0][i].field=obj[i-2].monitorPtName;
            	columns[0][i].title='测点:'+obj[i-2].monitorPtName;
            	columns[0][i].width='200';
        	}
        }
	}
	
	</script>
	
	<!-- 加载表格数据 -->
	<script>
	
	function LoadTable(){
		
		//获取页面的信息
		var monitorName=window.parent.curNode.attributes.monitorName;//监测项目名称
		var startTime=$('#dt_starttime').datetimebox('getValue');
		var endTime=$('#dt_endtime').datetimebox('getValue');
		
		//获取列的数据
		SetJLSZColumns(monitorName);
		
		
		$('#tb_historydata').datagrid({
			url:"../DataManageRequest",
			queryParams: {
                action: 'GetHistoryJLSZSamplePageData',
                monitorName:monitorName,
                startTime:startTime,
                endTime:endTime
            },
			method:'get',
			rownumbers:true,
			singleSelect:true,
			pagination:true,
			fitColumns:true,
			columns : columns,
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
					action : "GetHistoryJLSZSamplePageData",
					monitorName:monitorName,
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
	
	
	//初始化
	function InitChart(){
		
		myChart = echarts.init(document.getElementById('datacontainer'));
		
		
	}
	
	function InitOption(){
		 var option = {
	                title: {
	                    text: ''
	                },
	                grid: {
	                    show: true,
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

	                            paramsResult += params[0].name + "</br>";

	                            for (var i = 0; i < paramsLength; i++) {

	                                var para = params[i];

	                                paramsResult += para.seriesName + ':' + para.data + "</br>";

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

	                xAxis: {
	                    type: 'category',
	                    splitLine: {
	                        show: true
	                    },
	                    data: []
	                },
	                yAxis: {
	                    type: 'value',
	                    boundaryGap: [0, '100%'],
	                    splitLine: {
	                        show: true
	                    }
	                },
	                series: []
	            };

	            return option;
	}
	
	//加载图表
	function LoadChart(){
		
		if(!myChart){
			InitChart();
		}
		
		var option=InitOption();
		
		//清空
		
		myChart.clear();
		
		var JLSZmptNames=GetJLSZMPointName(window.parent.curNode.attributes.monitorName);
		
		//采样数据赋值
		
		var objChartData=GetHistoryChartData();
		
		var nJLSZDataLength = objChartData.length;
		
		for (var i = 0; i < nJLSZDataLength; i++) {
			
			 var JLSZserieitem = {
                     type: 'line',
                     symbol: 'circle',
                     symbolSize: 10,
                     data: [],
                     name: objChartData[i].SampleTime,
                 }
			
			 for (var j = 0; j < JLSZmptNames.length; j++) {
				 
				 JLSZserieitem.data.push(objChartData[i][JLSZmptNames[j].monitorPtName]);
				 
			 }
			 option.series.push(JLSZserieitem);

             option.legend.data.push(objChartData[i].SampleTime);
			
		}
		
		myChart.setOption(option);
		
	}
	
	//获取历史数据的图表数据
	function GetHistoryChartData(){
		
		//获取页面的信息
		var monitorName=window.parent.curNode.attributes.monitorName;//监测项目名称
		var startTime=$('#dt_starttime').datetimebox('getValue');
		var endTime=$('#dt_endtime').datetimebox('getValue');
		
		var result;
		
		$.ajax({
            url: "../DataManageRequest",
            async: false,//设置同步
            data: {
                action: "GetHistoryJLSZChartData",
                monitorName:monitorName,
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
                console.log('history JLSZ data error!');
            },
        });

        return result;
	}
	
	</script>

	

</body>
</html>