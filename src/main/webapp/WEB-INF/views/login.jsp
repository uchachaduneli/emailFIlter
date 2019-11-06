<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Authorization</title>
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
        app.controller("loginCtrl", function ($scope, $http, $location) {

            $scope.user = {};
            $scope.request = {};

            $scope.login = function () {
                function resFunc(res) {
                    if (res.errorCode == undefined) {
                        window.location.reload();
                    }
                }

                $scope.req = {username: $scope.user.username, password: $scope.user.password};
                ajaxCall($http, "login?username=" + $scope.user.username + "&password=" + $scope.user.password, null, resFunc);
            };

            $scope.sendRestoreMail = function () {
                function resFunc(res) {
                    if (res.errorCode == 0) {
                        successMsg('Password Sent, Check Email Please');
                        closeModal('editModal');
                    } else {
                        errorMsg('Operation Failed');
                    }
                    $scope.request = {};
                }

                ajaxCall($http, "restore-pass?username=" + $scope.request.username, null, resFunc);
            };

        });

    </script>

</head>
<body ng-app="app" class="hold-transition login-page">


<div class="login-box" data-role="none" ng-controller="loginCtrl">

    <div class="modal fade bs-example-modal-lg not-printable" id="editModal" role="dialog"
         aria-labelledby="editModalLabel"
         aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="editModalLabel">Restore Password</h4>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <form class="form-horizontal" name="ediFormName">
                            <div class="form-group col-sm-10 ">
                                <label class="control-label col-sm-3">Username</label>
                                <div class="col-sm-9">
                                    <input type="text" class="form-control" id="username" name="username"
                                           ng-model="request.username">
                                </div>
                            </div>
                            <div class="form-group col-sm-10"></div>
                            <div class="form-group col-sm-12 text-center">
                                <a class="btn btn-app" ng-click="sendRestoreMail()">
                                    <i class="fa fa-send"></i> Restore
                                </a>
                            </div>

                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="login-logo">
        <a href=""><img class="col-xs-12" src="resources/imgs/logo.jpg"></a>
    </div>
    <div class="login-box-body">
        <p class="login-box-msg">Sign in to start your session</p>
        <form>
            <div class="form-group has-feedback">
                <input type="text" class="form-control" id="username" name="username" ng-model="user.username"
                       placeholder="Username">
                <span class="fa fa-user form-control-feedback"></span>
            </div>
            <div class="form-group has-feedback">
                <input type="password" class="form-control" id="password" name="password" ng-model="user.password"
                       placeholder="Password">
                <span class="fa fa-key form-control-feedback"></span>
            </div>
            <br>
            <div class="form-group">
                <a class="btn btn-app btn-primary form-control" id="loginBtnId" style="margin: 0!important;"
                   ng-click="login()">
                    <i class="fa fa-sign-in"></i> L o g i n
                </a>
            </div>
            <a data-toggle="modal" data-target="#editModal" class="btn btn-xs">
                <i class="fa fa-key"></i>&nbsp;Forgot Password?
            </a>
        </form>
    </div>
</div>
</body>
</html>
