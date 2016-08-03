define(function (require) {

    var EventBus = require('./eventbus');
    var eventBus = new EventBus();

    var events = require('./events');

    var baseUrl = 'http://localhost:8080/';

    var UserService = require('./services/userService');
    var userService = new UserService(eventBus, events, baseUrl);

    var TodoListApp = require('./todoListApp');
    var todoListApp = new TodoListApp("app", eventBus, events, userService);

});
