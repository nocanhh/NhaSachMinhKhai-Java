<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<title>Thanh toán đơn hàng #${maHoaDon}</title>
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
				<h3 class="text-center mb-4 text-success fw-bold">THANH TOÁN
					ĐƠN HÀNG #${maHoaDon}</h3>

				<form action="XuLyThanhToanLaiController" method="POST"
					id="mainForm">
					<input type="hidden" name="maHoaDon" value="${maHoaDon}"> <input
						type="hidden" name="tongTien" value="${tongTien}">

					<div class="row">
						<div class="col-md-5 mb-4">
							<div class="card mb-3 shadow-sm border-0">
								<div
									class="card-header bg-white text-success fw-bold border-bottom">
									<i class="bi bi-person-lines-fill"></i> Thông tin nhận hàng
								</div>
								<div class="card-body">
									<div class="mb-1">
										<i class="bi bi-person"></i> ${sessionScope.un.hoten}
									</div>
									<div class="mb-1">
										<i class="bi bi-telephone"></i> ${sessionScope.un.sodt}
									</div>
									<div class="mb-1 text-muted small">
										<i class="bi bi-geo-alt"></i> ${sessionScope.un.diachi}
									</div>
								</div>
							</div>

							<div class="card shadow-sm border-0">
								<div
									class="card-header bg-white text-success fw-bold border-bottom">
									<i class="bi bi-credit-card"></i> Chọn phương thức thanh toán
								</div>
								<div class="card-body">
									<div class="form-check mb-3 p-3 border rounded bg-light">
										<input class="form-check-input" type="radio" name="phuongthuc"
											id="cod" value="COD" checked onchange="toggleInfo()">
										<label class="form-check-label fw-bold" for="cod">
											Thanh toán khi nhận hàng (COD) </label>
									</div>

									<div class="form-check mb-3 p-3 border rounded">
										<input class="form-check-input" type="radio" name="phuongthuc"
											id="qr" value="QR" onchange="toggleInfo()"> <label
											class="form-check-label fw-bold" for="qr"> <img
											src="https://img.vietqr.io/image/BIDV-8867134499-compact.png"
											height="20"> Chuyển khoản QR (Auto)
										</label>
									</div>

									<div class="form-check p-3 border rounded">
										<input class="form-check-input" type="radio" name="phuongthuc"
											id="momo" value="MOMO" onchange="toggleInfo()"> <label
											class="form-check-label fw-bold" for="momo"> <img
											src="https://developers.momo.vn/v3/assets/images/logo-custom2-57d6118fe524633b89befe8cb63a3956.png"
											height="20"> Ví MoMo
										</label>
									</div>
								</div>
							</div>

							<div id="qrInfo"
								class="card mt-3 border-success text-center shadow"
								style="display: none;">
								<div class="card-header bg-success text-white fw-bold">QUÉT
									MÃ ĐỂ THANH TOÁN</div>
								<div class="card-body bg-light">
									<c:set var="noiDungCK" value="DH${maHoaDon}" />

									<img
										src="https://img.vietqr.io/image/BIDV-8867134499-compact.png?amount=${tongTien}&addInfo=${noiDungCK}"
										class="img-fluid border border-2 border-white rounded shadow-sm mb-3"
										style="max-width: 220px;">

									<h4 class="text-price mb-2">
										<fmt:formatNumber value="${tongTien}" type="number" />
										₫
									</h4>

									<p class="mb-1 small text-muted">Nội dung chuyển khoản (Bắt
										buộc):</p>
									<h5
										class="text-success fw-bold bg-white p-2 border border-dashed rounded d-inline-block px-4">
										${noiDungCK}</h5>

									<div
										class="alert alert-warning mt-3 mb-0 d-flex align-items-center justify-content-center"
										id="statusMessage">
										<div
											class="spinner-border spinner-border-sm text-warning me-2"
											role="status"></div>
										<div class="small text-start">
											Đang chờ thanh toán...<br> (Tự động duyệt sau 15-20s)
										</div>
									</div>
								</div>
							</div>
						</div>

						<div class="col-md-7">
							<div class="card shadow-sm border-0">
								<div
									class="card-header bg-white text-success fw-bold border-bottom">
									<i class="bi bi-receipt"></i> Chi tiết đơn hàng
								</div>
								<div class="card-body p-0">
									<table class="table table-custom align-middle mb-0">
										<thead>
											<tr>
												<th>Sản phẩm</th>
												<th class="text-center">SL</th>
												<th class="text-end">Thành tiền</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach var="item" items="${dsChiTiet}">
												<tr>
													<td>
														<div class="d-flex align-items-center">
															<img src="${item.anh}" width="40" height="60"
																class="rounded border me-2" style="object-fit: cover;">
															<span class="small fw-bold">${item.tensach}</span>
														</div>
													</td>
													<td class="text-center fw-bold">${item.soLuongMua}</td>
													<td class="text-end text-muted fw-bold"><fmt:formatNumber
															value="${item.thanhTien}" type="number" /> ₫</td>
												</tr>
											</c:forEach>
										</tbody>
										<tfoot class="table-light">
											<tr>
												<td colspan="2" class="text-end fw-bold pt-3">TỔNG
													CỘNG:</td>
												<td class="text-end pt-3"><span class="text-price fs-5"><fmt:formatNumber
															value="${tongTien}" type="number" /> ₫</span></td>
											</tr>
										</tfoot>
									</table>
								</div>
								<div class="card-footer bg-white p-3">
									<div class="d-grid" id="btnSubmitContainer">
										<button type="submit"
											class="btn btn-orange btn-lg fw-bold shadow">
											XÁC NHẬN (COD) <i class="bi bi-check-lg"></i>
										</button>
									</div>

									<div
										class="text-center text-success mt-2 fw-bold small bg-light p-2 rounded border border-success"
										id="qrInstruction" style="display: none;">
										<i class="bi bi-info-circle-fill"></i> Vui lòng quét mã QR bên
										trái. Hệ thống sẽ tự động chuyển trang khi nhận được tiền.
									</div>

									<div class="text-center mt-3">
										<a href="lichsumuahangController"
											class="text-decoration-none text-muted small"><i
											class="bi bi-arrow-left"></i> Quay lại lịch sử</a>
									</div>
								</div>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>

			<script
				src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

			<script>
    const ORDER_ID = "${maHoaDon}";
    const AMOUNT = ${tongTien};
    const CONTENT_PATTERN = ("DH" + ORDER_ID).toUpperCase();

    var checkInterval = null;

    function toggleInfo() {
        var method = document.querySelector('input[name="phuongthuc"]:checked').value;
        var qrSection = document.getElementById("qrInfo");
        var btnContainer = document.getElementById("btnSubmitContainer");
        var qrInstruction = document.getElementById("qrInstruction");

        if (method === "QR") {
            qrSection.style.display = "block";
            btnContainer.style.display = "none";
            qrInstruction.style.display = "block";

            if (checkInterval == null) {
                checkInterval = setInterval(checkPayment, 20000);
            }
        } else {
            qrSection.style.display = "none";
            btnContainer.style.display = "block";
            qrInstruction.style.display = "none";

            if (checkInterval != null) {
                clearInterval(checkInterval);
                checkInterval = null;
            }
        }
    }

    async function checkPayment() {
        try {
            const res = await fetch('CheckSepayController', { cache: "no-store" });
            if (!res.ok) return;
            const data = await res.json();
            
            if (data && data.status === 200 && data.transactions) {
                for (let trans of data.transactions) {
                    let contentNganHang = (trans.transaction_content || "").toString().toUpperCase();
                    let soTienNganHang = Number(trans.amount_in || 0);

                    if (soTienNganHang >= AMOUNT && contentNganHang.includes(CONTENT_PATTERN)) {
                        const statusEl = document.getElementById("statusMessage");
                        statusEl.className = "alert alert-success d-flex align-items-center justify-content-center";
                        statusEl.innerHTML = "<i class='bi bi-check-circle-fill me-2'></i> <b>Thành công!</b> Đang chuyển hướng...";

                        if (checkInterval) { clearInterval(checkInterval); checkInterval = null; }
                        window.location.href = "XacNhanTuDongController?mahd=" + ORDER_ID;
                        return;
                    }
                }
            }
        } catch (error) {
            console.error("Lỗi check SePay:", error);
        }
    }
    
    // Auto start nếu reload
    document.addEventListener("DOMContentLoaded", function() {
        try { toggleInfo(); } catch(e) {}
    });
</script>
<jsp:include page="footer.jsp" />
</body>
</html>