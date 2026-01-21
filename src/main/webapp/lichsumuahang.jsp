<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Lịch sử mua hàng</title>
<link href="${pageContext.request.contextPath}/css/style-user.css"
	rel="stylesheet">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
</head>
<body>
	<jsp:include page="nav.jsp" />

	<div class="container mt-4 mb-5">
		<div class="row">
			<div class="col-md-3">
				<jsp:include page="sidebar.jsp" />
			</div>

			<div class="col-md-9">
				<h3 class="text-center text-success fw-bold mb-4">📜 LỊCH SỬ
					ĐƠN HÀNG</h3>

				<c:choose>
					<c:when test="${empty dsLichSu}">
						<div class="alert alert-info text-center p-4">
							<i class="bi bi-bag-x fs-3"></i><br> Bạn chưa có đơn hàng
							nào. <a href="tcController" class="fw-bold">Mua sắm ngay</a>
						</div>
					</c:when>
					<c:otherwise>
						<div class="card shadow-sm border-0">
							<div class="card-body p-0">
								<table
									class="table table-custom table-hover align-middle text-center mb-0">
									<thead>
										<tr>
											<th>Thời gian</th>
											<th>Sản phẩm</th>
											<th>Tổng tiền</th>
											<th>Trạng thái Đơn</th>
											<th>Thanh toán</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="item" items="${dsLichSu}">
											<tr>
												<td class="text-muted small"><i class="bi bi-clock"></i>
													<fmt:formatDate value="${item.ngayMua}"
														pattern="dd/MM/yyyy HH:mm" /></td>
												<td class="text-start"><span
													class="fw-bold text-success">${item.tensach}</span> <br>
													<small class="text-muted">SL: ${item.soLuongMua} x
														<fmt:formatNumber value="${item.gia}" type="number" />₫
												</small></td>
												<td class="text-price"><fmt:formatNumber
														value="${item.thanhTien}" type="number" /> ₫</td>

												<td><c:choose>
														<c:when test="${item.daXacNhan}">
															<span class="badge bg-primary"><i
																class="bi bi-truck"></i> Đang giao</span>
														</c:when>
														<c:otherwise>
															<span class="badge bg-warning text-dark"><i
																class="bi bi-hourglass-split"></i> Chờ duyệt</span>
														</c:otherwise>
													</c:choose></td>

												<td><c:choose>
														<c:when test="${item.damua}">
															<span class="badge bg-success"><i
																class="bi bi-check-circle"></i> Đã thanh toán</span>
														</c:when>
														<c:otherwise>
															<span class="badge bg-danger mb-2">Chưa thanh toán</span>
															<div>
																<a href="ThanhToanLaiController?mahd=${item.maDonHang}"
																	class="btn btn-sm btn-outline-danger fw-bold">
																	Thanh toán ngay </a>
															</div>
														</c:otherwise>
													</c:choose></td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</div>
	<jsp:include page="footer.jsp" />
</body>
</html>