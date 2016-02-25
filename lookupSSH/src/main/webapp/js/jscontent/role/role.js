function init_role(){
	$("#searchBtn").linkbutton();
	$("#clearBtn").linkbutton();
	init_roleGrid();
}

/**初始化datagrid**/
function init_roleGrid(){
	$("#roleGrid").datagrid({
		url:baseUrl+'/role/gridRole!gridRole.do',
		method:"POST",
		striped:true,
		nowrap:true,
		idField:'roleId',
		loadMsg:'加载数据中，请稍后。。。',
		pagination:true,
		rownumbers:true,
		singleSelect:true,
		pageNumber:1,
		pageSize:5,
		pageList:[5,10,15,20],
		remoteSort:true,
		columns:[[    
		          {field:'roleId',hidden:'true',title:'ID',width:100,align:'center'},    
		          {field:'roleName',title:'角色名称',width:100,align:'center'},    
		          {field:'roleDesc',title:'角色描述',width:200,align:'center'},
		          {field:'roleStatus',title:'父菜单编码',width:100,align:'center',formatter:function(value,row){
		        	  switch(value){
		        	  case 0:
		        		  return "有效";
		        		  break;
		        	  case 1:
		        		  return "无效";
		        	  }
		          }}
		      ]],
		onSelect : function(rowIndex, row) {
			$("#roleForm").form('load', {
				"role.roleId" : row.roleId,
				"role.roleName" : row.roleName,
				"role.roleDesc" : row.roleDesc,
				"role.roleStatus" : row.roleStatus
			});
			//加载角色菜单，选择已有的菜单
			checkRoleMenu(row);
		},
		toolbar:[{
			iconCls: 'icon-add',
			text:'添加',
			handler: function(){
				$("#roleForm").form('clear');
				$('#roleWindow').css({"display":"block"});
				$('#roleWindow').dialog({    
					title:'添加角色',
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
								url:baseUrl+'/role/saveOrUpdateRole!saveOrUpdateRole.do',
								dataType:'json',
								cache:false,
								data:$("#roleForm").serializeObj(),
								beforeSend:function(XMLHttpRequest){
									return $("#roleForm").form('validate');
								},
								success:function(data){
									if(data.success){
										$.messager.alert("消息",data.msg);
										$('#roleWindow').dialog("close");
										$("#roleGrid").datagrid("reload");
									}else{
										$.messager.alert("消息",data.msg);
									}
								}
								
							});
						}
					},{
						text:'重置',
						handler:function(){
							$("#roleForm").form("clear");
						}
					}]
				});  
			}
		},'-',{
			iconCls: 'icon-edit',
			text:'编辑',
			handler: function(){
				if($("#roleGrid").datagrid("getSelections").length ==0){
					$.messager.alert("提示","请选择一条记录。");
					return;
				}
				$('#roleWindow').css({"display":"block"});
				$('#roleWindow').dialog({    
					title:'编辑角色',
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
								url:baseUrl+'/role/saveOrUpdateRole!saveOrUpdateRole.do',
								dataType:'json',
								cache:false,
								data:$("#roleForm").serializeObj(),
								beforeSend:function(XMLHttpRequest){
									return $("#roleForm").form('validate');
								},
								success:function(data){
									if(data.success){
										$.messager.alert("消息",data.msg);
										$('#roleWindow').dialog("close");
										$("#roleGrid").datagrid("reload");
									}else{
										$.messager.alert("消息",data.msg);
									}
								}
								
							});
						}
					},{
						text:'重置',
						handler:function(){
							$("#roleForm").form("clear");
						}
					}]
				}); 
			}
		},'-',{
			iconCls: 'icon-no',
			text:'删除',
			handler: function(){
				var row =$("#roleGrid").datagrid("getSelected");
				if(null == row){
					$.messager.alert("提示","请选择一条记录。");
					return;
				}
				var json = {};
				json.roleId = row.roleId;
				$.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
				    if(r){
				    	$.ajax({
							method:"POST",
							async:true,
							url:baseUrl+'/role/deleteRole!deleteRole.do',
							dataType:'json',
							cache:false,
							data:json,
							success:function(data){
								if(data.success){
									$.messager.alert("消息",data.msg);
									$("#roleGrid").datagrid("reload");
									$("#roleGrid").datagrid("unselectAll");
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
	$("#roleGrid").datagrid("options").queryParams = $("#roleQryForm").serializeObj();
	$("#roleGrid").datagrid("options").pageNumber = 1;
	$("#roleGrid").datagrid("reload");
});
/**清空**/
$("#clearBtn").unbind("click").bind("click",function(){
	$("#roleQryForm").form("clear");
	$("#roleGrid").datagrid("unselectAll");
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
	$("#roleMenuPanel").panel({
		title:"角色菜单分配",
		iconCls:'icon-tip',
		fit:true
	});
	$("#roleMenuCheckTree").tree({
		 url: baseUrl+"/roleMenu/treeRoleMenus!treeRoleMenus.do?roleId="+row.roleId,
		 checkbox:true,
		 cascadeCheck:true
	});
	$("#rmSaveBtn").unbind('click').bind('click',function(){
		var nodes = $("#roleMenuCheckTree").tree("getChecked",['checked','indeterminate']);
		var json = {};
		var ids="";
		for(var i=0;i<nodes.length;i++){
			ids+=nodes[i].id+',';
		}
		ids = ids.substring(0,ids.length-1);
		json.ids = ids;
		json.roleId =$("#roleGrid").datagrid("getSelected").roleId;
		$.ajax({
			method:"POST",
			async:true,
			url:baseUrl+'/roleMenu/updateRoleMenus!updateRoleMenus.do',
			dataType:'json',
			cache:false,
			data:json,
			beforeSend:function(XMLHttpRequest){
				return $("#roleForm").form('validate');
			},
			success:function(data){
				if(data.success){
					$.messager.alert("消息",data.msg);
					$('#roleWindow').dialog("close");
					$("#roleGrid").datagrid("reload");
				}else{
					$.messager.alert("消息",data.msg);
				}
			}
			
		});
	});
	$("#rmResetBtn").unbind('click').bind('click',function(){
		$("#roleMenuCheckTree").tree("reload");
	});
}