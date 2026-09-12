<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quên mật khẩu - BaitapWeb</title>
</head>
<body>
    <div class="row justify-content-center">
        <div class="col-12 col-md-6 col-lg-4">
            <div class="card shadow-sm border-0 rounded-3">
                <div class="card-body p-4">
                    <h3 class="card-title text-center fw-bold mb-3 text-primary">
                        <i class="bi bi-key me-1"></i> Quên mật khẩu
                    </h3>
                    <p class="text-muted small text-center mb-4">
                        Nhập địa chỉ email của bạn. Chúng tôi sẽ gửi mã OTP để giúp bạn đặt lại mật khẩu.
                    </p>

                    <c:if test="${not empty alertMsg}">
                        <div class="alert alert-danger alert-dismissible fade show" role="alert">
                            <i class="bi bi-exclamation-triangle me-1"></i> ${alertMsg}
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        </div>
                    </c:if>

                    <form action="${pageContext.request.contextPath}/forgot-password" method="post" class="needs-validation" novalidate>
                        <div class="mb-3">
                            <label for="email" class="form-label">Địa chỉ Email <span class="text-danger">*</span></label>
                            <input type="email" class="form-control" id="email" name="email" 
                                   placeholder="example@domain.com" value="${not empty email ? email : param.email}" required autofocus>
                            <div class="invalid-feedback">Vui lòng nhập email hợp lệ.</div>
                        </div>
                        <button type="submit" class="btn btn-primary w-100 py-2 fw-semibold">
                            <i class="bi bi-send me-1"></i> Gửi mã OTP
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
