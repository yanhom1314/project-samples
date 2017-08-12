package main

import (
	"fmt"
	"strings"
)

func Nice() string {
	return "Nice!!!"
}

//export Test
func Test() {
	fmt.Println("export Test")
}

func main() {
	f := strings.Split("1+2+3", "+")
fmt.Printf("%d*************%s************\r\n", len(f), f[0])
}
