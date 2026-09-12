<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html><html><head><meta charset="UTF-8"><title>Add Category</title></head><body style="font-family:Arial;max-width:600px;margin:40px auto">
<h2>Thêm danh mục</h2><form action="${pageContext.request.contextPath}/category/add" method="post">
<p>Tên danh mục:<br><input style="width:100%;padding:9px" name="name" required></p>
<p>Link ảnh (icon):<br><input style="width:100%;padding:9px" name="icon" placeholder="https://..."></p>
<button style="padding:9px 18px" type="submit">Thêm</button> <a href="${pageContext.request.contextPath}/category/list">Hủy</a></form></body></html>
