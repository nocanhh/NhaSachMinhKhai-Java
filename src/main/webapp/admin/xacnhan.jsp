<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
    <title>Xác nhận Đơn hàng</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
    <link href="${pageContext.request.contextPath}/css/admin-style.css" rel="stylesheet">
</head>
<body>
    <jsp:include page="header.jsp" />

	<div class="container-fluid">
		<div class="row">
			<div class="col-md-2"><jsp:include page="menu.jsp" /></div>

			<div class="col-md-10">
                <div class="d-flex justify-content-between align-items-center mb-4">
				    <h4 class="section-header mb-0">QUẢN LÝ ĐƠN HÀNG MỚI</h4>
                    
                    <a href="XacNhanController?action=xoaton" 
                       class="btn btn-warning shadow-sm fw-bold text-white"
                       onclick="return confirm('CẢNH BÁO: Hệ thống sẽ xóa vĩnh viễn tất cả các đơn hàng chưa thanh toán quá 15 ngày.\nBạn có chắc chắn muốn thực hiện?')">
                       <i class="bi bi-trash3-fill"></i> Quét đơn tồn (>15 ngày)
                    </a>
                </div>

				<c:if test="${not empty tb}">
					<div class="alert alert-success alert-dismissible fade show shadow-sm">
						<i class="bi bi-check-circle-fill"></i> ${tb} 
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
					</div>
				</c:if>

				<div class="table-container shadow">
					<h5 class="mb-3 text-success fw-bold">Danh sách chờ xác nhận</h5>
					<table class="table table-hover text-center align-middle mb-0">
						<thead>
							<tr>
								<th>Mã HĐ</th>
								<th>Khách hàng</th>
								<th>SL</th>
								<th>Thành tiền</th>
								<th>Thanh toán</th>
								<th>Thao tác</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="dh" items="${dsDonHang}">
								<tr>
									<td class="fw-bold">#${dh.maHoaDon}</td>
									<td class="text-start">
                                        <div class="fw-bold">${dh.hoten}</div>
                                        <small class="text-muted">ID: ${dh.makh}</small>
                                    </td>
									<td>${dh.tongsoluong}</td>
									<td class="text-price"><fmt:formatNumber value="${dh.thanhTien}" type="number" /> ₫</td>
									<td>
                                        <c:choose>
											<c:when test="${dh.damua}">
												<span class="badge bg-success rounded-pill">Đã Thanh toán</span>
											</c:when>
											<c:otherwise>
												<span class="badge bg-secondary rounded-pill">COD</span>
											</c:otherwise>
										</c:choose>
                                    </td>
									<td>
                                        <a href="XacNhanController?action=chitiet&mahd=${dh.maHoaDon}" class="btn btn-sm btn-info text-white me-1 shadow-sm">Xem</a>
                                        <a href="XacNhanController?action=xacnhan&mahd=${dh.maHoaDon}" 
                                           class="btn btn-sm btn-primary fw-bold shadow-sm"
                                           onclick="return confirm('Xác nhận giao đơn này?')">Duyệt</a>
                                    </td>
								</tr>
							</c:forEach>
                            <c:if test="${empty dsDonHang}">
                                <tr><td colspan="6" class="p-4 text-muted">Hiện tại không có đơn hàng nào cần xử lý.</td></tr>
                            </c:if>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
	
	<jsp:include page="../footer.jsp" />
	
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>