/**
 * url正则读取 cookie操作,easyui 提示框中按钮
 */

//计算字符串长度
function ComputeStrLength(str) {
  if (str == null) return 0;
  if (typeof str != "string"){
    str += "";
  }
  return str.replace(/[^\x00-\xff]/g,"01").length;
}

//easyui 提示框中按钮设置为中文
$.extend($.messager.defaults,{  
    ok:"确定",  
    cancel:"取消"  
});

//url的参数
function getQueryString(name) {
	var res="";
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	var r = window.location.search.substr(1).match(reg);
	if (r != null){
		
		try{
			res= decodeURI(r[2]);
		}catch(err){
			res=r[2];
		}
		
	}
	
	return res;
}

var cookie = {
	    set: function (key, val, time) {// 设置cookie方法
	        var date = new Date(); // 获取当前时间
	        var expiresDays = time;  // 将date设置为n天以后的时间
	        date.setTime(date.getTime() + expiresDays * 24 * 3600 * 1000); // 格式化为cookie识别的时间
	        document.cookie = key + "=" + val + ";expires=" + date.toGMTString();  // 设置cookie
	    },

	    get: function (key) {// 获取cookie方法
	        /* 获取cookie参数 */
	        var getCookie = document.cookie.replace(/[ ]/g, "");  // 获取cookie，并且将获得的cookie格式化，去掉空格字符
	        var arrCookie = getCookie.split(";")  // 将获得的cookie以"分号"为标识
													// 将cookie保存到arrCookie的数组中
	        var tips;  // 声明变量tips
	        for (var i = 0; i < arrCookie.length; i++) {   // 使用for循环查找cookie中的tips变量
	            var arr = arrCookie[i].split("=");   // 将单条cookie用"等号"为标识，将单条cookie保存为arr数组
	            if (key == arr[0]) {  // 匹配变量名称，其中arr[0]是指的cookie名称，如果该条变量为tips则执行判断语句中的赋值操作
	                tips = arr[1];   // 将cookie的值赋给变量tips
	                break;   // 终止for循环遍历
	            }
	        }
	        return tips;
	    },

	    delete: function (key) { // 删除cookie方法
	        var date = new Date(); // 获取当前时间
	        date.setTime(date.getTime() - 10000); // 将date设置为过去的时间
	        document.cookie = key + "=v; expires =" + date.toGMTString();// 设置cookie
	    }

	}


//日期yyyy-MM-dd HH:mm:ss字符串 转换为Date()
function convertDateFromString(dateString) {
    if (dateString) {
        var arr1 = dateString.split(" ");
        var sdate = arr1[0].split('-');
        var stime = arr1[1].split(':');
        var date = new Date(sdate[0], sdate[1] - 1, sdate[2], stime[0], stime[1], stime[2]);
        return date;
    }
}

//格式化输入日期
function getFormatDate(inputdate) {

    if (inputdate == null || inputdate == undefined) {
        inputdate = new Date();
    } else {
        inputdate = new Date(inputdate);
    }

    var seperator1 = "-";

    var seperator2 = ":";

    var month = inputdate.getMonth() + 1;

    var strDate = inputdate.getDate();

    var strHours = inputdate.getHours();

    var strMinute = inputdate.getMinutes();

    var strSeconds = inputdate.getSeconds();

    if (month >= 1 && month <= 9) {

        month = "0" + month;

    }

    if (strDate >= 0 && strDate <= 9) {

        strDate = "0" + strDate;

    }

    if (strHours >= 0 && strHours <= 9) {
        strHours = "0" + strHours;
    }

    if (strMinute >= 0 && strMinute <= 9) {
        strMinute = "0" + strMinute;
    }

    if (strSeconds >= 0 && strSeconds <= 9) {
        strSeconds = "0" + strSeconds;
    }



    var currentdate = inputdate.getFullYear() + seperator1 + month + seperator1 + strDate

            + " " + strHours + seperator2 + strMinute

            + seperator2 + strSeconds;

    return currentdate;
}