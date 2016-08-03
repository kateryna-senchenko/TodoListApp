var TaskService = function (eventbus, events, baseUrl) {

    var _createTask = function (taskData) {

        var tokenId = taskData.tokenId;
        var userId = taskData.userId;
        var taskDescription = taskData.taskDescription;

        $.post(baseUrl + "app/create-task",
            {
                tokenId: tokenId,
                userId: userId,
                taskDescription: taskDescription

            }, function (xhr) {

                var data = eval("(" + xhr + ")");
                eventbus.post(events.UPDATED_TASK_LIST, data);

            }, 'text')

            .fail(function (xhr) {

                var data = eval("(" + xhr.responseText + ")");
                eventbus.post(events.TASK_CREATION_FAILED, data);
            })
    };

    var _markTaskAsDone = function (taskData) {

        var tokenId = taskData.tokenId;
        var userId = taskData.userId;
        var taskId = taskData.taskId;

        $.post(baseUrl + "app/mark-done",
            {
                tokenId: tokenId,
                userId: userId,
                taskId: taskId
            },
            function (xhr) {

                var data = eval("(" + xhr + ")");
                eventbus.post(events.UPDATED_TASK_LIST, data);

            }, 'text')

    };

    var _undoTask = function (taskData) {

        var tokenId = taskData.tokenId;
        var userId = taskData.userId;
        var taskId = taskData.taskId;

        $.post(baseUrl + "app/undo",
            {
                tokenId: tokenId,
                userId: userId,
                taskId: taskId
            },
            function (xhr) {

                var data = eval("(" + xhr + ")");
                eventbus.post(events.UPDATED_TASK_LIST, data);

            }, 'text')

    };

    var _deleteTask = function (taskData) {

        var tokenId = taskData.tokenId;
        var userId = taskData.userId;
        var taskId = taskData.taskId;

        $.post(baseUrl + "app/delete-task",
            {
                tokenId: tokenId,
                userId: userId,
                taskId: taskId
            },
            function (xhr) {

                var data = eval("(" + xhr + ")");
                eventbus.post(events.UPDATED_TASK_LIST, data);

            }, 'text')

    };

    return {
        "createTask": _createTask,
        "markAsDone": _markTaskAsDone,
        "undoTask": _undoTask,
        "deleteTask": _deleteTask
    };
};

if (typeof define !== 'function') {
    var define = require('amdefine')(module);
}

define(function () {
    return TaskService;
});

