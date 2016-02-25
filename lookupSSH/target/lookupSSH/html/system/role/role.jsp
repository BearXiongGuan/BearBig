<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>角色管理</title>
<jsp:include page="${baseUrl }/html/common.jsp"></jsp:include>
</head>
<body class="easyui-layout" data-options="fit:true">
	<div data-options="region:'north',title:'<%=request.getParameter("title") %>',iconCls:'<%=request.getParameter("icon") %>'"
		style="height: 80px">
		<form id="roleQryForm" method="post">
			<table border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td>角色名称：</td>
					<td><input type="text" class="easyui-validatebox"  id="roleName" name="roleName"/></td>
					<td colspan="2"><a id="searchBtn" class="easyui-linkbutton"
						data-options="iconCls:'icon-search',plain:true">查询</a><a id="clearBtn"
						class="easyui-linkbutton" data-options="iconCls:'icon-no',plain:true">重置</a></td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center'">
		<table id="roleGrid" data-options="fit:true,border:false"></table>
		<div id="roleWindow" style="display: none">
			<form id="roleForm" method="post">
				<input type="hidden" name="role.roleId"/>
				<input type="hidden" name="role.roleStatus" value="0"/>
				<table border="0" cellpadding="0" cellspacing="0"
					style="margin: 50px auto;">
					<tr>
						<td>角色名称</td>
						<td><input type="text" class="easyui-validatebox" id="role.roleName"
							name="role.roleName" data-options="required:true" /></td>
					</tr>
					<tr>
						<td>角色描述</td>
						<td><input type="text" class="easyui-validatebox"
							id="role.roleDesc" name="role.roleDesc"
							data-options="required:true" /> <!-- <textarea rows="3" cols="5" class="easyui-validatebox"
								id="role.roleDesc" name="role.roleDesc"
								data-options="required:true"></textarea> --></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<div data-options="region:'south',split:true" style="height: 200px;">
		<div data-options="region:'west'" style="width: 300px;height:180px">
			<div id="roleMenuPanel">
				<dl>
					<dd><div id="roleMenuCheckTree" style="padding:2px"></div></dd>
					<dd><a id="rmSaveBtn" style="display:none" class="easyui-linkbutton">保存</a>|<a id="rmResetBtn" style="display:none" class="easyui-linkbutton">重置</a></dd>
				</dl>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	require([ 'role/role' ], function() {
		init_role();
	});
</script>
</html>