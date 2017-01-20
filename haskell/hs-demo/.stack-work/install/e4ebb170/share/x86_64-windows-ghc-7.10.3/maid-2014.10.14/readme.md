Maid
====

A simple static web server

install
-------

    cabal install maid

run
---

### on port 3000

    maid

### on port 5000


    maid 5000

custom css
----------

### place `maid.css` in current path

default is

    body {
    line-height: 1.5em;
    font-size: 1.3em;
    }

      .directory a
    , .directory a:visited {
    color: grey;
    }

      a
    , a:visited {
    text-decoration: none;
    color: #222;
    display: block;
    background: #eee;
    padding: 3px;
    padding-left: 20px;
    }

    a:hover {
    background: #ccc;
    }

    li {
    list-style-type: none;

    width: 80%;
    margin: 5px;
    }


custom overwritten mime-types
-----------------------------

### place `mime.types` in current path

default: `src/Web/Maid/ApacheMimeTypes.hs`

