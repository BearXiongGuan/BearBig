<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>操作员管理</title>
<jsp:include page="${baseUrl }/html/common.jsp"></jsp:include>
<style type="text/css">
	dd dl{
		font-size:15px;
		font-weight:bold;
		cursor:pointer;
	}
	ul{
		list-style:none;
		list-style-type:none;
		margin:0px;
		padding:0px;
		text-align:center;
	}
	ul li{
		list-style:none;
		list-style-type:none;
		margin:0px;
		padding:0px;
		cursor:pointer;
		font-size:15px;
	}
	ul li:hover{
		background:#FBEC88
	}
	.yellowBG{
		background:#FBEC88
	}
	
</style>
</head>
<body class="easyui-layout" data-options="fit:true">
	<div data-options="region:'north',title:'<%=request.getParameter("title") %>',iconCls:'<%=request.getParameter("icon") %>'"
		style="height: 80px">
		<form id="operatorQryForm" method="post">
			<table border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td>操作员名称：</td>
					<td><input type="text" class="easyui-validatebox"  id="operatorName" name="operatorName"/></td>
					<td>用户名：</td>
					<td><input type="text" class="easyui-validatebox"  id="username" name="username"/></td>
					<td colspan="2"><a id="searchBtn" class="easyui-linkbutton"
						data-options="iconCls:'icon-search',plain:true">查询</a><a id="clearBtn"
						class="easyui-linkbutton" data-options="iconCls:'icon-no',plain:true">重置</a></td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center'">
		<table id="operatorGrid" data-options="fit:true,border:false"></table>
		<div id="operatorWindow" style="display: none">
			<form id="operatorForm" method="post">
				<input type="hidden" name="operator.operatorId"/>
				<input type="hidden" name="operator.status" value="0"/>
				<input type="hidden" id="operator.age" name="operator.age"/>
				<input type="hidden" id="operator.createTime" name="operator.createTime"/>
				<table border="0" cellpadding="0" cellspacing="0"
					style="margin: 50px auto;">
					<tr>
						<td>操作员名称</td>
						<td><input type="text" class="easyui-validatebox"
							id="operator.operatorName" name="operator.operatorName"
							data-options="required:true" /></td>
						<td>用户名</td>
						<td><input type="text" class="easyui-validatebox"
							id="operator.username" name="operator.username"
							data-options="required:true" /></td>
					</tr>
					<tr>
						<td>密码</td>
						<td><input type="text" class="easyui-validatebox"
							id="operator.password" name="operator.password"
							data-options="required:true" /></td>
						<td>性别</td>
						<td><input type="text" class="easyui-combobox" id="operator.sex" name="operator.sex"
							data-options="required:true,valueField : 'id',textField : 'text',data : [ {id : '1',text : '男'}, {id : '2',text : '女'} ]" /></td>
					</tr>
					<tr>
					
						<td>身份证</td>
						<td><input type="text" class="easyui-validatebox"
							id="operator.idcardNo" name="operator.idcardNo"
							data-options="required:true" /></td>
							<td>出生日期</td>
						<td><input type="text" class="easyui-datebox"
							id="operator.birthday" name="operator.birthday"
							data-options="required:true,editable:false,onSelect:function(date){
									var myDate = new Date();
									if(date>myDate){
										$.messager.alert('提示','出生日期不能大于当前日期');
										return;
									}
									$('#operator\\.age').val(myDate.getFullYear()-date.getFullYear());
							}" />
						</td>
					</tr>
					<tr>
						<td>描述</td>
						<td><input type="text" class="easyui-validatebox"
							id="operator.desc" name="operator.desc"
							data-options="required:true" />
					</tr>
				</table>
			</form>
		</div>
	</div>
	<div data-options="region:'south',fit:true" style="height:240px">
		<div id="opRoleDiv" style="display:none">
			<table cellpadding="0" cellspacing="0" border="0" style="width:300px;border:1px solid #95B8E7">
			<tr style="background:#049404">
				<td style="text-align:center;font-color:white;font-size:15px;">未占用角色</td>
				<td></td>
				<td style="text-align:center;font-color:white;font-size:15px;">占用角色</td>
			</tr>
			<tr>
				<td align="center" style="width:200px;">
					<ul id="notHasRoles"></ul>
				</td>
				<td style="width:30px;border:1px solid #95B8E7">
					<dd style="width:10px;margin:0 auto">
						<dl id='allRight'>&gt;&gt;</dl>
						<dl id='right'>&gt;</dl>
						<dl id='left'>&lt;</dl>
						<dl id='allLeft'>&lt;&lt</dl>
					</dd>
				</td>
				<td align="center" style="width:200px;">
					<ul id="hasRoles"></ul>
				</td>
			</tr>
			<tr style="background:#049404">
				<td colspan="3" style="text-align:center">
					<a id="editOpRoles" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true">保存</a>    
				</td>
			</tr>
		</table>
		</div>
	</div>
</body>
<script type="text/javascript">
	require([ 'operator/operator' ], function() {
		init_operator();
	});
</script>
</html>