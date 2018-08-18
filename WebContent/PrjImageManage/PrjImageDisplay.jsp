<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>工程图片展示</title>

<link rel="stylesheet"
	href="../Resource/bootstrap-3.3.7-dist/css/bootstrap.min.css">

<link rel="stylesheet" type="text/css"
	href="../Resource/jquery-easyui-1.5.4.2/themes/gray/easyui.css">
<link rel="stylesheet" type="text/css"
	href="../Resource/jquery-easyui-1.5.4.2/themes/icon.css">

<script src="../Resource/jquery-easyui-1.5.4.2/jquery.min.js"></script>
<script src="../Resource/jquery-easyui-1.5.4.2/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="../Resource/jquery-easyui-1.5.4.2/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="../Resource/jshelper/commonfun.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="../Resource/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>

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

	<div id="p" class="easyui-panel" title="工程图片展示" 
        style="width: 80%; height: 80%;;padding:10px;background:#fafafa;"
        data-options="iconCls:'icon-large-picture',closable:false,
                collapsible:false,minimizable:false,maximizable:false">
		<img id="prjImage" alt="" src="../Resource/Images/微功耗框图.png" style="width: 100%; height: 100%;"/>
	</div>
	<script>
		
	$(function() {
	
	 var objPrjInfo = GetPrjInfo();
	 
	 if (objPrjInfo == null || objPrjInfo == undefined) {
			return;
		}
	 
	 $.ajax({
			type : "GET",
			url : "../DictionaryComboRequest?action=GetPrjTypeCombo",
			/* data: "page=" + page + "&rows=" + rows + "&keyword=" + $('#hdKeyword').val(), */
			async : true,//设置同步
			data : {
				
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				console.log('delete monitor error!');
			},
			success : function(data) {
				
				var objPrjType = eval("(" + data + ")");
				
				if(objPrjType){
					
					//console.log(objPrjType);
					
					var nObjLength=objPrjType.length;
					
					for(var i=0;i<nObjLength;i++){
						
						var objItem=objPrjType[i];
						
						if(objItem.id==objPrjInfo.prjtype){
							
							switch(objItem.dicvalue){
								
							case "桥梁":
								{$("#prjImage").attr("src","../Resource/Images/桥梁在线监测系统原图.jpg");}
								break;
							case "基坑":
								{$("#prjImage").attr("src","../Resource/Images/基坑在线监测原图.jpg");}
								break;
							case "港口机械":
								{$("#prjImage").attr("src","../Resource/Images/港口机械在线监测原图.jpg");}
								break;
							
							}
							
						}
						
					}
					
				}
				
			}
		});
		
	});
	
	function GetPrjInfo() {

		var result;

		$.ajax({
			type : "GET",
			url : "../TstWebRequest/TstResquest",
			/* data: "page=" + page + "&rows=" + rows + "&keyword=" + $('#hdKeyword').val(), */
			async : false,//设置同步
			data : {
				action : "GetPrjInfoByID",
				prjID : cookie.get('prjID'),
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				console.log('data error!');
			},
			success : function(data) {
				var obj = eval("(" + data + ")");

				result = obj;
			}
		});

		return result;
	}
	
	</script>
	
</body>
</html>