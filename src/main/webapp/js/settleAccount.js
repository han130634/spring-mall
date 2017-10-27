(function(w,d,u){
	var getXmlHttpRequest = function() {
		if (window.XMLHttpRequest) {
			//主流浏览器提供了XMLHttpRequest对象
			return new XMLHttpRequest();
		} else if (window.ActiveXObject) {
			//低版本的IE浏览器没有提供XMLHttpRequest对象
			//所以必须使用IE浏览器的特定实现ActiveXObject
			return new ActiveXObject("Microsoft.XMLHttpRequest");
		}
	};
	var settleAccount = util.get('settleAccount');
	if(!settleAccount){
		return;
	}
	var name = 'products';
	var products = util.getCookie(name);
	var $ = function(id){
		return document.getElementById(id);
	}
	if (products && products != null) {
		var str = "<tr>" + 
		  "<th>" + '内容名称'  + "</th>"+ 
		  "<th>" + '数量' + "</th>" +
		  "<th>" + '价格' + "</th>" +
		  "</tr>";

		for(var i = 0; i < products.length; i++){
			str = str + 
			"<tr>" + 
			"<td>" + products[i].title  + "</td>"+
			"<td>" + 
			"<span class=\"lessNum\">"+ "-" + "</span>" +
			"<span class=\"totalNum\" id=\"allNum\">" + products[i].num + "</span>" +
			"<span id=\"thisId\">" + products[i].id + "</span>" +
			"<span class=\"moreNum\">"+ "+" + "</span>" + "</td>" +
			"<td>" + products[i].price + "</td>" +
			"</tr>";
		}
		
		$("newTable").innerHTML = str;
	}
	

	window.onload = function(){
		$('newTable').onclick = function(e){
			var e = arguments[0] || window.event;
			target = e.srcElement ? e.srcElement : e.target;
			if(target.nodeName == "SPAN" && target.className == "moreNum"){
				var num = target.parentElement.children[1].textContent;
				var id = target.parentElement.children[2].textContent;
				num ++;
				target.parentElement.children[1].textContent = num;
				util.modifyOne(products,id,num);
			}else if(target.nodeName == "SPAN" && target.className == "lessNum"){
				var num = target.parentElement.children[1].textContent;
				var id = target.parentElement.children[2].textContent;
				num --;
				if(num < 0){
					alert("该商品数量为0");
				}else{
					target.parentElement.children[1].textContent = num;
					util.modifyOne(products,id,num);
				}
			}
			return false;
		};
	};

	var loading = new Loading();
	var layer = new Layer();
	
	$('Account').onclick = function(e){
		if(!products || products == null){
			alert("没有可购买商品");
			return false;
		}
		var newProducts = products.map(function(arr){
			return {'id':arr.id,'number':arr.num};
		});
		console.log(newProducts);
		var ele = e.target;
			layer.reset({
				content:'确认购买吗？',
				onconfirm:function(){
					layer.hide();
					loading.show();
					
					//var xhr = new XMLHttpRequest();
					var xhr =getXmlHttpRequest();
					var data = JSON.stringify(newProducts);
					xhr.onreadystatechange = function(){
						 if(xhr.readyState == 4){
				                var status = xhr.status;
				                if(status >= 200 && status < 300 || status == 304){
				                	var json = JSON.parse(xhr.responseText);
				                	if(json && json.code == 200){
				                		loading.result('购买成功',function(){location.href = baseUrl + '/account';});
				                		util.deleteCookie(name);
				                	}else{
				                		alert(json.message);
				                		window.location.reload();
				                	}
				                }else{
				                	loading.result(message||'购买失败');
				                }
				            }
					};
					xhr.open('post',baseUrl + '/api/buy');
					xhr.setRequestHeader('Content-Type','application/json');
					xhr.setRequestHeader('x-requested-with','XMLHttpRequest');
					xhr.send(data);
				}.bind(this)
			}).show();
			return;
	};
	$('back').onclick = function(){
		window.history.go(-1);
		//location.href = window.history.back();
	}
})(window,document);