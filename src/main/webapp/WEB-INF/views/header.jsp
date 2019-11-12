<%@ page import="com.email.filter.dto.UsersDTO" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    boolean superAdmin = ((Integer) session.getAttribute("typeId") != null && (Integer) session.getAttribute("typeId") == UsersDTO.SUPER_ADMIN);
    boolean operator = ((Integer) session.getAttribute("typeId") != null && (Integer) session.getAttribute("typeId") == UsersDTO.OPERATOR);
    boolean admin = ((Integer) session.getAttribute("typeId") != null && (Integer) session.getAttribute("typeId") == UsersDTO.ADMIN);
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <base href="${pageContext.request.contextPath}/"/>
    <title>Email FIlter</title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <link rel="stylesheet" href="resources/css/bootstrap.css">
    <link rel="stylesheet" href="resources/css/font-awesome.css">
    <link rel="stylesheet" href="resources/css/AdminLTE.css">
    <link rel="stylesheet" href="resources/css/dataTables.bootstrap.css">
    <link rel="stylesheet" href="resources/css/skin-blue-light.css">
    <link rel="stylesheet" href="resources/css/global.css">
    <link rel="stylesheet" href="resources/css/bootstrap-select.css">
    <link rel="stylesheet" href="resources/css/bootstrap-datepicker.css">
    <link rel="stylesheet" href="resources/css/bootstrap-datetimepicker.css">
    <link rel="stylesheet" href="resources/css/ionicons.min.css">
    <link rel="shortcut icon" type="image/png" href="resources/imgs/logo.jpg"/>

    <script src="resources/js/jquery.js"></script>
    <script src="resources/js/jquery-ui.js"></script>
    <script src="resources/js/bootstrap-select.js"></script>
    <script>
        $.widget.bridge('uibutton', $.ui.button);
    </script>
    <script src="resources/js/moment.js"></script>
    <script src="resources/js/bootstrap.js"></script>
    <script src="resources/js/bootstrap-datepicker.js"></script>
    <script src="resources/js/bootstrap-datetimepicker.js"></script>
    <script src="resources/js/jquery.bootstrap-growl.min.js"></script>
    <script src="resources/js/adminlte.js"></script>
    <script src="resources/js/angular.js"></script>
    <script src="resources/js/global_util.js"></script>
    <script src="resources/js/growlMessages.js"></script>
    <script src="resources/js/requireds.js"></script>
    <script src="resources/js/checklist-model.js"></script>
    <script src="resources/js/ng-file-upload-shim.js"></script>
    <script src="resources/js/ng-file-upload.js"></script>

    <script>
        $(document).ready(function () {

            $(".datepicker").datepicker();
            var url = window.location;
            $('.menuItem').filter(function () {
                return this.href.indexOf(url.pathname) > -1;
            }).addClass('active');
            if (url.pathname.indexOf("emails") > -1) {
                $('#selected_item').text("Emails");
            } else if (url.pathname.indexOf("filters") > -1) {
                $('#selected_item').text("Filters");
            }


            $('.srch').keypress(function (e) {
                var key = e.which;
                if (key == 13) {
                    $('#srchBtnId').click();
                    return false;
                }
            });

            $('.dateInput').datepicker({
                format: "dd/mm/yyyy",
                autoclose: true,
            }).on('changeDate', function (ev) {
            });

            $('input').attr('autocomplete', 'off');

            $(function () {
                $('[data-toggle="popover"]').popover()
            });
        });

        var app = angular.module("app", ["checklist-model"]);
        app.controller("profileCtrl", function ($scope, $http, $location) {
            var absUrl = $location.absUrl();
            $scope.uri = "";
            if (absUrl.split("?")[1]) {
                $scope.uri = absUrl.split("?")[1].split("=")[1];
            }

            $scope.changePassword = function () {
                function resFunc(res) {
                    if (res.errorCode == 0) {
                        successMsg('Operation Successfull');
                        closeModal('dropdown');
                    } else {
                        errorMsg('Operation Failed');
                    }
                    $scope.newpass = {};
                }

                ajaxCall($http, "users/change-password?pass=" + $scope.newpass.password + "&newpass=" + $scope.newpass.newpassword, null, resFunc);
            };
        });
    </script>
