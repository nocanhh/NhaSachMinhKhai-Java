<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Xác nhận Thanh toán</title>
    <link href="${pageContext.request.contextPath}/css/style-user.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
</head>
<body>
	<jsp:include page="nav.jsp" />

	<div class="container mt-4 mb-5">
		<div class="row">
			<div class="col-md-3 mb-4">
				<jsp:include page="sidebar.jsp" />
			</div>

			<div class="col-md-9">
				<h3 class="text-center text-success fw-bold mb-4">XÁC NHẬN ĐƠN HÀNG</h3>

				<c:if test="${empty sessionScope.un || empty sessionScope.dsMuaHang}">
					<div class="alert alert-warning text-center p-4 shadow-sm">
                        <i class="bi bi-cart-x fs-3"></i><br>
                        Giỏ hàng trống. <a href="giohangController" class="fw-bold">Quay lại mua sắm</a>
                    </div>
				</c:if>

				<c:if test="${not empty sessionScope.un && not empty sessionScope.dsMuaHang}">
					<form action="xuLyThanhToanController" method="POST" id="paymentForm">
						<div class="row">
							<div class="col-md-5 mb-4">
								<div class="card shadow-sm border-0 mb-3">
									<div class="card-header bg-white text-success fw-bold border-bottom">
                                        <i class="bi bi-geo-alt-fill"></i> Thông tin giao hàng
                                    </div>
									<div class="card-body">
										<div class="mb-2"><i class="bi bi-person"></i> <strong>Người nhận:</strong> ${sessionScope.un.hoten}</div>
										<div class="mb-2"><i class="bi bi-telephone"></i> <strong>SĐT:</strong> ${sessionScope.un.sodt}</div>
										<div class="mb-2">
                                            <label class="form-label fw-bold small text-muted">Địa chỉ nhận hàng:</label> 
                                            <input type="text" class="form-control" name="diachi" value="${sessionScope.un.diachi}" required>
                                        </div>
									</div>
								</div>

								<div class="card shadow-sm border-0">
									<div class="card-header bg-white text-success fw-bold border-bottom">
                                        <i class="bi bi-credit-card-2-front"></i> Phương thức thanh toán
                                    </div>
									<div class="card-body">
										<div class="form-check mb-3 p-3 border rounded bg-light">
											<input class="form-check-input" type="radio" name="phuongthuc" id="cod" value="COD" checked onchange="togglePaymentInfo()"> 
											<label class="form-check-label fw-bold" for="cod">
                                                <i class="bi bi-cash-coin text-warning"></i> Thanh toán khi nhận hàng (COD)
                                            </label>
										</div>

										<div class="form-check mb-3 p-3 border rounded">
											<input class="form-check-input" type="radio" name="phuongthuc" id="qr" value="QR" onchange="togglePaymentInfo()"> 
											<label class="form-check-label fw-bold" for="qr">
												<img src="https://img.vietqr.io/image/MB-123456789-compact.png" height="20"> Chuyển khoản QR (Auto)
											</label>
										</div>

										<div class="form-check p-3 border rounded">
											<input class="form-check-input" type="radio" name="phuongthuc" id="momo" value="MOMO" onchange="togglePaymentInfo()"> 
											<label class="form-check-label fw-bold" for="momo">
												<img src="https://developers.momo.vn/v3/assets/images/logo-custom2-57d6118fe524633b89befe8cb63a3956.png" height="25"> Ví MoMo
											</label>
										</div>
									</div>
								</div>

								<div id="qrInfo" class="alert alert-info mt-3 text-center small shadow-sm" style="display: none;">
									<i class="bi bi-info-circle-fill"></i> Sau khi bấm <b>"Hoàn tất đặt hàng"</b>, mã QR sẽ hiện ra để bạn quét.
								</div>
							</div>

							<div class="col-md-7">
								<div class="card shadow-sm border-0">
									<div class="card-header bg-white text-success fw-bold border-bottom">
                                        <i class="bi bi-bag-check"></i> Chi tiết đơn hàng
                                    </div>
									<div class="card-body p-0">
										<table class="table table-custom align-middle mb-0">
											<thead>
												<tr><th>Sách</th><th class="text-center">SL</th><th class="text-end">Tiền</th></tr>
											</thead>
											<tbody>
												<c:forEach var="g" items="${sessionScope.dsMuaHang}">
													<tr>
														<td>
                                                            <div class="d-flex align-items-center">
                                                                <img src="${g.anh}" width="40" class="rounded border me-2"> 
                                                                <span class="small fw-bold">${g.tensach}</span>
                                                            </div>
                                                        </td>
														<td class="text-center">${g.soluong}</td>
														<td class="text-end text-muted fw-bold"><fmt:formatNumber value="${g.thanhtien}" type="number"/> ₫</td>
													</tr>
												</c:forEach>
											</tbody>
                                            <tfoot class="table-light">
                                                <tr>
                                                    <td colspan="2" class="text-end fw-bold pt-3">TỔNG THANH TOÁN:</td>
                                                    <td class="text-end pt-3">
                                                        <span class="text-price fs-5"><fmt:formatNumber value="${sessionScope.tongTienMua}" type="number" /> ₫</span>
                                                    </td>
                                                </tr>
                                            </tfoot>
										</table>
									</div>
									<div class="card-footer bg-white p-3">
										<div class="d-grid">
											<button type="submit" class="btn btn-orange btn-lg fw-bold shadow">
                                                HOÀN TẤT ĐẶT HÀNG <i class="bi bi-check2-circle"></i>
                                            </button>
										</div>
                                        <div class="text-center mt-2">
                                            <a href="giohangController" class="text-muted small text-decoration-none">Quay lại giỏ hàng</a>
                                        </div>
									</div>
								</div>
							</div>
						</div>
					</form>
				</c:if>
			</div>
		</div>
	</div>

	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
	<script>
        function togglePaymentInfo() {
            var method = document.querySelector('input[name="phuongthuc"]:checked').value;
            var qrInfo = document.getElementById("qrInfo");
            if (method === "QR") {
                qrInfo.style.display = "block";
            } else {
                qrInfo.style.display = "none";
            }
        }
    </script>
    <jsp:include page="footer.jsp" />
</body>
</html>