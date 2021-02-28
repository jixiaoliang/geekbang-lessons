<head>
<jsp:directive.include file="/WEB-INF/jsp/prelude/include-head-meta.jspf" />
	<title>My Home Page</title>
    <style>
      .bd-placeholder-img {
        font-size: 1.125rem;
        text-anchor: middle;
        -webkit-user-select: none;
        -moz-user-select: none;
        -ms-user-select: none;
        user-select: none;
      }

      @media (min-width: 768px) {
        .bd-placeholder-img-lg {
          font-size: 3.5rem;
        }
      }
    </style>
</head>
<body>
	<div class="container">
		<form class="form-signin" action="/user/account?methodName=register" method="post">
			<h1 class="h3 mb-3 font-weight-normal">注册</h1>
			<label for="inputEmail" class="sr-only">请输出电子邮件</label>

			<input
					type="text" name="name" id="inputName" class="form-control"
					placeholder="请输入姓名" required autofocus>

			<input
					type="text" name="phoneNumber" id="inputPhoneNumber" class="form-control"
					placeholder="请输入电话号码" required autofocus>

			<input
				type="email" name="email" id="inputEmail" class="form-control"
				placeholder="请输入电子邮件" required autofocus> <label
				for="inputPassword" class="sr-only">Password</label>

			<input
				type="password" name="password" id="inputPassword" class="form-control"
				placeholder="请输入密码" required>

			<button class="btn btn-lg btn-primary btn-block" type="submit">注册</button>
			<p class="mt-5 mb-3 text-muted">&copy; 2017-2021</p>
		</form>
	</div>
</body>