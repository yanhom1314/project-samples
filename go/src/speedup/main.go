package main

import (
	"flag"
	"fmt"
	"io/ioutil"
	"net/http"
	"strings"
	"time"
)

var myClient = &http.Client{Timeout: 10 * time.Second}

func httpGet(url string) string {
	resp, err := http.Get(url)
	if err != nil {
		// handle error
	}

	defer resp.Body.Close()
	body, err := ioutil.ReadAll(resp.Body)
	if err != nil {
		// handle error
	}

	return string(body)
}

var etURL = "http://61.160.183.220/jsts/ebit/start"
var key = "greatbit@2012"

func main() {
	un := flag.String("un", "b00139748", "User Number")
	flag.Parse()
	fmt.Println(*un)
	body := httpGet("http://221.231.154.58:9696/ip?un=" + *un)

	var m map[string]string
	var ss []string

	ss = strings.Split(body, "&")
	m = make(map[string]string)
	for _, pair := range ss {
		z := strings.Split(pair, "=")
		m[z[0]] = z[1]
	}
	fmt.Println(ss)
	ip, exists := m["ip"]
	if exists {
		fmt.Println(ip)
		c1 := httpGet(etURL + "/" + *un + "/" + ip + "/down/100?k=" + key)
		fmt.Println(c1)
		c2 := httpGet(etURL + "/" + *un + "/" + ip + "/up/10?k=" + key)
		fmt.Println(c2)
	}
}
