using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Cryptography;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace zad3w1
{
    class Program
    {
        static void Main(string[] args)
        {
        // make population
            Population p = new Population(100, 0.15, true);

        // GA
            double fit = p.GetFittest().fitness;
            p.CountFitness();

            while (p.GetFittest().fitness < 0.5)
            {
                p.Evolve();
                p.CountFitness();
                if (p.GetFittest().fitness > fit)
                {
                    fit = p.GetFittest().fitness;
                    Console.WriteLine(p.generation.ToString() + " :\t" + p.GetFittest().fitness.ToString() + " |\t" + p.GetFittest().Genotype2string());
                }
                else if(p.generation % 100 == 0)
                {
                    Console.WriteLine(' ' + p.generation.ToString() + " :\t" + p.GetFittest().fitness.ToString() + " |\t" + p.GetFittest().Genotype2string());
                }
            }
        }
    }
}
