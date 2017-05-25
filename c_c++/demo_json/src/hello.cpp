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
    vector<int> vi{1,2,3,4,5,6,7,8};
    for (auto ite = vi.begin(); ite != vi.end(); ++ite) {
        cout << ite.base() << endl;
    }
    cout << "Hello World!" << endl;
    return 0;
}