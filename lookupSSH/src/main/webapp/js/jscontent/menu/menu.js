function init_menu(){
	$("#searchBtn").linkbutton();
	$("#clearBtn").linkbutton();
	init_menuGrid();
}

/**初始化datagrid**/
function init_menuGrid(){
	$("#menuGrid").datagrid({
		url:baseUrl+'/menu/gridMenu!gridMenu.do',
		method:"POST",
		striped:true,
		nowrap:true,
		idField:'menuId',
		loadMsg:'加载数据中，请稍后。。。',
		pagination:true,
		rownumbers:true,
		singleSelect:true,
		pageNumber:1,
		pageSize:5,
		pageList:[5,10,15,20],
		remoteSort:true,
		columns:[[    
		          {field:'menuId',hidden:'true',title:'ID',width:100,align:'center'},    
		          {field:'menuNo',title:'编码',width:100,align:'center'},    
		          {field:'menuName',title:'名称',width:150,align:'center'},
		          {field:'pMenuNo',title:'父菜单编码',width:100,align:'center'},
		          {field:'menuUrl',title:'响应路径',width:200,align:'center'},
		          {field:'menuIcon',title:'图标',width:100,align:'center'},
		          {field:'menuOrder',title:'菜单排序',width:100,align:'center'},
		          {field:'isLeaf',title:'是否子菜单',width:100,align:'center'},
		          {field:'createTime',title:'创建时间',width:200,align:'center'}
		      ]],
		onSelect : function(rowIndex, row) {
			$("#menuForm").form('load', {
				"menuNo" : row.menuNo,
				"menuName" : row.menuName,
				"menuType" : row.isLeaf,
				"menuUrl" : row.menuUrl,
				"menuIcon" : row.menuIcon,
				"menuOrder" : row.menuOrder,
				"menuId" : row.menuId
			});
			if(row.isLeaf){
				$("#pm_tr").css({"visibility":"visible"});
				$("#menuUrl").validatebox({required:true});
				$("#menuUrl").attr("disabled",false);
				$("#pMenuNo").combobox({required:true});
				$("#pMenuNo").combobox("setValue",row.pMenuNo);
			}else{
				$("#pm_tr").css({"visibility":"hidden"});
				$("#menuUrl").validatebox({required:false});
				$("#menuUrl").attr("disabled",true);
				$("#pMenuNo").combobox({required:false});
			}
		},
		toolbar:[{
			iconCls: 'icon-add',
			text:'添加',
			handler: function(){
				$("#menuForm").form('clear');
				$('#menuWindow').css({"display":"block"});
				$('#menuWindow').dialog({    
					title:'添加菜单',
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
								url:baseUrl+'/menu/saveOrUpdateMenu!saveOrUpdateMenu.do',
								dataType:'json',
								cache:false,
								data:$("#menuForm").serializeObj(),
								beforeSend:function(XMLHttpRequest){
									return $("#menuForm").form('validate');
								},
								success:function(data){
									if(data.success){
										$.messager.alert("消息",data.msg);
										$('#menuWindow').dialog("close");
										$("#menuGrid").datagrid("reload");
									}else{
										$.messager.alert("消息",data.msg);
									}
								}
								
							});
						}
					},{
						text:'重置',
						handler:function(){
							$("#menuForm").form("clear");
						}
					}]
				});  
			}
		},'-',{
			iconCls: 'icon-edit',
			text:'编辑',
			handler: function(){
				if($("#menuGrid").datagrid("getSelections").length ==0){
					$.messager.alert("提示","请选择一条记录。");
					return;
				}
				$('#menuWindow').css({"display":"block"});
				$('#menuWindow').dialog({    
					title:'编辑菜单',
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
								url:baseUrl+'/menu/saveOrUpdateMenu!saveOrUpdateMenu.do',
								dataType:'json',
								cache:false,
								data:$("#menuForm").serializeObj(),
								beforeSend:function(XMLHttpRequest){
									return $("#menuForm").form('validate');
								},
								success:function(data){
									if(data.success){
										$.messager.alert("消息",data.msg);
										$('#menuWindow').dialog("close");
										$("#menuGrid").datagrid("reload");
									}else{
										$.messager.alert("消息",data.msg);
									}
								}
								
							});
						}
					},{
						text:'重置',
						handler:function(){
							$("#menuForm").form("clear");
						}
					}]
				}); 
			}
		},'-',{
			iconCls: 'icon-no',
			text:'删除',
			handler: function(){
				var row =$("#menuGrid").datagrid("getSelected");
				if(null == row){
					$.messager.alert("提示","请选择一条记录。");
					return;
				}
				var json = {};
				json.menuId = row.menuId;
				json.pMenuNo = row.pMenuNo;
				$.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
				    if(r){
				    	$.ajax({
							method:"POST",
							async:true,
							url:baseUrl+'/menu/deleteMenu!deleteMenu.do',
							dataType:'json',
							cache:false,
							data:json,
							success:function(data){
								if(data.success){
									$.messager.alert("消息",data.msg);
									$("#menuGrid").datagrid("reload");
									$("#menuGrid").datagrid("unselectAll");
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
	$("#menuGrid").datagrid("options").queryParams = $("#menuQryForm").serializeObj();
	$("#menuGrid").datagrid("options").pageNumber = 1;
	$("#menuGrid").datagrid("reload");
});
/**清空**/
$("#clearBtn").unbind("click").bind("click",function(){
	$("#menuQryForm").form("clear");
	$("#menuGrid").datagrid("unselectAll");
});

/**选择菜单图片**/
$("#mIcon").unbind("click").bind("click",function(){
	$('#iconDiv').css({"display":"block"});
	$('#iconDiv').dialog({    
	    title: '图标选择',
	    width: 480,    
	    height: 400,    
	    closed: false, 
	    cache: false,    
	    modal: true,
	    buttons:[{
			text:'确定',
			handler:function(){
				$('#iconDiv').dialog("close");
			}
		}]
	});   
});

$("input[name='icons']").unbind("click").bind("click",function(){
	$("input[name='menuIcon']").val($(this).parent().prev().children().attr("class"));
});

//初始化输入框
$("#menuType").combobox({
	valueField : 'id',
	textField : 'text',
	required:true,
	editable:false,
	data : [ {
		'id' : 0,
		'text' : '主菜单'
	}, {
		'id' : 1,
		'text' : '子菜单'
	} ],
	onSelect:function(rec){
		switch (rec.id) {
		case 1:
			$("#pm_tr").css({"visibility":"visible"});
			$("#menuUrl").validatebox({required:true});
			$("#menuUrl").attr("disabled",false);
			$("#pMenuNo").combobox({required:true});
			break;
		case 0:
			$("#pm_tr").css({"visibility":"hidden"});
			$("#menuUrl").validatebox({required:false});
			$("#menuUrl").attr("disabled",true);
			$("#pMenuNo").combobox({required:false});
		}
	}
});

$("#pMenuNo").combobox({
	valueField : 'id',
	textField : 'text',
	editable:false,
	required:true,
	url:baseUrl+"/menu/loadParentMenusCombobox!loadParentMenusCombobox.do"
});
