package main

import (
	"fmt"
	"net/http"
	 "flag"
 )
func main() {
	realPath := flag.String("path", "e:/Github/project-samples/web/angularjs", "static resource path")
	flag.Parse()
	fmt.Printf("Path:%s.\n",*realPath)
    http.Handle("/", http.FileServer(http.Dir(*realPath)))
    http.ListenAndServe(":80", nil)
 }
