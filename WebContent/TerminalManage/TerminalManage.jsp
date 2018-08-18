<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>终端管理</title>

<link rel="stylesheet" type="text/css"
	href="../Resource/jquery-easyui-1.5.4.2/themes/gray/easyui.css">
<link rel="stylesheet" type="text/css"
	href="../Resource/jquery-easyui-1.5.4.2/themes/icon.css">

<script src="../Resource/jquery-easyui-1.5.4.2/jquery.min.js"></script>
<script src="../Resource/jquery-easyui-1.5.4.2/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="../Resource/jquery-easyui-1.5.4.2/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="../Resource/jshelper/commonfun.js"></script>
<script type="text/javascript" src="../Resource/jshelper/RelationAcqjs.js"></script>
<script type="text/javascript" src="../Resource/jshelper/RelationTerminaljs.js"></script>
<script type="text/javascript" src="../Resource/jshelper/RelationSeneorjs.js"></script>
<script type="text/javascript" src="../Resource/jshelper/RelationMapInfojs.js?id=111"></script>

<style type="text/css">
html {
	height: 100%
}

body {
	height: 100%;
	margin: 0px;
	padding: 0px
}

.datagrid-header .datagrid-cell span {
  font-size: 12px;
  font-weight:900;
}

#search input{
    		margin-left: 5px;
    		margin-right: 20px;
    	}
</style>

</head>
<body class="easyui-layout">
<div data-options="region:'west',title:'',split:true,collapsible:true," style="width: 200px;">
        <ul class="easyui-tree">
            <li>
                <span>设备管理</span>
                <ul>
                    <li><span>采集仪概况</span></li>
                    <li><span>参数设置</span></li>
                    <li><span>离线报警设置</span></li>
                </ul>
            </li>
        </ul>
    </div>
    <div data-options="region:'center',title:'',split:false,collapsible:true," 
    	style="padding: 0px; margin: 0px; background: #eee;">
       
       		<!--content-->
			<div id="contentdiv" style="height: 100%;"></div>
			 
    </div>
    <script>
        $(function(){
        	
        	$('.easyui-tree li ul li span').click(function(){
        		//console.log(this.innerText);
        		
        		var dataurl='TerminalDetail.jsp';
        		
        		switch(this.innerText){
        		
        		case "采集仪概况":
        			{
        				
        			}
        			break;
        		case "参数设置":
        			{
        				
        			}
        			break;
        		case "离线报警设置":
    				{
        				
    				}
    				break;
        		
        		}
        		
        		var iframe = '<iframe';
				iframe += ' src="' + dataurl + '"';
				iframe += ' style="width: 100%; height: 100%; border: 0px;" scrolling="yes" frameborder="0" ';
				iframe += '></iframe>';

				document.getElementById("contentdiv").innerHTML = iframe;
        	});
        	
        });
    </script>
</body>
</html>