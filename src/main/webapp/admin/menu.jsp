<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="card sidebar-card shadow-sm h-100">
    <div class="card-header text-center border-bottom py-3" style="background-color: white;">
        <strong style="color: #20B970; font-size: 1.1rem;">
            <i class="bi bi-grid-fill"></i> DANH MỤC QUẢN LÝ
        </strong>
    </div>
    <div class="list-group list-group-flush">
        <a href="${pageContext.request.contextPath}/AdminHomeController" class="list-group-item list-group-item-action">
            <i ></i>📊 Thống kê chung
        </a>
        <a href="${pageContext.request.contextPath}/SachAdminController" class="list-group-item list-group-item-action">
            <i></i>📚 Quản lý Sách
        </a>
        <a href="${pageContext.request.contextPath}/LoaiAdminController" class="list-group-item list-group-item-action">
            <i ></i>🏷️ Quản lý Loại
        </a>
        <a href="${pageContext.request.contextPath}/QuanLyDonHangController" class="list-group-item list-group-item-action">
            <i ></i>📦 Tất cả Đơn hàng
        </a>
        <a href="${pageContext.request.contextPath}/XacNhanController" class="list-group-item list-group-item-action">
            <i></i>✅ Xác nhận Đơn mới
        </a>
        <a href="${pageContext.request.contextPath}/HoaDonAdminController?action=dathanhtoan" class="list-group-item list-group-item-action">
            <i></i>💰 Hóa đơn Đã Thanh toán
        </a>
    </div>
</div>