
function showBooks(){
        $.ajax({
        type: "GET",
        url: "/book/all",
        // data: '{"name" : "Test", "email" : "test@test.com" }',
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        // success: function(data){console.log(data)},
        failure: function(errMsg){console.log(errMsg)}
    }).then(function(data) {
            console.log("books", data)
            $('#AllBooksRow').html("")
            for (var i = 0; i < data.length; i++) {
                var elementDiv = $(document.createElement("div"))
                elementDiv.addClass("them-col col-sm-4 col-md-2 m-2 p-2 border rounded bg-white")
                var inElem = document.createElement("a")
                inElem.href = "#"
                inElem = $(inElem)

                inElem.addClass("text-dark")
                inElem.text(data[i].name+"   ")
                elementDiv.append(inElem)

                inElem.attr('id',  data[i].id);
                (function(id_book) {
                    inElem.click(function () {
                        // $('#be').empty()
                        // console.log("element.click", this.id)
                        showAllSentencesInBook(id_book)
                    })
                })(data[i].id)

                var delElem = document.createElement("a")
                delElem.href = "#"
                delElem = $(delElem)
                delElem.addClass("text-dark")
                delElem.text("del")
                elementDiv.append(delElem);
                (function(id_book) {
                    delElem.click(function () {
                        result = confirm("точно удалить книгу?");
                        if(!result){
                            return
                        }
                        deleteBook(id_book)
                    })
                })(data[i].id)


                $('#AllBooksRow').append(elementDiv);
            }
        })
}

function deleteBook(id_book){
    $.ajax({
        type: "GET",
        url: "/book/deleteBook/"+id_book,
        // data: '{"id_book" : '+id_book+'}',
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        // success: function(data){console.log(data)},
        failure: function(errMsg){console.log(errMsg)}
    }).then(function(data) {
        console.log("deleted ", data)
    })
}

function showAllSentencesInBook(id_book){
    $.ajax({
        type: "GET",
        url: "/book/allSentences/"+id_book,
        // data: '{"id_book" : '+id_book+'}',
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        // success: function(data){console.log(data)},
        failure: function(errMsg){console.log(errMsg)}
    }).then(function(data) {
        console.log("sentences", data)
        $('#AllSentencesRow').html("")
        //AllSentencesRow
        var table = $(document.createElement("table"))
        // table.style.width = '100%';
        // table.setAttribute('border', '1');
        // table.style("color","green")
        table.css('border', '1px solid black');
        table.attr("border","1");

        var tbdy = $(document.createElement('tbody'));
        table.append(tbdy)


        for (var i = 0; i < data.length; i++) {

            var tr = $(document.createElement('tr'));

            var tdId = $(document.createElement('td'));
            tdId.text(data[i].id)
            var tdRus = $(document.createElement('td'));
            tdRus.text(data[i].rus)
            var tdEng = $(document.createElement('td'));
            tdEng.text(data[i].eng)

            tr.append(tdId)
            tr.append(tdRus)
            tr.append(tdEng)
            tbdy.append(tr)

            // var elementSentence = $(document.createElement("div"))
            // var inElem = document.createElement("span")
            // inElem = $(inElem)
            // inElem.text(" id="+data[i].id + " | rus " + data[i].rus +" | eng " + data[i].eng)
            // var br = document.createElement("br")
            // elementSentence.append(inElem)
            // elementSentence.append(br)
            $('#AllSentencesRow').append(table);
        }
    })
}