function init_operator(){
	$("#searchBtn").linkbutton();
	$("#clearBtn").linkbutton();
	$("#editOpRoles").linkbutton();
	init_operatorGrid();
}
var curOpId;
/**初始化datagrid**/
function init_operatorGrid(){
	$("#operatorGrid").datagrid({
		url:baseUrl+'/operator/gridOperator!gridOperator.do',
		method:"POST",
		striped:true,
		nowrap:true,
		idField:'operatorId',
		loadMsg:'加载数据中，请稍后。。。',
		pagination:true,
		rownumbers:true,
		singleSelect:true,
		pageNumber:1,
		pageSize:5,
		pageList:[5,10,15,20],
		remoteSort:true,
		columns:[[    
		          {field:'operatorId',hidden:'true',title:'ID',width:100,align:'center'},    
		          {field:'operatorName',title:'操作员名称',width:100,align:'center'},    
		          {field:'username',title:'用户名',width:200,align:'center'},
		          {field:'age',title:'年龄 ',width:200,align:'center'},
		          {field:'sex',title:'性别',width:200,align:'center',formatter:function(value,row){
					switch (value) {
						case '1':
							return '男';
							break;
						case '2':
							return '女';
					}
		          }},
		          {field:'idcardNo',title:'身份证号码',width:200,align:'center'},
		          {field:'birthday',title:'出生日期',width:200,align:'center',formatter:function(value,row){
		        	  return value.substring(0,10);
		          }},
		          {field:'createTime',title:'创建时间',width:200,align:'center'},
		          {field:'desc',title:'描述',width:200,align:'center'}
		      ]],
		onSelect : function(rowIndex, row) {
			$("#operatorForm").form('load', {
				"operator.operatorId" : row.operatorId,
				"operator.operatorName" : row.operatorName,
				"operator.username" : row.username,
				"operator.password" : row.password,
				"operator.age" : row.age,
				"operator.sex" : row.sex,
				"operator.createTime" : row.createTime,
				"operator.idcardNo" : row.idcardNo,
				"operator.birthday" : row.birthday==""?"": row.birthday.substring(0,10),
				"operator.desc" : row.desc
			});
			curOpId = row.operatorId;
			$("#opRoleDiv").css({"display":"block"});
			//加载已有角色
			var hasRoleLiHtml = "";
			$.ajax({
				method:"POST",
				async:true,
				url:baseUrl+'/role/findOperatorhasRoles!findOperatorhasRoles.do',
				dataType:'json',
				cache:false,
				data:{"operatorId":row.operatorId},
				success:function(data){
					for(var i=0;i<data.length;i++){
						hasRoleLiHtml+="<li id='"+data[i].roleId+"'>"+data[i].roleName+"</li>";
					}
					$("#hasRoles").html(hasRoleLiHtml);
					$("#hasRoles li").unbind('click').bind('click',function(){
					if($(this).attr("class")=='yellowBG'){
						$(this).removeClass('yellowBG');
					}else{
						$(this).addClass('yellowBG');
					}
					});
				}
			});
			
			
			//加载没有的角色
			var notHasRoleLiHtml = "";
			$.ajax({
				method:"POST",
				async:true,
				url:baseUrl+'/role/findOperatorNotHasRoles!findOperatorNotHasRoles.do',
				dataType:'json',
				cache:false,
				data:{"operatorId":row.operatorId},
				success:function(data){
					for(var i=0;i<data.length;i++){
						notHasRoleLiHtml+="<li id='"+data[i].roleId+"'>"+data[i].roleName+"</li>";
					}
					$("#notHasRoles").html(notHasRoleLiHtml);
					$("#notHasRoles li").unbind('click').bind('click',function(){
					if($(this).attr("class")=='yellowBG'){
						$(this).removeClass('yellowBG');
					}else{
						$(this).addClass('yellowBG');
					}
					});
				}
			});
		},
		toolbar:[{
			iconCls: 'icon-add',
			text:'添加',
			handler: function(){
				$("#operatorForm").form('clear');
				$('#operatorWindow').css({"display":"block"});
				$('#operatorWindow').dialog({    
					title:'添加操作员',
				    width:500,    
				    height:400,
				    closed: false, 
				    cache: false, 
				    modal:true,
				    buttons:[{
						text:'保存',
						handler:function(){
							$.ajax({
								method:"POST",
								async:true,
								url:baseUrl+'/operator/saveOrUpdateOperator!saveOrUpdateOperator.do',
								dataType:'json',
								cache:false,
								data:$("#operatorForm").serializeObj(),
								beforeSend:function(XMLHttpRequest){
									return $("#operatorForm").form('validate');
								},
								success:function(data){
									if(data.success){
										$.messager.alert("消息",data.msg);
										$('#operatorWindow').dialog("close");
										$("#operatorGrid").datagrid("reload");
									}else{
										$.messager.alert("消息",data.msg);
									}
								}
								
							});
						}
					},{
						text:'重置',
						handler:function(){
							$("#operatorForm").form("clear");
						}
					}]
				});  
			}
		},'-',{
			iconCls: 'icon-edit',
			text:'编辑',
			handler: function(){
				if($("#operatorGrid").datagrid("getSelections").length ==0){
					$.messager.alert("提示","请选择一条记录。");
					return;
				}
				$('#operatorWindow').css({"display":"block"});
				$('#operatorWindow').dialog({    
					title:'编辑操作员',
				    width:500,    
				    height:400,
				    closed: false, 
				    cache: false, 
				    closed:false,
				    modal:true,
				    buttons:[{
						text:'保存',
						handler:function(){
							$.ajax({
								type:"POST",
								async:true,
								url:baseUrl+'/operator/saveOrUpdateOperator!saveOrUpdateOperator.do',
								dataType:'json',
								cache:false,
								data:$("#operatorForm").serializeObj(),
								beforeSend:function(XMLHttpRequest){
									return $("operatorForm").form('validate');
								},
								success:function(data){
									if(data.success){
										$.messager.alert("消息",data.msg);
										$('#operatorWindow').dialog("close");
										$("#operatorGrid").datagrid("reload");
									}else{
										$.messager.alert("消息",data.msg);
									}
								}
								
							});
						}
					},{
						text:'重置',
						handler:function(){
							$("#operatorForm").form("clear");
						}
					}]
				}); 
			}
		},'-',{
			iconCls: 'icon-no',
			text:'删除',
			handler: function(){
				var row =$("#operatorGrid").datagrid("getSelected");
				if(null == row){
					$.messager.alert("提示","请选择一条记录。");
					return;
				}
				var json = {};
				json.operatorId = row.operatorId;
				$.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
				    if(r){
				    	$.ajax({
							method:"POST",
							async:true,
							url:baseUrl+'/operator/deleteOperator!deleteOperator.do',
							dataType:'json',
							cache:false,
							data:json,
							success:function(data){
								if(data.success){
									$.messager.alert("消息",data.msg);
									$("#operatorGrid").datagrid("reload");
									$("#operatorGrid").datagrid("unselectAll");
								}else{
									$.messager.alert("消息",data.msg);
								}
							}
							
						});
				    }
				});  
				
			}
		}]

	});
}

