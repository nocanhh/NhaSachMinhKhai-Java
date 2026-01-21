<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Đăng nhập</title>
    <link href="${pageContext.request.contextPath}/css/style-user.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
</head>
<body>
    <jsp:include page="nav.jsp" />

    <div class="container auth-wrapper">
        <div class="auth-card">
            <h3 class="auth-title">ĐĂNG NHẬP</h3>
            
            <form action="dangnhapController" method="post">
                <c:if test="${not empty tb}">
                    <div class="alert alert-danger text-center small mb-4 border-0 bg-danger text-white" style="--bs-bg-opacity: .8;">
                        ${tb}
                    </div>
                </c:if>

                <div class="mb-3">
                    <label class="form-label fw-bold text-muted small">Tên đăng nhập</label>
                    <input type="text" name="txtun" class="form-control" value="${param.txtun}" required>
                </div>

                <div class="mb-3">
                    <label class="form-label fw-bold text-muted small">Mật khẩu</label>
                    <input type="password" name="txtpass" class="form-control" required>
                </div>

                <c:if test="${showCaptcha}">
                    <div class="mb-3 p-3 bg-light border rounded">
                        <div class="d-flex align-items-center justify-content-between mb-2">
                            <label class="fw-bold text-muted small">Mã xác nhận:</label>
                            <img src="${pageContext.request.contextPath}/simpleCaptcha.jpg" alt="Captcha" height="40" class="border rounded" />
                        </div>
                        <input type="text" name="captcha" class="form-control" placeholder="Nhập mã trên" required>
                    </div>
                </c:if>

                <div class="d-grid gap-2 my-4">
                    <button type="submit" class="btn btn-primary btn-lg fw-bold text-white shadow-sm">
                        ĐĂNG NHẬP
                    </button>
                </div>

                <div class="d-flex justify-content-between text-muted small">
                    <a href="dangkyController" class="text-decoration-none fw-bold" style="color: var(--accent-orange);">Đăng ký ngay</a>
                    <a href="QuenMatKhauController" class="text-secondary">Quên mật khẩu?</a>
                </div>
            </form>
        </div>
    </div>
</body>
</html>