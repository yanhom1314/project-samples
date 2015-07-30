package main

import (
	"bufio"
	"fmt"
	"io"
	"mathii"
	"newmath"
	"os"
)

func main() {
	result := newmath.Sqrt(2)
	fmt.Printf("Hello World!\n")
	fmt.Printf("Number:%d\n", result)
	mathii.SayHi("YaFengLi")
	fi, err := os.Open("e:/tmp/1.txt")

	if err != nil {
		panic(err)
	}
	defer func() {
		if err := fi.Close(); err != nil {
			panic(err)
		}
	}()
	reader := bufio.NewReader(fi)
	for {
		n, err := reader.ReadString()
	}
}
