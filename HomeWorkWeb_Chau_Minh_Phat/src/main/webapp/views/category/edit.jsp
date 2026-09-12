<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html><html><head><meta charset="UTF-8"><title>Edit Category</title></head><body style="font-family:Arial;max-width:600px;margin:40px auto">
<h2>Sửa danh mục</h2><form action="${pageContext.request.contextPath}/category/edit" method="post">
<input type="hidden" name="id" value="${category.id}">
<p>Tên danh mục:<br><input style="width:100%;padding:9px" name="name" value="${category.name}" required></p>
<p>Link ảnh (icon):<br><input style="width:100%;padding:9px" name="icon" value="${category.icon}"></p>
<p><img src="${category.icon}" style="max-width:180px"></p>
<button style="padding:9px 18px" type="submit">Lưu</button> <a href="${pageContext.request.contextPath}/category/list">Hủy</a></form></body></html>
