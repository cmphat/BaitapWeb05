<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>404 - Không tìm thấy trang</title>
</head>
<body>
    <div class="row justify-content-center text-center py-5">
        <div class="col-md-8 col-lg-6">
            <div class="card shadow-sm border-0 p-4">
                <div class="text-secondary mb-3">
                    <i class="bi bi-question-circle fs-1" style="font-size: 4rem;"></i>
                </div>
                <h1 class="h2 fw-bold text-dark mb-2">404 - Không tìm thấy trang</h1>
                <p class="text-muted mb-4">
                    Đường dẫn bạn yêu cầu không tồn tại hoặc đã bị thay đổi.
                </p>
                <div class="d-flex justify-content-center gap-2">
                    <a href="${pageContext.request.contextPath}/home" class="btn btn-primary">
                        <i class="bi bi-house-door me-1"></i> Quay về trang chủ
                    </a>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
