<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <title>Chi tiết doanh thu ngày</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
    <link href="css/admin-style.css" rel="stylesheet">
</head>
<body>
	<jsp:include page="header.jsp" />
	
    <div class="container-fluid mt-3">
        <div class="row">
            <div class="col-md-2"><jsp:include page="menu.jsp" /></div>
            
            <div class="col-md-10">
                <nav aria-label="breadcrumb">
                  <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="AdminHomeController">Trang chủ</a></li>
                    <li class="breadcrumb-item active" aria-current="page">Chi tiết doanh thu</li>
                  </ol>
                </nav>
            
            	<h4 class="section-header mb-4">DANH SÁCH HOÁ ĐƠN NGÀY: ${ngayXem}</h4>
                
                <table class="table table-striped table-bordered table-hover">
                    <thead class="table-primary">
                        <tr>
                            <th>Mã HĐ</th>
                            <th>Khách hàng</th>
                            <th>Thời gian mua</th>
                            <th>Tổng tiền</th>
                            <th>Trạng thái</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="hd" items="${dsHoaDonNgay}">
                            <tr>
                                <td>${hd.maHoaDon}</td>
                                <td>${hd.tenKhachHang}</td>
                                <td><fmt:formatDate value="${hd.ngayMua}" pattern="HH:mm:ss"/></td>
                                <td class="text-danger fw-bold">
                                    <fmt:formatNumber value="${hd.tongTien}" type="number"/> VNĐ
                                </td>
                                <td><span class="badge bg-success">Đã thanh toán</span></td>
                            </tr>
                        </c:forEach>
                        <c:if test="${empty dsHoaDonNgay}">
                            <tr><td colspan="5" class="text-center">Không có dữ liệu cho ngày này.</td></tr>
                        </c:if>
                    </tbody>
                </table>
                
                <div class="text-end fw-bold fs-4 text-danger mt-3">
                    Tổng cộng: 
                    <c:set var="total" value="${0}"/>
                    <c:forEach var="hd" items="${dsHoaDonNgay}">
                        <c:set var="total" value="${total + hd.tongTien}"/>
                    </c:forEach>
                    <fmt:formatNumber value="${total}" type="number"/> VNĐ
                </div>
            </div>
        </div>
    </div>
    
    <jsp:include page="../footer.jsp" />
</body>
</html>