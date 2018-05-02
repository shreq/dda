# cmd/ps > gnuplot "ploterror.gp"

set xlabel "epoch"
set ylabel "error"
plot './res/nens2'

pause mouse any
#pause -1
