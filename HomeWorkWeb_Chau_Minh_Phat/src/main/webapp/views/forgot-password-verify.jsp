<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Xác nhận OTP đặt lại mật khẩu - BaitapWeb</title>
</head>
<body>
    <div class="row justify-content-center">
        <div class="col-12 col-md-6 col-lg-4">
            <div class="card shadow-sm border-0 rounded-3">
                <div class="card-body p-4 text-center">
                    <div class="mb-3 text-warning">
                        <i class="bi bi-shield-lock display-4"></i>
                    </div>
                    <h3 class="card-title fw-bold mb-2">Nhập mã OTP</h3>
                    <p class="text-muted small mb-4">
                        Mã OTP đặt lại mật khẩu đã được gửi đến email của bạn. Vui lòng nhập mã để xác thực.
                    </p>

                    <c:if test="${not empty alertMsg}">
                        <div class="alert alert-danger alert-dismissible fade show text-start" role="alert">
                            <i class="bi bi-exclamation-triangle me-1"></i> ${alertMsg}
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        </div>
                    </c:if>

                    <form action="${pageContext.request.contextPath}/forgot-password/verify" method="post" class="needs-validation" novalidate>
                        <div class="mb-4">
                            <input type="text" class="form-control form-control-lg text-center fw-bold letter-spacing-2" 
                                   name="otp" id="otp" maxlength="6" placeholder="------" pattern="[0-9]{6}" required autofocus>
                            <div class="invalid-feedback text-start">Vui lòng nhập đúng 6 chữ số OTP.</div>
                        </div>
                        <button type="submit" class="btn btn-warning text-dark w-100 py-2 fw-semibold">
                            <i class="bi bi-shield-check me-1"></i> Xác nhận OTP
                        </button>
                    </form>

                    <div class="mt-3">
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
