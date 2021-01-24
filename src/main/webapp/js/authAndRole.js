

function isAuth(){
    // if(userInfo !== undefined && userInfo == null){
    //     // console.l og("you are not authenticated!!!", userInfo)
    // }
    return userInfo != null;
}


function hasRole(role) {
    var has=false

    if (userInfo == null)
        return has

    for(var i=0; i<userInfo.roles.length; i++){
        if(userInfo.roles[i] == role){
            has=true
        }
    }
    console.log("hasRople", has)
    return has
}