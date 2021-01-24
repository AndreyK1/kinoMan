$(document).ready(function() {
    $.ajax({
        type: "POST",
        url: "/create",
        data: '{"id": "177178", "content": "gfgdfgd1111"}',
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function(data){console.log(data)},
        failure: function(errMsg){console.log(errMsg)}
    }).then(function(data) {
        console.log(data)
        $('.greeting-id').append(data.id);
        $('.greeting-content').append(data.content);
    });
});