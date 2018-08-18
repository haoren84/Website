<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>传感器</title>

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
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="CreateInsSensor()">新增传感器</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="UpdateInsSensor()">修改传感器</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="DeleteInsSensor()">删除传感器</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="ShowInsSensor()">查看传感器</a>
        </div>
        <table id="dg" title="" class="easyui-datagrid" 
                url="../InstrumentSensor.jsp?action=SelectInsSensor" 
                toolbar="#toolbar" pagination="true" style="width:auto;height:100%;"
                rownumbers="true" fitColumns="true" singleSelect="true"
                pagesize="20" pageList="[20,50,100]">
                <thead>
                    </tr>
		                <th field="SensorID" width="50">名称</th>
		                <th field="SensorMeasureType" width="80">测量类型</th>
		                <th field="SensorSpec" width="80">规格</th>
		                <th field="SensorFactory" width="120">生产厂家</th>
		                <th field="Param1" width="50">指标1</th>
		                <th field="Param2" width="50">指标2</th>
		                <th field="Param3" width="50">指标3</th>
		                <th field="Param4" width="50">指标4</th>
		                <th field="Param5" width="50">指标5</th>
		                <th field="Param6" width="50">指标6</th>
		                <th field="Param7" width="50">指标7</th>
		                <th field="Param8" width="50">指标8</th>
                    </tr>
                </thead>
        </table>
    </div>
    
    <div id="dlg" class="easyui-dialog" style="width:400px"
    data-options="modal:true"closed="true" buttons="#dlg-buttons">
      <form id="fm" method="post" novalidate style="margin:0;padding:20px 50px">
          <div style="margin-bottom:10px">
              <input name="SensorID" class="easyui-textbox" required="true" label="名称:" style="width:100%">
          </div>
          <div style="margin-bottom:10px">
              <input name="SensorMeasureType" class="easyui-textbox" required="true" label="测量类型:" style="width:100%">
          </div>
          <div style="margin-bottom:10px">
              <input name="SensorSpec" class="easyui-textbox" required="true" label="规格:" style="width:100%">
          </div>
          <div style="margin-bottom:10px">
              <input name="SensorFactory" class="easyui-textbox" required="true" label="生产厂家:" style="width:100%">
          </div>
          <div style="margin-bottom:10px">
              <input name="Param1" class="easyui-numberbox" required="true" label="指标1:" style="width:100%">
          </div>
          <div style="margin-bottom:10px">
              <input name="Param2" class="easyui-numberbox" required="true" label="指标2:" style="width:100%">
          </div>
          <div style="margin-bottom:10px">
              <input name="Param3" class="easyui-numberbox" required="true" label="指标3:" style="width:100%">
          </div>
          <div style="margin-bottom:10px">
              <input name="Param4" class="easyui-numberbox" required="true" label="指标4:" style="width:100%">
          </div>
          <div style="margin-bottom:10px">
              <input name="Param5" class="easyui-numberbox" required="true" label="指标5:" style="width:100%">
          </div>
          <div style="margin-bottom:10px">
              <input name="Param6" class="easyui-numberbox" required="true" label="指标6:" style="width:100%">
          </div>
          <div style="margin-bottom:10px">
              <input name="Param7" class="easyui-numberbox" required="true" label="指标7:" style="width:100%">
          </div>
          <div style="margin-bottom:10px">
              <input name="Param8" class="easyui-numberbox" required="true" label="指标8:" style="width:100%">
          </div>
      </form>
 </div>
    
  <div id="dlg-buttons">
      <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="SaveInsSensor()" style="width:90px">保存</a>
      <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')" style="width:90px">取消</a>
  </div>
      
   <script type="text/javascript">
    var url;
    function CreateInsSensor(){
        $('#dlg').dialog('open').dialog('center').dialog('setTitle','新增传感器');
        $('#fm').form('clear');
        url = '../InstrumentSensor.jsp?action=CreateInsSensor';
    }
    function UpdateInsSensor(){
        var row = $('#dg').datagrid('getSelected');
        if (row){
            $('#dlg').dialog('open').dialog('center').dialog('setTitle','修改传感器');
            $('#fm').form('load',row);
            url = '../InstrumentSensor.jsp?action=UpdateInsSensor&AutoID='+row.AutoID;
        }
    }
    
    function ShowInsSensor(){
        
    }    
    function SaveInsSensor(){
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
    function DeleteInsSensor(){
        var row = $('#dg').datagrid('getSelected');
        if (row){
            $.messager.confirm('提示','确认删除选中项?',function(r){
                if (r){
                    $.post('../InstrumentSensor.jsp?action=DeleteInsSensor',{AutoID:row.AutoID},function(result){
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