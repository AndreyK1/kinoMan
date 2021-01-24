
// function addUser() {
//     $.ajax({
//         type: "POST",
//         url: "/users",
//         data: '{"name" : "Test", "email" : "test@test.com" }',
//         contentType: "application/json; charset=utf-8",
//         dataType: "json",
//         success: function(data){console.log(data)},
//         failure: function(errMsg){console.log(errMsg)}
//     }).then(function(data) {
//         console.log(data)
//         $('.greeting-id').append(data.id);
//         $('.greeting-content').append(data.content);
//     });
// }

console.log("show1t.js")

function getPhrase() {
    $.ajax({
        type: "GET",
        url: "/tree/"+22,
        // data: '{"name" : "Test", "email" : "test@test.com" }',
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function(data){console.log(data)},
        failure: function(errMsg){console.log(errMsg)}
    }).then(function(data) {
        console.log(data)
        renderOuter(data)
        // $('.greeting-id').append(data.id);
        // $('.greeting-content').append(data.content);
    });
}

getPhrase()

// addUser()

// d3.select("h1").style("color","green")
//https://blog.js.cytoscape.org/2016/05/24/getting-started/

// cytoscape.load_extra_layouts()


var cy = cytoscape({
    container: document.getElementById('cy'),
    elements: [],
    style: [
        {
            selector: 'node',
            style: {
                shape: 'hexagon',
                'background-color': 'red',
                label: 'data(id)'
            }
        }]
});


function renderInner(data){
    // cy.add({
    //         data: { id: 'node' + data.id }
    //     }
    // );
    var source = 'node' + data.id;

    if(data.childs.length > 0){
        // alert(data.childs.length)
        for(var i=0; i < data.childs.length; i++) {

            //add node
            var id = data.childs[i].id;

            // added.push('node' + data.id)




            console.log("add", id)

            id = createId(id)

            var dist = 'node' + id

            //проверяем, что такого элемента нет в системе
            // var elInCy = cy.$id(dist);
            // if(elInCy.length > 0){
            //     dist += "t"
            //     id += "t"
            // }
            // console.log("b",b.length)

            cy.add({
                    data: {id: dist}
                }
            );

            //add edge
            cy.add({
                data: {
                    id: 'edge' + id,
                    source: source,
                    target: dist
                }
            });

            renderInner(data.childs[i])
        }
    }

    cy.layout({
        // name: 'preset',
        name: 'circle',
        // name: 'breadthfirst',
        roots: '[id = "'+data.id+'"]'
    }).run();
}

function createId(id){
    //проверяем, что такого элемента нет в системе
    while(cy.$id('node' +id).length > 0){
        id += "t"
    }
    return id
    // var elInCy = cy.$id(dist);
    // if(elInCy.length > 0){
    //     dist += "t"
    //     id += "t"
    // }

}


function renderOuter(data){
    cy.add({
            data: { id: 'node' + data.id }
        }
    );
    var source = 'node' + data.id;
    renderInner(data)
    // var b = cy.$id("node24");
    // console.log("bdddd",b)
}


// var cy = cytoscape({
//     container: document.getElementById('cy'),
//     elements: [
//         { data: { id: 'a', name: 'gog' } },
//         { data: { id: 'b', name: 'mof' } },
//         {
//             data: {
//                 id: 'ab',
//                 source: 'a',
//                 target: 'b'
//             }
//         }],
//     // layout: {
//     //     name: 'grid'
//     // },
//     style: [
//         {
//             selector: 'node',
//             style: {
//                 shape: 'hexagon',
//                 'background-color': 'red',
//                 label: 'data(id)'
//             }
//         }]
// });



// cy.layout({
//     name: 'grid'
// }).run();
//
// var b = cy.$('#b');
// console.log("b",b)
// var directlyConnected = b.neighborhood();
// console.log("directlyConnected",directlyConnected)
//
// var node6 = cy.$('#node6')
// // node6.add(b)
// b.parent(node6)
//
//
// cy.layout({
//     name: 'grid'
// }).run();


cy.on('tap', 'node', function(evt){
    var node = evt.target;
    console.log( 'tapped ' + node.id() );
    cy.add({
            data: { id: 'node7' }
        }
    );
    var source = node.id();
    cy.add({
        data: {
            id: 'edge',
            source: source,
            target:'node7'
        }
    });

    //https://dash.plotly.com/cytoscape/layout
    cy.layout({
        name: 'grid',
        // name: 'breadthfirst',
        roots: '[id = "a"]'
    }).run();

});
