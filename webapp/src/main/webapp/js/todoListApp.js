var TodoListApp = function (rootDivId, eventbus, events, userService, taskService) {

    var RegistrationComponent = function () {

        var registrationBoxId = rootDivId + '_registration';

        var _initialize = function () {

            $('#' + rootDivId).append($('<div/>').attr('id', registrationBoxId));

            var contentId = registrationBoxId + "_content";
            $('#' + registrationBoxId).html($('<div/>').attr('id', contentId));

            var buttonId = registrationBoxId + '_button';
            var emailInputId = registrationBoxId + '_email';
            var passwordInputId = registrationBoxId + '_password';
            var confirmPasswordInputId = registrationBoxId + '_confirm_password';
            var registrationMessageId = registrationBoxId + '_message';

            $('#' + contentId).html($('<fieldset/>'));
            $('<legend/>').text('Registration').appendTo($('fieldset'));
            $('<input/>').attr('id', emailInputId).attr({
                'type': 'text',
                'placeholder': 'Email'
            }).appendTo($('fieldset'));
            $('<br>').appendTo($('fieldset'));
            $('<br>').appendTo($('fieldset'));
            $('<input/>').attr('id', passwordInputId).attr({
                'type': 'password',
                'placeholder': 'Password'
            }).appendTo($('fieldset'));
            $('<br>').appendTo($('fieldset'));
            $('<br>').appendTo($('fieldset'));
            $('<input/>').attr('id', confirmPasswordInputId).attr({
                'type': 'password',
                'placeholder': 'Confirm password'
            }).appendTo($('fieldset'));
            $('<br>').appendTo($('fieldset'));
            $('<br>').appendTo($('fieldset'));
            $('<p/>').attr('id', registrationMessageId).text('')
                .appendTo($('fieldset'));
            $('<button/>').attr('id', buttonId).text('Register').appendTo($('fieldset'));

            var loginButtonId = contentId + '_loginButton';
            $('<button/>').attr('id', loginButtonId).text('Login').appendTo($('fieldset'));


            $('#' + loginButtonId).click(function () {

                $('#' + registrationBoxId).remove();
                eventbus.post(events.ATTEMPT_TO_RENDER_LOGIN_FORM);

            });

            $('#' + buttonId).click(function () {

                var newUserData = {
                    email: $('#' + emailInputId).val(),
                    password: $('#' + passwordInputId).val(),
                    confirmPassword: $('#' + confirmPasswordInputId).val()
                };

                console.log('Trying to register user ' + newUserData.email);
                eventbus.post(events.ATTEMPT_TO_REGISTER, newUserData);

            });
        };

        var _displayRegistrationError = function (failRegistrationEvent) {

            var errorElementRootId = registrationBoxId + '_message';
            $('#' + errorElementRootId).html($('<p/>').attr('style', 'color:red; font-size:14; font-family:"Calibri"')
                .text(failRegistrationEvent.errorMessage));
        };

        var _displayRegistrationMessage = function (successfulRegistrationEvent) {

            var messageElementRootId = registrationBoxId + '_message';
            $('#' + messageElementRootId).html($('<p/>').attr('style', 'color:green; font-size:14; font-family:"Calibri"')
                .text(successfulRegistrationEvent.successMessage));

            var emailInputId = registrationBoxId + '_email';
            var passwordInputId = registrationBoxId + '_password';
            var confirmPasswordInputId = registrationBoxId + '_confirm_password';
            $('#' + emailInputId).val('');
            $('#' + passwordInputId).val('');
            $('#' + confirmPasswordInputId).val('');

        };

        var _removeLoginButton = function () {

            var loginButtonId = registrationBoxId + '_content_loginButton';
            $('#' + loginButtonId).remove();
        };


        return {
            "init": _initialize,
            "showRegistrationError": _displayRegistrationError,
            "showSuccessfulRegistrationMessage": _displayRegistrationMessage,
            "removeLoginButton": _removeLoginButton
        };
    };

    var LoginComponent = function () {

        var loginBoxId = rootDivId + '_login';

        var _initialize = function () {

            $('#' + rootDivId).append($('<div/>').attr('id', loginBoxId));

            var buttonId = loginBoxId + '_button';
            var emailInputId = loginBoxId + '_email';
            var passwordInputId = loginBoxId + '_password';

            var loginMessageId = loginBoxId + '_message';

            $('#' + loginBoxId).html($('<fieldset/>').attr('class', 'loginBox'));
            $('<legend/>').text('Login').appendTo($('.loginBox'));
            $('<input/>').attr('id', emailInputId).attr({
                'type': 'text',
                'placeholder': 'Email'
            }).appendTo($('.loginBox'));
            $('<br>').appendTo($('.loginBox'));
            $('<br>').appendTo($('.loginBox'));
            $('<input/>').attr('id', passwordInputId).attr({
                'type': 'password',
                'placeholder': 'Password'
            }).appendTo($('.loginBox'));
            $('<br>').appendTo($('.loginBox'));
            $('<br>').appendTo($('.loginBox'));
            $('<p/>').attr('id', loginMessageId).text('').appendTo($('.loginBox'));
            $('<button/>').attr('id', buttonId).text('Login').appendTo($('.loginBox'));


            $('#' + buttonId).click(function () {

                var userData = {
                    email: $('#' + emailInputId).val(),
                    password: $('#' + passwordInputId).val()

                };

                console.log('Trying to login user ' + userData.email);
                eventbus.post(events.ATTEMPT_TO_LOGIN, userData);

            });
        };

        var _displayLoginError = function (failLoginEvent) {

            var errorElementRootId = loginBoxId + '_message';

            $('#' + errorElementRootId).html($('<p/>').attr('style', 'color:red; font-size:14; font-family:"Calibri"')
                .text(failLoginEvent.errorMessage));
        };

        var _closeLoginForm = function () {

            $('#' + loginBoxId).remove();
        };

        return {
            "init": _initialize,
            "showLoginError": _displayLoginError,
            "closeLoginForm": _closeLoginForm
        };
    };

    var TodoListComponent = function () {

        var todoListComponentId = rootDivId + '_todo';
        var todoListBoxId = todoListComponentId + '_content';

        var _initialize = function (taskData) {

            $('#' + rootDivId).html($('<div/>').attr('id', todoListComponentId));
            $('#' + todoListComponentId).append($('<div/>').attr('id', todoListBoxId));

            $('#' + todoListBoxId).html($('<fieldset/>').attr('class', 'todolist'));
            $('<legend/>').text('ToDo List').appendTo($('fieldset'));

            var addButtonId = todoListBoxId + '_create';
            var taskInputId = todoListBoxId + '_newtask';
            var errorMessage = todoListBoxId + '_erorMessage';

            $('<table/>').attr('id', 'createTask').appendTo($('fieldset'));
            $('#createTask').append($('<tr/>')
                .append($('<td/>').attr({'class': 'column2'})
                    .append($('<input/>').attr('id', taskInputId).attr({
                        'type': 'text',
                        'placeholder': 'New task'
                    })))
                .append($('<td/>').attr({'class': 'column3'})
                    .append($('<button/>').attr('id', addButtonId).text('Add'))))
                .append($('<tr/>').append($('<span/>').attr('id', errorMessage)));

            $('#' + addButtonId).click(function () {

                var newTaskData = {
                    tokenId: taskData.tokenId,
                    userId: taskData.userId,
                    taskDescription: $('#' + taskInputId).val()
                };

                console.log('Trying to create task ' + newTaskData.taskDescription);
                eventbus.post(events.ATTEMPT_TO_CREATE_TASK, newTaskData);

            });

            $('<br>').appendTo($('fieldset'));

            var logoutButtonId = todoListComponentId + "_logout";

            $('#' + todoListComponentId).append( $('<br>'))
                .append( $('<br>'))
                .append($('<button/>').attr('id', logoutButtonId).text("Logout"));

            $('#' + logoutButtonId).click(function () {

                var userData = {
                    tokenId: taskData.tokenId,
                    userId: taskData.userId
                };
                console.log("User " + taskData.userId + " is trying to logout");
                eventbus.post(events.ATTEMPT_TO_LOGOUT, userData);
            });

            var tasks = JSON.parse(taskData.taskList);

            var noTasksId = todoListBoxId + "_noTasks";

            if (tasks.length === 0) {

                $('<table/>').attr('id', noTasksId).appendTo($('fieldset'));
                $('#' + noTasksId).append($('<tr/>')
                    .append($('<td/>').attr('class', 'column2')
                        .append($('<span/>').attr('class', 'noTasks').text("No tasks yet"))));


            } else {

                $('<table/>').attr('id', 'tasks').appendTo($('fieldset'));

                $.each(tasks, function (idx, obj) {

                    var checkboxId = obj.taskId.id;
                    var removeButtonId = obj.taskId.id;

                    if (obj.done === false) {


                        $('#tasks').append($('<tr/>')
                            .append($('<td/>').attr('class', 'column1')
                                .append($('<input/>').attr({
                                    'id': checkboxId,
                                    'type': 'checkbox',
                                    'class': 'undone'
                                })))
                            .append($('<td/>').attr('class', 'column2')
                                .append($('<span/>').attr('class', 'normal').text(obj.taskDescription)))
                            .append($('<td/>').attr('class', 'column3')
                                .append($('<span/>').attr('class', 'normal').text(obj.creationDate)))
                            .append($('<td/>').attr('class', 'column4')
                                .append($('<button/>').attr({
                                    'id': removeButtonId,
                                    'class': 'removeButton'
                                }).text('Remove'))));


                    } else {

                        $('#tasks').append($('<tr/>')
                            .append($('<td/>').attr('class', 'column1')
                                .append($('<input/>').attr({
                                    'id': checkboxId,
                                    'type': 'checkbox',
                                    'checked': true,
                                    'class': 'checked'
                                })))
                            .append($('<td/>').attr('class', 'column2')
                                .append($('<span/>').attr('class', 'done').text(obj.taskDescription)))
                            .append($('<td/>').attr('class', 'column3')
                                .append($('<span/>').attr('class', 'normal').text(obj.creationDate)))
                            .append($('<td/>').attr('class', 'column4')
                                .append($('<button/>').attr({
                                    'id': removeButtonId,
                                    'class': 'removeButton'
                                }).text('Remove'))));

                    }

                });
            }

            $('.undone').click(function () {
                var newTaskData = {
                    tokenId: taskData.tokenId,
                    userId: taskData.userId,
                    taskId: $(this).attr('id')
                };

                console.log("Trying to mark task " + newTaskData.taskId + " done");
                eventbus.post(events.ATTEMPT_TO_MARK_TASK_DONE, newTaskData);

            });

            $('.checked').click(function () {
                var newTaskData = {
                    tokenId: taskData.tokenId,
                    userId: taskData.userId,
                    taskId: $(this).attr('id')
                };

                console.log("Trying to undo task " + newTaskData.taskId);
                eventbus.post(events.ATTEMPT_TO_UNDO_TASK, newTaskData);

            });

            $('.removeButton').click(function () {
                var newTaskData = {
                    tokenId: taskData.tokenId,
                    userId: taskData.userId,
                    taskId: $(this).attr('id')
                };

                console.log("Trying to delete task " + newTaskData.taskId);
                eventbus.post(events.ATTEMPT_TO_DELETE_TASK, newTaskData);

            });

        };

        var _showErrorMessage = function (data) {
            var errorMessage = todoListBoxId + '_erorMessage';

            $('#' + errorMessage).html($('<span/>').attr('style', 'color:red; font-size:14; font-family:"Calibri"')
                .text(data.errorMessage));

        };

        var _closeTodoList = function () {
            $('#' + todoListComponentId).remove();
        };

        return {
            "init": _initialize,
            "showErrorMessage": _showErrorMessage,
            "remove": _closeTodoList
        };
    };

    var _initializeRegistration = function(){

        registrationComponent.init();

    };

    var _showRegistrationAndLogin = function(data){

        todoListComponent.remove();
        registrationComponent.init();
        registrationComponent.removeLoginButton();
        loginComponent.init();

    };

    var registrationComponent = new RegistrationComponent();
    var loginComponent = new LoginComponent();
    var todoListComponent = new TodoListComponent();

    eventbus.subscribe(events.ATTEMPT_TO_REGISTER, userService.registerUser);
    eventbus.subscribe(events.REGISTRATION_FAILED, registrationComponent.showRegistrationError);
    eventbus.subscribe(events.USER_IS_REGISTERED, registrationComponent.showSuccessfulRegistrationMessage);
    eventbus.subscribe(events.ATTEMPT_TO_RENDER_LOGIN_FORM, loginComponent.init);
    eventbus.subscribe(events.ATTEMPT_TO_LOGIN, userService.loginUser);
    eventbus.subscribe(events.LOGIN_FAILED, loginComponent.showLoginError);
    eventbus.subscribe(events.USER_IS_LOGGED_IN, loginComponent.closeLoginForm);
    eventbus.subscribe(events.USER_IS_LOGGED_IN, todoListComponent.init);
    eventbus.subscribe(events.ATTEMPT_TO_CREATE_TASK, taskService.createTask);
    eventbus.subscribe(events.TASK_CREATION_FAILED, todoListComponent.showErrorMessage);
    eventbus.subscribe(events.UPDATED_TASK_LIST, todoListComponent.init);
    eventbus.subscribe(events.ATTEMPT_TO_MARK_TASK_DONE, taskService.markAsDone);
    eventbus.subscribe(events.ATTEMPT_TO_UNDO_TASK, taskService.undoTask);
    eventbus.subscribe(events.ATTEMPT_TO_DELETE_TASK, taskService.deleteTask);
    eventbus.subscribe(events.ATTEMPT_TO_UPDATE_TASK_LIST, taskService.updateUserTasks);
    eventbus.subscribe(events.ATTEMPT_TO_LOGOUT, userService.logoutUser);
    eventbus.subscribe(events.USER_IS_LOGGED_OUT, _showRegistrationAndLogin);



    return{
        "initializeRegistration": _initializeRegistration,
        "showRegistrationAndLogin": _showRegistrationAndLogin

    }
};

define(function () {
    return TodoListApp;
});
