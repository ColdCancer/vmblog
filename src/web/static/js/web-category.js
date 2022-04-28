var current_page = 1, update_flag = 0;

$(function () {
    requestCategoryByPage();

    $('#article-page-previous').click(function () {
        current_page -= 1;
        requestCategoryByPage();
        if (update_flag === 0) current_page += 1;
    });
    $('#article-page-next').click(function () {
        current_page += 1;
        requestCategoryByPage();
        if (update_flag === 0) current_page -= 1;
    });

})

function requestCategoryByPage() {
    update_flag = 0;
    $.ajax({
        url: '/api/category/card/list/' + current_page,
        type: 'get',
        dataType: 'json',
        async: false,
        success: function (content) {
            if (content['code'] !== 0) return;
            var data = content['data'];
            // console.log(data);
            var category_elem = $('#category-list');
            category_elem.html('');
            for (var index in data) {
                var item = data[index];
                category_elem.append(new CategoryListItem(item['blogger'],
                    item['typename'], item['count'], item['account']).convert());
            }
            window.scrollTo(0, 0);
            $('#page-num').text('page: ' + current_page);
            update_flag = 1;
            // consolo.log('dddd');

            category_elem.on('click', 'li', function () {
                // console.log($($(this).find('input')).attr('value'));
                var url = $($(this).find('input')).attr('value');
                // console.log(url);
                window.location.href = url;
            });
        }
    })
}