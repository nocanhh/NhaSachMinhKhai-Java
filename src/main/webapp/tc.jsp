<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Trang chủ - Minh Khai Books</title>
<link href="${pageContext.request.contextPath}/css/style-user.css"
	rel="stylesheet">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
</head>
<body>
	<jsp:include page="nav.jsp" />

	<div class="container mt-4 mb-5">
		<div class="row">
			<div class="col-md-3 mb-4">
				<jsp:include page="sidebar.jsp" />
			</div>

			<div class="col-md-9">
				<div class="row g-4">
					<c:forEach var="s" items="${dssach}">
						<div class="col-md-4 col-sm-6">
							<div class="card product-card shadow-sm h-100">
								<img src="${s.anh}" class="card-img-top" alt="${s.tensach}">
								<div class="card-body p-3 text-center">
									<h6 class="card-title text-truncate fw-bold mb-1"
										title="${s.tensach}">${s.tensach}</h6>
									<p class="text-muted small mb-2">
										<i>${s.tacgia}</i>
									</p>
									<div class="price-tag mb-3">
										<fmt:formatNumber value="${s.gia}" type="number" />
										₫
									</div>
									<div class="mt-auto">
										<form
											action="${pageContext.request.contextPath}/giohangController"
											method="post">
											<input type="hidden" name="masach" value="${s.masach}" /> <input
												type="hidden" name="tensach" value="${s.tensach}" /> <input
												type="hidden" name="gia" value="${s.gia}" /> <input
												type="hidden" name="anh" value="${s.anh}" />
											<button type="submit"
												class="btn btn-orange w-100 rounded-pill shadow-sm">
												<i class="bi bi-cart-plus-fill"></i> ĐẶT MUA
											</button>
										</form>
									</div>
								</div>
							</div>
						</div>
					</c:forEach>

					<c:if test="${empty dssach}">
						<div class="col-12">
							<div class="alert alert-warning text-center p-5 shadow-sm">
								<i class="bi bi-search display-1 text-warning mb-3"></i><br>
								<h5>Không tìm thấy cuốn sách nào!</h5>
							</div>
						</div>
					</c:if>
				</div>

				<c:if test="${totalPages > 1}">
					<nav aria-label="Page navigation" class="mt-5">
						<ul class="pagination justify-content-center">

							<c:choose>
								<c:when test="${currentPage > 1}">
									<c:url var="prevUrl" value="tcController">
										<c:param name="page" value="${currentPage - 1}" />
										<c:param name="maloai" value="${maloai}" />
										<c:param name="key" value="${key}" />
									</c:url>
									<li class="page-item"><a class="page-link shadow-sm"
										href="${prevUrl}">&laquo;</a></li>
								</c:when>
								<c:otherwise>
									<li class="page-item disabled"><span class="page-link">&laquo;</span>
									</li>
								</c:otherwise>
							</c:choose>

							<c:forEach var="i" begin="1" end="${totalPages}">
								<c:choose>
									<%-- TRƯỜNG HỢP 1: Luôn hiển thị trang 1, trang cuối, và các trang lân cận (Current - 1, Current, Current + 1) --%>
									<c:when
										test="${i == 1 || i == totalPages || (i >= currentPage - 1 && i <= currentPage + 1)}">
										<c:url var="pageUrl" value="tcController">
											<c:param name="page" value="${i}" />
											<c:param name="maloai" value="${maloai}" />
											<c:param name="key" value="${key}" />
										</c:url>

										<li class="page-item ${i == currentPage ? 'active' : ''}">
											<a class="page-link shadow-sm fw-bold" href="${pageUrl}">${i}</a>
										</li>
									</c:when>

									<%-- TRƯỜNG HỢP 2: Hiển thị dấu ... nếu có khoảng cách --%>
									<c:when test="${i == currentPage - 2 || i == currentPage + 2}">
										<li class="page-item disabled"><span
											class="page-link border-0 bg-transparent text-muted fw-bold">...</span>
										</li>
									</c:when>

									<%-- Các trường hợp còn lại: Ẩn đi (Không làm gì cả) --%>
								</c:choose>
							</c:forEach>

							<c:choose>
								<c:when test="${currentPage < totalPages}">
									<c:url var="nextUrl" value="tcController">
										<c:param name="page" value="${currentPage + 1}" />
										<c:param name="maloai" value="${maloai}" />
										<c:param name="key" value="${key}" />
									</c:url>
									<li class="page-item"><a class="page-link shadow-sm"
										href="${nextUrl}">&raquo;</a></li>
								</c:when>
								<c:otherwise>
									<li class="page-item disabled"><span class="page-link">&raquo;</span>
									</li>
								</c:otherwise>
							</c:choose>
						</ul>
					</nav>
				</c:if>
			</div>
		</div>
	</div>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
	<jsp:include page="footer.jsp" />
</body>
</html>