<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑字典可用状态</title>

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

<!-- <div class="easyui-panel" title="字典状态" style="width:100%;height:100%;"> -->

	<table id="tb_Dictionary" style="width:100%;height:100%;">
	
	</table>

<!-- </div> -->

	<script>
	
	var nParentID='0';
	
	
	
	$(function() {
		
		nParentID=getQueryString("nParentID");
		
		var InLoadSuccess=0;
		
		$('#tb_Dictionary').datagrid({
			url : '../DictionaryRequest?action=GetDicUseStateData&DicParentID='+nParentID,
			
			columns : [ [ {
				field:'ck',
				checkbox:true,
				title : '状态',
				width : 150
			},{
				field : 'DicValue',
				title : '名称',
				width : 200
			} ] ],
			rownumbers : true,
			method : 'get',
			singleSelect : false,
			selectOnCheck:true,
			/* checkOnSelect:true, */
			onLoadSuccess:function(data){
				
				InLoadSuccess=1;
				
				//console.log(data);
				
				for(var i=0;i<data.total;i++){
					
					var rowItem=data.rows[i];
					
					if(rowItem.isUsed=="1"){
						$('#tb_Dictionary').datagrid('checkRow',i);
					}
					
				}
				
				InLoadSuccess=0;
			},
			/* onSelect:function(index,row){
				//console.log(index);
				//console.log(row);
				$('#tb_Dictionary').datagrid('reload');
			}, */
			onCheck:function(index,row){
				
				if(InLoadSuccess==1) return;
				
				//console.log(row);
				
				//更新字典信息
				
				row.isUsed="1";
				
				var updateRes = JSON.parse(UpdateState(row.isUsed,row.id));
				
				$('#tb_Dictionary').datagrid('reload');
				
				if (window.parent != null
						&& window.parent != undefined) {
					window.parent.ReflashTreeGrid();
				}
			},
			onUncheck:function(index,row){
				if(InLoadSuccess==1) return;
				
				//console.log(row);
				
				row.isUsed="0";
				
				var updateRes = JSON.parse(UpdateState(row.isUsed,row.id));
				
				$('#tb_Dictionary').datagrid('reload');
				
				if (window.parent != null
						&& window.parent != undefined) {
					window.parent.ReflashTreeGrid();
				}
			},
			onCheckAll:function(rows){
				
				if(InLoadSuccess==1) return;
				console.log(rows);
				
				var nLength=rows.length;
				
				for(var i=0;i<nLength;i++){
					
					var rowItem=rows[i];
					
					if(rowItem.isUsed=="1") continue;
					
					rowItem.isUsed="1";
					
					var updateRes = JSON.parse(UpdateState(rowItem.isUsed,rowItem.id));
					
					if (window.parent != null
							&& window.parent != undefined) {
						window.parent.ReflashTreeGrid();
					}
					
				}
				
				$('#tb_Dictionary').datagrid('reload');
				
				
			},
			onUncheckAll:function(rows){
				
				if(InLoadSuccess==1) return;
				console.log(rows);
				
				var nLength=rows.length;
				
				for(var i=0;i<nLength;i++){
					
					var rowItem=rows[i];
					
					if(rowItem.isUsed=="0") continue;
					
					rowItem.isUsed="0";
					
					var updateRes = JSON.parse(UpdateState(rowItem.isUsed,rowItem.id));
					
					if (window.parent != null
							&& window.parent != undefined) {
						window.parent.ReflashTreeGrid();
					}
					
				}
				
				$('#tb_Dictionary').datagrid('reload');
			},
			
			
		});
		
		
	});
	
	//修改状态的方法
	function UpdateState(strState,nAutoID){
		
		var res = null;

		$.ajax({
			type : "GET",
			url : "../DictionaryRequest",
			/* data: "page=" + page + "&rows=" + rows + "&keyword=" + $('#hdKeyword').val(), */
			async : false,//设置同步
			data : {
				action : "UpdateDicInfoState",
				isUsed : strState,
				DicID : nAutoID,
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