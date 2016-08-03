var Events = {

    ATTEMPT_TO_REGISTER: 'attemptToRegister',
    REGISTRATION_FAILED: 'registrationFailed',
    USER_IS_REGISTERED: 'userIsRegistered',

    ATTEMPT_TO_RENDER_LOGIN_FORM: 'attemptToRenderLoginForm',

    ATTEMPT_TO_LOGIN: 'attemptToLogin',
    LOGIN_FAILED: 'loginFailed',
    USER_IS_LOGGED_IN: 'userIsLoggedIn'

};

if (typeof define !== 'function') {
    var define = require('amdefine')(module);
}

define(function () {
    return Events;
});



