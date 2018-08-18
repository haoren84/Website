<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>工程信息</title>

<link rel="stylesheet" type="text/css"
	href="Resource/jquery-easyui-1.5.4.2/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="Resource/jquery-easyui-1.5.4.2/themes/icon.css">

<script src="Resource/jquery-easyui-1.5.4.2/jquery.min.js"></script>
<script src="Resource/jquery-easyui-1.5.4.2/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="Resource/jquery-easyui-1.5.4.2/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="Resource/jshelper/commonfun.js"></script>
</head>
<body>
	<table>
		<tr>
			<th style="color: red;">工程名称:</th>
			<td><input name="name" type="text" id="prjname"
				class="easyui-textbox easyui-validatebox" required="required" /></td>
			<th style="color: red;">工程类型：</th>
			<td><select name="category" id="categorymain_gis_project"
				class="easyui-combobox" data-options="editable:false">
					<option value="1">隧道</option>
					<option value="2">房屋</option>
					<option value="3">地铁</option>
					<option value="4">基坑</option>
			</select></td>
		</tr>

		<tr>
			<th style="color: red;">工程区域:</th>
			<td><input name="zone" type="text" id="prjzone"
				class="easyui-textbox easyui-validatebox" required="required" /></td>
			<th style="color: red;">工程地址:</th>
			<td><input name="address" id="prjaddress" type="text"
				class="easyui-textbox easyui-validatebox" required="required" /></td>
		</tr>
		<tr style="display:none;">
			<th>监督编号:</th>
			<td><input name="supervisionNo" type="text"
				class="easyui-textbox easyui-validatebox" /></td>
			<th>监测负责人:</th>
			<td><input name="chargePerson.id"
				class="easyui-textbox easyui-validatebox"
				id="chargePersonmain_gis_project" value="" /></td>
		</tr>
		<tr id="dateTrmain_gis_project" style="display:none;">
			<th>计划开挖日期:</th>
			<td><input name="startDate" type="text"
				class="easyui-datebox easyui-validatebox" style="width: 124px;" /></td>
			<th>计划开挖完成日期:</th>
			<td><input name="endDate" type="text"
				class="easyui-datebox easyui-validatebox" style="width: 124px;" /></td>
		</tr>
		<tr id="jkTrmain_gis_project" style="display:none;">
			<th>设计深度(m):</th>
			<td><input name="designDepth" type="text"
				class="easyui-textbox easyui-validatebox" /></td>
			<th>周长(m):</th>
			<td><input name="perimeter" type="text"
				class="easyui-textbox easyui-validatebox" /></td>
		</tr>
		<tr >
			<th style="color: red;">监测等级：</th>
			<td><select name="grade" id="grademain_gis_project"
				class="easyui-combobox" required="required" style="width: 124px;"
				data-options="editable:false">
					<option value="1">一级</option>
					<option value="2">二级</option>
					<option value="3">三级</option>
			</select></td>
			<th>经纬度:</th>
			<td><input id="longitude" name="longitude" type="text"
				style="width: 90px;" class="easyui-textbox " readonly="readonly"
				value="" /> <input id="latitude" name="latitude" type="text"
				style="width: 90px;" class="easyui-textbox " readonly="readonly"
				value="" /> <a class="easyui-linkbutton l-btn" plain="true"
				iconCls="icon-search" id="getPositionmain_gis_project"
				href="javascript:void(0);">定位</a></td>
		</tr>

		<tr style="display:none;">
			<th>监测人员:</th>
			<td colspan=3><input id="monitorPersonsmain_gis_project"
				class="easyui-combobox" style="width: 420px;"
				data-options="editable:false" /></td>
		</tr>
		<tr>
			<th>工程简介:</th>
			<td colspan="3"><textarea class="easyui-textbox" name="comments"
					id="remark" style="width: 535px; height: 40px;"></textarea></td>
		</tr>

		<tr>
			<td colspan="4" align="center">
				<div>
					<a class="easyui-linkbutton" data-options="iconCls:'icon-save'"
						style="width: 60px;" href="#" id="btnok">保存</a> <a
						class="easyui-linkbutton" data-options="iconCls:'icon-cancel'"
						style="width: 60px;" href="#" id="btncancle">取消</a>
				</div>
			</td>
		</tr>

	</table>

	<!-- combox分页数据  -->
	<script>
		$(function() {
			$('#chargePersonmain_gis_project').combogrid({
				panelWidth : 400,
				idField : 'id', //ID字段  
				textField : 'name', //显示的字段  
				url : "TstWebRequest/TstResquest?action=GetChargePersonInfo",
				fitColumns : true,
				striped : true,
				editable : true,
				pagination : true, //是否分页
				rownumbers : true, //序号
				collapsible : false, //是否可折叠的
				/* multiple:true, */
				/* fit: true, *///自动大小
				method : 'get',
				columns : [ [ {
					field : 'ck',
					checkbox : true
				}, {
					field : 'id',
					title : '用户编号',
					width : 80,
					hidden : true
				}, {
					field : 'name',
					title : '用户名称',
					width : 150
				}, {
					field : 'tel',
					title : '用户电话',
					width : 150
				}, {
					field : 'company.name',
					title : '用户单位',
					width : 150
				} ] ],
				onLoadSuccess:function(data){
					//console.log(data);
				},

			});

			//取得分页组件对象
			var pager = $('#chargePersonmain_gis_project').combogrid('grid')
					.datagrid('getPager');

			if (pager) {
				$(pager)
						.pagination(
								{
									pageSize : 10, //每页显示的记录条数，默认为10
									pageList : [ 10, 5, 3 ], //可以设置每页记录条数的列表
									beforePageText : '第', //页数文本框前显示的汉字
									afterPageText : '页    共 {pages} 页',
									displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录',
									//选择页的处理
									onSelectPage : function(pageNumber,
											pageSize) {
										//按分页的设置取数据
										getData(pageNumber, pageSize);
										//设置表格的pageSize属性，表格变化时按分页组件设置的pageSize显示数据
										$('#chargePersonmain_gis_project')
												.combogrid("grid").datagrid(
														'options').pageSize = pageSize;
										//将隐藏域中存放的查询条件显示在combogrid的文本框中
										/* $('#cg').combogrid("setValue", $('#hdKeyword').val());
										$('#txtGender').val(''); */
									},
									//改变页显示条数的处理
									//（处理后还是走onSelectPage事件，所以设置也写到onSelectPage事件中了）
									onChangePageSize : function() {
									},
									//点击刷新的处理
									onRefresh : function(pageNumber, pageSize) {
										//按分页的设置取数据
										getData(pageNumber, pageSize);
										//将隐藏域中存放的查询条件显示在combogrid的文本框中
										/* $('#cg').combogrid("setValue", $('#hdKeyword').val());
										$('#txtGender').val(''); */
									}
								});
			}

			var getData = function(page, rows) {
				$.ajax({
					type : "GET",
					url : "TstWebRequest/TstResquest",
					/* data: "page=" + page + "&rows=" + rows + "&keyword=" + $('#hdKeyword').val(), */
					async : false,//设置同步
					data : {
						action : "GetChargePersonInfo",
						page : page,
						rows : rows,
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						alert(textStatus);
						$.messager.progress('close');
					},
					success : function(data) {
						$('#chargePersonmain_gis_project').combogrid('grid')
								.datagrid('loadData', $.parseJSON(data));
					}
				});
			};
			
			
			
			
		});
	</script>


	<script>
	var strPrjName,nPrjType,strPrjZoom,strPrjAddress,strprjManager,fprjLng,fPrjLat,strRemark;
	
	//获取页面的信息
	function GetHtmlInfo(){
		strPrjName=$("#prjname").textbox('getValue');
		nPrjType=$("#categorymain_gis_project").combobox("getValue");
		strPrjZoom=$("#prjzone").textbox('getValue');
		strPrjAddress=$("#prjaddress").textbox('getValue');
		strprjManager=$("#chargePersonmain_gis_project").combobox("getValue");
		fprjLng=$('#longitude').textbox("getValue");
		fPrjLat=$('#latitude').textbox("getValue");
		strRemark=$('#remark').textbox("getValue");
	}
	
	//页面的信息赋值
	function SetHtmlInfo(){
		
		$("#prjname").textbox('setValue',strPrjName);
		$("#categorymain_gis_project").combobox("setValue",nPrjType);
		$("#prjzone").textbox('setValue',strPrjAddress);
		$("#prjaddress").textbox('setValue',strPrjAddress);
		$("#chargePersonmain_gis_project").textbox("setValue",strprjManager);
		$('#longitude').textbox("setValue",fprjLng);
		$('#latitude').textbox("setValue",fPrjLat);
		$('#remark').textbox("setValue",strRemark);
		
		//设置不可编辑
		
		$('#prjname').textbox('textbox').attr('readonly',true); 
		
	}
	
	
	//新增工程的方法
	function AddPrjInfo(){
		
		GetHtmlInfo();
		
		var res=null;
		
		$.ajax({
			type : "GET",
			url : "TstWebRequest/TstResquest",
			/* data: "page=" + page + "&rows=" + rows + "&keyword=" + $('#hdKeyword').val(), */
			async : false,//设置同步
			data : {
				action : "AddPrjInfo",
				prjName:strPrjName,
				prjAddress:strPrjAddress,
				PrjManager:strprjManager,
				PrjRemark:strRemark,
				prjType:nPrjType,
				prjLng:fprjLng,
				PrjLat:fPrjLat,
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				
				
			},
			success : function(data) {
				res=data;
			}
		});
		return res;
	}
	
	//修改工程的方法
	function UpdatePrjInfo(){
		
		GetHtmlInfo();
		
		var res=null;
		
		$.ajax({
			type : "GET",
			url : "TstWebRequest/TstResquest",
			/* data: "page=" + page + "&rows=" + rows + "&keyword=" + $('#hdKeyword').val(), */
			async : false,//设置同步
			data : {
				action : "UpdatePrjInfo",
				prjID:cookie.get('prjID'),
				prjName:strPrjName,
				prjAddress:strPrjAddress,
				PrjManager:strprjManager,
				PrjRemark:strRemark,
				prjType:nPrjType,
				prjLng:fprjLng,
				PrjLat:fPrjLat,
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				
				
			},
			success : function(data) {
				res=eval("(" + data + ")");;
			}
		});
		return res;
		
	}
	
	
	
	
	$(function() {
		
		var action=getQueryString("action");
		
		if(action==null||action==undefined){
			return;
		}
		
		if(action=="add"){
			//新增操作
			
			$('#btnok').click(function() {

				//读取页面的信息，调用保存方法
				var res=AddPrjInfo();
				
				if (window.parent != null && window.parent != undefined) {
					window.parent.CloseEditPrjWin("add");
				}
				
			});
			
			$('#btncancle').click(function() {

				//调用父页面的关闭方法
				if (window.parent != null && window.parent != undefined) {
				window.parent.CloseEditPrjWin("cancle");
			}
				
			});
			
		}else if (action=="update"){
			
			//获取路径中的数据信息
			
			strPrjName=getQueryString("prjnamevalue");
			nPrjType=getQueryString("prjtypevalue");
			strPrjAddress=getQueryString("prjaddressvalue");
			strprjManager=getQueryString("prjmanagervalue");
			fprjLng=getQueryString("prjlngvalue");
			fPrjLat=getQueryString("prjlatvalue");
			strRemark=getQueryString("prjremarkvalue");
			
			SetHtmlInfo();
			
			$('#btncancle').click(function() {
				//调用父页面的关闭方法
				if (window.parent != null && window.parent != undefined) {
					window.parent.WinCloseFun();
				}
			});
			
			$('#btnok').click(function() {
				//console.log($("#chargePersonmain_gis_project").combobox("getValue"));
				var res=UpdatePrjInfo();
				
				if(!res||!res.result||res.result!='true'){
					$.messager.alert('提示','修改失败!');
					return;
				}
				
				//调用父页面的关闭方法
				if (window.parent != null && window.parent != undefined) {
					window.parent.EditPrjSuccess();
					window.parent.WinCloseFun();
				}
			});
		}
		
	});
	
	
	</script>



	<!-- 地图选择页面  -->
	<!-- <div id="w" class="easyui-window" title=""
		data-options="modal:true,closed:true,iconCls:'icon-save',inline:false,top:($(window).height()) * 0.5,   
            left:($(window).width()) * 0.5"
		style="width: 750px; height: 500px;">
		<iframe scrolling="yes" frameborder="0" src="MapSelect.jsp"
			style="width: 100%; height: 90%;"></iframe>
	</div> -->
	<div id="w"></div>
	<!-- 地图选择 -->
	<script>
		$('#getPositionmain_gis_project').click(function() {

			//console.log($(window).height());
			//console.log($(window).width());
			
			var action=getQueryString("action");
			
			var top=0;
			var left=0;
			
			/* if(action=="add"){
				top=($(window).height()) * 0.5;   
	            left=($(window).width()) * 0.5;
			}else if(action=="update"){
				top=($(window).height()) * 0.5;  
			} */
			
			$('#w').window({
				width:700,
				height:400,
				modal:true,
				closed:true,
				inline:false,
				top:top,   
	            left:left,
	            content:"<iframe scrolling=\"yes\" frameborder=\"0\" src=\"MapSelect.jsp"
		    		+"\" style=\"width: 100%; height:100%;\"></iframe>",
		    	title:'',
			});
			
			$('#w').window('open');
			
			//$('#w').window('center');

		});

		//保存
		function SelectMapPoint(x, y) {
			$('#longitude').textbox("setValue", x);
			$('#latitude').textbox("setValue", y);
		}

		//关闭地图选择
		function CloseMapSelect() {
			$('#w').window('close');
		}
	</script>
</body>
</html>