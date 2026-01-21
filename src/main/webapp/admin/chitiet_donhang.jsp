<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
    <title>Chi tiết Đơn hàng #${maHD}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="css/admin-style.css" rel="stylesheet">
</head>
<body>
    <jsp:include page="header.jsp" />
    
	<div class="container mt-4 mb-5">
		<div class="card shadow border-0">
			<div class="card-header d-flex justify-content-between align-items-center">
				<h4 class="section-header mb-4">ĐƠN HÀNG ${maHD}</h4>
				<a href="XacNhanController" class="btn btn-light btn-sm fw-bold text-success">⬅ Quay lại danh sách</a>
			</div>

			<div class="card-body">
				<c:if test="${empty dsChiTiet}">
					<div class="alert alert-warning text-center">Không tìm thấy sản phẩm nào.</div>
				</c:if>

				<c:if test="${not empty dsChiTiet}">
                    <h5 class="text-success mb-3">Danh sách sản phẩm</h5>
					<table class="table table-striped table-hover text-center align-middle border">
						<thead class="table-success">
							<tr>
								<th>Ảnh</th>
								<th>Tên sách</th>
								<th>Giá bán</th>
								<th>Số lượng</th>
								<th>Thành tiền</th>
							</tr>
						</thead>
						<tbody>
							<c:set var="tongCong" value="0" />
							<c:forEach var="ct" items="${dsChiTiet}">
								<c:set var="tongCong" value="${tongCong + ct.thanhTien}" />
								<tr>
									<td><img src="${ct.anh}" class="rounded shadow-sm" style="height: 60px; border: 1px solid #eee;"></td>
									<td class="text-start fw-bold text-dark">${ct.tensach}</td>
									<td><fmt:formatNumber value="${ct.gia}" type="number" /> ₫</td>
									<td><span class="badge bg-secondary rounded-pill">${ct.soLuongMua}</span></td>
									<td class="fw-bold text-danger"><fmt:formatNumber value="${ct.thanhTien}" type="number" /> ₫</td>
								</tr>
							</c:forEach>
						</tbody>
						<tfoot>
							<tr class="table-active">
								<td colspan="4" class="text-end fw-bold fs-5">TỔNG CỘNG:</td>
								<td class="text-danger fw-bold fs-4"><fmt:formatNumber value="${tongCong}" type="number" /> ₫</td>
							</tr>
						</tfoot>
					</table>
				</c:if>
			</div>

			<div class="card-footer text-center p-3 bg-white">
				<a href="XacNhanController?action=xacnhan&mahd=${maHD}"
					class="btn btn-success btn-lg fw-bold px-5 shadow"
					onclick="return confirm('Xác nhận đơn hàng này?')">
					✅ DUYỆT ĐƠN HÀNG
                </a>
			</div>
		</div>
	</div>
    <jsp:include page="../footer.jsp" />
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>