<head>
<jsp:directive.include
	file="/WEB-INF/jsp/prelude/include-head-meta.jspf" />
<title>My Home Page</title>
</head>
<body>
	<div class="container-lg">
		<!-- Content here -->

		<br>
		<H1>你好:<font color="red">${name}</font></H1>
		<H1>当前应用名:<font color="red">${applicationName}</font></H1>
		<br>
		<a href="/user/account?methodName=registerPage">前去注册</a>
	</div>
</body>