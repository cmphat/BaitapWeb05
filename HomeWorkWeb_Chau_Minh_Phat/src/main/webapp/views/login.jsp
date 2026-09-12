<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Đăng nhập - BaitapWeb</title>
</head>
<body>
    <div class="row justify-content-center">
        <div class="col-12 col-md-6 col-lg-4">
            <div class="card shadow-sm border-0 rounded-3">
                <div class="card-body p-4">
                    <h3 class="card-title text-center fw-bold mb-4 text-primary">
                        <i class="bi bi-box-arrow-in-right me-1"></i> Đăng nhập
                    </h3>

                    <c:if test="${not empty successMsg}">
                        <div class="alert alert-success alert-dismissible fade show" role="alert">
                            <i class="bi bi-check-circle me-1"></i> ${successMsg}
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        </div>
                    </c:if>

                    <c:if test="${not empty alert}">
                        <div class="alert alert-danger alert-dismissible fade show" role="alert">
                            <i class="bi bi-exclamation-triangle me-1"></i> ${alert}
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        </div>
                    </c:if>

                    <form action="${pageContext.request.contextPath}/login" method="post" class="needs-validation" novalidate>
                        <div class="mb-3">
                            <label for="username" class="form-label">Tài khoản <span class="text-danger">*</span></label>
                            <input type="text" class="form-control" id="username" name="username" 
                                   placeholder="Nhập username" value="${empty username ? rememberedUsername : username}" required>
                            <div class="invalid-feedback">Vui lòng nhập tên đăng nhập.</div>
                        </div>
                        <div class="mb-3">
                            <label for="password" class="form-label">Mật khẩu <span class="text-danger">*</span></label>
                            <input type="password" class="form-control" id="password" name="password" 
                                   placeholder="Nhập mật khẩu" required>
                            <div class="invalid-feedback">Vui lòng nhập mật khẩu.</div>
                        </div>
                        <div class="mb-3 form-check">
                            <input type="checkbox" class="form-check-input" id="remember" name="remember">
                            <label class="form-check-label text-muted" for="remember">Nhớ tài khoản bằng Cookie</label>
                        </div>
                        <button type="submit" class="btn btn-primary w-100 py-2 fw-semibold">
                            <i class="bi bi-box-arrow-in-right me-1"></i> Đăng nhập
                        </button>
                    </form>

                    <div class="d-flex justify-content-between mt-3 text-center">
                        <a href="${pageContext.request.contextPath}/register" class="text-decoration-none">
                            <i class="bi bi-person-plus me-1"></i> Đăng ký
                        </a>
                        <a href="${pageContext.request.contextPath}/forgot-password" class="text-decoration-none text-muted">
                            Quên mật khẩu?
                        </a>
                    </div>

                    <hr class="my-3">
                    <div class="text-center text-muted small">
                        Tài khoản mẫu: <strong>admin</strong> / <strong>123</strong>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
