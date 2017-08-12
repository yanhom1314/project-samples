package hello

type Stack struct {
	data []interface{}
}

func (s *Stack) Push(x interface{}) {
	s.data = append(s.data, x)
}

func (s *Stack) Pop() interface{} {
	i := len(s.data) - 1
	res := s.data[i]
	s.data[i] = nil
	s.data = s.data[:i]
	return res
}
func (s *Stack) Size() int {
	return len(s.data)
}
