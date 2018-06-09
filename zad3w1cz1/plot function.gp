# setup
set xlabel "X"
set ylabel "Y"
set zlabel "Z"
set xrange [ 0: 18]
set yrange [ 0: 15]
set grid
set style function lines
set isosamples 33, 33

# definitions
f1(x,y) = 100 * (x**2 - y)**2 + (1 - x)**2 + 10

# action
splot f1(x,y) lc rgb "blue"

#pause mouse any
pause -1
