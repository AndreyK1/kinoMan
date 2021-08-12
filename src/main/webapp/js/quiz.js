function showQuizes(){
    var quizNum = $('#quizNum').val();
    var partNum = $('#partNum').val();

    console.log("quizNum", quizNum)
    console.log("partNum" , partNum)

    $.ajax({
        type: "GET",
        url: "/book/allQuizes/"+quizNum+"/"+partNum,
        // data: '{"name" : "Test", "email" : "test@test.com" }',
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        // success: function(data){console.log(data)},
        failure: function(errMsg){console.log(errMsg)}
    }).then(function(data) {
        console.log("Quizes", data)
        $('#AllQuizesRow').html("")


        for (var i = 0; i < data.length; i++) {
            var elementDiv = $(document.createElement("div"))
            // elementDiv.addClass("them-col col-sm-4 col-md-2 m-2 p-2 border rounded bg-white")
            var inElem = document.createElement("span")
            inElem = $(inElem)

            inElem.addClass("text-dark")
            inElem.text(data[i].rowNum+"   " + data[i].rus + " " + data[i].eng )
            elementDiv.append(inElem)


            $('#AllQuizesRow').append(elementDiv);
        }
    })
}
