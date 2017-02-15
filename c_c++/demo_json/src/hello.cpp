//
// Created by YaFengLi on 2017/2/6.
//

#include <iostream>
#include <vector>

using namespace std;

template<typename Creator>
void processProduct(const Creator &creator) {
    auto inst = creator.makeObject();
}

int main(int argc, char *argv[]) {
    auto i = 12;
    decltype(i) y = i;
    cout << y << endl;
    vector<int> vi;
    for (auto ite = vi.begin(); ite != vi.end(); ++ite) {

    }
    cout << "Hello World!" << endl;
    return 0;
}