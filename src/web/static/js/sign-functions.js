$(function () {
    $init_param = $.getUrlParam('nologin');
    if ($init_param === 'true') {
        alert_info("Sign In Account, Please.");
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
            $.ajax({
                url: '/web/passport/signup',
                data: {
                    'account': $account_elem.val(),
                    'password': $.md5($password_elem.val())},
                type: 'post',
                dataType: 'json',
                success: function (content) {
                    alert_info(content.message);
                }
            });
        }
    });

    $('#btn-sign-in').click(function () {
        var $account = $('#SignInAccount').val();
        var $password = $('#SignInPassword').val();
        var $remember = $('#SignInRemember').is(':checked');
        // alert_info($remember.toString()); // false:on,

        var pattern = /^\w{6,31}$/;
        if (!pattern.test($account)) {
            alert_info("Please enter the correct account.");
        } else if (!pattern.test($password)) {
            alert_info("Please enter the correct password.");

        } else {
            $.ajax({
                url: '/web/passport/signin',
                data: {
                    'account': $account,
                    'password': $.md5($password),
                    'remember': $remember},
                type: 'post',
                dataType: 'json',
                success: function (content) {
                    alert_info(content["message"]);
                    console.log(typeof(content['code']));
                    console.log(content['code']);
                    if (content['code'] === 0) {
                        $('#SignInBox').addClass("d-none");
                        $('#SignUpBox').addClass("d-none");
                        $('#AccountBox').removeClass("d-none");
                    }
                }
            });
        }
    });
});
