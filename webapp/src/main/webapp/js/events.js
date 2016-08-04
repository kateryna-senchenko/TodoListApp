var Events = {

    ATTEMPT_TO_REGISTER: 'attemptToRegister',
    REGISTRATION_FAILED: 'registrationFailed',
    USER_IS_REGISTERED: 'userIsRegistered',

    ATTEMPT_TO_RENDER_LOGIN_FORM: 'attemptToRenderLoginForm',

    ATTEMPT_TO_LOGIN: 'attemptToLogin',
    LOGIN_FAILED: 'loginFailed',
    USER_IS_LOGGED_IN: 'userIsLoggedIn',

    UPDATED_TASK_LIST: 'updatedTaskList',
    ATTEMPT_TO_CREATE_TASK: 'attemptToCreateTask',
    TASK_CREATION_FAILED: 'taskCreationFailed',
    ATTEMPT_TO_MARK_TASK_DONE: 'attemptToMarkTaskDone',
    ATTEMPT_TO_UNDO_TASK: 'attemptToUndoTask',
    ATTEMPT_TO_DELETE_TASK: 'attemptToDeleteTask'

};

if (typeof define !== 'function') {
    var define = require('amdefine')(module);
}

define(function () {
    return Events;
});



