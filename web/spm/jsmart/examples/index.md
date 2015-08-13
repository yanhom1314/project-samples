# Demo

---

## Normal usage
````html
<!doctype html>
<html>
<head>
    <title> ztree demo </title>
    <meta http-equiv="content-type" content="text/html; charset=utf-8">
    <script src="http://cdn.staticfile.org/seajs/2.3.0/sea.js"></script>
    <script type="text/javascript">
        <!--
        seajs.config({
            base: '/dist',
            alias: {
                'jsmart':'jsmart/2.13.1/jsmart-debug'
            }
        });
        seajs.use('jsmart', function(jsmart) {
          var data = {
                greeting: 'Hi, there are some JScript books you may find interesting:',
                books : [
                    {
                        title  : 'JavaScript: The Definitive Guide',
                        author : 'David Flanagan',
                        price  : '31.18'
                    },
                    {
                        title  : 'Murach JavaScript and DOM Scripting',
                        author : 'Ray Harris',
                    },
                    {
                        title  : 'Head First JavaScript',
                        author : 'Michael Morrison',
                        price  : '29.54'
                    }
                ]
            };

              var tpl = heredoc(function(){/*
                <h1>{$greeting}</h1>

                {foreach $books as $i => $book}
                    <div style="background-color: {cycle values="cyan,yellow"};">
                        [{$i+1}] {$book.title|upper} by {$book.author}
                            {if $book.price}
                                Price: <span style="color:red">${$book.price}</span>
                            {/if}
                    </div>
                {foreachelse}
                    No books
                {/foreach}

                Total: {$book@total}
              */});
              var output = new jSmart(tpl).fetch(data);
              console.log(output);
        });
        function heredoc(fn) {
            return fn.toString().split('\n').slice(1,-1).join('\n') + '\n'
        }
        //-->
    </script>

</head>
<body>  
</body>
</html>
````

````javascript
function heredoc(fn) {
    return fn.toString().split('\n').slice(1,-1).join('\n') + '\n'
}
seajs.use('jsmart', function(jsmart) {
  var data = {
        greeting: 'Hi, there are some JScript books you may find interesting:',
        books : [
            {
                title  : 'JavaScript: The Definitive Guide',
                author : 'David Flanagan',
                price  : '31.18'
            },
            {
                title  : 'Murach JavaScript and DOM Scripting',
                author : 'Ray Harris',
            },
            {
                title  : 'Head First JavaScript',
                author : 'Michael Morrison',
                price  : '29.54'
            }
        ]
    };

      var tpl = heredoc(function(){/*
        <h1>{$greeting}</h1>

        {foreach $books as $i => $book}
            <div style="background-color: {cycle values="cyan,yellow"};">
                [{$i+1}] {$book.title|upper} by {$book.author}
                    {if $book.price}
                        Price: <span style="color:red">${$book.price}</span>
                    {/if}
            </div>
        {foreachelse}
            No books
        {/foreach}

        Total: {$book@total}
      */});
      var output = new jSmart(tpl).fetch(data);
      console.log(output);
});
````
