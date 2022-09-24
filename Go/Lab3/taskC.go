package main

import (
	"math/rand"
	"sync"
)

var currentEngred []string

func containsElement(elements []string, element string) bool {
	for i := 0; i < len(elements); i++ {
		if elements[i] == element {
			return true
		}
	}
	return false
}

func addElements() {
	var elementsList = []string{"tobacco", "paper", "matches"}
	first := elementsList[rand.Intn(3)]
	second := elementsList[rand.Intn(3)]
	if first == second {
		first = elementsList[rand.Intn(3)]
	}
	print("Stranger give us: " + first + ", " + second + "\n")

	currentEngred = append(currentEngred, first, second)
}

func smoking(element string, wg *sync.WaitGroup, goSmoke *bool) {
	if !containsElement(currentEngred, element) && !*goSmoke {
		currentEngred = append(currentEngred, element)
		print("Smoker takes also " + element + "\n")
		print("Start smoking\n\n")
		currentEngred = []string{}
		*goSmoke = true
	}
	wg.Done()
}

func main() {
	var waitGroup = sync.WaitGroup{}

	for i := 0; i < 5; i++ {
		var goSmoke bool = false
		waitGroup.Add(1)
		go func() {
			addElements()
			waitGroup.Done()
		}()

		waitGroup.Wait()
		waitGroup.Add(3)
		go smoking("matches", &waitGroup, &goSmoke)
		go smoking("tobacco", &waitGroup, &goSmoke)
		go smoking("paper", &waitGroup, &goSmoke)
		waitGroup.Wait()
	}
}
