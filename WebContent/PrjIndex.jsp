<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>项目</title>

<link rel="stylesheet"
	href="Resource/bootstrap-3.3.7-dist/css/bootstrap.min.css">


<link rel="stylesheet" type="text/css"
	href="Resource/jquery-easyui-1.5.4.2/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="Resource/jquery-easyui-1.5.4.2/themes/icon.css">



<script src="Resource/jquery-easyui-1.5.4.2/jquery.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="Resource/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
<script src="Resource/jquery-easyui-1.5.4.2/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="Resource/jquery-easyui-1.5.4.2/locale/easyui-lang-zh_CN.js"></script>

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
</style>

<style type="text/css">
#div_left_side {
	width: 200px;
	height: 100%;
	float: left;
	background-color: #f8f8f8;
	border: 1px solid #ddd;
}

#div_left_mini_side {
	float: left;
	background-color: #f8f8f8;
	border: 1px solid #ddd;
	position: relative;
	/* display: block; */
	padding: 10px 15px;
	margin-bottom: -1px;
}

.list-group-item {
	border: none;
}

.second-item {
	cursor: pointer;
}

.second-item:hover, .second-item:focus {
	background: #F0FFF0;
}

.thread-item {
	cursor: pointer;
}

.thread-item:hover, .thread-item:focus {
	background: #F0FFF0;
}

.hideitem {
	display: none;
}

#list-menu-id {
	
}

.active2, .active2:hover, .active2:focus {
	z-index: 2;
	color: #fff;
	background-color: #63B8FF;
	border-color: #63B8FF;
}
</style>

