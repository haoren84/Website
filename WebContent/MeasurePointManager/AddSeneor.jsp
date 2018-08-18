<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增传感器</title>

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
</style>

</head>
<body>

	<!-- <div id="dlg"  style="width:100%">
      <form id="fm" method="post" novalidate style="margin:0;padding:20px 50px">
          <div style="margin-bottom:10px">
              <input id="SensorID" class="easyui-textbox" required="true" label="名称:" style="width:100%">
          </div>
          <div style="margin-bottom:10px">
              <select id="SensorMeasureType" class="easyui-combobox" required="true" label="测量类型:" style="width:100%">
              		<option value="应变">应变</option>
					<option value="温度">温度</option>
					<option value="倾角">倾角</option>
					<option value="电流">电流</option>
					<option value="索力">索力</option>
					<option value="振弦">振弦</option>
					<option value="风速">风速</option>
					<option value="桥式传感器">桥式传感器</option>
					<option value="静力水准仪">静力水准仪</option>
              </select>
          </div>
          <div style="margin-bottom:10px">
              <input id="SensorSpec" class="easyui-textbox" required="true" label="规格:" style="width:100%">
          </div>
          <div style="margin-bottom:10px">
              <input id="SensorFactory" class="easyui-textbox" required="true" label="生产厂家:" style="width:100%">
          </div>
          <div style="margin-bottom:10px">
              <input id="Param1" class="easyui-numberbox" required="true" label="指标1:" style="width:100%">
          </div>
          <div style="margin-bottom:10px">
              <input id="Param2" class="easyui-numberbox" required="true" label="指标2:" style="width:100%">
          </div>
          <div style="margin-bottom:10px">
              <input id="Param3" class="easyui-numberbox" required="true" label="指标3:" style="width:100%">
          </div>
          <div style="margin-bottom:10px">
              <input id="Param4" class="easyui-numberbox" required="true" label="指标4:" style="width:100%">
          </div>
          <div style="margin-bottom:10px">
              <input id="Param5" class="easyui-numberbox" required="true" label="指标5:" style="width:100%">
          </div>
          <div style="margin-bottom:10px">
              <input id="Param6" class="easyui-numberbox" required="true" label="指标6:" style="width:100%">
          </div>
          <div style="margin-bottom:10px">
              <input id="Param7" class="easyui-numberbox" required="true" label="指标7:" style="width:100%">
          </div>
          <div style="margin-bottom:10px">
              <input id="Param8" class="easyui-numberbox" required="true" label="指标8:" style="width:100%">
          </div>
      </form>
 </div>
    
  <div id="dlg-buttons" style="text-align:center;">
      <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" id="btnOK" style="width:90px">保存</a>
      <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="BtnCancle()" style="width:90px">取消</a>
  </div> -->


	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'center'" style="padding: 10px;">
			<div id="dlg" style="width: 100%">
				<form id="fm" method="post" novalidate
					style="margin: 0; padding: 20px 50px">
					<div style="margin-bottom: 10px">
						<input id="SensorID" class="easyui-textbox" required="true"
							label="名称:" style="width: 100%">
					</div>
					<div style="margin-bottom: 10px">
						<select id="SensorMeasureType" class="easyui-combobox"
							required="true" label="测量类型:" style="width: 100%">
							<option value="应变">应变</option>
							<option value="位移">位移</option>
							<option value="温度">温度</option>
							<option value="倾角">倾角</option>
							<option value="电流">电流</option>
							<option value="索力">索力</option>
							<option value="振弦">振弦</option>
							<option value="风速">风速</option>
							<option value="挠度">挠度</option>
							<option value="裂缝">裂缝</option>
							<option value="测斜仪">测斜仪</option>
							<option value="沉降仪">沉降仪</option>
							<option value="桥式传感器">桥式传感器</option>
							<option value="静力水准仪">静力水准仪</option>
						</select>
					</div>
					<div style="margin-bottom: 10px">
						<input id="SensorSpec" class="easyui-textbox" required="true"
							label="规格:" style="width: 100%">
					</div>
					<div style="margin-bottom: 10px">
						<input id="SensorFactory" class="easyui-textbox" required="true"
							label="生产厂家:" style="width: 100%">
					</div>
					<div style="margin-bottom: 10px">
						<input id="Param1" class="easyui-numberbox" required="true"
							label="指标1:" style="width: 100%">
					</div>
					<div style="margin-bottom: 10px">
						<input id="Param2" class="easyui-numberbox" required="true"
							label="指标2:" style="width: 100%">
					</div>
					<div style="margin-bottom: 10px">
						<input id="Param3" class="easyui-numberbox" required="true"
							label="指标3:" style="width: 100%">
					</div>
					<div style="margin-bottom: 10px">
						<input id="Param4" class="easyui-numberbox" required="true"
							label="指标4:" style="width: 100%">
					</div>
					<div style="margin-bottom: 10px">
						<input id="Param5" class="easyui-numberbox" required="true"
							label="指标5:" style="width: 100%">
					</div>
					<div style="margin-bottom: 10px">
						<input id="Param6" class="easyui-numberbox" required="true"
							label="指标6:" style="width: 100%">
					</div>
					<div style="margin-bottom: 10px">
						<input id="Param7" class="easyui-numberbox" required="true"
							label="指标7:" style="width: 100%">
					</div>
					<div style="margin-bottom: 10px">
						<input id="Param8" class="easyui-numberbox" required="true"
							label="指标8:" style="width: 100%">
					</div>
				</form>
			</div>
		</div>
		<div data-options="region:'south',border:false"
			style="text-align: right; padding: 5px; border-color: #dddddd #95B8E7 #95B8E7 #95B8E7; background: #F4F4F4;">
			<a href="javascript:void(0)" class="easyui-linkbutton c6"
				iconCls="icon-ok" id="btnOK" style="width: 90px">保存</a> <a
				href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-cancel" onclick="BtnCancle()" style="width: 90px">取消</a>

		</div>
	</div>

	<script>
		var nAutoID, SensorID, SensorMeasureType, SensorSpec, SensorFactory, Param1, Param2, Param3, Param4, Param5, Param6, Param7, Param8;

		$(function() {

			var action = getQueryString("action");

			if (action == "add") {

				$('#btnOK').click(
						function() {
							//新增采集仪

							GetHtmlInfo();

							var addRes = JSON.parse(AddSeneorFun());

							if (addRes && addRes.result == "true") {

								if (window.parent != null
										&& window.parent != undefined) {
									window.parent.CloseSeneorWin("add",
											addRes.errorMsg);
								}

							} else {
								$.messager.alert('提示', '新增失败');
							}

						});

			} else if (action == "update") {

				nAutoID = getQueryString("nAutoID");
				SensorID = getQueryString("SensorID");
				SensorMeasureType = getQueryString("SensorMeasureType");
				SensorSpec = getQueryString("SensorSpec");
				SensorFactory = getQueryString("SensorFactory");
				Param1 = getQueryString("Param1");
				Param2 = getQueryString("Param2");
				Param3 = getQueryString("Param3");
				Param4 = getQueryString("Param4");
				Param5 = getQueryString("Param5");
				Param6 = getQueryString("Param6");
				Param7 = getQueryString("Param7");
				Param8 = getQueryString("Param8");

				$('#SensorID').textbox('setValue', SensorID);
				$('#SensorMeasureType').combobox('setValue', SensorMeasureType);
				$('#SensorSpec').textbox('setValue', SensorSpec);
				$('#SensorFactory').textbox('setValue', SensorFactory);

				$('#Param1').textbox('setValue', Param1);
				$('#Param2').textbox('setValue', Param2);
				$('#Param3').textbox('setValue', Param3);
				$('#Param4').textbox('setValue', Param4);

				$('#Param5').textbox('setValue', Param5);
				$('#Param6').textbox('setValue', Param6);
				$('#Param7').textbox('setValue', Param7);
				$('#Param8').textbox('setValue', Param8);

				$('#btnOK').click(
						function() {
							//修改采集仪

							GetHtmlInfo();

							var updateRes = JSON.parse(UpdateSeneorFun());

							if (updateRes && updateRes.result == "true") {

								if (window.parent != null
										&& window.parent != undefined) {
									window.parent.CloseSeneorWin("update",
											updateRes.errorMsg);
								}

							} else {
								$.messager.alert('提示', '修改失败');
							}

						});
			}

		});

		//取消按钮
		function BtnCancle() {
			if (window.parent != null && window.parent != undefined) {
				window.parent.CloseSeneorWin(null, null);
			}
		}

		//获取页面的数据
		function GetHtmlInfo() {
			SensorID = $('#SensorID').textbox('getValue');
			SensorMeasureType = $('#SensorMeasureType').combobox('getValue');
			SensorSpec = $('#SensorFactory').textbox('getValue');
			SensorFactory = $('#SensorFactory').textbox('getValue');

			Param1 = $('#Param1').textbox('getValue');
			Param2 = $('#Param2').textbox('getValue');
			Param3 = $('#Param3').textbox('getValue');
			Param4 = $('#Param4').textbox('getValue');

			Param5 = $('#Param5').textbox('getValue');
			Param6 = $('#Param6').textbox('getValue');
			Param7 = $('#Param7').textbox('getValue');
			Param8 = $('#Param8').textbox('getValue');
		}

		//新增采集仪
		function AddSeneorFun() {

			var res = null;

			$.ajax({
				type : "GET",
				url : "../RelationInsRequest",
				/* data: "page=" + page + "&rows=" + rows + "&keyword=" + $('#hdKeyword').val(), */
				async : false,//设置同步
				data : {
					action : "CreateSeneor",
					SensorID : SensorID,
					SensorMeasureType : SensorMeasureType,
					SensorSpec : SensorSpec,
					SensorFactory : SensorFactory,
					Param1 : Param1,
					Param2 : Param2,
					Param3 : Param3,
					Param4 : Param4,
					Param5 : Param5,
					Param6 : Param6,
					Param7 : Param7,
					Param8 : Param8,
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					//alert(textStatus);
					//$.messager.progress('close');
					$.messager.alert('提示', 'error');
				},
				success : function(data) {
					res = data;
				}
			});

			return res;
		}

		//修改采集仪
		function UpdateSeneorFun() {
			var res = null;

			$.ajax({
				type : "GET",
				url : "../RelationInsRequest",
				/* data: "page=" + page + "&rows=" + rows + "&keyword=" + $('#hdKeyword').val(), */
				async : false,//设置同步
				data : {
					action : "UpdateSensor",
					nAutoID : nAutoID,
					SensorID : SensorID,
					SensorMeasureType : SensorMeasureType,
					SensorSpec : SensorSpec,
					SensorFactory : SensorFactory,
					Param1 : Param1,
					Param2 : Param2,
					Param3 : Param3,
					Param4 : Param4,
					Param5 : Param5,
					Param6 : Param6,
					Param7 : Param7,
					Param8 : Param8,
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					//alert(textStatus);
					//$.messager.progress('close');
					$.messager.alert('提示', 'error');
				},
				success : function(data) {
					res = data;
				}
			});

			return res;
		}
	</script>


</body>
</html>