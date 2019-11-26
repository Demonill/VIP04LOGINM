/**
 * 通过Ajax实现异步登录请求
 */

//编写了一个叫做LOgin的方法
function AjaxLogin(){
	$.ajax({
		//表征ajax请求的资源地址，这里使用到的是基于ajaxLogin.js所在位置的相对路径
		url:"./Login",
		//表示ajax方法所使用的http请求方法
		type:"post",
		//接收的返回的格式
		dataType:"json",
		//请求的参数
		data:$("#loginForm").serialize(),
		//当请求成功时，执行的方法，result参数，就是ajax完成请求之后，得到的返回
		success: function(result){
				$("#Msg")[0].innerText=result["msg"];
				//如果返回信息是恭喜您post方法登录成功！，那么跳转到user界面，否则不做任何操作
				if(result["msg"]=="恭喜您post方法登录成功！"){
					window.location.href="user.html";
				}
		},
		//当请求失败时，执行的方法。
		error:function(result){
			document.getElementById("Msg").innerText="Ajax请求出错，请检查";
		}
		
		
	})
	
}


/**
 * getUser函数，通过user.html中body的onload事件响应异步提交
 * 在访问user.html时就调用Userinfo接口获取用户信息并且写到对应的元素中。
 */
function getUser() {
	// 定义一个存放url的变量，指定请求的接口的地址
	var AjaxURL = "./GetUserInfo";
	$.ajax({
				// 方法用post
				type : "post",
				// 返回和请求的参数类型传递方式。
				dataType : "json",
				// 请求的接口地址
				url : AjaxURL,
				// 接口执行的返回，当接口调用成功时，执行success中的方法
				success : function(result) {
					// 将返回结果解析出来的对应内容填写到对应的元素中
					document.getElementById("userid").innerHTML = result["id"];
					document.getElementById("nickname").innerHTML = result["nickname"];
					document.getElementById("describe").innerHTML = result["describe"];
				},
				// 接口调用出错时，执行该方法
				error : function() {
					alert("调用UserInfo接口出错，请检查。");
				}
			});
}
/**
 * logout()方法在user.html中的注销按钮通过onclick事件响应 调用Logout接口，完成注销的操作。
 */
function logout() {
	// 定义一个存放url的变量，指定请求的接口的地址
	var AjaxURL = "./Logout";
	$.ajax({
		// 方法用post
		type : "post",
		// 返回和请求的参数类型传递方式。
		dataType : "json",
		// 请求的接口地址
		url : AjaxURL,
		// 接口执行的返回，当接口调用成功时，执行success中的方法
		success : function(result) {
			alert(result["msg"]);
			window.location.href = "index.html";
		},
		// 接口调用出错时，执行该方法
		error : function() {
			alert("调用Logout接口出错，请检查。");
		}
	});
}