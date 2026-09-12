<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Đặt lại mật khẩu - BaitapWeb</title>
</head>
<body>
    <div class="row justify-content-center">
        <div class="col-12 col-md-6 col-lg-4">
            <div class="card shadow-sm border-0 rounded-3">
                <div class="card-body p-4">
                    <h3 class="card-title text-center fw-bold mb-4 text-primary">
                        <i class="bi bi-shield-lock me-1"></i> Đặt lại mật khẩu
                    </h3>

                    <c:if test="${not empty alertMsg}">
                        <div class="alert alert-danger alert-dismissible fade show" role="alert">
                            <i class="bi bi-exclamation-triangle me-1"></i> ${alertMsg}
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        </div>
                    </c:if>

                    <form action="${pageContext.request.contextPath}/reset-password" method="post" class="needs-validation" novalidate>
                        <div class="mb-3">
                            <label for="newPassword" class="form-label">Mật khẩu mới <span class="text-danger">*</span></label>
                            <input type="password" class="form-control" id="newPassword" name="newPassword" 
                                   minlength="6" placeholder="Tối thiểu 6 ký tự" required autofocus>
                            <div class="invalid-feedback">Mật khẩu mới tối thiểu 6 ký tự.</div>
                        </div>
                        <div class="mb-3">
                            <label for="confirmPassword" class="form-label">Xác nhận mật khẩu mới <span class="text-danger">*</span></label>
                            <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" 
                                   minlength="6" placeholder="Nhập lại mật khẩu mới" required>
                            <div class="invalid-feedback">Vui lòng nhập lại mật khẩu mới.</div>
                        </div>
                        <button type="submit" class="btn btn-primary w-100 py-2 fw-semibold">
                            <i class="bi bi-check2-circle me-1"></i> Đổi mật khẩu
                        </button>
                    </form>

                    <div class="text-center mt-3">
                        <a href="${pageContext.request.contextPath}/login" class="text-decoration-none small text-muted">
                            &laquo; Quay lại đăng nhập
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
