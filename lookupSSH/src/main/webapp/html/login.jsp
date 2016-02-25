<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录</title>
</head>
<jsp:include page="common.jsp"></jsp:include>
<body style="width: 100%; height: 400px;background:url('${baseUrl}/images/bg.jpg') no-repeat;background-size:100% 100%">
	<div>
		<form id="loginForm" method="post">
			<table border="0" cellspacing="0" cellpadding="0"
				style="width:450px;height: 200px;margin:200px auto">
				<tr>
					<td align="right" width="150px;" style="font-size:18px">用户名：</td>
					<td><input class="easyui-validatebox" type="text"
						name="username" data-options="required:true" style="width:200px;"/></td>
				</tr>
				<tr>
					<td align="right" style="font-size:18px">密码：</td>
					<td><input class="easyui-validatebox" type="password"
						name="password" data-options="required:true" style="width:200px;"/></td>
				</tr>
				<tr>
					<td colspan="2" align="center"><a id="loginSub"
						class="easyui-linkbutton" data-options="iconCls:'icon-ok'">登录</a>&nbsp;&nbsp;&nbsp;&nbsp;
						<a id="loginReset" class="easyui-linkbutton"
						data-options="iconCls:'icon-cancel'">取消</a></td>
				</tr>
			</table>
		</form>
	</div>
</body>
<script type="text/javascript">
	$("#loginSub").linkbutton();
	$("#loginReset").linkbutton();
	$("#loginSub").unbind('click').bind('click',function(){
		$("#loginForm").form('submit',{
			url:"${baseUrl}/main/login!login.do",
			onSubmit:function(){
				if(!$("input[name='username']").val()){
					$.messager.alert("提示","用户名不能为空!","warning");
					return false;
				}
				if(!$("input[name='password']").val()){
					$.messager.alert("提示","密码不能为空!","warning");
					return false;
				}
				return true;
			},
			success:function(data){
				var jsonData = JSON.parse(data);
				if(jsonData.success){
					location.href="${baseUrl}/html/main.jsp";
				}else{
					$.messager.alert("提示",jsonData.msg,"info");
				}
			}
		});
	});
	$("#loginReset").unbind('click').bind('click',function(){
		$("#loginForm").form('reset');
	});
</script>
</html>