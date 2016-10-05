package main

import "C"

func Nice() string {
	return "Nice!!!"
}


//export Test
func Test() {
	println("export Test")
}