</head>
<body ng-app="app" class="hold-transition skin-blue-light sidebar-mini">
<div class="wrapper">
    <header class="main-header">
        <a href="" class="logo">
            <span class="logo-lg"><b>Email Filter</b></span>
        </a>
        <nav class="navbar navbar-static-top">
            <a href="#" class="sidebar-toggle" data-toggle="push-menu" role="button">
                <span class="sr-only">Collapse Menu</span>
            </a>

            <div class="navbar-custom-menu" ng-controller="profileCtrl">
                <ul class="nav navbar-nav">
                    <li class="dropdown user user-menu">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                            <i class="fa fa-user"></i>
                            <span class="hidden-xs"><%= session.getAttribute("userDesc") %></span>
                        </a>
                        <ul class="dropdown-menu">
                            <li class="user-header">
                                <p>
                                    <%= session.getAttribute("userDesc") %>
                                    <small><%= session.getAttribute("typeName") %>
                                    </small>
                                </p>
                            </li>
                            <li class="user-body text-center">
                                <div class=" form-group has-feedback">
                                    <input type="password" name="password" placeholder="Current Password"
                                           ng-model="newpass.password"
                                           class="form-control">
                                    <span class="fa fa-key form-control-feedback"></span>
                                </div>
                                <div class="form-group has-feedback">
                                    <input type="password" name="password" placeholder="New Password"
                                           ng-model="newpass.newpassword"
                                           class="form-control">
                                    <span class="fa fa-key form-control-feedback"></span>
                                </div>
                            </li>
                            <li class="user-footer">
                                <div class="pull-left">
                                    <a href="" ng-click="changePassword()" class="btn btn-default btn-flat">Save</a>
                                </div>
                                <div class="pull-right">
                                    <a href="logout" class="btn btn-default btn-flat">Log Out</a>
                                </div>
                            </li>
                        </ul>
                    </li>
                    <li title="Log Out">
                        <a href="logout"><i class="fa fa-sign-out"></i></a>
                    </li>
                </ul>
            </div>
        </nav>
    </header>
    <div class="row" class="main-sidebar">
        <aside class="main-sidebar">
            <section class="sidebar">
                <img src="resources/imgs/logo.jpg" style="width: 80%; margin-top: 10%; margin-left: 10%"/>
                <hr/>
                <ul class="sidebar-menu" data-widget="tree">
                    <li>
                        <a class="menuItem" href="emails">
                            <i class="fa fa-envelope"></i>
                            <span>Inbox</span>
                        </a>
                    </li>
                    <li>
                        <a class="menuItem" href="spam">
                            <i class="fa fa-exclamation-circle"></i>
                            <span>My Spam</span>
                        </a>
                    </li>
                    <li>
                        <a class="menuItem" href="filters">
                            <i class="fa fa-filter"></i>
                            <span>Filters</span>
                        </a>
                    </li>
                    <li>
                        <a href="users">
                            <i class="fa fa-users"></i>
                            <span>Users</span>
                        </a>
                    </li>
                </ul>
            </section>
        </aside>
    </div>

    <div class="content-wrapper" ng-controller="angController">
        <section class="content-header text-center">
            <h4 id="selected_item"></h4>
        </section>
        <section class="content">
            <input type="hidden" id="userId" value="<%=request.getSession().getAttribute("userId")%>">
            <div class="modal fade bs-example-modal-lg not-printable" id="loadingModal"
                 role="dialog" aria-labelledby="loadingModalLabel" aria-hidden="true">
                <div class="modal-dialog" style="height: 80%; width: 120px;">
                    <div class="loader" style="margin-top: 80%"></div>
                </div>
            </div>
