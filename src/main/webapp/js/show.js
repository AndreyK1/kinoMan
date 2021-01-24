
$(document).ready(function() {


    // console.log("userInfo", userInfo)
    // if(isAuth()) {
    //     $('#addThemaButton').css({display:"block"});
    // }


    var port = $('#be')

    // var idFromCookie = 22
    // var currentChainIdsFromCookie = [22,23,1204,1225,1226]

    var openDepthConst = 1

    // var idFromCookie = 1226
    // var currentChainIdsFromCookie = [1225,1226]
    // var chainIdsFromCookie = []
    //22-23-1204-1225-1226



    //если мы просто открываем тему (для показа), то мы не выводим элементы в колбаке с startIdFromCookie
    selectTheme(currentThemeIdCookie,
        // alert("hi")
        function(start_id_in_theme){
            getPhrase(startIdFromCookie, 0, openDepthConst, port, renderMidle)
        }
    )


    showAllThemes(function(start_id_in_theme){
        getPhrase(start_id_in_theme, 0, openDepthConst, port, renderMidle)
    })



    // getPhrase(27, 0, openDepthConst, port, renderMidle)






    // alert(currentChainIdsFromCookie)

    console.log("currentChainIdsFromCookie", currentChainIdsFromCookie)



    function addIdInChain(id, where){
        var sizeOfChain = 7
        if(where == "begin"){
            if(currentChainIdsFromCookie.length > sizeOfChain){
                currentChainIdsFromCookie.pop()
                // chainIdsFromCookie.shift()

            }
            currentChainIdsFromCookie.unshift(id)
        }else if(where == "end"){
            if(currentChainIdsFromCookie.length > sizeOfChain){
                // chainIdsFromCookie.pop()
                currentChainIdsFromCookie.shift()

            }
            currentChainIdsFromCookie.push(id)
        }
        console.log("where - "+where+" id- "+id, currentChainIdsFromCookie)
    }








    function getPhrase(id, depth, openDepth, elOfChainParents, renderFuction) {
        if (id == 0){ return; }
        // console.log("getPhrase : id - " + id)
        $.ajax({
            type: "GET",
            url: "/tree/" + id,
            // data: '{"name" : "Test", "email" : "test@test.com" }',
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data) {
                console.log(data)
            },
            failure: function (errMsg) {
                console.log(errMsg)
            }
        }).then(function (data) {
            // console.log(data)
            console.log("renderFuction", renderFuction)
            renderFuction(data, depth, openDepth, elOfChainParents)
            // $('.greeting-id').append(data.id);
            // $('.greeting-content').append(data.content);
        });
    }

    // getPhrase(22, 0, 1, port)



    function renderInner(data, depth, openDepth, parentEl) {
        // console.log("renderInner data ", data)



        // if (data.childs && data.childs.length > 0) {
        if (data.childs && data.childs.length > 0 &&  data.childs[0]['id'] ) {

            var havechildFromCurrArr = false
            for (var i = 0; i < data.childs.length; i++) {
                if (currentChainIdsFromCookie.includes(data.childs[i].id)){
                    havechildFromCurrArr = true;
                }
            }

            for (var i = 0; i < data.childs.length; i++) {

                //если есть тема, то отфильтровываем по текущей. Иначе считаем что это универсальный ответ

                //console.log("data.childs[i]", data.childs[i])
                //console.log("currentThemeIdCookie", currentThemeIdCookie)
                if(data.childs[i].theme  && data.childs[i].theme.id != currentThemeIdCookie){
                    console.log("hide data.childs[i].theme.id", data.childs[i].theme.id)
                    // alert(data.childs[i].theme.id)
                    continue;
                }

                // console.log("findIndex " , currentChainIdsFromCookie.indexOf(data.childs[i].id))
                // console.log("lastInsex " , currentChainIdsFromCookie.indexOf(data.childs[i].id))


                // console.log("parentEl ", parentEl)

                var child1 =  createElementOfChain(data.childs[i], depth, openDepth, i, parentEl)

                if (depth > openDepth && !havechildFromCurrArr) {
                    // console.log("hide " + data.childs[i].id )
                    // child1.parent().children(".expander").text("+")
                    // parentEl.children(".expander").text("+")
                    parentEl.children(".expander").removeClass("fa-minus-square")
                    parentEl.children(".expander").addClass("fa-plus-square")

                    child1.hide()
                }else{
                    // child1.parent().parent().children(".expander").text("-")
                    // parentEl.children(".expander").text("-")
                    parentEl.children(".expander").removeClass("fa-plus-square")
                    parentEl.children(".expander").addClass("fa-minus-square")
                }

                parentEl.append(child1)


                // console.log(depth, depth%3)
                if(depth > 20 && depth%20 == 1 ){
                    var r = confirm("Вы погрузились в монолог на "+depth+ " фраз, продолжаем?");
                    if (r == false) {
                        return
                    }
                }
                renderInner(data.childs[i], depth + 1, openDepth,  child1)
            }
        }else{
            // console.log("data.id "+data.id+ " findIndex nn " , currentChainIdsFromCookie.indexOf(data.id))
            //если у нас в массиве открытой цепочки есть этот член, и он не последний в цепочке, лезем за ним в БД
            if(currentChainIdsFromCookie.indexOf(data.id) > -1){
                if(currentChainIdsFromCookie[currentChainIdsFromCookie.length-1] != data.id){
                    // console.log("here")
                    getPhrase(data.id, depth, openDepth, parentEl, renderMidle)
                }
            }
        }
    }


    function renderParentNew(childData, depth, openDepth, elemOfChainParents ,elOfChainChilds) {
        if(childData.parents.length < 1){
            return
        }
        renderMidle(childData.parents[0], depth, openDepth, elemOfChainParents ,elOfChainChilds)
    }

    function renderMidle(data, depth, openDepth, elemOfChainParents) {
        // console.log("renderMidle depth " + depth + " openDepth " + openDepth)

        var elemForChainP = elemOfChainParents;

        if (depth == 0) {
            //рисуем корневой элемент
            elemForChainP = createParentElement(data, depth, openDepth, elemOfChainParents)
            depth++

        }
        renderInner(data, depth, openDepth, elemForChainP)
    }


    // function renderParent(childData, depth, openDepth, elOfChainChilds) {
    //     if(childData.parents.length < 1){
    //         return
    //     }
    //
    //     var parent = createParentElement( childData.parents[0], depth, openDepth, elOfChainChilds)
    //
    //     // console.log("elOfChainChilds.children(v)", elOfChainChilds.children("div")  )
    //     parent.append(elOfChainChilds.children("div"))
    //
    //
    //     // port.empty()
    //     // port.html("")
    //     port.append(parent)
    //     // port = $('#be')
    //
    // }

    function createParentElement(data, depth, openDepth, elemOfChainParents){
        // console.log("createParentElement data ", data)

        var parent = createElementOfChain(data, depth, openDepth, null, elemOfChainParents)

        var showParent = $(document.createElement("b"))
        showParent.html("&#8593; ")

        showParent.on("click", function () {

            // console.log("parent click this data.parents", this, data.parents, data.parents.length)


            // port.empty()
            // getPhrase(data.parents[0].id, 0, 1, port)
            if(data.parents.length > 0){
                port.empty()
            }

            if(data.parents.length > 0 && data.parents[0].id ) {
                addIdInChain(data.parents[0].id, "begin")
                // renderParent(data, depth, openDepth, elOfChain)
                renderMidle(data.parents[0], 0, openDepthConst, port)
            }else{
                getPhrase(data.id, 0, openDepthConst, port, renderParentNew)
            }

            this.remove()
        })

        parent.prepend(showParent)

        elemOfChainParents.append(parent)

        return parent
    }

    function createElementOfChain(data, depth, openDepth, i, parentEl){
        var parent = $(document.createElement("div"))
        parent.css({paddingLeft:"30px"})
        // parent.addClass("border rounded")

        // <div class="them-col col-sm-4 col-md-2 m-2 p-2 border rounded bg-white" style="border-radius: 30px;">-->

        var expander = createExpander(data, depth, i)
        parent.append(expander)

        // var idn = $(document.createElement("span"))
        // idn.text(data.id)
        // idn.addClass("idn")
        // parent.append(idn)

        var txt = $(document.createElement("span"))
        txt.attr('id',  data.id);
        txt.attr('title', data.id);
        var theme_id = 0
        if(data.theme){
            theme_id = data.theme.id
        }
        txt.attr('thema', theme_id);
        txt.addClass("idn")
        txt.addClass("border rounded border-primary p-2 my-1")
        // txt.css({display:"none"})
        txt.text( data.firstName)
        txt.addClass("qwestTxt")


        txt.attr('member', data.member);
        if(data.member==1){
            txt.css({ fontSize:"1rem", backgroundColor:"#F0EDF0"})

        }else if(data.member==2){
            txt.css({backgroundColor:"#CDCACD"})
        }else{
            txt.css({backgroundColor:"#B6B3B6"})
        }

        parent.append(txt)

        var chainIds = $(document.createElement("span"))
        chainIds.css({display:"none"})
        chainIds.addClass("chainIds")
        var parChainIds = parentEl.children(".chainIds").text()
        chainIds.text(parChainIds+"-"+data.id)
        parent.append(chainIds)



        var adder = createAnswerAdder(depth, openDepth, parent, data.id, getPhrase, renderMidle)
        parent.append(adder)

        if(data.theme == null){//если универмальный ответ
            var universal = $(document.createElement("i"))
            universal.addClass("fas fa-random")
            universal.css({ fontSize:"1rem", color:"#A8A1C7"})
            universal.attr("title","универсальный ответ для фразы (не зависит от темы)")

            parent.append(universal)

        }

        var editor = createEditor(depth, openDepth, parent, data.id, getPhrase, renderMidle)
        parent.append(editor)



        return parent
    }



    function createEditor(depth, openDepth, parentEl, parentId, getPhraseFunc, renderMidleFunc){

        if(!hasRole("ROLE_ADMIN")){
            return
        }
        var editBut = $(document.createElement("i"))
        editBut.addClass("fas fa-edit")
        editBut.css({cursor:"pointer", fontSize:"1rem", color:"#A8A1C7"})
        editBut.attr("title","отредактировать")


        editBut.on("click", function () {

            idn = $(this).parent().children('.idn').attr('id')


            parentId = $(this).parent().parent().children('.idn').attr('id')

            // console.log("idn "+ idn+ " parentId "+parentId)
            // console.log("parentEl ", parentEl[0].outerHTML)
            // console.log("parentElN ", $(this).parent().parent()[0].outerHTML)

            parentEl = $(this).parent().parent()


            // alert(idn)
            var txt = $(this).parent().children('.qwestTxt').text()
            $('#qwestionArea').val(txt)

            //номер участника разговора
            var member = $(this).parent().children('.qwestTxt').attr("member")
            var thema = $(this).parent().children('.qwestTxt').attr("thema")


            $('#inputMember').val(member)
            $('#inputThemaEd').val(thema)
            $('#currentThemaId').text(currentThemeIdCookie)



            $('#editModal').modal('toggle');

            // createSelectBox()

            $('#sendEdit').remove()

            var button = $(document.createElement("button"))
            // <button id="sendAnswer" type="button" class="btn btn-primary">Save changes</button>
            button.text("Изменить")
            button.addClass("btn")
            button.addClass("btn-primary")
            button.attr("id", "sendEdit");
            $('#buttonAreaEd').append(button)

            button.on("click", function () {

                //
                // var forTheme = 0;
                // if($('#themeCheckBox').prop('checked') && currentThemeIdCookie!=0){
                //     forTheme = currentThemeIdCookie
                // }

                // // alert(themeCheckBox.prop('checked'))
                // console.log("$('#inputArea')", $('#inputArea').val())
                // console.log("idn", idn)
                // console.log("foundid_to_use", $('#inputFoundId').val())
                // console.log("for_theme", forTheme)

                $.ajax({
                    type: "POST",
                    url: "/editAnswer",
                    data: '{"foundid_to_use": "'+idn+'", "text": "'+$('#qwestionArea').val()+'", "for_theme": "'+$('#inputThemaEd').val() + '", "member": "' +$('#inputMember').val() +'"}',
                    contentType: "application/json; charset=utf-8",
                    dataType: "json",
                    // success: function(data){console.log(data)},
                    failure: function(errMsg){console.log(errMsg)}
                }).then(function(data) {
                    parentEl.children("div").empty()
                    //перерисовываем парента полностью
                    console.log("ерерисовываем парента полностью", data)
                    getPhraseFunc(parentId, depth+1, depth+1, parentEl, renderMidleFunc)
                    // parentEl.children(".expander").text("-")

                    parentEl.children(".expander").removeClass("fa-plus-square")
                    parentEl.children(".expander").addClass("fa-minus-square")

                });


                //!!!!! pзакрываем
                $('.closeEdit').click()
            });

         });



        return editBut

    }




    function createExpander(data, depth, iter){
        // var href = $(document.createElement("b"))
        // href.addClass("expander")

        var href = $(document.createElement("i"))
        href.addClass("expander fas text-primary")
        href.css({ fontSize:"1.5rem", cursor:"pointer"})
        href.attr("title","свернуть/развернуть")

        if(iter == null){  //если отрисовываем родителя
            // href.text("-")
            href.addClass("fa-minus-square")
        }else{
            // href.text("+")
            href.addClass("fa-plus-square")
        }

        if(data.childs.length == 0){
            // href.text("-")
            href.removeClass("fa-plus-square")
            href.addClass("fa-minus-square")
        }

        href.on("click", function () {


            setCurrentChainIds($(this).parent().children(".chainIds").text())
            // if($(this).have.text() == "+"){
             if($(this).hasClass("fa-plus-square")){

                // addIdInChain(data.childs[iter].id, "end")
                // $(this).text("-")
                 $(this).removeClass("fa-plus-square")
                 $(this).addClass("fa-minus-square")

                var found = 0;
                $(this).parent().children('div').each(function () {
                    found++
                    $(this).show() // "this" is the current element in the loop
                });

                //если наследников нет, то идем за ними  на сервер
                if(found == 0){
                    console.log("go in DB")
                    getPhrase(data.id, depth+1, depth+1,$(this).parent(), renderMidle)

                }

            }else{
                // $(this).text("+")
                 $(this).removeClass("fa-minus-square")
                 $(this).addClass("fa-plus-square")

                $(this).parent().children('div').each(function () {
                    $(this).hide()
                });
            }
        })

        return href;
    }








});

