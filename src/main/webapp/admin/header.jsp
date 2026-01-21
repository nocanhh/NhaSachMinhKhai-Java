<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<nav class="navbar navbar-expand-lg navbar-dark navbar-custom mb-4 sticky-top">
    <div class="container-fluid">
        <a class="navbar-brand" href="AdminHomeController">
            <i class="bi bi-book-fill"></i> QUẢN TRỊ NHÀ SÁCH MINH KHAI
        </a>
        <div class="user-info d-flex align-items-center">
            <span class="me-3">Xin chào, <strong>${sessionScope.admin.tenDangNhap}</strong></span>
            <a href="${pageContext.request.contextPath}/AdminLogoutController" class="btn btn-sm btn-outline-light fw-bold">Đăng xuất</a>
        </div>
    </div>
</nav>