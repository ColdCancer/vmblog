var login_flag = false;

$(function () {
    var content = requestStateOfLogin();
    if (content !== null && content['code'] === 0) {
        var account = content['data']['account'];
        successAction(account);
        login_flag = true;
    } else {
        login_flag = false;
    }

    $('#btn-sign-up').click(function () {
        var $account_elem = $('#SignUpAccount');
        var $password_elem = $('#SignUpPassword');
        var $repassword_elem = $('#SignUpRePassword');

        var pattern = /^\w{6,31}$/;
        if (!pattern.test($account_elem.val())) {
            alert_info("Please enter the correct account.");
            $account_elem.val('');
        } else if (!pattern.test($password_elem.val())) {
            alert_info("Please enter the correct password.");
            $password_elem.val('');
        } else if (!pattern.test($repassword_elem.val())) {
            alert_info("Please enter the correct re-password.");
            $repassword_elem.val('');
        } else if ($password_elem.val() !== $repassword_elem.val()) {
            alert_info("The second password is inconsistent.");
        } else {
            var content_ = requestLoginUp($account_elem.val(), $password_elem.val());
            alert_info(content_['message']);
        }
    });

    $('#btn-sign-in').click(function () {
        var account = $('#SignInAccount').val();
        var password = $('#SignInPassword').val();
        var remember = $('#SignInRemember').is(':checked');

        var pattern = /^\w{6,31}$/;
        if (!pattern.test(account)) {
            alert_info("Please enter the correct account.");
        } else if (!pattern.test(password)) {
            alert_info("Please enter the correct password.");
        } else {
            var content_ = requestLoginIn(account, password, remember);
            alert_info(content_['message']);
            if (content_['code'] === 0) {
                successAction(account);
                // location.reload();
            }
        }
    });

    $('#out-link').click(function () {
        content = requestLoginOut();
        alert_info(content);
        if (content['code'] === 0) {
            login_flag = false;
            location.reload();
        }
    });
});

function successAction(account) {
    $('#SignInBox').addClass("d-none");
    $('#SignUpBox').addClass("d-none");
    $('#AccountBox').removeClass("d-none");
    $('#index-account').text(' ' + account + ' ');
    var photo_url = '/api/resources/' + account + '/profile-photo';
    $('#profile-photo').attr('src', photo_url);
    login_flag = true;
}