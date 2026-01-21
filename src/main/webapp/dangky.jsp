<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Đăng ký tài khoản</title>
    <link href="${pageContext.request.contextPath}/css/style-user.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
</head>
<body>
    <jsp:include page="nav.jsp" />

    <div class="container auth-wrapper">
        <div class="auth-card" style="max-width: 600px;">
            <h3 class="auth-title">ĐĂNG KÝ THÀNH VIÊN</h3>
            
            <form action="dangkyController" method="post">
                <c:if test="${not empty tb}">
                    <div class="alert alert-danger text-center small mb-3 border-0 bg-danger text-white" style="--bs-bg-opacity: .8;">${tb}</div>
                </c:if>
            
                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label class="form-label small fw-bold text-muted">Họ tên (*)</label>
                        <input type="text" class="form-control" name="hoten" value="${param.hoten}" required>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label class="form-label small fw-bold text-muted">Số điện thoại (*)</label>
                        <input type="text" class="form-control" name="sodt" value="${param.sodt}" required>
                    </div>
                </div>

                <div class="mb-3">
                    <label class="form-label small fw-bold text-muted">Email (*)</label>
                    <input type="email" class="form-control" name="email" value="${param.email}" required>
                </div>
                
                <div class="mb-3">
                    <label class="form-label small fw-bold text-muted">Địa chỉ (*)</label>
                    <input type="text" class="form-control" name="diachi" value="${param.diachi}" required>
                </div>

                <hr class="my-4 text-muted">

                <div class="mb-3">
                    <label class="form-label small fw-bold text-muted">Tên đăng nhập (*)</label>
                    <input type="text" class="form-control" name="tendn" value="${param.tendn}" required>
                </div>

                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label class="form-label small fw-bold text-muted">Mật khẩu (*)</label>
                        <input type="password" class="form-control" name="pass" required>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label class="form-label small fw-bold text-muted">Nhập lại (*)</label>
                        <input type="password" class="form-control" name="repass" required>
                    </div>
                </div>
                
                <div class="d-grid mt-4">
                    <button type="submit" class="btn btn-orange btn-lg fw-bold shadow-sm">ĐĂNG KÝ NGAY</button>
                </div>
                
                <div class="text-center mt-3 small">
                    Đã có tài khoản? <a href="dangnhapController" class="fw-bold" style="color: var(--main-green);">Đăng nhập</a>
                </div>
            </form>
        </div>
    </div>
</body>
</html>