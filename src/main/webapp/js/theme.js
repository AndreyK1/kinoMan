
function showModalThema() {
    if(!isAuth()) return;
    $('#themaModal').modal('toggle');
}


function selectTheme(id, funcToStartTreeFromElement){
    if(id == 0){
        return;
    }
    $.ajax({
        type: "GET",
        url: "/theme/"+id,
        // data: '{"name" : "Test", "email" : "test@test.com" }',
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        // success: function(data){console.log(data)},
        failure: function(errMsg){console.log(errMsg)}
    }).then(function(data) {
        console.log("theme", data)
        // renderOuter(data)
        $('#theme').empty()
        $('#theme').append(data.name);
        funcToStartTreeFromElement(data.start_id)

    });
}


function showAllThemes(funcToStartTreeFromElement) {
    $.ajax({
        type: "GET",
        url: "/theme/all",
        // data: '{"name" : "Test", "email" : "test@test.com" }',
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        // success: function(data){console.log(data)},
        failure: function(errMsg){console.log(errMsg)}
    }).then(function(data) {
        console.log("themes", data)
        // renderOuter(data)
        for (var i = 0; i < data.length; i++) {


            //для тем в контексте
            var elementDiv = $(document.createElement("div"))
            elementDiv.addClass("them-col col-sm-4 col-md-2 m-2 p-2 border rounded bg-white")
            var inElem = document.createElement("a")
            inElem.href="#"
            inElem = $(inElem)

            inElem.addClass("text-dark")
            inElem.text(data[i].name)
            elementDiv.append(inElem)

            // <div class="them-col col-sm-4 col-md-2 m-2 p-2 border rounded bg-white" style="border-radius: 30px;">
            //     <a href="#" class="text-dark"> col1312312213123123 123123123123123123 123123123123123123 12312312312312312312312 </a>
            // </div>

            //для тем в сайд баре
            var elementLi = $(document.createElement("li"))
            elementLi.addClass("py-2")
            var inElemLi = document.createElement("a")
            inElemLi.href="#"
            inElemLi = $(inElemLi)
            inElemLi.addClass("text-white")
            inElemLi.text(data[i].name)
            elementLi.append(inElemLi)

            // <li class="py-2">
            //     <a href="#" class="text-white"> ol1312312213123123 123123123123123123 123123123123123123 12312312312312312312312</a>
            // </li >



            elementDiv.attr('id',  data[i].id);
            (function(id_theme, start_id) {

             elementDiv.click(function () {
                $('#be').empty()
                // console.log("element.click", this.id)
                setThemeCookie(id_theme, start_id)
                selectTheme(id_theme,funcToStartTreeFromElement);
            })


            elementLi.click(function () {
                $('#be').empty()
                // console.log("element.click", this.id)
                setThemeCookie(id_theme, start_id)
                selectTheme(id_theme,funcToStartTreeFromElement);
            })

            })(data[i].id, data[i].start_id)




            // $('#allThemes').append(element);
            $('#AllThemasRow').append(elementDiv);
            $('#AllThemasUl').append(elementLi);



        }


        // funcToStartTreeFromElement(data.start_id)

    });
}


function addTheme() {
    console.log("addTheme", $("#themeName").val())
    console.log("startQuestion", $("#startQuestion").val())

    $.ajax({
        type: "POST",
        url: "/theme/add",
        data: '{"name" : "'+$("#themeName").val()+'", "start_qwest" : "'+$("#startQuestion").val()+'" }',
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        // success: function(data){console.log(data)},
        failure: function(errMsg){console.log(errMsg)}
    }).then(function(data) {
        console.log("addTheme ",data)
        setThemeCookie(data.id, data.start_id)
        //!!! Reload
         document.location.reload(true);

        // userInfo = null
        // if(data.name){
            // userInfo = data
            // $('#userInfo').html(data.name + "  роль: " + data.roles + "  <a href=\"/logout\">logout</a>");
        // }

    });

}