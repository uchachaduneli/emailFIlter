<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Password Restoration</title>
    <link rel="stylesheet" href="resources/css/bootstrap.css">
    <link rel="stylesheet" href="resources/css/font-awesome.css">
    <link rel="stylesheet" href="resources/css/AdminLTE.css">
    <link rel="stylesheet" href="resources/css/global.css">
    <link rel="shortcut icon" type="image/png" href="resources/imgs/logo.jpg"/>

    <script src="resources/js/jquery.js"></script>
    <script src="resources/js/bootstrap.js"></script>
    <script src="resources/js/global_util.js"></script>
    <script src="resources/js/growlMessages.js"></script>
    <script src="resources/js/jquery.bootstrap-growl.min.js"></script>
    <script src="resources/js/angular.js"></script>

    <script type="text/javascript">
        $(document).keypress(function (e) {
            if (e.which == 13) {
                document.getElementById("loginBtnId").click();
            }
        });

        var app = angular.module("app", []);
        app.controller("loginCtrl", function ($scope, $http, $location, $window) {

            $scope.user = {};

            $scope.restore = function () {

                if ($scope.user.passwordConfirm != $scope.user.password) {
                    errorMsg("New Password Confirm Error");
                    return;
                }
                $('#loadingModal').modal('show');

                function resFunc(res) {
                    if (res.errorCode == 0) {
                        $window.location.href = "/emailFilter/login";
                    }
                    $('#loadingModal').modal('hide');
                }

                ajaxCall($http, "restore-password?oneTimePass=" + $scope.user.oneTimePass + "&newpass=" + $scope.user.password, null, resFunc);
            };

        });

    </script>

</head>
<body ng-app="app" class="hold-transition login-page">
<div class="login-box" data-role="none" ng-controller="loginCtrl">
    <div class="modal fade bs-example-modal-lg not-printable" id="loadingModal"
         role="dialog" aria-labelledby="loadingModalLabel" aria-hidden="true">
        <div class="modal-dialog" style="height: 80%; width: 120px;">
            <div class="loader" style="margin-top: 80%"></div>
        </div>
    </div>
    <div class="login-box-body">
        <p class="login-box-msg">Enter One Time Password from email</p>
        <form>
            <div class="form-group has-feedback">
                <input type="text" class="form-control" ng-model="user.oneTimePass"
                       placeholder="One Time Password">
                <span class="fa fa-user form-control-feedback"></span>
            </div>
            <div class="form-group has-feedback">
                <input type="password" class="form-control" ng-model="user.password"
                       placeholder="Password">
                <span class="fa fa-key form-control-feedback"></span>
            </div>
            <div class="form-group has-feedback">
                <input type="password" class="form-control" ng-model="user.passwordConfirm"
                       placeholder="Confirm Password">
                <span class="fa fa-key form-control-feedback"></span>
            </div>
            <br>
            <div class="form-group">
                <a class="btn btn-app btn-primary form-control" id="loginBtnId" style="margin: 0!important;"
                   ng-click="restore()">
                    <i class="fa fa-sign-in"></i> Save
                </a>
            </div>
        </form>
    </div>
</div>
</body>
</html>
