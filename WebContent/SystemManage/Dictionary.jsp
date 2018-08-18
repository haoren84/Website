<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>字典</title>

<link rel="stylesheet" type="text/css"
	href="../Resource/jquery-easyui-1.5.4.2/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="../Resource/jquery-easyui-1.5.4.2/themes/icon.css">

<script src="../Resource/jquery-easyui-1.5.4.2/jquery.min.js"></script>
<script src="../Resource/jquery-easyui-1.5.4.2/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="../Resource/jquery-easyui-1.5.4.2/locale/easyui-lang-zh_CN.js"></script>  
 <script type="text/javascript"
	src="../Resource/jshelper/commonfun.js"></script>
		
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
<!-- http://testelectron.eicp.net:8080/MonitorWeb/PrjManage/PrjDictionary.jsp?projectId=2&projectName=%E6%88%BF%E5%B1%8B%E7%9B%91%E6%B5%8Bwww -->
	<div class="easyui-panel" title="字典管理" style="width:100%;height:100%;">
	
	<!-- 项目结构窗口  -->
	<div id="win"></div>
	
	<table id="tb_Dictionary" style="width:100%;height:100%;"></table>
	
    </div>	
    
    
	
	<!-- 加载表格数据 -->
	<script>
	
	var tbDictionaryToolbar = [
		{
			text : '添加',
			iconCls : 'icon-add',
			handler : function() 
			{
				AddDictionary();
			}
		},
		{
			text : '编辑',
			iconCls : 'icon-edit',
			handler : function() 
			{
				UpdateDictionary();
			}
		},
		{
			text : '删除',
			iconCls : 'icon-remove',
			handler : function() 
			{
				DeleteDictionary();
			}
		} ];	
	
	$(function() 
	{
		//结构的树表格
		$('#tb_Dictionary').treegrid
		(
		{
		    url:'../Dictionary.jsp?action=GetDictionaryData',
		    idField:'id',
		    treeField:'Name',
		    columns:[[
		        {field:'Name',title:'字典名称',width:180},
		        {field:'value',title:'字典值',width:80},
		        {field:'isUsed',title:'是否使用',width:80},
		        {field:'DictionaryParent',title:'父节点',width:80},
		    ]],
		    rownumbers: true,
		    pagination: true,
            pageSize: 10,
            pageList: [5,10,20],
            onBeforeLoad: function(row,param)
            {
                if (!row) 
                {    // load top level rows
                    param.id = 0;    // set id=0, indicate to load new page rows
                }
            },
            toolbar:tbDictionaryToolbar,
		}
		);
	});	
	</script>

	<!-- 新增结构的弹框 -->
	<script>
	
	function AddDictionary()
	{
		var objSelect = $('#tb_Dictionary').treegrid('getSelected');
		if(objSelect==null||objSelect==undefined)
		{
			$('#win').window
			(
			{
			   width:400,
			   height:250,
			   modal:true,
			   content:"<iframe scrolling=\"yes\" frameborder=\"0\"src=\"AddDictionaryWin.jsp?action=add"				   		
			   	    +"\"style=\"width: 100%; height:100%;\"></iframe>",
			  		title:'新增字典',		    
			}
			);		
			$('#win').window('open');	
		}
		else
		{
			$('#win').window
			(
			{
			   width:400,
			   height:250,
			   modal:true,
			   content:"<iframe scrolling=\"yes\" frameborder=\"0\"src=\"AddDictionaryWin.jsp?action=add&id="
				   	+objSelect.id
	                +"&DictionaryParent="+objSelect.Name	 
			   	    +"\"style=\"width: 100%; height:100%;\"></iframe>",
			  		title:'新增字典',		    
			}
			);		
			$('#win').window('open');	
		}
	}
	

	//更新的弹框
	function UpdateDictionary()
	{		
		var objSelect = $('#tb_Dictionary').treegrid('getSelected');
		if(objSelect==null||objSelect==undefined)
		{
			return;
		}
		else
		{
		   $('#win').window
		   (
			  {
				width:400,
				height:250,
				modal:true,
				content:"<iframe scrolling=\"yes\" frameborder=\"0\" src=\"AddDictionaryWin.jsp?action=update&id="
			   	+objSelect.id
			   	+"&Name="+objSelect.Name
			   	+"&value="+objSelect.value
		   	    +"&isUsed="+objSelect.isUsed
		   	    +"&DictionaryParent="+objSelect.DictionaryParent			   	   
				+"\" style=\"width: 100%; height: 100%;\"></iframe>",
				title:'更新字典',	
			   }
		   );		
		$('#win').window('open');
	   }
	}
	

	
	//关闭新增弹框的方法
	function CloseAddDictionary(actionInfo,actionMsg)
	{		
		if(actionMsg!=null&&actionMsg!=""&&actionMsg!=undefined)
		{
			$.messager.alert('提示',actionMsg);
		}
		$('#win').window('close');
		
		if(actionInfo=="add")
		{
			$('#tb_Dictionary').treegrid('reload');
		}
		
		if(actionInfo=="update")
		{
			$('#tb_Dictionary').treegrid('reload');
		}
	}
	
    function DeleteDictionary()
    {
		var objSelect = $('#tb_Dictionary').treegrid('getSelected');
		if(objSelect==null||objSelect==undefined)
		{
			return;
		}
		else
		{
	      $.messager.confirm('提示','确认删除选中项?',function(r)
   		  {
                if (r)
                {
                    $.post('../Dictionary.jsp?action=DeleteDictionary',{AutoID:objSelect.id},function(result)
                 		{
		                      if (result.errorMsg)
		                      {
		                          $.messager.show
		                          (
		                        	{    // show error message
		                              title: '错误',
		                              msg: result.errorMsg
		                          	}
		                           );
		                      } 
		                      else 
		                      {
		                           $('#dg').datagrid('reload');    // reload the user data
		                      }
                         },'json');
	             }
   		  });
	      $('#tb_Dictionary').treegrid('reload');
		}
    }
	</script>	
		
</body>
</html>