<link rel="Shortcut Icon" href="Resource/Images/favicon.ico" />
</head>
<body>

	<div class="easyui-layout" style="width: 100%; height: 100%;">
		<div data-options="region:'north',title:'',split:false,border:false,"
			style="height: 70px; padding: 0px;">

			<!-- <nav class="navbar navbar-default navbar-static-top"
		style="margin-bottom:0px;"> -->
			<div class="container-fluid">
				<div class="navbar-header">
					<img class="navbar-brand" src="Resource/Images/tzt.jpg"
						style="height: auto; padding: 5px;" /> <span id="prjinfo_a"
						style="font-size: 40px; line-height: 0; padding: 33px 205px"
						class="navbar-brand" data-url="#"></span>
				</div>

				<div style="z-Index: 999; position: fixed; right: 5px;">
					<ul class="nav navbar-nav navbar-right" style="margin: 15px;">
						<li style="margin-right: 5px;">
							<div class="btn-group">
								<button type="button" id="UserNameBtn"
									class="btn btn-default dropdown-toggle" data-toggle="dropdown"
									aria-haspopup="true" aria-expanded="false">
									<span id="UserNameSpan">admin</span> <span class="caret"></span>
								</button>
								<ul class="dropdown-menu">
									<li><a href="#" onclick="UpdatePwd()">修改密码</a></li>
									<li role="separator" class="divider"></li>
									<li><a href="#" onclick="Exit()">安全退出</a></li>
									<li role="separator" class="divider"></li>
									<li><a href="#" onclick="BackToMapIndex()">工程分布页面</a></li>
								</ul>
							</div>
						</li>
						<li>
							<button type="button" class="btn btn-info"
								onclick="Btn_Help_Click()">帮助</button>
						</li>
					</ul>
				</div>
			</div>
			<!-- </nav> -->

		</div>

		<div data-options="region:'south',title:'',split:false,border:false,"
			style="height: 20px; padding: 0px; text-align: center;">
			江苏泰之特物联科技股份有限公司 2018 All rights reserved. 苏ICP备18010044号</div>
		<!-- <div data-options="region:'east',title:'East',split:true"
			style="width: 100px;"></div> -->


		<div
			data-options="region:'west',title:'',split:true,collapsible:true,"
			style="width: 200px; padding: 0px; margin: 0px;">
			<div id="div_left_side" style="" class="">
				<ul class="list-group" id="list-menu-id">
					<li class="list-group-item">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;功能<span
						class="glyphicon glyphicon-menu-hamburger pull-right"></span></li>

					<li class="list-group-item glyphicon glyphicon-cog second-item">&nbsp;工程管理
						<span class="glyphicon glyphicon-plus pull-right"></span>
					</li>
					<li class="list-group-item thread-item hideitem"
						data-url="PrjManage/PrjDetailInfo.jsp">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;工程信息<span
						class="glyphicon glyphicon-menu-right pull-right"></span></li>
					<li class="list-group-item thread-item hideitem"
						data-url="PrjImageManage/PrjImageDisplay.jsp">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;工程指示图<span
						class="glyphicon glyphicon-menu-right pull-right"></span></li>
					<!-- <li class="list-group-item thread-item hideitem"
						data-url="PrjManage/PrjStruct.jsp">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;工程结构<span
						class="glyphicon glyphicon-menu-right pull-right"></span></li>
					<li class="list-group-item thread-item hideitem"
						data-url="PrjManage/PrjStruct.jsp">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;工程布点图<span
						class="glyphicon glyphicon-menu-right pull-right"></span></li>
					<li class="list-group-item thread-item hideitem"
						data-url="PrjManage/PrjGuide.jsp">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;工程向导<span
						class="glyphicon glyphicon-menu-right pull-right"></span></li> -->


					<!-- <li
						class="list-group-item glyphicon glyphicon-list-alt second-item">&nbsp;工况
						<span class="glyphicon glyphicon-plus pull-right"></span>
					</li>
					<li class="list-group-item thread-item hideitem">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;终端电量<span
						class="glyphicon glyphicon-menu-right pull-right"></span></li>
					<li class="list-group-item thread-item hideitem">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;采集仪在线情况<span
						class="glyphicon glyphicon-menu-right pull-right"></span></li>
					<li class="list-group-item thread-item hideitem">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;采集仪供电情况<span
						class="glyphicon glyphicon-menu-right pull-right"></span></li> -->

					<!------------------------------------ 设备管理 --------------------------------------->
					<!-- <li class="list-group-item glyphicon glyphicon-list-alt second-item">&nbsp;设备管理
				<span class="glyphicon glyphicon-plus pull-right"></span>
			</li>
			<li class="list-group-item thread-item hideitem"
				data-url="InsManage/InstrumentAcq.jsp">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;采集仪<span
				class="glyphicon glyphicon-menu-right pull-right"></span></li>
			<li class="list-group-item thread-item hideitem"
			    data-url="InsManage/InstrumentTerminal.jsp">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;终端<span
				class="glyphicon glyphicon-menu-right pull-right"></span></li>
			<li class="list-group-item thread-item hideitem"
			    data-url="InsManage/InstrumentSensor.jsp">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;传感器<span
				class="glyphicon glyphicon-menu-right pull-right"></span></li>
			<li class="list-group-item thread-item hideitem">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;设备调试<span
				class="glyphicon glyphicon-menu-right pull-right"></span></li> -->

					<!------------------------------------ 终端管理 --------------------------------------->
					<li
						class="list-group-item glyphicon glyphicon-list-alt second-item">&nbsp;终端管理
						<span class="glyphicon glyphicon-plus pull-right"></span>
					</li>
					<li class="list-group-item thread-item hideitem"
						data-url="TerminalManage/TerminalDetail.jsp">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;终端信息<span
						class="glyphicon glyphicon-menu-right pull-right"></span></li>
						
					<!------------------------------------ 传感器管理 --------------------------------------->
					<li
						class="list-group-item glyphicon glyphicon-list-alt second-item">&nbsp;传感器管理
						<span class="glyphicon glyphicon-plus pull-right"></span>
					</li>
					<li class="list-group-item thread-item hideitem"
						data-url="SensorManage/SensorDetail.jsp">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;传感器信息<span
						class="glyphicon glyphicon-menu-right pull-right"></span></li>
					<!---------------------------------- 测点管理 --------------------------------------->
					<li
						class="list-group-item glyphicon glyphicon-list-alt second-item">&nbsp;测点管理
						<span class="glyphicon glyphicon-plus pull-right"></span>
					</li>

					<li class="list-group-item thread-item hideitem"
						data-url="MeasurePointManager/RelationIns.jsp">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;关联设备<span
						class="glyphicon glyphicon-menu-right pull-right"></span></li>
					<li class="list-group-item thread-item hideitem"
						data-url="MPointManager/MPointManager.jsp">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;监测测点管理<span
						class="glyphicon glyphicon-menu-right pull-right"></span></li>
					
					
					<!-- <li class="list-group-item thread-item hideitem"
                data-url="InsManage/InstrumentTerminal.jsp">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;采集任务<span
                class="glyphicon glyphicon-menu-right pull-right"></span></li>
            <li class="list-group-item thread-item hideitem"
                data-url="InsManage/InstrumentSensor.jsp">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;数据调试<span
                class="glyphicon glyphicon-menu-right pull-right"></span></li> -->

					<!---------------------------------- 基础数据 --------------------------------------->
					<li class="list-group-item glyphicon glyphicon-cog second-item">&nbsp;基础数据
						<span class="glyphicon glyphicon-plus pull-right"></span>
					</li>
					<!-- <li class="list-group-item thread-item hideitem"
						data-url="DataManage/BasicData.jsp">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;数据信息<span
						class="glyphicon glyphicon-menu-right pull-right"></span></li> -->
					<li class="list-group-item thread-item hideitem"
						data-url="DataManage/BasicData2.jsp?action=history">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;历史数据<span
						class="glyphicon glyphicon-menu-right pull-right"></span></li>
					<li class="list-group-item thread-item hideitem"
						data-url="DataManage/BasicData2.jsp?action=realtime">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;实时数据<span
						class="glyphicon glyphicon-menu-right pull-right"></span></li>
					<li class="list-group-item thread-item hideitem"
						data-url="DataManage/BasicData2.jsp?action=dynamicrealtime">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;动态实时数据<span
						class="glyphicon glyphicon-menu-right pull-right"></span></li>
					<!---------------------------------- 系统管理 --------------------------------------->
					<li
						class="list-group-item glyphicon glyphicon-list-alt second-item">&nbsp;系统管理
						<span class="glyphicon glyphicon-plus pull-right"></span>
					</li>
					<!-- <li class="list-group-item thread-item hideitem">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;公司信息<span
				class="glyphicon glyphicon-menu-right pull-right"></span></li>
			<li class="list-group-item thread-item hideitem">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;部门管理<span
				class="glyphicon glyphicon-menu-right pull-right"></span></li>
			<li class="list-group-item thread-item hideitem">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;职务管理<span
				class="glyphicon glyphicon-menu-right pull-right"></span></li> -->
					<li class="list-group-item thread-item hideitem"
						data-url="UserManager/UserList.jsp">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;人员管理<span
						class="glyphicon glyphicon-menu-right pull-right"></span></li>
					<!-- <li class="list-group-item thread-item hideitem"
				data-url="SystemManage/DictionaryInfo.jsp">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;字典管理<span
				class="glyphicon glyphicon-menu-right pull-right"></span></li> -->
					<li class="list-group-item thread-item hideitem"
						data-url="DictionaryManage/Dictionary.jsp">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;字典管理<span
						class="glyphicon glyphicon-menu-right pull-right"></span></li>
					<!-- <li class="list-group-item thread-item hideitem">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;监测类型<span
				class="glyphicon glyphicon-menu-right pull-right"></span></li> -->
			</div>

			<div id="div_left_mini_side"
				class="glyphicon glyphicon-menu-hamburger hideitem"></div>
		</div>



		<div
			data-options="region:'center',title:'',split:false,collapsible:true,"
			style="padding: 0px; margin: 0px; background: #eee;">

			<!--content-->
			<div id="contentdiv" style="height: 100%;"></div>

		</div>

	</div>

	<!-- 帮助按钮的弹框 内容-->
	<div id="dlg" class="easyui-dialog" title="云平台联系方式"
		data-options="iconCls:'icon-save',closed:true"
		style="width: 400px; height: 200px; padding: 10px">
		联系电话：xxxxxxxx</div>
	<!-- 帮助按钮的弹框 触发-->
	<script>
		//帮助按钮点击事件
		function Btn_Help_Click() {
			$('#dlg').dialog('open');
		}
	</script>






	<script>
		$(function() {

			//记录prjID 和 prjName 到cookie中去

			var prjID = getQueryString("projectId");
			var prjName = encodeURI(getQueryString("projectName"));

			document.title = '项目 - ' + getQueryString("projectName");

			$('#prjinfo_a').html(getQueryString("projectName"));

			cookie.set('prjID', prjID, 1);
			cookie.set('prjName', prjName, 1);
		});
	</script>

	<script>
		//创建用于记录菜单信息的对象
		function objMenuItem(strMenuInfo, nMenuIndex, nParentIndex) {
			this.strMenuInfo = strMenuInfo;
			this.nMenuIndex = nMenuIndex;
			this.nParentIndex = nParentIndex;
		}

		objMenuItem.property = {
			constructor : objMenuItem,
		}
	</script>

	<script>
		var arrayMenu = new Array();

		var nMenuLength = $('#list-menu-id').children().length;

		var nCurrSecondIndex = 0;

		for (var i = 0; i < nMenuLength; i++) {
			var domCurr = $('#list-menu-id').children()[i];

			if (domCurr.className.indexOf('second-item') >= 0) {
				//二级菜单
				nCurrSecondIndex = i;

				var strMenuInfo = domCurr.innerText;

				var menuItem = new objMenuItem(strMenuInfo, i, 0);

				arrayMenu.push(menuItem);

			} else if (domCurr.className.indexOf('thread-item') >= 0) {

				var strMenuInfo = domCurr.innerText;

				var menuItem = new objMenuItem(strMenuInfo, i, nCurrSecondIndex);

				arrayMenu.push(menuItem);
			}

		}

		//找到匹配的菜单信息
		function FindMapMenu(strMenuInfo) {

			var nMenuLength = arrayMenu.length;

			for (var i = 0; i < nMenuLength; i++) {

				var objItem = arrayMenu[i];

				if (objItem.strMenuInfo == strMenuInfo) {
					return objItem;
				}
			}

		}

		//根据父节点index找到子项
		function FindChildMenu(nParentIndex) {

			var arrayChild = new Array();

			var nMenuLength = arrayMenu.length;

			for (var i = 0; i < nMenuLength; i++) {

				var objItem = arrayMenu[i];

				if (objItem.nParentIndex == nParentIndex) {
					arrayChild.push(objItem);
				}
			}

			return arrayChild;
		}

		//根据父节点index找到父项
		function FindParentMenu(nParentIndex) {
			var nMenuLength = arrayMenu.length;

			for (var i = 0; i < nMenuLength; i++) {

				var objItem = arrayMenu[i];

				if (objItem.nMenuIndex == nParentIndex) {
					return objItem;
				}
			}
		}
	</script>


	<script>
		$(function() {

			$('.second-item')
					.click(
							function() {
								console.log(this.innerText);

								var objMenuitem = FindMapMenu(this.innerText);

								if (objMenuitem == null
										|| objMenuitem == undefined) {
									return;
								}

								var arrayChild = FindChildMenu(objMenuitem.nMenuIndex);

								if (this.className.indexOf('active') >= 0) {
									//当前是选中状态

									//取消选中，设为加号，清除下级显示
									this.classList.remove("active");

									if ($('#list-menu-id span')[objMenuitem.nMenuIndex].className
											.indexOf("glyphicon-minus") >= 0) {

										$('#list-menu-id span')[objMenuitem.nMenuIndex].classList
												.remove("glyphicon-minus");
										$('#list-menu-id span')[objMenuitem.nMenuIndex].classList
												.add("glyphicon-plus");
									}

									if (arrayChild != null
											&& arrayChild != undefined
											&& arrayChild.length != undefined
											&& arrayChild.length > 0) {

										var nChildLength = arrayChild.length;

										for (var i = 0; i < nChildLength; i++) {

											var currChild = $('#list-menu-id')
													.children()[arrayChild[i].nMenuIndex];

											if (currChild.className
													.indexOf("hideitem") < 0) {
												currChild.classList
														.add("hideitem");
											}

										}

									}

								} else if (this.className.indexOf('active') < 0) {
									//当前是非选中状态

									//添加选中，设为减号，下级显示

									this.classList.add("active");

									if ($('#list-menu-id span')[objMenuitem.nMenuIndex].className
											.indexOf("glyphicon-plus") >= 0) {

										$('#list-menu-id span')[objMenuitem.nMenuIndex].classList
												.remove("glyphicon-plus");
										$('#list-menu-id span')[objMenuitem.nMenuIndex].classList
												.add("glyphicon-minus");
									}

									if (arrayChild != null
											&& arrayChild != undefined
											&& arrayChild.length != undefined
											&& arrayChild.length > 0) {

										var nChildLength = arrayChild.length;

										for (var i = 0; i < nChildLength; i++) {

											var currChild = $('#list-menu-id')
													.children()[arrayChild[i].nMenuIndex];

											if (currChild.className
													.indexOf("hideitem") >= 0) {
												currChild.classList
														.remove("hideitem");
											}

										}

									}
								}
							});

			$(".second-item")[0].click();

		});
	</script>



	<script type="text/javascript">
		//刷新右侧页面
		function ReflashContent(elem) {

			var prjID = getQueryString("projectId");
			var prjName = getQueryString("projectName");
			//解析出对应的data-url
			var dataurl = elem.getAttribute('data-url')

			if (dataurl == null || dataurl == undefined) {
				return;
			} else {
				var iframe = '<iframe';
				iframe += ' src="' + dataurl + '?projectId=' + prjID
						+ '&prjName=' + prjName + '"';
				iframe += ' style="width: 100%; height: 100%; border: 0px;"';
				iframe += '></iframe>';

				document.getElementById("contentdiv").innerHTML = iframe;
			}
		}
	</script>

	<script>
		$(function() {

			$('.thread-item')
					.click(
							function() {

								var objMenuitem = FindMapMenu(this.innerText);

								if (objMenuitem == null
										|| objMenuitem == undefined) {
									return;
								}

								var objParentitem = FindParentMenu(objMenuitem.nParentIndex);//对应的父节点

								if (this.className.indexOf("active2") <= 0) {
									//当前没选中

									//设置选中，清除其它的三级选中项，清除其它的二级选中

									this.classList.add("active2");

									var nMenuLength = arrayMenu.length;

									for (var i = 0; i < nMenuLength; i++) {

										var objItem = arrayMenu[i];

										if (objItem.nMenuIndex == objMenuitem.nParentIndex
												|| objItem.nMenuIndex == objMenuitem.nMenuIndex) {
											continue;
										}

										var curmenu = $('#list-menu-id')
												.children()[objItem.nMenuIndex];

										if (objItem.nParentIndex == 0) {
											//二级

											if (curmenu.className
													.indexOf('active') >= 0) {
												curmenu.classList
														.remove("active");
											}

											if ($('#list-menu-id span')[objItem.nMenuIndex].className
													.indexOf("glyphicon-minus") >= 0) {

												$('#list-menu-id span')[objItem.nMenuIndex].classList
														.remove("glyphicon-minus");
												$('#list-menu-id span')[objItem.nMenuIndex].classList
														.add("glyphicon-plus");
											}

										} else if (objItem.nParentIndex > 0) {

											//三级

											if (curmenu.className
													.indexOf('active2') >= 0) {
												curmenu.classList
														.remove("active2");
											}

											if (objItem.nParentIndex != objMenuitem.nParentIndex) {
												if (curmenu.className
														.indexOf('hideitem') < 0) {
													curmenu.classList
															.add("hideitem");
												}
											}

										}
									}

									ReflashContent(this);

								}

							})

			$(".thread-item")[0].click();
		});
	</script>

	<!-- 用户信息窗口 -->
	<div id="userwin"></div>
	<!-- 用户信息 -->
	<script>
		var UserName = decodeURI(cookie.get('UserName'));

		if (!UserName || UserName == undefined) {
			window.location.href = "login.jsp";
		} else {

			$('#UserNameSpan').text(UserName);

		}

		//修改密码
		function UpdatePwd() {

			$('#userwin')
					.window(
							{
								width : 500,
								height : 300,
								modal : true,
								content : "<iframe scrolling=\"yes\" frameborder=\"0\" src=\"UserManager/EditUser.jsp?action=update"
										+ "&UserName="
										+ UserName
										+ "\" style=\"width: 100%; height:100%;\"></iframe>",
								title : '修改密码',
								collapsible : false,
								minimizable : false,
								maximizable : false,
							});

			$('#userwin').window('open');

		}

		//退出到首页
		function Exit() {

			window.location.href = "login.jsp";
		}

		//返回地图页
		function BackToMapIndex() {
			window.location.href = "MapIndex.jsp";
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