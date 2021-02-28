<head>
<jsp:directive.include
	file="/WEB-INF/jsp/prelude/include-head-meta.jspf" />
<title>My Home Page</title>
</head>
<body>
	<div class="container-lg">
		<!-- Content here -->
		Hello,World 2021
		<br>
		<H1>你好:<font color="red">${name}</font></H1>
		<br>
		<a href="/user/account?methodName=registerPage">前去注册</a>
	</div>
</body>