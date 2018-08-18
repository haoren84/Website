<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑终端信息</title>

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
		<div data-options="region:'center'" style="padding: 10px;">
			<div id="dlg" style="width: 100%;">
				<form id="fm" method="post" novalidate="false" style="margin: 0; padding: 0">
					<div style="margin-bottom: 10px">
						<input id="insFactoryID" class="easyui-combogrid" required="true"
							label="出厂编号:" labelWidth="150px" style="width: 100%" validType="insFactoryID_isExist">
					</div>
					<div style="margin-bottom: 10px">
						<input id="terminalName" class="easyui-textbox" required="true"
							label="终端名称:" labelWidth="150px" style="width: 100%">
					</div>
					<div style="margin-bottom: 10px">
						<input id="terminalType" class="easyui-textbox" required="true"
							label="终端型号:" labelWidth="150px" style="width: 100%">
					</div>
					<div style="margin-bottom: 10px">
						<input id="insID" class="easyui-numberbox" required="true"
							label="机号:" labelWidth="150px" style="width: 100%">
					</div>
					<!-- <div style="margin-bottom: 10px">
						<input id="insType" class="easyui-combobox" required="true"
							label="仪器类型:" labelWidth="150px" style="width: 100%">
					</div> -->
					<div style="margin-bottom: 10px">
						<input id="insChnCount" class="easyui-numberbox" required="true"
							label="通道个数:" labelWidth="150px" style="width: 100%">
					</div>
					<div style="margin-bottom: 10px">
						<input id="netID" class="easyui-textbox" required="true"
							label="网络ID:" labelWidth="150px" style="width: 100%">
					</div>
					<div style="margin-bottom: 10px">
						<input id="terminalDesc" class="easyui-textbox" required="true"
							label="终端描述:" labelWidth="150px" style="width: 100%"
							multiline="true">
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
	
		var nAutoID, insFactoryID, insID, insType, insChnCount,terminalName,terminalType,terminalDesc,netID;
		var oldFactoryID;
		var action;

		$(function() {

			action = getQueryString("action");

			$('#insFactoryID').combogrid({

				url : "../InsTerminalRequest",
				queryParams : {
					action : 'GetTerminalFactoryComboData',
				},
				panelWidth : 400,
				idField : 'autoid', //ID字段  
				textField : 'insFactoryID', //显示的字段
				fitColumns : true,
				striped : true,
				editable : true,
				pagination : true, //是否分页
				rownumbers : true, //序号
				collapsible : false, //是否可折叠的
				singleSelect:true,
				method : 'get',
				columns : [ [ {
					field : 'insFactoryID',
					title : '出厂编号',
					width : 100,
					//hidden : true
				}, {
					field : 'terminalName',
					title : '终端名称',
					width : 150
				}, {
					field : 'terminalType',
					title : '终端类型',
					width : 150
				}, {
					field : 'insID',
					title : '终端机号',
					width : 50
				} ] ],
				onLoadSuccess:function(data){
					//console.log(data);
					
					if(action == "update"){
						//把数据填入到页面
						nAutoID=getQueryString("autoid");
						insFactoryID=getQueryString("insFactoryID"); 
						oldFactoryID=getQueryString("insFactoryID");
						insID=getQueryString("insID");
						insType=getQueryString("insType"); 
						insChnCount=getQueryString("insChnCount");
						terminalName=getQueryString("terminalName");
						terminalType=getQueryString("terminalType");
						terminalDesc=getQueryString("terminalDesc");
						netID=getQueryString("netID");
						
						$('#terminalName').textbox('setValue',terminalName);
						$('#terminalType').textbox('setValue',terminalType);
						$('#insID').textbox('setValue',insID);
						$('#insChnCount').textbox('setValue',insChnCount);
						$('#netID').textbox('setValue',netID);
						$('#terminalDesc').textbox('setValue',terminalDesc);
						$('#insFactoryID').combogrid('setText',insFactoryID);
					}
					
				},
				onSelect:function(record){
					//console.log(record);
					
					//var objSelect=$('#insFactoryID').combogrid('getSelected');
					
					var g = $('#insFactoryID').combogrid('grid');	// get datagrid object
					var r = g.datagrid('getSelected');	// get the selected row
					
					if(r){
						
						$('#terminalName').textbox('setValue',r.terminalName);
						$('#terminalType').textbox('setValue',r.terminalType);
						$('#insID').textbox('setValue',r.insID);
						$('#insChnCount').textbox('setValue',r.insChnCount);
						$('#netID').textbox('setValue',r.netID);
						$('#terminalDesc').textbox('setValue',r.terminalDesc);
						
						//数据记录下来
						
						insFactoryID=r.insFactoryID; 
						insID=r.insID;
						insType=r.insType; 
						insChnCount=r.insChnCount;
						terminalName=r.terminalName;
						terminalType=r.terminalType;
						terminalDesc=r.terminalDesc;
						netID=r.netID;
						
					}
					
				},
				onChange:function(newValue, oldValue){
					//console.log("newValue:");
					//console.log(newValue);
					
				}
			});
			
			//取得分页组件对象
			var pager = $('#insFactoryID').combogrid('grid')
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
										$('#insFactoryID')
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
					url : "../InsTerminalRequest",
					/* data: "page=" + page + "&rows=" + rows + "&keyword=" + $('#hdKeyword').val(), */
					async : false,//设置同步
					data : {
						action : "GetTerminalFactoryComboData",
						page : page,
						rows : rows,
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						alert(textStatus);
						$.messager.progress('close');
					},
					success : function(data) {
						$('#insFactoryID').combogrid('grid')
								.datagrid('loadData', $.parseJSON(data));
					}
				});
			};
			
			if (action == "add") {
				
				$('#btnOK').click(function() {	
					$('#fm').form('submit',{
						onSubmit:function(){
		                    return $(this).form('enableValidation').form('validate');
		                },
						success: function(addRes){    
							var addRes = JSON.parse(AddInsTerminalFun());

							if (addRes && addRes.result == "true") {

								if (window.parent != null
										&& window.parent != undefined) {
									window.parent.CloseEditWin("add",
											addRes.errorMsg);
								}

							} else {
								$.messager.alert('提示', '新增失败');
							}
					    }
					});
					
				});
				
			} else if (action == "update") {
				
				$('#btnOK').click(function() {		
					$('#fm').form('submit',{
						success: function(addRes){ 
							var updateRes = JSON.parse(UpdateInsTerminalFun());

							if (updateRes && updateRes.result == "true") {

								if (window.parent != null
										&& window.parent != undefined) {
									window.parent.CloseEditWin("update",
											updateRes.errorMsg);
								}

							} else {
								$.messager.alert('提示', '修改失败');
							}
						}
					});
				});

			}

		});
		
		function BtnCancle(){
			
			if (window.parent != null && window.parent != undefined) {
				window.parent.CloseEditWin(null, null);
			}
			
		}
	</script>
	
	<script>
	
	//新增终端
	function AddInsTerminalFun(){
		var res = null;

		$.ajax({
			type : "GET",
			url : "../InsTerminalRequest",
			/* data: "page=" + page + "&rows=" + rows + "&keyword=" + $('#hdKeyword').val(), */
			async : false,//设置同步
			data : {
				action : "AddTerminal",
				insFactoryID : insFactoryID,
				insID : insID,
				insType : insType,
				insChnCount : insChnCount,
				terminalName:terminalName,
				terminalType:terminalType,
				terminalDesc:terminalDesc,
				netID:netID,
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
	
	//修改终端
	function UpdateInsTerminalFun() {
		
		var res = null;

		$.ajax({
			type : "GET",
			url : "../InsTerminalRequest",
			/* data: "page=" + page + "&rows=" + rows + "&keyword=" + $('#hdKeyword').val(), */
			async : false,//设置同步
			data : {
				action : "UpdateTerminal",
				oldFactoryID:oldFactoryID,
				autoid : nAutoID,
				insFactoryID : insFactoryID,
				insID : insID,
				insType : insType,
				insChnCount : insChnCount,
				terminalName:terminalName,
				terminalType:terminalType,
				terminalDesc:terminalDesc,
				netID:netID,
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
	<script>
	// 验证出厂编号是否存在
	$.extend($.fn.validatebox.defaults.rules, {
		insFactoryID_isExist: {
	        // ...
	        
	        validator: function (value, param) {
	        	var bool=false;
	        	$.ajax({
	            	type: "get",
	               	dataType: 'json',
	               	async:false,   //没有async属性表单不能通过验证
	        		url: '../InsTerminalRequest',
	        		data : {
	        			action: "CheckTerminalisExist",
	        			insFactoryID: insFactoryID,
	        		},
	        		cache: false,
	        		success: function(data){
	        			if(data&&data.result=="true"){
	        				$.fn.validatebox.defaults.rules.insFactoryID_isExist.message = '该编号已存在,请重新输入'; 
	        				bool=false;
	        			}else{
	        				$.fn.validatebox.defaults.rules.insFactoryID_isExist.message ='该编号不存在,可以输入';
	        				bool=true;        
	                    }
	        		}
	           	});
                return bool;   
			},
	        message: ''
	    }
	});
	
	</script>

</body>
</html>