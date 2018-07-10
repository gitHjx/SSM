<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Spring-SpringMVC-Mybatic-Maven-Shiro框架整合</title>

</head>
<body>
	<h1>Spring-SpringMVC-Mybatic-Maven-Shiro框架整合-密码加密</h1>
	<h2>login</h2>
	<form id="form "action="user/login">
		<input id="username" type="text" name="username">
		</br>
		</br>
		<input id="password" type="password" name="password">
		</br>
		</br>
		<input type="button" value="登录" onclick="login()">
		<span>体验账号：sally 密码：123456</span>
	</form>


<script type="text/javascript">


function getXMLHttpRequest() {  
    var xhr;  
    if(window.ActiveXObject) {  
             xhr= new ActiveXObject("Microsoft.XMLHTTP");  
    }else if (window.XMLHttpRequest) {  
             xhr= new XMLHttpRequest();  
    }else {  
             xhr= null;  
    }  
    return xhr;  
}  

function login() {  
	var username = document.getElementById("username").value;
	var password = document.getElementById("password").value;
    var xhr = getXMLHttpRequest(); 
	var url="user/login?username="+username+"&password="+password;
    xhr.open("get",url,true);  
    xhr.onreadystatechange= function() {  
             if(xhr.readyState == 4 && xhr.status == 200) {  
                     // alert("returned:"+ xhr.responseText);  
                      var result = JSON.parse(xhr.responseText);
                      if(result.state==1){
                    	  window.location.href="/SSM/user/success";
                      }else{
                    	  alert(result.message)
                      }
             }  
    };  
    xhr.send(null);  
}




	
	


</script>
</body>
</html>