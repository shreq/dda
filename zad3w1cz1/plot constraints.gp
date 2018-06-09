# setup
set xlabel "X"
set ylabel "Y"
set zlabel "Z"
set xrange [ 0: 18]
set yrange [ 0: 15]
set grid
set style function lines
set ytics 1
set xtics 1
set isosamples 33, 33

# definitions
f2(x,y) = x * y + x - y - 3.5 <= 0
f3(x,y) = 10 - x * y <= 0

# action
splot f2(x,y) lc rgb "blue",  \
      f3(x,y) lc rgb "red"

#pause mouse any
pause -1
