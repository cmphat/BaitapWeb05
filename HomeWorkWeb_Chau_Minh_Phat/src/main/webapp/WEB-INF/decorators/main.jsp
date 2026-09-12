<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><sitemesh:write property="title">BaitapWeb - Bài tập Web</sitemesh:write></title>
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
    </style>
    <sitemesh:write property="head"/>
</head>
<body>

    <!-- Shared Navbar -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary shadow-sm">
        <div class="container">
            <a class="navbar-brand fw-bold" href="${pageContext.request.contextPath}/home">
                <i class="bi bi-box-seam me-1"></i> BaitapWeb
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#mainNavbar" aria-controls="mainNavbar" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="mainNavbar">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/home">
                            <i class="bi bi-house-door"></i> Home
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/product">
                            <i class="bi bi-grid"></i> Products
                        </a>
                    </li>
                    <c:if test="${not empty sessionScope.account}">
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="adminDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                <i class="bi bi-gear"></i> Admin Management
                            </a>
                            <ul class="dropdown-menu" aria-labelledby="adminDropdown">
                                <li>
                                    <a class="dropdown-item" href="${pageContext.request.contextPath}/admin/categories">
                                        <i class="bi bi-tags"></i> Categories
                                    </a>
                                </li>
                                <li>
                                    <a class="dropdown-item" href="${pageContext.request.contextPath}/admin/products">
                                        <i class="bi bi-box"></i> Products
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
                                <a class="nav-link text-white" href="${pageContext.request.contextPath}/profile">
                                    <i class="bi bi-person-circle"></i>
                                    <span class="fw-semibold">
                                        <c:out value="${sessionScope.account.fullname != null ? sessionScope.account.fullname : sessionScope.account.username}"/>
                                    </span>
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="btn btn-outline-light btn-sm" href="${pageContext.request.contextPath}/logout">
                                    <i class="bi bi-box-arrow-right"></i> Logout
                                </a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="nav-item me-2">
                                <a class="nav-link" href="${pageContext.request.contextPath}/login">
                                    <i class="bi bi-box-arrow-in-right"></i> Login
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="btn btn-light btn-sm text-primary fw-semibold" href="${pageContext.request.contextPath}/register">
                                    <i class="bi bi-person-plus"></i> Register
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
            <sitemesh:write property="body"/>
        </div>
    </main>

    <!-- Shared Footer -->
    <footer class="footer bg-white text-center text-muted py-3 border-top mt-auto">
        <div class="container">
            <small>&copy; 2026 Exercise / BaitapWeb - HomeWorkWeb. Built with Bootstrap 5 &amp; SiteMesh 3.</small>
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
