
var adderForm = null;
var idn
function createAnswerAdder(depth, openDepth, parentEl, parentId, getPhraseFunc, renderMidleFunc){
    if(!isAuth()) return;

    var adder = $(document.createElement("i"))
    adder.addClass("fas fa-comment-dots text-primary")
    adder.attr("title","ответить")
    adder.css({ fontSize:"1.5rem", cursor:"pointer"})
    // adder.text("+")
    // <i class="fas fa-search" style="color: Mediumslateblue; font-size: 1.5rem;" ></i>




    adder.on("click", function () {

        idn = $(this).parent().children('.idn').attr('id')
        // alert(idn)
        var txt = $(this).parent().children('.qwestTxt').text()
        $('#qwestion').text(txt)

        //выставляем номер участника разговора
        var member = $(this).parent().children('.qwestTxt').attr("member")
        // alert(member)
        var newMember = 1
        if(member == 1){
            newMember = 2
        }
        //$('select option[value="Mazda"]).prop('selected', true);
        //$('#make').val('Mazda')
        $('#selectBoxMemb').val(newMember)

        $('#exampleModal').modal('toggle');

        createSelectBox()

         $('#sendAnswer').remove()

        var button = $(document.createElement("button"))
        // <button id="sendAnswer" type="button" class="btn btn-primary">Save changes</button>
        button.text("Ответить")
        button.addClass("btn")
        button.addClass("btn-primary")
        button.attr("id","sendAnswer");
        $('#buttonArea').append(button)

        button.on("click", function () {

            //
            var forTheme = 0;
            if($('#themeCheckBox').prop('checked') && currentThemeIdCookie!=0){
                forTheme = currentThemeIdCookie
            }

            // alert(themeCheckBox.prop('checked'))
            console.log("$('#inputArea')", $('#inputArea').val())
            console.log("idn", idn)
            console.log("foundid_to_use", $('#inputFoundId').val())
            console.log("for_theme", forTheme)



            // return
            // alert(input.val())


            $.ajax({
                type: "POST",
                url: "/addAnswer",
                data: '{"parent_id": "'+idn+'", "text": "'+$('#inputArea').val()+'", "foundid_to_use": "'+$('#inputFoundId').val()+'", "for_theme": "'+forTheme + '", "member": "' +$('#selectBoxMemb').val() +'"}',
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                // success: function(data){console.log(data)},
                failure: function(errMsg){console.log(errMsg)}
            }).then(function(data) {
                parentEl.children("div").empty()
                //перерисовываем парента полностью
                getPhraseFunc(parentId, depth+1, depth+1, parentEl, renderMidleFunc)
                // parentEl.children(".expander").text("-")

                parentEl.children(".expander").removeClass("fa-plus-square")
                parentEl.children(".expander").addClass("fa-minus-square")

            });


            //!!!!! pзакрываем
            $('.closeAnswer').click()

        });

    });


    return adder;
}


function initModalAnswer() {
    $('#inputArea').on('keyup', function () {
        $('#inputFoundId').val("")
        autocompleteAnsrwer(this, $('#selectBox'))
    } );






    $('.closeAnswer').on("click", function () {
        console.log("closeAnswer")
        $('#inputArea').val("")
        $('#inputFoundId').val("")
        $('#themeCheckBox').prop("checked", true);

    });
}

$(document).ready(function() {
    initModalAnswer()
});


function createSelectBox() {
    $('#selectBoxDiv').empty()
    var selectBox = $(document.createElement("select"))
    selectBox.attr("id","selectBox");
    selectBox.css({position: 'absolute', left: '253px', top: '-36px'})
    $('#selectBoxDiv').append(selectBox)

    $('#selectBox').on('change select click', function(){
        console.log("select")
        // selectBox.change(function () {
        //     var optionSelected = $("#selectBox :selected");
        var optionSelected = $('#selectBox').children(":selected");
        // var optionSelected = $(this)
        // sleep(500)
        var valueSelected  = optionSelected.val();
        var textSelected   = optionSelected.text();
        console.log("valueSelected " + valueSelected + " textSelected "+textSelected)
        if(typeof valueSelected != "undefined"){

            $('#inputFoundId').val(valueSelected)

            $('#inputArea').val(textSelected)
            // alert("gfdg")
        }
    });

}


function autocompleteAnsrwer(input, selectBox) {
    var text = $(input).val()

    if(text.length > 4){
        console.log(text)
        $.ajax({
            type: "POST",
            url: "/findAnswer",
            data: '{ "text": "'+text+'"}',
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            // success: function(data){console.log(data)},
            failure: function(errMsg){console.log(errMsg)}
        }).then(function(data) {
            console.log("data", data)

            selectBox.empty()
            for (var i = 0; i < data.length; i++) {
                var option = document.createElement("option");
                option.value = data[i].id;
                option.text = data[i].firstName;
                selectBox.append(option);
            }


            // sel.setAttribute('size', len);
            // selectBox.s().focus().click();
            selectBox.attr('size', data.length)

        });

    }

}
