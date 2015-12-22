var ractive = new Ractive({
    // The `el` option can be a node, an ID, or a CSS selector.
    el: '#container',

    // We could pass in a string, but for the sake of convenience
    // we're passing the ID of the <script> tag above.
    template: '#template',

    // Here, we're passing in some initial data
    data: {
        name: 'ya_feng_li@163.com',
        greeting: 'Hello',
        color: 'purple',
        size: 4,
        font: 'Georgia',
        counter: 0
    }
});

//not jquery 
//document.getElementById("count").addEventListener('click',function(){
 //   ractive.set('counter',ractive.get('counter')+1);
//});

//use jquery
$(function(){
    $("#count").click(function(){
        ractive.set('counter',ractive.get('counter')+1);
        $.get('test.json',function(data){
            ractive.set('name',data.name);
            if(window.console) window.console.log(data.name);
        });
    });
});
