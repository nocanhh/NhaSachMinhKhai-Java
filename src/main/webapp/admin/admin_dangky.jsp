<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Đăng ký Quản trị viên</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
    <link href="${pageContext.request.contextPath}/css/login-style.css" rel="stylesheet">
</head>
<body>
    <div class="login-container register-container">
        <div class="login-banner">
            <div class="mb-3" style="font-size: 4rem;"><i class="bi bi-person-plus-fill"></i></div>
            <h2 class="banner-title">GIA NHẬP ĐỘI NGŨ</h2>
            <p class="banner-text">Đăng ký tài khoản Quản trị viên<br>Nhà sách Minh Khai</p>
        </div>

        <div class="login-form-wrapper">
            <h3 class="login-title">Tạo tài khoản mới</h3>
            
            <form action="${pageContext.request.contextPath}/AdminDangKyController" method="post">
                <c:if test="${not empty tb}">
                    <div class="alert alert-danger text-center py-2 mb-3 small shadow-sm border-0 bg-danger text-white" style="--bs-bg-opacity: .8;">${tb}</div>
                </c:if>

                <div class="mb-3">
                    <div class="input-group">
                        <span class="input-group-text"><i class="bi bi-person-vcard"></i></span>
                        <input type="text" name="hoten" class="form-control" placeholder="Họ và tên (*)" value="${param.hoten}" required>
                    </div>
                </div>

                <div class="mb-3">
                    <div class="input-group">
                        <span class="input-group-text"><i class="bi bi-envelope"></i></span>
                        <input type="email" name="email" class="form-control" placeholder="Email (*)" value="${param.email}" required>
                    </div>
                </div>

                <div class="mb-3">
                    <div class="input-group">
                        <span class="input-group-text"><i class="bi bi-person"></i></span>
                        <input type="text" name="tendn" class="form-control" placeholder="Tên đăng nhập (*)" value="${param.tendn}" required>
                    </div>
                </div>

                <div class="row mb-3">
                    <div class="col-6">
                        <div class="input-group">
                            <span class="input-group-text"><i class="bi bi-lock"></i></span>
                            <input type="password" name="pass" class="form-control" placeholder="Mật khẩu" required>
                        </div>
                    </div>
                    <div class="col-6">
                        <div class="input-group">
                            <span class="input-group-text"><i class="bi bi-check-lg"></i></span>
                            <input type="password" name="repass" class="form-control" placeholder="Nhập lại" required>
                        </div>
                    </div>
                </div>

                <div class="mb-4 text-muted small fst-italic text-center">
                    * Tài khoản mới sẽ cần chờ phê duyệt hoặc có quyền hạn mặc định.
                </div>

                <div class="d-grid gap-2 mb-3">
                    <button type="submit" class="btn btn-login">ĐĂNG KÝ NGAY</button>
                </div>

                <div class="text-center">
                    <a href="AdminLoginController" class="back-link">Đã có tài khoản? Đăng nhập</a>
                </div>
            </form>
        </div>
    </div>
</body>
</html>