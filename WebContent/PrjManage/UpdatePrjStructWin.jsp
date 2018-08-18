<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑结构页面</title>

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


</head>
<body>
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'west',split:true" title="工程结构基本信息"
			style="width: 45%; height: 100%;">

			<table class="form_table" style="margin: 0px;">
				<tr>
					<th>名称</th>
					<td><input id="structname" placeholder="请输入工程结构名称"
						class="easyui-textbox " type="text" required="required" /></td>
				</tr>
				<tr>
					<th>编号</th>
					<td><input id="code" placeholder="请输入工程结构编号"
						class="easyui-textbox " type="text" /></td>
				</tr>
				<tr>
					<th>所属父结构</th>
					<td><input name="parentname" placeholder="请输入所属父结构"
						id="parentDATA_PROJECT_STRUCTURE_dlg" class="easyui-textbox "
						type="text" /></td>
				</tr>
				<tr>
					<th>类型</th>
					<td><input name="type" id="typeDATA_PROJECT_STRUCTURE_dlg"
						class="easyui-textbox " /></td>
				</tr>
				<tr>
					<th>地址</th>
					<td><input id="address" placeholder="请输入工程结构地址"
						class="easyui-textbox " type="text" /></td>
				</tr>
				<tr>
					<th>描述</th>
					<td><textarea class="easyui-textbox" id="note"
							style="width: 126px; height: 126px"></textarea></td>
				</tr>
			</table>
		</div>

		<div data-options="region:'center',title:'工程结构所包含的测点信息',">
			<font color=red>注意：如果当前结构没有安装设备请不要选择测点。</font>
			<table id="monitorPointDATA_PROJECT_STRUCTURE_dlg"></table>
		</div>

		<div data-options="region:'south',border:false"
			style="text-align: right; padding: 0px;">
			<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'"
				href="javascript:void(0)" onclick="BtnOkFun()" style="width: 80px">确认</a>
			<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'"
				href="javascript:void(0)" onclick="BtnCancleFun()"
				style="width: 80px">取消</a>
		</div>
	</div>

	<!-- 所属父结构的数据加载 -->
	<script>
		$(function() {

			$('#parentDATA_PROJECT_STRUCTURE_dlg').combotree(
					{
						url : '../PrjStructResquest?action=GetStructNameTree',
						method : 'get',
						onLoadSuccess : function(row, data) {
							//console.log(row);
							//console.log(data);
							//console.log('structParent:'+structParent);
							if (structParent && structParent != '0') {
								$('#parentDATA_PROJECT_STRUCTURE_dlg')
										.combotree('setValue', structParent);
							}
						},
					});

			$('#typeDATA_PROJECT_STRUCTURE_dlg').combobox({
				valueField : 'label',
				textField : 'value',
				data : [ {
					label : '隧道',
					value : '隧道'
				}, {
					label : '地铁',
					value : '地铁'
				}, {
					label : '区划',
					value : '区划'
				}, {
					label : '街道',
					value : '街道'
				}, {
					label : '社区',
					value : '社区'
				}, {
					label : '房屋',
					value : '房屋'
				} ],
				onLoadSuccess : function() {
					//console.log('structType:'+structType);
					//$('#typeDATA_PROJECT_STRUCTURE_dlg').combobox('setValue', structType);
				},
			});

		})
	</script>

	<!-- 加载所有的监测测点 -->
	<script>
		$(function() {
			$('#monitorPointDATA_PROJECT_STRUCTURE_dlg')
					.treegrid(
							{
								url : '../PrjStructResquest?action=GetStructAllMPointWithSelect',
								//url:'../Resource/jquery-easyui-1.5.4.2/demo/treegrid/treegrid_data1.json',
								method : 'get',
								idField : 'id',
								treeField : 'name',
								parentField : '_parentId',
								columns : [ [ {
									field : 'name',
									title : '名称',
									width : 180
								}, {
									field : 'type',
									title : '类型',
									width : 80
								} ] ],
								rownumbers : true,
								singleSelect : false,
								animate : true,
								collapsible : true,
								border : false,
								striped : true,
								cascadeCheck : true,
								deepCascadeCheck : true,
								onClickRow : function(row) {
									console.log(row);
								},
								onLoadSuccess : function(row, data) {
									//console.log(row);
									console.log(data);

									var ndataLength = data.rows.length;

									for (var i = 0; i < ndataLength; i++) {
										if (data.rows[i].isselect && data.rows[i].isselect == "true") {
											$(
													'#monitorPointDATA_PROJECT_STRUCTURE_dlg')
													.treegrid('select', data.rows[i].id);
										}
									}

								},

							});

		})
	</script>

	<!-- 加载传来的信息 -->
	<script>
		function objPointItem(strMName, nMid, strPName, nPid) {
			this.strMName = strMName;//监测名称
			this.nMid = nMid;//监测id
			this.strPName = strPName;//测点名称
			this.nPid = nPid;//测点ID
		}

		objPointItem.property = {
			constructor : objPointItem,
		}
	
		var structName, structCode, structType, structParent, structRemark, structAddress;

		$(function() {

			structName = getQueryString("structName");
			structCode = getQueryString("structCode");
			structType = getQueryString("structType");
			//console.log('structType0:'+structType);
			structParent = getQueryString("structParent");
			structRemark = getQueryString("structRemark");
			structAddress = getQueryString("structAddress");

			$('#structname').textbox('setValue', structName)
			$('#code').textbox('setValue', structCode);
			$('#typeDATA_PROJECT_STRUCTURE_dlg').combobox('setValue',
					structType);
			$('#address').textbox('setValue', structRemark);
			$('#note').textbox('setValue', structAddress);
		});

		//获取左侧的信息
		function GetLeftInfo() {
			structName = $('#structname').textbox('getValue');
			structCode = $('#code').textbox('getValue');
			structType = $('#typeDATA_PROJECT_STRUCTURE_dlg').combobox(
					'getValue');
			structParent = $('#parentDATA_PROJECT_STRUCTURE_dlg').combotree(
					'getValue');
			structRemark = $('#address').textbox('getValue');
			structAddress = $('#note').textbox('getValue');
		}
		
		var arrayPoints = new Array();

		//获取选中的测点
		function GetSelectPoints() {

			if (arrayPoints.length > 0) {
				arrayPoints.splice(0, arrayPoints.length);
			}

			var SelectPointArray = $('#monitorPointDATA_PROJECT_STRUCTURE_dlg')
					.treegrid('getSelections');

			if (SelectPointArray == null
					|| SelectPointArray.length == undefined
					|| SelectPointArray.length <= 0) {
				return;
			}

			var nLength = SelectPointArray.length;

			for (var i = 0; i < nLength-1; i++) {

				var item = SelectPointArray[i];

				if (item.type == "测点") {

					var pointItem = new objPointItem(item.curpname,
							item.curpid, item.name, item.curid);
	
					arrayPoints.push(pointItem);
				}
			}
		}
	</script>

	<!-- 按钮触发的方法 -->
	<script>
	//确认按钮的触发方法		
	function BtnOkFun() {
		
		var updateRes=UpdateStructInfo();
		
		var updateJson=JSON.parse(updateRes);
		
		if(updateJson.result=="true"){
			if (window.parent != null && window.parent != undefined) {
				window.parent.CloseAddStruct("update",updateJson.errorMsg);
			}
		}else{
			$.messager.alert('提示',updateJson.errorMsg);
		}
		
	}
	
	//取消按钮的触发方法
	function BtnCancleFun() {
			if (window.parent != null && window.parent != undefined) {
				window.parent.CloseAddStruct("cancle","");
			}
		}
	
	</script>
	
	<!-- 请求后台的修改方法 -->
	<script>
		
	function UpdateStructInfo(){
		
		var res=null;
		
		GetLeftInfo();
		GetSelectPoints();
		
		$.ajax({
			type : "GET",
			url : "../PrjStructResquest",
			/* data: "page=" + page + "&rows=" + rows + "&keyword=" + $('#hdKeyword').val(), */
			async : false,//设置同步
			data : {
				action : "UpdateStruct",
				structName:structName,
				structCode:structCode,
				structType:structType,
				structParent:structParent,
				structRemark:structRemark,
				structAddress:structAddress,
				points:JSON.stringify(arrayPoints),
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				//alert(textStatus);
				//$.messager.progress('close');
				$.messager.alert('提示', 'error');
			},
			success : function(data) {
				res=data;
			}
		});
		
		return res;
	}
	
	</script>

</body>
</html>