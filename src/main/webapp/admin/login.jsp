<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Đăng nhập Quản trị</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
<link href="${pageContext.request.contextPath}/css/login-style.css"
	rel="stylesheet">
</head>
<body>
	<div class="login-container">
		<div class="login-banner">
			<div class="mb-3" style="font-size: 4rem;">
				<i class="bi bi-shield-lock-fill"></i>
			</div>
			<h2 class="banner-title">QUẢN TRỊ VIÊN</h2>
			<p class="banner-text">Hệ thống quản lý Nhà sách Minh Khai</p>
		</div>

		<div class="login-form-wrapper">
			<h3 class="login-title">Đăng nhập</h3>

			<form
				action="${pageContext.request.contextPath}/AdminLoginController" method="post">
				<c:if test="${not empty tb}">
					<div
						class="alert alert-danger text-center py-2 mb-3 small shadow-sm border-0 bg-danger text-white"
						style="--bs-bg-opacity: .8;">
						<i class="bi bi-exclamation-circle-fill me-2"></i> ${tb}
					</div>
				</c:if>

				<div class="mb-3">
					<div class="input-group input-group-lg">
						<span class="input-group-text"><i class="bi bi-person-fill"></i></span>
						<input type="text" name="txtun" class="form-control"
							   placeholder="Username" required>
					</div>
				</div>

				<div class="mb-3">
					<div class="input-group input-group-lg">
						<span class="input-group-text"><i class="bi bi-lock-fill"></i></span>
						<input type="password" name="txtpass" class="form-control"
							   placeholder="Password" required>
					</div>
				</div>

				<c:if test="${showCaptcha}">
					<div class="captcha-box">
						<img src="simpleCaptcha.jpg" alt="Captcha" height="40" /> <input
							type="text" name="captcha"
							class="form-control ms-2 border-success"
							placeholder="Nhập mã xác nhận" required>
					</div>
				</c:if>

				<div class="d-flex justify-content-between mb-4 small">
					<a href="${pageContext.request.contextPath}/AdminDangKyController"
						class="back-link fw-bold">Đăng ký mới</a> <a
						href="${pageContext.request.contextPath}/AdminQuenMatKhauController"
						class="back-link">Quên mật khẩu?</a>
				</div>

				<div class="d-grid gap-2 mb-4">
					<button type="submit" class="btn btn-login"> ĐĂNG NHẬP 
						<i class="bi bi-arrow-right-circle-fill ms-2"></i>
					</button>
				</div>

				<div class="text-center">
					<a href="${pageContext.request.contextPath}/tcController"
						class="back-link"> <i class="bi bi-arrow-left"></i> Về trang chủ bán hàng
					</a>
				</div>
			</form>
		</div>
	</div>
</body>
</html>