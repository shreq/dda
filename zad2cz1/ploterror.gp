# cmd/ps > gnuplot "ploterror.gp"

set xlabel "% of epochs"
set ylabel "error"
plot './res/error.dat' with linespoints ls 1

pause mouse any
#pause -1
