<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>主页</title>
</head>
<jsp:include page="common.jsp"></jsp:include>
<body class="easyui-layout" style="width:100%;height:100%;border:false">
	<div data-options="region:'north'" style="height:100px">aaa</div>
	<div data-options="region:'west',split:true,iconCls:'icon-reload',title:'菜单'" style="width:200px;">
		<ul id="menuTree"></ul>
	</div>
	<div data-options="region:'center'" style="border:false">
		<iframe id="iframe" frameborder="0" scrolling="no" style="width:100%;height:100%" marginheight="0" marginwidth="0" allowtransparency=”yes” src="${baseUrl}/index.jsp"></iframe>
	</div>
</body>
<script type="text/javascript">

	require(['main/main'],function(){
		init_main();
	});
</script>
</html>