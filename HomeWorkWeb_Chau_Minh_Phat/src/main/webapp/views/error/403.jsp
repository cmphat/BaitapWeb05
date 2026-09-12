<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>403 - Không có quyền truy cập</title>
</head>
<body>
    <div class="row justify-content-center text-center py-5">
        <div class="col-md-8 col-lg-6">
            <div class="card shadow-sm border-0 p-4">
                <div class="text-danger mb-3">
                    <i class="bi bi-shield-slash fs-1" style="font-size: 4rem;"></i>
                </div>
                <h1 class="h2 fw-bold text-danger mb-2">403 - Quyền truy cập bị từ chối</h1>
                <p class="text-muted mb-4">
                    Tài khoản của bạn không có quyền truy cập vào khu vực quản trị này (Role ADMIN).
                    Vui lòng liên hệ quản trị viên hoặc đăng nhập bằng tài khoản quản trị.
                </p>
                <div class="d-flex justify-content-center gap-2">
                    <a href="${pageContext.request.contextPath}/home" class="btn btn-outline-primary">
                        <i class="bi bi-house-door me-1"></i> Về trang chủ
                    </a>
                    <a href="${pageContext.request.contextPath}/login" class="btn btn-primary">
                        <i class="bi bi-box-arrow-in-right me-1"></i> Đăng nhập tài khoản khác
                    </a>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
