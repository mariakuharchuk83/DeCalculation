package main

import (
	"math"
	"math/rand"
	"time"
)

func createFighters(size int) chan int {
	list := make(chan int, size)

	s := rand.NewSource(time.Now().Unix())
	random := rand.New(s)

	for i := 0; i < size; i++ {
		val := random.Intn(100) + 1
		list <- val
	}
	println()
	return list
}

func fight(list chan int, nextloop chan int) {
	fighter1 := <-list
	fighter2 := <-list
	if fighter1 > fighter2 {
		println("Fight", fighter1, "vs", fighter2, ":", fighter1, "wins")
		nextloop <- fighter1
	} else {
		println("Fight", fighter1, "vs", fighter2, ":", fighter2, "wins")
		nextloop <- fighter2
	}
}

func main() {
	const size = 4
	fighters := createFighters(size)

	loops := int(math.Log2(size))
	fights := size / 2

	for i := 1; i <= loops; i++ {
		nextFighter := make(chan int, fights)
		if i == loops {
			go fight(fighters, nextFighter)
			winner := <-nextFighter
			println("\nWinner of the fight is:", winner)
			break
		}

		for i := 0; i < fights; i++ {
			go fight(fighters, nextFighter)
		}

		fights /= 2
		fighters = nextFighter
	}
}
