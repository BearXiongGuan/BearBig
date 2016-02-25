<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>菜单管理</title>
<jsp:include page="${baseUrl }/html/common.jsp"></jsp:include>
<style type="text/css">
	
	#iconTable tr td{
		border:1px solid #71D800;
		width:16px;
		height:16px;
	}
</style>

</head>
<body class="easyui-layout" data-options="fit:true">
	<div data-options="region:'north',title:'<%=request.getParameter("title") %>',iconCls:'<%=request.getParameter("icon") %>'"
		style="height: 80px;padding:5px">
		<form id="menuQryForm" method="post">
			<table border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td>菜单编号：</td>
					<td><input type="text" class="easyui-validatebox"  id="menuNo" name="menuNo"/></td>
					<td>菜单名称：</td>
					<td><input type="text" class="easyui-validatebox" id="menuName" name="menuName"/></td>
					<td colspan="2"><a id="searchBtn" class="easyui-linkbutton"
						data-options="iconCls:'icon-search',plain:true">查询</a><a id="clearBtn"
						class="easyui-linkbutton" data-options="iconCls:'icon-no',plain:true">重置</a></td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center'">
		<table id="menuGrid" data-options="fit:true,border:false"></table>
		<div id="menuWindow" style="display:none">
		<form id="menuForm" method="post">
			<table border="0" cellpadding="0" cellspacing="0" style="margin:50px auto;">
				<tr>
					<td>菜单编码</td>
					<td><input type="text" class="easyui-validatebox" id="menuNo" name="menuNo" data-options="required:true"/></td>
					<td>菜单名称</td>
					<td><input type="text" class="easyui-validatebox" id="menuName" name="menuName" data-options="required:true"/></td>
				</tr>
				<tr>
					<td>菜单类型</td>
					<td><input type="text" id="menuType" name="menuType"/></td>
					<td>响应路径</td>
					<td><input type="text" class="easyui-validatebox" id="menuUrl" name="menuUrl" data-options="required:true"/></td>
				</tr>
				<tr>
					<td>图标选择</td>
					<td><input type="text" class="easyui-validatebox" id="menuIcon" name="menuIcon" data-options="required:true" readonly="readonly"/><img id="mIcon" src="${baseUrl}/js/jquery-easyui-1.3.5/themes/icons/search.png" style="cursor:pointer;"></img></td>
					<td>菜单排序</td>
					<td><input type="text" class="easyui-validatebox" id="menuOrder" name="menuOrder" data-options="required:true"/></td>
				</tr>
				<tr id="pm_tr" style="visibility: hidden">
				<td>父菜单</td>
					<td><input id="pMenuNo" type="text" name="pMenuNo"/></td>
					<td><input type="hidden" class="easyui-validatebox" id="menuId" name="menuId" data-options="required:false"/></td>
					<td></td>
				</tr>
			</table>
		</form>
	</div>
	<div id="iconDiv" style="padding:2px;display:none">
		<form id="iconForm" method="post">
		<table id="iconTable" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td><span class="ext-icon-anchor">&nbsp;&nbsp;</span></td>
				<td><input type="radio" name="icons"/></td>
				<td><span class="ext-icon-arrow_green">&nbsp;&nbsp;</span></td>
				<td><input type="radio" name="icons"/></td>
				<td><span class="ext-icon-asterisk_orange">&nbsp;&nbsp;</span></td>
				<td><input type="radio" name="icons"/></td>
				<td><span class="ext-icon-asterisk_yellow">&nbsp;&nbsp;</span></td>
				<td><input type="radio" name="icons"/></td>
				<td><span class="ext-icon-attach">&nbsp;&nbsp;</span></td>
				<td><input type="radio" name="icons"/></td>
			</tr>
			<tr>
				<td><span class="ext-icon-bell">&nbsp;&nbsp;</span></td>
				<td><input type="radio" name="icons"/></td>
				<td><span class="ext-icon-bell_add">&nbsp;&nbsp;</span></td>
				<td><input type="radio" name="icons"/></td>
				<td><span class="ext-icon-bell_delete">&nbsp;&nbsp;</span></td>
				<td><input type="radio" name="icons"/></td>
				<td><span class="ext-icon-bell_error">&nbsp;&nbsp;</span></td>
				<td><input type="radio" name="icons"/></td>
				<td><span class="ext-icon-bell_go">&nbsp;&nbsp;</span></td>
				<td><input type="radio" name="icons"/></td>
			</tr>
			<tr>
				<td><span class="ext-icon-bell_link">&nbsp;&nbsp;</span></td>
				<td><input type="radio" name="icons"/></td>
				<td><span class="ext-icon-bin_closed">&nbsp;&nbsp;</span></td>
				<td><input type="radio" name="icons"/></td>
				<td><span class="ext-icon-bin_empty">&nbsp;&nbsp;</span></td>
				<td><input type="radio" name="icons"/></td>
				<td><span class="ext-icon-bomb">&nbsp;&nbsp;</span></td>
				<td><input type="radio" name="icons"/></td>
				<td><span class="ext-icon-book">&nbsp;&nbsp;</span></td>
				<td><input type="radio" name="icons"/></td>
			</tr>
			<tr>
				<td><span class="ext-icon-book_add">&nbsp;&nbsp;</span></td>
				<td><input type="radio" name="icons"/></td>
				<td><span class="ext-icon-book_addresses">&nbsp;&nbsp;</span></td>
				<td><input type="radio" name="icons"/></td>
				<td><span class="ext-icon-book_delete">&nbsp;&nbsp;</span></td>
				<td><input type="radio" name="icons"/></td>
				<td><span class="ext-icon-book_edit">&nbsp;&nbsp;</span></td>
				<td><input type="radio" name="icons"/></td>
				<td><span class="ext-icon-book_error">&nbsp;&nbsp;</span></td>
				<td><input type="radio" name="icons"/></td>
			</tr>
			<tr>
				<td><span class="ext-icon-book_go">&nbsp;&nbsp;</span></td>
				<td><input type="radio" name="icons"/></td>
				<td><span class="ext-icon-book_key">&nbsp;&nbsp;</span></td>
				<td><input type="radio" name="icons"/></td>
				<td><span class="ext-icon-book_link">&nbsp;&nbsp;</span></td>
				<td><input type="radio" name="icons"/></td>
				<td><span class="ext-icon-book_next">&nbsp;&nbsp;</span></td>
				<td><input type="radio" name="icons"/></td>
				<td><span class="ext-icon-book_open">&nbsp;&nbsp;</span></td>
				<td><input type="radio" name="icons"/></td>
			</tr>
			<tr>
				<td><span class="ext-icon-book_previous">&nbsp;&nbsp;</span></td>
				<td><input type="radio" name="icons"/></td>
				<td><span class="ext-icon-box">&nbsp;&nbsp;</span></td>
				<td><input type="radio" name="icons"/></td>
				<td><span class="ext-icon-brick">&nbsp;&nbsp;</span></td>
				<td><input type="radio" name="icons"/></td>
				<td><span class="ext-icon-bricks">&nbsp;&nbsp;</span></td>
				<td><input type="radio" name="icons"/></td>
				<td><span class="ext-icon-brick_add">&nbsp;&nbsp;</span></td>
				<td><input type="radio" name="icons"/></td>
			</tr>
			<tr>
				<td><span class="ext-icon-brick_delete">&nbsp;&nbsp;</span></td>
				<td><input type="radio" name="icons"/></td>
				<td><span class="ext-icon-brick_edit">&nbsp;&nbsp;</span></td>
				<td><input type="radio" name="icons"/></td>
				<td><span class="ext-icon-brick_error">&nbsp;&nbsp;</span></td>
				<td><input type="radio" name="icons"/></td>
				<td><span class="ext-icon-brick_go">&nbsp;&nbsp;</span></td>
				<td><input type="radio" name="icons"/></td>
				<td><span class="ext-icon-brick_link">&nbsp;&nbsp;</span></td>
				<td><input type="radio" name="icons"/></td>
			</tr>
			<tr>
				<td><span class="ext-icon-briefcase">&nbsp;&nbsp;</span></td>
				<td><input type="radio" name="icons"/></td>
				<td><span class="ext-icon-building">&nbsp;&nbsp;</span></td>
				<td><input type="radio" name="icons"/></td>
				<td><span class="ext-icon-building_add">&nbsp;&nbsp;</span></td>
				<td><input type="radio" name="icons"/></td>
				<td><span class="ext-icon-building_delete">&nbsp;&nbsp;</span></td>
				<td><input type="radio" name="icons"/></td>
				<td><span class="ext-icon-building_edit">&nbsp;&nbsp;</span></td>
				<td><input type="radio" name="icons"/></td>
			</tr>
			<tr>
				<td><span class="ext-icon-building_error">&nbsp;&nbsp;</span></td>
				<td><input type="radio" name="icons"/></td>
				<td><span class="ext-icon-building_go">&nbsp;&nbsp;</span></td>
				<td><input type="radio" name="icons"/></td>
				<td><span class="ext-icon-building_key">&nbsp;&nbsp;</span></td>
				<td><input type="radio" name="icons"/></td>
				<td><span class="ext-icon-building_link">&nbsp;&nbsp;</span></td>
				<td><input type="radio" name="icons"/></td>
				<td><span class="ext-icon-bullet_add">&nbsp;&nbsp;</span></td>
				<td><input type="radio" name="icons"/></td>
			</tr>
			<tr>
				<td><span class="ext-icon-bullet_arrow_bottom">&nbsp;&nbsp;</span></td>
				<td><input type="radio" name="icons"/></td>
				<td><span class="ext-icon-bullet_arrow_down">&nbsp;&nbsp;</span></td>
				<td><input type="radio" name="icons"/></td>
				<td><span class="ext-icon-bullet_arrow_top">&nbsp;&nbsp;</span></td>
				<td><input type="radio" name="icons"/></td>
				<td><span class="ext-icon-bullet_arrow_up">&nbsp;&nbsp;</span></td>
				<td><input type="radio" name="icons"/></td>
				<td><span class="ext-icon-bullet_black">&nbsp;&nbsp;</span></td>
				<td><input type="radio" name="icons"/></td>
			</tr>
			<tr>
				<td><span class="ext-icon-bullet_blue">&nbsp;&nbsp;</span></td>
				<td><input type="radio" name="icons"/></td>
				<td><span class="ext-icon-bullet_delete">&nbsp;&nbsp;</span></td>
				<td><input type="radio" name="icons"/></td>
				<td><span class="ext-icon-bullet_disk">&nbsp;&nbsp;</span></td>
				<td><input type="radio" name="icons"/></td>
				<td><span class="ext-icon-bullet_error">&nbsp;&nbsp;</span></td>
				<td><input type="radio" name="icons"/></td>
				<td><span class="ext-icon-bullet_feed">&nbsp;&nbsp;</span></td>
				<td><input type="radio" name="icons"/></td>
			</tr>
			<tr>
				<td><span class="ext-icon-bullet_go">&nbsp;&nbsp;</span></td>
				<td><input type="radio" name="icons"/></td>
				<td><span class="ext-icon-bullet_green">&nbsp;&nbsp;</span></td>
				<td><input type="radio" name="icons"/></td>
				<td><span class="ext-icon-bullet_key">&nbsp;&nbsp;</span></td>
				<td><input type="radio" name="icons"/></td>
				<td><span class="ext-icon-bullet_orange">&nbsp;&nbsp;</span></td>
				<td><input type="radio" name="icons"/></td>
				<td><span class="ext-icon-bullet_picture">&nbsp;&nbsp;</span></td>
				<td><input type="radio" name="icons"/></td>
			</tr>
			<tr>
				<td><span class="ext-icon-bullet_pink">&nbsp;&nbsp;</span></td>
				<td><input type="radio" name="icons"/></td>
				<td><span class="ext-icon-cancel">&nbsp;&nbsp;</span></td>
				<td><input type="radio" name="icons"/></td>
				<td><span class="ext-icon-database">&nbsp;&nbsp;</span></td>
				<td><input type="radio" name="icons"/></td>
				<td><span class="ext-icon-database_add">&nbsp;&nbsp;</span></td>
				<td><input type="radio" name="icons"/></td>
				<td><span class="ext-icon-clock_add">&nbsp;&nbsp;</span></td>
				<td><input type="radio" name="icons"/></td>
			</tr>
			<tr>
				<td><span class="ext-icon-anchor">&nbsp;&nbsp;</span></td>
				<td><input type="radio" name="icons"/></td>
				<td><span class="ext-icon-computer_link">&nbsp;&nbsp;</span></td>
				<td><input type="radio" name="icons"/></td>
				<td><span class="ext-icon-computer_key">&nbsp;&nbsp;</span></td>
				<td><input type="radio" name="icons"/></td>
				<td><span class="ext-icon-computer_go">&nbsp;&nbsp;</span></td>
				<td><input type="radio" name="icons"/></td>
				<td><span class="ext-icon-computer_error">&nbsp;&nbsp;</span></td>
				<td><input type="radio" name="icons"/></td>
			</tr>
			<tr>
				<td><span class="ext-icon-computer_edit">&nbsp;&nbsp;</span></td>
				<td><input type="radio" name="icons"/></td>
				<td><span class="ext-icon-computer_delete">&nbsp;&nbsp;</span></td>
				<td><input type="radio" name="icons"/></td>
				<td><span class="ext-icon-computer_add">&nbsp;&nbsp;</span></td>
				<td><input type="radio" name="icons"/></td>
				<td><span class="ext-icon-html">&nbsp;&nbsp;</span></td>
				<td><input type="radio" name="icons"/></td>
				<td><span class="ext-icon-html_add">&nbsp;&nbsp;</span></td>
				<td><input type="radio" name="icons"/></td>
			</tr>
			<tr>
				<td><span class="ext-icon-html_delete">&nbsp;&nbsp;</span></td>
				<td><input type="radio" name="icons"/></td>
				<td><span class="ext-icon-html_go">&nbsp;&nbsp;</span></td>
				<td><input type="radio" name="icons"/></td>
				<td><span class="ext-icon-html_valid">&nbsp;&nbsp;</span></td>
				<td><input type="radio" name="icons"/></td>
				<td><span class="ext-icon-image">&nbsp;&nbsp;</span></td>
				<td><input type="radio" name="icons"/></td>
				<td><span class="ext-icon-images">&nbsp;&nbsp;</span></td>
				<td><input type="radio" name="icons"/></td>
			</tr>
		</table>
		</form>
	</div>
	</div>
	
</body>
<script type="text/javascript">
	require([ 'menu/menu' ], function() {
		init_menu();
	});
</script>
</html>