<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form method="post" action="sachAdminController" enctype= "multipart/form-data">
  	Mã sách: <input type="text" name="txtmasach" value=""> <br>
  	Tên sách: <input type="text" name="txttensach" value=""> <br>
  	Ảnh: <input type="file" name="txtfile"> <br>
  	Số lượng: <input type="number" name="txtsoluong" value="0"> <br>
  	<input type="submit" value = "Add"> 
  	<input type="submit" name = "butxoa" value = "xoa">
  	</form> 
	
</body>
</html>