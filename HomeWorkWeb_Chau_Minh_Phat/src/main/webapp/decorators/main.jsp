<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><%out.print("<sitemesh:write property=\"title\"/>");%></title>
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" rel="stylesheet">
    <style>
        body {
            min-height: 100vh;
            display: flex;
            flex-direction: column;
            background-color: #f8f9fa;
        }
        .main-content {
            flex: 1 0 auto;
        }
        .footer {
            flex-shrink: 0;
        }
        .navbar-brand {
            letter-spacing: 0.5px;
        }
    </style>
    <%out.print("<sitemesh:write property=\"head\"/>");%>
</head>
<body>

    <!-- Shared Navbar -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary shadow-sm sticky-top">
        <div class="container">
            <a class="navbar-brand fw-bold" href="${pageContext.request.contextPath}/home">
                <i class="bi bi-box-seam me-1"></i> BaitapWeb 05
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#mainNavbar" aria-controls="mainNavbar" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="mainNavbar">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/home">
                            <i class="bi bi-house-door"></i> Trang chủ
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/product">
                            <i class="bi bi-grid"></i> Sản phẩm
                        </a>
                    </li>
                    <c:if test="${not empty sessionScope.account && sessionScope.account.roleid == 1}">
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle text-warning fw-semibold" href="#" id="adminDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                <i class="bi bi-shield-lock-fill"></i> Quản trị Admin
                            </a>
                            <ul class="dropdown-menu shadow-sm" aria-labelledby="adminDropdown">
                                <li>
                                    <a class="dropdown-item" href="${pageContext.request.contextPath}/admin">
                                        <i class="bi bi-speedometer2 text-primary me-2"></i> Bảng điều khiển (Dashboard)
                                    </a>
                                </li>
                                <li><hr class="dropdown-divider"></li>
                                <li>
                                    <a class="dropdown-item" href="${pageContext.request.contextPath}/admin/categories">
                                        <i class="bi bi-tags text-success me-2"></i> Quản lý danh mục
                                    </a>
                                </li>
                                <li>
                                    <a class="dropdown-item" href="${pageContext.request.contextPath}/admin/users">
                                        <i class="bi bi-people text-info me-2"></i> Quản lý người dùng
                                    </a>
                                </li>
                                <li>
                                    <a class="dropdown-item" href="${pageContext.request.contextPath}/admin/products">
                                        <i class="bi bi-box-seam text-warning me-2"></i> Quản lý sản phẩm
                                    </a>
                                </li>
                            </ul>
                        </li>
                    </c:if>
                </ul>

                <!-- Session User State -->
                <ul class="navbar-nav ms-auto mb-2 mb-lg-0 align-items-center">
                    <c:choose>
                        <c:when test="${not empty sessionScope.account}">
                            <li class="nav-item me-2">
                                <a class="nav-link text-white d-flex align-items-center" href="${pageContext.request.contextPath}/profile">
                                    <i class="bi bi-person-circle fs-5 me-1"></i>
                                    <span class="fw-semibold me-1">
                                        <c:out value="${sessionScope.account.fullname != null ? sessionScope.account.fullname : sessionScope.account.username}"/>
                                    </span>
                                    <c:choose>
                                        <c:when test="${sessionScope.account.roleid == 1}">
                                            <span class="badge bg-danger rounded-pill ms-1">Admin</span>
                                        </c:when>
                                        <c:when test="${sessionScope.account.roleid == 2}">
                                            <span class="badge bg-warning text-dark rounded-pill ms-1">Manager</span>
                                        </c:when>
                                    </c:choose>
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="btn btn-outline-light btn-sm" href="${pageContext.request.contextPath}/logout">
                                    <i class="bi bi-box-arrow-right"></i> Đăng xuất
                                </a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="nav-item me-2">
                                <a class="nav-link" href="${pageContext.request.contextPath}/login">
                                    <i class="bi bi-box-arrow-in-right"></i> Đăng nhập
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="btn btn-light btn-sm text-primary fw-semibold" href="${pageContext.request.contextPath}/register">
                                    <i class="bi bi-person-plus"></i> Đăng ký
                                </a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </div>
        </div>
    </nav>

    <!-- Main Content Container -->
    <main class="main-content py-4">
        <div class="container">
            <%out.print("<sitemesh:write property=\"body\"/>");%>
        </div>
    </main>

    <!-- Shared Footer -->
    <footer class="footer bg-white text-center text-muted py-3 border-top mt-auto">
        <div class="container">
            <small>&copy; 2026 BaitapWeb05 - Spring Boot 4 + JSP + SiteMesh 3 + Bootstrap 5. Developed by Chau Minh Phat.</small>
        </div>
    </footer>

    <!-- Bootstrap 5 Bundle JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

    <!-- Global Client-side Bootstrap Validation -->
    <script>
        (function () {
            'use strict';
            var forms = document.querySelectorAll('.needs-validation');
            Array.prototype.slice.call(forms).forEach(function (form) {
                form.addEventListener('submit', function (event) {
                    if (!form.checkValidity()) {
                        event.preventDefault();
                        event.stopPropagation();
                    }
                    form.classList.add('was-validated');
                }, false);
            });
        })();
    </script>
</body>
</html>
