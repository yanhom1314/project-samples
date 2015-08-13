# jsmart [![spm version](http://spmjs.io/badge/jsmart)](http://spmjs.io/package/jsmart)

---

jSmart is a port of the Smarty Template Engine to Javascript.


---

## Install

```
$ spm install jsmart --save
```

## Usage

```js
require('jsmart');
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
// use jsmart
```
## 注意
```js
function heredoc(fn) {
    return fn.toString().split('\n').slice(1,-1).join('\n') + '\n'
}
```
