$(document).ready(function(){
    // Prevent form submission
    form = $('#msgToChat')
    form.submit(function(event){
        event.preventDefault();
        var message=$('#messageTC').val()

        $.ajax({
            type: "POST",
            url: "/telbot/sendMessage",
            data: '{ "message" : "'+message+'" }',
            contentType: "application/json; charset=utf-8",
            // dataType: "json",
            // success: function(data){console.log(data)},
            failure: function(errMsg){console.log(errMsg)}
        }).then(function(data) {

        })

    })



    sentenceForm = $('#sentenceToGroup')
    sentenceForm.submit(function(event){
        event.preventDefault();

        $.ajax({
            type: "GET",
            url: "/message/sendToGroup",
            // data: '{ "message" : "'+message+'" }',
            contentType: "application/json; charset=utf-8",
            // dataType: "json",
            // success: function(data){console.log(data)},
            failure: function(errMsg){console.log(errMsg)}
        }).then(function(data) {

        })

    })




});