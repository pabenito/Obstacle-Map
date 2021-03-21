# Obstacle-Map

---DESCRIPTION---

This proyect create a random obstacle map (matix) given a seed where obstacles are generated with a 100% probability if there are (0 < obstacles around <= MAX_AROUND_WALLS) with 8 neightbour model, and with a dinamic probability that decrease exponentially with the number of obstacles alredy placed. This result in a very nice obstacle distribution in O(rows * cols) time guaranteed.

Map is treated as cyclic to calculate number of obstacles around, so that allow map to be treat as continuous map without lose coherence in borders.

---EXAMPLE---

This is one example of map generated in 0,27 ms:

- Wall = 'X'
- Path = '_'

Obstacle Map: Seed = -98832348; rows = 30; cols = 82; walls = 702.

```
       X     XXXX    X    X   X    X      XX  X  X   X     X    XX   XX          X
      XX   X       X         X  X   X       X X  X    X      X X   XXX      X     
      X  X   X      X         XX                X           X   XX    XXX         
  XXX X    X X    X  X X               XX   XX      X  XX X     X   X        X X  
     X     X    XX    X  X        X  X   X   X X    XX XXX   X  X            XXX  
X                X        X XX   XX  X X    XX     X     X   X       X   X  XX XXX
  X X   X   XX   XX               X  X  X                 X X     X X    XXX X  X 
XX     XXXX X    X        X X       XX X X  XX  X    X     X  X     X X           
    XXXX          XX  X XXX  X   X   X     XX X     X    XX  XX        X      X   
X    X               X X X                  X XX     X XXX   X     X    XX XXX   X
                XX  X    X  X XX    X       XX     XX       X        X X        X 
                 X X X   X   XX   XX   X     X       X               XXX         X
X    X    X        X        X  X             X        X   X     X    X X        X 
 XX      X      X          XX  X     X X       X    XXX XXX  XX  XX X XX       X X
   X  XX X   X         XX           X X   X    X    X              X  X X         
         X                    X    X  X  X      X        X        XXX           X 
        X X X           XXX            XX               XX        X XX        X   
        XX   X    XX        XX      B   XXXX      X      XX X    XXX              
 X                   X           X   X   X        X     X             X     XX    
   XXX       XX     X                 XX                              XXX  X X    
       X   XX  X             X   X   X             X  X         XXXX         XX   
   X  X  X X X    X  X X X XX    X   X X        XX XX  X XX         X  X  X      X
       X      XX  X    X       X       XX        X   X X X           X  XX X   XX 
 X    XX   X           XX X    X     X  X X X   X     X X      XX             X  X
X        XX      X    XX   X    X X     X  X   X       X       X  X   XX        XX
X     X        X XX XXXX    XX XX    X  X X X X    X X      X  X  X    X          
 X   X         X  X    X XXX X       X       X  X X  XXX   X X   XXX  XXX         
 X         XXXXX X X X X   X         XX  X     XX   XX  X          XX   X    XX   
 X  X X   X    X  XX     X      X      XXE                         X   X     XX   
 XXXX     X X X    X X    X XX X  X XX   X        X  XX   X       X  X  X    XX X
 ```

Can be setted a beggining and an ending.
