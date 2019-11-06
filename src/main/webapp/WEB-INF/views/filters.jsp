<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="header.jsp" %>

<script>
    app.controller("angController", function ($scope, $http, $filter) {
        $scope.filters = [];
        $scope.filterTypes = [];
        $scope.request = {};

        $scope.loadMainData = function () {
            $('#loadingModal').modal('hide');

            function getFilters(res) {
                $scope.filters = res.data;
                $('#loadingModal').modal('hide');
            }

            ajaxCall($http, "filters/get-filter", null, getFilters);
        }

        $scope.loadMainData();

        $scope.remove = function (id) {
            if (confirm("Confirm Filter deletion")) {
                if (id != undefined) {
                    function resFnc(res) {
                        if (res.errorCode == 0) {
                            successMsg('Operation Successful');
                            $scope.loadMainData();
                        }
                    }

                    ajaxCall($http, "filters/delete-filter?id=" + id, null, resFnc);
                }
            }
        };

        $scope.edit = function (id) {
            if (id != undefined) {
                var selected = $filter('filter')($scope.filters, {id: id}, true);
                $scope.request = selected[0];
            }
        };


        $scope.init = function () {
            $scope.request = {};
        };

        $scope.save = function () {
            function resFunc(res) {
                if (res.errorCode == 0) {
                    successMsg('Operation Successful');
                    $scope.loadMainData();
                    closeModal('editModal');
                } else {
                    errorMsg('Operation Failed');
                }
            }

            $scope.req = {};

            $scope.req.id = $scope.request.id;
            $scope.req.desc = $scope.request.desc;
            $scope.req.typeId = $scope.request.typeId;

            console.log($scope.req);
            ajaxCall($http, "filters/save", angular.toJson($scope.req), resFunc);
        };

        function getFilterTypes(res) {
            $scope.filterTypes = res.data;
        }

        ajaxCall($http, "filters/get-filter-types", null, getFilterTypes);

    });
</script>


<div class="modal fade bs-example-modal-lg" id="editModal" role="dialog" aria-labelledby="editModalLabel"
     aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="editModalLabel">Filter Information</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <form class="form-horizontal" name="myForm">
                        <div class="form-group col-sm-10 ">
                            <label class="control-label col-sm-3">Desc</label>
                            <div class="col-sm-9">
                                <input type="text" ng-model="request.desc"
                                       class="form-control input-sm">
                            </div>
                        </div>
                        <div class="form-group col-sm-10">
                            <label class="control-label col-sm-3">Level</label>
                            <div class="col-xs-9 btn-group">
                                <select class="form-control" ng-model="request.typeId">
                                    <option ng-repeat="s in filterTypes"
                                            ng-selected="s.id === request.typeId"
                                            ng-value="s.id">{{s.name}}
                                    </option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group col-sm-10"></div>
                        <div class="form-group col-sm-10"></div>
                        <div class="form-group col-sm-12 text-center">
                            <a class="btn btn-app" ng-click="save()">
                                <i class="fa fa-save"></i> Save
                            </a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>


<div class="row">
    <div class="col-xs-12">
        <div class="box">
            <div class="box-header">
                <div class="col-md-2">
                    <button type="button" class="btn btn-block btn-primary btn-md" ng-click="init()" data-toggle="modal"
                            data-target="#editModal">
                        <i class="fa fa-plus" aria-hidden="true"></i> &nbsp;
                        Add Filter
                    </button>
                </div>
            </div>
            <!-- /.box-header -->
            <div class="box-body">
                <table class="table table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Desc</th>
                        <th>Type</th>
                        <th class="col-md-1 text-center">Create Date</th>
                        <th class="col-md-1 text-center">Update Date</th>
                        <th class="col-md-2 text-center">Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr ng-repeat="r in filters">
                        <td>{{r.id}}</td>
                        <td>{{r.desc}}</td>
                        <td>{{r.type.name}}</td>
                        <td class="text-center">
                            <small>{{r.createDate}}</small>
                        </td>
                        <td class="text-center">
                            <small>{{r.updateDate}}</small>
                        </td>
                        <td class="text-center">
                            <a ng-click="edit(r.id)" data-toggle="modal" data-target="#editModal"
                               class="btn btn-xs">
                                <i class="fa fa-pencil"></i>&nbsp;Edit
                            </a>&nbsp;|&nbsp;
                            <a ng-click="remove(r.id)" class="btn btn-xs">
                                <i class="fa fa-trash-o"></i>&nbsp;Remove
                            </a>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<%@include file="footer.jsp" %>