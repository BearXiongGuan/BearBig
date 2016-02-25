/**主方法**/
function init_main(){
	loadMenu();
}

/**加载左边菜单**/
function loadMenu(){
	$("#menuTree").tree({
		url:baseUrl+"/main/loadMenuTree!loadMenuTree.do",
		onClick:function(item){
			if(item.attributes.isLeaf == 0){
				return;
			}
			$("#iframe").attr("src",baseUrl+item.attributes.menuUrl+"?title="+item.text+"&icon="+item.iconCls);
		}
	});
}