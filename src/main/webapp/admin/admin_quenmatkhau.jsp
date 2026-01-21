<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Khôi phục mật khẩu Admin</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
    <link href="${pageContext.request.contextPath}/css/login-style.css" rel="stylesheet">
</head>
<body>
    <div class="login-container forgot-container">
        <div class="text-center mb-4">
            <div class="text-success mb-2" style="font-size: 3rem;"><i class="bi bi-key-fill"></i></div>
            <h3 class="fw-bold text-dark">QUÊN MẬT KHẨU?</h3>
            <p class="text-muted small">Nhập email đăng ký để nhận mật khẩu mới.</p>
        </div>

        <form action="${pageContext.request.contextPath}/AdminQuenMatKhauController" method="post">
            <c:if test="${not empty tb}">
                <div class="alert alert-danger text-center small">${tb}</div>
            </c:if>

            <div class="mb-4">
                <div class="input-group input-group-lg">
                    <span class="input-group-text"><i class="bi bi-envelope-fill"></i></span>
                    <input type="email" name="email" class="form-control" placeholder="Nhập địa chỉ Email..." required>
                </div>
            </div>

            <div class="d-grid gap-2 mb-3">
                <button type="submit" class="btn btn-login">GỬI MẬT KHẨU MỚI</button>
            </div>

            <div class="text-center">
                <a href="AdminLoginController" class="back-link">
                    <i class="bi bi-arrow-left"></i> Quay lại Đăng nhập
                </a>
            </div>
        </form>
    </div>
</body>
</html>