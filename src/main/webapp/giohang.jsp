<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Giỏ hàng</title>
	<link href="${pageContext.request.contextPath}/css/style-user.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
</head>
<body>
	<jsp:include page="nav.jsp" />

	<div class="container mt-4 mb-5">
		<div class="row">
			<div class="col-md-3">
				<jsp:include page="sidebar.jsp" />
			</div>

			<div class="col-md-9">
				<h3 class="text-center text-success fw-bold mb-4">🛒 GIỎ HÀNG
					CỦA BẠN</h3>

				<c:choose>
					<c:when test="${empty sessionScope.gio.ds}">
						<div class="text-center p-5 bg-white rounded shadow-sm">
							<i class="bi bi-cart-x text-muted" style="font-size: 4rem;"></i>
							<p class="mt-3 text-muted">Giỏ hàng đang trống.</p>
							<a href="tcController" class="btn btn-primary fw-bold">Mua
								sắm ngay</a>
						</div>
					</c:when>

					<c:otherwise>
						<form action="giohangController" method="post" id="giohangForm">
							<div class="card shadow-sm border-0 mb-4">
								<div class="card-body p-0">
									<table class="table table-custom align-middle text-center mb-0">
										<thead>
											<tr>
												<th><input type="checkbox" id="checkAll"
													class="form-check-input"></th>
												<th>Sản phẩm</th>
												<th>Đơn giá</th>
												<th>Số lượng</th>
												<th>Thành tiền</th>
												<th>#</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach var="g" items="${sessionScope.gio.ds}">
												<tr>
													<td><input type="checkbox" name="chonMua"
														value="${g.masach}" class="form-check-input itemCheckbox"></td>
													<td class="text-start">
														<div class="d-flex align-items-center">
															<img src="${g.anh}" width="50" height="70"
																class="rounded border me-3"> <span class="fw-bold">${g.tensach}</span>
														</div>
													</td>
													<td class="text-muted"><fmt:formatNumber
															value="${g.gia}" type="number" /> ₫</td>
													<td>
														<div
															class="input-group input-group-sm justify-content-center"
															style="width: 120px; margin: 0 auto;">
															<input type="number" name="soluongmoi_${g.masach}"
																value="${g.soluong}" min="1"
																class="form-control text-center">
															<button type="submit" name="sua" value="${g.masach}"
																class="btn btn-outline-success">
																<i class="bi bi-arrow-repeat"></i>
															</button>
														</div>
													</td>
													<td class="text-price"><fmt:formatNumber
															value="${g.thanhtien}" type="number" /> ₫</td>
													<td>
														<button type="submit" name="xoa" value="${g.masach}"
															class="btn btn-sm btn-light text-danger border"
															onclick="return confirm('Xóa sách này?');">
															<i class="bi bi-trash"></i>
														</button>
													</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>

							<div class="row align-items-center">
								<div class="col-md-6">
									<a href="tcController"
										class="btn btn-outline-secondary fw-bold"><i
										class="bi bi-arrow-left"></i> Tiếp tục mua</a>
									<button type="submit" name="xoaall" value="1"
										class="btn btn-outline-danger ms-2"
										onclick="return confirm('Xóa hết giỏ hàng?');">Xóa
										tất cả</button>
								</div>

								<div class="col-md-6 text-end">
									<div class="fs-4 fw-bold mb-3">
										Tổng tiền: <span class="text-danger"><fmt:formatNumber
												value="${sessionScope.gio.TongTien()}" type="number" /> ₫</span>
									</div>

									<button type="submit" formaction="thanhtoanController"
										class="btn btn-orange btn-lg rounded-pill px-5 shadow">
										TIẾN HÀNH THANH TOÁN <i class="bi bi-arrow-right"></i>
									</button>
								</div>
							</div>
						</form>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</div>

			<script
				src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
			<script>
        document.getElementById('checkAll').addEventListener('change', function () {
            var checkboxes = document.getElementsByClassName('itemCheckbox');
            for (var i = 0; i < checkboxes.length; i++) { checkboxes[i].checked = this.checked; }
        });
    </script>
    <jsp:include page="footer.jsp" />
</body>
</html>