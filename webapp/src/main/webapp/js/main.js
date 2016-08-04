define(function (require) {

    var EventBus = require('./eventbus');
    var eventBus = new EventBus();

    var events = require('./events');

    var baseUrl = 'http://localhost:8080/';

    var UserService = require('./services/userService');
    var userService = new UserService(eventBus, events, baseUrl);

    var TaskService = require('./services/taskService');
    var taskService = new TaskService(eventBus, events, baseUrl);

    var TodoListApp = require('./todoListApp');
    var todoListApp = new TodoListApp("app", eventBus, events, userService, taskService);

    if(!sessionStorage.getItem("session")) {

        if(!sessionStorage.getItem("loggedOut")){
            todoListApp.initializeRegistration();
        } else {
            todoListApp.showRegistrationAndLogin();
        }

    } else {

        var token = JSON.parse(sessionStorage.getItem("session"));

        var tokenData = {
            tokenId: token.tokenId,
            userId: token.userId
        };

        taskService.updateUserTasks(tokenData);

    }
});
