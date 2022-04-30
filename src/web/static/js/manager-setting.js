$(function () {
    $.ajax({
        url: '/web/api/blogger/previous',
        type: 'get',
        dataType: 'json',
        success: function (content) {
            if (content['code'] !== 0) return;
            var data = content['data'];
            $('#blogger-photo').attr('src', data['photo']);
            $('#blogger-motto').val(data['motto']);
            $('#blogger-sex').val(data['sex']);
            $('#blogger-email').val(data['email']);
            $('#blogger-company').val(data['company']);
            $('#blogger-birthplace').val(data['birthplace']);
            $('#blogger-education').val(data['education']);
        }
    })

    $('#photo-modify').click(function () {
        $('#photo-commit').removeClass('disabled');
        $('#photo-cancel').removeClass('disabled');
        $('#profile-photo').attr('disabled', false);
    });
    $('#photo-cancel').click(function () {
        $('#photo-commit').addClass('disabled');
        $('#photo-cancel').addClass('disabled');
        $('#profile-photo').attr('disabled', true);
    });
    $('#profile-photo').change(function () {
        var img_elem = $('#profile-photo');
        var div_elem = $(img_elem[0].parentElement);
        var label_elem = $(div_elem[0].getElementsByTagName('label'));
        label_elem.text(img_elem[0].files[0].name);
    });
    $('#photo-commit').click(function () {
        var file = $('#profile-photo')[0].files[0];
        // console.log(file);
        if (file) {
            var formData = new FormData();
            formData.append("photo", file);

            $.ajax({
                url: '/web/api/blogger/changePhoto',
                type: 'post',
                processData: false,
                contentType: false,
                data: formData,
                dataType: 'json',
                success: function (content) {
                    // alert_info(content['message']);
                    if (content['code'] !== 0) {
                        alert_info("change photo failure.");
                    } else {
                        alert_info("change photo success.");
                    }
                }
            });
        }
    });

    $('#pwd-modify').click(function () {
        var old_text = $('#blogger-old-pwd').val();
        var new_text = $('#blogger-new-pwd').val();
        var re_text = $('#blogger-re-pwd').val();

        var pattern = /^\w{6,31}$/;
        if (!pattern.test(old_text)) {
            alert_info("Input correct password, please.");
        } else if (!pattern.test(new_text)) {
            alert_info("Input correct new password, please.");
        } else if (!pattern.test(re_text)) {
            alert_info("Input correct repeat password, please.");
        } else if (new_text !== re_text) {
            alert_info("The second password is inconsistent.");
        } else {
            $.ajax({
                url: '/web/api/blogger/changePassword',
                type: 'post',
                data: {
                    'old_pwd': $.md5(old_text),
                    'new_pwd': $.md5(new_text)},
                dataType: 'json',
                success: function (content) {
                    if (content['code'] !== 0) {
                        alert_info("change password failure.");
                    } else {
                        alert_info("change password success.");
                        setTimeout(function () {
                            location.reload();
                        }, 2000);
                    }
                }
            });
        }
    });

    $('#info-modify').click(function () {
        $('#blogger-sex').attr('disabled', false);
        $('#blogger-birthplace').attr('disabled', false);
        $('#blogger-email').attr('disabled', false);
        $('#blogger-education').attr('disabled', false);
        $('#blogger-company').attr('disabled', false);
        $('#blogger-motto').attr('disabled', false);
        $('#info-cancel').removeClass('disabled');
        $('#info-commit').removeClass('disabled');
    });
    $('#info-cancel').click(function () {
        $('#blogger-sex').attr('disabled', true);
        $('#blogger-birthplace').attr('disabled', true);
        $('#blogger-email').attr('disabled', true);
        $('#blogger-education').attr('disabled', true);
        $('#blogger-company').attr('disabled', true);
        $('#blogger-motto').attr('disabled', true);
        $('#info-cancel').addClass('disabled');
        $('#info-commit').addClass('disabled');
    });
    $('#info-commit').click(function () {
        let sex = $('#blogger-sex').val();
        let email = $('#blogger-email').val();
        let company = $('#blogger-company').val();
        let birthplace = $('#blogger-birthplace').val();
        let education = $('#blogger-education').val();
        let motto = $('#blogger-motto').val();
        $.ajax({
            url: '/web/api/blogger/changeInformation',
            type: 'post',
            data: {
                "sex": sex,
                "email": email,
                "company": company,
                "birthplace": birthplace,
                "motto": motto,
                "education": education
            },
            dataType: 'json',
            success: function (content) {
                if (content['code'] !== 0) {
                    alert_info("change information failure.");
                } else {
                    alert_info("change information success.");
                    // todo: update
                    location.reload();
                }
            }
        })
    })

});