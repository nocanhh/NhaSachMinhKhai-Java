<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Trang chủ Admin</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
    <link href="css/admin-style.css" rel="stylesheet"> 
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
</head>
<body>
    <jsp:include page="header.jsp" />

    <div class="container-fluid">
        <div class="row">
            <div class="col-md-2">
                <jsp:include page="menu.jsp" />
            </div>

            <div class="col-md-10">
                <h4 class="section-header">TỔNG QUAN KINH DOANH</h4>
                
                <div class="row mb-4">
                    <div class="col-md-4">
                        <div class="card card-stat bg-primary h-100 shadow-sm">
                            <div class="card-body text-center">
                                <div class="fs-1 mb-2"><i class="bi bi-currency-dollar"></i></div>
                                <h3 class="stat-value">
                                    <a href="HoaDonAdminController?action=xemngay&ngay=${homnay}" class="text-white text-decoration-none">
                                        <fmt:formatNumber value="${doanhThuHomNay}" type="number" /> VNĐ
                                    </a>
                                </h3>
                                <div class="stat-label">Doanh thu hôm nay</div>
                            </div>
                        </div>
                    </div>

                    <div class="col-md-4">
                        <div class="card card-stat bg-warning h-100 shadow-sm">
                            <div class="card-body text-center">
                                <div class="fs-1 mb-2"><i class="bi bi-exclamation-triangle"></i></div>
                                <h3 class="stat-value">${dsSapHet.size()} quyển</h3>
                                <div class="stat-label">Sách sắp hết hàng (<= 10)</div>
                            </div>
                        </div>
                    </div>

                    <div class="col-md-4">
                        <div class="card card-stat bg-success h-100 shadow-sm">
                            <div class="card-body text-center">
                                <div class="fs-1 mb-2"><i class="bi bi-trophy"></i></div>
                                <h5 class="stat-value text-truncate">
                                    <c:if test="${not empty dsBanChay}">
                                        ${dsBanChay[0].tensach}
                                    </c:if>
                                    <c:if test="${empty dsBanChay}">Chưa có</c:if>
                                </h5>
                                <div class="stat-label">Sách bán chạy nhất</div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="card shadow mb-4">
                    <div class="card-header bg-white text-dark fw-bold border-bottom py-3">
                        <i class="bi bi-bar-chart-fill text-success fs-5"></i> BIỂU ĐỒ DOANH THU 7 NGÀY GẦN NHẤT
                    </div>
                    <div class="card-body">
                        <div id="chart_div" style="width: 100%; height: 600px;"></div>
                    </div>
                </div>

                <div class="table-container shadow">
                    <h5 class="mb-3 text-danger fw-bold"><i class="bi bi-box-seam"></i> CẢNH BÁO: SÁCH SẮP HẾT HÀNG</h5>
                    <table class="table table-hover align-middle text-center">
                        <thead>
                            <tr>
                                <th>Mã sách</th>
                                <th>Tên sách</th>
                                <th>Số lượng còn</th>
                                <th>Hành động</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="s" items="${dsSapHet}">
                                <tr>
                                    <td>${s.masach}</td>
                                    <td class="text-start fw-bold">${s.tensach}</td>
                                    <td>
                                        <span class="badge bg-danger rounded-pill px-3 py-2 fs-6">${s.soluong}</span>
                                    </td>
                                    <td>
                                        <a href="SachAdminController?action=edit&ms=${s.masach}" class="btn btn-sm btn-warning fw-bold">
                                            <i class="bi bi-plus-circle"></i> Nhập hàng
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty dsSapHet}">
                                <tr><td colspan="4" class="text-muted">Kho hàng đang ổn định.</td></tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>

            </div>
        </div>
    </div>

    <script type="text/javascript">
      // Nạp biểu đồ
      google.charts.load('current', {'packages':['corechart']});
      google.charts.setOnLoadCallback(drawChart);

      function drawChart() {
        var data = google.visualization.arrayToDataTable([
          ['Ngày', 'Doanh thu', { role: 'style' }, { role: 'annotation' }], // Thêm annotation để hiện số tiền lên đầu cột
          <c:forEach var="item" items="${listDoanhThuChart}">
            ['${item.ngay}', ${item.tongTien}, 'color: #20B970', '${item.tongTien}'], // Hiện số tiền lên cột
          </c:forEach>
        ]);

        var options = {
          // Bỏ title ở đây vì đã có ở Header Card rồi
          // Tăng diện tích hiển thị biểu đồ (chartArea)
          chartArea: { width: '85%', height: '80%' },
          
          hAxis: {
              title: 'Ngày trong tuần', 
              titleTextStyle: {color: '#333', fontSize: 14, bold: true},
              textStyle: {fontSize: 12}
          },
          vAxis: {
              title: 'Doanh thu (VNĐ)', 
              minValue: 0,
              textStyle: {fontSize: 12},
              gridlines: {color: '#f0f0f0'} // Đường kẻ mờ cho đẹp
          },
          legend: { position: 'none' }, // Ẩn chú thích vì chỉ có 1 màu
          bar: { groupWidth: '50%' },   // Chỉnh độ rộng cột (50% là vừa đẹp, không bị gầy quá)
          
          animation: {
            startup: true,
            duration: 1200,
            easing: 'out',
          }
        };

        var chart = new google.visualization.ColumnChart(document.getElementById('chart_div'));
        chart.draw(data, options);
      }
    </script>
    
    <jsp:include page="../footer.jsp" />
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>