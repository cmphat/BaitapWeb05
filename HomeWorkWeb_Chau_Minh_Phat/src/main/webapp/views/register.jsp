<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Đăng ký tài khoản - BaitapWeb</title>
</head>
<body>
    <div class="row justify-content-center">
        <div class="col-12 col-md-8 col-lg-5">
            <div class="card shadow-sm border-0 rounded-3">
                <div class="card-body p-4">
                    <h3 class="card-title text-center fw-bold mb-4 text-success">
                        <i class="bi bi-person-plus me-1"></i> Đăng ký tài khoản
                    </h3>

                    <c:if test="${not empty alertMsg}">
                        <div class="alert alert-danger alert-dismissible fade show" role="alert">
                            <i class="bi bi-exclamation-triangle me-1"></i> ${alertMsg}
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        </div>
                    </c:if>

                    <form action="${pageContext.request.contextPath}/register" method="post" class="needs-validation" novalidate id="registerForm">
                        <div class="mb-3">
                            <label for="username" class="form-label">Tên đăng nhập <span class="text-danger">*</span></label>
                            <input type="text" class="form-control" id="username" name="username" 
                                   value="${not empty username ? username : param.username}" minlength="3" placeholder="Nhập tên đăng nhập (ít nhất 3 ký tự)" required>
                            <div class="invalid-feedback">Tên đăng nhập tối thiểu 3 ký tự.</div>
                        </div>
                        <div class="mb-3">
                            <label for="email" class="form-label">Email <span class="text-danger">*</span></label>
                            <input type="email" class="form-control" id="email" name="email" 
                                   value="${not empty email ? email : param.email}" placeholder="example@domain.com" required>
                            <div class="invalid-feedback">Vui lòng nhập địa chỉ email hợp lệ.</div>
                        </div>
                        <div class="mb-3">
                            <label for="password" class="form-label">Mật khẩu <span class="text-danger">*</span></label>
                            <input type="password" class="form-control" id="password" name="password" 
                                   minlength="6" placeholder="Tối thiểu 6 ký tự" required>
                            <div class="invalid-feedback">Mật khẩu tối thiểu 6 ký tự.</div>
                        </div>
                        <div class="mb-3">
                            <label for="fullname" class="form-label">Họ và tên <span class="text-danger">*</span></label>
                            <input type="text" class="form-control" id="fullname" name="fullname" 
                                   value="${not empty fullname ? fullname : param.fullname}" placeholder="Nguyễn Văn A" required>
                            <div class="invalid-feedback">Vui lòng nhập họ và tên.</div>
                        </div>
                        <div class="mb-3">
                            <label for="phone" class="form-label">Số điện thoại</label>
                            <input type="tel" class="form-control" id="phone" name="phone" 
                                   pattern="0[0-9]{9,10}" value="${not empty phone ? phone : param.phone}" placeholder="VD: 0901234567">
                            <div class="invalid-feedback">Số điện thoại gồm 10-11 chữ số và bắt đầu bằng 0.</div>
                        </div>

                        <button type="submit" class="btn btn-success w-100 py-2 fw-semibold">
                            <i class="bi bi-check2-circle me-1"></i> Đăng ký ngay
                        </button>
                    </form>

                    <div class="text-center mt-3">
                        <span class="text-muted">Đã có tài khoản?</span>
                        <a href="${pageContext.request.contextPath}/login" class="text-decoration-none fw-semibold">
                            Đăng nhập
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
