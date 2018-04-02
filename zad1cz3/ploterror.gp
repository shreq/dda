# cmd/ps > gnuplot "ploterror.gp"

set xlabel "epoch"
set ylabel "error"
plot '../res/error.dat'

pause -1
