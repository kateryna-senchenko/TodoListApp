var UserService = function (eventbus, events, baseUrl) {

    var _registerUser = function (userData) {

        var email = userData.email;
        var password = userData.password;
        var confirmPassword = userData.confirmPassword;

        $.post(baseUrl + "app/registration",
            {
                email: email,
                password: password,
                confirmPassword: confirmPassword

            }, function (xhr) {

                var data = eval("(" + xhr + ")");

                console.log("User is registered");
                eventbus.post(events.USER_IS_REGISTERED, data);

            }, 'text')

            .fail(function (xhr) {

                var data = eval("(" + xhr.responseText + ")");
                eventbus.post(events.REGISTRATION_FAILED, data);
            })
    };

    var _loginUser = function (userData) {

        var email = userData.email;
        var password = userData.password;

        $.post(baseUrl + "app/login",
            {
                email: email,
                password: password
            },
            function (xhr) {

                var data = eval("(" + xhr + ")");
                var _session = {
                    tokenId: data.tokenId,
                    userId: data.userId
                };

                sessionStorage.setItem("session", JSON.stringify(_session));
                console.log("User is logged in");
                eventbus.post(events.USER_IS_LOGGED_IN, data);

            }, 'text')

            .fail(function (xhr) {

                var data = eval("(" + xhr.responseText + ")");
                eventbus.post(events.LOGIN_FAILED, data);
            })
    };

    var _logoutUser = function (userData) {

        var tokenId = userData.tokenId;
        var userId = userData.userId;

        $.post(baseUrl + "app/logout",
            {
                tokenId: tokenId,
                userId: userId
            },
            function (xhr) {

                var data = eval("(" + xhr + ")");

                sessionStorage.removeItem("session");
                sessionStorage.setItem("loggedOut", "true");

                console.log("User is logged out");

                eventbus.post(events.USER_IS_LOGGED_OUT, data);

            }, 'text')


    };

    return {
        "registerUser": _registerUser,
        "loginUser": _loginUser,
        "logoutUser": _logoutUser
    };
};

if (typeof define !== 'function') {
    var define = require('amdefine')(module);
}

define(function () {
    return UserService;
});
