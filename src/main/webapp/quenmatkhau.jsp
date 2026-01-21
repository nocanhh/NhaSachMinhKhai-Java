<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quên mật khẩu</title>
    <link href="${pageContext.request.contextPath}/css/style-user.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
</head>
<body>
    <jsp:include page="nav.jsp" />

    <div class="container auth-wrapper">
        <div class="auth-card">
            <div class="text-center mb-4" style="color: var(--main-green); font-size: 3rem;">
                <i class="bi bi-key-fill"></i>
            </div>
            
            <h3 class="auth-title mb-2">KHÔI PHỤC MẬT KHẨU</h3>
            <p class="text-center text-muted small mb-4">Nhập email đã đăng ký để nhận mật khẩu mới.</p>
            
            <form action="QuenMatKhauController" method="post">
                <c:if test="${not empty tb}">
                    <div class="alert alert-warning text-center small mb-3 border-0 shadow-sm">${tb}</div>
                </c:if>
            
                <div class="mb-4">
                    <label class="form-label fw-bold text-muted small">Email của bạn</label>
                    <input type="email" name="email" class="form-control" placeholder="vidu@gmail.com" required>
                </div>
                
                <div class="d-grid gap-2">
                    <button type="submit" class="btn btn-primary btn-lg fw-bold text-white shadow-sm">GỬI MẬT KHẨU</button>
                    <a href="dangnhapController" class="btn btn-light text-secondary mt-2">Quay lại đăng nhập</a>
                </div>
            </form>
        </div>
    </div>
</body>
</html>