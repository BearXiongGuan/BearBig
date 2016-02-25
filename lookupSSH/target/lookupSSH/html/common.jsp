<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
	String baseUrl = request.getContextPath();
	request.setAttribute("baseUrl",baseUrl);
%>
<link rel="stylesheet" type="text/css" href="${baseUrl }/js/jquery-easyui-1.3.5/themes/default/easyui.css"/>
<link rel="stylesheet" type="text/css" href="${baseUrl }/js/jquery-easyui-1.3.5/themes/icon.css"/>
<link rel="stylesheet" type="text/css" href="${baseUrl }/js//themes/CykjUiIcon.css"/>
<style type="text/css">
	body,iframe{
		font-family:"宋体","微软雅黑","Times New Roman",Georgia,Serif;
	}
	iframe form{
		padding:10px 3px 2px 15px;
		//background:#ddd
	}
	table tr td{
		padding:6px;
	}
	form table tr td{
		padding:10px;
	}
</style>
<%-- <script type="text/javascript" src="${baseUrl }/js/jquery-easyui-1.3.5/jquery.min.js"></script>
<script type="text/javascript" src="${baseUrl }/js/jquery-1.9.1.min.js"></script> --%>
<script type="text/javascript" src="${baseUrl }/js/requireplugins-jquery-1.7.1.js"></script>
<script type="text/javascript" src="${baseUrl }/js/jquery-easyui-1.3.5/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${baseUrl }/js/jquery-easyui-1.3.5/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${baseUrl }/js/jquery.serializeObj.js"></script>

<script type="text/javascript">
	var baseUrl = "${baseUrl}";
	require.config({
		baseUrl:"${baseUrl}/js/jscontent/"
	});
</script>