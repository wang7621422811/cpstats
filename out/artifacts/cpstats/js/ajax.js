function InitAjax() 
{
	var http_request = false;
	if (window.XMLHttpRequest) {
		http_request = new XMLHttpRequest();
		if (http_request.overrideMimeType) {
			http_request.overrideMimeType('text/xml');
		}
	} else if (window.ActiveXObject) {
		http_request = new ActiveXObject("Microsoft.XMLHTTP");
		try {
			http_request = new ActiveXObject("Msxml2.XMLHTTP");
		} catch (e) {
			try {
				http_request = new ActiveXObject("Microsoft.XMLHTTP");
			} catch (e) {
				http_request = false;
			}
		}
	}
	return http_request;
}

function alertLayer(title, w, h) {
	var s = document.getElementsByTagName("select"); //--------------把所有select标签捉住
	for ( var j = 0; j < s.length; j++) {
		s[j].style.display = "none";
	}
	if(window.frames["callframe"]!=undefined){
		var chirds = window.frames["callframe"].document.getElementsByTagName("select");
		for ( var j = 0; j < chirds.length; j++) {
			chirds[j].style.display = "none";
		} //--------------设为不显示，再进行下面操作
	}
	//--------------设为不显示，再进行下面操作
	var titleheight = "20px"; // 提示窗口标题高度
	var bordercolor = "#666699"; // 提示窗口的边框颜色
	var titlecolor = "#FFFFFF"; // 提示窗口的标题颜色
	var titlebgcolor = "#1d5798"; // 提示窗口的标题背景色
	var bgcolor = "#FFFFFF"; // 提示内容的背景色
	var iWidth = document.documentElement.clientWidth;
	var iHeight = document.documentElement.clientHeight;
	var bgObj = document.createElement("div");
	//bgObj.set('id', 'bgObjDiv');
	bgObj.setAttribute("id", "bgObjDiv");
	bgObj.style.cssText = "position:absolute;left:0px;top:0px;width:"
			+ iWidth
			+ "px;height:"
			+ Math.max(document.body.clientHeight, iHeight)
			+ "px;filter:Alpha(Opacity=30);opacity:0.3;background-color:#000000;z-index:101;";
	document.body.appendChild(bgObj);
	var msgObj = document.createElement("div");
	//msgObj.set('id', 'msgObjDiv');
	msgObj.setAttribute("id", "msgObjDiv");
	msgObj.style.cssText = "position:absolute;font:11px '宋体';top:"
			+ (iHeight - h) / 2 + "px;left:" + (iWidth - w) / 2 + "px;width:"
			+ w + "px;height:" + h + "px;text-align:center;border:1px solid "
			+ bordercolor + ";background-color:" + bgcolor
			+ ";padding:1px;line-height:22px;z-index:102;";
	document.body.appendChild(msgObj);
	var table = document.createElement("table");
	msgObj.appendChild(table);
	table.style.cssText = "margin:0px;border:0px;padding:0px;";
	table.cellSpacing = 0;
	var tr = table.insertRow(-1);
	var titleBar = tr.insertCell(-1);
	titleBar.style.cssText = "width:100%;height:"
			+ titleheight
			+ "px;text-align:left;padding:3px;margin:0px;font:bold 13px '宋体';color:"
			+ titlecolor + ";border:1px solid " + bordercolor
			+ ";cursor:move;background-color:" + titlebgcolor;
	titleBar.style.paddingLeft = "10px";
	titleBar.innerHTML = title;
	var moveX = 0;
	var moveY = 0;
	var moveTop = 0;
	var moveLeft = 0;
	var moveable = false;
	var docMouseMoveEvent = document.onmousemove;
	var docMouseUpEvent = document.onmouseup;
	titleBar.onmousedown = function() {
		var evt = getEvent();
		moveable = true;
		moveX = evt.clientX;
		moveY = evt.clientY;
		moveTop = parseInt(msgObj.style.top);
		moveLeft = parseInt(msgObj.style.left);
		document.onmousemove = function() {
			if (moveable) {
				var evt = getEvent();
				var x = moveLeft + evt.clientX - moveX;
				var y = moveTop + evt.clientY - moveY;
				if (x > 0 && (x + w < iWidth) && y > 0 && (y + h < iHeight)) {
					msgObj.style.left = x + "px";
					msgObj.style.top = y + "px";
				}
			}
		};
		document.onmouseup = function() {
			if (moveable) {
				document.onmousemove = docMouseMoveEvent;
				document.onmouseup = docMouseUpEvent;
				moveable = false;
				moveX = 0;
				moveY = 0;
				moveTop = 0;
				moveLeft = 0;
			}
		};
	}
	var closeBtn = tr.insertCell(-1);
	closeBtn.style.cssText = "cursor:pointer; padding:2px;background-color:"
			+ titlebgcolor;
	closeBtn.innerHTML = "<span style='font-size:15pt; color:" + titlecolor
			+ ";'>×</span>";
	closeBtn.onclick = function() {
		for ( var j = 0; j < s.length; j++) {
			s[j].style.display = "";
		} 
		if(window.frames["callframe"]!=undefined){
			var chirds = window.frames["callframe"].document.getElementsByTagName("select");
			for ( var j = 0; j < chirds.length; j++) {
				chirds[j].style.display = "";
			} 
		}
		//--------------再给select显出来
		document.body.removeChild(bgObj);
		document.body.removeChild(msgObj);
		if(window.frames["callframe"]!=undefined){
			window.frames["callframe"].location = window.frames["callframe"].location;
		}
	}
	var msgBox = table.insertRow(-1).insertCell(-1);
	msgBox.style.cssText = "font:10pt '宋体';";
	msgBox.colSpan = 2;
	//	msgBox.innerHTML = msg;
	msgBox.innerHTML = "<table border='0' align='center'><tr><td align='center'><br><img id='loadingpic' src='/pages/images/loading.gif'><br><div id='messageDiv'>正在保存信息...</div></td></tr></table>";
	// 获得事件Event对象，用于兼容IE和FireFox
	function getEvent() {
		return window.event || arguments.callee.caller.arguments[0];
	}
}
function returnok() {
	var loadingpic = document.getElementById("loadingpic");
	loadingpic.src="/pages/images/ok.png";
}
function returnerror() {
	var loadingpic = document.getElementById("loadingpic");
	loadingpic.src="/pages/images/error.gif";
}
function closeLayer() {
	var s = document.getElementsByTagName("select");
	var msgObjDiv = document.getElementById("msgObjDiv");
	var bgObjDiv = document.getElementById("bgObjDiv");
	for ( var j = 0; j < s.length; j++) {
		s[j].style.display = "";
	} //--------------再给select显出来
	document.body.removeChild(bgObjDiv);
	document.body.removeChild(msgObjDiv);
}
