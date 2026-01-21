<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Navbar</title>
    <link href="${pageContext.request.contextPath}/css/style-user.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-custom sticky-top">
        <div class="container">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/tcController">
                <i class="bi bi-book-half"></i> MINH KHAI BOOKS
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/tcController">Trang chủ</a></li>
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/giohangController">Giỏ hàng</a></li>
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/lichsumuahangController">Lịch sử</a></li>
                </ul>

                <form class="d-flex me-4" action="${pageContext.request.contextPath}/tcController" method="get">
                    <div class="input-group">
                        <input class="form-control search-input ps-3" type="search" name="key" placeholder="Tìm sách..." value="${param.key}">
                        <button class="btn search-btn px-3" type="submit"><i class="bi bi-search"></i></button>
                    </div>
                </form>

                <ul class="navbar-nav d-flex align-items-center">
                    <c:choose>
                        <c:when test="${not empty sessionScope.un}">
                            <li class="nav-item d-flex align-items-center bg-white rounded-pill px-3 py-1 shadow-sm">
                                <span class="text-success fw-bold me-2">
                                    <i class="bi bi-person-circle"></i> ${sessionScope.un.hoten}
                                </span>
                                <span class="text-muted">|</span>
                                <a class="btn btn-sm text-danger fw-bold ms-2" href="${pageContext.request.contextPath}/dangxuatController">
                                    <i class="bi bi-box-arrow-right"></i> Đăng xuất
                                </a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/dangnhapController">Đăng nhập</a></li>
                            <li class="nav-item ms-2">
                                <a class="btn btn-sm btn-light text-success fw-bold rounded-pill px-3" href="${pageContext.request.contextPath}/dangkyController">Đăng ký</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </div>
        </div>
    </nav>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>