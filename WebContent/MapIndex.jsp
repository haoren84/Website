<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>泰之特-在线监测管理系统</title>

		<script type="text/javascript" src="http://api.map.baidu.com/api?v=3.0&ak=Nii1WdRnGdGOWlkov0c36Gutpp0y8XsF"></script>

		<link rel="stylesheet" href="Resource/bootstrap-3.3.7-dist/css/bootstrap.min.css">

		<link rel="stylesheet" type="text/css" href="Resource/jquery-easyui-1.5.4.2/themes/metro/easyui.css">
		<link rel="stylesheet" type="text/css" href="Resource/jquery-easyui-1.5.4.2/themes/icon.css">

		<script src="Resource/jquery-easyui-1.5.4.2/jquery.min.js"></script>
		<script src="Resource/jquery-easyui-1.5.4.2/jquery.easyui.min.js"></script>

		<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
		<script src="Resource/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>

		<script type="text/javascript" src="Resource/jshelper/commonfun.js"></script>

		<style type="text/css">
			html {
				height: 100%
			}

			body {
				height: 100%;
				margin: 0px;
				padding: 0px
			}

			#container {
				height: 100%
			}

			.datagrid-header .datagrid-cell span {
				font-size: 12px;
				font-weight: 900;
			}
		</style>
		<link rel="Shortcut Icon" href="Resource/Images/favicon.ico" />

	</head>

	<body>

		<div class="easyui-layout" style="width: 101%; height: 100%;">

			<div data-options="region:'north',title:'',split:false,border:false," style="height: 70px; padding: 0px;width: 100%;">

				<!-- <nav class="navbar navbar-default navbar-static-top"
		style="margin-bottom:0px;"> -->
				<div class="container-fluid">
					<div class="navbar-header">
						<img class="navbar-brand" src="Resource/Images/tzt.jpg" style="height: auto; padding:5px;" />
						<a class="navbar-brand" data-url="#"></a>
					</div>

					<div style="z-Index:999;position:fixed;right:5px;">
						<ul class="nav navbar-nav navbar-right" style="margin: 15px;">
							<li style="margin-right: 5px;">
								<div class="btn-group">
									<button type="button" id="UserNameBtn" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true"
									    aria-expanded="false">
										<span id="UserNameSpan">admin</span>
										<span class="caret"></span>
									</button>
									<ul class="dropdown-menu">
										<li>
											<a href="#" onclick="UpdatePwd()">修改密码</a>
										</li>
										<li role="separator" class="divider"></li>
										<li>
											<a href="#" onclick="Exit()">安全退出</a>
										</li>
									</ul>
								</div>
							</li>
							<li>
								<button type="button" class="btn btn-info" onclick="Btn_Help_Click()">帮助</button>
							</li>
						</ul>
					</div>
				</div>
				<!-- </nav> -->

			</div>

			<!-- 工程表格 -->
			<div data-options="region:'center',title:'',split:false,collapsible:true," style="padding: 0px; margin: 0px; background: #eee;">

				<table id="tb_prjinfo" style="height: 100%;width:100%;"></table>

			</div>

			<!-- 地图区域  -->
			<div data-options="region:'south',title:'',split:true,collapsible:true,border:false," style=" padding: 0px; margin: 0px; background: #eee;height: 70%;">
				<!--content-->
				<div id="contentdiv" style="height: 100%;width:100%;"></div>

			</div>

		</div>

		<!-- 帮助按钮的弹框 内容-->
		<div id="dlg" class="easyui-dialog" title="云平台联系方式" data-options="iconCls:'icon-save',closed:true" style="width: 400px; height: 200px; padding: 10px">
			联系电话：xxxxxxxx</div>
		<!-- 帮助按钮的弹框 触发-->
		<script>
			//帮助按钮点击事件
			function Btn_Help_Click() {
				$('#dlg').dialog('open');
			}
		</script>

		<!-- 新建工程窗口 -->
		<div id="w" class="easyui-window" title="新建工程" data-options="modal:true,closed:true,iconCls:'icon-save',title:'新建工程',collapsible:false,minimizable:false,maximizable:false,resizable:false,"
		    style="width: 750px; height: 400px; ">
			<!-- <iframe scrolling="yes" frameborder="0"
			src="EditSlnInfo.jsp?action=add"
			style="width:100%;height:90%;"></iframe> -->

			<div class="easyui-layout" data-options="fit:true">

				<div data-options="region:'center'" style="padding: 10px;">

					<div id="dlgfm" style="width: 100%">

						<form id="fm" method="post" novalidate="false" style="margin: 0; padding: 20px 50px">

							<div style="margin-bottom: 10px">
								<input id="prjname" style="width: 100%" class="easyui-textbox easyui-validatebox" label="工程名称:" data-options="required:true,
              							  validType:'length[0,30]',
              							  missingMessage:'请输入工程名称，字数不超过30'">
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

								<input id="categorymain_gis_project" class="easyui-combobox" required="true" label="工程类型:" style="width: 100%">

							</div>

							<div style="margin-bottom: 10px">
								<input id="prjaddress" class="easyui-textbox easyui-validatebox" required="true" label="工程地址:" style="width: 100%" validType="length[0,50]"
								    missingMessage="请输入工程地址，字数不超过50">
							</div>

							<div style="margin-bottom: 10px">
								<input id="longitude" name="longitude" type="text" style="width: 180px;" class="easyui-textbox " readonly="readonly" value=""
								    label="经纬度:" required="true" />
								<input id="latitude" name="latitude" type="text" style="width: 90px;" class="easyui-textbox " readonly="readonly" value=""
								    required="true" />
								<a class="easyui-linkbutton l-btn" plain="true" iconCls="icon-search" id="getPositionmain_gis_project" href="javascript:void(0);">定位</a>
							</div>

							<div style="margin-bottom: 10px">
								<textarea class="easyui-textbox" name="comments" id="remark" style="width: 100%; height: 55px;" label="工程简介:" validType="length[0,200]"
								    missingMessage="请输入工程简介，字数不超过200"></textarea>
							</div>


						</form>

					</div>

				</div>
				<div data-options="region:'south',border:false" style="text-align: right; padding: 5px; border-color: #dddddd #95B8E7 #95B8E7 #95B8E7; background: #F4F4F4;">
					<a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" id="btnOK" style="width: 90px">保存</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="CloseEditPrjWin('cancle')"
					    style="width: 90px">取消</a>

				</div>
			</div>

		</div>

		<!-- 工程类型加载 -->
		<script type="text/javascript">
			//工程类型加载 
			$(function () {
				$('#categorymain_gis_project').combobox({
					url: "DictionaryComboRequest?action=GetPrjTypeCombo",
					valueField: 'id',
					textField: 'dicvalue',
				});

			});
		</script>


		<!-- 新建工程触发 -->
		<script>
			$('#getPositionmain_gis_project').click(function () {

				var top = 0;
				var left = 0;

				$('#mapw').window({
					width: 700,
					height: 400,
					modal: true,
					closed: true,
					inline: false,
					collapsible: false,
					minimizable: false,
					maximizable: false,
					closable: false,
					resizable: false,
					content: "<iframe scrolling=\"yes\" frameborder=\"0\" src=\"MapSelect.jsp" +
						"\" style=\"width: 100%; height:100%;\"></iframe>",
					title: '选择经纬度',
				});

				$('#mapw').window('open');

			});
		    //得到表单窗口中的form对象
		    var fm = $('#fm');
		    //初始化表单中的验证器
		    $('input[type!="hidden"],select,textarea',fm ).each(function(){
		    	$(this).validatebox();
		    });
			// 验证框提示
			// 重写$.fn.validatebox.defaults.rules定义的验证器和错误信息
			$.extend($.fn.validatebox.defaults.rules, {
				// 在这里写重写的验证其及方法和错误信息
				length: {
					validator: function (value, param) {
						return value.length >= param[0] && value.length <= param[1];
					},
					message: '长度应介于{0}-{1}之间'
				}
			});
			//触发保存事件
			$('#btnOK').click(function () {

				var strPrjName = $("#prjname").textbox('getValue');
				var nPrjType = $("#categorymain_gis_project").combobox("getValue");
				var strPrjAddress = $("#prjaddress").textbox('getValue');
				var fprjLng = $('#longitude').textbox("getValue");
				var fPrjLat = $('#latitude').textbox("getValue");
				var strRemark = $('#remark').textbox("getValue");
				if (ComputeStrLength(strPrjName) > 30) {

					$.messager.alert('提示', '工程名称长度超出!');

					return;
				}

				if (ComputeStrLength(strPrjAddress) > 50) {

					$.messager.alert('提示', '工程地址长度超出!');

					return;
				}

				if (ComputeStrLength(strRemark) > 150) {

					$.messager.alert('提示', '备注长度超出!');

					return;
				}

				$.ajax({
					type: "GET",
					url: "TstWebRequest/TstResquest",
					/* data: "page=" + page + "&rows=" + rows + "&keyword=" + $('#hdKeyword').val(), */
					async: false, //设置同步
					data: {
						action: "AddPrjInfo",
						prjName: strPrjName,
						prjAddress: strPrjAddress,
						PrjManager: "1",
						PrjRemark: strRemark,
						prjType: nPrjType,
						prjLng: fprjLng,
						PrjLat: fPrjLat,
					},
					error: function (XMLHttpRequest, textStatus, errorThrown) {

						console.log("add prj error");
					},
					success: function (data) {

						var res = JSON.parse(data);

						if (!res || !res.result || res.result != 'true') {
							$.messager.alert('提示', '新增失败!');
							return;
						}

						CloseEditPrjWin("add");

					}
				});

			});




			function CloseEditPrjWin(action) {

				$('#w').window('close');

				if (action == "add") {
					//ReflashHtmlData();
					location.reload();
				}
			}
		</script>

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



		<!--地图相关-->
		<script>
			var map = new BMap.Map("contentdiv"); //地图以及初始定位
			var point = new BMap.Point(99.894149, 35.460649);
			map.centerAndZoom(point, 6);
			map.enableScrollWheelZoom(true); //开启鼠标滚轮缩放
			map.addControl(new BMap.NavigationControl()); //缩放
			map.addControl(new BMap.ScaleControl()); //比例尺

			//常见自定义的地图控件

			// 定义一个控件类,即function
			function AddSlnControl() {
				// 默认停靠位置和偏移量
				this.defaultAnchor = BMAP_ANCHOR_TOP_RIGHT;
				this.defaultOffset = new BMap.Size(10, 10);
			}

			// 通过JavaScript的prototype属性继承于BMap.Control
			AddSlnControl.prototype = new BMap.Control();

			// 自定义控件必须实现自己的initialize方法,并且将控件的DOM元素返回
			// 在本方法中创建个div元素作为控件的容器,并将其添加到地图容器中
			AddSlnControl.prototype.initialize = function (map) {
				// 创建一个DOM元素
				var div = document.createElement("div");
				// 添加文字说明
				div.appendChild(document.createTextNode("添加工程"));
				// 设置样式
				/* div.style.cursor = "pointer";
				div.style.border = "1px solid gray";
				div.style.backgroundColor = "white"; */

				div.className = "btn btn-default";

				// 绑定事件,点击一次放大两级
				div.onclick = function (e) {
					//alert("新建工程信息！！！！！");

					//新建工程信息弹框
					$('#w').window('open');

				};
				// 添加DOM元素到地图中
				map.getContainer().appendChild(div);
				// 将DOM元素返回
				return div;
			}
			// 创建控件
			var myAddSlnCtrl = new AddSlnControl();
			// 添加到地图当中
			map.addControl(myAddSlnCtrl);
		</script>

		<!-- 页面数据加载 -->
		<script>
			//获取关联数据
			function GetMapSlnInfo() {
				var result;

				$.ajax({
					url: "TstWebRequest/TstResquest",
					async: false, //设置同步
					data: {
						action: "GetLeftInfoInMap",
					},
					type: "get",
					datatype: "json",
					success: function (data) {
						var obj = eval("(" + data + ")");

						result = obj;

					},
					error: function () {
						console.log('LeftInfo error!');
					},
				});

				return result;

			}

			var optsInfo = {
				width: 250, // 信息窗口宽度
				height: 80, // 信息窗口高度
				title: "信息窗口", // 信息窗口标题
				enableMessage: true
				//设置允许信息窗发送短息
			};

			function addClickHandler(content, marker) {
				marker.addEventListener("click", function (e) {
					openInfo(content, e)
				});
			}

			function openInfo(content, e) {
				//var p = e.target;
				//var point = new BMap.Point(p.getPosition().lng, p.getPosition().lat);
				var point = new BMap.Point(content.x, content.y);

				optsInfo.title = content.name;

				var infoUrl = "<a href='PrjIndex.jsp?projectId=" +
					content.id + "&projectName=" + content.name + "'><br>&nbsp;&nbsp;查看详情&nbsp;&nbsp;</a>";

				var infoWindow = new BMap.InfoWindow(infoUrl, optsInfo); // 创建信息窗口对象 
				map.openInfoWindow(infoWindow, point); //开启信息窗口
			}

			//地图完善
			function CompleteMap(slnObj) {

				//遍历数据添加map信息，包括文字标注，图片标注

				var nObjLength = slnObj.length;

				for (var i = nObjLength - 1; i >= 0; i--) {
					if (slnObj[i].x && slnObj[i].y) {
						var objpoint = new BMap.Point(slnObj[i].x, slnObj[i].y);

						//文字标注的设置
						var optsTxt = {
							position: objpoint, // 指定文本标注所在的地理位置
							offset: new BMap.Size(-20, -20)
							//设置文本偏移量
						};

						//文字标识
						var label = new BMap.Label(slnObj[i].name, optsTxt); // 创建文本标注对象
						label.setStyle({
							backgroundColor: "white",
							border: "none",
							color: "black",
							fontSize: "14px",
							height: "20px",
							lineHeight: "20px",
							fontFamily: "微软雅黑"
						});
						map.addOverlay(label);

						//图片标识

						var myIcon = new BMap.Icon(
							"http://api.map.baidu.com/img/markers.png",
							new BMap.Size(23, 25), {
								offset: new BMap.Size(10, 25),
								imageOffset: new BMap.Size(0, -25 *
									(nObjLength - 1 - i))
							});

						var marker = new BMap.Marker(objpoint, {
							icon: myIcon
						}); // 创建标注    

						marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画

						map.addOverlay(marker); // 将标注添加到地图中

						addClickHandler(slnObj[i], marker);
					}

				}

			}

			//工程信息表格
			function LoadPrjTable() {

				$('#tb_prjinfo').datagrid({
					url: 'TstWebRequest/TstResquest?action=GetPrjInfoTableData',
					columns: [
						[{
							field: 'name',
							title: '工程名称',
							width: '10%'
						}, {
							field: 'address',
							title: '工程地址',
							width: '45%'
						}, {
							field: 'remark',
							title: '备注',
							width: '45%'
						}]
					],
					rownumbers: true,
					method: 'get',
					singleSelect: true,
					onDblClickRow: function (index, row) {
						//console.log(row);

						window.location = "PrjIndex.jsp?projectId=" +
							row.id + "&projectName=" + row.name;
					},

				});



			}



			//刷新页面数据
			function ReflashHtmlData() {

				var slnObj = GetMapSlnInfo();

				if (slnObj == null || slnObj == undefined ||
					slnObj.length == undefined || slnObj.length <= 0)
					return;

				//地图的加载
				CompleteMap(slnObj);

				//表格加载
				LoadPrjTable();
			}

			$(document).ready(function () {

				//加载问题，设置了200的延迟
				setTimeout(function () {
					ReflashHtmlData();
				}, 200);

			});
		</script>

		<!-- 用户信息窗口 -->
		<div id="userwin"></div>
		<!-- 用户信息 -->
		<script>
			var UserName = decodeURI(cookie.get('UserName'));

			if (!UserName || UserName == 'undefined') {
				window.location.href = "login.jsp";
			} else {

				$('#UserNameSpan').text(UserName);

			}

			//修改密码
			function UpdatePwd() {

				$('#userwin')
					.window({
						width: 500,
						height: 300,
						modal: true,
						content: "<iframe scrolling=\"yes\" frameborder=\"0\" src=\"UserManager/EditUser.jsp?action=update" +
							"&UserName=" +
							UserName +
							"\" style=\"width: 100%; height:100%;\"></iframe>",
						title: '修改密码',
						collapsible: false,
						minimizable: false,
						maximizable: false,
					});

				$('#userwin').window('open');

			}

			//退出到首页
			function Exit() {

				window.location.href = "login.jsp";
			}

			//关闭用户的编辑窗口
			function CloseUserWin(action, msg) {

				if (msg) {
					$.messager.alert('提示', msg);
				}

				$('#userwin').window('close');
			}
		</script>


	</body>

	</html>