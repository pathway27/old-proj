'use strict';

angular.module('angularTcApp')
  .controller('MainCtrl', function ($scope, $http) {

    $scope.fetchTweets = function() {
      $http.get('tweets/tweets.json')
        .then(function(res) {

          var data = res.data[0];
          var length = data.messages.length;

          // Unix timestamp into a Date Object
          data.timestamp = new Date(data.timestamp * 1000);

          // Make dates out of every tweet
          for (var i=0; i<length; i++) {
            data.messages[i].date = new Date(data.messages[i].date.replace(/-/g, '/'));
          }

          $scope.tweets = data;
        });
    };

    $scope.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma',
      'angular-tc'
    ];

  });
