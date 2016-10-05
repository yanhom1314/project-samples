package main

import (
	"encoding/json"
	"fmt"
)

type User struct {
	Un    string   `json:"un"`
	Count int      `json:"count"`
	Ips   []IPAddr `json:"ips"`
}

type IPAddr struct {
	IP     string `json:"ip"`
	Time   string `json:"time"`
	Online bool   `json:"online"`
}

func main() {
	un := &User{Un: "test_caocheng_1", Count: 100}
	un.Ips = append(un.Ips, IPAddr{IP: "127.0.0.1", Time: "2016090801220"})
	un.Ips = append(un.Ips, IPAddr{IP: "127.0.0.2", Time: "2016090801222"})
	un.Ips = append(un.Ips, IPAddr{IP: "127.0.0.3", Time: "2016090801223", Online: true})
	b, err := json.Marshal(un)
	if err != nil {
		fmt.Println(err)
		return
	}
	fmt.Println(string(b))

	m := make(map[string]IPAddr)
	m["1"] = IPAddr{IP: "127.0.0.1", Time: "2016090801220"}
	m["2"] = IPAddr{IP: "127.0.0.2", Time: "2016090801220"}
	m["3"] = IPAddr{IP: "127.0.0.1", Time: "2016090801220"}
	m["4"] = IPAddr{IP: "127.0.0.1", Time: "2016090801220"}

	// Can this be done better?
	ips := make([]IPAddr, len(m))
	idx := 0
	for _, v := range m {
		ips[idx] = v
		idx++
	}

	c, err_c := json.Marshal(ips)
	if err_c != nil {
		fmt.Println(err_c)
		return
	}
	fmt.Println(string(c))
}
