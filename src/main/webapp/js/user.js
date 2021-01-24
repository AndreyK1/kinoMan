$(document).ready(function() {
    // console.log("getuserinfo start")
    $.ajax({
        type: "POST",
        url: "/getuserinfo",
        data: '{}',
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        // success: function(data){console.log(data)},
        failure: function(errMsg){console.log(errMsg)}
    }).then(function(data) {
        // console.log("userinfo ",data)
        userInfo = null
        if(data.name){
            userInfo = data
            // $('#userInfoDiv').html(data.name + "  роль: " + data.roles);
            $('#userInfoDiv').html(" <a href=\"#l\" class=\"nav-link\">"+data.name+"</a>");
            $('#userLogoutLi').html(" <a href=\"/logout\" class=\"nav-link\">logout</a>");

            $('#rolesLi').css({display:"block"});

            for(var i=0; i< data.roles.length; i++){
                $('#homeSubmenu').html($('#homeSubmenu').html()+"<li><a href=\"#\" class=\"nav-link\">"+data.roles[i]+"</a></li>")
            }


            $('#addThemaButton').css({display:"block"});
        }

    });
});