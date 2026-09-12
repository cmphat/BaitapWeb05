<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html><html><head><meta charset="UTF-8"><title>Category</title>
<style>body{font-family:Arial;max-width:1000px;margin:30px auto}table{border-collapse:collapse;width:100%}th,td{border:1px solid #ddd;padding:10px;text-align:left}img{width:100px;height:70px;object-fit:cover}.btn{display:inline-block;padding:8px 12px;background:#1677ff;color:white;text-decoration:none;border-radius:5px}.danger{background:#d33}</style></head><body>
<h2>CRUD Category</h2><p><a href="${pageContext.request.contextPath}/home">Trang chủ</a> | <a class="btn" href="${pageContext.request.contextPath}/category/add">+ Thêm danh mục</a></p>
<table><tr><th>STT</th><th>Hình</th><th>Tên danh mục</th><th>Hành động</th></tr>
<c:forEach items="${cateList}" var="cate" varStatus="stt"><tr><td>${stt.index+1}</td><td><c:if test="${not empty cate.icon}"><img src="${cate.icon}"></c:if></td><td>${cate.name}</td><td><a href="${pageContext.request.contextPath}/category/edit?id=${cate.id}">Sửa</a> | <a class="danger" style="color:white;padding:4px 7px;text-decoration:none" onclick="return confirm('Xóa danh mục này?')" href="${pageContext.request.contextPath}/category/delete?id=${cate.id}">Xóa</a></td></tr></c:forEach>
</table></body></html>
