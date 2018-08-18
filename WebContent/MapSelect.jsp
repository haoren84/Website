<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>地图选择</title>

<script type="text/javascript"
	src="http://api.map.baidu.com/api?v=3.0&ak=Nii1WdRnGdGOWlkov0c36Gutpp0y8XsF"></script>

<script src="Resource/jquery-easyui-1.5.4.2/jquery.min.js"></script>

<link rel="stylesheet" type="text/css"
	href="Resource/jquery-easyui-1.5.4.2/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="Resource/jquery-easyui-1.5.4.2/themes/icon.css">

<script src="Resource/jquery-easyui-1.5.4.2/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="Resource/jquery-easyui-1.5.4.2/locale/easyui-lang-zh_CN.js"></script>

<style type="text/css">
html {
	height: 100%
}

body {
	height: 100%;
	margin: 0px;
	padding: 0px
}

#container {
	height: 100%
}
</style>

</head>
<body>
	
	<div class="easyui-layout" data-options="fit:true">
		
		<div data-options="region:'center'" style="padding: 10px;">
			<!-- Map -->
			<div id="container"></div>
		</div>
		
		<div data-options="region:'south',border:false"
			style="text-align: right; padding: 5px; border-color: #dddddd #95B8E7 #95B8E7 #95B8E7; background: #F4F4F4;">
			<a href="javascript:void(0)" class="easyui-linkbutton c6"
				iconCls="icon-ok" id="btnOK" style="width: 90px">保存</a> <a
				href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-cancel" id="btnCancle" style="width: 90px">取消</a>

			</div>
	
	</div>
	
	<script>
		var xValue = "", yValue = "";
		var map = new BMap.Map("container"); //地图以及初始定位
		var point = new BMap.Point(99.894149, 35.460649);
		map.centerAndZoom(point, 5);
		map.enableScrollWheelZoom(true); //开启鼠标滚轮缩放
		map.addControl(new BMap.NavigationControl()); //缩放
		map.addControl(new BMap.ScaleControl()); //比例尺

		function showInfo(e) {
			//alert(e.point.lng + ", " + e.point.lat);

			//清除之前的点

			var allOverlay = map.getOverlays();

			var nLength = allOverlay.length - 1;

			for (var i = nLength; i >= 0; i--) {
				map.removeOverlay(allOverlay[i]);
			}

			//记录下xy的值
			xValue = e.point.lng;
			yValue = e.point.lat;

			//添加标识

			var objpoint = new BMap.Point(e.point.lng, e.point.lat);

			var marker = new BMap.Marker(objpoint); // 创建标注    

			map.addOverlay(marker); // 将标注添加到地图中

		}

		map.addEventListener("click", showInfo);
	</script>

	<script>
		//确认保存
		$('#btnOK').click(function() {
			if (window.parent != null && window.parent != undefined) {
				window.parent.SelectMapPoint(xValue, yValue);
				window.parent.CloseMapSelect();
			}
		});

		//取消
		$('#btnCancle').click(function() {
			if (window.parent != null && window.parent != undefined) {
				window.parent.CloseMapSelect();
			}
		});
	</script>
</body>
</html>