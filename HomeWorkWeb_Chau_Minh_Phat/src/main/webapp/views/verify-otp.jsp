<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Xác nhận OTP - BaitapWeb</title>
</head>
<body>
    <div class="row justify-content-center">
        <div class="col-12 col-md-6 col-lg-4">
            <div class="card shadow-sm border-0 rounded-3">
                <div class="card-body p-4 text-center">
                    <div class="mb-3 text-primary">
                        <i class="bi bi-shield-check display-4"></i>
                    </div>
                    <h3 class="card-title fw-bold mb-2">Xác nhận tài khoản</h3>
                    <p class="text-muted small mb-4">
                        Mã OTP gồm 6 chữ số đã được gửi tới email của bạn. Vui lòng nhập mã để hoàn tất kích hoạt.
                    </p>

                    <c:if test="${not empty alertMsg}">
                        <div class="alert alert-danger alert-dismissible fade show text-start" role="alert">
                            <i class="bi bi-exclamation-triangle me-1"></i> ${alertMsg}
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        </div>
                    </c:if>

                    <form action="${pageContext.request.contextPath}/verify-otp" method="post" class="needs-validation" novalidate>
                        <div class="mb-4">
                            <input type="text" class="form-control form-control-lg text-center fw-bold letter-spacing-2" 
                                   name="otp" id="otp" maxlength="6" placeholder="------" pattern="[0-9]{6}" required autofocus>
                            <div class="invalid-feedback text-start">Vui lòng nhập mã OTP gồm đúng 6 chữ số.</div>
                        </div>
                        <button type="submit" class="btn btn-primary w-100 py-2 fw-semibold">
                            <i class="bi bi-check-lg me-1"></i> Xác nhận OTP
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
