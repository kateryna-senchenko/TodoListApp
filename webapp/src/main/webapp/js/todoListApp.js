var TodoListApp = function (rootDivId, eventbus, events, userService) {

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


        return {
            "init": _initialize,
            "showRegistrationError": _displayRegistrationError,
            "showSuccessfulRegistrationMessage": _displayRegistrationMessage
        };
    };


    var registrationComponent = new RegistrationComponent();

    registrationComponent.init();

    eventbus.subscribe(events.ATTEMPT_TO_REGISTER, userService.registerUser);
    eventbus.subscribe(events.REGISTRATION_FAILED, registrationComponent.showRegistrationError);
    eventbus.subscribe(events.USER_IS_REGISTERED, registrationComponent.showSuccessfulRegistrationMessage);

};

define(function () {
    return TodoListApp;
});

