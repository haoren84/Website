<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>终端</title>

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
		html 
		{
		    height: 100%;
		}
		
		body 
		{
		    height: 95%;
		    margin: 5px;
		    padding: 0px
		}
    </style>
</head>
<body>
    <div id="p" class="easyui-panel" title="查询条件" style="width:auto;height:10%;min-height:100px;padding:15px;" >
        <input class="easyui-textbox" data-options="label:'出厂编号：',labelPosition:'left'" 
                style="width:300px;height:25px;">
        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" style="width:80px">查询</a>
    </div>
    <div style="margin:0px 0 0px 0";style="width:auto;height:2%;"></div>
	<div id="p" class="easyui-panel" title="" style="width:auto;height:80%;min-height:400px;" >
	   <div id="toolbar" style="width:auto;height:30px;padding:15px;">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="CreateInsTerminal()">新增终端</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="UpdateInsTerminal()">修改终端</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="DeleteInsTerminal()">删除终端</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="ShowInsTerminal()">查看终端</a>
        </div>
        <table id="dg" title="" class="easyui-datagrid" 
                url="../InstrumentTerminal.jsp?action=SelectInsTerminal" 
                toolbar="#toolbar" pagination="true" style="width:auto;height:100%;"
                rownumbers="true" fitColumns="true" singleSelect="true"
                pagesize="20" pageList="[20,50,100]">
                <thead>
                    </tr>
		                <th field="insFactoryID" width="50">出厂编号</th>
		                <th field="insID" width="50">机号</th>
		                <th field="insType" width="50">仪器类型</th>
		                <th field="insChnCount" width="50">通道个数</th>
                    </tr>
                </thead>
        </table>
    </div>
    
    <div id="dlg" class="easyui-dialog" style="width:400px"
        data-options="modal:true"closed="true" buttons="#dlg-buttons">
        <form id="fm" method="post" novalidate style="margin:0;padding:20px 50px">
            <div style="margin-bottom:10px">
                <input name="insFactoryID" class="easyui-textbox" required="true" label="出厂编号:" style="width:100%">
            </div>
            <div style="margin-bottom:10px">
                <input name="insID" class="easyui-numberbox" required="true" label="机号:" style="width:100%">
            </div>
            <div style="margin-bottom:10px">
                <input name="insType" class="easyui-numberbox" required="true" label="仪器类型:" style="width:100%">
            </div>
            <div style="margin-bottom:10px">
                <input name="insChnCount" class="easyui-numberbox" required="true" label="通道个数:" style="width:100%">
            </div>
        </form>
     </div>
        
      <div id="dlg-buttons">
          <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="SaveInsTerminal()" style="width:90px">保存</a>
          <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')" style="width:90px">取消</a>
      </div>
    
   <script type="text/javascript">
    var url;
    function CreateInsTerminal(){
        $('#dlg').dialog('open').dialog('center').dialog('setTitle','新增终端');
        $('#fm').form('clear');
        url = '../InstrumentTerminal.jsp?action=CreateInsTerminal';
    }
    function UpdateInsTerminal(){
        var row = $('#dg').datagrid('getSelected');
        if (row){
            $('#dlg').dialog('open').dialog('center').dialog('setTitle','修改终端');
            $('#fm').form('load',row);
            url = '../InstrumentTerminal.jsp?action=UpdateInsTerminal&AutoID='+row.AutoID;
        }
    }
    function ShowInsTerminal(){
        
    }
    function SaveInsTerminal(){
        $('#fm').form('submit',{
            url: url,
            onSubmit: function(){
                return $(this).form('validate');
            },
            success: function(result){
                var result = eval('('+result+')');
                if (result.errorMsg){
                    $.messager.show({
                        title: '错误',
                        msg: result.errorMsg
                    });
                } else {
                    $('#dlg').dialog('close');      // close the dialog
                    $('#dg').datagrid('reload');    // reload 
                }
            }
        });
    }
    function DeleteInsTerminal(){
        var row = $('#dg').datagrid('getSelected');
        if (row){
            $.messager.confirm('提示','确认删除选中项?',function(r){
                if (r){
                    $.post('../InstrumentTerminal.jsp?action=DeleteInstrumentTerminal',{AutoID:row.AutoID},function(result){
                        if (result.errorMsg)
                            $.messager.show({    // show error message
                                title: '错误',
                                msg: result.errorMsg
                            });
                        
                    else 
                    {
                         $('#dg').datagrid('reload');    // reload the user data
                    }
                 },'json');
                }
            });
        }
    }
</script>
    
    
</body>
</html>