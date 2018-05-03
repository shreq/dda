reset
set term gif animate
set output "som.gif"    # name of output file

n=100                    # frames, should be equal to number of files
set xrange [-10:13]
set yrange [-12:9]

i=0
load "load_pic.gp"
set output