
//
// //TODO
// var currentChainIdsFromCookie;
// var startIdFromCookie;
// var currentThemeIdCookie;
//
//
// // var strOfChain = $.cookie("strOfChain");
// var strOfChain = Cookies.get('strOfChain');
// console.log("strOfChain", strOfChain)
//
// if (typeof strOfChain != 'undefined'){
//     // alert(selectedDays)
//     currentChainIdsFromCookie =JSON.parse(strOfChain)
//     currentChainIdsFromCookie = currentChainIdsFromCookie.map(function(item) {
//         return parseInt(item, 10);
//     });
//     startIdFromCookie = currentChainIdsFromCookie[0]
// }else{
//     currentChainIdsFromCookie = []
// }
//
// var startIdFromCookie = Cookies.get('startIdFromCookie');
// console.log("startIdFromCookie", startIdFromCookie)
// if (typeof startIdFromCookie == 'undefined'){
//     startIdFromCookie = 0
// }
//
// var currentThemeIdCookie = Cookies.get('currentThemeIdCookie');
// console.log("currentThemeIdCookie", currentThemeIdCookie)
// if (typeof currentThemeIdCookie == 'undefined'){
//     currentThemeIdCookie = 0
// }
//




// function setCurrentChainIds(strChain){
//     currentChainIdsFromCookie = strChain.split("-")
//     currentChainIdsFromCookie = currentChainIdsFromCookie.filter(Number)
//     console.log("currentChainIdsFromCookie" ,currentChainIdsFromCookie)
//
//     var strOfChain = JSON.stringify(currentChainIdsFromCookie);
//     // Cookies.remove('bookableDates');
//     // Cookies.set('bookableDates', BookableArr);
//
//     // createCookie('bookableDates', BookableArr);
//
//
//     // $.cookie("strOfChain", strOfChain, { expires: getAgeOfCookie(), path: '/' });
//     //7 day from now
//     Cookies.set("strOfChain", strOfChain, { expires: 7, path: '/' });
//
//
//     setStartIdFromCookie(currentChainIdsFromCookie[0])
//
// }

// function setStartIdFromCookie(startId){
//     Cookies.set("startIdFromCookie", startId, { expires: 7, path: '/' });
// }


// function setThemeCookie(id_theme, start_id){
//     console.log("id_theme", id_theme)
//     console.log("start_id", start_id)
//     setStartIdFromCookie(start_id)
//     Cookies.set("currentThemeIdCookie", id_theme, { expires: 7, path: '/' });
//     currentThemeIdCookie = id_theme
//
// }


// function getAgeOfCookie(){
//     var date = new Date();
//     var days = 30;
//     var expdate =date.setTime(date.getTime() + (days *24 * 60 * 60 * 1000))
//     alert(expdate)
//     return expdate;
//
// }