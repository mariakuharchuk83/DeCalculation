package main

import (
	"fmt"
	"strconv"
	"sync"
	"time"
)

type BusStop struct {
	channel chan struct{}
}

func (stop *BusStop) park() {
	stop.channel <- struct{}{}
}

func (stop *BusStop) leave() {
	<-stop.channel
}

func newBusStop(maxNumOfBuses int) *BusStop {
	stop := &BusStop{make(chan struct{}, maxNumOfBuses)}
	return stop
}

func bus(stops []*BusStop, number int, wg *sync.WaitGroup) {
	for i := 0; i < len(stops); i++ {
		stops[i].park()
		fmt.Println("#Bus" + strconv.Itoa(number) + " arrived to #BusStop" + strconv.Itoa(i))
		time.Sleep(time.Second)
		stops[i].leave()
		fmt.Println("#Bus" + strconv.Itoa(number) + " left the #BusStop" + strconv.Itoa(i))
	}
	wg.Done()
}

func main() {
	numOfBuses := 6
	numOfStops := 3
	numMaxOfBuses := 2
	stops := make([]*BusStop, numOfStops)
	for i := 0; i < numOfStops; i++ {
		stops[i] = newBusStop(numMaxOfBuses)
	}

	wg := sync.WaitGroup{}
	wg.Add(numOfBuses)
	for i := 0; i < numOfBuses; i++ {
		go bus(stops, i, &wg)
	}
	wg.Wait()
}