/**查询**/
$("#searchBtn").unbind("click").bind("click",function(){
	$("#operatorGrid").datagrid("options").queryParams = $("#operatorQryForm").serializeObj();
	$("#operatorGrid").datagrid("options").pageNumber = 1;
	$("#operatorGrid").datagrid("reload");
});
/**清空**/
$("#clearBtn").unbind("click").bind("click",function(){
	$("#operatorQryForm").form("clear");
	$("#operatorGrid").datagrid("unselectAll");
});

/**加载角色对应的菜单**/
function checkRoleMenu(row){
	$("#rmSaveBtn").show().linkbutton({
		iconCls : 'icon-save',
		plain : true
	});
	$("#rmResetBtn").show().linkbutton({
		iconCls : 'icon-clear',
		plain : true
	});
}

$("#allRight").unbind('click').bind('click',function(){
	$("#hasRoles").append($("#notHasRoles li"));
	$("#notHasRoles").empty();
});

$("#right").unbind('click').bind('click',function(){
	$("#notHasRoles li").each(function(id,e){
		if($(e).attr('class') == 'yellowBG'){
			$("#hasRoles").append($(e));
		}
	});
});

$("#allLeft").unbind('click').bind('click',function(){
	$("#notHasRoles").append($("#hasRoles li"));
	$("#hasRoles").empty();
});

$("#left").unbind('click').bind('click',function(){
	$("#hasRoles li").each(function(id,e){
		if($(e).attr('class') == 'yellowBG'){
			$("#notHasRoles").append($(e));
		}
	});
});

$("#editOpRoles").unbind('click').bind('click',function(){
	var ids = "";
	$("#hasRoles li").each(function(id,e){
		ids+=$(e).attr('id')+',';
	});
	ids = ids.substring(0,ids.length-1);
	$.ajax({
		method:"POST",
		async:true,
		url:baseUrl+'/operatorRole/updateOperatorRoles!updateOperatorRoles.do',
		dataType:'json',
		cache:false,
		data:{"ids":ids,"operatorId":curOpId},
		success:function(data){
				$.messager.alert("消息",data.msg);
		}
		
	});
});

