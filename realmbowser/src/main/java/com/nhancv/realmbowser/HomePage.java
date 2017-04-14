package com.nhancv.realmbowser;

/**
 * Created by nhancao on 4/14/17.
 */

public class HomePage {

    public static String content = "<!DOCTYPE html>\n" +
                                   "<html ng-app=\"myApp\">\n" +
                                   "<head>\n" +
                                   "    <meta charset=\"utf-8\">\n" +
                                   "    <title>Realm Browser</title>\n" +
                                   "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                                   "    <script src=\"https://ajax.googleapis.com/ajax/libs/angularjs/1.6.4/angular.min.js\"></script>\n" +
                                   "    <!-- Latest compiled and minified CSS -->\n" +
                                   "    <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\">\n" +
                                   "    <!-- jQuery library -->\n" +
                                   "    <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js\"></script>\n" +
                                   "    <!-- Latest compiled JavaScript -->\n" +
                                   "    <script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js\"></script>\n" +
                                   "    <style>\n" +
                                   "        /* Set height of the grid so .sidenav can be 100% (adjust if needed) */\n" +
                                   "        .row.content {\n" +
                                   "            height: 680px\n" +
                                   "        }\n" +
                                   "\n" +
                                   "        /* Set gray background color and 100% height */\n" +
                                   "        .sidenav {\n" +
                                   "            background-color: #f1f1f1;\n" +
                                   "            height: 100%;\n" +
                                   "        }\n" +
                                   "\n" +
                                   "        /* Set black background color, white text and some padding */\n" +
                                   "        footer {\n" +
                                   "            background-color: #555;\n" +
                                   "            color: white;\n" +
                                   "            padding: 15px;\n" +
                                   "        }\n" +
                                   "\n" +
                                   "        /* On small screens, set height to 'auto' for sidenav and grid */\n" +
                                   "        @media screen and (max-width: 767px) {\n" +
                                   "            .sidenav {\n" +
                                   "                height: auto;\n" +
                                   "                padding: 15px;\n" +
                                   "            }\n" +
                                   "\n" +
                                   "            .row.content {\n" +
                                   "                height: auto;\n" +
                                   "            }\n" +
                                   "        }\n" +
                                   "\n" +
                                   "    </style>\n" +
                                   "</head>\n" +
                                   "<body ng-controller=\"myCtrl\">\n" +
                                   "\n" +
                                   "<div class=\"container-fluid\">\n" +
                                   "    <div class=\"row content\">\n" +
                                   "        <div class=\"col-sm-3 sidenav\">\n" +
                                   "            <h4>Tables - <a href=\"/api/list\" target=\"_blank\">ApiList</a></h4>\n" +
                                   "            <div class=\"input-group\">\n" +
                                   "                <input type=\"text\" class=\"form-control\" ng-model=\"searchTable\" placeholder=\"Search table...\">\n" +
                                   "                <span class=\"input-group-btn\">\n" +
                                   "                  <button disabled class=\"btn btn-default\" type=\"button\">\n" +
                                   "                    <span class=\"glyphicon glyphicon-search\"></span>\n" +
                                   "                  </button>\n" +
                                   "                </span>\n" +
                                   "            </div>\n" +
                                   "            <hr>\n" +
                                   "            <ul class=\"nav nav-pills nav-stacked\">\n" +
                                   "                <li ng-repeat=\"table in tables | filter: searchTable\" ng-class=\"activeTable==$index ? 'active':''\"\n" +
                                   "                    style=\"cursor:pointer\"><a ng-click=\"tableClick($index)\">{{table.name}}</a>\n" +
                                   "                </li>\n" +
                                   "            </ul>\n" +
                                   "            <br>\n" +
                                   "\n" +
                                   "        </div>\n" +
                                   "\n" +
                                   "        <div class=\"col-sm-9\">\n" +
                                   "            <h4>\n" +
                                   "                <div ng-show=\"!isShowHome\" class=\"input-group\">\n" +
                                   "                    <span class=\"input-group-addon\">\n" +
                                   "                        Query:\n" +
                                   "                    </span>\n" +
                                   "                    <input type=\"text\" class=\"form-control\" ng-model=\"query.field\"\n" +
                                   "                           ng-model-options=\"{ debounce: 200 }\"\n" +
                                   "                           ng-change=\"doQuery()\"\n" +
                                   "                           placeholder=\"Column name\">\n" +
                                   "                    <span style=\"font-size: 14px; padding: 10px;\">\n" +
                                   "                    <label class=\"radio-inline\"><input style=\"margin-bottom: 0; margin-top: 0;\" type=\"radio\"\n" +
                                   "                                                       name=\"optradio\" ng-model=\"query.action\"\n" +
                                   "                                                       ng-change=\"doQuery()\"\n" +
                                   "                                                       ng-value=\"0\">equal</label>\n" +
                                   "                    <label class=\"radio-inline\"><input style=\"margin-bottom: 0; margin-top: 0;\" type=\"radio\"\n" +
                                   "                                                       name=\"optradio\" ng-model=\"query.action\"\n" +
                                   "                                                       ng-change=\"doQuery()\"\n" +
                                   "                                                       ng-value=\"1\">begin</label>\n" +
                                   "                    <label class=\"radio-inline\"><input style=\"margin-bottom: 0; margin-top: 0;\" type=\"radio\"\n" +
                                   "                                                       name=\"optradio\" ng-model=\"query.action\"\n" +
                                   "                                                       ng-change=\"doQuery()\"\n" +
                                   "                                                       ng-value=\"2\">contains</label>\n" +
                                   "                    </span>\n" +
                                   "                    <input type=\"text\" class=\"form-control\" ng-model=\"query.value\"\n" +
                                   "                           ng-model-options=\"{ debounce: 200 }\"\n" +
                                   "                           ng-change=\"doQuery()\"\n" +
                                   "                           placeholder=\"value\">\n" +
                                   "                </div>\n" +
                                   "                <br>\n" +
                                   "                <div class=\"input-group\">\n" +
                                   "                    <span class=\"input-group-btn\">\n" +
                                   "                      <button disabled class=\"btn btn-default\" type=\"button\">\n" +
                                   "                        <span class=\"glyphicon glyphicon-search\"></span>\n" +
                                   "                      </button>\n" +
                                   "                    </span>\n" +
                                   "                    <input type=\"text\" class=\"form-control\" ng-model=\"searchData\"\n" +
                                   "                           ng-model-options=\"{ debounce: 200 }\"\n" +
                                   "                           placeholder=\"Search data...\">\n" +
                                   "                </div>\n" +
                                   "\n" +
                                   "            </h4>\n" +
                                   "            <hr>\n" +
                                   "            <div id=\"TableHome\" ng-if=\"isShowHome\" style=\"height:540px; overflow: scroll\">\n" +
                                   "                <table ng-repeat=\"table in tables | filter: isShowHome && searchData\"\n" +
                                   "                       class=\"table table-hover table-striped\"\n" +
                                   "                       style=\"width: auto; min-width: 300px; display: inline-table\">\n" +
                                   "                    <thead>\n" +
                                   "                    <tr colspan=\"2\">\n" +
                                   "                        <td>{{table.name}}</td>\n" +
                                   "                    </tr>\n" +
                                   "                    <tr>\n" +
                                   "                        <th>Field</th>\n" +
                                   "                        <th>Type</th>\n" +
                                   "                    </tr>\n" +
                                   "                    </thead>\n" +
                                   "                    <tbody>\n" +
                                   "                    <tr ng-repeat=\"row in table.structures\">\n" +
                                   "                        <td>{{row.property}}</td>\n" +
                                   "                        <td>{{row.type}}</td>\n" +
                                   "                    </tr>\n" +
                                   "                    </tbody>\n" +
                                   "                </table>\n" +
                                   "            </div>\n" +
                                   "\n" +
                                   "            <div id=\"TableDetails\" ng-if=\"!isShowHome\" style=\"height:440px; overflow: scroll\">\n" +
                                   "                <table class=\"table table-hover table-striped table-bordered\">\n" +
                                   "                    <thead>\n" +
                                   "                    <tr>\n" +
                                   "                        <th ng-repeat=\"col in tableDetails.columns\">{{col}}</th>\n" +
                                   "                    </tr>\n" +
                                   "                    </thead>\n" +
                                   "                    <tbody>\n" +
                                   "                    <tr ng-repeat=\"row in tableDetails.rows | filter: !isShowHome && searchData\">\n" +
                                   "                        <td ng-repeat=\"item in row\">{{item}}</td>\n" +
                                   "                    </tr>\n" +
                                   "                    </tbody>\n" +
                                   "                </table>\n" +
                                   "            </div>\n" +
                                   "\n" +
                                   "        </div>\n" +
                                   "    </div>\n" +
                                   "</div>\n" +
                                   "\n" +
                                   "<footer class=\"container-fluid\">\n" +
                                   "    <p>@Nhan Cao</p>\n" +
                                   "</footer>\n" +
                                   "\n" +
                                   "<script>\n" +
                                   "    var app = angular.module('myApp', []);\n" +
                                   "    app.controller('myCtrl', function ($scope, $http, myService) {\n" +
                                   "        var host = \"-----[HOSTNAME:PORT]------\";\n" +
                                   "\n" +
                                   "        $scope.tables = [];\n" +
                                   "        $scope.tableDetails = [];\n" +
                                   "        $scope.tableDetailsOriginal = [];\n" +
                                   "        $scope.isShowHome = true;\n" +
                                   "        myService\n" +
                                   "            .httpPromise(host + \"/api\")\n" +
                                   "            .then(function (response) {\n" +
                                   "                $scope.tables = response.data;\n" +
                                   "            });\n" +
                                   "\n" +
                                   "        $scope.activeTable = -1;\n" +
                                   "\n" +
                                   "        $scope.query = {\n" +
                                   "            field: null,\n" +
                                   "            action: 0,\n" +
                                   "            value: null\n" +
                                   "        };\n" +
                                   "\n" +
                                   "        $scope.doQuery = function () {\n" +
                                   "            if ($scope.query.field != '' && $scope.query.field != null && $scope.query.field != undefined &&\n" +
                                   "                $scope.query.value != '' && $scope.query.value != null && $scope.query.value != undefined) {\n" +
                                   "\n" +
                                   "                var action = $scope.query.action == 0 ? \"equal\" : $scope.query.action == 1 ? \"begin\" : \"contains\";\n" +
                                   "\n" +
                                   "                myService\n" +
                                   "                    .httpPromise(host + \"/api?where=\" + $scope.tables[$scope.activeTable].name\n" +
                                   "                        + \"&field=\" + $scope.query.field\n" +
                                   "                        + \"&\" + action + \"=\" + $scope.query.value)\n" +
                                   "                    .then(function (response) {\n" +
                                   "                        $scope.tableDetails = response.data;\n" +
                                   "                    });\n" +
                                   "            } else {\n" +
                                   "                $scope.tableDetails = $scope.tableDetailsOriginal;\n" +
                                   "                console.log(\"Query field, value not null\");\n" +
                                   "            }\n" +
                                   "\n" +
                                   "        };\n" +
                                   "\n" +
                                   "        $scope.tableClick = function ($index) {\n" +
                                   "            if ($scope.activeTable == $index) {\n" +
                                   "                $scope.activeTable = -1;\n" +
                                   "                $scope.isShowHome = true;\n" +
                                   "            } else {\n" +
                                   "                $scope.activeTable = $index;\n" +
                                   "                $scope.isShowHome = false;\n" +
                                   "\n" +
                                   "                myService\n" +
                                   "                    .httpPromise(host + \"/api?where=\" + $scope.tables[$index].name + \"&all\")\n" +
                                   "                    .then(function (response) {\n" +
                                   "                        $scope.tableDetailsOriginal = response.data;\n" +
                                   "                        $scope.tableDetails = response.data;\n" +
                                   "                    });\n" +
                                   "            }\n" +
                                   "        }\n" +
                                   "\n" +
                                   "    });\n" +
                                   "\n" +
                                   "    app.service('myService', function ($http, $q) {\n" +
                                   "        this.httpPromise = function (url) {\n" +
                                   "            var defer = $q.defer();\n" +
                                   "\n" +
                                   "            var req = {\n" +
                                   "                method: 'GET',\n" +
                                   "                url: url,\n" +
                                   "                headers: {\n" +
                                   "                    'Content-Type': 'application/json; charset=UTF-8'\n" +
                                   "                }\n" +
                                   "            };\n" +
                                   "\n" +
                                   "            $http(req).then(function (response) {\n" +
                                   "                if (typeof response.data === 'object') {\n" +
                                   "                    defer.resolve(response.data);\n" +
                                   "                } else {\n" +
                                   "                    defer.reject(response.data);\n" +
                                   "                }\n" +
                                   "            }, function (error) {\n" +
                                   "                defer.reject(error);\n" +
                                   "            });\n" +
                                   "\n" +
                                   "            return defer.promise;\n" +
                                   "        };\n" +
                                   "\n" +
                                   "    });\n" +
                                   "\n" +
                                   "</script>\n" +
                                   "\n" +
                                   "</body>\n" +
                                   "</html>\n";
}
